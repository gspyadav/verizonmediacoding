package com.verizon.media;

import java.util.Comparator;

/**
 *
 */
public class MovieWatchedCompare implements Comparator<Movie> {
    public int compare(Movie m2, Movie m1) {
        if (m1.getAvgWatched() < m2.getAvgWatched())
            return -1;
        else if (m1.getAvgWatched() > m2.getAvgWatched())
            return 1;
        else
            return 0;
    }
}
