package jupiterpapi.midgardcharacter.configuration;

import jupiterpapi.midgardcharacter.configuration.model.BonusCost;
import jupiterpapi.midgardcharacter.configuration.model.ClassEPCost;
import jupiterpapi.midgardcharacter.configuration.model.SkillCost;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class ConfigurationService {

    public Map<String, SkillCost> skillCost;
    public Map<String, ClassEPCost> classEPCost;
    public Map<String, BonusCost> bonusCost;

    public void read() throws InternalException {
        try {
            bonusCost = new ReadUtility<BonusCost>().readToMap("BonusCost.txt", BonusCost::read, BonusCost::getKey);
            classEPCost = new ReadUtility<ClassEPCost>().readToMap("ClassEPCost.txt", ClassEPCost::read, ClassEPCost::getKey);
            skillCost = new ReadUtility<SkillCost>().readToMap("SkillCost.txt", SkillCost::read, SkillCost::getKey);
        } catch (IOException  e) {
            e.printStackTrace();
            throw new InternalException();
        }
    }

}
