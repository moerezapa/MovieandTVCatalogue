package zap.dev.movieandtvcatalogue.favourite;

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

import zap.dev.movieandtvcatalogue.R;
import zap.dev.movieandtvcatalogue.model.TVFavourite;
import zap.dev.movieandtvcatalogue.tvshow.TVDetailActivity;

public class TVFavAdapter extends RecyclerView.Adapter<TVFavAdapter.ViewHolder> {

    private Context context;
    private List<TVFavourite> tvItems = new ArrayList<>();

    public TVFavAdapter(Context context, List<TVFavourite> movieFavouriteList) {
        this.context = context;
        this.tvItems = movieFavouriteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tvshow_item, viewGroup, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.txt_filmname.setText(tvItems.get(viewHolder.getAdapterPosition()).getOriginal_name());
        viewHolder.txt_releasedate.setText(tvItems.get(viewHolder.getAdapterPosition()).getRelease_date());
        viewHolder.txt_popularity.setText(context.getResources().getString(R.string.popularity) + ": "
                                            + tvItems.get(viewHolder.getAdapterPosition()).getPopularity());
        Picasso.get()
                .load(tvItems.get(viewHolder.getAdapterPosition()).getPoster_path())
                .into(viewHolder.poster);

        viewHolder.cardView_film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TVDetailActivity.class);
                intent.putExtra(TVDetailActivity.TVFAV_DETAIL, tvItems.get(viewHolder.getAdapterPosition()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return tvItems.size(); }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_filmname, txt_releasedate, txt_popularity;
        ImageView poster;

        CardView cardView_film;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_filmname = itemView.findViewById(R.id.txt_name);
            txt_releasedate = itemView.findViewById(R.id.txt_tanggalrilis);
            txt_popularity = itemView.findViewById(R.id.txt_popularity);

            poster = itemView.findViewById(R.id.tvshow_poster);

            cardView_film = itemView.findViewById(R.id.cardview_tvshow);
        }
    }
}
