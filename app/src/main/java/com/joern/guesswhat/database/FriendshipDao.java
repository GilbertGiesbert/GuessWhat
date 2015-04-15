package com.joern.guesswhat.database;

import com.joern.guesswhat.activity.friends.PendingFriendshipType;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.User;

import java.util.List;

/**
 * Created by joern on 13.04.2015.
 */
public interface FriendshipDao {

    public boolean createFriendship(String eMailRequester, String eMailAcceptor);
    public boolean updateFriendship(User user1, User user2);
    public boolean deleteFriendship(User user1, User user2);

    public List<Friendship> getAllFriendships(User user);

    List<Friendship> getAllPendingFriendships(User user, PendingFriendshipType type);

    public List<Friendship> getAllFriendshipsToAccept(User user);
}