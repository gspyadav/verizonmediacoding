package com.verizon.media.SolutionThree;

import com.verizon.media.Movie;

import java.util.List;
import java.util.Map;

public class FilesMoviesDataStore {
    private Map<String, List<Movie>> moviesListMap;

    public Map<String, List<Movie>> getMoviesListMap() {
        return moviesListMap;
    }

    public void setMoviesListMap(Map<String, List<Movie>> moviesListMap) {
        this.moviesListMap = moviesListMap;
    }

    public List<Movie> getMoviesList(String filePath){
        return moviesListMap.get(filePath);
    }
}
