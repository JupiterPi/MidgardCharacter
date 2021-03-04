package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.create.*;
import jupiterpapi.midgardcharacter.backend.model.dto.CharacterDTO;
import jupiterpapi.midgardcharacter.backend.model.dto.CharacterMetaDTO;
import jupiterpapi.midgardcharacter.backend.model.dto.UserDTO;

import java.util.Collection;

public interface MidgardService {
    Collection<UserDTO> getUsers();

    Collection<CharacterMetaDTO> getCharacters(String userId);

    CharacterDTO getCharacter(String characterId) throws UserException;

    UserDTO postUser(UserCreateDTO user);

    CharacterDTO postCharacter(CharacterCreateDTO character) throws UserException;

    CharacterDTO postReward(RewardCreateDTO reward) throws UserException;

    CharacterDTO postRewardPP(PPRewardCreateDTO rewardPP) throws UserException;

    CharacterDTO postLearning(LearningCreateDTO learning) throws UserException;

    CharacterDTO postLevelUp(LevelUpCreateDTO levelUp) throws UserException;
}
