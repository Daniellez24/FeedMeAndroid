    package com.example.feedme.models.dao;

    import androidx.lifecycle.LiveData;
    import androidx.room.Dao;
    import androidx.room.Delete;
    import androidx.room.Insert;
    import androidx.room.OnConflictStrategy;
    import androidx.room.Query;

    import com.example.feedme.models.Recipe;

    import java.util.List;

    @Dao
    public interface RecipeDao {
        @Query("select * from Recipe")
        LiveData<List<Recipe>> getAll();

        @Query("select * from Recipe where userId = :id")
        LiveData<List<Recipe>> getRecipeByUserId(String id);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertAll(Recipe... recipes);

        @Delete
        void delete(Recipe recipe);
    }
