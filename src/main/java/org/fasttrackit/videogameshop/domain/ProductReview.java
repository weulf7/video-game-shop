package org.fasttrackit.videogameshop.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "product_review")
@Entity
public class ProductReview {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductReview that = (ProductReview) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
