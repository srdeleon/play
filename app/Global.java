import play.*;
import play.libs.*;
import java.util.*;
import models.*;
import play.data.format.Formatters;
import play.data.format.Formatters.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import utils.*;

public class Global extends GlobalSettings {
    
    public void onStart(Application app) {
        // Register our DateFormater
        Formatters.register(Date.class, 
                     new SimpleFormatter<Date>() {
        
         private final static String PATTERN = "dd-MM-yyyy";
                
         public Date parse(String text, Locale locale) 
            throws java.text.ParseException {     
           if(text == null || text.trim().isEmpty()) {
             return null;
           }
           SimpleDateFormat sdf = 
             new SimpleDateFormat(PATTERN, locale);    
           sdf.setLenient(false);  
           return sdf.parse(text);                     
         }
                
         public String print(Date value, Locale locale){
           if(value == null) {
            return "";
           }
           return new SimpleDateFormat(PATTERN, locale)
                      .format(value);                   
         }
                
        });
      Formatters.register(Date.class, new AnnotationDateFormatter());
        
    }
}