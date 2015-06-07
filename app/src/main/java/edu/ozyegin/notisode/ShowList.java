package edu.ozyegin.notisode;

import java.util.ArrayList;

import edu.ozyegin.notisode.objects.Show;

/**
 * Created by Batuhan on 3.6.2015.
 */
public class ShowList {

    private ArrayList<Show> data;

    public ShowList() {

    }

    public ShowList(ArrayList<Show> data) {
        this.data = data;
    }

    public ArrayList<Show> getData() {
        return data;
    }

    public void setData(ArrayList<Show> data) {
        this.data = data;
    }
}
