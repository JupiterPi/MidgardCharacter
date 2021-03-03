package jupiterpapi.midgardcharacter.backend.model.repo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class CharacterDB {
    @Id
    String id;

    String name;
    String className;
    String userId;
    int level;
    String createdAt;
}
