package bean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class User {
    private boolean isAdministrator;
    private Date birthdate;
    private int id;
    private Sex sex;
    private Set<House> house;
    private String email;
    private String username;
    private String password;
    private String telephone;

    public enum Sex {
        male, female, other
    }

    public User() {
    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public static User get(int id) throws MalformedURLException, IOException, ParseException {
        ObjectMapper userMapper = new ObjectMapper();
        JsonNode userNode = userMapper.readTree(new URL("http://localhost:8086/HouseExchangeManager/DAO/user.json"));
        User newUser = new User();

        for (JsonNode user : userNode) {
            if (user.path("id").asInt() == id) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                newUser.birthdate = dateFormat.parse(user.path("birthdate").textValue());
                newUser.email = user.path("email").textValue();
                // newUser.house
                newUser.id = user.path("id").asInt();
                newUser.isAdministrator = user.path("isAdministrator").booleanValue();
                newUser.password = user.path("password").textValue();
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
                newUser.telephone = user.path("telephone").textValue();
                newUser.username = user.path("username").textValue();
                break;
            }
        }

        return newUser;
    }

    public static void set(User user) {

    }

    public void set() {

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

    public Set<House> getHouse() {
        return house;
    }

    public void setHouse(Set<House> house) {
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
