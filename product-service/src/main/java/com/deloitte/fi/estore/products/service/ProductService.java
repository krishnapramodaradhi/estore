package com.deloitte.fi.estore.products.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deloitte.fi.estore.products.constants.Constants;
import com.deloitte.fi.estore.products.exception.InternalServerException;
import com.deloitte.fi.estore.products.model.Product;
import com.deloitte.fi.estore.products.repository.ProductsRepository;
import com.google.gson.Gson;

@Service
public class ProductService {

	private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	ProductsRepository productsRepository;
	
	@Autowired
	Gson gson;

	public Product create(Product product) {
		LOG.info("~~~~~ Inside create method of " + this.getClass().getSimpleName() + " ~~~~~");
		try {
			return productsRepository.save(product);
		} catch (Exception e) {
			LOG.error("~~~~~ Exception in create method of " + this.getClass().getSimpleName() + e.getMessage()
					+ " ~~~~~");
			throw new InternalServerException(e.getMessage());
		}
	}

	public Product findOne(String id) {
		LOG.info("~~~~~ Inside findOne method of " + this.getClass().getSimpleName() + " of id " + id + " ~~~~~");
		try {
			return productsRepository.findOne(id);
		} catch (Exception e) {
			LOG.error("~~~~~ Exception in findOne method of " + this.getClass().getSimpleName() + e.getMessage()
					+ " ~~~~~");
			throw new InternalServerException(e.getMessage());
		}
	}

	public Iterable<Product> findAll() {
		LOG.info("~~~~~ Inside findAll method of " + this.getClass().getSimpleName() + " ~~~~~");
		try {
			return productsRepository.findAll();
		} catch (Exception e) {
			LOG.error("~~~~~ Exception in findAll method of " + this.getClass().getSimpleName() + e.getMessage()
					+ " ~~~~~");
			throw new InternalServerException(e.getMessage());
		}
	}

	public Product update(String id, Product product) {
		LOG.info("~~~~~ Inside update method of " + this.getClass().getSimpleName() + " ~~~~~");
		try {
			Product p = productsRepository.findOne(id);
			if (null == p && null == product)
				throw new InternalServerException(Constants.NO_ID);
			if (null != product.getTitle())
				p.setTitle(product.getTitle());
			if (0 != product.getPrice())
				p.setPrice(product.getPrice());
			if (null != product.getImageUrl())
				p.setImageUrl(product.getImageUrl());
			if (null != product.getCategory())
				p.setCategory(product.getCategory());
			return productsRepository.save(p);
		} catch (Exception e) {
			LOG.error("~~~~~ Exception in update method of " + this.getClass().getSimpleName() + e.getMessage()
					+ " ~~~~~");
			throw new InternalServerException(e.getMessage());
		}
	}

	public String delete(String id) {
		LOG.info("~~~~~ Inside delete method of " + this.getClass().getSimpleName() + " of id " + id + " ~~~~~");
		try {
			productsRepository.deleteById(id);
			return gson.toJson(Constants.PRODUCT_DELETED);
		} catch (Exception e) {
			LOG.error("~~~~~ Exception in update method of " + this.getClass().getSimpleName() + e.getMessage()
					+ " ~~~~~");
			throw new InternalServerException(e.getMessage());
		}
	}
}
