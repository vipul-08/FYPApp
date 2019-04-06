package socio.connect.models;

public class LoginResponse {

    String message;
    User user;

    public LoginResponse(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public LoginResponse() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}