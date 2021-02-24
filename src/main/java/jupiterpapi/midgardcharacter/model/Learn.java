package jupiterpapi.midgardcharacter.model;

import lombok.Data;

@Data
public class Learn {
    String id;
    String characterId;
    String skillName;
    boolean starting;
    boolean learned;
    int newBonus;
    int epSpent;
    int goldSpent;
    int PPSpent;
}
