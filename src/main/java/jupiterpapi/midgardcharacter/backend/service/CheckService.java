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
            if (a.getValue() > 100)
                throw new UserException();
        }
    }

    void checkReward(Reward reward) throws UserException {
        if (reward.getEp() < 0)
            throw new UserException();
        if (reward.getGold() < 0)
            throw new UserException();

        getCharacter(reward.getCharacterId());
    }

    void checkRewardPP(PPReward PPReward) throws UserException {
        if (PPReward.getPP() < 1)
            throw new UserException();

        skillService.checkSkillName(PPReward.getSkillName());

        getCharacter(PPReward.getCharacterId());
    }

    void checkAndEnrichLearning(Learning learning) throws UserException {
        Character c = getCharacter(learning.getCharacterId());

        Skill skill = c.getSkills().get(learning.getSkillName());
        if (skill == null) {
            skill = new Skill(learning.getSkillName(), learning.getCharacterId(), 0);
            skill = skillService.calculateCost(skill, c.getClassName());
        }
        enrichLearning(learning, skill);
        checkLearning(skill, learning, c);
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
        if (!name.equals("St") && !name.equals("Gs") && !name.equals("Gw") && !name.equals("Ko") && !name.equals("In")
                && !name.equals("Zt") && !name.equals("pA") && !name.equals("Au")) {
            throw new UserException();
        }
    }

    private void enrichLearning(Learning learning, Skill skill) {
        if (learning.isStarting()) {
            learning.setPPSpent(0);
            learning.setEpSpent(0);
            learning.setGoldSpent(0);
            learning.setLearned(true);
        } else {
            int pp = Math.min(skill.getPP(), skill.getTECost());
            int te = skill.getTECost() - pp;
            int ep = te * skill.getEPCost() / skill.getTECost();
            int gold = ep * 2 * learning.getPercentageGold() / 100;
            ep = ep * (100 - learning.getPercentageGold()) / 100;

            learning.setPPSpent(pp);
            learning.setEpSpent(ep);
            learning.setGoldSpent(gold);
        }
    }

    private void checkLearning(Skill skill, Learning learning, Character c) throws UserException {
        if (learning.isStarting()) {
            if (learning.getEpSpent() != 0)
                throw new UserException();
            if (learning.getGoldSpent() != 0)
                throw new UserException();
            if (learning.getPPSpent() != 0)
                throw new UserException();
        } else {
            int remainingTE = skill.getTECost() - learning.getPPSpent();
            int remainingEP = skill.getEPCost() * remainingTE / skill.getTECost();
            int withoutGold = remainingEP - learning.getGoldSpent() / 2;
            if (learning.getPercentageGold() < 0)
                throw new UserException();
            if (learning.getPercentageGold() > 50)
                throw new UserException();
            if (learning.getEpSpent() != withoutGold)
                throw new UserException();
            if (learning.getGoldSpent() > c.getGold())
                throw new UserException();
            if (withoutGold > c.getEp())
                throw new UserException();
        }
    }

}
