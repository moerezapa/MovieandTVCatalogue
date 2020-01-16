package zap.dev.movieandtvcatalogue.tvshow;

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
import zap.dev.movieandtvcatalogue.model.TV;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.ViewHolder> {

    private Context context;
    private List<TV> tvShowList = new ArrayList<>();

    public void setData(Context context, ArrayList<TV> items) {
        tvShowList.clear();
        tvShowList.addAll(items);
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.tvshow_item, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.txt_tvname.setText(tvShowList.get(viewHolder.getAdapterPosition()).getName());
        viewHolder.txt_releasedate.setText(tvShowList.get(viewHolder.getAdapterPosition()).getFirst_air_date());
        viewHolder.txt_popularity.setText(context.getResources().getString(R.string.popularity) + ": " + tvShowList.get(viewHolder.getAdapterPosition()).getPopularity());
        Picasso.get()
                .load(tvShowList.get(i).getPoster_path())
                .into(viewHolder.poster);
        viewHolder.cardView_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TVDetailActivity.class);
                intent.putExtra(TVDetailActivity.TV_DETAIL, tvShowList.get(viewHolder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return tvShowList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_tvname, txt_releasedate, txt_popularity;
        ImageView poster;

        CardView cardView_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_tvname = itemView.findViewById(R.id.txt_name);
            txt_releasedate = itemView.findViewById(R.id.txt_tanggalrilis);
            txt_popularity = itemView.findViewById(R.id.txt_popularity);

            poster = itemView.findViewById(R.id.tvshow_poster);

            cardView_tv = itemView.findViewById(R.id.cardview_tvshow);
        }

    }
}
