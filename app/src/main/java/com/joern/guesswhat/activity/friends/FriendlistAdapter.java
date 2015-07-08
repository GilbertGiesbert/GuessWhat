package com.joern.guesswhat.activity.friends;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joern.guesswhat.R;
import com.joern.guesswhat.common.SessionHelper;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.User;
import com.joern.guesswhat.persistence.database.FriendshipService;
import com.joern.guesswhat.persistence.database.FriendshipServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joern on 14.04.2015.
 */
public class FriendListAdapter extends BaseAdapter{

    private static final String LOG_TAG = FriendListAdapter.class.getSimpleName();

    private Context context;
    private LayoutInflater inflater;

    private FriendsTabType type;
    private List<Friendship> friendList;

    private class ViewHolder{
        public ImageView iv_profilePicture;
        public TextView tv_name;
        public TextView tv_eMail;
    }

    public FriendListAdapter(Context context, FriendsTabType type){

        this.context = context;
        this.inflater = LayoutInflater.from(context);

        this.type = type;
        this.friendList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Friendship getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null){

            convertView = inflater.inflate(R.layout.friends_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.iv_profilePicture = (ImageView) convertView.findViewById(R.id.iv_profilePicture);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_eMail = (TextView) convertView.findViewById(R.id.tv_eMail);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User friend = friendList.get(position).getFriend();
        Log.d(LOG_TAG, "Friend: "+friend.getName()+", Pos: "+position);

        Drawable profilePicture = getProfilePicture(position);
        if(profilePicture != null){
            viewHolder.iv_profilePicture.clearColorFilter();
            viewHolder.iv_profilePicture.setImageDrawable(profilePicture);
        }else{
            viewHolder.iv_profilePicture.setColorFilter(Color.argb(255, 0, 0, 0)); // black tint
        }
        viewHolder.tv_name.setText(friend.getName());
        viewHolder.tv_eMail.setText(friend.getEmail());

        return convertView;
    }

    private Drawable getProfilePicture(int position) {

        //TODO
        return null;
    }


    public void  reload(){

        friendList.clear();

        User sessionUser = SessionHelper.getSessionUser(context);
        FriendshipService fsService = new FriendshipServiceImpl(context);

        switch (type){
            case INVITES:
                friendList.addAll(fsService.getFriendshipInvites(sessionUser));
                break;
            case FRIENDS:
                friendList.addAll(fsService.getFriendshipsActive(sessionUser));
                break;
            case REQUESTS:
                friendList.addAll(fsService.getFriendshipRequests(sessionUser));
                break;
        }
    }
}