package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.create.*;
import jupiterpapi.midgardcharacter.backend.model.dto.CharacterDTO;
import jupiterpapi.midgardcharacter.backend.model.dto.CharacterMetaDTO;

import java.util.Collection;

public interface MidgardService {

    Collection<CharacterMetaDTO> getCharacters();

    CharacterDTO getCharacter(String characterId) throws MidgardException;

    CharacterDTO postCharacter(CharacterCreateDTO character) throws MidgardException;

    CharacterDTO postReward(RewardCreateDTO reward) throws MidgardException;

    CharacterDTO postRewardPP(PPRewardCreateDTO rewardPP) throws MidgardException;

    CharacterDTO postLearning(LearningCreateDTO learning) throws MidgardException;

    CharacterDTO postLevelUp(LevelUpCreateDTO levelUp) throws MidgardException;
}
