package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.configuration.ConfigurationService;
import jupiterpapi.midgardcharacter.backend.configuration.model.BonusCost;
import jupiterpapi.midgardcharacter.backend.configuration.model.ClassEPCost;
import jupiterpapi.midgardcharacter.backend.configuration.model.SkillCost;
import jupiterpapi.midgardcharacter.backend.model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillService {
    @Autowired
    ConfigurationService configurationService;

    public String getBaseAttributeOfSkill(String skillName) throws UserException {
        return getSkillCost(skillName).getAttribute();
    }

    public int getStartingBonusOfSkill(String skillName) throws UserException {
        return getSkillCost(skillName).getStartingBonus();
    }

    public void checkSkillName(String skillName) throws UserException {
        getSkillCost(skillName);
    }

    public List<Skill> getInitialSkills(String characterId) {
        List<Skill> skills = new ArrayList<>();
        for (SkillCost cost : configurationService.skillCost.values()) {
            skills.add(new Skill(cost.getSkill(), characterId, cost.getUnlearnedBonus(), false));
        }
        return skills;
    }

    public Skill calculateCost(Skill skill, String className) throws UserException {

        // High Skill Levels
        if (skill.getBonus() > 17) {
            skill.setTECost(1000);
            skill.setEPCost(1000);
            return skill;
        }

        SkillCost skillCost = getSkillCost(skill.getName());

        int te = getTE(skill.getBonus(), skillCost);
        skill.setTECost(te);

        int epCost = getEPCost(skill, skillCost, className);
        skill.setEPCost(epCost * te);

        return skill;
    }

    private SkillCost getSkillCost(String skillName) throws UserException {
        SkillCost s = configurationService.skillCost.get(skillName);
        if (s == null)
            throw new UserException();
        return s;
    }

    private int getTE(int skillBonus, SkillCost skillCost) {
        String line = skillCost.getLine();
        BonusCost b = configurationService.bonusCost.get(line + "/" + (skillBonus + 1));
        if (b != null) {
            return b.getCost();
        } else {
            return skillCost.getLe() * 3;
        }
    }

    private int getEPCost(Skill skill, SkillCost skillCost, String className) throws UserException {
        String groups = skillCost.getGroups();
        String[] groups2 = groups.split(",");
        int epCost = 9999999;
        for (String g : groups2) {
            ClassEPCost c = configurationService.classEPCost.get(className + "/" + g);
            if (c == null)
                throw new UserException();
            if (c.getCost() < epCost)
                epCost = c.getCost();
        }
        return epCost;
    }

}
