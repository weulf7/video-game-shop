package org.fasttrackit.videogameshop.transfer.productReview;

public class GetProductReviewsRequest {

    private String partialContent;


    public String getPartialContent() {
        return partialContent;
    }

    public void setPartialContent(String partialContent) {
        this.partialContent = partialContent;
    }


    @Override
    public String toString() {
        return "GetProductReviewsRequest{" +
                "partialContent='" + partialContent + '\'' +
                '}';
    }
}
