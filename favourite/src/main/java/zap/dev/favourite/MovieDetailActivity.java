package zap.dev.favourite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String MOVIE_DETAIL = "movie_detail";
    private MovieFavourite movieFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        showMovieDetail();
    }

    private void showMovieDetail() {
        TextView judulfilm, popularity, tanggalrilis, overview, vote;
        ImageView poster;

        movieFavourite = getIntent().getParcelableExtra(MOVIE_DETAIL);

        judulfilm = findViewById(R.id.judulfilm);
        poster = findViewById(R.id.img_poster);
        popularity = findViewById(R.id.txt_popularity);
        tanggalrilis = findViewById(R.id.txt_releasedate);
        overview = findViewById(R.id.txt_overview);
        vote = findViewById(R.id.txt_vote_average);

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
