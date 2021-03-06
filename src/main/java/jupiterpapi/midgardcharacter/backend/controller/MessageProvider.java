package jupiterpapi.midgardcharacter.backend.controller;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageProvider {

    final static String language = "de";
    final static String country = "DE";
    final static Locale locale = new Locale(language, country);
    final static ResourceBundle messages = ResourceBundle.getBundle("messages", locale);

    public static String get(String key) {
        return messages.getString(key);
    }
}
