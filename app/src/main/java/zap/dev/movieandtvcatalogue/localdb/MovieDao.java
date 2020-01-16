package zap.dev.movieandtvcatalogue.localdb;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.List;

import zap.dev.movieandtvcatalogue.model.MovieFavourite;

/*
    Note:
    @Dao
    Create a data access object in the database using an interface class
 */
@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavourite(MovieFavourite movie);

    @Query("SELECT * FROM " + MovieFavourite.TABLE_NAME)
    List<MovieFavourite> getAllFavourite();

    @Delete
    void deleteFavourite(MovieFavourite movie);

    @Query("SELECT * FROM " + MovieFavourite.TABLE_NAME)
    Cursor selectAllMovie();
}
