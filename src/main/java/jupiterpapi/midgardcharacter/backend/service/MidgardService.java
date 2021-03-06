package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.create.*;
import jupiterpapi.midgardcharacter.backend.model.dto.CharacterDTO;
import jupiterpapi.midgardcharacter.backend.model.dto.CharacterMetaDTO;
import jupiterpapi.midgardcharacter.backend.model.dto.UserDTO;

import java.util.Collection;

public interface MidgardService {
    Collection<UserDTO> getUsers();

    Collection<CharacterMetaDTO> getCharacters(String userId);

    CharacterDTO getCharacter(String characterId) throws MidgardException;

    UserDTO postUser(UserCreateDTO user);

    CharacterDTO postCharacter(CharacterCreateDTO character) throws MidgardException;

    CharacterDTO postReward(RewardCreateDTO reward) throws MidgardException;

    CharacterDTO postRewardPP(PPRewardCreateDTO rewardPP) throws MidgardException;

    CharacterDTO postLearning(LearningCreateDTO learning) throws MidgardException;

    CharacterDTO postLevelUp(LevelUpCreateDTO levelUp) throws MidgardException;
}
