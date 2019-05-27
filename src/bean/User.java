package bean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class User {
    private boolean isAdministrator;
    private Date birthdate;
    private int id;
    private Sex sex;
    private Set<Integer> house;
    private String email;
    private String username;
    private String password;
    private String telephone;

    public enum Sex {
        male, female, other
    }

    public User() {
    }

    public User(int id) {
        setId(id);
    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public static void delete(int id) throws IOException {
        File userFile = new File(System.getProperty("user.dir") + "/WebContent/DAO/user.json");
        JsonFactory userFactory = new JsonFactory();
        ObjectMapper userMapper = new ObjectMapper();
        ArrayNode userNode = (ArrayNode) userMapper.readTree(userFile);
        JsonGenerator userGenerator = userFactory.createGenerator(new FileWriter(userFile));

        for (int userCount = 0; userCount < userNode.size(); userCount++) {
            if (userNode.get(userCount).path("id").asInt() == id) {
                userNode.remove(userCount);
                break;
            }
        }
        userMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        userMapper.writeTree(userGenerator, userNode);
    }

    public void delete() throws IOException {
        delete(this.id);
    }

    public static User get(int id) throws IOException, ParseException {
        ObjectMapper userMapper = new ObjectMapper();
        JsonNode userNode = userMapper.readTree(new File(System.getProperty("user.dir") + "/WebContent/DAO/user.json"));
        User newUser = new User();

        for (JsonNode user : userNode) {
            if (user.path("id").asInt() == id) {
                newUser = get(user);
                return newUser;
            }
        }

        return null;
    }

    // if(isAuthentic) return User;
    // else return null;
    public static User get(String username, String password) throws IOException, ParseException {
        ObjectMapper userMapper = new ObjectMapper();
        JsonNode userNode = userMapper.readTree(new File(System.getProperty("user.dir") + "/WebContent/DAO/user.json"));

        User newUser = new User();

        for (JsonNode user : userNode) {
            if (user.path("username").textValue().equals(username)
                    && user.path("password").textValue().equals(password)) {
                newUser = get(user);

                return newUser;
            }
        }

        return null;
    }

    public static User get(JsonNode user) throws ParseException {
        User newUser = new User();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            newUser.birthdate = dateFormat.parse(user.path("birthdate").textValue());
        } catch (NullPointerException exception) {
            newUser.birthdate = null;
        }
        newUser.email = user.path("email").textValue();
        newUser.house = new HashSet<>();
        for (JsonNode house : user.path("house")) {
            newUser.house.add(house.asInt());
        }
        newUser.id = user.path("id").asInt();
        newUser.isAdministrator = user.path("isAdministrator").booleanValue();
        newUser.password = user.path("password").textValue();
        try {
            switch (user.path("sex").textValue()) {
            case "male":
                newUser.sex = Sex.male;
                break;
            case "female":
                newUser.sex = Sex.female;
                break;
            case "other":
                newUser.sex = Sex.other;
                break;
            default:
                newUser.sex = null;
                break;
            }
        } catch (NullPointerException exception) {
            newUser.sex = null;
        }
        newUser.telephone = user.path("telephone").textValue();
        newUser.username = user.path("username").textValue();

        return newUser;
    }

    public static Map<Integer, User> getAll() throws IOException, ParseException {
        ObjectMapper userMapper = new ObjectMapper();
        JsonNode userNode = userMapper.readTree(new File(System.getProperty("user.dir") + "/WebContent/DAO/user.json"));
        Map<Integer, User> userMap = new HashMap<>();

        for (JsonNode user : userNode) {
            User newUser = get(user);

            userMap.put(newUser.id, newUser);
        }

        return userMap;
    }

    public static void set(User user) throws IOException {
        File userFile = new File(System.getProperty("user.dir") + "/WebContent/DAO/user.json");
        JsonFactory userFactory = new JsonFactory();
        JsonNodeFactory userNodeFactory = new JsonNodeFactory(false);
        ObjectMapper userMapper = new ObjectMapper();
        ObjectNode newUserNode = userNodeFactory.objectNode();
        ArrayNode userNode = (ArrayNode) userMapper.readTree(userFile);
        ArrayNode houseNode = userNodeFactory.arrayNode();
        JsonGenerator userGenerator = userFactory.createGenerator(new FileWriter(userFile));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int userCount = 0; userCount < userNode.size(); userCount++) {
            if (userNode.get(userCount).path("id").asInt() == user.id) {
                userNode.remove(userCount);
                break;
            }
        }
        newUserNode.put("id", user.id);
        newUserNode.put("username", user.username);
        newUserNode.put("password", user.password);
        newUserNode.put("isAdministrator", user.isAdministrator);
        try {
            newUserNode.put("birthdate", dateFormat.format(user.birthdate));
        } catch (NullPointerException exception) {
            newUserNode.putNull("birthdate");
        }
        newUserNode.put("email", user.email);
        try {
            for (int house : user.house) {
                houseNode.add(house);
            }
            newUserNode.set("house", houseNode);
        } catch (NullPointerException exception) {
            houseNode.nullNode();
            newUserNode.set("house", houseNode);
        }
        try {
            switch (user.sex) {
            case male:
                newUserNode.put("sex", "male");
                break;
            case female:
                newUserNode.put("sex", "female");
                break;
            case other:
                newUserNode.put("sex", "other");
                break;
            }
        } catch (NullPointerException exception) {
            newUserNode.putNull("sex");
        }
        newUserNode.put("telephone", user.telephone);
        userNode.add(newUserNode);
        userMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        userMapper.writeTree(userGenerator, userNode);
    }

    public void set() throws IOException {
        set(this);
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean isAdministrator) {
        this.isAdministrator = isAdministrator;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Set<Integer> getHouse() {
        return house;
    }

    public void setHouse(Set<Integer> house) {
        this.house = house;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
