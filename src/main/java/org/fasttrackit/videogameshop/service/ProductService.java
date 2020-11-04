package org.fasttrackit.videogameshop.service;

import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.exception.ResourceNotFoundException;
import org.fasttrackit.videogameshop.persistance.ProductRepository;
import org.fasttrackit.videogameshop.transfer.product.GetProductsRequest;
import org.fasttrackit.videogameshop.transfer.product.ProductResponse;
import org.fasttrackit.videogameshop.transfer.product.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;


    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(SaveProductRequest request) {
        LOGGER.info("Creating product: {}", request);

        Product product = new Product();
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());


        Product savedProduct = productRepository.save(product);

        return mapProductResponse(savedProduct);


    }


    public Product getProduct(long id) {
        LOGGER.info("Retrieving product: {}", id);

        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " does not exist"));

    }

    public ProductResponse getProductResponse(long id) {
        LOGGER.info("Retrieving product {}", id);

        Product product = getProduct(id);

        return mapProductResponse(product);


    }

    public Page<ProductResponse> getProducts(GetProductsRequest request, Pageable pageable) {

        LOGGER.info("Retrieving products: {}", request);

        Product exampleProduct = new Product();
        exampleProduct.setName(request.getPartialName());
        exampleProduct.setDescription(request.getPartialDescription());
        exampleProduct.setPrice(request.getPrice());
        exampleProduct.setQuantity(request.getMinQuantity());
        exampleProduct.setCarts(null);

        Example<Product> productExample = Example.of(exampleProduct,
                ExampleMatcher.matchingAny()
                        .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("description", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("price", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                        .withMatcher("quantity", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()));


        Page<Product> productsPage = productRepository.findAll(productExample, pageable);

        List<ProductResponse> products = new ArrayList<>();

        for (Product product : productsPage.getContent()) {

            ProductResponse productResponse = mapProductResponse(product);

            products.add(productResponse);
        }

        return new PageImpl<>(products, pageable, productsPage.getTotalElements());


    }


    public ProductResponse updateProduct(SaveProductRequest request, long id) {
        LOGGER.info("Updating Product {}: {}", id, request);

        Product product = getProduct(id);

        BeanUtils.copyProperties(request, product);

        Product save = productRepository.save(product);

        ProductResponse productResponse = mapProductResponse(save);


        return productResponse;


    }

    public void deleteProduct(long id){

        LOGGER.info("Deleting product {}",id);

        productRepository.deleteById(id);
    }

    private ProductResponse mapProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setDescription(product.getDescription());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());

        return productResponse;
    }
}
