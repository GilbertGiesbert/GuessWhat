package com.joern.guesswhat.persistence.database;

import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.FriendshipState;
import com.joern.guesswhat.model.User;

import java.util.List;

/**
 * Created by joern on 13.04.2015.
 */
public interface UserDao {

    public boolean createUser(String name, String eMail, int passwordHash);
    public User readUser(int id);
    public User readUser(String name);
    public boolean updateUser(User user);
    public boolean deleteUser(User user);

    public boolean checkPswd(User user, int passwordHash);
    public boolean updatePswd(User user, int oldPswdHash, int newPswdHash);

    public List<Friendship> getFriendships(User user, FriendshipState state);
}