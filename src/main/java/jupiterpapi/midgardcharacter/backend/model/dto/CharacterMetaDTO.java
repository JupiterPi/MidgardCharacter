package jupiterpapi.midgardcharacter.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterMetaDTO {
    String id;
    String name;
    String userId;
    String className;
    int level;
    String createdAt;
}
