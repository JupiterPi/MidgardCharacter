package jupiterpapi.midgardcharacter.service;

import jupiterpa.model.*;
import jupiterpa.model.repo.*;
import jupiterpapi.midgardcharacter.model.Character;
import jupiterpapi.midgardcharacter.model.*;
import jupiterpapi.midgardcharacter.model.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DBServiceImpl implements DBService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    CharacterRepo characterRepo;

    @Autowired
    AttributeRepo attributeRepo;

    @Autowired
    LevelUpRepo levelUpRepo;

    @Autowired
    RewardRepo rewardRepo;

    @Autowired
    RewardPPRepo rewardPPRepo;

    @Autowired
    LearnRepo learnRepo;

    @Autowired
    DBMapper mapper;

    public void reset() {
        userRepo.deleteAll();
        characterRepo.deleteAll();
        attributeRepo.deleteAll();
        levelUpRepo.deleteAll();
        rewardRepo.deleteAll();
        rewardPPRepo.deleteAll();
        learnRepo.deleteAll();
    }

    public List<User> getUser() {
        List<UserDB> dbs = userRepo.findAll();
        return dbs.stream().map(db -> mapper.map(db)).collect(Collectors.toList());
    }
    public List<Character> getCharacters(String userId) {
        List<CharacterDB> dbs = characterRepo.findByUserId(userId);
        return dbs.stream().map(db -> mapper.map(db)).collect(Collectors.toList());
    }
    public Character getCharacter(String characterId) {
        Optional<CharacterDB> c = characterRepo.findById(characterId);
        return c.map(characterDB -> mapper.map(characterDB)).orElse(null);
    }
    public List<Attribute> getAttributes(String characterId) {
        List<AttributeDB> dbs = attributeRepo.findByCharacterId(characterId);
        return dbs.stream().map(db -> mapper.map(db)).collect(Collectors.toList());
    }
    public List<LevelUp> getLevelUps(String characterId) {
        return levelUpRepo
                    .findByCharacterId(characterId)
                    .stream().map(db -> mapper.map(db))
                    .collect(Collectors.toList());
    }
    public List<Reward> getRewards(String characterId) {
        return rewardRepo
                .findByCharacterId(characterId)
                .stream().map(db -> mapper.map(db))
                .collect(Collectors.toList());
    }
    public List<RewardPP> getRewardPPs(String characterId) {
        return rewardPPRepo
                .findByCharacterId(characterId)
                .stream().map(db -> mapper.map(db))
                .collect(Collectors.toList());
    }
    public List<Learn> getLearnings(String characterId) {
        return learnRepo
                .findByCharacterId(characterId)
                .stream().map(db -> mapper.map(db))
                .collect(Collectors.toList());
    }

    public User postUser(User user) {
        return mapper.map( userRepo.insert(mapper.map(user)) );
    }
    public void postCharacter(Character character) {
        characterRepo.insert(mapper.map(character));
    }
    public void postAttributes(List<Attribute> attributes) {
        List<AttributeDB> list = attributes.stream().map(a -> mapper.map(a)).collect(Collectors.toList());
        attributeRepo.insert(list);
    }
    public void postLevelUp(LevelUp levelUp) {
        levelUpRepo.insert(mapper.map(levelUp));
    }
    public void postReward(Reward reward) {
        rewardRepo.insert(mapper.map(reward));
    }
    public void postRewardPP(RewardPP rewardPP) {
        rewardPPRepo.insert(mapper.map(rewardPP));
    }
    public void postLearn(Learn learn) {
        learnRepo.insert(mapper.map(learn));
    }
}
