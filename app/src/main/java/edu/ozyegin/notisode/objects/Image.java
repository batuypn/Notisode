package edu.ozyegin.notisode.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Batuhan on 27.4.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Image implements Serializable {
    private String full;
    private String medium;
    private String thumb;

    public String getFull() {
        return full;
    }

    public String getMedium() {
        return medium;
    }

    public String getThumb() {
        return thumb;
    }
}
