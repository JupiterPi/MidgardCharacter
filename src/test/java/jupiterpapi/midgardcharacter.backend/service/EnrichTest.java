package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Skill;
import lombok.var;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnrichTest extends TestBase {


    @Test(expected = UserException.class)
    public void getCharacterFail() throws UserException {
        var c = service.getCharacter("XYZ");
    }

    @Test
    public void InitialCharacter() throws UserException {
        addCharacter();

        var c = service.getCharacter("ID");

        assertEquals(c,initial);
    }

    @Test
    public void InitialWithAttributes() throws UserException {
        addCharacterWithAttributes();

        var c = service.getCharacter("ID");

        assertEquals(c,initial);
    }

    @Test
    public void withReward() throws UserException {
        addCharacterWithAttributes();
        addReward( 100,500 );
        initial.setEp(100);
        initial.setEs(100);
        initial.setGold(500);

        var c = service.getCharacter("ID");

        assertEquals(c,initial);
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

        var c = service.getCharacter("ID");

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

        var c = service.getCharacter("ID");

        assertEquals(initial,c);
    }

    @Test
    public void withLevelUp() throws UserException {
        addCharacterWithAttributes();
        addLevelUp(2,"In",2,10);

        var c = service.getCharacter("ID");

        assertEquals(initial,c);
    }

    @Test
    public void withLevelUpButWithoutAttribute() throws UserException {
        addCharacterWithAttributes();
        addLevelUp(2,"",0,10);

        var c = service.getCharacter("ID");

        assertEquals(initial,c);
    }

}
