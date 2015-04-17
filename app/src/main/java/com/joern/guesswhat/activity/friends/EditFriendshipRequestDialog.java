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

import com.joern.guesswhat.R;
import com.joern.guesswhat.database.FriendshipDao;
import com.joern.guesswhat.database.FriendshipDaoImpl;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.FriendshipRequestType;
import com.joern.guesswhat.model.FriendshipState;

/**
 * Created by joern on 16.04.2015.
 */
public class EditFriendshipRequestDialog extends DialogFragment{

    private Friendship friendshipRequested;
    private FriendshipRequestType friendshipRequester;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = getActivity().getResources().getString(R.string.pendingFriends_dialog_title);
        String requesterMessage = getActivity().getResources().getString(R.string.pendingFriends_dialog_requesterMessage)+friendshipRequested.geteMailAcceptor() + " ?";
        String acceptorMessage = friendshipRequested.getEMailRequester() + getActivity().getResources().getString(R.string.pendingFriends_dialog_acceptorMessage);
        String message = FriendshipRequestType.SENT.equals(friendshipRequester) ?
                            requesterMessage : acceptorMessage;


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.pendingfriends_dialog_editrequest, null);

        TextView tv_message = (TextView) root.findViewById(R.id.tv_message);
        tv_message.setText(message);

        if(FriendshipRequestType.RECEIVED.equals(friendshipRequester)){

            Drawable profilePic = getProfilePic(friendshipRequested.getEMailRequester());
            if(profilePic!=null){
                ImageView iv_profile = (ImageView) root.findViewById(R.id.iv_profile);
                iv_profile.setImageDrawable(profilePic);
            }
        }

        final String positiveButton = FriendshipRequestType.SENT.equals(friendshipRequester) ?
                getActivity().getResources().getString(R.string._discard) : getActivity().getResources().getString(R.string._accept);
        final String negativeButton = FriendshipRequestType.SENT.equals(friendshipRequester) ?
                getActivity().getResources().getString(R.string._cancel) : getActivity().getResources().getString(R.string._reject);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(root)
        .setTitle(title)

        .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (FriendshipRequestType.SENT.equals(friendshipRequester)) {
                    friendshipRequested.setFriendshipState(FriendshipState.REQUEST_CANCELLED);
                } else {
                    friendshipRequested.setFriendshipState(FriendshipState.ACTIVE);
                }

                FriendshipDao dao = new FriendshipDaoImpl(getActivity());
                dao.updateFriendship(friendshipRequested);
            }
        })

        .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (FriendshipRequestType.RECEIVED.equals(friendshipRequester)) {
                    friendshipRequested.setFriendshipState(FriendshipState.REQUEST_REJECTED);
                    FriendshipDao dao = new FriendshipDaoImpl(getActivity());
                    dao.updateFriendship(friendshipRequested);
                }
            }
        });

        return builder.create();
    }

    private Drawable getProfilePic(String eMailRequester) {

        //TODO
        return null;
    }

    public void setFriendshipRequested(Friendship friendshipRequested) {
        this.friendshipRequested = friendshipRequested;
    }

    public void setFriendshipRequester(FriendshipRequestType friendshipRequester) {
        this.friendshipRequester = friendshipRequester;
    }
}