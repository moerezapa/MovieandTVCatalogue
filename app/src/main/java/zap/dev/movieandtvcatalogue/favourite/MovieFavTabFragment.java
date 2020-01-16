package zap.dev.movieandtvcatalogue.favourite;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import zap.dev.movieandtvcatalogue.R;
import zap.dev.movieandtvcatalogue.localdb.AppDatabase;
import zap.dev.movieandtvcatalogue.localdb.DBRepository;
import zap.dev.movieandtvcatalogue.model.MovieFavourite;

public class MovieFavTabFragment extends Fragment {

    private View view;

    private MovieFavAdapter movieFavAdapter;

    private RecyclerView rvFav;
    private ProgressBar progressBar;

    private AppDatabase appDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.moviefavourite_fragment, container, false);

        rvFav = view.findViewById(R.id.recyclerview_filmFav);
        rvFav.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFav.setHasFixedSize(true);
        progressBar = view.findViewById(R.id.progressBar_filmFav);

        fetchFavourite();
        return view;
    }

    public void fetchFavourite(){
        appDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, DBRepository.DB_NAME).build();
        new AsyncTask<Void, Void, List<MovieFavourite>>() {
            @Override
            protected List<MovieFavourite> doInBackground(Void... voids) {
                List<MovieFavourite> movieFavouriteList = appDatabase.daoAccess().getAllFavourite();
                return movieFavouriteList;
            }

            @Override
            protected void onPostExecute(List<MovieFavourite> movieFavouriteList) {
                super.onPostExecute(movieFavouriteList);
                if (movieFavouriteList != null) {
                    view.findViewById(R.id.txt_noFilmFavourite).setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    movieFavAdapter = new MovieFavAdapter(getActivity(), movieFavouriteList);
                    rvFav.setAdapter(movieFavAdapter);
                    if (movieFavouriteList.size() == 0)
                        view.findViewById(R.id.txt_noFilmFavourite).setVisibility(View.VISIBLE);
                }
            }
        }.execute();
    }
}
