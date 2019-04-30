package com.souschef.recipes.list;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.souschef.R;
import com.souschef.recipes.detail.RecipeDetail;
import com.souschef.recipes.domain.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends FirestoreRecyclerAdapter<Recipe, RecipesAdapter.RecipeViewHolder> {

    private static final String TAG = RecipesAdapter.class.getSimpleName();

    private List<Recipe> recipiesList = new ArrayList<>();

    RecipesListActivity recipesListActivity;

    public RecipesAdapter(@NonNull FirestoreRecyclerOptions options, RecipesListActivity recipesListActivity) {
        super(options);
        this.recipesListActivity = recipesListActivity;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new RecipesAdapter.RecipeViewHolder(inflater.inflate(R.layout.activity_recipe_item, viewGroup, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull RecipeViewHolder holder, int position, @NonNull Recipe model) {
        holder.bind(model, position);
        Log.d(TAG, "onBindViewHolder");
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Recipe recipie, int position) {
            Log.d(TAG, "Bind new recipe");
            if (recipie != null) {
                TextView recipeName = itemView.findViewById(R.id.dishName);

                // Reference to an image file in Cloud Storage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                storageReference.child("cropped.jpg");

                ImageView imageView = itemView.findViewById(R.id.imageView);

                Glide.with(recipesListActivity)
                        .load(storageReference.child("cropped.jpg"))
                        .into(imageView);
                recipeName.setText(recipie.getDishName());
//                updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
//                priorityView = itemView.findViewById(R.id.priorityTextView);            }
            }
            itemView.setOnClickListener((v) -> {
                Intent goToDetailsIntent = new Intent(recipesListActivity, RecipeDetail.class);
                goToDetailsIntent.putExtra(Intent.EXTRA_TEXT, getSnapshots().getSnapshot(position).getReference().getPath());
                recipesListActivity.startActivity(goToDetailsIntent);
            });
        }
    }
}
