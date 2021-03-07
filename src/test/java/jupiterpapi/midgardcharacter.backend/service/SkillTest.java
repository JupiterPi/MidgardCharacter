package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Skill;
import lombok.var;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SkillTest extends TestFactory {

    @Test
    public void getBaseAttributeOfSkill() throws MidgardException {
        var attribute = skillService.getBaseAttributeOfSkill("Akrobatik");
        assertEquals(attribute, "Gw");
    }

    @Test(expected = MidgardException.class)
    public void getBaseAttributeOfSkillFail() throws MidgardException {
        skillService.getBaseAttributeOfSkill("XYZ");
    }

    @Test
    public void getStartingBonusOfSkill() throws MidgardException {
        var bonus = skillService.getStartingBonusOfSkill("Akrobatik");
        assertEquals(8, bonus);
    }

    @Test(expected = MidgardException.class)
    public void getStartingBonusOfSkillFail() throws MidgardException {
        var bonus = skillService.getStartingBonusOfSkill("XYZ");
        assertEquals(8, bonus);
    }

    @Test
    public void checkSkillName() throws MidgardException {
        skillService.checkSkillName("Akrobatik");
    }

    @Test(expected = MidgardException.class)
    public void checkSkillNameFail() throws MidgardException {
        skillService.checkSkillName("XYZ");
    }

    Skill getSkill(String skillName, int bonus, boolean learned) {
        return new Skill(skillName, "ID", bonus, 0, bonus, 0, 0, 0, learned);
    }

    @Test
    public void getInitialSkills() {
        Skill exp = getSkill("Akrobatik", 6, false);
        var skills = skillService.getInitialSkills("ID");
        var opt = skills.stream().filter(s -> s.getName().equals("Akrobatik")).findFirst();
        assertTrue(opt.isPresent());
        assertEquals(opt.get(), exp);
    }

    @Test
    public void calculateCostNew() throws MidgardException {
        Skill s = getSkill("Akrobatik", 0, false);
        var c = skillService.calculateCost(s, "As");
        Assert.assertEquals(c.getTECost(), 6);
        Assert.assertEquals(c.getEPCost(), 60);
    }

    @Test
    public void calculateCostIncrease() throws MidgardException {
        Skill s = getSkill("Akrobatik", 8, true);
        var c = skillService.calculateCost(s, "As");
        Assert.assertEquals(c.getTECost(), 2);
        Assert.assertEquals(c.getEPCost(), 20);
    }

    @Test(expected = MidgardException.class)
    public void calculateCostFailSkillName() throws MidgardException {
        Skill s = getSkill("XYZ", 0, false);
        var c = skillService.calculateCost(s, "As");
    }

    @Test(expected = MidgardException.class)
    public void calculateCostFailClassName() throws MidgardException {
        Skill s = getSkill("Akrobatik", 0, false);
        var c = skillService.calculateCost(s, "XYZ");
    }

    @Test
    public void calculateCostHighBonus() throws MidgardException {
        Skill s = getSkill("Akrobatik", 99, true);
        var cost = skillService.calculateCost(s, "As");
        assertEquals(1000, cost.getTECost());
        assertEquals(1000, cost.getEPCost());
    }


}
