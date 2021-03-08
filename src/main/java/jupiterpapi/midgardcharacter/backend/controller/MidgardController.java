package jupiterpapi.midgardcharacter.backend.controller;

import jupiterpapi.midgardcharacter.backend.model.create.*;
import jupiterpapi.midgardcharacter.backend.model.dto.CharacterDTO;
import jupiterpapi.midgardcharacter.backend.model.dto.CharacterMetaDTO;
import jupiterpapi.midgardcharacter.backend.service.MidgardException;
import jupiterpapi.midgardcharacter.backend.service.MidgardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping(path = MidgardController.PATH)
@RestController
public class MidgardController implements MidgardService {
    public static final String PATH = "/api/character";

    @Autowired
    MidgardService service;

    @GetMapping("/user/{userId}")
    public Collection<CharacterMetaDTO> getCharacters(@PathVariable("userId") String userId) {
        return service.getCharacters(userId);
    }

    @GetMapping("/{characterId}")
    public CharacterDTO getCharacter(@PathVariable("characterId") String characterId) throws MidgardException {
        return service.getCharacter(characterId);
    }

    @Deprecated
    @PostMapping("")
    public CharacterDTO postCharacter(@RequestBody CharacterCreateDTO character) throws MidgardException {
        return service.postCharacter(character);
    }

    @PostMapping("/reward")
    public CharacterDTO postReward(@RequestBody RewardCreateDTO reward) throws MidgardException {
        return service.postReward(reward);
    }

    @PostMapping("/ppReward")
    public CharacterDTO postRewardPP(@RequestBody PPRewardCreateDTO rewardPP) throws MidgardException {
        return service.postRewardPP(rewardPP);
    }

    @PostMapping("/learning")
    public CharacterDTO postLearning(@RequestBody LearningCreateDTO learn) throws MidgardException {
        return service.postLearning(learn);
    }

    @PostMapping("/levelUp")
    public CharacterDTO postLevelUp(@RequestBody LevelUpCreateDTO levelUp) throws MidgardException {
        return service.postLevelUp(levelUp);
    }

    @ExceptionHandler({MidgardException.class})
    public ResponseEntity<String> handleUserException(MidgardException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
