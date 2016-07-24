package com.example.ayushimathur.outsideselect;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Krysia on 7/23/16.
*/

public class Artist implements Comparable<Artist> {
    private long id;
    private String name;
    private String location;
    private String starttime;
    private String endtime;

    public Artist(){}
    public Artist(long id, String name, String location, String starttime, String endtime) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(Artist a) {

        String s1 = this.getStarttime();
        String s2 = a.getStarttime();

        DateFormat f = new SimpleDateFormat("hh:mm");
        try {
            return f.parse(s1).compareTo(f.parse(s2));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }


}