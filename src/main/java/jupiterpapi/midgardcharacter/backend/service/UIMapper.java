package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;
import jupiterpapi.midgardcharacter.backend.model.create.*;
import jupiterpapi.midgardcharacter.backend.model.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("WrongUsageOfMappersFactory")
@Mapper(componentModel = "spring")
public interface UIMapper {
    UIMapper INSTANCE = Mappers.getMapper(UIMapper.class);

    User map(UserCreate user);

    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "levelUps", ignore = true)
    @Mapping(target = "rewards", ignore = true)
    @Mapping(target = "rewardsPP", ignore = true)
    @Mapping(target = "es", ignore = true)
    @Mapping(target = "ep", ignore = true)
    @Mapping(target = "gold", ignore = true)
    @Mapping(target = "level", ignore = true)
    Character map(CharacterCreate character);

    List<Attribute> mapAttributesCreate(Collection<AttributeCreate> attribute);

    List<Learning> mapLearningsCreate(Collection<LearningCreate> learnings);

    PPReward map(PPRewardCreate rewardPP);

    @Mapping(target = "PPSpent", ignore = true)
    @Mapping(target = "epSpent", ignore = true)
    @Mapping(target = "goldSpent", ignore = true)
    @Mapping(target = "learned", ignore = true)
    Learning map(LearningCreate lean);

    Reward map(RewardCreate reward);

    LevelUp map(LevelUpCreate levelUp);

    UserDTO map(User user);

    CharacterMetaDTO mapInfo(Character character);

    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "skills", ignore = true)
    CharacterDTO map(Character character);

    Collection<AttributeDTO> mapAttributes(Collection<Attribute> attributes);

    Collection<RewardDTO> mapRewards(Collection<Reward> rewards);

    Collection<PPRewardDTO> mapPPRewards(Collection<PPReward> PPRewards);

    Collection<LearningDTO> mapLearnings(Collection<Learning> learnings);

    Collection<LevelUpDTO> mapLevelUps(Collection<LevelUp> levelUps);

    Collection<SkillDTO> mapSkills(Collection<Skill> skills);
}

