package jupiterpapi.midgardcharacter.model;

import lombok.Data;

@Data
public class RewardPP {
    String id;
    String characterId;
    String skillName;
    int PP;
}
