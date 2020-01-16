package zap.dev.movieandtvcatalogue.movie;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import zap.dev.movieandtvcatalogue.MainActivity;
import zap.dev.movieandtvcatalogue.R;
import zap.dev.movieandtvcatalogue.model.Movie;
import zap.dev.movieandtvcatalogue.model.MyViewModel;

public class MovieTabFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private MyViewModel movieViewModel;
    private MovieAdapter movieAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movie_tab, container, false);

        // initialize recylerview
        recyclerView = view.findViewById(R.id.recyclerview_film);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity().getApplicationContext()));

        movieViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        movieViewModel.getMovies().observe(this, getMovie);

        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(movieAdapter);

        getDataList();
        searchMovie();
        return view;
    }

    private void searchMovie() {
        final SearchView searchView = view.findViewById(R.id.searchMovie);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieViewModel.searchMovie(searchView.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieViewModel.searchMovie(searchView.getQuery().toString());
                return false;
            }
        });
    }

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movieItems) {
            if (movieItems != null) {
                movieAdapter.setData(getContext(), movieItems);
                showLoading(false);
            }
                else
                    showLoading(true);
        }
    };

    private void showLoading(Boolean state) {
        if (state)
            view.findViewById(R.id.progressBar_movie).setVisibility(View.VISIBLE);
            else
                view.findViewById(R.id.progressBar_movie).setVisibility(View.GONE);
    }

    private void getDataList() {
        if (MainActivity.bahasa.equals(MainActivity.language_used))
            movieViewModel.setListMovies(getContext(), "id");
            else if (MainActivity.language.equals(MainActivity.language_used))
                movieViewModel.setListMovies(getContext(), "en");
                else {
                    Toast.makeText(getActivity(), "Only supports for english and indonesian", Toast.LENGTH_SHORT).show();
                    movieViewModel.setListMovies(getContext(), "en");
                }
    }
}
