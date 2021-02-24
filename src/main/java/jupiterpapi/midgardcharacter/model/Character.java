package jupiterpapi.midgardcharacter.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class Character {
    String id;
    String name;
    String userId;
    int level;
    String className;
    int ep;
    int es;
    int gold;
    int ap;

    HashMap<String, Attribute> attributes;
    HashMap<String,Skill> skills;
    List<LevelUp> levelUps;
    List<Reward> rewards;
    List<RewardPP> rewardsPP;
    List<Learn> learnings;
}
