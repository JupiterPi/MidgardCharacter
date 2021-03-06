package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;
import org.junit.Before;
import org.junit.Test;

public class CheckTest extends TestBase {

    @Before
    public void hideInitialSkills() {
        enrichService.hideInitialSkills = true;
        checkService.numberOfAttributes = 0;
    }

    @Test
    public void checkNewCharacterSuccess() throws MidgardException {
        addCharacterWithAttributes();
        Character c = new Character("ID2", "Name", "User", "As", 0);
        checkService.checkNewCharacter(c, initial.getAttributes().values());
    }

    @Test(expected = MidgardException.class)
    public void checkNewCharacterFailExists() throws MidgardException {
        addCharacterWithAttributes();
        checkService.checkNewCharacter(initial, initial.getAttributes().values());
    }

    @Test(expected = MidgardException.class)
    public void checkNewCharacterFailAttributesHigh() throws MidgardException {
        addCharacter();
        initial.setId("XYZ");
        initial.getAttributes().put("Zt", new Attribute("ID/Zt", "Zt", "ID", 200, 0));
        checkService.checkNewCharacter(initial, initial.getAttributes().values());
    }

    @Test(expected = MidgardException.class)
    public void checkNewCharacterFailAttributesLow() throws MidgardException {
        addCharacter();
        initial.setId("XYZ");
        initial.getAttributes().put("Zt", new Attribute("ID/Zt", "Zt", "ID", 0, 0));
        checkService.checkNewCharacter(initial, initial.getAttributes().values());
    }

    @Test(expected = MidgardException.class)
    public void checkNewCharacterFailNumberAttributes() throws MidgardException {
        addCharacter();
        initial.setId("XYZ");
        initial.getAttributes().put("Zt", new Attribute("ID/Zt", "Zt", "ID", 0, 0));
        checkService.numberOfAttributes = 9;
        checkService.checkNewCharacter(initial, initial.getAttributes().values());
    }

    @Test
    public void checkRewardSuccess() throws MidgardException {
        addCharacterWithAttributes();
        Reward r = new Reward("1", "ID", 10, 10);
        checkService.checkReward(r);
    }

    @Test(expected = MidgardException.class)
    public void checkRewardFailEP() throws MidgardException {
        addCharacterWithAttributes();
        Reward r = new Reward("1", "ID", -1, 10);
        checkService.checkReward(r);
    }

    @Test(expected = MidgardException.class)
    public void checkRewardFailGold() throws MidgardException {
        addCharacterWithAttributes();
        Reward r = new Reward("1", "ID", 0, -1);
        checkService.checkReward(r);
    }

    @Test
    public void checkRewardPPSuccess() throws MidgardException {
        addCharacterWithAttributes();
        addLearning("Akrobatik", true, true, 8, 0, 0, 0);
        addRewardPP("Akrobatik", 2);

        checkService.checkRewardPP(initial.getRewardsPP().get(0));
    }

    @Test(expected = MidgardException.class)
    public void checkRewardPPFailPP() throws MidgardException {
        addCharacterWithAttributes();
        addLearning("Akrobatik", true, true, 8, 0, 0, 0);
        addRewardPP("Akrobatik", -1);
        checkService.checkRewardPP(initial.getRewardsPP().get(0));
    }

    @Test(expected = MidgardException.class)
    public void checkRewardPPFailGold() throws MidgardException {
        addCharacterWithAttributes();
        addLearning("Akrobatik", true, true, 8, 0, 0, 0);
        addRewardPP("XYZ", 2);
        checkService.checkRewardPP(initial.getRewardsPP().get(0));
    }

    @Test
    public void checkLevelUpSuccess() throws MidgardException {
        addCharacterWithAttributes();
        addReward(1000, 0);
        LevelUp l = new LevelUp("1", "ID", 2, "", 0, 10);

        checkService.checkLevelUp(l);
    }

