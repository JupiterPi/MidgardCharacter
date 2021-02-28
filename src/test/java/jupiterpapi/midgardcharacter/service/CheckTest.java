package jupiterpapi.midgardcharacter.service;

import jupiterpapi.midgardcharacter.configuration.InternalException;
import jupiterpa.model.*;
import jupiterpapi.midgardcharacter.model.Character;
import jupiterpapi.midgardcharacter.model.*;
import org.junit.Before;
import org.junit.Test;

public class CheckTest extends TestBase {

    CheckService check;

    @Before
    public void setup() throws InternalException {
        super.setup();

        check = new CheckService();
        check.db = service.db;
        check.enrich = service;
        check.skillService = service.skillService;
    }

    @Test
    public void checkNewCharacterSuccess() throws UserException {
        addCharacterWithAttributes();
        Character c = new Character("ID2", "Name", "User", "As");
        check.checkNewCharacter(c,initial.getAttributes().values());
    }

    @Test(expected = UserException.class)
    public void checkNewCharacterFailExists() throws UserException {
        addCharacterWithAttributes();
        check.checkNewCharacter(initial,initial.getAttributes().values());
    }

    @Test(expected = UserException.class)
    public void checkNewCharacterFailAttributesHigh() throws UserException {
        addCharacter();
        initial.setId("XYZ");
        initial.getAttributes().put("Zt", new Attribute("Zt","ID",200));
        check.checkNewCharacter(initial,initial.getAttributes().values());
    }

    @Test(expected = UserException.class)
    public void checkNewCharacterFailAttributesLow() throws UserException {
        addCharacter();
        initial.setId("XYZ");
        initial.getAttributes().put("Zt", new Attribute("Zt","ID",0));
        check.checkNewCharacter(initial,initial.getAttributes().values());
    }


    @Test
    public void checkRewardSuccess() throws UserException {
        addCharacterWithAttributes();
        Reward r = new Reward("1","ID",10,10);
        check.checkReward(r);
    }

    @Test(expected = UserException.class)
    public void checkRewardFailEP() throws UserException {
        addCharacterWithAttributes();
        Reward r = new Reward("1","ID",-1,10);
        check.checkReward(r);
    }

    @Test(expected = UserException.class)
    public void checkRewardFailGold() throws UserException {
        addCharacterWithAttributes();
        Reward r = new Reward("1","ID",0,-1);
        check.checkReward(r);
    }


    @Test
    public void checkRewardPPSuccess() throws UserException {
        addCharacterWithAttributes();
        addLearning( "Akrobatik",true,true, 8,0,0,0);
        addRewardPP( "Akrobatik",2 );

        check.checkRewardPP(initial.getRewardsPP().get(0));
    }

    @Test(expected = UserException.class)
    public void checkRewardPPFailPP() throws UserException {
        addCharacterWithAttributes();
        addLearning( "Akrobatik",true,true, 8,0,0,0);
        addRewardPP( "Akrobatik",-1 );
        check.checkRewardPP(initial.getRewardsPP().get(0));
    }

    @Test(expected = UserException.class)
    public void checkRewardPPFailGold() throws UserException {
        addCharacterWithAttributes();
        addLearning( "Akrobatik",true,true, 8,0,0,0);
        addRewardPP( "XYZ",2 );
        check.checkRewardPP(initial.getRewardsPP().get(0));
    }

    @Test
    public void checkLevelUpSuccess() throws UserException {
        addCharacterWithAttributes();
        LevelUp l = new LevelUp("1","ID",2,"",0,10);

        check.checkLevelUp(l);
    }

    @Test(expected = UserException.class)
    public void checkLevelUpFailAttribute() throws UserException {
        addCharacterWithAttributes();
        LevelUp l = new LevelUp("1","ID",2,"ZZ",1,10);

        check.checkLevelUp(l);
    }

