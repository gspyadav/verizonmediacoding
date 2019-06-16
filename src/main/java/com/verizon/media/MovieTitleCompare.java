package com.verizon.media;

import java.util.Comparator;

public class MovieTitleCompare implements Comparator<Movie> {
    public int compare(Movie m1, Movie m2) {
        return m1.getTitle().compareTo(m2.getTitle());
    }
}
