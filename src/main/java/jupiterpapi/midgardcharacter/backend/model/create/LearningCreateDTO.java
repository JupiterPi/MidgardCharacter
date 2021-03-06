package jupiterpapi.midgardcharacter.backend.model.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningCreateDTO {
    String id;
    String characterId;
    String skillName;
    boolean starting;
    int percentageGold;
}
