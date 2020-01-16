package zap.dev.movieandtvcatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

import zap.dev.movieandtvcatalogue.localdb.AppDatabase;
import zap.dev.movieandtvcatalogue.localdb.MovieDao;
import zap.dev.movieandtvcatalogue.model.MovieFavourite;

public class MovieProvider extends ContentProvider {
    public static final String AUTHORITY = "zap.dev.movieandtvshow.provider";

    public static final Uri URI_MENU = Uri.parse("content://" + AUTHORITY + "/" + MovieFavourite.TABLE_NAME);

    private static final int MOVIEFAV_CODE = 1;
    private static final int MOVIEFAV_ITEM = 2;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private AppDatabase appDatabase;

    static {
        MATCHER.addURI(AUTHORITY, MovieFavourite.TABLE_NAME, MOVIEFAV_CODE);
        MATCHER.addURI(AUTHORITY, MovieFavourite.TABLE_NAME + "/*", MOVIEFAV_ITEM);
    }

    @Override
    public boolean onCreate() {
        appDatabase = AppDatabase.getInstance(getContext());
        return true;
    }

    /*
        Method query digunakan ketika ingin menjalankan query Select
        return cursor
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final int code = MATCHER.match(uri);
        if (code == MOVIEFAV_CODE || code == MOVIEFAV_ITEM) {
            final Context context = getContext();
            if (context == null)
                return null;

            MovieDao movieDao = appDatabase.getInstance(context).daoAccess();
            Cursor cursor = null;
            if (code == MOVIEFAV_CODE)
                cursor = movieDao.selectAllMovie();

            Objects.requireNonNull(cursor).setNotificationUri(context.getContentResolver(), uri);

            return cursor;
        }
            else
                throw new IllegalArgumentException("Failed because uri: " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) { return null; }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) { return null; }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) { return 0; }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) { return 0; }
}
