package zap.dev.movieandtvcatalogue.tvshow;

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
import zap.dev.movieandtvcatalogue.model.MyViewModel;
import zap.dev.movieandtvcatalogue.model.TV;

public class TVShowTabFragment extends Fragment {
    private View view;

    private RecyclerView recyclerView;

    private MyViewModel tvViewModel;
    private TVAdapter tvAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tvshow_tab, container, false);

        //________initialize
        recyclerView = view.findViewById(R.id.recyclerview_tvshow);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity().getApplicationContext()));

        tvViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        tvViewModel.getTV().observe(this, getTV);

        tvAdapter = new TVAdapter();
        tvAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(tvAdapter);

        getDataList();
        searchTV();
        return view;
    }

    private void searchTV() {
        final SearchView searchView = view.findViewById(R.id.searchTV);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tvViewModel.searchTV(searchView.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tvViewModel.searchTV(searchView.getQuery().toString());
                return false;
            }
        });
    }

    private Observer<ArrayList<TV>> getTV = new Observer<ArrayList<TV>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TV> tvItems) {
            if (tvItems != null) {
                tvAdapter.setData(getContext(), tvItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(boolean state) {
        if (state)
            view.findViewById(R.id.progressBar_tv).setVisibility(View.VISIBLE);
            else
                view.findViewById(R.id.progressBar_tv).setVisibility(View.GONE);
    }

    private void getDataList() {
        if (MainActivity.bahasa.equals(MainActivity.language_used))
            tvViewModel.setListTV(getContext(), "id");
            else if (MainActivity.language.equals(MainActivity.language_used))
                tvViewModel.setListTV(getContext(), "en");
                else {
                    Toast.makeText(getActivity(), "Only supports for english and indonesian", Toast.LENGTH_SHORT).show();
                    tvViewModel.setListTV(getContext(), "en");
                }
    }
}
