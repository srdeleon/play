package controllers;

import models.ExpeditedOrders;
import play.libs.Comet;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {
	
	public static Result index() {
		return ok(index.render("live stream"));
	}
	
	public static Result liveUpdate() {
		Comet comet = new Comet("parent.cometMessage") {
			public void onConnected() {
				ExpeditedOrders.registerChunkOut(this);
			}
		};		
		return ok(comet);
}

}