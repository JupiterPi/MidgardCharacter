package jupiterpapi.midgardcharacter.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import jupiterpapi.midgardcharacter.backend.model.Learn;
import jupiterpapi.midgardcharacter.backend.model.create.*;
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

@SuppressWarnings("SameParameterValue")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class IntegrationTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    DBService db;

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

    final UserCreate userCreate = new UserCreate("1","Name","password");
    final UserDTO userDTO = new UserDTO("1","Name","password");
    final List<UserDTO> users = new ArrayList<>();
    @Before
    public void resetUsers() {
        users.clear();
    }
    void getUsers(List<UserDTO> users) throws Exception {
        getAndExpect("/api/users",users);
    }
    void postUser(UserCreate user) throws Exception {
        postAndExpect("/api/user",user,user);
    }

    @Test
    public void initiallyReturnsNoUser() throws Exception {
        getUsers(users);
    }
    @Test
    public void postUser() throws Exception {
        postUser(userCreate);
        users.add(userDTO);
        getUsers(users);
    }


    final CharacterCreate characterCreate = new CharacterCreate("SC1","Name","1","As");
    final CharacterMetaDTO characterMeta = new CharacterMetaDTO("SC1","Name","1");
    final List<CharacterMetaDTO> characterMetas = new ArrayList<>();
    final CharacterDTO characterDTO = new CharacterDTO("SC1","Name","1","As");
    @Before
    public void resetCharacterMetas() {
        characterMetas.clear();
    }
    void getCharacters(String userId, List<CharacterMetaDTO> characterMetas) throws Exception {
        getAndExpect("/api/characters/"+userId,characterMetas);
    }
    void postCharacter(CharacterCreate character) throws Exception {
        postAndExpect("/api/character",character,null);
    }
    void getCharacter(String characterId,CharacterDTO character) throws Exception {
        getAndExpect("/api/character/"+characterId,character);
    }

    @Test
    public void initialCharacters() throws Exception {
        getCharacters("1",characterMetas);
    }
    @Test
    public void postCharacterAndGetList() throws Exception {
        postUser(userCreate);
        postCharacter(characterCreate);
        characterMetas.add(characterMeta);
        getCharacters("1",characterMetas);
    }
    @Test
    public void postCharacterAndGetIt() throws Exception {
        postUser(userCreate);
        postCharacter(characterCreate);
        getCharacter("SC1",characterDTO);

    }

    int attributeId = 0;
    AttributeDTO getAttributeDTO(String name, int value, int bonus) {
        return new AttributeDTO(String.valueOf(attributeId),name,"SC1",value,bonus);
    }
    AttributeCreate getAttributeCreate(String name, int value, int bonus) {
        return new AttributeCreate(String.valueOf(attributeId),name,"SC1",value,bonus);
    }
    void addAttribute(String name, int value, int bonus) {
        attributeId++;
        characterCreate.getAttributes().add( getAttributeCreate(name,value,bonus) );
        characterDTO.getAttributes().add( getAttributeDTO(name,value,bonus) );
    }
    @Test
    public void postWithAttribute() throws Exception {
        postUser(userCreate);
        addAttribute("St",50,0);
        addAttribute("Gw",10,-1);
        addAttribute("Gs",98,2);
        postCharacter(characterCreate);
        getCharacter("SC1",characterDTO);
    }

    void postStandard() throws Exception {
        postUser(userCreate);
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
        getCharacter("SC1",characterDTO);
    }


    @Test
    public void postReward() throws Exception {
        postStandard();
        RewardCreate reward = new RewardCreate("1","SC1",100,200);
        postAndExpect("/api/reward",reward,reward);
    }

    @Test
    public void postLearning() throws Exception {
        postStandard();
        RewardCreate reward = new RewardCreate("1","SC1",100,200);
        postAndExpect("/api/reward",reward,reward);
        LearningCreate learnCreate = new LearningCreate("1","SC1","Akrobatik",true,8,0);
        LearningDTO learnDTO = new LearningDTO("1","SC1","Akrobatik",true,true,8,0,0,0);
        postAndExpect("/api/learn",learnCreate,learnDTO);
    }

    @Test
    public void postRewardPP() throws Exception {
        postStandard();
        RewardCreate reward = new RewardCreate("1","SC1",100,200);
        postAndExpect("/api/reward",reward,reward);
        LearningCreate learnCreate = new LearningCreate("1","SC1","Akrobatik",true,8,0);
        LearningDTO learnResult = new LearningDTO("1","SC1","Akrobatik",true,true,8,0,0,0);
        postAndExpect("/api/learn",learnCreate,learnResult);
        PPRewardCreate rewardPP = new PPRewardCreate("1","SC1","Akrobatik",1);
        postAndExpect("/api/rewardPP",rewardPP,rewardPP);
    }

    @Test
    public void postLevelUp() throws Exception {
        postStandard();
        RewardCreate reward = new RewardCreate("1","SC1",100,200);
        postAndExpect("/api/reward",reward,reward);
        LevelUpCreate levelUp = new LevelUpCreate("1","SC1",1,"",0,10);
        postAndExpect("/api/levelUp",levelUp,levelUp);
    }



}
