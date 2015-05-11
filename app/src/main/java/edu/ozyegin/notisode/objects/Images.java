package edu.ozyegin.notisode.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Batuhan on 27.4.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Images implements Serializable {
    private Image poster;
    private Image fanart;
    private Image screenshot;
    private Image headshot;
    private Image banner;
    private Image logo;
    private Image clearart;
    private Image thumb;
    private Image avatar;

    public Image getPoster() {
        return poster;
    }

    public Image getFanart() {
        return fanart;
    }

    public Image getScreenshot() {
        return screenshot;
    }

    public Image getHeadshot() {
        return headshot;
    }

    public Image getBanner() {
        return banner;
    }

    public Image getLogo() {
        return logo;
    }

    public Image getClearart() {
        return clearart;
    }

    public Image getThumb() {
        return thumb;
    }

    public Image getAvatar() {
        return avatar;
    }
}
