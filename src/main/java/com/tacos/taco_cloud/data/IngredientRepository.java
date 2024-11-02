package com.tacos.taco_cloud.data;

import com.tacos.taco_cloud.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
