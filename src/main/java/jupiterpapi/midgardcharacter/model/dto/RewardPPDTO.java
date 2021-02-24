package jupiterpapi.midgardcharacter.model.dto;

import lombok.Data;

@Data
public class RewardPPDTO {
    String characterId;
    String skillName;
    int PP;
}
