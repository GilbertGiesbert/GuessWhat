package com.joern.guesswhat.model;

/**
 * Created by joern on 14.04.2015.
 */
public class Friendship {

    private User friendshipRequester;
    private User friendshipAcceptor;

    private boolean terminated;

    private FriendshipRequestState requestState;

    public Friendship(User friendshipRequester, User friendshipAcceptor) {
        this.friendshipRequester = friendshipRequester;
        this.friendshipAcceptor = friendshipAcceptor;
        this.requestState = FriendshipRequestState.PENDING;
        this.terminated = false;
    }

    public User getFriendshipRequester() {
        return friendshipRequester;
    }

    public void setFriendshipRequester(User friendshipRequester) {
        this.friendshipRequester = friendshipRequester;
    }

    public User getFriendshipAcceptor() {
        return friendshipAcceptor;
    }

    public void setFriendshipAcceptor(User friendshipAcceptor) {
        this.friendshipAcceptor = friendshipAcceptor;
    }

    public FriendshipRequestState getRequestState() {
        return requestState;
    }

    public void setRequestState(FriendshipRequestState requestState) {
        this.requestState = requestState;
    }
}