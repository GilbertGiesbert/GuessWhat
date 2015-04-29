package com.joern.guesswhat.persistence.database;

import com.joern.guesswhat.model.Game;

/**
 * Created by joern on 28.04.2015.
 */
public interface GameDao {

    public Game createGame(String title, boolean isPrivate, String solution, String hints);
    public Game readGame(String title);
    public boolean updateGame(Game game);
    public boolean deleteGame(Game game);
}
