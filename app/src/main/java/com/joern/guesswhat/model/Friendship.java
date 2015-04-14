package com.joern.guesswhat.model;

/**
 * Created by joern on 14.04.2015.
 */
public class Friendship {

    public enum RequestState{
        TRUE, FALSE, PENDING
    }

    private User friendshipRequester;
    private User friendshipAcceptor;

    private RequestState requestState;

    public Friendship(User friendshipRequester, User friendshipAcceptor) {
        this.friendshipRequester = friendshipRequester;
        this.friendshipAcceptor = friendshipAcceptor;
        this.requestState = RequestState.PENDING;
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

    public RequestState getRequestState() {
        return requestState;
    }

    public void setRequestState(RequestState requestState) {
        this.requestState = requestState;
    }
}
