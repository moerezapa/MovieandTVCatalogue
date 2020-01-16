package zap.dev.movieandtvcatalogue.widget;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;
import zap.dev.movieandtvcatalogue.R;
import zap.dev.movieandtvcatalogue.localdb.AppDatabase;
import zap.dev.movieandtvcatalogue.localdb.DBRepository;
import zap.dev.movieandtvcatalogue.model.MovieFavourite;

import java.util.List;

class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private List<MovieFavourite> movieFavouriteArrayList;
    private AppDatabase appDatabase;

    WidgetRemoteViewsFactory(Context context) { mContext = context; }

    @Override
    public void onCreate() {
        appDatabase = Room.databaseBuilder(mContext, AppDatabase.class, DBRepository.DB_NAME).build();
    }

    @Override
    public void onDataSetChanged() { movieFavouriteArrayList = appDatabase.daoAccess().getAllFavourite(); }

    @Override
    public void onDestroy() { }

    @Override
    public int getCount() {
        if (movieFavouriteArrayList == null)
            return 0;
            else
                return movieFavouriteArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (movieFavouriteArrayList == null || movieFavouriteArrayList.size() == 0)
            return null;

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        try {
            Bitmap b = Picasso.get().load(movieFavouriteArrayList.get(position).getBackdrop_path()).get();
            remoteViews.setImageViewBitmap(R.id.img_moviewidget, b);
        }
            catch (Exception e) {
                Log.d("Test", e.getMessage());
            }

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
