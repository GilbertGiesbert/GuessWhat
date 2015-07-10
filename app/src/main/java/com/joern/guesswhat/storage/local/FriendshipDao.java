package com.joern.guesswhat.storage.local;

import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.FriendshipState;
import com.joern.guesswhat.model.User;

/**
 * Created by joern on 13.04.2015.
 */
public interface FriendshipDao {

    public boolean createFriendship(User user, User friend, FriendshipState state);
    public Friendship readFriendship(User user, User friend);
    public boolean updateFriendship(Friendship friendship);
    public boolean deleteFriendship(Friendship friendship);
}