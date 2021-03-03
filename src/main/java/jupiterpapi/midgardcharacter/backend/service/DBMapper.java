package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;
import jupiterpapi.midgardcharacter.backend.model.repo.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DBMapper {
    @SuppressWarnings("WrongUsageOfMappersFactory")
    DBMapper INSTANCE = Mappers.getMapper(DBMapper.class);

    User map(UserDB user);

    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "rewardsPP", ignore = true)
    @Mapping(target = "rewards", ignore = true)
    @Mapping(target = "levelUps", ignore = true)
    @Mapping(target = "learnings", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "ap", ignore = true)
    @Mapping(target = "es", ignore = true)
    @Mapping(target = "ep", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "gold", ignore = true)
    Character map(CharacterDB character);

    @Mapping(target = "bonus", ignore = true)
    Attribute map(AttributeDB attribute);

    LevelUp map(LevelUpDB levelUp);
    Reward map(RewardDB reward);
    RewardPP map(RewardPPDB rewardPP);

    Learning map(LearningDB learn);

    UserDB map(User user);
    CharacterDB map(Character character);

    AttributeDB map(Attribute attribute);
    LevelUpDB map(LevelUp levelUp);
    RewardDB map(Reward reward);

    RewardPPDB map(RewardPP rewardPP);

    LearningDB map(Learning learning);
}
