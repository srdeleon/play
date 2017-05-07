package models;

import play.mvc.WebSocket;

import java.util.*;
import play.libs.*;
import scala.concurrent.duration.Duration;
import akka.actor.*;

import static java.util.concurrent.TimeUnit.*;

public class ExpeditedOrders extends UntypedActor {
	
	static List<WebSocket.Out<String>> members = new ArrayList<WebSocket.Out<String>>();
	
	static ActorRef defaultActor = Akka.system().actorOf(new Props(ExpeditedOrders.class));
	
	static {
		Akka.system().scheduler().schedule(
				Duration.create(4, SECONDS), 
				Duration.create(5, SECONDS),
				defaultActor, 
				new Order(),
				Akka.system().dispatcher(),
				null
				);
	}
	
	public static void register(WebSocket.Out<String> out) {
		members.add(out);
	}
	
	public static void unregister(WebSocket.Out<String> out) {
	    members.remove(out);
	}
	
	public static void notifyOthers(WebSocket.Out<String> me, String event) {
	    for(WebSocket.Out<String> out : members) {
	        if(!out.equals(me)) {
	            out.write(event);
	        }
	    }
	}

	public static void notifyAll(String event) {
	    for(WebSocket.Out<String> out : members) {
	        out.write(event);
	    }
	}
	
	public void onReceive(Object message) throws Exception{
	    Order order = (Order)message;
	    notifyAll(order.toString());
	}

}
