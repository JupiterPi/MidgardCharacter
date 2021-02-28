package jupiterpapi.midgardcharacter.backend.model.dto;

import lombok.Data;

@Data
public class SkillDTO {
    String name;
    String characterId;
    int bonus;
    int attributeBonus;
    int totalBonus;
    int TECost;
    int EPCost;
    int PP;
}
