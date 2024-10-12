package com.example.receptov_net;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button enterButton = findViewById(R.id.enter_button);
        Button recipeButton = findViewById(R.id.recipe_button);

        enterButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            startActivity(intent);
        });

        recipeButton.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

            if (isLoggedIn) {
                // Переход на экран рецептов
                Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
                startActivity(intent);
            } else {
                // Если пользователь не авторизован, перенаправляем его на экран входа
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                startActivity(intent);
            }
        });
    }
}
