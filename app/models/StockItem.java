package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;



@Entity
public class StockItem extends Model {
    
    public static Finder<Long, StockItem> find = new Finder<>(Long.class, StockItem.class);
    
    @Id
    public Long id;
    
    @ManyToOne
    public Warehouse warehouse;
    
    @ManyToOne
    public Product product;
    
    public Long quantity;
    
    public String toString() {
        return String.format("%d %s", quantity, product);
    }

}
