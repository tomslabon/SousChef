package com.souschef.recipes.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.souschef.R;
import com.souschef.databinding.ActivityRecipesListBinding;
import com.souschef.infrastructure.LocalDatabase;
import com.souschef.recipes.domain.Recipe;

import java.util.List;

public class RecipesListActivity extends AppCompatActivity {

    private static final String TAG = RecipesListActivity.class.getSimpleName();
    private RecipesAdapter recipesAdapter;

    private ActivityRecipesListBinding recipesListBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        recipesListBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipes_list);
        setUpDataBindingToDishList();

    }


    private void setUpDataBindingToDishList() {
        recipesAdapter = new RecipesAdapter(this);
        // Recycler list dishes

        recipesListBinding.dishList.setLayoutManager(new LinearLayoutManager(this));
        recipesListBinding.dishList.setAdapter(recipesAdapter);
        recipesListBinding.dishList.setHasFixedSize(true);

        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getAllRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                Log.d(TAG, "Recipies Adapter -> setRecipies");
                recipesAdapter.setRecipies(recipes);
            }
        });
        recipesListBinding.fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LocalDatabase.getInstance(getApplicationContext()).recipesDao().insert(Recipe.builder().dishName("Spaghetti carbonara a'la Romek").build());
            }
        });
    }

}
