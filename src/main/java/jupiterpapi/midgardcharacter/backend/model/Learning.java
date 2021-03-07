package jupiterpapi.midgardcharacter.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Learning {
    String id;
    String characterId;
    String skillName;
    boolean starting;
    boolean learned;
    int newBonus;
    int percentageGold;
    int epSpent;
    int goldSpent;
    int PPSpent;
}
