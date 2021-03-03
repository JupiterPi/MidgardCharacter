package jupiterpapi.midgardcharacter.backend.model.create;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data @NoArgsConstructor
public class CharacterCreate {
    String id;
    String name;
    String userId;
    String className;
    int ap;

    Collection<AttributeCreate> attributes = new ArrayList<>();
    Collection<LearningCreate> learnings = new ArrayList<>();

    public CharacterCreate(String id, String name, String userId, String className, int ap) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.className = className;
        this.ap = ap;
    }
}
