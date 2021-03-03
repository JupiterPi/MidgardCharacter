package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;
import jupiterpapi.midgardcharacter.backend.model.create.*;
import jupiterpapi.midgardcharacter.backend.model.dto.*;
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
    DBService db;

    @Autowired
    EnrichService enrichService;
    @Autowired
    CheckService checkService;

    public List<UserDTO> getUsers() {
        List<User> users = db.getUser();
        return users.stream().map( user -> mapper.map(user) ).collect(Collectors.toList());
    }

    public List<CharacterMetaDTO> getCharacters(String userId) {
        List<Character> users = db.getCharacters(userId);
        return users.stream().map( character -> mapper.mapInfo(character) ).collect(Collectors.toList());
    }

    public CharacterDTO getCharacter(String characterId) throws UserException {
        Character newCharacter = enrichService.getCharacter(characterId);
        CharacterDTO newCharacterDTO = mapper.map( newCharacter );

        newCharacterDTO.setAttributes( mapper.mapAttributes( newCharacter.getAttributes().values() )
                       .stream().sorted(Comparator.comparing(AttributeDTO::getId)).collect(Collectors.toList()) ) ;

        newCharacterDTO.setRewards( mapper.mapRewards( newCharacter.getRewards() )
                .stream().sorted(Comparator.comparing(RewardDTO::getId)).collect(Collectors.toList()) ) ;
        newCharacterDTO.setRewardsPP( mapper.mapPPRewards( newCharacter.getRewardsPP() )
                       .stream().sorted(Comparator.comparing(PPRewardDTO::getId)).collect(Collectors.toList()) ) ;
        newCharacterDTO.setLearnings( mapper.mapLearnings( newCharacter.getLearnings())
                       .stream().sorted(Comparator.comparing(LearningDTO::getId)).collect(Collectors.toList()) ) ;
        newCharacterDTO.setLevelUps( mapper.mapLevelUps( newCharacter.getLevelUps())
                       .stream().sorted(Comparator.comparing(LevelUpDTO::getId)).collect(Collectors.toList()) ) ;
        return newCharacterDTO;
    }

    public UserDTO postUser(UserCreate user) {
        return mapper.map( db.postUser( mapper.map(user) ) );
    }

    public CharacterDTO postCharacter(CharacterCreate character) throws UserException {
        Character c = mapper.map(character);
        List<Attribute> list = mapper.mapAttributesCreate( character.getAttributes() );

        checkService.checkNewCharacter(c,list);

        db.postCharacter( c );
        db.postAttributes( list );

        for (LearningCreate l : character.getLearnings() ) {
            postLearn(l);
        }

        return getCharacter(character.getId());
    }

    public CharacterDTO postReward(RewardCreate reward) throws UserException {
        Reward r = mapper.map(reward);
        checkService.checkReward(r);
        db.postReward(r);
        return getCharacter(reward.getCharacterId());
    }

    public CharacterDTO postRewardPP(PPRewardCreate rewardPP) throws UserException {
        PPReward r = mapper.map(rewardPP);
        checkService.checkRewardPP(r);
        db.postRewardPP( r );
        return getCharacter(rewardPP.getCharacterId());
    }

    public CharacterDTO postLearn(LearningCreate learn) throws UserException {
        Learning l = mapper.map(learn);
        checkService.checkAndEnrichLearning(l);
        db.postLearn(l);
        return getCharacter(learn.getCharacterId());
    }

    public CharacterDTO postLevelUp(LevelUpCreate levelUp) throws UserException {
        LevelUp l = mapper.map(levelUp);
        checkService.checkLevelUp(l);
        db.postLevelUp( l );
        return getCharacter(levelUp.getCharacterId());
    }
}
