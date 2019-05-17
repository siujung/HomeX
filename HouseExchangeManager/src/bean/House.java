package bean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class House {
    private boolean isAvailable;
    private int id;
    private Set<Constraint> constraint;
    private Set<Service> service;
    private String address;
    private String title;
    private User host;
    private User tenant;

    public enum Constraint {
        kid, noise, pet, plant, smoke
    }

    public enum Service {
        clean, pet, plant
    }

    public House() {
    }

    public House(User host) {
        setHost(host);
    }

    public static House get(int id) throws MalformedURLException, IOException, ParseException {
        ObjectMapper houseMapper = new ObjectMapper();
        JsonNode houseNode = houseMapper.readTree(new URL("http://localhost:8086/HouseExchangeManager/DAO/house.json"));
        House newHouse = new House();

        for (JsonNode house : houseNode) {
            if (house.path("id").asInt() == id) {
                newHouse.address = house.path("address").textValue();
                // newHouse.constraint
                newHouse.host = User.get(house.path("user").asInt());
                newHouse.id = house.path("id").asInt();
                newHouse.isAvailable = house.path("isAvailable").booleanValue();
                // newHouse.service
                newHouse.tenant = User.get(house.path("tenant").asInt());
                newHouse.title = house.path("title").textValue();
                break;
            }
        }

        return newHouse;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Constraint> getConstraint() {
        return constraint;
    }

    public void setConstraint(Set<Constraint> constraint) {
        this.constraint = constraint;
    }

    public Set<Service> getService() {
        return service;
    }

    public void setService(Set<Service> service) {
        this.service = service;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public User getTenant() {
        return tenant;
    }

    public void setTenant(User tenant) {
        this.tenant = tenant;
    }
}
