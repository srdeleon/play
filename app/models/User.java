package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class User extends Model{
    
    @Id
    public Long Id;
    
    @Constraints.Required
    public String email;
    
    @Constraints.Required
    public String password;
    
    public User() {
        
    }
    
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    public static User authenticate(String email, String password) {
        
        if ("silvio@email.com".equals(email)
                        && "silvio@email.com".equals(password))
                return new User("silvio@email.com", "silvio@email.com");
             else
                 return null;
    }
    
    public static Finder<Long, User> finder = new Finder<Long, User>(Long.class, User.class);

}
