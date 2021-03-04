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
        return new SkillCost(args.get(0).replaceAll("\\s+", ""), Integer.parseInt(args.get(1).replaceAll("\\s+", "")),
                Integer.parseInt(args.get(2).replaceAll("\\s+", "")), args.get(3).replaceAll("\\s+", ""),
                args.get(4).replaceAll("\\s+", ""), Integer.parseInt(args.get(5).replaceAll("\\s+", "")),
                args.get(6).replaceAll("\\s+", ""));
    }
    public String getKey() {
        return skill;
    }
}
