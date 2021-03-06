package org.fasttrackit.videogameshop.service;

import org.fasttrackit.videogameshop.domain.Cart;
import org.fasttrackit.videogameshop.domain.Product;
import org.fasttrackit.videogameshop.domain.User;
import org.fasttrackit.videogameshop.exception.ResourceNotFoundException;
import org.fasttrackit.videogameshop.persistance.CartRepository;
import org.fasttrackit.videogameshop.transfer.cart.AddProductToCartRequest;
import org.fasttrackit.videogameshop.transfer.cart.CartResponse;
import org.fasttrackit.videogameshop.transfer.cart.ProductInCart;
import org.fasttrackit.videogameshop.transfer.product.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class CartService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;

    private final UserService userService;

    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, UserService userService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
    }


    @Transactional
    public void  addProductToCart(AddProductToCartRequest request) {
        LOGGER.info("Adding product to cart: {}", request);

        Cart cart = cartRepository.findById(request.getUserId()).orElse(new Cart());

        if (cart.getUser() == null) {
            User user = userService.getUser(request.getUserId());
            cart.setUser(user);
        }


        Product product = productService.getProduct(request.getProductId());


        cart.addProduct(product);

         cartRepository.save(cart);
    }



    @Transactional
    public CartResponse getCart(long userId){
        LOGGER.info("Retrieving cart: {}",userId);

        Cart cart = cartRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with id " + userId + " does not exist"));

            CartResponse cartResponse= new CartResponse();
            cartResponse.setId(cart.getId());

        Set<ProductInCart>products=new HashSet<>();

        for (Product product: cart.getProducts()){
            ProductInCart productInCart=new ProductInCart();
            productInCart.setId(product.getId());
            productInCart.setImageUrl(product.getImageUrl());
            productInCart.setName(product.getName());
            productInCart.setPrice(product.getPrice());

            products.add(productInCart);
        }

        cartResponse.setProducts(products);

            return cartResponse;

    }
}
