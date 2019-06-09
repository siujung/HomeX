package servlet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

@ServerEndpoint("/websocket")
public class ChatServer {

	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Vector<Session> room = new Vector<Session>();
	
	
	/**
	 * user connecton
	 * @param session, selectable 
	 */
	@OnOpen
	public void onOpen(Session session){
		room.addElement(session);
	}
	
	/**
	 * receive message from user
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onMessage(String message,Session session){

		//parse the message sent by user, and convert them to JSONobject
		JSONObject obj = JSONObject.fromObject(message);
		//add sending time into JSONobject
		obj.put("date", df.format(new Date()));
		//go through all the messages in chat room
		for(Session se : room){
			//set if the message is sent by him/herself
			obj.put("isSelf", se.equals(session));
			//send message to remote user 
			se.getAsyncRemote().sendText(obj.toString());
		}
	}
	
	/**
	 * user disconnection
	 * @param session
	 */
	@OnClose
	public void onClose(Session session){
		room.remove(session);
	}
	
	/**
	 * User connect error
	 * @param t
	 */
	@OnError
	public void onError(Throwable t){
		
	}
}
