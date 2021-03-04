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

    public String getAttribute(String skillName) throws UserException {
        SkillCost s = configurationService.skillCost.get(skillName);
        if (s == null)
            throw new UserException();
        return s.getAttribute();
    }
    public void checkSkillName(String name) throws UserException {
        SkillCost s = configurationService.skillCost.get(name);
        if (s == null) throw new UserException();
    }
    public Skill calculateCost(Skill skill, String className) throws UserException {
        if (skill.getBonus() > 17) {
            skill.setTECost(1000);
            skill.setEPCost(1000);
            return skill;
        }

        // SkillCost for Skill
        SkillCost s = configurationService.skillCost.get(skill.getName());
        if (s == null)
            throw new UserException();

        // BonusCost for Skill -> TE/LE
        int te;
        String line = s.getLine();
        BonusCost b = configurationService.bonusCost.get(line + "/" + (skill.getBonus() + 1));
        if (b != null) {
            te = b.getCost();
        } else {
            te = s.getLe() * 3;
        }
        skill.setTECost(te);

        // ClassEPCost --> EP
        String groups = s.getGroups();
        String[] groups2 = groups.split(",");
        int epCost = 99;
        for (String g : groups2) {
            ClassEPCost c = configurationService.classEPCost.get(className + "/" + g);
            if (c == null)
                throw new UserException();
            if (c.getCost() < epCost)
                epCost = c.getCost();
        }
        skill.setEPCost(epCost * te);

        return skill;
    }

    public List<Skill> getInitialSkills(String characterId) {
        List<Skill> skills = new ArrayList<>();
        for (SkillCost cost : configurationService.skillCost.values()) {
            skills.add(new Skill(cost.getSkill(), characterId, cost.getUnlearnedBonus(), false));
        }
        return skills;
    }

    public int getInitialBonus(String skillName) {
        return configurationService.skillCost.get(skillName).getStartingBonus();
    }
}
