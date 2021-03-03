package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.create.AttributeCreate;
import jupiterpapi.midgardcharacter.backend.model.create.CharacterCreate;
import jupiterpapi.midgardcharacter.backend.model.create.LearningCreate;
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

    @Test
    public void newCharacterNoSkills() throws UserException {
        var character = postCharacter("Name", "As", "");
        assertEquals("Name", character.getName());
        assertEquals(9, character.getAttributes().size());
    }

    @Test
    public void newCharacterWithInitialSkills() throws UserException {
        final String NAME = "Name";
        var character = postCharacter(NAME, "As", "Akrobatik.Alchemie");

        assertEquals(2, character.getLearnings().size());
        assertEquals(2, character.getSkills().size());

        // TODO bonus must be calculated correctly
        assertEquals(0, getSkill(character, "Akrobatik").getBonus());
    }

    @Test
    public void RewardAndLevelUp() {

    }

}
