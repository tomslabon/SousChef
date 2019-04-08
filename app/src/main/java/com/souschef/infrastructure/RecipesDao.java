package com.souschef.infrastructure;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.souschef.recipes.domain.Recipe;

import java.util.List;

@Dao
public interface RecipesDao {

    @Query("SELECT * FROM Recipe")
    LiveData<List<Recipe>> getAllRecipes();

    @Insert
    void insert(Recipe recipe);

    @Query("SELECT * FROM Recipe WHERE dishName = :dishName")
    List<Recipe> findByDishName(String dishName);

    @Query("DELETE FROM Recipe")
    void cleanData();

    @Query("SELECT * FROM Recipe WHERE id = :id")
    Recipe load(int id);
}
