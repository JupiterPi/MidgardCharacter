package jupiterpapi.midgardcharacter.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LevelUp {
    String id;
    String characterId;
    int level;
    String attribute;
    int increase;
    int ap;
}
