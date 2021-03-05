package jupiterpapi.midgardcharacter.backend.controller;

import jupiterpapi.midgardcharacter.backend.model.create.*;
import jupiterpapi.midgardcharacter.backend.model.dto.CharacterDTO;
import jupiterpapi.midgardcharacter.backend.model.dto.CharacterMetaDTO;
import jupiterpapi.midgardcharacter.backend.model.dto.UserDTO;
import jupiterpapi.midgardcharacter.backend.service.MessageProvider;
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

    @Deprecated
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

    @Deprecated
    @PostMapping("/user")
    public UserDTO postUser(@RequestBody UserCreateDTO user) {
        return service.postUser(user);
    }

    @Deprecated
    @PostMapping("/character")
    public CharacterDTO postCharacter(@RequestBody CharacterCreateDTO character) throws UserException {
        return service.postCharacter(character);
    }

    @PostMapping("/reward")
    public CharacterDTO postReward(@RequestBody RewardCreateDTO reward) throws UserException {
        return service.postReward(reward);
    }

    @PostMapping("/ppReward")
    public CharacterDTO postRewardPP(@RequestBody PPRewardCreateDTO rewardPP) throws UserException {
        return service.postRewardPP(rewardPP);
    }

    @PostMapping("/learning")
    public CharacterDTO postLearning(@RequestBody LearningCreateDTO learn) throws UserException {
        return service.postLearning(learn);
    }

    @PostMapping("/levelUp")
    public CharacterDTO postLevelUp(@RequestBody LevelUpCreateDTO levelUp) throws UserException {
        return service.postLevelUp(levelUp);
    }

    @ExceptionHandler({UserException.class})
    public ResponseEntity<String> handleUserException(UserException exception) {
        String key = exception.getMessage();
        if (key == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }

        String message = MessageProvider.get(key);
        if (message.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }
}
