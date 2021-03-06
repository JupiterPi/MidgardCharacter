package jupiterpapi.midgardcharacter.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class PPRewardDTO {
    String id;
    String characterId;
    String skillName;
    int pp;
}
