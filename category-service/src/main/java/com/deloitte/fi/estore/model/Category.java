package com.deloitte.fi.estore.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="categories")
public class Category {
	@Id
	private String id;
	private String name;
	
	/*public Category(String id, String title) {
		super();
		this.id = id;
		this.title = title;
	}*/
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setTitle(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
}
