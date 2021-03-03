package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;
import org.junit.Test;

public class CheckTest extends TestBase {


    @Test
    public void checkNewCharacterSuccess() throws UserException {
        addCharacterWithAttributes();
        Character c = new Character("ID2", "Name", "User", "As");
        checkService.checkNewCharacter(c,initial.getAttributes().values());
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
        initial.getAttributes().put("Zt", new Attribute("Zt","ID",200));
        checkService.checkNewCharacter(initial,initial.getAttributes().values());
    }

    @Test(expected = UserException.class)
    public void checkNewCharacterFailAttributesLow() throws UserException {
        addCharacter();
        initial.setId("XYZ");
        initial.getAttributes().put("Zt", new Attribute("Zt","ID",0));
        checkService.checkNewCharacter(initial,initial.getAttributes().values());
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
        LevelUp l = new LevelUp("1","ID",2,"",0,10);

        checkService.checkLevelUp(l);
    }

    @Test(expected = UserException.class)
    public void checkLevelUpFailAttribute() throws UserException {
        addCharacterWithAttributes();
        LevelUp l = new LevelUp("1","ID",2,"ZZ",1,10);

        checkService.checkLevelUp(l);
    }

    @Test(expected = UserException.class)
    public void checkLevelUpFailIncrease() throws UserException {
        addCharacterWithAttributes();
        LevelUp l = new LevelUp("1","ID",2,"Gw",0,10);

        checkService.checkLevelUp(l);
    }

    @Test(expected = UserException.class)
    public void checkLevelUpFailAp() throws UserException {
        addCharacterWithAttributes();
        LevelUp l = new LevelUp("1","ID",2,"Gw",1,0);

        checkService.checkLevelUp(l);
    }

    @Test(expected = UserException.class)
    public void checkLevelUpFailLevel() throws UserException {
        addCharacterWithAttributes();
        addLevelUp(2, "", 0, 10);

        LevelUp l = new LevelUp("1", "ID", 2, "", 0, 10);

        checkService.checkLevelUp(l);
    }

    @Test
    public void checkLearningInitialSkill() throws UserException {
        addCharacterWithAttributes();
        addReward(100,100);
        Learn l = new Learn("1","ID",
                "Akrobatik",true,true,8,0,0,0,0);

        checkService.checkAndEnrichLearning(l);
    }

    @Test
    public void checkLearningNewSkill() throws UserException {
        addCharacterWithAttributes();
        addReward(100,100);
        Learn l = new Learn("1","ID",
                       "Akrobatik",false,true,8,0,0,0,0);

        checkService.checkAndEnrichLearning(l);
    }


    @Test(expected = UserException.class)
    public void checkLearningWithTooLittleEP() throws UserException {
        addCharacterWithAttributes();
        addReward(20,100);
        Learn l = new Learn("1","ID",
                "Akrobatik",false,true,
                8,0,0,0,0);

        checkService.checkAndEnrichLearning(l);
    }
    @Test(expected = UserException.class)
    public void checkLearningWithTooLittleGold() throws UserException {
        addCharacterWithAttributes();
        addReward(2000,20);
        Learn l = new Learn("1","ID",
                "Akrobatik",false,true,
                8,50,0,0,0);

        checkService.checkAndEnrichLearning(l);
    }


    @Test
    public void checkLearningIncrSkill() throws UserException {
        addCharacterWithAttributes();
        addLearning("Akrobatik",true,true,8,0,0,0);
        addReward(100,100);
        Learn l = new Learn("1","ID",
                "Akrobatik",false,true,
                9,0,0,0,0);

        checkService.checkAndEnrichLearning(l);
    }

}
