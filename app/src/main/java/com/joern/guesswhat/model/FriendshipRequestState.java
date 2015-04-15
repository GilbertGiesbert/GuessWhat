package com.joern.guesswhat.model;

public enum FriendshipRequestState {

    // request has been sent but acceptor has not yet seen it
    // needed to flag 'new' invites
    PENDING_RECOGNITION(1),

    // request has been seen by acceptor and is waiting for approval
    PENDING_ACCEPTANCE(2),

    // request has been accepted by acceptor
    ACCEPTED(3),

    // request has been rejected by acceptor
    REJECTED(4),

    // request has been cancelled by requester
    CANCELLED(5),

    // request has been invalidated by system
    // e.g. because of friend's mail does not exist in system
    INVALID(6);

    private int value;

    private FriendshipRequestState(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FriendshipRequestState valueOf(int input){

        for(FriendshipRequestState state: FriendshipRequestState.values()){
            if(state.getValue() == input){
                return state;
            }
        }
        return null;
    }
}