package com.joern.guesswhat.database;

import com.joern.guesswhat.model.User;

import java.util.List;

/**
 * Created by joern on 13.04.2015.
 */
public interface UserDao {

    public boolean createLocalUser(String name, String eMail, int passwordHash);
    public User readLocalUser(String email);
    public boolean updateUser(User oldUser, User newUser);
    public boolean deleteUser(User user);

    public List<User> getFriendships(User user);
}