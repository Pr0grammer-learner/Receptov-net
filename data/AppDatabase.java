package com.example.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavoriteRecipe.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract FavoriteRecipeDao favoriteRecipeDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "recipe_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
