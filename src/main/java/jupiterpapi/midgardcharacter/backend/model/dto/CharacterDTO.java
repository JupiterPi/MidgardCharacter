package jupiterpapi.midgardcharacter.backend.model.dto;

import jupiterpapi.midgardcharacter.backend.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data @NoArgsConstructor
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

    Collection<AttributeDTO> attributes = new ArrayList<>();
    List<Skill> skills = new ArrayList<>();
    List<LevelUp> levelUps = new ArrayList<>();
    List<Reward> rewards = new ArrayList<>();
    List<RewardPP> rewardsPP = new ArrayList<>();
    List<Learn> learnings = new ArrayList<>();

    public CharacterDTO(String id,String name, String userId, String className) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.className = className;
    }
}
