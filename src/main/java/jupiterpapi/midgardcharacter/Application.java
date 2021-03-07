package jupiterpapi.midgardcharacter;

import jupiterpapi.midgardcharacter.backend.configuration.ConfigurationService;
import jupiterpapi.midgardcharacter.backend.configuration.InternalException;
import jupiterpapi.midgardcharacter.backend.controller.ApplicationConfig;
import jupiterpapi.midgardcharacter.backend.model.repo.UserDB;
import jupiterpapi.midgardcharacter.backend.model.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ConfigurationService configuration;
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ApplicationConfig applicationConfig;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        readConfiguration();
        insertAdmin();
    }

    void readConfiguration() {
        try {
            configuration.read();
        } catch (InternalException e) {
            logger.error("ERROR reading configuration");
            return;
        }
        logger.info("Configuration read successfully");
    }

    void insertAdmin() {
        UserDB admin = userRepo.findByName("admin");
        if (admin != null)
            userRepo.delete(admin);
        else {
            admin = new UserDB();
            admin.setName("admin");
        }

        String adminPassword = applicationConfig.getAdminPassword();
        admin.setPassword(passwordEncoder.encode(adminPassword));
        logger.info("AdminPassword: {}", adminPassword);
        userRepo.insert(admin);
    }

}
