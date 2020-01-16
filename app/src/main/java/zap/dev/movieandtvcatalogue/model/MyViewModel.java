package zap.dev.movieandtvcatalogue.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import zap.dev.movieandtvcatalogue.MainActivity;
import zap.dev.movieandtvcatalogue.utilities.API;
import zap.dev.movieandtvcatalogue.utilities.RetrofitClient;

import static android.support.constraint.Constraints.TAG;

public class MyViewModel extends ViewModel {
    private ArrayList<Movie> listMovie = new ArrayList<>();
    private ArrayList<TV> listTV = new ArrayList<>();

    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TV>> listTVs = new MutableLiveData<>();

    API api;
    public void setListMovies(final Context context, String language) {
        api = RetrofitClient.getClient().create(API.class);
        api.getMovies(MainActivity.myAPI, language)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, retrofit2.Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            listMovie = response.body().getResults();
                            Log.e(TAG, "Hasil List Movie: " + listMovie);
                            listMovies.postValue(listMovie);
                        }
                            else
                                Toast.makeText(context, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "Failed movie because: " + response.raw());
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {

                    }
                });
    }

    public void setListTV(final Context context, String language) {
        api = RetrofitClient.getClient().create(API.class);
        api.getTV(MainActivity.myAPI, language)
                .enqueue(new Callback<TVResponse>() {
                    @Override
                    public void onResponse(Call<TVResponse> call, retrofit2.Response<TVResponse> response) {
                        if (response.isSuccessful()) {
                            listTV = response.body().getResults();
                            Log.e(TAG, "Hasil List TV: " + listTV);
                            listTVs.postValue(listTV);
                        }
                            else
                                Toast.makeText(context, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "Failed");
                    }
                    @Override
                    public void onFailure(Call<TVResponse> call, Throwable t) {

                    }
                });
    }

    public void searchMovie(String movieName) {
        api = RetrofitClient.getClient().create(API.class);
        api.searchMovie(MainActivity.myAPI, movieName)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, retrofit2.Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            listMovie = response.body().getResults();
                            Log.e(TAG, "Hasil List Movie: " + listMovie);
                            listMovies.postValue(listMovie);
                        }
                            else
                                Log.e(TAG, "Failed searching movie because: " + response.raw());
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {

                    }
                });
    }
    public void searchTV(String tvName) {
        api = RetrofitClient.getClient().create(API.class);
        api.searchTV(MainActivity.myAPI, tvName)
                .enqueue(new Callback<TVResponse>() {
                    @Override
                    public void onResponse(Call<TVResponse> call, retrofit2.Response<TVResponse> response) {
                        if (response.isSuccessful()) {
                            listTV = response.body().getResults();
                            Log.e(TAG, "Hasil List TV: " + listTV);
                            listTVs.postValue(listTV);
                        }
                            else
                                Log.e(TAG, "Failed tv because: " + response.raw());
                    }

                    @Override
                    public void onFailure(Call<TVResponse> call, Throwable t) {

                    }
                });
    }

    public LiveData<ArrayList<Movie>> getMovies() { return listMovies; }
    public LiveData<ArrayList<TV>> getTV() { return listTVs; }
}
