package zap.dev.favourite;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    public static final String AUTHORITY = "zap.dev.movieandtvshow.provider";
    public static final Uri URI_MOVIE = Uri.parse("content://" + AUTHORITY + "/" + MovieFavourite.TABLE_NAME);
    private static final int FAVOURITE = 1;

    private Adapter movieAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        // initialize recylerview
        recyclerView = findViewById(R.id.recyclerview_film);
        progressBar = findViewById(R.id.progressBar_movie);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        movieAdapter = new Adapter();
        movieAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(movieAdapter);

        LoaderManager.getInstance(this).initLoader(FAVOURITE,null, mLoaderCallbacks);
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                @NonNull
                @Override
                public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
                    switch (id){
                        case FAVOURITE:
                            return new CursorLoader(
                                    getApplicationContext(),
                                    URI_MOVIE,
                                    null,
                                    null,
                                    null,
                                    null);

                        default:
                            throw new IllegalArgumentException();
                    }
                }

                @Override
                public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
                    switch (loader.getId()){
                        case FAVOURITE:
                            movieAdapter.setData(getApplicationContext(), data);
                            progressBar.setVisibility(View.GONE);
                            break;
                    }
                }

                @Override
                public void onLoaderReset(@NonNull Loader<Cursor> loader) {
                    switch (loader.getId()){
                        case FAVOURITE:
                            movieAdapter.setData(getApplicationContext(), null);
                            progressBar.setVisibility(View.GONE);
                            break;
                    }
                }
            };
}
