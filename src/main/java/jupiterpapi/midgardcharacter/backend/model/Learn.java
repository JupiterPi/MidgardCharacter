package jupiterpapi.midgardcharacter.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
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
