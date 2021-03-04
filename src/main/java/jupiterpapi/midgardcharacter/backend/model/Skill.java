package jupiterpapi.midgardcharacter.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class Skill {
    String name;
    String characterId;
    int bonus;
    int attributeBonus;
    int totalBonus;
    int TECost;
    int EPCost;
    int PP;
    boolean learned;

    public Skill(String name, String characterId, int bonus, boolean learned) {
        this.name = name;
        this.characterId = characterId;
        this.bonus = bonus;
    }

}
