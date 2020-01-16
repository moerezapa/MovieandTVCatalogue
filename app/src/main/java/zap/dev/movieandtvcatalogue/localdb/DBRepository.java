package zap.dev.movieandtvcatalogue.localdb;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import zap.dev.movieandtvcatalogue.R;
import zap.dev.movieandtvcatalogue.model.MovieFavourite;
import zap.dev.movieandtvcatalogue.model.TVFavourite;

public class DBRepository {
    public static final String DB_NAME = "favourite_db";
    private AppDatabase appDatabase;
    private Context context;

    public DBRepository(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
        this.context = context;
    }

    public void insertFavourite(final MovieFavourite movieFav, final Context context) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.daoAccess().insertFavourite(movieFav);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(context, R.string.add_to_favourite, Toast.LENGTH_LONG).show();
            }
        }.execute();
    }

    public void insertTVFavourite(final TVFavourite tvFavourite, final Context context) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.tvDao().insertTVFavourite(tvFavourite);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(context, R.string.add_to_favourite, Toast.LENGTH_LONG).show();
            }
        }.execute();
    }

    public void deleteFromFavourites(final MovieFavourite movie) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.daoAccess().deleteFavourite(movie);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(context, R.string.delete_from_favourite, Toast.LENGTH_LONG).show();
            }
        }.execute();
    }
    public void deleteTVFromFavourites(final TVFavourite tvFavourite) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.tvDao().deleteTVFavourite(tvFavourite);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(context, R.string.delete_from_favourite, Toast.LENGTH_LONG).show();
            }
        }.execute();
    }
}
