package socio.connect.models;

public class RegisterResponse {
    String message;

    public RegisterResponse() {
    }

    public RegisterResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
