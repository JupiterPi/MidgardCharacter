package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.configuration.ConfigurationService;
import jupiterpapi.midgardcharacter.backend.configuration.InternalException;
import org.junit.Before;

public class TestFactory {

    DBServiceMock dbService;
    ConfigurationService configurationService;
    SkillService skillService;
    EnrichService enrichService;
    CheckService checkService;

    @Before
    public void setup() throws InternalException {

        dbService = new DBServiceMock();

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

    }

}
