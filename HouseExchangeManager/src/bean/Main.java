package bean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

	public static void main(String[] args) throws MalformedURLException, IOException {
		ObjectMapper userMapper = new ObjectMapper();
		JsonNode userNode = userMapper.readTree(new URL("http://localhost:8086/HouseExchangeManager/DAO/user.json"));
		User newUser = new User();

		for (JsonNode user : userNode) {
			if (user.path("id").asInt() == 1000) {
				System.out.println("ok");
				System.out.println(user.path("isAdministrator").booleanValue());
			}
		}
	}

}
