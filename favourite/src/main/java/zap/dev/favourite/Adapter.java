package zap.dev.favourite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private Cursor favouriteCursor;
    private List<MovieFavourite> movieFavouriteList;

    void setData(Context context, Cursor cursor) {
        this.context = context;
        favouriteCursor = cursor;
        movieFavouriteList = new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        if (favouriteCursor.moveToPosition(position)) {
            viewHolder.txt_filmname.setText(favouriteCursor.getString(
                    favouriteCursor.getColumnIndexOrThrow(MovieFavourite.COLUMN_TITLE)));
            viewHolder.txt_releasedate.setText(favouriteCursor.getString(
                    favouriteCursor.getColumnIndexOrThrow(MovieFavourite.COLUMN_RELEASEDATE)));
            viewHolder.txt_popularity.setText(favouriteCursor.getString(
                    favouriteCursor.getColumnIndexOrThrow(MovieFavourite.COLUMN_POPULARITY)));
            Picasso.get()
                    .load(favouriteCursor.getString(
                            favouriteCursor.getColumnIndexOrThrow(MovieFavourite.COLUMN_POSTERPATH)))
                    .into(viewHolder.poster);

            MovieFavourite movieFavourite = new MovieFavourite();
            movieFavourite.setTitle(favouriteCursor.getString(
                    favouriteCursor.getColumnIndexOrThrow(MovieFavourite.COLUMN_TITLE)));
            movieFavourite.setPoster_path(favouriteCursor.getString(
                    favouriteCursor.getColumnIndexOrThrow(MovieFavourite.COLUMN_POSTERPATH)));
            movieFavourite.setRelease_date(favouriteCursor.getString(
                    favouriteCursor.getColumnIndexOrThrow(MovieFavourite.COLUMN_RELEASEDATE)));
            movieFavourite.setVote_average(favouriteCursor.getDouble(
                    favouriteCursor.getColumnIndexOrThrow(MovieFavourite.COLUMN_VOTEAVERAGE)));
            movieFavourite.setPopularity(favouriteCursor.getDouble(
                    favouriteCursor.getColumnIndexOrThrow(MovieFavourite.COLUMN_POPULARITY)));
            movieFavourite.setOverview(favouriteCursor.getString(
                    favouriteCursor.getColumnIndexOrThrow(MovieFavourite.COLUMN_OVERVIEW)));
            movieFavouriteList.add(movieFavourite);
            viewHolder.cardView_film.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    intent.putExtra(MovieDetailActivity.MOVIE_DETAIL, movieFavouriteList.get(position));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() { return favouriteCursor.getCount(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_filmname, txt_releasedate, txt_popularity;
        ImageView poster;

        CardView cardView_film;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_filmname = itemView.findViewById(R.id.txt_name);
            txt_releasedate = itemView.findViewById(R.id.txt_tanggalrilis);
            txt_popularity = itemView.findViewById(R.id.txt_popularity);

            poster = itemView.findViewById(R.id.movie_poster);

            cardView_film = itemView.findViewById(R.id.cardview_film);
        }
    }
}
