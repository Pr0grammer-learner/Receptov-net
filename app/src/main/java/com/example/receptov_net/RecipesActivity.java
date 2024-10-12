package com.example.receptov_net;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.FavoriteRecipeRepositoryImpl; // Импортируйте репозиторий
import com.example.data.RecipeRepositoryImpl;
import com.example.domain.model.Meal;
import com.example.domain.repository.FavoriteRecipeRepository;
import com.example.domain.repository.RecipeRepository;

import java.util.List;

public class RecipesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private RecipeRepository recipeRepository;
    private FavoriteRecipeRepository favoriteRecipeRepository; // Добавьте репозиторий избранных рецептов
    private ImageButton menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        // Инициализация RecyclerView
        recyclerView = findViewById(R.id.recipeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Инициализация репозиториев
        recipeRepository = new RecipeRepositoryImpl();
        favoriteRecipeRepository = new FavoriteRecipeRepositoryImpl(this); // Создайте экземпляр репозитория

        // Загружаем рецепты
        loadRecipes();

        // Логика кнопки перехода в меню
        menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecipesActivity.this, MenuActivity.class);
            startActivity(intent);
        });
    }

    private void loadRecipes() {
        recipeRepository.getMealsAsync(new RecipeRepository.MealCallback() {
            @Override
            public void onSuccess(List<Meal> meals) {
                // Обновляем адаптер, если данные успешно получены
                if (meals != null) {
                    recipeAdapter = new RecipeAdapter(meals, favoriteRecipeRepository); // Передайте репозиторий избранного
                    recyclerView.setAdapter(recipeAdapter);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("RecipesActivity", "Failed to load meals: " + t.getMessage());
            }
        });
    }
}
