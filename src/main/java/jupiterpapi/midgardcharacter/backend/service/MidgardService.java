package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.create.*;
import jupiterpapi.midgardcharacter.backend.model.dto.*;

import java.util.Collection;

public interface MidgardService {
    Collection<UserDTO> getUsers();
    Collection<CharacterMetaDTO> getCharacters(String userId);
    CharacterDTO getCharacter(String characterId) throws UserException;

    UserDTO postUser(UserCreate user);

    CharacterDTO postCharacter(CharacterCreate character) throws UserException;
    CharacterDTO postReward(RewardCreate reward) throws UserException;
    CharacterDTO postRewardPP(PPRewardCreate rewardPP) throws UserException;
    CharacterDTO postLearn(LearningCreate learn) throws UserException;
    CharacterDTO postLevelUp(LevelUpCreate levelUp) throws UserException;
}
