package org.fasttrackit.videogameshop.service;

import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.exception.ResourceNotFoundException;
import org.fasttrackit.videogameshop.persistance.ProductRepository;
import org.fasttrackit.videogameshop.transfer.product.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;


    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(SaveProductRequest request) {
        LOGGER.info("Creating product: {}", request);

        Product product = new Product();
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        return productRepository.save(product);

    }

    public Product getProduct(long id){
        LOGGER.info("Retrieving product: {}",id);

        return productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product with id "+id+" does not exist"));
    }
}
