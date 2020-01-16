package zap.dev.movieandtvcatalogue.favourite;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import zap.dev.movieandtvcatalogue.R;
import zap.dev.movieandtvcatalogue.tvshow.TVDetailActivity;

public class FavouriteTabFragment extends Fragment {
    private View view, movieView, tvView;
    private TextView movieFavTabTitle, tvFavTabTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favourite_tab, container, false);

        // initialize view
        movieView = view.findViewById(R.id.movieView);
        tvView = view.findViewById(R.id.tvView);
        movieFavTabTitle = view.findViewById(R.id.moviefavourite_tabtitle);
        tvFavTabTitle = view.findViewById(R.id.tvfavourite_tabtitle);

        //LOAD PAGE FOR FIRST
        String tvdetail = getActivity().getIntent().getStringExtra(TVDetailActivity.asal);

        if (savedInstanceState == null) {
            loadPage(new MovieFavTabFragment());
            movieFavTabTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
            if (tvdetail != null) {
                loadPage(new TVFavTabFragment());
                tvFavTabTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }

        tabsClickHandler();
        return view;
    }

    private void tabsClickHandler() {
        view.findViewById(R.id.moviefavourite_framelayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPage(new MovieFavTabFragment());

                movieFavTabTitle.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                tvFavTabTitle.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));

                movieView.setVisibility(View.VISIBLE);
                tvView.setVisibility(View.INVISIBLE);
            }
        });

        view.findViewById(R.id.tvfavourite_framelayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPage(new TVFavTabFragment());

                movieFavTabTitle.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                tvFavTabTitle.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));

                movieView.setVisibility(View.INVISIBLE);
                tvView.setVisibility(View.VISIBLE);
            }
        });
    }

    //LOAD PAGE FRAGMENT METHOD
    private boolean loadPage(Fragment fragment) {
        if (fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerfavourite, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}