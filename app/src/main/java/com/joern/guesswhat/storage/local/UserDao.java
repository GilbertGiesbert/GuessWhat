package com.joern.guesswhat.storage.local;

import com.joern.guesswhat.model.User;

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
}