package org.fasttrackit.videogameshop.web;

import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.service.ProductService;
import org.fasttrackit.videogameshop.transfer.product.GetProductsRequest;
import org.fasttrackit.videogameshop.transfer.product.ProductResponse;
import org.fasttrackit.videogameshop.transfer.product.SaveProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid SaveProductRequest request) {
        ProductResponse product = productService.createProduct(request);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable long id) {
        ProductResponse productResponse = productService.getProductResponse(id);

        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(GetProductsRequest request, Pageable pageable){
        Page<ProductResponse> products = productService.getProducts(request, pageable);

        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody @Valid SaveProductRequest request, @PathVariable long id) {

        ProductResponse productResponse = productService.updateProduct(request, id);

        return ResponseEntity.ok(productResponse);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteProduct(@PathVariable long id){
        productService.deleteProduct(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
