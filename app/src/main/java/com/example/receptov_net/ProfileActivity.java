package com.example.receptov_net;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.data.FavoriteRecipeRepositoryImpl;
import com.example.data.UserRepository;
import com.example.domain.model.FavoriteRecipe;
import com.example.domain.repository.FavoriteRecipeRepository;
import com.example.domain.repository.UserRepositoryInterface;
import com.example.domain.repository.UserServiceInterface;
import com.example.domain.usecases.UserService;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private TextView emailTextView;
    private TextView usernameTextView;
    private TextView favoriteCountTextView; // TextView для отображения количества избранных рецептов
    private ImageButton menuButton;
    private UserServiceInterface userService;
    private UserRepositoryInterface userRepository;
    private FavoriteRecipeRepository favoriteRecipeRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d("ProfileActivity", "ProfileActivity Created"); // Add this log

        // Инициализация репозиториев
        userRepository = new UserRepository(this);
        userService = new UserService();
        favoriteRecipeRepository = new FavoriteRecipeRepositoryImpl(this); // Инициализация репозитория избранных рецептов

        // Получаем email пользователя из репозитория
        String userEmail = userRepository.getEmail();

        // Получаем логин пользователя из репозитория
        String username = userRepository.getUsername();

        // Если логина нет, генерируем случайный логин
        if (username == null) {
            username = userService.generateRandomUsername();
            userRepository.saveUsername(username); // Сохраняем сгенерированный логин
        }

        // Отображаем email и логин на странице профиля
        emailTextView = findViewById(R.id.email_text);
        emailTextView.setText(userEmail);

        usernameTextView = findViewById(R.id.username_text);
        usernameTextView.setText(username);

        // Инициализация++ TextView для количества избранных рецептов
        favoriteCountTextView = findViewById(R.id.favorite_recipes_text);

        // Получаем количество избранных рецептов асинхронно
        new Thread(() -> {
            List<FavoriteRecipe> favoriteRecipes = favoriteRecipeRepository.getAllFavoriteRecipes();
            int favoriteCount = favoriteRecipes.size(); // Получаем количество избранных рецептов

            // Обновляем UI на основном потоке
            runOnUiThread(() -> favoriteCountTextView.setText("Favorites: " + favoriteCount));
        }).start();

        // Логика кнопки перехода в меню
        menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MenuActivity.class);
            startActivity(intent);
        });
    }
}
