package jupiterpapi.midgardcharacter.backend.service;

public class UserException extends Exception {
    public UserException() {
        super();
    }

    public UserException(String key) {
        super(key);
    }
}
