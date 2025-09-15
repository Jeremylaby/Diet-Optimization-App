package ki.agh.dyeti.exception;

public class UserEmailNotFoundException extends RuntimeException {
    public UserEmailNotFoundException(String email) {
        super("User with " + email + " not found");
    }
}
