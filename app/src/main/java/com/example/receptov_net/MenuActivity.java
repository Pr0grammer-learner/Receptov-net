package com.example.receptov_net;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {

    private Button allRecipesButton;
    private Button favouriteButton;
    private Button createRecipeButton;
    private Button myAccountButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Инициализация кнопок
        allRecipesButton = findViewById(R.id.all_recipes_button);
        favouriteButton = findViewById(R.id.favourite_button);
        createRecipeButton = findViewById(R.id.create_recipe_button);
        myAccountButton = findViewById(R.id.my_account_button);
        backButton = findViewById(R.id.back_button);

        // Логика кнопки "All recipes"
        allRecipesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, RecipesActivity.class); // Замените на фактический класс
            startActivity(intent);
        });

        // Логика кнопки "Favourite"
        favouriteButton.setEnabled(false); // Делаем кнопку неактивной

        // Логика кнопки "Create recipe"
        createRecipeButton.setEnabled(false); // Делаем кнопку неактивной

        // Логика кнопки "My account"
        myAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ProfileActivity.class); // Переход на экран профиля
            startActivity(intent);
        });

        // Логика кнопки "Назад"
        backButton.setOnClickListener(v -> {
            finish(); // Завершает текущую активность и возвращает на предыдущую
        });
    }
}