    @Test(expected = UserException.class)
    public void checkLevelUpFailIncrease() throws UserException {
        addCharacterWithAttributes();
        LevelUp l = new LevelUp("1","ID",2,"Gw",0,10);

        check.checkLevelUp(l);
    }

    @Test(expected = UserException.class)
    public void checkLevelUpFailAp() throws UserException {
        addCharacterWithAttributes();
        LevelUp l = new LevelUp("1","ID",2,"Gw",1,0);

        check.checkLevelUp(l);
    }

    @Test(expected = UserException.class)
    public void checkLevelUpFailLevel() throws UserException {
        addCharacterWithAttributes();
        dbService.characters.get(0).setLevel(2);

        LevelUp l = new LevelUp("1","ID",1,"",0,10);

        check.checkLevelUp(l);
    }

    @Test
    public void checkLearningNewSkill() throws UserException {
        addCharacterWithAttributes();
        addReward(100,100);
        Learn l = new Learn("1","ID",
                       "Akrobatik",false,true,
                       8,10,100,0);

        check.checkLearn(l);
    }

    @Test(expected = UserException.class)
    public void checkLearningNewSkillWithWrongEP() throws UserException {
        addCharacterWithAttributes();
        addReward(100,100);
        Learn l = new Learn("1","ID",
                "Akrobatik",false,true,
                8,20,100,0);

        check.checkLearn(l);
    }

    @Test
    public void checkLearningNewSkillWithPP() throws UserException {
        addCharacterWithAttributes();
        addReward(100,100);
        Learn l = new Learn("1","ID",
                "Akrobatik",false,true,
                8,10,80,1);

        check.checkLearn(l);
    }

    @Test(expected = UserException.class)
    public void checkLearningNewSkillWithWrongPP() throws UserException {
        addCharacterWithAttributes();
        addReward(100,100);
        Learn l = new Learn("1","ID",
                "Akrobatik",false,true,
                8,10,80,2);

        check.checkLearn(l);
    }

    @Test(expected = UserException.class)
    public void checkLearningWithTooLittleEP() throws UserException {
        addCharacterWithAttributes();
        addReward(20,100);
        Learn l = new Learn("1","ID",
                "Akrobatik",false,true,
                8,30,40,2);

        check.checkLearn(l);
    }
    @Test(expected = UserException.class)
    public void checkLearningWithTooLittleGold() throws UserException {
        addCharacterWithAttributes();
        addReward(2000,20);
        Learn l = new Learn("1","ID",
                "Akrobatik",false,true,
                8,30,40,2);

        check.checkLearn(l);
    }


    @Test
    public void checkLearningIncrSkill() throws UserException {
        addCharacterWithAttributes();
        addLearning("Akrobatik",true,true,8,0,0,0);
        addReward(100,100);
        Learn l = new Learn("1","ID",
                "Akrobatik",false,true,
                9,10,20,0);

        check.checkLearn(l);
    }

    @Test(expected = UserException.class)
    public void checkLearningIncrSkillWithWrongEP() throws UserException {
        addCharacterWithAttributes();
        addLearning("Akrobatik",true,true,8,0,0,0);
        addReward(100,100);
        Learn l = new Learn("1","ID",
                "Akrobatik",false,true,
                8,20,20,0);

        check.checkLearn(l);
    }

    @Test
    public void checkLearningIncrSkillWithPP() throws UserException {
        addCharacterWithAttributes();
        addLearning("Akrobatik",true,true,8,0,0,0);
        addReward(100,100);
        Learn l = new Learn("1","ID",
                "Akrobatik",false,true,
                8,10,0,1);

        check.checkLearn(l);
    }

    @Test(expected = UserException.class)
    public void checkLearningIncrSkillWithWrongPP() throws UserException {
        addCharacterWithAttributes();
        addLearning("Akrobatik",true,true,8,0,0,0);
        addReward(100,100);
        Learn l = new Learn("1","ID",
                "Akrobatik",false,true,
                8,10,20,1);

        check.checkLearn(l);
    }

}
