package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrichService {
    @Autowired
    DBService db;
    @Autowired
    SkillService skillService;

    Character getCharacter(String characterId) throws UserException {
        Character c = read(characterId);
        Character c2 = applyRewardsAndLearnings(c);
        Character c3 = calculateCost(c2);
        return calculateBonus(c3);
    }

    private Character read(String characterId) throws UserException {

        Character character = db.getCharacter(characterId);
        if (character == null)
            throw new UserException();

        List<Attribute> attributes = db.getAttributes(characterId);
        for (Attribute a : attributes) {
            character.getAttributes().put(a.getName(),a);
        }
        character.setLevelUps( db.getLevelUps(characterId) );
        character.setRewards( db.getRewards(characterId) );
        character.setRewardsPP( db.getRewardPPs(characterId) );
        character.setLearnings( db.getLearnings(characterId) );

        return character;
    }

    private Character applyRewardsAndLearnings(Character c) {
        Character c1 = applyReward(c);
        Character c2 = applyLevelUp(c1);
        Character c3 = applyLearning(c2);
        return applyRewardPP(c3);
    }

    private Character applyReward(Character c) {
        for (Reward r : c.getRewards()) {
            c.setEp(c.getEp() + r.getEp() );
            c.setGold(c.getGold() + r.getGold());
        }
        c.setEs(c.getEp());
        return c;
    }
    private Character applyLevelUp(Character c) {
        for (LevelUp l : c.getLevelUps()) {
            if (c.getLevel() < l.getLevel())
                c.setLevel(l.getLevel());
            if (c.getAp() < l.getAp())
                c.setAp(l.getAp());

            if (!l.getAttribute().equals("")) {
                Attribute a = c.getAttributes().get(l.getAttribute());
                a.setValue(a.getValue() + l.getIncrease());
            }

        }
        if (c.getLevel() == 0)
            c.setLevel(1);
        return c;
    }
    private Character applyLearning(Character c) {
        for (Learning l : c.getLearnings()) {
            Skill s = c.getSkills().get(l.getSkillName());
            if (s == null) {
                s = new Skill();
                s.setName(l.getSkillName());
                s.setCharacterId(c.getId());
                c.getSkills().put(s.getName(), s);
            }
            if (s.getBonus() < l.getNewBonus())
                s.setBonus(l.getNewBonus());
            c.setEp(c.getEp() - l.getEpSpent());
            c.setGold(c.getGold() - l.getGoldSpent());
           s.setPP( s.getPP() - l.getPPSpent() );
        }

        return c;
    }
    private Character applyRewardPP(Character c) {
        for (PPReward r : c.getRewardsPP()) {
            Skill s = c.getSkills().get(r.getSkillName());
            s.setPP(s.getPP() + r.getPP());
        }
        return c;
    }

    private Character calculateCost(Character c) throws UserException {
        for (Skill s : c.getSkills().values()) {
            skillService.calculateCost(s, c.getClassName());
        }
        return c;
    }
    private Character calculateBonus(Character c) throws UserException {
        for (Attribute a : c.getAttributes().values()) {
            a.setBonus( getBonus( a.getValue()));
        }

        for (Skill s : c.getSkills().values()) {
            Attribute a = c.getAttributes().get( skillService.getAttribute(s.getName()));
            s.setAttributeBonus( a.getBonus() );
            s.setTotalBonus( s.getBonus() + s.getAttributeBonus() );
        }
        return c;
    }
    private int getBonus(int v) {
        if (v <= 5) return -2;
        if (v <= 20) return -1;
        if (v >= 95) return 2;
        if (v >= 80) return 1;
        return 0;
    }
}

