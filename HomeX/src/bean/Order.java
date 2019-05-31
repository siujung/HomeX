package bean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Order {
    private int id;
    private int house;
    private int host;
    private int tenant;
    private Date start;
    private Date end;

    public Order() throws IOException, ParseException {
    }

    public Order(boolean isNew) throws IOException, ParseException {
        if (isNew) {
            Set<Integer> idSet = new TreeSet<>();

            idSet.addAll(getAll().keySet());
            setId(Collections.max(idSet) + 1);
        }
    }

    public Order(int id) {
        setId(id);
    }

    public static void delete(int id) throws IOException {
        File orderFile = new File(System.getProperty("user.dir") + "/WebContent/DAO/order.json");
        JsonFactory orderFactory = new JsonFactory();
        ObjectMapper orderMapper = new ObjectMapper();
        ArrayNode orderNode = (ArrayNode) orderMapper.readTree(orderFile);
        JsonGenerator orderGenerator = orderFactory.createGenerator(new FileWriter(orderFile));

        for (int orderCount = 0; orderCount < orderNode.size(); orderCount++) {
            if (orderNode.get(orderCount).path("id").asInt() == id) {
                orderNode.remove(orderCount);
                break;
            }
        }
        orderMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        orderMapper.writeTree(orderGenerator, orderNode);
    }

    public void delete() throws IOException {
        delete(this.id);
    }

    public static Order get(int id) throws IOException, ParseException {
        ObjectMapper orderMapper = new ObjectMapper();
        JsonNode orderNode = orderMapper
                .readTree(new File(System.getProperty("user.dir") + "/WebContent/DAO/order.json"));
        Order newOrder = new Order();

        for (JsonNode order : orderNode) {
            if (order.path("id").asInt() == id) {
                newOrder = get(order);
                return newOrder;
            }
        }

        return newOrder;
    }

    public static Order get(JsonNode order) throws ParseException, IOException {
        Order newOrder = new Order();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            newOrder.start = dateFormat.parse(order.path("start").textValue());
        } catch (NullPointerException exception) {
            newOrder.start = null;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            newOrder.end = dateFormat.parse(order.path("end").textValue());
        } catch (NullPointerException exception) {
            newOrder.end = null;
        }
        newOrder.host = order.path("host").asInt();
        newOrder.house = order.path("house").asInt();
        newOrder.id = order.path("id").asInt();
        newOrder.tenant = order.path("tenant").asInt();

        return newOrder;
    }

    public static Map<Integer, Order> getAll() throws IOException, ParseException {
        ObjectMapper orderMapper = new ObjectMapper();
        JsonNode orderNode = orderMapper
                .readTree(new File(System.getProperty("user.dir") + "/WebContent/DAO/order.json"));
        Map<Integer, Order> orderMap = new HashMap<>();

        for (JsonNode order : orderNode) {
            Order newOrder = get(order);

            orderMap.put(newOrder.id, newOrder);
        }

        return orderMap;
    }

    public static void set(Order order) throws IOException {
        File orderFile = new File(System.getProperty("user.dir") + "/WebContent/DAO/order.json");
        JsonFactory orderFactory = new JsonFactory();
        JsonNodeFactory orderNodeFactory = new JsonNodeFactory(false);
        ObjectMapper orderMapper = new ObjectMapper();
        ObjectNode newOrderNode = orderNodeFactory.objectNode();
        ArrayNode orderNode = (ArrayNode) orderMapper.readTree(orderFile);
        JsonGenerator orderGenerator = orderFactory.createGenerator(new FileWriter(orderFile));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int orderCount = 0; orderCount < orderNode.size(); orderCount++) {
            if (orderNode.get(orderCount).path("id").asInt() == order.id) {
                orderNode.remove(orderCount);
                break;
            }
        }
        newOrderNode.put("id", order.id);
        newOrderNode.put("house", order.house);
        newOrderNode.put("host", order.host);
        newOrderNode.put("tenant", order.tenant);
        try {
            newOrderNode.put("start", dateFormat.format(order.start));
        } catch (NullPointerException exception) {
            newOrderNode.putNull("start");
        }
        try {
            newOrderNode.put("end", dateFormat.format(order.end));
        } catch (NullPointerException exception) {
            newOrderNode.putNull("end");
        }
        orderNode.add(newOrderNode);
        orderMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        orderMapper.writeTree(orderGenerator, orderNode);
    }

    public void set() throws IOException {
        set(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public int getHost() {
        return host;
    }

    public void setHost(int host) {
        this.host = host;
    }

    public int getTenant() {
        return tenant;
    }

    public void setTenant(int tenant) {
        this.tenant = tenant;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

}
