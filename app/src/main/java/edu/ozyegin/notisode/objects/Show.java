package edu.ozyegin.notisode.objects;

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
}
