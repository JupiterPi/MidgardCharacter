package jupiterpapi.midgardcharacter.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import jupiterpapi.midgardcharacter.backend.model.create.*;
import jupiterpapi.midgardcharacter.backend.model.dto.*;
import jupiterpapi.midgardcharacter.backend.service.DBService;
import jupiterpapi.midgardcharacter.backend.service.TimeProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("SameParameterValue")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "admin", authorities = "ADMIN")
public class IntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DBService db;

    @Autowired
    TimeProvider timeProvider;

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
        this.mockMvc.perform(get(url))
                //.andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString(expect)));
    }

    void getAndExpectFail(String url, String message) throws Exception {
        this.mockMvc.perform(get(url))
                //.andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().string(containsString(message)));
    }

    void postAndExpect(String url, Object body, Object exp) throws Exception {
        if (exp == null) {
            this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(asJsonString(body)))
                    //.andDo(print())
                    .andExpect(status().isOk());
        } else {
            String expect = asJsonString(exp);
            this.mockMvc.perform(
                    post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(body))
                    )
                    //.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString(expect)));
        }
    }

    final UserCreateDTO userCreateDTO = new UserCreateDTO("1", "Name", "password");
    final UserDTO userDTO = new UserDTO("1", "Name", "password");
    final List<UserDTO> users = new ArrayList<>();
    @Before
    public void resetUsers() {
        users.clear();
    }

    void getUsers(List<UserDTO> users) throws Exception {
        getAndExpect("/api/user", users);
    }

    void postUser(UserCreateDTO user) throws Exception {
        postAndExpect("/api/user", user, user);
    }

    @Test
    public void initiallyReturnsNoUser() throws Exception {
        getUsers(users);
    }

    @Test
    public void postUser() throws Exception {
        postUser(userCreateDTO);
        users.add(userDTO);
        getUsers(users);
    }

    CharacterCreateDTO characterCreateDTO;
    CharacterMetaDTO characterMeta;
    final List<CharacterMetaDTO> characterMetas = new ArrayList<>();
    CharacterDTO characterDTO;

    @Before
    public void prepareCharacter() {
        characterCreateDTO = new CharacterCreateDTO("SC1", "Name", "1", "As", 0);
        characterMeta = new CharacterMetaDTO("SC1", "Name", "1", "As", 1, timeProvider.getDate());
        characterDTO = new CharacterDTO("SC1", "Name", "1", "As", timeProvider.getDate());
        characterMetas.clear();
    }

    void getCharacters(String userId, List<CharacterMetaDTO> characterMetas) throws Exception {
        getAndExpect("/api/character/user/" + userId, characterMetas);
    }

    void postCharacter(CharacterCreateDTO character) throws Exception {
        postAndExpect("/api/character", character, null);
    }

    void getCharacter(String characterId, CharacterDTO character) throws Exception {
        getAndExpect("/api/character/" + characterId, character.getId());
    }

    int attributeId = 0;

    AttributeDTO getAttributeDTO(String name, int value, int bonus) {
        return new AttributeDTO(name, value, bonus);
    }

    AttributeCreateDTO getAttributeCreate(String name, int value) {
        return new AttributeCreateDTO(String.valueOf(attributeId), name, "SC1", value);
    }

    void addAttribute(String name, int value, int bonus) {
        attributeId++;
        characterCreateDTO.getAttributes().add(getAttributeCreate(name, value));
        characterDTO.getAttributes().add(getAttributeDTO(name, value, bonus));
    }

    void postStandard() throws Exception {
        postUser(userCreateDTO);
        addAttribute("Au", 50, 0);
        addAttribute("Gs", 50, 0);
        addAttribute("Gw", 50, 0);
        addAttribute("In", 50, 0);
        addAttribute("Ko", 50, 0);
        addAttribute("St", 50, 0);
        addAttribute("Wk", 50, 0);
        addAttribute("Zt", 50, 0);
        addAttribute("pA", 50, 0);
        postCharacter(characterCreateDTO);
    }

    @Test
    public void postStandardCheck() throws Exception {
        postStandard();
        getCharacter("SC1", characterDTO);
    }

    @Test
    public void getCharacterList() throws Exception {
        postStandard();
        characterMetas.add(characterMeta);
        getCharacters("1", characterMetas);
    }

    @Test
    public void postReward() throws Exception {
        postStandard();

        RewardCreateDTO reward = new RewardCreateDTO("1", "SC1", 100, 200);
        postAndExpect("/api/character/reward", reward, reward);
    }

    @Test
    public void postLearning() throws Exception {
        postStandard();

        RewardCreateDTO reward = new RewardCreateDTO("1", "SC1", 100, 200);
        postAndExpect("/api/character/reward", reward, reward);

        LearningCreateDTO learnCreate = new LearningCreateDTO("1", "SC1", "Akrobatik", true, 0);
        LearningDTO learnDTO = new LearningDTO("1", "SC1", "Akrobatik", true, true, 8, 60, 0, 0);
        postAndExpect("/api/character/learning", learnCreate, learnDTO);
    }

    @Test
    public void postPPReward() throws Exception {
        postStandard();

        RewardCreateDTO reward = new RewardCreateDTO("1", "SC1", 100, 200);
        postAndExpect("/api/character/reward", reward, reward);

        LearningCreateDTO learnCreate = new LearningCreateDTO("1", "SC1", "Akrobatik", true, 0);
        LearningDTO learnResult = new LearningDTO("1", "SC1", "Akrobatik", true, true, 8, 60, 0, 0);
        postAndExpect("/api/character/learning", learnCreate, learnResult);

        PPRewardCreateDTO rewardPP = new PPRewardCreateDTO("1", "SC1", "Akrobatik", 1);
        postAndExpect("/api/character/ppReward", rewardPP, rewardPP);
    }

    @Test
    public void postLevelUp() throws Exception {
        postStandard();

        RewardCreateDTO reward = new RewardCreateDTO("1", "SC1", 100, 200);
        postAndExpect("/api/character/reward", reward, reward);

        LevelUpCreateDTO levelUp = new LevelUpCreateDTO("1", "SC1", 2, "", 0, 10);
        postAndExpect("/api/character/levelUp", levelUp, levelUp);
    }

    @Test
    public void getCharacterFail() throws Exception {
        getAndExpectFail("/api/character/XYZ", "Charakter XYZ existiert nicht");
    }

}
