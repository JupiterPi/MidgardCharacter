package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.configuration.ConfigurationService;
import jupiterpapi.midgardcharacter.backend.configuration.InternalException;
import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;
import org.junit.Before;

import java.util.HashMap;

@SuppressWarnings("SameParameterValue")
public class TestBase {
    SkillService skillService;
    EnrichService service;
    DBServiceMock dbService;

    final Character initial_db = new Character("ID", "Name", "User", "As");
    final Attribute gw_db = new Attribute("Gw","ID",96);
    final Attribute in_db = new Attribute("In","ID",50);

    final Character initial = new Character("ID", "Name", "User", "As");
    final Attribute gw = new Attribute("Gw","ID",96);
    final Attribute in = new Attribute("In","ID",50);
    final HashMap<String,Attribute> attributes = new HashMap<>();

    @Before
    public void testData() {
        attributes.put("Gw",gw);
        attributes.put("In",in);
    }

    @Before
    public void setup() throws InternalException {
        skillService = new SkillService();
        service = new EnrichService();
        service.skillService = skillService;
        skillService.configurationService = new ConfigurationService();
        skillService.configurationService.read();
        dbService = new DBServiceMock();
        service.db = dbService;
    }

    protected void addCharacter() {
        dbService.characters.add( initial_db );
    }

    protected void addCharacterWithAttributes() {
        addCharacter();

        dbService.attributes.add(gw_db);
        dbService.attributes.add(in_db);

        gw.setBonus(2);
        initial.setAttributes(attributes);
    }

    protected void addReward(int ep, int gold) {
        Reward reward = new Reward("1","ID",ep,gold);
        Reward reward_db = new Reward("1","ID",ep,gold);
        dbService.rewards.add(reward_db);
        initial.getRewards().add(reward);
    }

    protected void addLearning(String skill, boolean starting, boolean learned, int newBonus, int ep, int gold, int pp) {
        Learn learn = new Learn("1","ID",skill,starting,learned,newBonus,ep,gold,pp);
        Learn learnDB = new Learn("1","ID",skill,starting,learned,newBonus,ep,gold,pp);
        dbService.learnings.add(learnDB);
        initial.getLearnings().add(learn);
    }

    protected void addRewardPP(String skill, int pp) {
        RewardPP r = new RewardPP("1","ID",skill,pp);
        RewardPP db = new RewardPP("1","ID",skill,pp);
        dbService.rewardsPP.add(db);
        initial.getRewardsPP().add(r);
    }

    protected void addLevelUp(int level, String attribute, int increase, int ap) {
        LevelUp l1 = new LevelUp("1","ID",level,attribute,increase,ap);
        LevelUp db = new LevelUp("1","ID",level,attribute,increase,ap);
        dbService.levelUps.add(db);
        initial.getLevelUps().add(l1);
        initial.setLevel(level);
        initial.setAp(ap);
        if (! attribute.equals("") ) {
            int old = initial.getAttributes().get(attribute).getValue();
            initial.getAttributes().get(attribute).setValue(old + increase);
        }
    }

}
