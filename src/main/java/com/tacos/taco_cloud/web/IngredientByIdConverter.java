package com.tacos.taco_cloud.web;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.tacos.taco_cloud.Ingredient;
import com.tacos.taco_cloud.data.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private IngredientRepository ingredientRepo;

    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepo.findById(id).orElse(null);
    }
}