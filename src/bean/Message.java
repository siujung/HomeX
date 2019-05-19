package bean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Message {
    private int from;
    private int to;
    private String content;
    private Timestamp time;

    public Message() {
    }

    public Message(Timestamp timestamp) {
        setTime(timestamp);
    }

    public static class MessageComparator implements Comparator<Message> {
        @Override
        public int compare(Message message1, Message message2) {
            return message1.time.compareTo(message2.time);
        }
    }

    public static void delete(Message message) throws MalformedURLException, IOException {
        ObjectMapper messageMapper = new ObjectMapper();
        ArrayNode messageNode = (ArrayNode) messageMapper
                .readTree(new URL("http://localhost:8086/HouseExchangeManager/DAO/message.json"));

        for (int messageCount = 0; messageCount < messageNode.size(); messageCount++) {
            if (messageNode.get(messageCount).path("from").asInt() == message.from
                    && messageNode.get(messageCount).path("to").asInt() == message.to
                    && Timestamp.valueOf(messageNode.get(messageCount).path("time").textValue()).equals(message.time)
                    && messageNode.get(messageCount).path("content").textValue().equals(message.content)) {
                messageNode.remove(messageCount);
                break;
            }
        }
    }

    public void delete() throws MalformedURLException, IOException {
        delete(this);
    }

    public static List<Message> get(int from, int to, boolean is2Way) throws MalformedURLException, IOException {
        ObjectMapper messageMapper = new ObjectMapper();
        JsonNode messageNode = messageMapper
                .readTree(new URL("http://localhost:8086/HouseExchangeManager/DAO/message.json"));
        List<Message> newMessage = new ArrayList<>();
        MessageComparator messageComparator = new MessageComparator();

        for (JsonNode message : messageNode) {
            if (message.path("from").asInt() == from && message.path("to").asInt() == to) {
                newMessage.add(get(message));
            } else if (is2Way && message.path("from").asInt() == to && message.path("to").asInt() == from) {
                newMessage.add(get(message));
            }
        }
        newMessage.sort(messageComparator);

        return newMessage;
    }

    public static List<Message> get(int user1, int user2) throws MalformedURLException, IOException {
        return get(user1, user2, true);
    }

    public static Message get(JsonNode message) {
        Message newMessage = new Message();

        newMessage.content = message.path("content").textValue();
        newMessage.from = message.path("from").asInt();
        newMessage.time = Timestamp.valueOf(message.path("time").textValue());
        newMessage.to = message.path("to").asInt();

        return newMessage;
    }

    public static void set(Message message) throws MalformedURLException, IOException {
        JsonFactory messageFactory = new JsonFactory();
        JsonNodeFactory messageNodeFactory = new JsonNodeFactory(false);
        JsonGenerator messageGenerator = messageFactory.createGenerator(
                new FileWriter(new File(System.getProperty("user.dir") + "/WebContent/DAO/message.json")));
        ObjectMapper messageMapper = new ObjectMapper();
        ObjectNode newMessageNode = messageNodeFactory.objectNode();
        ArrayNode messageNode = (ArrayNode) messageMapper
                .readTree(new URL("http://localhost:8086/HouseExchangeManager/DAO/message.json"));

        newMessageNode.put("from", message.from);
        newMessageNode.put("to", message.to);
        newMessageNode.put("content", message.content);
        newMessageNode.put("time", message.time.toString());
        messageNode.add(newMessageNode);
        messageMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        messageMapper.writeTree(messageGenerator, messageNode);
    }

    public void set() throws MalformedURLException, IOException {
        set(this);
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
