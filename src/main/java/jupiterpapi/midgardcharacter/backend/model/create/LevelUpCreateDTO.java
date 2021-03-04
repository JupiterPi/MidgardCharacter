package jupiterpapi.midgardcharacter.backend.model.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LevelUpCreateDTO {
    String id;
    String characterId;
    int level;
    String attribute;
    int increase;
    int ap;
}