    @Test(expected = MidgardException.class)
    public void checkLevelUpFailAttribute() throws MidgardException {
        addCharacterWithAttributes();
        addReward(1000, 0);
        LevelUp l = new LevelUp("1", "ID", 2, "ZZ", 1, 10);

        checkService.checkLevelUp(l);
    }

    @Test(expected = MidgardException.class)
    public void checkLevelUpFailIncrease() throws MidgardException {
        addCharacterWithAttributes();
        addReward(1000, 0);
        LevelUp l = new LevelUp("1", "ID", 2, "Gw", 0, 10);

        checkService.checkLevelUp(l);
    }

    @Test(expected = MidgardException.class)
    public void checkLevelUpFailAp() throws MidgardException {
        addCharacterWithAttributes();
        addReward(1000, 0);
        LevelUp l = new LevelUp("1", "ID", 2, "Gw", 1, 0);

        checkService.checkLevelUp(l);
    }

    @Test(expected = MidgardException.class)
    public void checkLevelUpFailLevel() throws MidgardException {
        addCharacterWithAttributes();
        addReward(1000, 0);
        addLevelUp(2, "", 0, 10);

        LevelUp l = new LevelUp("1", "ID", 2, "", 0, 10);

        checkService.checkLevelUp(l);
    }

    @Test(expected = MidgardException.class)
    public void checkLevelUpFailEs() throws MidgardException {
        addCharacterWithAttributes();
        addReward(10, 0);
        LevelUp l = new LevelUp("2", "ID", 2, "", 0, 10);

        checkService.checkLevelUp(l);
    }

    @Test(expected = MidgardException.class)
    public void checkLevelUpFailEsHigh() throws MidgardException {
        addCharacterWithAttributes();
        addReward(10, 0);
        LevelUp l = new LevelUp("2", "ID", 20, "", 0, 10);

        checkService.checkLevelUp(l);
    }

    @Test
    public void checkLearningInitialSkill() throws MidgardException {
        addCharacterWithAttributes();
        initial.setEp(100);
        Learning l = new Learning("1", "ID", "Akrobatik", true, true, 8, 0, 0, 0, 0);
        Skill s = new Skill("Akrobatik", "ID", 0, 0, 0, 1, 20, 0, false);

        enrichService.enrichLearning(l, s);
        checkService.checkLearning(l, s, initial);
    }

    @Test
    public void checkLearningNewSkill() throws MidgardException {
        addCharacterWithAttributes();
        initial.setEp(100);
        Learning l = new Learning("1", "ID", "Akrobatik", false, true, 8, 0, 0, 0, 0);
        Skill s = new Skill("Akrobatik", "ID", 0, 0, 0, 1, 20, 0, false);

        enrichService.enrichLearning(l, s);
        checkService.checkLearning(l, s, initial);
    }

    @Test(expected = MidgardException.class)
    public void checkLearningWithTooLittleEP() throws MidgardException {
        addCharacterWithAttributes();
        initial.setEp(20);
        Learning l = new Learning("1", "ID", "Akrobatik", false, true, 8, 0, 0, 0, 0);

        Skill s = new Skill("Akrobatik", "ID", 0, 0, 0, 10, 200, 0, false);

        enrichService.enrichLearning(l, s);
        checkService.checkLearning(l, s, initial);
    }

    @Test(expected = MidgardException.class)
    public void checkLearningWithTooLittleGold() throws MidgardException {
        addCharacterWithAttributes();
        initial.setEp(100);
        initial.setGold(0);
        Learning l = new Learning("1", "ID", "Akrobatik", false, true, 8, 50, 0, 0, 0);

        Skill s = new Skill("Akrobatik", "ID", 0, 0, 0, 10, 100, 0, false);

        enrichService.enrichLearning(l, s);
        checkService.checkLearning(l, s, initial);
    }

