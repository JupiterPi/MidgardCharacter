package jupiterpapi.midgardcharacter.backend.service;

import jupiterpapi.midgardcharacter.backend.controller.MidgardMessageProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MidgardException extends Exception {

    final List<String> parameters = new ArrayList<>();

    public MidgardException() {
        super();
    }

    public MidgardException(MidgardErrorMessages message, String... key) {
        super(message.name());
        Collections.addAll(parameters, key);
    }

    @Override
    public String getMessage() {
        String key = super.getMessage();
        if (key != null) {
            String msg;
            msg = MidgardMessageProvider.get(key);
            int index = 0;
            for (String s : parameters) {
                index++;
                msg = msg.replaceAll("&" + index, s);
            }
            return msg;
        } else {
            return "";
        }
    }
}
