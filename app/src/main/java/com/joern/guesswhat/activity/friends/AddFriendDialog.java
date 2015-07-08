package com.joern.guesswhat.activity.friends;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import com.joern.guesswhat.R;
import com.joern.guesswhat.common.SessionHelper;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.FriendshipState;
import com.joern.guesswhat.model.User;
import com.joern.guesswhat.persistence.database.FriendshipService;
import com.joern.guesswhat.persistence.database.FriendshipServiceImpl;
import com.joern.guesswhat.persistence.database.UserDaoImpl;

/**
 * Created by joern on 14.04.2015.
 */
public class AddFriendDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){


        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.friends_dialog_title)
        .setView(inflater.inflate(R.layout.friends_dialog_addfriend, null))
        .setPositiveButton(R.string._ok, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                String friendMail = ((EditText) AddFriendDialog.this.getDialog().findViewById(R.id.et_friendMail)).getText().toString();
                handleFriendRequest(friendMail);
            }
        })
        .setNegativeButton(R.string._cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AddFriendDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    private void handleFriendRequest(String friendName){

        User sessionUser = SessionHelper.getSessionUser(getActivity());

        if(sessionUser != null){

            if(sessionUser.getName().equalsIgnoreCase(friendName)){
                Toast.makeText(getActivity(), getResources().getString(R.string.friends_dialog_existingFriend), Toast.LENGTH_SHORT).show();

                User friend = new UserDaoImpl(getActivity()).readUser(friendName);
                if(friend == null) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.friends_dialog_nonExistingFriend) + friendName, Toast.LENGTH_SHORT).show();

                }else{

                    FriendshipService service = new FriendshipServiceImpl(getActivity());
                    Friendship friendship = service.getFriendship(sessionUser, friend);


                    // sent request
                    if(friendship == null){
                        boolean requestSuccessful = service.requestFriendship(sessionUser, friend);
                        if(requestSuccessful){
                            Toast.makeText(getActivity(), getResources().getString(R.string.friends_dialog_requestSent) + friendName, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), getResources().getString(R.string.friends_dialog_requestFailed) + friendName, Toast.LENGTH_SHORT).show();
                        }
                    }

                    // friendship already exists
                    else if(FriendshipState.ACTIVE.equals(friendship.getFriendshipState())) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.friends_dialog_existingFriend) + friendName, Toast.LENGTH_SHORT).show();
                    }

                    // friendship already requested
                    else if(FriendshipState.REQUEST.equals(friendship.getFriendshipState())) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.friends_dialog_existingRequest) + friendName, Toast.LENGTH_SHORT).show();
                    }

                    // invite already exists
                    if(FriendshipState.INVITE.equals(friendship.getFriendshipState())) {
                        boolean friendshipAccepted = service.acceptFriendship(friendship);
                        if(friendshipAccepted){
                            Toast.makeText(getActivity(), getResources().getString(R.string.friends_dialog_existingInvite) + friendName, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), getResources().getString(R.string.friends_dialog_requestFailed) + friendName, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }
}