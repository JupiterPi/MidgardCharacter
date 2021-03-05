package jupiterpapi.midgardcharacter.backend.model;

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
    String createdAt;

    HashMap<String,Attribute> attributes = new HashMap<>();
    HashMap<String,Skill> skills = new HashMap<>();
    List<LevelUp> levelUps = new ArrayList<>();
    List<Reward> rewards = new ArrayList<>();
    List<PPReward> rewardsPP = new ArrayList<>();
    List<Learning> learnings = new ArrayList<>();

    public Character(String id, String name, String userId, String className, int ap) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.className = className;
        this.level = 1;
        this.ep = 0;
        this.es = 0;
        this.gold = 0;
        this.ap = ap;
    }
}
