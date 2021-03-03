package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.create.*;
import jupiterpapi.midgardcharacter.backend.model.dto.AttributeDTO;
import jupiterpapi.midgardcharacter.backend.model.dto.CharacterDTO;
import jupiterpapi.midgardcharacter.backend.model.dto.SkillDTO;
import lombok.var;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("SameParameterValue")
public class ScenarioTest extends TestFactory {

    void addAttribute(CharacterCreate character, String name, int value) {
        var a = new AttributeCreate(character.getId() + name, name, character.getId(), value, 0);
        character.getAttributes().add(a);
    }

    void addLearning(CharacterCreate character, String skillName) {
        var l = new LearningCreate(character.getId() + skillName, character.getId(), skillName, true, 0, 0);
        character.getLearnings().add(l);
    }

    CharacterDTO postCharacter(String name, String className, String initialLearnings) throws UserException {
        var character = new CharacterCreate(name, name, "User", className);
        addAttribute(character, "St", 50);
        addAttribute(character, "Gs", 50);
        addAttribute(character, "Gw", 50);
        addAttribute(character, "Ko", 50);
        addAttribute(character, "In", 50);
        addAttribute(character, "Zt", 50);
        addAttribute(character, "Au", 50);
        addAttribute(character, "pA", 50);
        addAttribute(character, "Wk", 50);

        if (initialLearnings.length() > 0)
            for (String l : initialLearnings.split("\\.")) {
                addLearning(character, l);
            }

        return midgardService.postCharacter(character);
    }

    SkillDTO getSkill(CharacterDTO character, String name) {
        var opt = character.getSkills().stream().filter(s -> s.getName().equals(name)).findFirst();
        assertTrue(opt.isPresent());
        return opt.get();
    }
    AttributeDTO getAttribute(CharacterDTO character, String name) {
        var opt = character.getAttributes().stream().filter(a -> a.getName().equals(name)).findFirst();
        assertTrue(opt.isPresent());
        return opt.get();
    }

    int currentId = 0;

    CharacterDTO postReward(CharacterDTO character, int ep, int gold) throws UserException {
        currentId++;
        var r = new RewardCreate(String.valueOf(currentId), character.getId(), ep, gold);
        return midgardService.postReward(r);
    }

    CharacterDTO postLevelUp(CharacterDTO character, int level, String attr, int incr, int ap) throws UserException {
        currentId++;
        var l = new LevelUpCreate(String.valueOf(currentId), character.getId(), level, attr, incr, ap);
        return midgardService.postLevelUp(l);
    }

    CharacterDTO postLearning(CharacterDTO character, String skillName, boolean starting, int newBonus,
            int percentageGold) throws UserException {
        currentId++;
        var l = new LearningCreate(String.valueOf(currentId), character.getId(), skillName, starting, newBonus,
                percentageGold);
        return midgardService.postLearning(l);
    }

    CharacterDTO postPPReward(CharacterDTO character, String skillName, int pp) throws UserException {
        currentId++;
        var r = new PPRewardCreate(String.valueOf(currentId), character.getId(), skillName, pp);
        return midgardService.postRewardPP(r);
    }

    @Test
    public void newCharacterNoSkills() throws UserException {
        var character = postCharacter("Name", "As", "");
        assertEquals("Name", character.getName());
        assertEquals(9, character.getAttributes().size());
        assertEquals(50, getAttribute(character, "St").getValue());
    }

    @Test
    public void newCharacterWithInitialSkills() throws UserException {

        var character = postCharacter("Name", "As", "Akrobatik.Alchemie");

        assertEquals(2, character.getLearnings().size());
        assertEquals(2, character.getSkills().size());

        // TODO bonus must be calculated correctly
        assertEquals(0, getSkill(character, "Akrobatik").getBonus());
    }

    @Test
    public void RewardAndLevelUp() throws UserException {
        var c0 = postCharacter("Name", "As", "Akrobatik.Alchemie");

        var c1 = postReward(c0, 100, 200);
        assertEquals(1, c1.getRewards().size());
        assertEquals(100, c1.getEp());
        assertEquals(100, c1.getEs());
        assertEquals(200, c1.getGold());

        var c2 = postLevelUp(c1, 2, "", 0, 10);
        assertEquals(2, c2.getLevel());
        assertEquals(10, c2.getAp());

        var c3 = postReward(c2, 300, 300);
        assertEquals(2, c3.getRewards().size());
        assertEquals(400, c3.getEp());
        assertEquals(400, c3.getEs());
        assertEquals(500, c3.getGold());

        var c4 = postLevelUp(c3, 3, "St", 2, 15);
        assertEquals(3, c4.getLevel());
        assertEquals(15, c4.getAp());
        assertEquals(52, getAttribute(c4, "St").getValue());
    }

    @Test
    public void Learnings() throws UserException {
        final String C_Alchemie = "Alchemie";
        final String C_Akrobatik = "Akrobatik";

        var c0 = postCharacter("Name", "As", C_Akrobatik);
        var c1 = postReward(c0, 500, 200);

        var c2 = postLearning(c1, C_Akrobatik, false, 9, 0);
        assertEquals(2, c2.getLearnings().size());
        assertEquals(9, getSkill(c2, C_Akrobatik).getBonus());

        var c3 = postLearning(c2, C_Alchemie, false, 8, 0);
        assertEquals(3, c3.getLearnings().size());
        assertEquals(8, getSkill(c3, C_Alchemie).getBonus());

        var c4 = postPPReward(c3, C_Alchemie, 1);
        assertEquals(1, getSkill(c4, C_Alchemie).getPP());

        var c5 = postLearning(c4, C_Alchemie, false, 9, 0);
        assertEquals(4, c5.getLearnings().size());
        assertEquals(9, getSkill(c5, C_Alchemie).getBonus());
        assertEquals(0, getSkill(c5, C_Alchemie).getPP());
    }

}
