package jupiterpapi.midgardcharacter.backend.model.repo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @Document
public class LevelUpDB {
    @Id
    String id;

    String characterId;
    int level;
    String attribute;
    int increase;
    int ap;
}
