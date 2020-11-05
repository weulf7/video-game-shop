package org.fasttrackit.videogameshop.transfer.productReview;

public class ProductReviewRequest {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ProductReviewRequest{" +
                "content='" + content + '\'' +
                '}';
    }
}
