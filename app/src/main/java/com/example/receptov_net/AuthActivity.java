package com.example.receptov_net;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.data.FirebaseAuthRepository;
import com.example.domain.usecases.LoginUser;
import com.example.domain.usecases.RegisterUser;
import com.example.domain.repository.AuthRepository;

public class AuthActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        AuthRepository authRepository = new FirebaseAuthRepository();
        LoginUser loginUser = new LoginUser(authRepository);
        RegisterUser registerUser = new RegisterUser(authRepository);

        // Логика для авторизации
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Проверка на пустые поля
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(AuthActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Выполнение логина
            loginUser.execute(email, password, new AuthRepository.AuthCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(AuthActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    // Сохранение данных пользователя в SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);  // Сохранение email пользователя
                    editor.putBoolean("isLoggedIn", true);  // Сохранение флага авторизации
                    editor.apply();  // Применение изменений

                    // Переход на страницу рецептов
                    Intent intent = new Intent(AuthActivity.this, ProfileActivity.class);  // Переход на экран рецептов
                    startActivity(intent);
                    finish();  // Закрытие текущей активности
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(AuthActivity.this, "Login failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Логика для перехода на страницу регистрации
        registerButton.setOnClickListener(v -> {
            // Открытие страницы регистрации
            Intent intent = new Intent(AuthActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
