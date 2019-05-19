package bean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Authentication {
    private Set<User> userSet;

    public Authentication() {
    }

    public void init() throws MalformedURLException, IOException {
        ObjectMapper userMapper = new ObjectMapper();
        JsonNode userNode = userMapper.readTree(new URL("http://localhost:8086/HouseExchangeManager/DAO/user.json"));

        userSet = new HashSet<>();

    }

    public boolean isAuthentic() {

    }
}
