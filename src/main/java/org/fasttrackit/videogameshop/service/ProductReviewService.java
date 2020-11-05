package org.fasttrackit.videogameshop.service;

import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.domain.ProductReview;
import org.fasttrackit.videogameshop.exception.ResourceNotFoundException;
import org.fasttrackit.videogameshop.persistance.ProductReviewRepository;
import org.fasttrackit.videogameshop.transfer.productReview.GetProductReviewsRequest;
import org.fasttrackit.videogameshop.transfer.productReview.ProductReviewResponse;
import org.fasttrackit.videogameshop.transfer.productReview.SaveProductReviewRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductReviewService {

    private final static Logger LOGGER= LoggerFactory.getLogger(ProductReviewService.class);

    private final ProductReviewRepository productReviewRepository;

    private final ProductService productService;

    @Autowired
    public ProductReviewService(ProductReviewRepository productReviewRepository, ProductService productService) {
        this.productReviewRepository = productReviewRepository;
        this.productService = productService;
    }


    @Transactional
    public ProductReviewResponse createProductReview(SaveProductReviewRequest reviewRequest){
        LOGGER.info("Creating product review: {}",reviewRequest);

        Product product = productService.getProduct(reviewRequest.getProductId());


        ProductReview productReview=new ProductReview();
        productReview.setContent(reviewRequest.getContent());


        productReview.setProduct(product);

        ProductReview savedReview = productReviewRepository.save(productReview);

       return mapProductReviewResponse(savedReview);


    }


    public ProductReview getProductReview(long id) {
        LOGGER.info("Retrieving product review: {}", id);

        return productReviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product review " + id + " does not exist"));

    }

    public ProductReviewResponse getProductReviewResponse(long id){
        LOGGER.info("Retrieving product review {}",id);

        ProductReview productReview = getProductReview(id);

        return mapProductReviewResponse(productReview);

    }

    public Page<ProductReviewResponse> getProductReviews(GetProductReviewsRequest reviewsRequest,Pageable pageable){
        LOGGER.info("Retrieving product reviews :{}",reviewsRequest);

        ProductReview exampleReview= new ProductReview();



        exampleReview.setContent(reviewsRequest.getPartialContent());


        Example<ProductReview>reviewExample=Example.of(exampleReview,
                ExampleMatcher.matchingAny()
                        .withMatcher("content", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()));

        Page<ProductReview> reviewPage = productReviewRepository.findAll(reviewExample, pageable);

        List<ProductReviewResponse>productReviews=new ArrayList<>();

        for (ProductReview productReview :reviewPage.getContent()){
            ProductReviewResponse response = mapProductReviewResponse(productReview);
            productReviews.add(response);
        }

        return new PageImpl<>(productReviews,pageable,reviewPage.getTotalElements());

    }



    private ProductReviewResponse mapProductReviewResponse(ProductReview review){
        ProductReviewResponse  response= new ProductReviewResponse();
        response.setContent(review.getContent());
        response.setId(review.getId());

        return response;
    }
}
