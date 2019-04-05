package com.souschef.recipes.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

@Entity(primaryKeys = {"recipeId", "ingredientId"},
        foreignKeys = {
                @ForeignKey(entity = Recipe.class,
                        parentColumns = "id",
                        childColumns = "recipeId"),
                @ForeignKey(entity = Ingredient.class,
                        parentColumns = "id",
                        childColumns = "ingredientId")
        })
public class RecipeIngredientJoin {
    public int recipeId;
    public int ingredientId;
}