package org.fasttrackit.videogameshop.web;

import org.fasttrackit.videogameshop.service.ProductReviewService;
import org.fasttrackit.videogameshop.transfer.productReview.GetProductReviewsRequest;
import org.fasttrackit.videogameshop.transfer.productReview.ProductReviewRequest;
import org.fasttrackit.videogameshop.transfer.productReview.ProductReviewResponse;
import org.fasttrackit.videogameshop.transfer.productReview.SaveProductReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product-reviews")
@CrossOrigin
public class ProductReviewController {

    private final ProductReviewService productReviewService;


    @Autowired
    public ProductReviewController(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }


    @PostMapping
    public ResponseEntity<ProductReviewResponse> createProductReview(@RequestBody @Valid SaveProductReviewRequest reviewRequest){
        ProductReviewResponse productReview = productReviewService.createProductReview(reviewRequest);

        return new ResponseEntity<>(productReview, HttpStatus.OK);

    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductReviewResponse> getProductReview(@PathVariable long id){
        ProductReviewResponse productReviewResponse = productReviewService.getProductReviewResponse(id);

        return ResponseEntity.ok(productReviewResponse);
    }

    @GetMapping
    public ResponseEntity<Page<ProductReviewResponse>> getProductReviews(GetProductReviewsRequest reviewsRequest, Pageable pageable){
        Page<ProductReviewResponse> productReviews = productReviewService.getProductReviews(reviewsRequest, pageable);

        return ResponseEntity.ok(productReviews);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductReviewResponse> updateProductReview(@RequestBody @Valid ProductReviewRequest reviewsRequest, @PathVariable long id){
        ProductReviewResponse reviewResponse = productReviewService.updateProductReview(reviewsRequest, id);

        return ResponseEntity.ok(reviewResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductReview(long id){
        productReviewService.deleteProductReview(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
