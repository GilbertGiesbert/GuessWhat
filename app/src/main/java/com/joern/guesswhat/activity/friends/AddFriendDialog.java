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
import com.joern.guesswhat.database.FriendshipDao;
import com.joern.guesswhat.database.FriendshipDaoImpl;
import com.joern.guesswhat.model.User;

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

                Toast.makeText(getActivity(), "Send friend request to " + friendMail, Toast.LENGTH_SHORT).show();

                addFriend(friendMail);
            }
        })
        .setNegativeButton(R.string._cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AddFriendDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    private void addFriend(String friendMail){

        User sessionUser = SessionHelper.getSessionUser(getActivity());

        if(sessionUser != null && !sessionUser.getEmail().equals(friendMail)){

            FriendshipDao dao = new FriendshipDaoImpl(getActivity());
            dao.createFriendship(sessionUser.getEmail(), friendMail);
        }
    }
}