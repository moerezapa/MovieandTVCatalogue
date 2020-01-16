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
import zap.dev.movieandtvcatalogue.model.TVFavourite;

public class TVFavTabFragment extends Fragment {

    private View view;
    private ProgressBar progressBar;
    private RecyclerView rvFav;
    private TVFavAdapter tvFavAdapter;

    private AppDatabase appDatabase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tvfavourite_fragment, container, false);

        progressBar = view.findViewById(R.id.progressBar_tvFav);
        rvFav = view.findViewById(R.id.recyclerview_tvFav);
        rvFav.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFav.setHasFixedSize(true);

        fetchFavourite();
        return view;
    }

    public void fetchFavourite(){
        appDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, DBRepository.DB_NAME).build();
        new AsyncTask<Void, Void, List<TVFavourite>>() {
            @Override
            protected List<TVFavourite> doInBackground(Void... voids) {
                List<TVFavourite> tvFavouriteList = appDatabase.tvDao().getAllTVFavourite();
                return tvFavouriteList;
            }

            @Override
            protected void onPostExecute(List<TVFavourite> tvFavouriteList) {
                super.onPostExecute(tvFavouriteList);
                if (tvFavouriteList != null) {
                    view.findViewById(R.id.txt_noTVFavourite).setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    tvFavAdapter = new TVFavAdapter(getActivity(), tvFavouriteList);
                    rvFav.setAdapter(tvFavAdapter);
                    if (tvFavouriteList.size() == 0)
                        view.findViewById(R.id.txt_noTVFavourite).setVisibility(View.VISIBLE);
                }
            }
        }.execute();
    }
}
