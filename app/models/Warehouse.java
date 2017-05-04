package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class Warehouse extends Model {
    
    public static Finder<Long, Warehouse> find = new Finder<>(Long.class, Warehouse.class);
    
    @Id
    public Long id;
    
    public String name;
    
    @OneToOne
    public Address address;
    
    @OneToMany(mappedBy="warehouse")
    public List<StockItem> stock = new ArrayList<>();
    
    @Override
    public String toString() {
        return name;
    }

}
