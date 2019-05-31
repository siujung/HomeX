package bean;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		Message message = new Message(new Timestamp(System.currentTimeMillis()));
		message.setFrom(10000);
		message.setTo(10001);
		message.setContent("ok");
		Message.set(message);
		List<Message> messages = Message.get(10000, 10001);
		System.out.println(messages);
	}

}
