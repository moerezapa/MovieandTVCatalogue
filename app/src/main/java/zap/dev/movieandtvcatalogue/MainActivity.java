package zap.dev.movieandtvcatalogue;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import zap.dev.movieandtvcatalogue.favourite.FavouriteTabFragment;
import zap.dev.movieandtvcatalogue.movie.MovieTabFragment;
import zap.dev.movieandtvcatalogue.reminder.ReminderSettingActivity;
import zap.dev.movieandtvcatalogue.tvshow.TVDetailActivity;
import zap.dev.movieandtvcatalogue.tvshow.TVShowTabFragment;

public class MainActivity extends AppCompatActivity {

    public static final String myAPI = "12e9a4179676130733c36f4c28358b1f";
    public static final String bahasa = "in";
    public static final String language = "en";
    public static final String from = "MainActivity";
    public static final String fromTVDetail = "TVDetail";
    public static String language_used = Resources.getSystem().getConfiguration().locale.getLanguage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        String fromTV = getIntent().getStringExtra(fromTVDetail);
        String fromTVFav = getIntent().getStringExtra(TVDetailActivity.fromTVFav);

        if (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.navigation_movie);
            if (fromTV != null) {
                if (fromTVFav != null) { // navigate to favourite tab
                    loadFragment(new FavouriteTabFragment());
                    navView.setSelectedItemId(R.id.navigation_tv);
                }
                    else { // navigate to tv tab
                        loadFragment(new TVShowTabFragment());
                        navView.setSelectedItemId(R.id.navigation_favourite);
                    }
            }
        }
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    fragment = new MovieTabFragment();
                    break;
                case R.id.navigation_tv:
                    fragment = new TVShowTabFragment();
                    break;
                case R.id.navigation_favourite:
                    fragment = new FavouriteTabFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.btn_settingreminder)
            startActivity(new Intent(getApplicationContext(), ReminderSettingActivity.class));

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.confirmation_exitdialog))
                .setPositiveButton(getResources().getString(R.string.confirmation_exitdialog_yes), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }

                })
                .setNegativeButton(getResources().getString(R.string.confirmation_exitdialog_no), null)
                .show();
    }
}