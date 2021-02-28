package jupiterpapi.midgardcharacter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AttributeDTO {
    String id;
    String name;
    String characterId;
    int value;
    int bonus;
}
