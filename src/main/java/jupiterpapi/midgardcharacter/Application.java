package jupiterpapi.midgardcharacter;

import jupiterpapi.midgardcharacter.backend.configuration.ConfigurationService;
import jupiterpapi.midgardcharacter.backend.configuration.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    ConfigurationService configuration;

    @Override
    public void run(String... args) {
        try {
            configuration.read();
        } catch (InternalException e) {
            logger.error("ERROR reading configuration");
            return;
        }
        logger.info("Configuration read successfully");
    }
}
