package com.example.receptov_net;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.model.FavoriteRecipe;
import com.example.domain.model.Meal; // Importing Meal from the domain model
import com.example.domain.repository.FavoriteRecipeRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Meal> meals;
    private FavoriteRecipeRepository favoriteRecipeRepository; // Добавьте репозиторий для избранных рецептов

    public RecipeAdapter(List<Meal> meals, FavoriteRecipeRepository favoriteRecipeRepository) {
        this.meals = meals;
        this.favoriteRecipeRepository = favoriteRecipeRepository; // Инициализация репозитория
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Meal meal = meals.get(position);

        holder.recipeName.setText(meal.getName());
        holder.recipeInfo.setText("Category: " + meal.getCategory());
        holder.recipeDescription.setText(meal.getInstructions());

        // Загрузка изображения через Picasso
        Picasso.get().load(meal.getImageUrl()).into(holder.recipeImage);

        // Установка обработчика нажатия на кнопку "избранное"
        holder.favoriteButton.setOnClickListener(v -> {
            FavoriteRecipe favoriteRecipe = new FavoriteRecipe(
                    meal.getName(),
                    meal.getCategory(),
                    meal.getInstructions(),
                    meal.getImageUrl()
            );

            // Добавление в избранное
            new Thread(() -> {
                favoriteRecipeRepository.addFavoriteRecipe(favoriteRecipe);
            }).start();

            // Изменяем иконку кнопки
            holder.favoriteButton.setImageResource(R.drawable.ic_favorite_filled); // Измените на подходящий ресурс
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void updateData(List<Meal> newMeals) {
        this.meals.clear();
        this.meals.addAll(newMeals);
        notifyDataSetChanged();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView recipeImage;
        TextView recipeName;
        TextView recipeInfo;
        TextView recipeDescription;
        ImageButton favoriteButton;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeInfo = itemView.findViewById(R.id.recipe_info);
            recipeDescription = itemView.findViewById(R.id.recipe_description);
            favoriteButton = itemView.findViewById(R.id.favorite_button);
        }
    }
}
