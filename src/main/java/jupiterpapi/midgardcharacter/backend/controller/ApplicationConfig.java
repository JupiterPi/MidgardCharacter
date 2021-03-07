package jupiterpapi.midgardcharacter.backend.controller;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
@Data
public class ApplicationConfig {
    private String name;
    private String description;
    private String version;
    private String adminPassword;
}
