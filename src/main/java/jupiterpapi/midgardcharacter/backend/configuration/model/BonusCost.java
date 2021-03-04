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
        return new BonusCost(args.get(0).replaceAll("\\s+", ""), Integer.parseInt(args.get(1).replaceAll("\\s+", "")),
                Integer.parseInt(args.get(2).replaceAll("\\s+", "")));
    }
    public String getKey() {
        return line + "/" + bonus;
    }
}
