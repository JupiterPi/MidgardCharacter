package jupiterpapi.midgardcharacter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CharacterInfoDTO {
    String id;
    String name;
    String userId;
}