    @Test
    public void checkLearningIncrSkill() throws MidgardException {
        addCharacterWithAttributes();
        addLearning("Akrobatik", true, true, 8, 0, 0, 0);
        initial.setEp(100);
        Learning l = new Learning("1", "ID", "Akrobatik", false, true, 9, 0, 0, 0, 0);
        Skill s = new Skill("Akrobatik", "ID", 0, 0, 0, 1, 10, 0, false);

        enrichService.enrichLearning(l, s);
        checkService.checkLearning(l, s, initial);
    }

    @Test(expected = MidgardException.class)
    public void checkLearningFailPercentageHigh() throws MidgardException {
        addCharacterWithAttributes();
        addLearning("Akrobatik", true, true, 8, 0, 0, 0);
        initial.setEp(100);
        Learning l = new Learning("1", "ID", "Akrobatik", false, true, 9, 51, 0, 0, 0);
        Skill s = new Skill("Akrobatik", "ID", 0, 0, 0, 1, 10, 0, false);

        enrichService.enrichLearning(l, s);
        checkService.checkLearning(l, s, initial);
    }

    @Test(expected = MidgardException.class)
    public void checkLearningFailPercentageLow() throws MidgardException {
        addCharacterWithAttributes();
        addLearning("Akrobatik", true, true, 8, 0, 0, 0);
        initial.setEp(100);
        Learning l = new Learning("1", "ID", "Akrobatik", false, true, 9, -1, 0, 0, 0);
        Skill s = new Skill("Akrobatik", "ID", 0, 0, 0, 1, 10, 0, false);

        enrichService.enrichLearning(l, s);
        checkService.checkLearning(l, s, initial);
    }

    @Test(expected = MidgardException.class)
    public void checkLearningFailNotNextBonus() throws MidgardException {
        addCharacterWithAttributes();
        addLearning("Akrobatik", true, true, 8, 0, 0, 0);
        initial.setEp(100);
        Learning l = new Learning("1", "ID", "Akrobatik", false, false, 10, 0, 0, 0, 0);
        Skill s = new Skill("Akrobatik", "ID", 8, 0, 0, 1, 10, 0, true);

        enrichService.enrichLearning(l, s);
        l.setNewBonus(10);
        checkService.checkLearning(l, s, initial);
    }

    @Test
    public void checkLearningOnCreate() throws MidgardException {
        Learning l = new Learning("1", "ID", "Akrobatik", true, true, 8, 0, 0, 0, 0);
        checkService.checkLearningOnCreate(l);
    }

    @Test(expected = MidgardException.class)
    public void checkLearningOnCreateFailEp() throws MidgardException {
        Learning l = new Learning("1", "ID", "Akrobatik", true, true, 8, 0, 2, 0, 0);
        checkService.checkLearningOnCreate(l);
    }

    @Test(expected = MidgardException.class)
    public void checkLearningOnCreateFailGold() throws MidgardException {
        Learning l = new Learning("1", "ID", "Akrobatik", true, true, 8, 0, 0, 2, 0);
        checkService.checkLearningOnCreate(l);
    }

    @Test(expected = MidgardException.class)
    public void checkLearningOnCreateFailPP() throws MidgardException {
        Learning l = new Learning("1", "ID", "Akrobatik", true, true, 8, 0, 0, 0, 2);
        checkService.checkLearningOnCreate(l);
    }

    @Test(expected = MidgardException.class)
    public void checkLearningOnCreateFailLearned() throws MidgardException {
        Learning l = new Learning("1", "ID", "Akrobatik", true, false, 8, 0, 0, 0, 0);
        checkService.checkLearningOnCreate(l);
    }

    @Test(expected = MidgardException.class)
    public void checkLearningOnCreateFailStarting() throws MidgardException {
        Learning l = new Learning("1", "ID", "Akrobatik", false, true, 8, 0, 0, 0, 0);
        checkService.checkLearningOnCreate(l);
    }
}
