package com.joern.guesswhat.model;

/**
 * Created by joern on 14.04.2015.
 */
public class Friendship {

    private String eMailRequester;
    private String eMailAcceptor;
    private FriendshipRequestState requestState;

    public Friendship(String eMailRequester, String eMailAcceptor, FriendshipRequestState requestState) {
        this.eMailRequester = eMailRequester;
        this.eMailAcceptor = eMailAcceptor;
        this.requestState = requestState;
    }

    public String geteMailRequester() {
        return eMailRequester;
    }

    public void seteMailRequester(String eMailRequester) {
        this.eMailRequester = eMailRequester;
    }

    public String geteMailAcceptor() {
        return eMailAcceptor;
    }

    public void seteMailAcceptor(String eMailAcceptor) {
        this.eMailAcceptor = eMailAcceptor;
    }

    public FriendshipRequestState getRequestState() {
        return requestState;
    }

    public void setRequestState(FriendshipRequestState requestState) {
        this.requestState = requestState;
    }
}