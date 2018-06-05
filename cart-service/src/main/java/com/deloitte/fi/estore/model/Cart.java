package com.deloitte.fi.estore.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class Cart {
	@Id
	private String id;
	private ArrayList<Product> prodArray;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<Product> getProdArray() {
		return prodArray;
	}
	public void setProdArray(ArrayList<Product> prodArray) {
		this.prodArray = prodArray;
	}
	
		

}
