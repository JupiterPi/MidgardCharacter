package jupiterpapi.midgardcharacter.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class Attribute {
    String id;
    String name;
    String characterId;
    int value;
    int bonus;
}
