package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;
import org.junit.Before;
import org.junit.Test;

public class CheckTest extends TestBase {

    @Before
    public void hideInitialSkills() {
        enrichService.hideInitialSkills = true;
    }

    @Test
    public void checkNewCharacterSuccess() throws UserException {
        addCharacterWithAttributes();
        Character c = new Character("ID2", "Name", "User", "As", 0);
        checkService.checkNewCharacter(c, initial.getAttributes().values());
    }

    @Test(expected = UserException.class)
    public void checkNewCharacterFailExists() throws UserException {
        addCharacterWithAttributes();
        checkService.checkNewCharacter(initial,initial.getAttributes().values());
    }

    @Test(expected = UserException.class)
    public void checkNewCharacterFailAttributesHigh() throws UserException {
        addCharacter();
        initial.setId("XYZ");
        initial.getAttributes().put("Zt", new Attribute("ID/Zt", "Zt", "ID", 200, 0));
        checkService.checkNewCharacter(initial, initial.getAttributes().values());
    }

    @Test(expected = UserException.class)
    public void checkNewCharacterFailAttributesLow() throws UserException {
        addCharacter();
        initial.setId("XYZ");
        initial.getAttributes().put("Zt", new Attribute("ID/Zt", "Zt", "ID", 0, 0));
        checkService.checkNewCharacter(initial, initial.getAttributes().values());
    }


    @Test
    public void checkRewardSuccess() throws UserException {
        addCharacterWithAttributes();
        Reward r = new Reward("1","ID",10,10);
        checkService.checkReward(r);
    }

    @Test(expected = UserException.class)
    public void checkRewardFailEP() throws UserException {
        addCharacterWithAttributes();
        Reward r = new Reward("1","ID",-1,10);
        checkService.checkReward(r);
    }

    @Test(expected = UserException.class)
    public void checkRewardFailGold() throws UserException {
        addCharacterWithAttributes();
        Reward r = new Reward("1","ID",0,-1);
        checkService.checkReward(r);
    }


    @Test
    public void checkRewardPPSuccess() throws UserException {
        addCharacterWithAttributes();
        addLearning( "Akrobatik",true,true, 8,0,0,0);
        addRewardPP( "Akrobatik",2 );

        checkService.checkRewardPP(initial.getRewardsPP().get(0));
    }

    @Test(expected = UserException.class)
    public void checkRewardPPFailPP() throws UserException {
        addCharacterWithAttributes();
        addLearning( "Akrobatik",true,true, 8,0,0,0);
        addRewardPP( "Akrobatik",-1 );
        checkService.checkRewardPP(initial.getRewardsPP().get(0));
    }

    @Test(expected = UserException.class)
    public void checkRewardPPFailGold() throws UserException {
        addCharacterWithAttributes();
        addLearning( "Akrobatik",true,true, 8,0,0,0);
        addRewardPP( "XYZ",2 );
        checkService.checkRewardPP(initial.getRewardsPP().get(0));
    }

    @Test
    public void checkLevelUpSuccess() throws UserException {
        addCharacterWithAttributes();
        addReward(1000, 0);
        LevelUp l = new LevelUp("1","ID",2,"",0,10);

        checkService.checkLevelUp(l);
    }

    @Test(expected = UserException.class)
    public void checkLevelUpFailAttribute() throws UserException {
        addCharacterWithAttributes();
        addReward(1000, 0);
        LevelUp l = new LevelUp("1","ID",2,"ZZ",1,10);

        checkService.checkLevelUp(l);
    }

    @Test(expected = UserException.class)
    public void checkLevelUpFailIncrease() throws UserException {
        addCharacterWithAttributes();
        addReward(1000, 0);
        LevelUp l = new LevelUp("1","ID",2,"Gw",0,10);

        checkService.checkLevelUp(l);
    }

    @Test(expected = UserException.class)
    public void checkLevelUpFailAp() throws UserException {
        addCharacterWithAttributes();
        addReward(1000, 0);
        LevelUp l = new LevelUp("1","ID",2,"Gw",1,0);

        checkService.checkLevelUp(l);
    }

    @Test(expected = UserException.class)
    public void checkLevelUpFailLevel() throws UserException {
        addCharacterWithAttributes();
        addReward(1000, 0);
        addLevelUp(2, "", 0, 10);

        LevelUp l = new LevelUp("1", "ID", 2, "", 0, 10);

        checkService.checkLevelUp(l);
    }

