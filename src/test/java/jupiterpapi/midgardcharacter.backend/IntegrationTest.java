package jupiterpapi.midgardcharacter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jupiterpapi.midgardcharacter.backend.model.Learn;
import jupiterpa.model.dto.*;
import jupiterpapi.midgardcharacter.backend.service.DBService;
import jupiterpapi.midgardcharacter.backend.model.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class IntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DBService db;

    @Before
    public void reset() {
        db.reset();
    }

    String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    void getAndExpect(String url, Object exp) throws Exception {
        String expect = asJsonString(exp);
        this.mockMvc
                .perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expect)));
    }
    void postAndExpect(String url, Object body, Object exp) throws Exception {
        if (exp == null) {
            this.mockMvc.perform(
                    post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(body))
                    )
                    .andDo(print())
                    .andExpect(status().isOk());
        } else {
            String expect = asJsonString(exp);
            this.mockMvc.perform(
                    post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(body))
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString(expect)));
        }
    }

    UserDTO user = new UserDTO("1","Name","password");
    List<UserDTO> users = new ArrayList<>();
    @Before
    public void resetUsers() {
        users.clear();
    }
    void getUsers(List<UserDTO> users) throws Exception {
        getAndExpect("/api/users",users);
    }
    void postUser(UserDTO user) throws Exception {
        postAndExpect("/api/user",user,user);
    }

    @Test
    public void initiallyReturnsNoUser() throws Exception {
        getUsers(users);
    }
    @Test
    public void postUser() throws Exception {
        postUser(user);
        users.add(user);
        getUsers(users);
    }


    CharacterDTO characterCreate = new CharacterDTO("SC1","Name","1","As");
    CharacterInfoDTO characterInfo = new CharacterInfoDTO("SC1","Name","1");
    CharacterDTO character = new CharacterDTO("SC1","Name","1","As");
    List<CharacterInfoDTO> characterInfos = new ArrayList<>();
    @Before
    public void resetCharacterInfos() {
        characterInfos.clear();
    }
    void getCharacters(String userId, List<CharacterInfoDTO> characterInfos) throws Exception {
        getAndExpect("/api/characters/"+userId,characterInfos);
    }
    void postCharacter(CharacterDTO character) throws Exception {
        postAndExpect("/api/character",character,null);
    }
    void getCharacter(String characterId,CharacterDTO character) throws Exception {
        getAndExpect("/api/character/"+characterId,character);
    }

    @Test
    public void initialCharacters() throws Exception {
        getCharacters("1",characterInfos);
    }
    @Test
    public void postCharacterAndGetList() throws Exception {
        postUser(user);
        postCharacter(characterCreate);
        characterInfos.add(characterInfo);
        getCharacters("1",characterInfos);
    }
    @Test
    public void postCharacterAndGetIt() throws Exception {
        postUser(user);
        postCharacter(characterCreate);
        getCharacter("SC1",character);

    }

    int attributeId = 0;
    AttributeDTO getAttribute(String name, int value, int bonus) {
        attributeId++;
        return new AttributeDTO(String.valueOf(attributeId),name,"SC1",value,bonus);
    }
    void addAttribute(String name, int value, int bonus) {
        AttributeDTO attr = getAttribute(name,value,bonus);
        characterCreate.getAttributes().add(attr);
        character.getAttributes().add(attr);
    }
    @Test
    public void postWithAttribute() throws Exception {
        postUser(user);
        addAttribute("St",50,0);
        addAttribute("Gw",10,-1);
        addAttribute("Gs",98,2);
        postCharacter(characterCreate);
        getCharacter("SC1",character);
    }

    void postStandard() throws Exception {
        postUser(user);
        addAttribute("St",50,0);
        addAttribute("Gw",50,0);
        addAttribute("Gs",50,0);
        addAttribute("Ko",50,0);
        addAttribute("In",50,0);
        addAttribute("Zt",50,0);
        addAttribute("pA",50,0);
        addAttribute("Au",50,0);
        postCharacter(characterCreate);
    }
    @Test
    public void postStandardCheck() throws Exception {
        postStandard();
    }


    @Test
    public void postReward() throws Exception {
        postStandard();
        RewardDTO reward = new RewardDTO("1","SC1",100,200);
        postAndExpect("/api/reward",reward,reward);
    }

    @Test
    public void postLearning() throws Exception {
        postStandard();
        RewardDTO reward = new RewardDTO("1","SC1",100,200);
        postAndExpect("/api/reward",reward,reward);
        LearnDTO learn = new LearnDTO("1","SC1","Akrobatik",true,true,8,0,0,0);
        postAndExpect("/api/learn",learn,learn);
    }

    @Test
    public void postRewardPP() throws Exception {
        postStandard();
        RewardDTO reward = new RewardDTO("1","SC1",100,200);
        postAndExpect("/api/reward",reward,reward);
        LearnDTO learn = new LearnDTO("1","SC1","Akrobatik",true,true,8,0,0,0);
        Learn learnResult = new Learn("1","SC1","Akrobatik",true,true,8,0,0,0);
        postAndExpect("/api/learn",learn,learn);
        RewardPPDTO rewardPP = new RewardPPDTO("1","SC1","Akrobatik",1);
        postAndExpect("/api/rewardPP",rewardPP,rewardPP);
    }

    @Test
    public void postLevelUp() throws Exception {
        postStandard();
        RewardDTO reward = new RewardDTO("1","SC1",100,200);
        postAndExpect("/api/reward",reward,reward);
        LevelUpDTO levelUp = new LevelUpDTO("1","SC1",1,"",0,10);
        postAndExpect("/api/levelUp",levelUp,levelUp);
    }



}
