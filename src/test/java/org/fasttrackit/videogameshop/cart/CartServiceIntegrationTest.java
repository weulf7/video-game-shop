package org.fasttrackit.videogameshop.cart;

import org.fasttrackit.videogameshop.domain.Cart;
import org.fasttrackit.videogameshop.domain.User;
import org.fasttrackit.videogameshop.service.CartService;
import org.fasttrackit.videogameshop.steps.UserTestSteps;
import org.fasttrackit.videogameshop.transfer.cart.AddProductToCartRequest;
import org.fasttrackit.videogameshop.transfer.cart.CartResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class CartServiceIntegrationTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserTestSteps userTestSteps;

    @Test
    public void addProductToCart_whenValidRequest_thenProductIsAddedToCart(){

        AddProductToCartRequest request = new AddProductToCartRequest();
        User user = userTestSteps.createUser();

        request.setUserId(user.getId());
        request.setProductId(1L);

        cartService.addProductToCart(request);

        CartResponse cart = cartService.getCart(request.getUserId());

        assertThat(cart,notNullValue());
        assertThat(cart.getId(),is(request.getUserId()));



    }
}
