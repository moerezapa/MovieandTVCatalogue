package zap.dev.movieandtvcatalogue.movie;

import android.content.Context;
import android.content.Intent;
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

import zap.dev.movieandtvcatalogue.MainActivity;
import zap.dev.movieandtvcatalogue.R;
import zap.dev.movieandtvcatalogue.model.Movie;

public class MovieAdapter  extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movieItems = new ArrayList<>();

    public void setData(Context context, ArrayList<Movie> items) {
        movieItems.clear();
        movieItems.addAll(items);
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.txt_filmname.setText(movieItems.get(viewHolder.getAdapterPosition()).getOriginalTitle());
        viewHolder.txt_releasedate.setText(movieItems.get(viewHolder.getAdapterPosition()).getReleaseDate());
        viewHolder.txt_popularity.setText(context.getResources().getString(R.string.popularity) + ": " + movieItems.get(i).getPopularity());
        Picasso.get()
                .load(movieItems.get(viewHolder.getAdapterPosition()).getPosterPath())
                .into(viewHolder.poster);

        viewHolder.cardView_film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.MOVIE_DETAIL, movieItems.get(viewHolder.getAdapterPosition()));
                intent.putExtra(MainActivity.from, "mainactivity");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return movieItems.size(); }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

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
