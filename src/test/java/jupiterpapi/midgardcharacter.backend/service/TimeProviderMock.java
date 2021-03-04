package jupiterpapi.midgardcharacter.backend.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!prod")
public class TimeProviderMock implements TimeProvider {

    String date;

    public TimeProviderMock() {
        date = new TimeProviderImpl().getDate();
    }

    public String getDate() {
        return date;
    }
}
