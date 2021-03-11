package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.common.correlationid.CorrelationContext;
import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;
import jupiterpapi.midgardcharacter.backend.model.create.*;
import jupiterpapi.midgardcharacter.backend.model.dto.*;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static jupiterpapi.midgardcharacter.backend.service.MidgardErrorMessages.INTERNAL_NO_SKILL;
import static jupiterpapi.midgardcharacter.backend.service.MidgardErrorMessages.NOT_AUTHORIZED;

@Component
public class MidgardServiceImpl implements MidgardService {

    @Autowired
    UIMapper mapper;

    @Autowired
    DBService db;

    @Autowired
    EnrichService enrichService;
    @Autowired
    CheckService checkService;

    public List<CharacterMetaDTO> getCharacters() {
        String userId = CorrelationContext.getUserName();

        List<Character> characters = db.getCharacters(userId);
        return characters.stream().map(character -> mapper.mapInfo(character)).collect(Collectors.toList());
    }

    public CharacterDTO getCharacter(String characterId) throws MidgardException {
        String userId = CorrelationContext.getUserName();

        Character newCharacter = enrichService.getCharacter(characterId);
        if (!(newCharacter.getUserId().equals(userId) || userId.equals("admin")))
            throw new MidgardException(NOT_AUTHORIZED, characterId);
        CharacterDTO newCharacterDTO = mapper.map(newCharacter);

        newCharacterDTO.setAttributes(mapper.mapAttributes(newCharacter.getAttributes().values()).stream()
                .sorted(Comparator.comparing(AttributeDTO::getName)).collect(Collectors.toList()));
        newCharacterDTO.setSkills(mapper.mapSkills(newCharacter.getSkills().values()).stream()
                .sorted(Comparator.comparing(SkillDTO::getName)).collect(Collectors.toList()));

        newCharacterDTO.setRewards(
                mapper.mapRewards(newCharacter.getRewards()).stream().sorted(Comparator.comparing(RewardDTO::getId))
                        .collect(Collectors.toList()));
        newCharacterDTO.setRewardsPP(mapper.mapPPRewards(newCharacter.getRewardsPP()).stream()
                .sorted(Comparator.comparing(PPRewardDTO::getId)).collect(Collectors.toList()));
        newCharacterDTO.setLearnings(mapper.mapLearnings(newCharacter.getLearnings()).stream()
                .sorted(Comparator.comparing(LearningDTO::getId)).collect(Collectors.toList()));
        newCharacterDTO.setLevelUps(
                mapper.mapLevelUps(newCharacter.getLevelUps()).stream().sorted(Comparator.comparing(LevelUpDTO::getId))
                        .collect(Collectors.toList()));
        return newCharacterDTO;
    }

    public CharacterDTO postCharacter(CharacterCreateDTO character) throws MidgardException {
        Character c = mapper.map(character);
        List<Attribute> list = mapper.mapAttributesCreate(character.getAttributes());

        enrichService.enrichCharacterOnCreate(c);
        checkService.checkNewCharacter(c, list);

        db.postCharacter(c);
        db.postAttributes(list);

        for (LearningCreateDTO learningCreateDTO : character.getLearnings()) {
            Learning learning = mapper.map(learningCreateDTO);
            enrichService.enrichLearningOnCreate(learning);
            checkService.checkLearningOnCreate(learning);
            db.postLearning(learning);
        }

        return getCharacter(character.getId());
    }

    public CharacterDTO postReward(RewardCreateDTO reward) throws MidgardException {
        Reward r = mapper.map(reward);
        checkService.checkReward(r);
        db.postReward(r);
        return getCharacter(reward.getCharacterId());
    }

    public CharacterDTO postRewardPP(PPRewardCreateDTO rewardPP) throws MidgardException {
        PPReward r = mapper.map(rewardPP);
        checkService.checkRewardPP(r);
        db.postRewardPP(r);
        return getCharacter(rewardPP.getCharacterId());
    }

    public CharacterDTO postLearning(LearningCreateDTO learning) throws MidgardException {
        Character character = enrichService.getCharacter(learning.getCharacterId());
        Skill skill = getSkill(character, learning.getSkillName());

        Learning l = mapper.map(learning);
        enrichService.enrichLearning(l, skill);
        checkService.checkLearning(l, skill, character);
        db.postLearning(l);
        return getCharacter(learning.getCharacterId());
    }

    private Skill getSkill(Character character, String skillName) throws MidgardException {
        var opt = character.getSkills().values().stream().filter(s -> s.getName().equals(skillName)).findFirst();
        if (!opt.isPresent())
            throw new MidgardException(INTERNAL_NO_SKILL, skillName);
        return opt.get();
    }

    public CharacterDTO postLevelUp(LevelUpCreateDTO levelUp) throws MidgardException {
        LevelUp l = mapper.map(levelUp);
        checkService.checkLevelUp(l);
        db.postLevelUp(l);
        return getCharacter(levelUp.getCharacterId());
    }
}
