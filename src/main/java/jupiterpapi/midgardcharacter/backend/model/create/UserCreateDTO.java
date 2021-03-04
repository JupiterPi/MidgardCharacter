package jupiterpapi.midgardcharacter.backend.model.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    String id;
    String name;
    String password;
}
