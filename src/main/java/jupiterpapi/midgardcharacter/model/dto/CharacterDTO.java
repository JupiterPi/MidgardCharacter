package jupiterpapi.midgardcharacter.model.dto;

import jupiterpapi.midgardcharacter.model.*;
import lombok.Data;

import java.util.List;

@Data
public class CharacterDTO {
    String id;
    String name;
    String userId;
    int level;
    String className;
    int ep;
    int es;
    int gold;
    int ap;

    List<AttributeDTO> attributes;
    List<Skill> skills;
    List<LevelUp> levelUps;
    List<Reward> rewards;
    List<RewardPP> rewardsPP;
    List<Learn> learnings;
}
