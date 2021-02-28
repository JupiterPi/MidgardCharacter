package jupiterpapi.midgardcharacter.configuration.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor
public class SkillCost {
    String skill;
    int startingBonus;
    String attribute;
    String groups;
    int le;
    String line;

    public static SkillCost read(List<String> args) {
        if (args.size() < 6) {
            return null;
        }
        return new SkillCost(args.get(0),
                             Integer.parseInt(args.get(1)),
                             args.get(2),
                             args.get(3),
                             Integer.parseInt(args.get(4)),
                             args.get(5));
    }
    public String getKey() {
        return skill;
    }
}
