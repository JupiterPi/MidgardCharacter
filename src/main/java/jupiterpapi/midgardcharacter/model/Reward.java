package jupiterpapi.midgardcharacter.model;

import lombok.Data;

@Data
public class Reward {
    String id;
    String characterId;
    int ep;
    int gold;
}
