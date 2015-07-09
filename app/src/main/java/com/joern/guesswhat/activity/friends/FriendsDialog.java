package com.joern.guesswhat.activity.friends;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.joern.guesswhat.R;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.persistence.database.FriendshipService;
import com.joern.guesswhat.persistence.database.FriendshipServiceImpl;

/**
 * Created by joern on 14.04.2015.
 */
public class FriendsDialog extends DialogFragment {

    private Friendship friendship;
    private FriendsTabType tabType;

    private FriendsDialogListener dialogListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            dialogListener = (FriendsDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + FriendsDialogListener.class.getSimpleName());
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = inflater.inflate(R.layout.friends_dialog_friend, null);
        TextView tv_dialogPrompt = (TextView) view.findViewById(R.id.tv_dialogPrompt);
        tv_dialogPrompt.setText(getActivity().getResources().getString(getPrompt())+" "+friendship.getFriend().getName());

        builder.setTitle(getTitle())
                .setView(view)
                .setPositiveButton(getPositiveButtonLabel(), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        handleFriendEdit(true);
                    }
                })
                .setNegativeButton(getNegativeButtonLabel(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        handleFriendEdit(false);
                    }
                });

        return builder.create();
    }

    private int getTitle() {
        switch (tabType) {
            case INVITES:
                return R.string.friends_dialog_invite_title;
            case FRIENDS:
                return R.string.friends_dialog_friend_title;
            case REQUESTS:
                return R.string.friends_dialog_request_title;
            default:
                return 0;
        }
    }

    private int getPrompt() {
        switch (tabType) {
            case INVITES:
                return R.string.friends_dialog_invite_prompt;
            case FRIENDS:
                return R.string.friends_dialog_friend_prompt;
            case REQUESTS:
                return R.string.friends_dialog_request_prompt;
            default:
                return 0;
        }
    }

    private int getPositiveButtonLabel() {

        switch (tabType) {
            case INVITES:
                return R.string._accept;
            case FRIENDS:
                return R.string._delete;
            case REQUESTS:
                return R.string._discard;
            default:
                return 0;
        }
    }

    private int getNegativeButtonLabel() {

        switch (tabType) {
            case INVITES:
                return R.string._reject;
            case FRIENDS:
                return R.string._cancel;
            case REQUESTS:
                return R.string._cancel;
            default:
                return 0;
        }
    }

    private void handleFriendEdit(boolean positive) {

        FriendshipService service;

        switch (tabType) {
            case INVITES:

                service = new FriendshipServiceImpl(getActivity());
                if (positive) {
                    service.acceptFriendship(friendship);
                    dialogListener.onFriendAccepted();

                } else {
                    service.deleteFriendship(friendship);
                    dialogListener.onFriendDeleted();
                }
                break;

            case FRIENDS:

                if (positive) {
                    service = new FriendshipServiceImpl(getActivity());
                    service.deleteFriendship(friendship);
                    dialogListener.onFriendDeleted();
                }else{
                    getDialog().cancel();
                }
                break;

            case REQUESTS:

                if (positive) {
                    service = new FriendshipServiceImpl(getActivity());
                    service.deleteFriendship(friendship);
                    dialogListener.onFriendDeleted();
                }else{
                    getDialog().cancel();
                }
                break;
        }
    }

    public void init(FriendsTabType tabType, Friendship friendship){
        this.tabType = tabType;
        this.friendship = friendship;
    }
}