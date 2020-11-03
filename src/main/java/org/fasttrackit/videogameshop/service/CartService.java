package org.fasttrackit.videogameshop.service;

import org.fasttrackit.videogameshop.domain.Cart;
import org.fasttrackit.videogameshop.domain.User;
import org.fasttrackit.videogameshop.exception.ResourceNotFoundException;
import org.fasttrackit.videogameshop.persistance.CartRepository;
import org.fasttrackit.videogameshop.transfer.cart.AddProductToCartRequest;
import org.fasttrackit.videogameshop.transfer.cart.CartResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CartService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;

    private final UserService userService;

    @Autowired
    public CartService(CartRepository cartRepository, UserService userService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
    }


    @Transactional
    public Cart addProductToCart(AddProductToCartRequest request) {
        LOGGER.info("Adding product to cart: {}", request);

        Cart cart = cartRepository.findById(request.getUserId()).orElse(new Cart());

        if (cart.getUser() == null) {
            User user = userService.getUser(request.getUserId());
            cart.setUser(user);
        }

        // TODO: add product to cart

        return cartRepository.save(cart);
    }



    @Transactional
    public CartResponse getCart(long userId){
        LOGGER.info("Retrieving cart: {}",userId);

        Cart cart = cartRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + userId + " does not exist"));

            CartResponse cartResponse= new CartResponse();

            cartResponse.setId(cart.getId());

            return cartResponse;

    }
}
