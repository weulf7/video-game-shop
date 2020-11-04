package org.fasttrackit.videogameshop.transfer.productReview;

public class ProductReviewResponse {

    private String content;

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
                ", id=" + id +
                '}';
    }
}
