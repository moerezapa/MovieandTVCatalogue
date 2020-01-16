package zap.dev.movieandtvcatalogue.localdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import zap.dev.movieandtvcatalogue.model.MovieFavourite;
import zap.dev.movieandtvcatalogue.model.TVFavourite;

/*
    Note:
    @Database
    Create an abstraction for the data access object
 */

// update version if there's change
@Database(entities = {MovieFavourite.class, TVFavourite.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao daoAccess();
    public abstract TVDao tvDao();
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if (instance == null)
            instance = Room.databaseBuilder(context, AppDatabase.class, DBRepository.DB_NAME)
                    .allowMainThreadQueries()
                    .build();

        return instance;
    }
}
