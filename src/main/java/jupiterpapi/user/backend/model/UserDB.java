package jupiterpapi.user.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @Document
public class UserDB {
    @Id
    String id;

    String name;
    String password;
}
