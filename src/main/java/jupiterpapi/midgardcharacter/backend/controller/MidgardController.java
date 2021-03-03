package jupiterpapi.midgardcharacter.backend.controller;

import jupiterpapi.midgardcharacter.backend.model.create.*;
import jupiterpapi.midgardcharacter.backend.model.dto.CharacterDTO;
import jupiterpapi.midgardcharacter.backend.model.dto.CharacterMetaDTO;
import jupiterpapi.midgardcharacter.backend.model.dto.UserDTO;
import jupiterpapi.midgardcharacter.backend.service.MidgardService;
import jupiterpapi.midgardcharacter.backend.service.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping(path = MidgardController.PATH)
@RestController
public class MidgardController implements MidgardService {
    public static final String PATH = "/api";

    @Autowired
    MidgardService service;

    @GetMapping("/users")
    public Collection<UserDTO> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/characters/{userId}")
    public Collection<CharacterMetaDTO> getCharacters(@PathVariable("userId") String userId) {
        return service.getCharacters(userId);
    }

    @GetMapping("/character/{characterId}")
    public CharacterDTO getCharacter(@PathVariable("characterId") String characterId) throws UserException {
        return service.getCharacter(characterId);
    }

    @PostMapping("/user")
    public UserDTO postUser(@RequestBody UserCreate user) {
        return service.postUser(user);
    }

    @PostMapping("/character")
    public CharacterDTO postCharacter(@RequestBody CharacterCreate character) throws UserException {
        return service.postCharacter(character);
    }

    @PostMapping("/reward")
    public CharacterDTO postReward(@RequestBody RewardCreate reward) throws UserException {
        return service.postReward(reward);
    }

    @PostMapping("/PPReward")
    public CharacterDTO postRewardPP(@RequestBody PPRewardCreate rewardPP) throws UserException {
        return service.postRewardPP(rewardPP);
    }

    @PostMapping("/learn")
    public CharacterDTO postLearning(@RequestBody LearningCreate learn) throws UserException {
        return service.postLearning(learn);
    }

    @PostMapping("/levelUp")
    public CharacterDTO postLevelUp(@RequestBody LevelUpCreate levelUp) throws UserException {
        return service.postLevelUp(levelUp);
    }

    @ExceptionHandler({ UserException.class })
    public ResponseEntity<String> handleUserException(UserException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
