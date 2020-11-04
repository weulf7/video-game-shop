package org.fasttrackit.videogameshop.transfer.productReview;

public class SaveProductReviewRequest {

    private String content;

    private long productId;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SaveProductReviewRequest{" +
                "content='" + content + '\'' +
                ", productId=" + productId +
                '}';
    }
}
