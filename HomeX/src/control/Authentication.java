package control;

import java.io.IOException;
import java.text.ParseException;

import bean.User;

public class Authentication {
    private User user;

    public Authentication() {
        user = null;
    }

    public Authentication(String username, String password) throws IOException, ParseException {
        this.user = User.get(username, password);
    }

    public static boolean isAuthentic(String username, String password) throws IOException, ParseException {
        if (User.get(username, password).equals(null))
            return false;
        else
            return true;
    }

    public boolean isAuthentic() {
        if (this.user.equals(null))
            return false;
        else
            return true;
    }
    
    public boolean isLoggedIn() {
        if (this.user.equals(null)) {
            return false;
        } else
            return true;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}