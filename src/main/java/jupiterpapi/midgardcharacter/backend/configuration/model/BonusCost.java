package jupiterpapi.midgardcharacter.backend.configuration.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor
public class BonusCost {
    String line;
    int bonus;
    int cost;

    public static BonusCost read(List<String> args) {
        if (args.size() < 3) {
            return null;
        }
        return new BonusCost(args.get(0), Integer.parseInt(args.get(1)), Integer.parseInt(args.get(2)));
    }
    public String getKey() {
        return line + "/" + bonus;
    }
}
