package jupiterpapi.midgardcharacter.backend.model.repo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @Document
public class RewardDB {
    @Id
    String id;

    String characterId;
    int ep;
    int gold;
}