    @Test(expected = UserException.class)
    public void checkLevelUpFailEs() throws UserException {
        addCharacterWithAttributes();
        addReward(10, 0);
        LevelUp l = new LevelUp("2", "ID", 2, "", 0, 10);

        checkService.checkLevelUp(l);
    }

    @Test
    public void checkLearningInitialSkill() throws UserException {
        addCharacterWithAttributes();
        initial.setEp(100);
        Learning l = new Learning("1", "ID", "Akrobatik", true, true, 8, 0, 0, 0, 0);
        Skill s = new Skill("Akrobatik", "ID", 0, 0, 0, 1, 20, 0, false);

        enrichService.enrichLearning(l, s);
        checkService.checkLearning(l, s, initial);
    }

    @Test
    public void checkLearningNewSkill() throws UserException {
        addCharacterWithAttributes();
        initial.setEp(100);
        Learning l = new Learning("1", "ID", "Akrobatik", false, true, 8, 0, 0, 0, 0);
        Skill s = new Skill("Akrobatik", "ID", 0, 0, 0, 1, 20, 0, false);

        enrichService.enrichLearning(l, s);
        checkService.checkLearning(l, s, initial);
    }


    @Test(expected = UserException.class)
    public void checkLearningWithTooLittleEP() throws UserException {
        addCharacterWithAttributes();
        initial.setEp(20);
        Learning l = new Learning("1", "ID", "Akrobatik", false, true, 8, 0, 0, 0, 0);

        Skill s = new Skill("Akrobatik", "ID", 0, 0, 0, 10, 200, 0, false);

        enrichService.enrichLearning(l, s);
        checkService.checkLearning(l, s, initial);
    }
    @Test(expected = UserException.class)
    public void checkLearningWithTooLittleGold() throws UserException {
        addCharacterWithAttributes();
        initial.setEp(100);
        initial.setGold(0);
        Learning l = new Learning("1", "ID", "Akrobatik", false, true, 8, 50, 0, 0, 0);

        Skill s = new Skill("Akrobatik", "ID", 0, 0, 0, 10, 100, 0, false);

        enrichService.enrichLearning(l, s);
        checkService.checkLearning(l, s, initial);
    }


    @Test
    public void checkLearningIncrSkill() throws UserException {
        addCharacterWithAttributes();
        addLearning("Akrobatik", true, true, 8, 0, 0, 0);
        initial.setEp(100);
        Learning l = new Learning("1", "ID", "Akrobatik", false, true, 9, 0, 0, 0, 0);
        Skill s = new Skill("Akrobatik", "ID", 0, 0, 0, 1, 10, 0, false);

        enrichService.enrichLearning(l, s);
        checkService.checkLearning(l, s, initial);
    }

    @Test
    public void checkLearningOnCreate() throws UserException {
        Learning l = new Learning("1", "ID", "Akrobatik", true, true, 8, 0, 0, 0, 0);
        checkService.checkLearningOnCreate(l);
    }

    @Test(expected = UserException.class)
    public void checkLearningOnCreateFailEp() throws UserException {
        Learning l = new Learning("1", "ID", "Akrobatik", true, true, 8, 0, 2, 0, 0);
        checkService.checkLearningOnCreate(l);
    }

    @Test(expected = UserException.class)
    public void checkLearningOnCreateFailGold() throws UserException {
        Learning l = new Learning("1", "ID", "Akrobatik", true, true, 8, 0, 0, 2, 0);
        checkService.checkLearningOnCreate(l);
    }

    @Test(expected = UserException.class)
    public void checkLearningOnCreateFailPP() throws UserException {
        Learning l = new Learning("1", "ID", "Akrobatik", true, true, 8, 0, 0, 0, 2);
        checkService.checkLearningOnCreate(l);
    }

    @Test(expected = UserException.class)
    public void checkLearningOnCreateFailLearned() throws UserException {
        Learning l = new Learning("1", "ID", "Akrobatik", true, false, 8, 0, 0, 0, 0);
        checkService.checkLearningOnCreate(l);
    }

    @Test(expected = UserException.class)
    public void checkLearningOnCreateFailStarting() throws UserException {
        Learning l = new Learning("1", "ID", "Akrobatik", false, true, 8, 0, 0, 0, 0);
        checkService.checkLearningOnCreate(l);
    }
}
