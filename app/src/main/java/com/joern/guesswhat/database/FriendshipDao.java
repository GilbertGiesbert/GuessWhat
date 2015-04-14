package com.joern.guesswhat.database;

import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.User;

import java.util.List;

/**
 * Created by joern on 13.04.2015.
 */
public interface FriendshipDao {

    public boolean createFriendship(User friendshipRequester, User friendshipAcceptor);
    public boolean updateFriendship(User user1, User user2);
    public boolean deleteFriendship(User user1, User user2);

    public List<Friendship> getAllFriendships(User user);

    public List<Friendship> getAllFriendshipsRequestedAndPending(User user);

    public List<Friendship> getAllFriendshipsRequestedButNotAccepted(User user);

    public List<Friendship> getAllFriendshipsToAccept(User user);
}