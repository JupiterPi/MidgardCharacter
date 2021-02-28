package jupiterpapi.midgardcharacter.backend.model;

import lombok.Data;

@Data
public class User {
    String id;
    String name;
    String password;
}
