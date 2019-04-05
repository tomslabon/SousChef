package com.souschef.infrastructure;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.souschef.recipes.domain.Ingredient;
import com.souschef.recipes.domain.Recipe;
import com.souschef.recipes.domain.Step;

@Database(entities = {Recipe.class, Step.class, Ingredient.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    private static final String LOG_TAG = LocalDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "Recipes";
    private static LocalDatabase sInstance;

    public static LocalDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new Recipe database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        LocalDatabase.class, LocalDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the Recipe database instance");
        return sInstance;
    }

    public abstract RecipesDao recipesDao();
}
