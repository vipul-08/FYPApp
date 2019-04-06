package socio.connect.models;

public class User {
    private String _id;
    private String profilePic;
    private String firstName;
    private String userName;
    private String email;
    private String password;
    private String dateTimepicker;
    private String gender;
    private String __v;

    public User() {
    }

    public User(String _id, String profilePic, String firstName, String userName, String email, String password, String dataTimepicker, String gender, String __v) {
        this._id = _id;
        this.profilePic = profilePic;
        this.firstName = firstName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.dateTimepicker = dataTimepicker;
        this.gender = gender;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateTimepicker() {
        return dateTimepicker;
    }

    public void setDateTimepicker(String dataTimepicker) {
        this.dateTimepicker = dataTimepicker;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }
}
