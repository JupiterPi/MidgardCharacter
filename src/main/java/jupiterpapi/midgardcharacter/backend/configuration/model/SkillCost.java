package jupiterpapi.midgardcharacter.backend.configuration.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor
public class SkillCost {
    String skill;
    int startingBonus;
    int unlearnedBonus;
    String attribute;
    String groups;
    int le;
    String line;

    public static SkillCost read(List<String> args) {
        if (args.size() < 6) {
            return null;
        }
        return new SkillCost(args.get(0), Integer.parseInt(args.get(1)), Integer.parseInt(args.get(2)), args.get(3),
                args.get(4), Integer.parseInt(args.get(5)), args.get(6));
    }
    public String getKey() {
        return skill;
    }
}
