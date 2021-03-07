package jupiterpapi.midgardcharacter.backend.model.repo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class LearningDB {
    @Id
    String id;

    String characterId;
    String skillName;
    boolean starting;
    boolean learned;
    int newBonus;
    int percentageGold;
    int epSpent;
    int goldSpent;
    int PPSpent;
}
