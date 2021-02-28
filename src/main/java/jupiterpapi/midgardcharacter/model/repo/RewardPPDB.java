package jupiterpapi.midgardcharacter.model.repo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @Document
public class RewardPPDB {
    @Id
    String id;

    String characterId;
    String skillName;
    int PP;
}
