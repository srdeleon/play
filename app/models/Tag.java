package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tag extends Model{

  public static List<Tag> tags = new LinkedList<Tag>();
  
  public static Finder<Long, Tag> find = new Finder<>(Long.class, Tag.class);

  public static Tag findById(Long id) {
    return find.byId(id);
  }
  
  @Id
  public Long id;
  @Constraints.Required
  public String name;
  
  @ManyToMany(mappedBy="tags")
  public List<Product> products;


  public Tag(){
    // Left empty
  }

  public Tag(Long id, String name, Collection<Product> products) {
    this.id = id;
    this.name = name;
    this.products = new LinkedList<Product>(products);
    for (Product product : products) {
      product.tags.add(this);
    }
  }
}
