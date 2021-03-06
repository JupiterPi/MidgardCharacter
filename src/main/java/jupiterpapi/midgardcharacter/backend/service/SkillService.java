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

import static jupiterpapi.midgardcharacter.backend.service.MidgardErrorMessages.INTERNAL_NO_CLASS_COST;
import static jupiterpapi.midgardcharacter.backend.service.MidgardErrorMessages.INTERNAL_UNKNOWN_SKILL;

@Service
public class SkillService {
    @Autowired
    ConfigurationService configurationService;

    public String getBaseAttributeOfSkill(String skillName) throws MidgardException {
        return getSkillCost(skillName).getAttribute();
    }

    public int getStartingBonusOfSkill(String skillName) throws MidgardException {
        return getSkillCost(skillName).getStartingBonus();
    }

    public void checkSkillName(String skillName) throws MidgardException {
        getSkillCost(skillName);
    }

    public List<Skill> getInitialSkills(String characterId) {
        List<Skill> skills = new ArrayList<>();
        for (SkillCost cost : configurationService.skillCost.values()) {
            skills.add(
                    new Skill(cost.getSkill(), characterId, cost.getUnlearnedBonus(), 0, cost.getUnlearnedBonus(), 0, 0,
                            0, false));
        }
        return skills;
    }

    public Skill calculateCost(Skill skill, String className) throws MidgardException {

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

    private SkillCost getSkillCost(String skillName) throws MidgardException {
        SkillCost s = configurationService.skillCost.get(skillName);
        if (s == null)
            throw new MidgardException(INTERNAL_UNKNOWN_SKILL, skillName);
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

    private int getEPCost(Skill skill, SkillCost skillCost, String className) throws MidgardException {
        String groups = skillCost.getGroups();
        String[] groups2 = groups.split(",");
        int epCost = 9999999;
        for (String g : groups2) {
            ClassEPCost c = configurationService.classEPCost.get(className + "/" + g);
            if (c == null)
                throw new MidgardException(INTERNAL_NO_CLASS_COST, className, g);
            if (c.getCost() < epCost)
                epCost = c.getCost();
        }
        return epCost;
    }

}
