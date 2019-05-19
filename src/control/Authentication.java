package control;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

import bean.User;

public class Authentication {
    // if(!isAuthentic) user = null;
    // A cookie is suggested to show if the user has logged in or not
    // it seems a little redundant to have getters or setters here
    // but more functions could be added if necessary
    private User user;

    public Authentication() {
    }

    // the function of authentication can be realized using
    // User.get(username, password)
    // which will return null if is not authentic
    public Authentication(String username, String password) throws MalformedURLException, IOException, ParseException {
        this.user = User.get(username, password);
    }

    public static boolean isAuthentic(String username, String password)
            throws MalformedURLException, IOException, ParseException {
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
}
