package com.souschef.recipes.list;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.souschef.infrastructure.LocalDatabase;
import com.souschef.recipes.domain.Recipe;

import java.util.List;

import lombok.Getter;

public class RecipeViewModel extends AndroidViewModel {

    @Getter
    private LiveData<List<Recipe>> allRecipes;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        allRecipes = LocalDatabase.getInstance(application).recipesDao().getAllRecipes();
    }

}
