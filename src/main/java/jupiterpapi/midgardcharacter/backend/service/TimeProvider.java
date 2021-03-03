package jupiterpapi.midgardcharacter.backend.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeProvider {
    public static String date; //TODO non-public

    public static String getDate() {
        if (date == null) {
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            return dateFormat.format(date);
        } else {
            return date;
        }
    }
}
