package zap.dev.movieandtvcatalogue.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/*
    Note:
    @Entity
    Create a sqlite table in the database using a data model class
 */
@Entity
public class TVFavourite implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String poster_path;
    private String overview;
    private String release_date;
    private String original_name;
    private String name;
    private String originalLanguage;
    private String backdrop_path;
    private Double popularity;
    private Integer vote_count;
    private Boolean video;
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
        return null; //Use Picasso to put placeholder for poster
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

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getBackdrop_path() {
        if (backdrop_path != null && !backdrop_path.isEmpty()) {
            if(!backdrop_path.toLowerCase().contains("https://"))
                return "https://image.tmdb.org/t/p/original" + backdrop_path;
            else
                return backdrop_path;
        }
        return null; //Use Picasso to put placeholder for poster
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
        dest.writeString(this.original_name);
        dest.writeString(this.name);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.backdrop_path);
        dest.writeValue(this.popularity);
        dest.writeValue(this.vote_count);
        dest.writeValue(this.video);
        dest.writeValue(this.vote_average);
    }

    public TVFavourite() {
    }

    protected TVFavourite(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.poster_path = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.original_name = in.readString();
        this.name = in.readString();
        this.originalLanguage = in.readString();
        this.backdrop_path = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.vote_count = (Integer) in.readValue(Integer.class.getClassLoader());
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.vote_average = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<TVFavourite> CREATOR = new Creator<TVFavourite>() {
        @Override
        public TVFavourite createFromParcel(Parcel source) {
            return new TVFavourite(source);
        }

        @Override
        public TVFavourite[] newArray(int size) {
            return new TVFavourite[size];
        }
    };
}
