package com.deloitte.fi.estore.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.fi.estore.model.Cart;
import com.deloitte.fi.estore.model.Product;
import com.deloitte.fi.estore.repository.Repository;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {
	
	@Autowired
	Cart cart;
	@Autowired
	Repository repos;
	
	ArrayList<Product> prod;
	
	/*public CartController(Cart cart) {
		this.cart = cart;
	}*/

	
	@RequestMapping(method =RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Cart createCart(@RequestBody Product product) {
		
		prod=new ArrayList<Product>();
		prod.add(product);
		cart.setProdArray(prod);
		return  repos.save(cart);
	}
	
	@RequestMapping(value="/{id}",method= RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Optional<Cart> addUpdate(@PathVariable String id ,@RequestBody Product product) {
		
	   return repos.findById(id).map(cart->{
		   boolean flag=false;
		   prod=cart.getProdArray();
		   for(Product prodobj :prod) {
			   if(prodobj.getId().equals(product.getId())){
				   prodobj.setQuantity(prodobj.getQuantity()+1);
				   flag=true;
			   }
		   }
		   if(flag==false) {
		   prod.add(product);
		   }
		   cart.setProdArray(prod);
		   return repos.save(cart);
	   });
		
		
	}
	
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Optional<Cart> removeProduct(@PathVariable String id ,@RequestBody Product product) {
		
	   return repos.findById(id).map(cart->{
		   prod=cart.getProdArray();
		   Product prodtoberemoved = null;
		   for(Product prodobj :prod) {
			   if(prodobj.getId().equals(product.getId())){
				  
				   prodtoberemoved = prodobj;
				    }
		   }
		  
		   if(prodtoberemoved.getQuantity()==1) {
			   prod.remove(prodtoberemoved);
		   }else {
		   prodtoberemoved.setQuantity(prodtoberemoved.getQuantity()-1);
		   prod.add(prodtoberemoved);
		   }
		   cart.setProdArray(prod);
		   return repos.save(cart);
	   });
		
	}
	/*   @RequestMapping(value="/{id}",method= RequestMethod.DELETE, consumes=MediaType.APPLICATION_JSON_VALUE)
		public Optional<Cart> removeAll(@PathVariable String id ,@RequestBody Product product) {
			
		  return repos.findById(id).map(cart->{
			   prod=cart.getProdArray();
			   prod.clear();
			   cart.setProdArray(prod);
			   return repos.save(cart);
			   
		   });
		
	}*/
	
	
	   @RequestMapping(method= RequestMethod.GET)
		public Iterable<Cart> removeAll() {
		  return repos.findAll();
		
	}
	
	

}
