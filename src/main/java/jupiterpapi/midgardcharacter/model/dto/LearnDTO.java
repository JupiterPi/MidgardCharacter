package jupiterpapi.midgardcharacter.model.dto;

import lombok.Data;

@Data
public class LearnDTO {
    String characterId;
    String skillName;
    boolean starting;
    int goldSpent;
    int PPSpent;
}
