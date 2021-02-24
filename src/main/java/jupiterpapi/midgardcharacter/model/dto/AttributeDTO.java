package jupiterpapi.midgardcharacter.model.dto;

import lombok.Data;

@Data
public class AttributeDTO {
    String id;
    String name;
    String characterId;
    int value;
    int bonus;
}
