package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;

import java.util.List;

public interface DBService {

    List<User> getUser();

    List<Character> getCharacters(String userId);

    Character getCharacter(String characterId);

    List<Attribute> getAttributes(String characterId);

    List<LevelUp> getLevelUps(String characterId);

    List<Reward> getRewards(String characterId);

    List<PPReward> getRewardPPs(String characterId);

    List<Learning> getLearnings(String characterId);

    User postUser(User user);

    void postCharacter(Character character);

    void postAttributes(List<Attribute> attributes);

    void postLevelUp(LevelUp levelUp);

    void postReward(Reward reward);

    void postRewardPP(PPReward PPReward);

    void postLearning(Learning learning);

    void reset();
}
