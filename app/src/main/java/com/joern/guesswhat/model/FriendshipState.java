package com.joern.guesswhat.model;

public enum FriendshipState {

    // request has been sent but receiver has not yet seen it
    // needed to flag 'new' requests received
    REQUEST_SEND(1),

    // request has arrived at/ been seen by receiver
    // and is now pending acceptance
    REQUEST_RECEIVED(2),

    // request has been accepted by receiver
    // friendship is now active
    ACTIVE(3),

    // request has been rejected by receiver
    REQUEST_REJECTED(4),

    // request has been cancelled by requester
    REQUEST_CANCELLED(5),

    // friendship has been terminated by one friend
    TERMINATED(6);

    //  --- removed ---
    // try to sent invite
    // - if server does not answer, don't list invite
    // - if server says requested friend does not exist, don't list invite either
    // -----------------
    // request has been invalidated by system
    // e.g. because of friend's mail does not exist in system
    //INVALID(7);

    private int value;

    private FriendshipState(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FriendshipState valueOf(int input){

        for(FriendshipState state: FriendshipState.values()){
            if(state.getValue() == input){
                return state;
            }
        }
        return null;
    }
}