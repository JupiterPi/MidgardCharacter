package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Skill;
import lombok.var;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SkillTest extends TestFactory {

    @Test
    public void getAttribute() throws UserException {
        var attribute = skillService.getAttribute("Akrobatik");
        assertEquals(attribute,"Gw");
    }

    @Test(expected = UserException.class)
    public void getAttributeFail() throws UserException {
        skillService.getAttribute("XYZ");
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
    public void calculateCostNew() throws UserException {
        Skill s = new Skill("Akrobatik","ID",0);
        var c = skillService.calculateCost(s,"As");
        Assert.assertEquals(c.getTECost(),6);
        Assert.assertEquals(c.getEPCost(),60);
    }

    @Test
    public void calculateCostIncrease() throws UserException {
        Skill s = new Skill("Akrobatik","ID",8);
        var c = skillService.calculateCost(s,"As");
        Assert.assertEquals(c.getTECost(),2);
        Assert.assertEquals(c.getEPCost(),20);
    }

    @Test(expected = UserException.class)
    public void calculateCostFailSkillName() throws UserException {
        Skill s = new Skill("XYZ","ID",0);
        var c = skillService.calculateCost(s,"As");
    }

    @Test(expected = UserException.class)
    public void calculateCostFailClassName() throws UserException {
        Skill s = new Skill("Akrobatik","ID",0);
        var c = skillService.calculateCost(s,"XYZ");
    }

    @Test(expected = UserException.class)
    public void calculateCostFailBonus() throws UserException {
        Skill s = new Skill("Akrobatik","ID",99);
        var c = skillService.calculateCost(s,"As");
    }

}
