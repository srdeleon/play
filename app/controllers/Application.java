package controllers;

import models.ExpeditedOrders;
import models.User;
import play.data.Form;
import play.libs.Comet;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.*;
import static play.data.Form.form;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("live stream"));
    }

    public static WebSocket<String> liveUpdate() {
        return new WebSocket<String>() {

            public void onReady(final WebSocket.In<String> in, final WebSocket.Out<String> out) {
                in.onMessage(new Callback<String>(){
                    public void invoke(String event) {
                        ExpeditedOrders.notifyOthers(out, event + " is being processed");
                    }
                });
                
                in.onClose(new Callback0() {
                    public void invoke() {
                        ExpeditedOrders.unregister(out);
                    }
                });

                ExpeditedOrders.register(out);
            }
            
        };
    }
    
    public static WebSocket<String> hello() {
        return new WebSocket<String>() {

            public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
                in.onMessage(new Callback<String>() {
                    public void invoke(String event) {
                        System.out.println(event);
                    }
                });
                
                in.onClose(new Callback0() {
                    public void invoke() {
                        System.out.println("Disconnected");
                    }
                });
                
                out.write("Hello client!");
            }
            
        };
    }
    
    public static class Login {
        
        public String email;
        public String password;
    }
    
    public static Result login() {
        return ok(login.render(form(Login.class)));
    }
    
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        String email = loginForm.get().email;
        String password = loginForm.get().password;
        if (User.authenticate(email, password) == null) {
            return forbidden("invalid password");
        }
        session().clear();
        session("email", email);
        return redirect(routes.Products.index());
    }

}