package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CheckService {
    @Autowired
    DBService db;
    @Autowired
    EnrichService enrich;
    @Autowired
    SkillService skillService;

    void checkNewCharacter(Character character, Collection<Attribute> attributes) throws UserException {
        if (db.getCharacter(character.getId()) != null) {
            throw new UserException();
        }

        for( Attribute a : attributes ) {
            if (a.getValue() < 1) throw new UserException();
            if (a.getValue() > 100) throw new UserException();
        }
    }
    void checkReward(Reward reward) throws UserException {
        if (reward.getEp() < 0) throw new UserException();
        if (reward.getGold() < 0) throw new UserException();

        getCharacter(reward.getCharacterId());
    }
    void checkRewardPP(RewardPP rewardPP) throws UserException {
        if (rewardPP.getPP() < 1) throw new UserException();

        skillService.checkSkillName( rewardPP.getSkillName() );

        getCharacter(rewardPP.getCharacterId());
    }
    void checkLearn(Learn learn) throws UserException {
        Character c = getCharacter(learn.getCharacterId());
        Skill s = c.getSkills().get(learn.getSkillName());

        checkLearning(s,learn,c);
    }
    void checkLevelUp(LevelUp levelUp) throws UserException {
        Character c = getCharacter(levelUp.getCharacterId());
        if (levelUp.getLevel() <= c.getLevel())
            throw new UserException();

        if (! levelUp.getAttribute().equals(""))
          checkAttribute(levelUp.getAttribute());

        if (!levelUp.getAttribute().equals("") && levelUp.getIncrease() < 1 ) throw new UserException();
        if (levelUp.getAp() < 1) throw new UserException();
    }

    private Character getCharacter(String characterId) throws UserException {
        return enrich.getCharacter(characterId);
    }
    private void checkAttribute(String name) throws UserException {
        if (!name.equals("St") && !name.equals("Gs") && !name.equals("Gw") && !name.equals("Ko") && !name.equals("In") && !name.equals("Zt") && !name.equals("pA") && !name.equals("Au") ) {
            throw new UserException();
        }
    }
    private void checkLearning(Skill skill, Learn learn, Character c) throws UserException {
        if (learn.isStarting()) {
          if (learn.getEpSpent() != 0)
              throw new UserException();
            if (learn.getGoldSpent() != 0)
                throw new UserException();
            if (learn.getPPSpent() != 0)
                throw new UserException();
        } else {
            if (skill == null)
                skill = new Skill(learn.getSkillName(), learn.getCharacterId(), 0);
            Skill enr = skillService.calculateCost(skill, c.getClassName());
            int remainingTE = enr.getTECost() - learn.getPPSpent();
            int remainingEP = enr.getEPCost() * remainingTE / enr.getTECost();
            int withoutGold = remainingEP - learn.getGoldSpent() / 2;
            if (learn.getEpSpent() != withoutGold)
                throw new UserException();
            if (learn.getGoldSpent() > c.getGold())
                throw new UserException();
            if (withoutGold > c.getEp())
                throw new UserException();
        }
    }

}
