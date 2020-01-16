package zap.dev.movieandtvcatalogue.utilities;

import zap.dev.movieandtvcatalogue.model.MovieResponse;
import zap.dev.movieandtvcatalogue.model.TVResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    @GET("discover/movie")
    Call<MovieResponse> getMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("discover/tv")
    Call<TVResponse> getTV(
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("search/movie")
    Call<MovieResponse> searchMovie(
            @Query("api_key") String apiKey,
            @Query("query") String moviename);
    @GET("search/tv")
    Call<TVResponse> searchTV(
            @Query("api_key") String apiKey,
            @Query("query") String tvname);

    @GET("dicover/movie")
    Call<MovieResponse> getUpcomingMovie(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String tanggal,
            @Query("primary_release_date.lte") String tanggalhariini);
}
