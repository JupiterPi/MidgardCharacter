package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("SameParameterValue")
public class TestBase extends TestFactory {

    final Character initial = new Character("ID", "Name", "User", "As");
    final Attribute gw = new Attribute("Gw", "ID", 96);
    final Attribute in = new Attribute("In", "ID", 50);
    final HashMap<String, Attribute> attributesMap = new HashMap<>();
    final List<Attribute> attributeList = new ArrayList<>();

    @Before
    public void testData() {
        attributesMap.put("Gw", gw);
        attributesMap.put("In", in);
        attributeList.add(gw);
        attributeList.add(in);
    }

    protected void addCharacter() {
        dbService.postCharacter(initial);
    }

    protected void addCharacterWithAttributes() {
        addCharacter();

        dbService.postAttributes(attributeList);

        gw.setBonus(2);
        initial.setAttributes(attributesMap);
    }

    protected void addReward(int ep, int gold) {
        Reward reward = new Reward("1","ID",ep,gold);
        dbService.postReward(reward);
        initial.getRewards().add(reward);
    }

    protected void addLearning(String skill, boolean starting, boolean learned, int newBonus, int ep, int gold, int pp) {
        Learning learning = new Learning("1", "ID", skill, starting, learned, newBonus, 0, ep, gold, pp);
        dbService.postLearn(learning);
        initial.getLearnings().add(learning);
    }

    protected void addRewardPP(String skill, int pp) {
        RewardPP r = new RewardPP("1","ID",skill,pp);
        dbService.postRewardPP(r);
        initial.getRewardsPP().add(r);
    }

    protected void addLevelUp(int level, String attribute, int increase, int ap) {
        LevelUp l1 = new LevelUp("1","ID",level,attribute,increase,ap);
        dbService.postLevelUp(l1);
        initial.getLevelUps().add(l1);
        initial.setLevel(level);
        initial.setAp(ap);
        if (! attribute.equals("") ) {
            int old = initial.getAttributes().get(attribute).getValue();
            initial.getAttributes().get(attribute).setValue(old + increase);
        }
    }

}
