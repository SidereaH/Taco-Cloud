package com.tacos.taco_cloud;

import lombok.Data;
import javax.persistence.Entity;

@Data
@Entity
public class IngredientRef {
    private final String ingredient;
}