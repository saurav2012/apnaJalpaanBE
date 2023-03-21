package com.food.apnajalpaan.repository;

import com.food.apnajalpaan.model.Food;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface FoodRepository extends ReactiveMongoRepository<Food,String> {
    Flux<Food> findDistinctFoodByFoodNameContainingIgnoreCaseOrTypeContainingIgnoreCase(String FoodName,String Type);

    Flux<Food> findDistinctFoodByFoodNameContainingIgnoreCaseOrTypeContainingIgnoreCaseAndCategory(String searchQuery, String searchQuery1, String category);
}
