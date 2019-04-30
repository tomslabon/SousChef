package com.souschef;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.souschef.recipes.domain.Recipe;
import com.souschef.recipes.domain.Step;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class RecipeTestsIT {

    @Test
    public void shouldRunCorrectly() {

    }

    //    @Test
    public void shouldPersistRecipeOnFirestore() throws InterruptedException {

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

        for (int i = 0; i < 10; i++) {
            Recipe recipe = getRecipe();
            mFirestore.collection("Recipes").document().set(recipe).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("df", "Df");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(15000);
    }

    @Test
    public void shouldReturnImage() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child("cropped.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Assert.assertNotNull(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Assert.assertNotNull(e);
            }
        });
    }

    @Test
    public void readFromJson() throws IOException {
        Context ctx = InstrumentationRegistry.getTargetContext();
        InputStream is = ctx.getResources().getAssets().open("recipes.json");
        assertNotNull(is);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Type collectionType = new TypeToken<List<RecipeTest>>(){}.getType();
        List<RecipeTest> recipeTests = gson.fromJson(new InputStreamReader(is), collectionType);
        assertNotNull(recipeTests);


    }

    private Recipe getRecipe() {
        List<Step> steps = new ArrayList<>();
        steps.add(Step.builder().number(1).description("To make the dressing, combine fish sauce, coriander, ginger, chill, garlic, lime juice and caster sugar in a jar and shake to combine.").build());
        steps.add(Step.builder().number(2).description("Gently toss prawns and remaining salad ingredients together, divide among plates and spoon over dressing. Serve with lime wedges.").build());
        return Recipe.builder().dishName("Cooked Tiger Prawns & Peach Thai Salad").Steps(steps).timeToProduce(15).image("gs://souschef-583d5.appspot.com/pobrane.jfif").build();
    }

}
