package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Skill;
import lombok.var;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SkillTest extends TestFactory {

    @Test
    public void getBaseAttributeOfSkill() throws UserException {
        var attribute = skillService.getBaseAttributeOfSkill("Akrobatik");
        assertEquals(attribute, "Gw");
    }

    @Test(expected = UserException.class)
    public void getBaseAttributeOfSkillFail() throws UserException {
        skillService.getBaseAttributeOfSkill("XYZ");
    }

    @Test
    public void getStartingBonusOfSkill() throws UserException {
        var bonus = skillService.getStartingBonusOfSkill("Akrobatik");
        assertEquals(8, bonus);
    }

    @Test
    public void getStartingBonusOfSkillFail() throws UserException {
        var bonus = skillService.getStartingBonusOfSkill("XYZ");
        assertEquals(8, bonus);
    }

    @Test
    public void checkSkillName() throws UserException {
        skillService.checkSkillName("Akrobatik");
    }

    @Test(expected = UserException.class)
    public void checkSkillNameFail() throws UserException {
        skillService.checkSkillName("XYZ");
    }

    @Test
    public void getInitialSkills() {
        Skill exp = new Skill("Akrobatik", "ID", 6, false);
        var skills = skillService.getInitialSkills("ID");
        var opt = skills.stream().filter(s -> s.getName().equals("Akrobatik")).findFirst();
        assertTrue(opt.isPresent());
        assertEquals(opt.get(), exp);
    }

    @Test
    public void calculateCostNew() throws UserException {
        Skill s = new Skill("Akrobatik", "ID", 0, false);
        var c = skillService.calculateCost(s, "As");
        Assert.assertEquals(c.getTECost(), 6);
        Assert.assertEquals(c.getEPCost(), 60);
    }

    @Test
    public void calculateCostIncrease() throws UserException {
        Skill s = new Skill("Akrobatik", "ID", 8, true);
        var c = skillService.calculateCost(s, "As");
        Assert.assertEquals(c.getTECost(),2);
        Assert.assertEquals(c.getEPCost(),20);
    }

    @Test(expected = UserException.class)
    public void calculateCostFailSkillName() throws UserException {
        Skill s = new Skill("XYZ", "ID", 0, false);
        var c = skillService.calculateCost(s, "As");
    }

    @Test(expected = UserException.class)
    public void calculateCostFailClassName() throws UserException {
        Skill s = new Skill("Akrobatik", "ID", 0, false);
        var c = skillService.calculateCost(s, "XYZ");
    }

    @Test
    public void calculateCostHighBonus() throws UserException {
        Skill s = new Skill("Akrobatik", "ID", 99, true);
        var cost = skillService.calculateCost(s, "As");
        assertEquals(1000, cost.getTECost());
        assertEquals(1000, cost.getEPCost());
    }


}
