package jupiterpapi.midgardcharacter.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Reward {
    String id;
    String characterId;
    int ep;
    int gold;
}
