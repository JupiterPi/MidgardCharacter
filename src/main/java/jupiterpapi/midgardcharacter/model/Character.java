package jupiterpapi.midgardcharacter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data @NoArgsConstructor
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

    HashMap<String,Attribute> attributes = new HashMap<>();
    HashMap<String,Skill> skills = new HashMap<>();
    List<LevelUp> levelUps = new ArrayList<>();
    List<Reward> rewards = new ArrayList<>();
    List<RewardPP> rewardsPP = new ArrayList<>();
    List<Learn> learnings = new ArrayList<>();

    public Character(String id, String name, String userId, String className) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.className = className;
    }
}
