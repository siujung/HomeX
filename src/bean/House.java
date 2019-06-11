package bean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class House {
    private boolean isAvailable;
    private int host;
    private int id;
    private int tenant;
    private Map<Constraint, String> constraint;
    private Map<Service, String> service;
    private String address;
    private String title;

    public enum Constraint {
        kid, noise, pet, smoke, other
    }

    public enum Service {
        clean, pet, plant, other
    }

    public House() throws IOException {
    }

    public House(boolean isNew) throws IOException {
        if (isNew) {
            Set<Integer> idSet = new TreeSet<>();

            idSet.addAll(getAll().keySet());
            setId(Collections.max(idSet) + 1);
        }
    }

    public House(int id) {
        setId(id);
    }

    public static void delete(int id) throws IOException {
        File houseFile = new File(System.getProperty("user.home") + "/HomeX/house.json");
        JsonFactory houseFactory = new JsonFactory();
        ObjectMapper houseMapper = new ObjectMapper();
        ArrayNode houseNode = (ArrayNode) houseMapper.readTree(houseFile);
        JsonGenerator houseGenerator = houseFactory.createGenerator(new FileWriter(houseFile));

        for (int houseCount = 0; houseCount < houseNode.size(); houseCount++) {
            if (houseNode.get(houseCount).path("id").asInt() == id) {
                houseNode.remove(houseCount);
                break;
            }
        }
        houseMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        houseMapper.writeTree(houseGenerator, houseNode);
    }

    public void delete() throws IOException {
        delete(this.id);
    }

    public static House get(int id) throws IOException, ParseException {
        File houseFile = new File(System.getProperty("user.home") + "/HomeX/house.json");
        ObjectMapper houseMapper = new ObjectMapper();
        JsonNode houseNode = houseMapper.readTree(houseFile);
        House newHouse = new House();

        for (JsonNode house : houseNode) {
            if (house.path("id").asInt() == id) {
                newHouse = get(house);

                return newHouse;
            }
        }

        return null;
    }

    public static House get(JsonNode house) throws IOException {
        House newHouse = new House();
        Iterator<String> fieldIterator;

        newHouse.address = house.path("address").textValue();
        newHouse.constraint = new HashMap<>();
        fieldIterator = house.path("constraint").fieldNames();
        while (fieldIterator.hasNext()) {
            String fieldName = fieldIterator.next();

            switch (fieldName) {
            case "kid":
                newHouse.constraint.put(Constraint.kid, house.path("constraint").path("kid").textValue());
                break;
            case "noise":
                newHouse.constraint.put(Constraint.noise, house.path("constraint").path("noise").textValue());
                break;
            case "pet":
                newHouse.constraint.put(Constraint.pet, house.path("constraint").path("pet").textValue());
                break;
            case "smoke":
                newHouse.constraint.put(Constraint.smoke, house.path("constraint").path("smoke").textValue());
                break;
            case "other":
                newHouse.constraint.put(Constraint.other, house.path("constraint").path("other").textValue());
                break;
            }
        }
        newHouse.host = house.path("host").asInt();
        newHouse.id = house.path("id").asInt();
        newHouse.isAvailable = house.path("isAvailable").booleanValue();
        newHouse.service = new HashMap<>();
        fieldIterator = house.path("service").fieldNames();
        while (fieldIterator.hasNext()) {
            String fieldName = fieldIterator.next();

            switch (fieldName) {
            case "clean":
                newHouse.service.put(Service.clean, house.path("service").path("clean").textValue());
                break;
            case "pet":
                newHouse.service.put(Service.pet, house.path("service").path("pet").textValue());
                break;
            case "plant":
                newHouse.service.put(Service.plant, house.path("service").path("plant").textValue());
                break;
            case "other":
                newHouse.service.put(Service.other, house.path("service").path("other").textValue());
                break;
            }
        }
        newHouse.tenant = house.path("tenant").asInt();
        newHouse.title = house.path("title").textValue();

        return newHouse;
    }

    public static Map<Integer, House> getAll() throws IOException {
        File houseFile = new File(System.getProperty("user.home") + "/HomeX/house.json");
        ObjectMapper houseMapper = new ObjectMapper();
        JsonNode houseNode = houseMapper.readTree(houseFile);
        Map<Integer, House> houseMap = new HashMap<>();

        for (JsonNode house : houseNode) {
            House newHouse = get(house);

            houseMap.put(newHouse.id, newHouse);
        }

        return houseMap;
    }

    public static String getHTML(int id) throws IOException, ParseException {
        House house = get(id);

        if (null == house)
            return null;
        else {
            String html = "<h2>" + house.title + "</h2>" + "<p>AVAILABLE = " + house.isAvailable + "</p>";

            if (!house.constraint.isEmpty())
                html += "<p><b>Constraints</b></p>";
            for (Entry<Constraint, String> constraint : house.constraint.entrySet()) {
                html += "<p>" + constraint.getKey().toString() + " = " + constraint.getValue() + "</p>";
            }
            if (!house.service.isEmpty())
                html += "<p><b>Services</b></p>";
            for (Entry<Service, String> service : house.service.entrySet()) {
                html += "<p>" + service.getKey().toString() + " = " + service.getValue() + "</p>";
            }
            html += "<p>ADDRESS = " + house.address + "</p>";

            html += "<p>ID = " + house.id + "</p>";

            return html;
        }
    }

    public String getHTML() {
        String html = "<h2>" + this.title + "</h2>" + "<p>AVAILABLE = " + this.isAvailable + "</p>";

        if (!this.constraint.isEmpty())
            html += "<p><b>Constraints</b></p>";
        for (Entry<Constraint, String> constraint : this.constraint.entrySet()) {
            html += "<p>" + constraint.getKey().toString() + " = " + constraint.getValue() + "</p>";
        }
        if (!this.service.isEmpty())
            html += "<p><b>Services</b></p>";
        for (Entry<Service, String> service : this.service.entrySet()) {
            html += "<p>" + service.getKey().toString() + " = " + service.getValue() + "</p>";
        }
        html += "<p>ADDRESS = " + this.address + "</p>";
        html += "<p>ID = " + this.id + "</p>";
        
        //html += "<p><input type=\"hidden\" id=\"houseId\" name=\"houseId\" value=\""+this.getId()+"\"></p>";
        //html += "<p><input type=\"text\" value=\""+this.id+"\" id=\"houseId\" name=\"houseId\" readonly></p>";
        //System.out.println(html);
        return html;
    }

    public static void set(House house) throws IOException {
        File houseFile = new File(System.getProperty("user.home") + "/HomeX/house.json");
        JsonFactory houseFactory = new JsonFactory();
        JsonNodeFactory houseNodeFactory = new JsonNodeFactory(false);
        ObjectMapper houseMapper = new ObjectMapper();
        ObjectNode newHouseNode = houseNodeFactory.objectNode();
        ObjectNode newConstraintNode = houseNodeFactory.objectNode();
        ObjectNode newServiceNode = houseNodeFactory.objectNode();
        ArrayNode houseNode = (ArrayNode) houseMapper.readTree(houseFile);
        JsonGenerator houseGenerator = houseFactory.createGenerator(new FileWriter(houseFile));

        for (int houseCount = 0; houseCount < houseNode.size(); houseCount++) {
            if (houseNode.get(houseCount).path("id").asInt() == house.id) {
                houseNode.remove(houseCount);
                break;
            }
        }
        newHouseNode.put("id", house.id);
        newHouseNode.put("host", house.host);
        newHouseNode.put("tenant", house.tenant);
        newHouseNode.put("title", house.title);
        newHouseNode.put("address", house.address);
        try {
            for (Constraint constraint : house.constraint.keySet()) {
                switch (constraint) {
                case kid:
                    newConstraintNode.put("kid", house.constraint.get(constraint));
                    break;
                case noise:
                    newConstraintNode.put("noise", house.constraint.get(constraint));
                    break;
                case pet:
                    newConstraintNode.put("pet", house.constraint.get(constraint));
                    break;
                case smoke:
                    newConstraintNode.put("smoke", house.constraint.get(constraint));
                    break;
                case other:
                    newConstraintNode.put("other", house.constraint.get(constraint));
                    break;
                }
            }
            newHouseNode.set("constraint", newConstraintNode);
        } catch (NullPointerException exception) {
            newHouseNode.putNull("constraint");
        }
        try {
            for (Service service : house.service.keySet()) {
                switch (service) {
                case clean:
                    newServiceNode.put("clean", house.service.get(service));
                    break;
                case pet:
                    newServiceNode.put("pet", house.service.get(service));
                    break;
                case plant:
                    newServiceNode.put("plant", house.service.get(service));
                    break;
                case other:
                    newServiceNode.put("other", house.service.get(service));
                    break;
                }
            }
            newHouseNode.set("service", newServiceNode);
        } catch (NullPointerException exception) {
            newHouseNode.putNull("service");
        }
        newHouseNode.put("isAvailable", house.isAvailable);
        houseNode.add(newHouseNode);
        houseMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        houseMapper.writeTree(houseGenerator, houseNode);
    }

    public void set() throws IOException {
        set(this);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getHost() {
        return host;
    }

    public void setHost(int host) {
        this.host = host;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTenant() {
        return tenant;
    }

    public void setTenant(int tenant) {
        this.tenant = tenant;
    }

    public Map<Constraint, String> getConstraint() {
        return constraint;
    }

    public void setConstraint(Map<Constraint, String> constraint) {
        this.constraint = constraint;
    }

    public Map<Service, String> getService() {
        return service;
    }

    public void setService(Map<Service, String> service) {
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
}
