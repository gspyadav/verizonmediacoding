package com.verizon.media;

/**
 * Common Movie class which store and computer all parameters of single movie.
 */
public class Movie implements Comparable<Movie> {
    private String category;
    private String title;
    private int length;
    private int avgWatched;
    private int views;
    private double ratio;

    public double getRatio() {
        return ratio;
    }

    public void computeRatio() {
        this.ratio = (double)this.avgWatched/this.length;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getAvgWatched() {
        return avgWatched;
    }

    public void setAvgWatched(int avgWatched) {
        this.avgWatched = avgWatched;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int compareTo(Movie obj) {
        return this.category.compareTo(obj.category);
    }
}








