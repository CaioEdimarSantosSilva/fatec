package com.example.omdb_titulos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Titulo {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Plot")
    private String plot;

    @JsonProperty("Poster")
    private String poster;

    @JsonProperty("imdbRating")
    private String imdbRating;

    @JsonProperty("Genre")
    private String genre;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Runtime")
    private String runtime;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Actors")
    private String actors;

    @JsonProperty("totalSeasons")
    private String totalSeasons;

    @JsonProperty("Response")
    private String response;

    @JsonProperty("Error")
    private String error;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getPlot() { return plot; }
    public void setPlot(String plot) { this.plot = plot; }

    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }

    public String getImdbRating() { return imdbRating; }
    public void setImdbRating(String imdbRating) { this.imdbRating = imdbRating; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getRuntime() { return runtime; }
    public void setRuntime(String runtime) { this.runtime = runtime; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getActors() { return actors; }
    public void setActors(String actors) { this.actors = actors; }

    public String getTotalSeasons() { return totalSeasons; }
    public void setTotalSeasons(String totalSeasons) { this.totalSeasons = totalSeasons; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}
