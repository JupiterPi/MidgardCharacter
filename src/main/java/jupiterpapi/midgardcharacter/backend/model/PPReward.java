package jupiterpapi.midgardcharacter.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PPReward {
    String id;
    String characterId;
    String skillName;
    int pp;
}
