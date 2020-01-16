package zap.dev.movieandtvcatalogue.movie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import zap.dev.movieandtvcatalogue.R;
import zap.dev.movieandtvcatalogue.localdb.DBRepository;
import zap.dev.movieandtvcatalogue.model.Movie;
import zap.dev.movieandtvcatalogue.model.MovieFavourite;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String MOVIE_DETAIL = "movie_detail";
    // set status for add or delete from movieFavourite
    public static final String FAV_DETAIL = "favourite_detail";

    Movie movie;
    MovieFavourite movieFavourite;
    DBRepository DBRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviedetail_movietab);

        showMovieDetail();
    }

    private void showMovieDetail() {
        TextView judulfilm, popularity, tanggalrilis, overview, vote;
        ImageView poster;

        movie = getIntent().getParcelableExtra(MOVIE_DETAIL);
        movieFavourite = getIntent().getParcelableExtra(FAV_DETAIL);

        judulfilm = findViewById(R.id.judulfilm);
        poster = findViewById(R.id.img_poster);
        popularity = findViewById(R.id.txt_popularity);
        tanggalrilis = findViewById(R.id.txt_releasedate);
        overview = findViewById(R.id.txt_overview);
        vote = findViewById(R.id.txt_vote_average);

        if (movie != null) {
            getSupportActionBar().setTitle(movie.getTitle());
            judulfilm.setText(movie.getTitle());

            popularity.setText(String.valueOf(movie.getPopularity()));
            tanggalrilis.setText(movie.getReleaseDate());
            Picasso.get()
                    .load(movie.getPosterPath())
                    .into(poster);
            if (movie.getOverview().isEmpty())
                overview.setText(R.string.no_overview);
                else
                    overview.setText(movie.getOverview());

            vote.setText(movie.getVoteAverage() + " %");
        }
            else if (movieFavourite != null) {
                judulfilm.setText(movieFavourite.getTitle());
                getSupportActionBar().setTitle(movieFavourite.getTitle());
                judulfilm.setText(movieFavourite.getTitle());

                popularity.setText(String.valueOf(movieFavourite.getPopularity()));
                tanggalrilis.setText(movieFavourite.getRelease_date());
                Picasso.get()
                        .load(movieFavourite.getPoster_path())
                        .into(poster);
                if (movieFavourite.getOverview().isEmpty())
                    overview.setText(R.string.no_overview);
                    else
                        overview.setText(movieFavourite.getOverview());

                vote.setText(movieFavourite.getVote_average() + " %");
            }
    }

    // add button to action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (movie != null)
            getMenuInflater().inflate(R.menu.addfav_menu, menu); // add fav button
            else if (movieFavourite != null)
                getMenuInflater().inflate(R.menu.deletefav_menu, menu); // add remove from fav button

        return super.onCreateOptionsMenu(menu);
    }

    // fungsi ketika menu diklik
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addFav:
                addToMovieFavourite();
                break;
            case R.id.action_deleteFav:
                deleteFromFavourite();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToMovieFavourite() {
        MovieFavourite movieFavourite = new MovieFavourite();
        movieFavourite.setId(movie.getId());
        movieFavourite.setOriginal_title(movie.getOriginalTitle());
        movieFavourite.setTitle(movie.getTitle());
        movieFavourite.setOverview(movie.getOverview());
        movieFavourite.setPoster_path(movie.getPosterPath());
        movieFavourite.setBackdrop_path(movie.getBackdropPath());
        movieFavourite.setPopularity(movie.getPopularity());
        movieFavourite.setRelease_date(movie.getReleaseDate());
        movieFavourite.setVote_average(movie.getVoteAverage());
        movieFavourite.setVote_count(movie.getVoteCount());

        DBRepository = new DBRepository(getApplicationContext());
        DBRepository.insertFavourite(movieFavourite,
                getApplicationContext());
    }

    private void deleteFromFavourite() {
        DBRepository = new DBRepository(getApplicationContext());
        DBRepository.deleteFromFavourites(movieFavourite);
        finish();
    }
}
