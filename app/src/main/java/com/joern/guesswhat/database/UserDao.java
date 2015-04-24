package com.joern.guesswhat.database;

import com.joern.guesswhat.model.User;

import java.util.List;

/**
 * Created by joern on 13.04.2015.
 */
public interface UserDao {

    public boolean createUser(String name, String eMail, int passwordHash);
    public User readUser(String name);
    public boolean updateUser(User user);
    public boolean deleteUser(User user);

    public User getUserByMail(String name);
    public List<User> getFriendships(User user);
}