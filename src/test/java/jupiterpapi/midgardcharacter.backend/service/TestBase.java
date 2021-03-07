package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.model.Character;
import jupiterpapi.midgardcharacter.backend.model.*;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("SameParameterValue")
public class TestBase extends TestFactory {

    final Character initial = new Character("ID", "Name", "User", "As", 0);
    final Attribute gw = new Attribute("ID/Gw", "Gw", "ID", 96, 2);
    final Attribute in = new Attribute("ID/In", "In", "ID", 50, 0);
    final Attribute st = new Attribute("ID/St", "St", "ID", 92, 1);
    final Attribute ko = new Attribute("ID/Ko", "Ko", "ID", 15, -1);
    final Attribute zt = new Attribute("ID/Zt", "Zt", "ID", 2, -2);
    final HashMap<String, Attribute> attributesMap = new HashMap<>();
    final List<Attribute> attributeList = new ArrayList<>();

    @Before
    public void testData() {
        initial.setLevel(1);
        attributesMap.put("Gw", gw);
        attributesMap.put("In", in);
        attributesMap.put("St", st);
        attributesMap.put("Ko", ko);
        attributesMap.put("Zt", zt);
        attributeList.add(gw);
        attributeList.add(in);
        attributeList.add(st);
        attributeList.add(ko);
        attributeList.add(zt);
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
        Reward reward = new Reward("1", "ID", ep, gold);
        dbService.postReward(reward);
        initial.getRewards().add(reward);
        initial.setEp(initial.getEp() + ep);
        initial.setEs(initial.getEs() + ep);
        initial.setGold(initial.getGold() + gold);
    }

    protected void addLearning(String skill, boolean starting, boolean learned, int newBonus, int ep, int gold, int pp) {
        Learning learning = new Learning("1", "ID", skill, starting, learned, newBonus, 0, ep, gold, pp);
        dbService.postLearning(learning);
        initial.getLearnings().add(learning);
    }

    protected void addRewardPP(String skill, int pp) {
        PPReward r = new PPReward("1", "ID", skill, pp);
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
