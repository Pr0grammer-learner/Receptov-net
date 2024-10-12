package com.example.data;

import android.content.Context;

import com.example.data.model.FavoriteRecipeEntity;
import com.example.domain.model.FavoriteRecipe;
import com.example.domain.repository.FavoriteRecipeRepository;

import java.util.ArrayList;
import java.util.List;

public class FavoriteRecipeRepositoryImpl implements FavoriteRecipeRepository {
    private FavoriteRecipeDao favoriteRecipeDao;

    public FavoriteRecipeRepositoryImpl(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        favoriteRecipeDao = db.favoriteRecipeDao();
    }

    @Override
    public void addFavoriteRecipe(FavoriteRecipe recipe) {
        // Convert FavoriteRecipe to FavoriteRecipeEntity and insert it into the database
        FavoriteRecipeEntity entity = new FavoriteRecipeEntity(
                recipe.getName(),
                recipe.getCategory(),
                recipe.getInstructions(),
                recipe.getImageUrl()
        );
        new Thread(() -> favoriteRecipeDao.insert(entity)).start();
    }

    @Override
    public List<FavoriteRecipe> getAllFavoriteRecipes() {
        List<FavoriteRecipeEntity> entities = favoriteRecipeDao.getAllFavoriteRecipes();
        List<FavoriteRecipe> recipes = new ArrayList<>();
        for (FavoriteRecipeEntity entity : entities) {
            recipes.add(mapToDomain(entity)); // Map entity to domain model
        }
        return recipes;
    }

    @Override
    public void deleteFavoriteRecipe(int recipeId) {
        new Thread(() -> favoriteRecipeDao.deleteFavoriteRecipe(recipeId)).start();
    }

    // Method to map FavoriteRecipeEntity to FavoriteRecipe
    private FavoriteRecipe mapToDomain(FavoriteRecipeEntity entity) {
        return new FavoriteRecipe(
                entity.getName(),
                entity.getCategory(),
                entity.getInstructions(),
                entity.getImageUrl()
        );
    }
}
