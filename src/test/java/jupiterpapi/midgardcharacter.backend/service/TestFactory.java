package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.configuration.ConfigurationService;
import jupiterpapi.midgardcharacter.backend.configuration.InternalException;
import org.junit.Before;

public class TestFactory {

    DBMapper dbMapper;
    UIMapper uiMapper;
    DBServiceMock dbService;
    ConfigurationService configurationService;
    SkillService skillService;
    EnrichService enrichService;
    CheckService checkService;
    MidgardServiceImpl midgardService;

    @Before
    public void setup() throws InternalException {

        dbMapper = DBMapper.INSTANCE;
        uiMapper = UIMapper.INSTANCE;

        dbService = new DBServiceMock();
        dbService.mapper = dbMapper;

        configurationService = new ConfigurationService();
        configurationService.read();

        skillService = new SkillService();
        skillService.configurationService = configurationService;

        enrichService = new EnrichService();
        enrichService.skillService = skillService;
        enrichService.db = dbService;

        checkService = new CheckService();
        checkService.db = dbService;
        checkService.enrich = enrichService;
        checkService.skillService = skillService;

        midgardService = new MidgardServiceImpl();
        midgardService.db = dbService;
        midgardService.mapper = uiMapper;
        midgardService.enrichService = enrichService;
        midgardService.checkService = checkService;
    }

}
