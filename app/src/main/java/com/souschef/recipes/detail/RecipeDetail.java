package com.souschef.recipes.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.souschef.R;
import com.souschef.infrastructure.LocalDatabase;
import com.souschef.recipes.domain.Recipe;

public class RecipeDetail extends AppCompatActivity {

    TextView recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeName = findViewById(R.id.recipeName);

        Intent goToDeatilsIntent = getIntent();

        if(goToDeatilsIntent.hasExtra("recipeId")) {
            Integer id = goToDeatilsIntent.getIntExtra("recipeId",0);
            Recipe recipe = LocalDatabase.getInstance(this).recipesDao().load(id);
            recipeName.setText(recipe.getDishName());
        }
    }
}
