package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.CartItem;
import com.food.apnajalpaan.model.Order;
import com.food.apnajalpaan.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository repository;
    public Mono<CartItem> addToCart(Mono<CartItem> cartItemMono,String userId){
        return cartItemMono.flatMap(
                cartItem -> {
                    cartItem.setUserId(userId);
                    cartItem.setSubTotal(cartItem.getItems()*cartItem.getFood().getFoodCost());
                    return Mono.just(cartItem);
                }
        ).flatMap(repository::insert);
    }

    public Mono<CartItem> updateCart(String cartId, Mono<CartItem> cartItemMono){
        return repository.findById(cartId)
                .flatMap(res -> cartItemMono.flatMap(
                        x -> {
                            if(x.getItems()!=null) res.setItems(x.getItems());
                            if(x.getFood()!=null) res.setFood(x.getFood());
                            if(x.getSubTotal()!=null) res.setSubTotal(x.getSubTotal());
                            return Mono.just(res);
                        }))
                .flatMap(repository::save);
    }
    public Flux<CartItem> getAllByUserId(String userId){
        return repository.findAll().flatMap(
                cartItem -> {
                    if(cartItem.getUserId().equals(userId)) {
                        repository.deleteById(cartItem.getCartId());
                        return Mono.just(cartItem);
                    }
                    else return Mono.empty();
                }
        );
    }
    public Flux<CartItem> getAllCartItem(){
        return repository.findAll();
    }

    public Mono<Void> deleteByCartId(String cartId) {
        return repository.deleteById(cartId);
    }

    public Mono<Void> deleteAllByUserId(String userId){
        return repository.deleteAll(getAllByUserId(userId));
    }
}
