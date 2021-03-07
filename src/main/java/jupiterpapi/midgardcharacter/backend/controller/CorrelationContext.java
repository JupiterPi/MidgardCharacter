package jupiterpapi.midgardcharacter.backend.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.codec.Base64;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class CorrelationContext {

    public final static String CORRELATION_ID = "CorrelationID";
    public final static String AUTHENTICATION = "Authentication";

    private static final ThreadLocal<String> id = new ThreadLocal<>();
    private static final ThreadLocal<User> user = new ThreadLocal<>();

    // ControllerFilter
    public static void setCorrelationID(String new_id) {
        id.set(new_id);
    }

    public static String getCorrelationID() {
        return id.get();
    }

    public static void determineCorrelationID(String prefix) {
        String corrID = prefix + " - " + UUID.randomUUID();
        setCorrelationID(corrID);
    }

    // Not used
    public static String getAuthentication() {
        String userName = user.get().getUsername();
        String password = user.get().getPassword();
        String auth = userName + ":" + password;
        //noinspection deprecation
        byte[] encodedAuth = Base64.encode(auth.getBytes(StandardCharsets.US_ASCII));
        return "Basic " + new String(encodedAuth);
    }

    // set By AOP
    public static void setUser(User new_user) {
        user.set(new_user);
    }

}
