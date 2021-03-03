package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;
import jupiterpapi.midgardcharacter.backend.model.repo.*;
import lombok.var;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DBServiceMock implements DBService {
    final List<UserDB> users = new ArrayList<>();
    final List<CharacterDB> characters = new ArrayList<>();
    final List<AttributeDB> attributes = new ArrayList<>();
    final List<LevelUpDB> levelUps = new ArrayList<>();
    final List<RewardDB> rewards = new ArrayList<>();
    final List<RewardPPDB> rewardsPP = new ArrayList<>();
    final List<LearnDB> learnings = new ArrayList<>();

    DBMapper mapper;

    public void reset() {
        users.clear();
        characters.clear();
        attributes.clear();
        levelUps.clear();
        rewards.clear();
        rewardsPP.clear();
        learnings.clear();
    }
    public List<User> getUser() {
        return users.stream().map(e -> mapper.map(e)).collect(Collectors.toList());
    }
    public List<Character> getCharacters(String userId) {
        return characters.stream().filter(c -> c.getUserId().equals(userId)).map(c -> mapper.map(c))
                .collect(Collectors.toList());
    }
    public Character getCharacter(String characterId) {
        for (CharacterDB entry : characters) {
            if (entry.getId().equals(characterId))
                return mapper.map(entry);
        }
        return null;
    }
    public List<Attribute> getAttributes(String characterId) {
        List<AttributeDB> list = new ArrayList<>();
        return attributes.stream().filter(e -> e.getCharacterId().equals(characterId)).map(e -> mapper.map(e))
                .collect(Collectors.toList());
    }
    public List<LevelUp> getLevelUps(String characterId) {
        List<LevelUpDB> list = new ArrayList<>();
        return levelUps.stream().filter(e -> e.getCharacterId().equals(characterId)).map(e -> mapper.map(e))
                .collect(Collectors.toList());
    }
    public List<Reward> getRewards(String characterId) {
        List<RewardDB> list = new ArrayList<>();
        return rewards.stream().filter(e -> e.getCharacterId().equals(characterId)).map(e -> mapper.map(e))
                .collect(Collectors.toList());
    }
    public List<RewardPP> getRewardPPs(String characterId) {
        List<RewardDB> list = new ArrayList<>();
        return rewardsPP.stream().filter(e -> e.getCharacterId().equals(characterId)).map(e -> mapper.map(e))
                .collect(Collectors.toList());
    }
    public List<Learn> getLearnings(String characterId) {
        List<LearnDB> list = new ArrayList<>();
        return learnings.stream().filter(e -> e.getCharacterId().equals(characterId)).map(e -> mapper.map(e))
                .collect(Collectors.toList());
    }
    public User postUser(User user) {
        users.add(mapper.map(user));
        return user;
    }
    public void postCharacter(Character character) {
        characters.add(mapper.map(character));
    }
    public void postAttributes(List<Attribute> attributes) {
        var attr = attributes.stream().map(e -> mapper.map(e)).collect(Collectors.toList());
        this.attributes.addAll(attr);
    }
    public void postLevelUp(LevelUp levelUp) {
        levelUps.add(mapper.map(levelUp));
    }
    public void postReward(Reward reward) {
        rewards.add(mapper.map(reward));
    }
    public void postRewardPP(RewardPP rewardPP) {
        rewardsPP.add(mapper.map(rewardPP));
    }
    public void postLearn(Learn learn) {
        learnings.add(mapper.map(learn));
    }
}
