package Model;

/**
 * Created by sai on 7/3/18.
 */


public class User {

    public String username;
    public String email;
    public String referalcode;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String username, String email, String referalcode) {
        this.username = username;
        this.email = email;
        this.referalcode = referalcode;
    }

    public String getReferalcode() {
        return referalcode;
    }

    public void setReferalcode(String referalcode) {
        this.referalcode = referalcode;
    }
}
