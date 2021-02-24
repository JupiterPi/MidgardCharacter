package jupiterpapi.midgardcharacter.model;

import lombok.Data;

@Data
public class Skill {
    String name;
    String characterId;
    int bonus;
    int attributeBonus;
    int totalBonus;
    int TECost;
    int EPCost;
    int PP;
}
