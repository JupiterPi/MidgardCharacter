package jupiterpapi.midgardcharacter.backend.configuration;

import jupiterpapi.midgardcharacter.backend.configuration.model.BonusCost;
import jupiterpapi.midgardcharacter.backend.configuration.model.ClassEPCost;
import jupiterpapi.midgardcharacter.backend.configuration.model.SkillCost;
import lombok.var;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConfigurationReadTest {
    @Test
    public void readBonus() throws IOException, InternalException {
        var bonusCost = new ReadUtility<BonusCost>().readToMap("BonusCost.csv", BonusCost::read, BonusCost::getKey);

        var line1 = bonusCost.get("A/13");
        assertNotNull(line1);
        assertEquals(line1.toString(),new BonusCost("A",13,1).toString());

        var line2 = bonusCost.get("A/14");
        assertNotNull(line2);
        assertEquals(line2.toString(),new BonusCost("A",14,2).toString());
    }

    @Test
    public void readClassEP() throws IOException, InternalException {
        var classCost = new ReadUtility<ClassEPCost>()
                .readToMap("ClassEPCost.csv", ClassEPCost::read, ClassEPCost::getKey);

        var line1 = classCost.get("As/Alltag");
        assertNotNull(line1);
        assertEquals(line1.toString(),new ClassEPCost("As","Alltag",20).toString());

        var line2 = classCost.get("As/Körper");
        assertNotNull(line2);
        assertEquals(line2.toString(),new ClassEPCost("As","Körper",10).toString());
    }

    @Test
    public void readClassEp() throws IOException, InternalException {
        var skillCost = new ReadUtility<SkillCost>().readToMap("SkillCost.csv", SkillCost::read, SkillCost::getKey);

        var line1 = skillCost.get("Akrobatik");
        assertNotNull(line1);
        assertEquals(line1.toString(), new SkillCost("Akrobatik", 8, 6, "Gw", "Halbwelt,Körper", 2, "E").toString());

        var line2 = skillCost.get("Alchemie");
        assertNotNull(line2);
        assertEquals(line2.toString(), new SkillCost("Alchemie", 8, 0, "In", "Wissen", 2, "F").toString());
    }

    @Test
    public void read() throws InternalException {
        ConfigurationService service = new ConfigurationService();
        service.read();
        assertEquals(service.skillCost.size(), 72);
        assertEquals(service.classEPCost.size(), 135);
        assertEquals(service.bonusCost.size(), 151);
    }
}
