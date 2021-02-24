package jupiterpapi.midgardcharacter.model;

import lombok.Data;

@Data
public class Attribute {
    String id;
    String name;
    String characterId;
    int value;
    int bonus;
}
