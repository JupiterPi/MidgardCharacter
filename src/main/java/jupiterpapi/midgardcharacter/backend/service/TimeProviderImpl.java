package jupiterpapi.midgardcharacter.backend.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
@Profile("prod")
public class TimeProviderImpl implements TimeProvider {

    public String getDate() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }
}
