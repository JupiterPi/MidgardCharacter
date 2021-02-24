package jupiterpapi.midgardcharacter.service;

import jupiterpapi.midgardcharacter.model.dto.*;

import java.util.List;

public interface MidgardService {
    List<UserDTO> getUsers();
    List<CharacterInfoDTO> getCharacters(String userId);
    CharacterDTO getCharacter(String characterId) throws MidgardException;

    UserDTO postUser(UserDTO user);

    CharacterDTO postCharacter(CharacterDTO character) throws MidgardException;
    CharacterDTO postReward(RewardDTO reward) throws MidgardException;
    CharacterDTO postRewardPP(RewardPPDTO rewardPP) throws MidgardException;
    CharacterDTO postLearn(LearnDTO learnDTO) throws MidgardException;
    CharacterDTO postLevelUp(LevelUpDTO levelUp) throws MidgardException;
}
