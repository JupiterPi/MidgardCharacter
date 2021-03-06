package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Skill;
import lombok.var;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnrichTest extends TestBase {

    @Before
    public void hideSkills() {
        enrichService.hideInitialSkills = true;
    }

    @Test(expected = MidgardException.class)
    public void getCharacterFail() throws MidgardException {
        var c = enrichService.getCharacter("XYZ");
    }

    @Test
    public void InitialCharacter() throws MidgardException {
        addCharacter();

        var c = enrichService.getCharacter("ID");

        assertEquals(c, initial);
    }

    @Test
    public void InitialWithAttributes() throws MidgardException {
        addCharacterWithAttributes();

        var c = enrichService.getCharacter("ID");

        assertEquals(c, initial);
    }

    @Test
    public void withReward() throws MidgardException {
        addCharacterWithAttributes();
        addReward(100, 500);

        var c = enrichService.getCharacter("ID");

        assertEquals(c, initial);
    }

    @Test
    public void withMultipleReward() throws MidgardException {
        addCharacterWithAttributes();

        var c = enrichService.getCharacter("ID");

        assertEquals(c, initial);
    }

    @Test
    public void withLevelUpIncreasingAttribute() throws MidgardException {
        addCharacterWithAttributes();
        addLevelUp(2, "In", 2, 10);

        var c = enrichService.getCharacter("ID");

        assertEquals(initial, c);
    }

    @Test
    public void withLevelUpWithoutIncreasingAttribute() throws MidgardException {
        addCharacterWithAttributes();
        addLevelUp(2, "", 0, 10);

        var c = enrichService.getCharacter("ID");

        assertEquals(initial, c);
    }

    @Test
    public void withMultipleLevelUp() throws MidgardException {
        addCharacterWithAttributes();
        addLevelUp(2, "", 0, 10);
        addLevelUp(3, "In", 0, 15);

        initial.setAp(15);
        var c = enrichService.getCharacter("ID");

        assertEquals(initial, c);
    }

    @Test
    public void withLowerApInSecondLevel() throws MidgardException {
        addCharacterWithAttributes();
        addLevelUp(2, "", 0, 10);
        addLevelUp(3, "In", 0, 5);

        initial.setAp(10);
        var c = enrichService.getCharacter("ID");

        assertEquals(initial, c);
    }

    void putAkrobatikSkill(int bonus, int pp) {
        initial.getSkills().put("Akrobatik", new Skill("Akrobatik", "ID", bonus, 2, bonus + 2, 2, 20, pp, true));
    }

    void addAkrobatikLearning(int bonus) {
        if (bonus == 8) {
            addLearning("Akrobatik", true, true, bonus, 0, 0, 0);
        } else {
            addLearning("Akrobatik", false, false, bonus, 0, 0, 0);
        }
    }

    @Test
    public void withRewardAndLearning() throws MidgardException {
        addCharacterWithAttributes();
        addReward(100, 500);
        addAkrobatikLearning(8);
        putAkrobatikSkill(8, 0);

        var c = enrichService.getCharacter("ID");

        assertEquals(initial, c);
    }

    @Test
    public void withMultipleLearning() throws MidgardException {
        addCharacterWithAttributes();
        addReward(500, 1000);
        addAkrobatikLearning(8);
        addAkrobatikLearning(9);
        putAkrobatikSkill(9, 0);

        var c = enrichService.getCharacter("ID");

        assertEquals(initial, c);
    }

    @Test
    public void withRewardAndLearningAndPP() throws MidgardException {
        addCharacterWithAttributes();
        addReward(10, 400);
        addAkrobatikLearning(8);
        addRewardPP("Akrobatik", 2);
        putAkrobatikSkill(8, 2);

        var c = enrichService.getCharacter("ID");

        assertEquals(initial, c);
    }

    @Test
    public void withMultiplePPRewards() throws MidgardException {
        addCharacterWithAttributes();
        addReward(10, 400);
        addAkrobatikLearning(8);
        addRewardPP("Akrobatik", 2);
        addRewardPP("Akrobatik", 1);
        putAkrobatikSkill(8, 3);

        var c = enrichService.getCharacter("ID");

        assertEquals(initial, c);
    }

    @Test
    public void withMultipleLearningAndPPRewards() throws MidgardException {
        addCharacterWithAttributes();
        addReward(10, 400);
        addAkrobatikLearning(8);
        addRewardPP("Akrobatik", 2);
        addRewardPP("Akrobatik", 1);
        addLearning("Akrobatik", true, false, 9, 0, 0, 2);
        putAkrobatikSkill(9, 1);

        var c = enrichService.getCharacter("ID");

        assertEquals(initial,c);
    }
}
