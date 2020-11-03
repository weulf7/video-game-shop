package org.fasttrackit.videogameshop.web;


import org.fasttrackit.videogameshop.domain.Cart;
import org.fasttrackit.videogameshop.service.CartService;
import org.fasttrackit.videogameshop.transfer.cart.AddProductToCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/carts")
@CrossOrigin
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping
    public ResponseEntity<Cart> addProductToCart(@RequestBody @Valid AddProductToCartRequest request){
        Cart cart = cartService.addProductToCart(request);

        return ResponseEntity.ok(cart);

    }
}
