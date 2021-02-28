package jupiterpapi.midgardcharacter.backend.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

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
    Collection<SkillDTO> skills = new ArrayList<>();
    Collection<LevelUpDTO> levelUps = new ArrayList<>();
    Collection<RewardDTO> rewards = new ArrayList<>();
    Collection<PPRewardDTO> rewardsPP = new ArrayList<>();
    Collection<LearningDTO> learnings = new ArrayList<>();

    public CharacterDTO(String id,String name, String userId, String className) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.className = className;
    }
}
