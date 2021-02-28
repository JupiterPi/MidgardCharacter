package jupiterpapi.midgardcharacter.backend.model.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AttributeCreate {
    String id;
    String name;
    String characterId;
    int value;
    int bonus;
}
