package com.joern.guesswhat.database;

import com.joern.guesswhat.activity.friends.FriendshipRequester;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.User;

import java.util.List;

/**
 * Created by joern on 13.04.2015.
 */
public interface FriendshipDao {

    public boolean createFriendship(String eMailRequester, String eMailAcceptor);
    public boolean updateFriendship(Friendship friendship);
    public boolean deleteFriendship(Friendship friendship);

    public List<Friendship> getAllFriendships(User user);

    List<Friendship> getRequestedFriendships(User user, FriendshipRequester from);

    public List<Friendship> getAllFriendshipsToAccept(User user);

    List<User> getFriends(User user);
}