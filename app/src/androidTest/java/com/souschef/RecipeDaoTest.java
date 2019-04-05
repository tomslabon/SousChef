package com.souschef;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.souschef.infrastructure.LocalDatabase;
import com.souschef.infrastructure.RecipesDao;
import com.souschef.recipes.domain.Recipe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RecipeDaoTest {


    public static final String DEFAULT_DISH_NAME = "Dish name";
    public static final int DEFAULT_NUMBER_OF_RECIPES = 10;
    private LocalDatabase localDatabase;
    private RecipesDao recipiesDao;
    private Context appContext;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void setRecipeDao() {
        appContext = InstrumentationRegistry.getTargetContext();
        localDatabase = LocalDatabase.getInstance(InstrumentationRegistry.getTargetContext());
        recipiesDao = localDatabase.recipesDao();
        recipiesDao.cleanData();
    }

    @Test
    public void useAppContext() {
        assertEquals("com.souschef", appContext.getPackageName());
    }

    @Test
    public void shouldGetInstanceOfDatabase() {
        assertNotNull(localDatabase);
        assertNotNull(recipiesDao);
    }

    @Test
    public void shouldInsertRecipeToDatabase() {
        insertDishToDatabase(DEFAULT_DISH_NAME);

        List<Recipe> returnRecipes = recipiesDao.findByDishName(DEFAULT_DISH_NAME);

        assertNotEquals(0, returnRecipes.size());
    }

    @Test
    public void shouldReturnAllRecipesFromDatabase() throws InterruptedException {
        insertNumbersOfRecipies(DEFAULT_NUMBER_OF_RECIPES);

        final List<Recipe> recipes = LiveDataTestHelper.getValue(recipiesDao.getAllRecipes());

        assertNotNull(recipes);
        assertEquals(10,recipes.size());
    }

    @Test
    public void shouldCleanRecipesDatabase() throws InterruptedException {
        insertNumbersOfRecipies(DEFAULT_NUMBER_OF_RECIPES);

        recipiesDao.cleanData();

        final List<Recipe> recipes = LiveDataTestHelper.getValue(recipiesDao.getAllRecipes());

        assertEquals(0, recipes.size());
    }

    @Test
    public void shouldGetRecipe() {

    }

    private void insertNumbersOfRecipies(int number) {
        for (int i = 0; i < number; i++) {
            insertDishToDatabase(DEFAULT_DISH_NAME);
        }
    }

    private void insertDishToDatabase(String dishName) {
        Recipe recipe = Recipe.builder().dishName(dishName).build();

        recipiesDao.insert(recipe);
    }

}
