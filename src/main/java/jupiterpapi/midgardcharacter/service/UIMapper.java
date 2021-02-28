package jupiterpapi.midgardcharacter.service;

import jupiterpa.model.*;
import jupiterpa.model.dto.*;
import jupiterpapi.midgardcharacter.model.Character;
import jupiterpapi.midgardcharacter.model.*;
import jupiterpapi.midgardcharacter.model.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UIMapper {
    UserDTO map(User user);

    CharacterInfoDTO mapInfo(Character character);

    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "rewardsPP", ignore = true)
    @Mapping(target = "rewards", ignore = true)
    @Mapping(target = "levelUps", ignore = true)
    @Mapping(target = "learnings", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    CharacterDTO map(Character character);

    User map(UserDTO user);

    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "rewardsPP", ignore = true)
    @Mapping(target = "rewards", ignore = true)
    @Mapping(target = "levelUps", ignore = true)
    @Mapping(target = "learnings", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    Character map(CharacterDTO character);

    Attribute map(AttributeDTO attribute);
    AttributeDTO map(Attribute attribute);

    Reward map(RewardDTO reward);

    RewardPP map(RewardPPDTO rewardPP);

    Learn map(LearnDTO lean);

    LevelUp map(LevelUpDTO levelUp);
}

