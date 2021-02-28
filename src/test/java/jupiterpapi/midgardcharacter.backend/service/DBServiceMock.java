package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DBServiceMock implements DBService {
    final List<Character> characters = new ArrayList<>();
    final List<Attribute> attributes = new ArrayList<>();
    final List<LevelUp>   levelUps = new ArrayList<>();
    final List<Reward>    rewards = new ArrayList<>();
    final List<RewardPP>  rewardsPP = new ArrayList<>();
    final List<Learn>     learnings = new ArrayList<>();

    public void reset() {
        characters.clear();
        attributes.clear();
        levelUps.clear();
        rewards.clear();
        rewardsPP.clear();
        learnings.clear();
    }
    public List<User> getUser() {
        return null;
    }
    public List<Character> getCharacters(String userId) {
        return null;
    }
    public Character getCharacter(String characterId) {
        for (Character entry : characters) {
            if (entry.getId().equals(characterId))
                return entry;
        }
        return null;
    }
    public List<Attribute> getAttributes(String characterId) {
        List<Attribute> list = new ArrayList<>();
        return attributes.stream().filter( e -> e.getCharacterId().equals(characterId)).collect(Collectors.toList());
    }
    public List<LevelUp> getLevelUps(String characterId) {
        List<LevelUp> list = new ArrayList<>();
        return levelUps.stream().filter( e -> e.getCharacterId().equals(characterId)).collect(Collectors.toList());
    }
    public List<Reward> getRewards(String characterId) {
        List<Reward> list = new ArrayList<>();
        return rewards.stream().filter( e -> e.getCharacterId().equals(characterId)).collect(Collectors.toList());
    }
    public List<RewardPP> getRewardPPs(String characterId) {
        List<Reward> list = new ArrayList<>();
        return rewardsPP.stream().filter( e -> e.getCharacterId().equals(characterId)).collect(Collectors.toList());
    }
    public List<Learn> getLearnings(String characterId) {
        List<Learn> list = new ArrayList<>();
        return learnings.stream().filter( e -> e.getCharacterId().equals(characterId)).collect(Collectors.toList());
    }
    public User postUser(User user) {
        return null;
    }
    public void postCharacter(Character character) {

    }
    public void postAttributes(List<Attribute> attributes) {

    }
    public void postLevelUp(LevelUp levelUp) {

    }
    public void postReward(Reward reward) {

    }
    public void postRewardPP(RewardPP rewardPP) {

    }
    public void postLearn(Learn learn) {

    }
}
