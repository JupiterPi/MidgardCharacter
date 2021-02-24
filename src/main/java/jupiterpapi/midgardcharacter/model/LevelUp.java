package jupiterpapi.midgardcharacter.model;

import lombok.Data;

@Data
public class LevelUp {
    String id;
    String characterId;
    int level;
    String attribute;
    int increase;
    int ap;
}
