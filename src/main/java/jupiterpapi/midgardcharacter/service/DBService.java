package jupiterpapi.midgardcharacter.service;

import jupiterpapi.midgardcharacter.model.Character;
import jupiterpapi.midgardcharacter.model.*;

import java.util.List;

public interface DBService {

    List<User> getUser();

    List<Character> getCharacters(String userId);

    Character getCharacter(String characterId);

    List<Attribute> getAttributes(String characterId);

    List<LevelUp> getLevelUps(String characterId);

    List<Reward> getRewards(String characterId);

    List<RewardPP> getRewardPPs(String characterId);

    List<Learn> getLearnings(String characterId);

    User postUser(User user);

    void postCharacter(Character character);

    void postAttributes(List<Attribute> attributes);

    void postLevelUp(LevelUp levelUp);

    void postReward(Reward reward);

    void postRewardPP(RewardPP rewardPP);

    void postLearn(Learn learn);

    void reset();
}
