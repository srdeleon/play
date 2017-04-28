package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Product;
import models.Tag;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.products.list;
import views.html.products.details;

@Catch
public class Products extends Controller {
	
	private static final Form<Product> productForm = Form.form(Product.class);
	
	public static Result index() {
		return redirect(routes.Products.list(0));
	}
	
	public static Result list(Integer page) {
		List<Product> products = Product.findAll();
		return ok(list.render(products));
	}
	
	public static Result newProduct() {
		return ok(details.render(productForm));
	}
	
	public static Result details(Product product) {
		Form<Product> filledForm = productForm.fill(product);
		return ok(details.render(filledForm));
	}
	
	public static Result save() {
		Form<Product> boundForm = productForm.bindFromRequest();
		if(boundForm.hasErrors()) {
			return ok(details.render(boundForm));
		}
		Product product = boundForm.get();
		
		List<Tag> tags = new ArrayList<Tag>();
		for (Tag tag : product.tags) {
			if (tag.id != null) {
				tags.add(Tag.findById(tag.id));
			}
		}
		product.tags = tags;
		product.save();
		flash("success", String.format("Successfully added product %s", product));
		
		return redirect(routes.Products.list(1));
	}
	
	public static Result delete(String ean) {
		final Product product = Product.findByEan(ean);
		if (product == null) {
			return notFound(String.format("Product %s does not exists.", ean));
		}
		
		Product.remove(product);
		return redirect(routes.Products.list(1));
	}

}
