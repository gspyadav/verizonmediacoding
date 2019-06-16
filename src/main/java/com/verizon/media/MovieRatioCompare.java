package com.verizon.media;

import java.util.Comparator;

public class MovieRatioCompare implements Comparator<Movie> {
    public int compare(Movie m2, Movie m1) {
        if (m1.getRatio() < m2.getRatio())
            return -1;
        else if (m1.getRatio() > m2.getRatio())
            return 1;
        else
            return 0;
    }
}
