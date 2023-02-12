package com.example.feedme.models;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.feedme.MyApplication;
import com.example.feedme.models.Recipe;
import com.example.feedme.models.dao.RecipeDao;
import com.example.feedme.models.dao.UsersRecipeDao;


@Database(entities = {Recipe.class}, version = 80)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract RecipeDao recipeDao();
    public abstract UsersRecipeDao usersRecipeDao();
}
public class AppLocalDb{
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb(){}
}