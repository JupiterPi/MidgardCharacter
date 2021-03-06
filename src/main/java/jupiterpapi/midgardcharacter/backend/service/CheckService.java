package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static jupiterpapi.midgardcharacter.backend.service.MidgardErrorMessages.*;

@Service
public class CheckService {
    @Autowired
    DBService db;
    @Autowired
    EnrichService enrich;
    @Autowired
    SkillService skillService;

    int numberOfAttributes = 9;

    void checkNewCharacter(Character character, Collection<Attribute> attributes) throws MidgardException {
        if (db.getCharacter(character.getId()) != null) {
            throw new MidgardException(NEW_CHARACTER_EXISTS);
        }

        Set<String> attributeSet = new HashSet<>();
        for (Attribute a : attributes) {
            attributeSet.add(a.getName());
            if (a.getValue() < 1)
                throw new MidgardException(NEW_ATTRIBUTE_TOO_LOW, a.getName(), String.valueOf(a.getValue()));
            if (a.getValue() > 100)
                throw new MidgardException(NEW_ATTRIBUTE_TOO_HIGH, a.getName(), String.valueOf(a.getValue()));
        }
        if (attributeSet.size() < numberOfAttributes)
            throw new MidgardException(NEW_WRONG_NUMBER_OF_ATTRIBUTES);
    }

    void checkReward(Reward reward) throws MidgardException {
        if (reward.getEp() < 0)
            throw new MidgardException(REWARD_EP_LOW, String.valueOf(reward.getEp()));
        if (reward.getGold() < 0)
            throw new MidgardException(REWARD_GOLD_LOW, String.valueOf(reward.getGold()));

        getCharacter(reward.getCharacterId());
    }

    void checkRewardPP(PPReward PPReward) throws MidgardException {
        if (PPReward.getPp() < 1)
            throw new MidgardException(REWARD_PP_LOW, String.valueOf(PPReward.getPp()));

        skillService.checkSkillName(PPReward.getSkillName());

        getCharacter(PPReward.getCharacterId());
    }

    void checkLevelUp(LevelUp levelUp) throws MidgardException {
        Character c = getCharacter(levelUp.getCharacterId());
        if (levelUp.getLevel() <= c.getLevel())
            throw new MidgardException(LEVELUP_LEVEL_LOW, String.valueOf(levelUp.getLevel()),
                    String.valueOf(c.getLevel()));

        if (!levelUp.getAttribute().equals(""))
            checkAttribute(levelUp.getAttribute());

        if (!levelUp.getAttribute().equals("") && levelUp.getIncrease() < 1)
            throw new MidgardException(LEVELUP_ATTR_INCREASE_LOW, String.valueOf(levelUp.getIncrease()));
        if (levelUp.getAp() < 1)
            throw new MidgardException(LEVELUP_AP_LOW, String.valueOf(levelUp.getAp()));
        checkEsForLevelUp(levelUp.getLevel(), c.getEs());
    }

    private Character getCharacter(String characterId) throws MidgardException {
        return enrich.getCharacter(characterId);
    }

    private void checkAttribute(String name) throws MidgardException {
        if (!name.equals("St") && !name.equals("Gs") && !name.equals("Gw") && !name.equals("Ko") && !name.equals("In")
                && !name.equals("Zt") && !name.equals("pA") && !name.equals("Au") && !name.equals("Wk")) {
            throw new MidgardException(INTERNAL_UNKNOWN_ATTRIBUTE, name);
        }
    }

    void checkLearningOnCreate(Learning learning) throws MidgardException {
        if (learning.getEpSpent() != 0)
            throw new MidgardException(LEARN_CREATE_EP_MUST_BE_ZERO);
        if (learning.getGoldSpent() != 0)
            throw new MidgardException(LEARN_CREATE_GOLD_MUST_BE_ZERO);
        if (learning.getPPSpent() != 0)
            throw new MidgardException(LEARN_CREATE_PP_MUST_BE_ZERO);
        if (!learning.isLearned())
            throw new MidgardException(LEARN_CREATE_LEARNED_MUST_BE_SET);
        if (!learning.isStarting())
            throw new MidgardException(LEARN_CREATE_STARTING_MUST_BE_SET);
    }

    void checkLearning(Learning learning, Skill skill, Character character) throws MidgardException {
        if (learning.getPercentageGold() < 0)
            throw new MidgardException(LEARN_LOW_GOLD);
        if (learning.getPercentageGold() > 50)
            throw new MidgardException(LEARN_HIGH_GOLD);
        if (learning.getEpSpent() > character.getEp())
            throw new MidgardException(LEARN_EP_HIGH, String.valueOf(character.getEp()),
                    String.valueOf(learning.getEpSpent()));
        if (learning.getGoldSpent() > character.getGold())
            throw new MidgardException(LEARN_GOLD_HIGH, String.valueOf(character.getGold()),
                    String.valueOf(learning.getGoldSpent()));
        if (!learning.isLearned())
            if (learning.getNewBonus() != skill.getBonus() + 1)
                throw new MidgardException(LEARN_INCREASE, String.valueOf(skill.getBonus()),
                        String.valueOf(learning.getNewBonus()));
    }

    private void checkEsForLevelUp(int level, int es) throws MidgardException {
        if (level <= 15) {
            String table = "2:100,3:250,4:500,5:750,6:1000,7:1250,8:1500,9:1750,10:2000,11:2500,12:3000,13:3500,14:4000,15:4500,15:5000";
            List<String> list = new ArrayList<>();
            for (String entry : table.split(",")) {
                String[] values = entry.split(":");
                list.add(values[1]);
            }
            String value = list.get(level - 2);
            int needed = Integer.parseInt(value);
            if (es < needed)
                throw new MidgardException(LEVELUP_LOW, String.valueOf(es), String.valueOf(needed));
        } else {
            int needed = (level - 11) * 1000;
            if (es < needed)
                throw new MidgardException();
        }
    }

}
