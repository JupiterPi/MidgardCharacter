package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.*;
import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.create.*;
import jupiterpapi.midgardcharacter.backend.model.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UIMapper {
    User map(UserCreate user);

    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "levelUps", ignore = true)
    @Mapping(target = "rewards", ignore = true)
    @Mapping(target = "rewardsPP", ignore = true)
    Character map(CharacterCreate character);
    List<Attribute> mapAttributesCreate(Collection<AttributeCreate> attribute);
    List<Learn> mapLearningsCreate(Collection<LearningCreate> learnings);

    RewardPP map(PPRewardCreate rewardPP);
    @Mapping(target = "PPSpent", ignore = true)
    @Mapping(target = "epSpent", ignore = true)
    @Mapping(target = "goldSpent", ignore = true)
    @Mapping(target = "learned", ignore = true)
    Learn map(LearningCreate lean);
    Reward map(RewardCreate reward);
    LevelUp map(LevelUpCreate levelUp);


    UserDTO map(User user);
    CharacterMetaDTO mapInfo(Character character);

    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "skills", ignore = true)
    CharacterDTO map(Character character);
    Collection<AttributeDTO> mapAttributes(Collection<Attribute> attribute);
    Collection<RewardDTO> mapRewards(Collection<Reward> reward);
    Collection<PPRewardDTO> mapPPRewards(Collection<RewardPP> rewardPP);
    Collection<LearningDTO> mapLearnings(Collection<Learn> reward);
    Collection<LevelUpDTO> mapLevelUps(Collection<LevelUp> reward);
}
