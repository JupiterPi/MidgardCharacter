package jupiterpapi.midgardcharacter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class LearnDTO {
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
