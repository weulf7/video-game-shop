package org.fasttrackit.videogameshop.persistance;

import org.fasttrackit.videogameshop.domain.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewRepository extends JpaRepository<ProductReview,Long> {

    Page<ProductReview>findByProductId(long productId, Pageable pageable);
}
