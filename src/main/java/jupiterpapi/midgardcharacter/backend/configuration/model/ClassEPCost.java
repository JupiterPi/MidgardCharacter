package jupiterpapi.midgardcharacter.backend.configuration.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor
public class ClassEPCost {
    String className;
    String group;
    int cost;

    public static ClassEPCost read(List<String> args)  {
        if (args.size() < 3) {
            return null;
        }
        return new ClassEPCost(
                args.get(0),
                args.get(1),
                Integer.parseInt(args.get(2))
        );
    }
    public String getKey() {
        return className + "/" + group;
    }
}
