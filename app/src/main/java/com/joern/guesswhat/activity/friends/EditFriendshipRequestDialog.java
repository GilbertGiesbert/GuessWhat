package com.joern.guesswhat.activity.friends;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joern.guesswhat.R;
import com.joern.guesswhat.model.Friendship;

/**
 * Created by joern on 16.04.2015.
 */
public class EditFriendshipRequestDialog extends DialogFragment implements View.OnClickListener{

    private Friendship friendshipRequested;
    private FriendshipRequester friendshipRequester;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = "Request for friendship";
        String requesterMessage = "Discard friendship request for "+friendshipRequested.geteMailAcceptor() + " ?";
        String acceptorMessage = friendshipRequested.getEMailRequester() + " is requesting your friendship";
        String message = FriendshipRequester.USER.equals(friendshipRequester) ?
                            requesterMessage : acceptorMessage;


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.pendingfriends_dialog_editrequest, null);

        TextView tv_message = (TextView) root.findViewById(R.id.tv_message);
        tv_message.setText(message);

        if(FriendshipRequester.FRIEND.equals(friendshipRequester)){

            Drawable profilePic = getProfilePic(friendshipRequested.getEMailRequester());
            if(profilePic!=null){
                ImageView iv_profile = (ImageView) root.findViewById(R.id.iv_profile);
                iv_profile.setImageDrawable(profilePic);
            }
        }

        final String positiveButton = FriendshipRequester.USER.equals(friendshipRequester) ? "Discard" : "Accept";
        final String negativeButton = FriendshipRequester.USER.equals(friendshipRequester) ? "Cancel" : "Reject";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(root)
        .setTitle(title)

        .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Toast.makeText(getActivity(), "clicked "+positiveButton, Toast.LENGTH_SHORT).show();

            }
        })

        .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Toast.makeText(getActivity(), "clicked " + negativeButton, Toast.LENGTH_SHORT).show();
            }
        });









//        Button b = (Button) root.findViewById(R.id.bt_accept);
//
//
//        Toast.makeText(getActivity(), "b!=null: "+(b!=null), Toast.LENGTH_SHORT).show();

        return builder.create();
    }

    private Drawable getProfilePic(String eMailRequester) {

        //TODO
        return null;
    }

    public void setFriendshipRequested(Friendship friendshipRequested) {
        this.friendshipRequested = friendshipRequested;
    }

    public void setFriendshipRequester(FriendshipRequester friendshipRequester) {
        this.friendshipRequester = friendshipRequester;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "clicked: "+getActivity().getResources().getResourceEntryName(v.getId()), Toast.LENGTH_SHORT).show();
    }
}