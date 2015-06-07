package edu.ozyegin.notisode.objects;

import android.support.v7.graphics.Palette;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Batuhan on 27.4.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Show implements Serializable {
    private String title;
    private int year;
    private IDs ids;
    private String overview;
    private String status;
    private float rating;
    private String[] genres;
    private int aired_episodes;
    private Images images;
    private Airs airs;
    private String network;
    private int runtime;
    transient private Palette.Swatch swatch;

    public Palette.Swatch getSwatch() {
        return swatch;
    }

    public void setSwatch(Palette.Swatch swatch) {
        this.swatch = swatch;
    }

    public String getNetwork() {
        return network;
    }

    public int getRuntime() {
        return runtime;
    }

    public Airs getAirs() {
        return airs;
    }

    public int getAired_episodes() {
        return aired_episodes;
    }

    public Images getImages() {
        return images;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public IDs getIds() {
        return ids;
    }

    public String getOverview() {
        return overview;
    }

    public String getStatus() {
        return status;
    }

    public float getRating() {
        return rating;
    }

    public String[] getGenres() {
        return genres;
    }

    public class Airs implements Serializable {
        public String day, time, timezone;
    }
}
