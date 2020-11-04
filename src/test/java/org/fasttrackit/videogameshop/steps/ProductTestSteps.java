package org.fasttrackit.videogameshop.steps;

import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.service.ProductService;
import org.fasttrackit.videogameshop.transfer.product.ProductResponse;
import org.fasttrackit.videogameshop.transfer.product.SaveProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class ProductTestSteps {

    @Autowired
    private ProductService productService;


    public ProductResponse createProduct(){
        SaveProductRequest request = new SaveProductRequest();
        request.setName("Test product");
        request.setDescription("Test description");
        request.setQuantity(100);
        request.setPrice(20.6);

        ProductResponse product = productService.createProduct(request);

        assertThat(product,notNullValue());
        assertThat(product.getId(),notNullValue());
        assertThat(product.getId(),greaterThan(0L));
        assertThat(product.getDescription(),is(request.getDescription()));
        assertThat(product.getName(),is(request.getName()));
        assertThat(product.getPrice(),is(request.getPrice()));
        assertThat(product.getQuantity(),is(request.getQuantity()));

        return product;
    }
}
