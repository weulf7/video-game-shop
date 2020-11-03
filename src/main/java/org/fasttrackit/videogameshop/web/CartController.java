package org.fasttrackit.videogameshop.web;


import org.fasttrackit.videogameshop.domain.Cart;
import org.fasttrackit.videogameshop.service.CartService;
import org.fasttrackit.videogameshop.transfer.cart.AddProductToCartRequest;
import org.fasttrackit.videogameshop.transfer.cart.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Void> addProductToCart(@RequestBody @Valid AddProductToCartRequest request){
        cartService.addProductToCart(request);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable long userId){
        CartResponse cart = cartService.getCart(userId);

       return ResponseEntity.ok(cart);
    }
}
