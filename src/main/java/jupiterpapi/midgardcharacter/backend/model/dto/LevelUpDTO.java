package jupiterpapi.midgardcharacter.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class LevelUpDTO {
    String id;
    String characterId;
    int level;
    String attribute;
    int increase;
    int ap;
}
