package models;

import play.mvc.Results.Chunks;
import java.util.*;
import play.libs.*;
import scala.concurrent.duration.Duration;
import akka.actor.*;

import static java.util.concurrent.TimeUnit.*;

public class ExpeditedOrders extends UntypedActor {
	
	static List<Comet> comets = new ArrayList<Comet>();
	
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
	
	public static void registerChunkOut(Comet out) {
		ExpeditedOrders.comets.add(out);
	}

	@Override
	public void onReceive(Object message) throws Exception {
		Order order = (Order)message;
		
		for(Comet comet: comets) {
			comet.sendMessage(order.toString());
		}
		
	}

}
