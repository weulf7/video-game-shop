package org.fasttrackit.videogameshop.cart;

import org.fasttrackit.videogameshop.domain.Cart;
import org.fasttrackit.videogameshop.domain.User;
import org.fasttrackit.videogameshop.service.CartService;
import org.fasttrackit.videogameshop.steps.ProductTestSteps;
import org.fasttrackit.videogameshop.steps.UserTestSteps;
import org.fasttrackit.videogameshop.transfer.cart.AddProductToCartRequest;
import org.fasttrackit.videogameshop.transfer.cart.CartResponse;
import org.fasttrackit.videogameshop.transfer.cart.ProductInCart;
import org.fasttrackit.videogameshop.transfer.product.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
public class CartServiceIntegrationTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserTestSteps userTestSteps;

    @Autowired
    private ProductTestSteps productTestSteps;

    @Test
    public void addProductToCart_whenValidRequest_thenProductIsAddedToCart(){
        User user = userTestSteps.createUser();
        ProductResponse product = productTestSteps.createProduct();

        AddProductToCartRequest request = new AddProductToCartRequest();
        request.setUserId(user.getId());
        request.setProductId(product.getId());

        cartService.addProductToCart(request);

        CartResponse cart = cartService.getCart(request.getUserId());

        assertThat(cart,notNullValue());
        assertThat(cart.getId(),is(request.getUserId()));

        assertThat(cart.getProducts(),notNullValue());
        assertThat(cart.getProducts(),hasSize(1));

        ProductInCart productInCart = cart.getProducts().iterator().next();

        assertThat(productInCart,notNullValue());
        assertThat(productInCart.getId(),is(product.getId()));
        assertThat(productInCart.getName(),is(product.getName()));
        assertThat(productInCart.getImageUrl(),is(product.getImageUrl()));
        assertThat(productInCart.getPrice(),is(product.getPrice()));



    }
}
