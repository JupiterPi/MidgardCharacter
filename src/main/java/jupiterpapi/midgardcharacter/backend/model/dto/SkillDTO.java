package jupiterpapi.midgardcharacter.backend.model.dto;

import lombok.Data;

@Data
public class SkillDTO {
    String name;
    int bonus;
    int attributeBonus;
    int totalBonus;
    int TECost;
    int EPCost;
    int PP;
    boolean learned;
}
