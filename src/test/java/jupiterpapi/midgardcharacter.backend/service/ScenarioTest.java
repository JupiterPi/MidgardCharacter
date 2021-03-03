package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.create.CharacterCreate;
import jupiterpapi.midgardcharacter.backend.model.create.LearningCreate;
import jupiterpapi.midgardcharacter.backend.model.dto.CharacterDTO;
import lombok.var;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SameParameterValue")
public class ScenarioTest extends TestFactory {

    CharacterDTO postCharacter(String name, String className, Collection<LearningCreate> initialLearnings)
            throws UserException {
        var create = new CharacterCreate(name, name, "User", className);

        return midgardService.postCharacter(create);
    }

    @Test
    public void newCharacterNoSkills() throws UserException {
        var character = postCharacter("Name", "As", null);
        assertEquals(character.getName(), "Name");
    }

}
