package com.verizon.media;

import java.util.Comparator;

public class MovieViewsCompare implements Comparator<Movie> {
    public int compare(Movie m2, Movie m1) {
        if (m1.getViews() < m2.getViews())
            return -1;
        else if (m1.getViews() > m2.getViews())
            return 1;
        else
            return 0;
    }
}
