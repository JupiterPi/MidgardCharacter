package jupiterpa.model.dto;

import lombok.Data;

@Data
public class LevelUpDTO {
    String characterId;
    int level;
    String attribute;
    int increase;
    int ap;
}
