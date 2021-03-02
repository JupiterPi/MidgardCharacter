package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Skill;
import lombok.var;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnrichTest extends TestBase {

    @Test(expected = UserException.class)
    public void getCharacterFail() throws UserException {
        var c = enrichService.getCharacter("XYZ");
    }

    @Test
    public void InitialCharacter() throws UserException {
        addCharacter();

        var c = enrichService.getCharacter("ID");

        assertEquals(c,initial);
    }

    @Test
    public void InitialWithAttributes() throws UserException {
        addCharacterWithAttributes();

        var c = enrichService.getCharacter("ID");

        assertEquals(c,initial);
    }

    @Test
    public void withReward() throws UserException {
        addCharacterWithAttributes();
        addReward( 100,500 );
        initial.setEp(100);
        initial.setEs(100);
        initial.setGold(500);

        var c = enrichService.getCharacter("ID");

        assertEquals(c,initial);
    }

    @Test
    public void withMultipleReward() throws UserException {
        addCharacterWithAttributes();
        addReward( 100,500 );
        addReward( 100,500 );
        addReward( 100,500 );
        initial.setEp(300);
        initial.setEs(300);
        initial.setGold(1500);

        var c = enrichService.getCharacter("ID");

        assertEquals(c,initial);
    }

    @Test
    public void withLevelUpIncreasingAttribute() throws UserException {
        addCharacterWithAttributes();
        addLevelUp(2,"In",2,10);

        var c = enrichService.getCharacter("ID");

        assertEquals(initial,c);
    }

    @Test
    public void withLevelUpWithoutIncreasingAttribute() throws UserException {
        addCharacterWithAttributes();
        addLevelUp(2,"",0,10);

        var c = enrichService.getCharacter("ID");

        assertEquals(initial,c);
    }

    @Test
    public void withMultipleLevelUp() throws UserException {
        addCharacterWithAttributes();
        addLevelUp(2,"",0,10);
        addLevelUp(3,"In",0,15);

        initial.setAp(15);
        var c = enrichService.getCharacter("ID");

        assertEquals(initial,c);
    }

    @Test
    public void withLowerApInSecondLevel() throws UserException {
        addCharacterWithAttributes();
        addLevelUp(2,"",0,10);
        addLevelUp(3,"In",0,5);

        initial.setAp(10);
        var c = enrichService.getCharacter("ID");

        assertEquals(initial,c);
    }

    @Test
    public void withRewardAndLearning() throws UserException {
        addCharacterWithAttributes();
        addReward( 100,500 );
        addLearning( "Akrobatik",true,true, 8,0,0,0);
        initial.setEp(100);
        initial.setEs(100);
        initial.setGold(500);
        initial.getSkills().put("Akrobatik",new Skill("Akrobatik","ID",8,2,10,2,20,0));

        var c = enrichService.getCharacter("ID");

        assertEquals(initial,c);
    }

    @Test
    public void withMultipleLearning() throws UserException {
        addCharacterWithAttributes();
        addReward( 500,1000 );
        addLearning( "Akrobatik",true,true, 8,0,0,0);
        addLearning( "Akrobatik",false,false, 9,0,0,0);
        initial.setEp(500);
        initial.setEs(500);
        initial.setGold(1000);
        initial.getSkills().put("Akrobatik",new Skill("Akrobatik","ID",9,2,11,3,30,0));

        var c = enrichService.getCharacter("ID");

        assertEquals(initial,c);
    }

    @Test
    public void withRewardAndLearningAndPP() throws UserException {
        addCharacterWithAttributes();
        addReward( 10,400 );
        addLearning( "Akrobatik",true,true, 8,0,0,0);
        addRewardPP( "Akrobatik",2 );
        initial.setEp(10);
        initial.setEs(10);
        initial.setGold(400);
        initial.getSkills().put("Akrobatik",new Skill("Akrobatik","ID",8,2,10,2,20,2));

        var c = enrichService.getCharacter("ID");

        assertEquals(initial,c);
    }

    @Test
    public void withMultiplePPRewards() throws UserException {
        addCharacterWithAttributes();
        addReward( 10,400 );
        addLearning( "Akrobatik",true,true, 8,0,0,0);
        addRewardPP( "Akrobatik",2 );
        addRewardPP( "Akrobatik",1 );
        initial.setEp(10);
        initial.setEs(10);
        initial.setGold(400);
        initial.getSkills().put("Akrobatik",new Skill("Akrobatik","ID",8,2,10,2,20,3));

        var c = enrichService.getCharacter("ID");

        assertEquals(initial,c);
    }

    @Test
    public void withMultipleLearningAndPPRewards() throws UserException {
        addCharacterWithAttributes();
        addReward( 10,400 );
        addLearning( "Akrobatik",true,true, 8,0,0,0);
        addRewardPP( "Akrobatik",2 );
        addRewardPP( "Akrobatik",1 );
        addLearning( "Akrobatik",true,false, 9,0,0,2);
        initial.setEp(10);
        initial.setEs(10);
        initial.setGold(400);
        initial.getSkills().put("Akrobatik",new Skill("Akrobatik","ID",9,2,11,3,30,1));

        var c = enrichService.getCharacter("ID");

        assertEquals(initial,c);
    }
}
