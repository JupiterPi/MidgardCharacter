package jupiterpapi.midgardcharacter.backend.model.repo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class PPRewardDB {
    @Id
    String id;

    String characterId;
    String skillName;
    int PP;
}
