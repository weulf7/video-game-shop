package org.fasttrackit.videogameshop.service;

import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.domain.ProductReview;
import org.fasttrackit.videogameshop.persistance.ProductReviewRepository;
import org.fasttrackit.videogameshop.transfer.productReview.ProductReviewResponse;
import org.fasttrackit.videogameshop.transfer.productReview.SaveProductReviewRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    private ProductReviewResponse mapProductReviewResponse(ProductReview review){
        ProductReviewResponse  response= new ProductReviewResponse();
        response.setContent(review.getContent());
        response.setId(review.getId());

        return response;
    }
}
