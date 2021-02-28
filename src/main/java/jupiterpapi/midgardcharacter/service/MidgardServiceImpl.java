package jupiterpapi.midgardcharacter.service;

import jupiterpapi.midgardcharacter.model.Character;
import jupiterpapi.midgardcharacter.model.*;
import jupiterpapi.midgardcharacter.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MidgardServiceImpl implements MidgardService {

    @Autowired
    UIMapper mapper;

    @Autowired
    DBServiceImpl db;

    @Autowired
    EnrichService enrichService;
    @Autowired
    CheckService checkService;

    public List<UserDTO> getUsers() {
        List<User> users = db.getUser();
        return users.stream().map( user -> mapper.map(user) ).collect(Collectors.toList());
    }

    public List<CharacterInfoDTO> getCharacters(String userId) {
        List<Character> users = db.getCharacters(userId);
        return users.stream().map( character -> mapper.mapInfo(character) ).collect(Collectors.toList());
    }

    public CharacterDTO getCharacter(String characterId) throws UserException {
        Character newCharacter = enrichService.getCharacter(characterId);
        CharacterDTO newCharacterDTO = mapper.map( newCharacter );
        List<AttributeDTO> attributes =
                newCharacter.getAttributes().values().stream()
                        .map(a->mapper.map(a))
                        .sorted(Comparator.comparing(AttributeDTO::getId))
                        .collect(Collectors.toList());
        newCharacterDTO.setAttributes( attributes );
        newCharacterDTO.setRewards(newCharacter.getRewards());
        newCharacterDTO.setRewardsPP(newCharacter.getRewardsPP());
        newCharacterDTO.setLearnings(newCharacter.getLearnings());
        newCharacterDTO.setLevelUps(newCharacter.getLevelUps());
        return newCharacterDTO;
    }

    public UserDTO postUser(UserDTO user) {
        return mapper.map( db.postUser( mapper.map(user) ) );
    }

    public CharacterDTO postCharacter(CharacterDTO character) throws UserException {
        Character c = mapper.map(character);
        List<Attribute> list = character.getAttributes().stream().map( a -> mapper.map(a)).collect(Collectors.toList());
        checkService.checkNewCharacter(c,list);
        db.postCharacter( c );
        db.postAttributes( list );
        return getCharacter(character.getId());
    }

    public CharacterDTO postReward(RewardDTO reward) throws UserException {
        Reward r = mapper.map(reward);
        checkService.checkReward(r);
        db.postReward(r);
        return getCharacter(reward.getCharacterId());
    }

    public CharacterDTO postRewardPP(RewardPPDTO rewardPP) throws UserException {
        RewardPP r = mapper.map(rewardPP);
        checkService.checkRewardPP(r);
        db.postRewardPP( r );
        return getCharacter(rewardPP.getCharacterId());
    }

    public CharacterDTO postLearn(LearnDTO learn) throws UserException {
        Learn l = mapper.map(learn);
        checkService.checkLearn(l);
        db.postLearn(l);
        return getCharacter(learn.getCharacterId());
    }

    public CharacterDTO postLevelUp(LevelUpDTO levelUp) throws UserException {
        LevelUp l = mapper.map(levelUp);
        checkService.checkLevelUp(l);
        db.postLevelUp( l );
        return getCharacter(levelUp.getCharacterId());
    }
}
