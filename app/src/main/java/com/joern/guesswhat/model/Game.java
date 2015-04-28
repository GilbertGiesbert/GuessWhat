package com.joern.guesswhat.model;

/**
 * Created by joern on 28.04.2015.
 */
public class Game {

    private int id;
    private String title;
    private boolean isPrivate;
    private String solution;
    private String hints;

    public Game(int id, String title, boolean isPrivate, String solution, String hints) {
        this.id = id;
        this.title = title;
        this.isPrivate = isPrivate;
        this.solution = solution;
        this.hints = hints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getHints() {
        return hints;
    }

    public void setHints(String hints) {
        this.hints = hints;
    }
}
