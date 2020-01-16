package zap.dev.favourite;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

@Entity(tableName = MovieFavourite.TABLE_NAME)
public class MovieFavourite implements Parcelable {
    /** The name of the Menu table. */
    public static final String TABLE_NAME = "MovieFavourite";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    /** The unique ID of the menu. */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public Integer id;
    public static final String COLUMN_POSTERPATH = "poster_path";
    @ColumnInfo(name = COLUMN_POSTERPATH)
    private String poster_path;
    public static final String COLUMN_OVERVIEW = "overview";
    @ColumnInfo(name = COLUMN_OVERVIEW)
    private String overview;
    public static final String COLUMN_RELEASEDATE = "release_date";
    @ColumnInfo(name = COLUMN_RELEASEDATE)
    private String release_date;
    private String original_title;
    public static final String COLUMN_TITLE = "title";
    @ColumnInfo(name = COLUMN_TITLE)
    private String title;
    private String originalLanguage;
    private String backdrop_path;
    public static final String COLUMN_POPULARITY = "popularity";
    @ColumnInfo(name = COLUMN_POPULARITY)
    private Double popularity;
    public static final String COLUMN_VOTECOUNT = "vote_count";
    @ColumnInfo(name = COLUMN_VOTECOUNT)
    private Integer vote_count;
    private Boolean video;
    public static final String COLUMN_VOTEAVERAGE = "vote_average";
    @ColumnInfo(name = COLUMN_VOTEAVERAGE)
    private Double vote_average;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPoster_path() {
        if (poster_path != null && !poster_path.isEmpty()) {
            if(!poster_path.toLowerCase().contains("https://"))
                return "https://image.tmdb.org/t/p/w342" + poster_path;
                else
                    return poster_path;
        }
        return null;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.poster_path);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.original_title);
        dest.writeString(this.title);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.backdrop_path);
        dest.writeValue(this.popularity);
        dest.writeValue(this.vote_count);
        dest.writeValue(this.video);
        dest.writeValue(this.vote_average);
    }

    public MovieFavourite() {
    }

    protected MovieFavourite(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.poster_path = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.original_title = in.readString();
        this.title = in.readString();
        this.originalLanguage = in.readString();
        this.backdrop_path = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.vote_count = (Integer) in.readValue(Integer.class.getClassLoader());
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.vote_average = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<MovieFavourite> CREATOR = new Creator<MovieFavourite>() {
        @Override
        public MovieFavourite createFromParcel(Parcel source) {
            return new MovieFavourite(source);
        }

        @Override
        public MovieFavourite[] newArray(int size) {
            return new MovieFavourite[size];
        }
    };
}
