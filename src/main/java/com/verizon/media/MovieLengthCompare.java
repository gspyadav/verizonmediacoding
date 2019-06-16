package com.verizon.media;

import java.util.Comparator;

/**
 * Comparator class for Movie
 */
public class MovieLengthCompare implements Comparator<Movie> {
    public int compare(Movie m2, Movie m1) {
        if (m1.getLength() < m2.getLength())
            return -1;
        else if (m1.getLength() > m2.getLength())
            return 1;
        else
            return 0;
    }
}
