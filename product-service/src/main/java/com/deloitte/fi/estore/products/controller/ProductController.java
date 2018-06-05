package com.deloitte.fi.estore.products.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.fi.estore.products.constants.Constants;
import com.deloitte.fi.estore.products.exception.InternalServerException;
import com.deloitte.fi.estore.products.model.Product;
import com.deloitte.fi.estore.products.service.ProductService;

@RestController
@RequestMapping(Constants.PRODUCT_ROUTE)
@CrossOrigin("*")
public class ProductController {

	private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ProductService productService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public Product create(@RequestBody Product product) {
		LOG.info("~~~~~ Inside create method of " + this.getClass().getSimpleName() + " ~~~~~");
		try {
			if (null == product)
				throw new InternalServerException(Constants.NO_FORM);
			return productService.create(product);
		} catch (Exception e) {
			LOG.error("~~~~~ Exception in create method of " + this.getClass().getSimpleName() + e.getMessage()
					+ " ~~~~~");
			throw new InternalServerException(e.getMessage());
		}
	}

	@GetMapping(value = Constants.PRODUCT_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public Product findOne(@PathVariable String id) {
		LOG.info("~~~~~ Inside findOne method of " + this.getClass().getSimpleName() + " of id: " + id + " ~~~~~");
		try {
			if (null == id)
				throw new InternalServerException(Constants.NO_ID);
			return productService.findOne(id);
		} catch (Exception e) {
			LOG.error("~~~~~ Exception in findOne method of " + this.getClass().getSimpleName() + e.getMessage()
					+ " ~~~~~");
			throw new InternalServerException(e.getMessage());
		}
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Product> findAll() {
		LOG.info("~~~~~ Inside findAll method of " + this.getClass().getSimpleName() + " ~~~~~");
		try {
			return productService.findAll();
		} catch (Exception e) {
			LOG.error("~~~~~ Exception in findAll method of " + this.getClass().getSimpleName() + e.getMessage()
					+ " ~~~~~");
			throw new InternalServerException(e.getMessage());
		}
	}

	@PatchMapping(value = Constants.PRODUCT_ID, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Product update(@PathVariable String id, @RequestBody Product product) {
		LOG.info("~~~~~ Inside update method of " + this.getClass().getSimpleName() + " ~~~~~");
		try {
			if (null == id)
				throw new InternalServerException(Constants.NO_ID);
			if (null == product)
				throw new InternalServerException(Constants.NO_FORM);
			return productService.update(id, product);
		} catch (Exception e) {
			LOG.error("~~~~~ Exception in update method of " + this.getClass().getSimpleName() + e.getMessage()
					+ " ~~~~~");
			throw new InternalServerException(e.getMessage());
		}
	}

	@RequestMapping(value = Constants.PRODUCT_DELETE_ID, method = RequestMethod.DELETE)
	public String delete(@PathVariable String id) {
		LOG.info("~~~~~ Inside delete method of " + this.getClass().getSimpleName() + " ~~~~~");
		try {
			if (null == id)
				throw new InternalServerException(Constants.NO_ID);
			return productService.delete(id);
		} catch (Exception e) {
			LOG.error("~~~~~ Exception in update method of " + this.getClass().getSimpleName() + e.getMessage()
					+ " ~~~~~");
			throw new InternalServerException(e.getMessage());
		}
	}
}
