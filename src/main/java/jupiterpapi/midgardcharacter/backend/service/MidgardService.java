package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.dto.*;

import java.util.List;

public interface MidgardService {
    List<UserDTO> getUsers();
    List<CharacterInfoDTO> getCharacters(String userId);
    CharacterDTO getCharacter(String characterId) throws UserException;

    UserDTO postUser(UserDTO user);

    CharacterDTO postCharacter(CharacterDTO character) throws UserException;
    CharacterDTO postReward(RewardDTO reward) throws UserException;
    CharacterDTO postRewardPP(RewardPPDTO rewardPP) throws UserException;
    CharacterDTO postLearn(LearnDTO learn) throws UserException;
    CharacterDTO postLevelUp(LevelUpDTO levelUp) throws UserException;
}
