package org.fasttrackit.videogameshop.product;

import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.exception.ResourceNotFoundException;
import org.fasttrackit.videogameshop.service.ProductService;
import org.fasttrackit.videogameshop.steps.ProductTestSteps;
import org.fasttrackit.videogameshop.transfer.product.ProductResponse;
import org.fasttrackit.videogameshop.transfer.product.SaveProductRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTestSteps productTestSteps;

    @Test
    public void createProduct_whenValidRequest_thenSaveCreatedProduct(){
       SaveProductRequest request = new SaveProductRequest();

       request.setName("Red Dead Redemption");
       request.setDescription("Western,Action,Shooter,Open World");
       request.setPrice(300.50);
       request.setQuantity(1000);

        ProductResponse product = productService.createProduct(request);

        assertThat(product,notNullValue());
        assertThat(product.getId(),notNullValue());
        assertThat(product.getId(),greaterThan(0L));
        assertThat(product.getName(),is(request.getName()));
        assertThat(product.getDescription(),is(request.getDescription()));
        assertThat(product.getQuantity(),is(request.getQuantity()));
        assertThat(product.getPrice(),is(request.getPrice()));
        assertThat(product.getImageUrl(),is(request.getImageUrl()));
    }

    @Test
    public void getProduct_whenExistingProduct_thenReturnProduct(){
        ProductResponse createdProduct = productTestSteps.createProduct();
        Product productResponse = productService.getProduct(createdProduct.getId());

        assertThat(productResponse,notNullValue());
        assertThat(productResponse.getId(),is(createdProduct.getId()));
        assertThat(productResponse.getDescription(),is(createdProduct.getDescription()));
        assertThat(productResponse.getName(),is(createdProduct.getName()));
        assertThat(productResponse.getQuantity(),is(createdProduct.getQuantity()));
        assertThat(productResponse.getPrice(),is(createdProduct.getPrice()));
        assertThat(productResponse.getImageUrl(),is(createdProduct.getImageUrl()));


    }

    @Test
    public void updateProduct_whenExistingProduct_thenSaveUpdatedProduct(){
        ProductResponse existingProduct = productTestSteps.createProduct();
        SaveProductRequest request=new SaveProductRequest();
        request.setName(existingProduct.getName());
        request.setDescription(existingProduct.getDescription());
        request.setQuantity(existingProduct.getQuantity());
        request.setPrice(existingProduct.getPrice());
        request.setImageUrl(existingProduct.getImageUrl());

        ProductResponse updatedProduct = productService.updateProduct(request, existingProduct.getId());

        assertThat(updatedProduct,notNullValue());
        assertThat(updatedProduct.getId(),is(existingProduct.getId()));
        assertThat(updatedProduct.getName(),is(request.getName()));
        assertThat(updatedProduct.getDescription(),is(request.getDescription()));
        assertThat(updatedProduct.getImageUrl(),is(request.getImageUrl()));
        assertThat(updatedProduct.getPrice(),is(request.getPrice()));
        assertThat(updatedProduct.getQuantity(),is(request.getQuantity()));
    }

    @Test
    public void deleteProduct_whenExistingProduct_thenProductIsDeleted(){
        ProductResponse product = productTestSteps.createProduct();

        productService.deleteProduct(product.getId());

        Assertions.assertThrows(ResourceNotFoundException.class,
                ()->productService.getProduct(product.getId()));

    }



}
