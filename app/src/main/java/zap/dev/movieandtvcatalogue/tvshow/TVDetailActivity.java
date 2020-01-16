package zap.dev.movieandtvcatalogue.tvshow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import zap.dev.movieandtvcatalogue.MainActivity;
import zap.dev.movieandtvcatalogue.R;
import zap.dev.movieandtvcatalogue.localdb.DBRepository;
import zap.dev.movieandtvcatalogue.model.TV;
import zap.dev.movieandtvcatalogue.model.TVFavourite;

public class TVDetailActivity extends AppCompatActivity {

    public static final String TV_DETAIL = "tv_detail";
    // set status for add or delete from movieFavourite
    public static final String TVFAV_DETAIL = "favourite_detail";
    public static final String fromTVFav = "tv_favourite";

    public static String asal;
    TV tvShow;

    private DBRepository DBRepository;
    private TVFavourite tvShowFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tvshowdetail_tbshowtab);

        showMovieDetail();
    }

    private void showMovieDetail() {
        TextView judultv, tanggalrilis, overview, vote_average, popularity;
        ImageView poster;
        judultv = findViewById(R.id.judultv);
        poster = findViewById(R.id.img_poster);
        tanggalrilis = findViewById(R.id.txt_firstairdate);
        overview = findViewById(R.id.txt_overview);
        vote_average = findViewById(R.id.txt_vote_average);
        popularity = findViewById(R.id.txt_popularity);

        tvShow = getIntent().getParcelableExtra(TV_DETAIL);
        tvShowFavourite = getIntent().getParcelableExtra(TVFAV_DETAIL);
        DBRepository = new DBRepository(getApplicationContext());

        if (tvShow != null) {
            getSupportActionBar().setTitle(tvShow.getName());

            judultv.setText(tvShow.getName());
            tanggalrilis.setText(tvShow.getFirst_air_date());
            Picasso.get()
                    .load(tvShow.getPoster_path())
                    .into(poster);
            popularity.setText(String.valueOf(tvShow.getPopularity()));
            if (tvShow.getOverview().isEmpty())
                overview.setText(R.string.no_overview);
                else
                    overview.setText(tvShow.getOverview());

            vote_average.setText(tvShow.getVote_average() + " %");
        }
            else if (tvShowFavourite != null) {
                getSupportActionBar().setTitle(tvShowFavourite.getName());

                judultv.setText(tvShowFavourite.getName());
                tanggalrilis.setText(tvShowFavourite.getRelease_date());
                Picasso.get()
                        .load(tvShowFavourite.getPoster_path())
                        .into(poster);
                popularity.setText(String.valueOf(tvShowFavourite.getPopularity()));
                if (tvShowFavourite.getOverview().isEmpty())
                    overview.setText(R.string.no_overview);
                    else
                        overview.setText(tvShowFavourite.getOverview());

                vote_average.setText(tvShowFavourite.getVote_average() + " %");
            }
    }

    // add button to action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (tvShow != null)
            getMenuInflater().inflate(R.menu.addfav_menu, menu); // add fav button
            else if (tvShowFavourite != null)
                getMenuInflater().inflate(R.menu.deletefav_menu, menu); // add remove from fav button

        return super.onCreateOptionsMenu(menu);
    }

    // fungsi ketika menu diklik
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addFav:
                addToTVFavourite();
                break;
            case R.id.action_deleteFav:
                deleteFromFavourite();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToTVFavourite() {
        TVFavourite tvShowFavourite = new TVFavourite();
        tvShowFavourite.setId(tvShow.getId());
        tvShowFavourite.setOriginal_name(tvShow.getOriginal_name());
        tvShowFavourite.setName(tvShow.getName());
        tvShowFavourite.setOverview(tvShow.getOverview());
        tvShowFavourite.setPoster_path(tvShow.getPoster_path());
        tvShowFavourite.setBackdrop_path(tvShow.getBackdrop_path());
        tvShowFavourite.setPopularity(tvShow.getPopularity());
        tvShowFavourite.setRelease_date(tvShow.getFirst_air_date());
        tvShowFavourite.setVote_average(tvShow.getVote_average());
        tvShowFavourite.setVote_count(tvShow.getVote_count());

        DBRepository.insertTVFavourite(tvShowFavourite,
                getApplicationContext());
    }

    private void deleteFromFavourite() {
        DBRepository.deleteTVFromFavourites(tvShowFavourite);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        if (tvShowFavourite != null)
            intent.putExtra(MainActivity.fromTVDetail, fromTVFav);
            else if (tvShow != null)
                intent.putExtra(MainActivity.fromTVDetail, MainActivity.fromTVDetail);

        startActivity(intent);
        finish();
    }
}
