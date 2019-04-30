package com.souschef.recipes.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.souschef.R;
import com.souschef.recipes.domain.Recipe;

import java.util.HashMap;
import java.util.Map;

public class RecipesListActivity extends AppCompatActivity {

    private static final String TAG = RecipesListActivity.class.getSimpleName();

    private RecipesAdapter recipesAdapter;

    private RecyclerView dishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        dishList = findViewById(R.id.dishList);
//        recipesListBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipes_list);
        setUpDataBindingToDishList();
    }


    private void setUpDataBindingToDishList() {
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        Query firstRecipes = mFirestore.collection("Recipes");
        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<Recipe>()
                .setQuery(firstRecipes, Recipe.class).setLifecycleOwner(this)
                .build();

        recipesAdapter = new RecipesAdapter(options,this);

        dishList.setLayoutManager(new LinearLayoutManager(this));
        dishList.setAdapter(recipesAdapter);
        dishList.setHasFixedSize(true);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
                Map<String, Object> map = new HashMap<>();
                map.put("id", mFirestore.collection("Recipes").getId());
                map.put("dishName", "Nazwa 9");
                mFirestore.collection("Recipes").document().set(map);
//                LocalDatabase.getInstance(getApplicationContext()).recipesDao().insert(Recipe.builder().dishName("Spaghetti carbonara a'la Romek").build());
            }
        });
    }

}
