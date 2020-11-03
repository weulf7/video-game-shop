package org.fasttrackit.videogameshop.persistance;

import org.fasttrackit.videogameshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
