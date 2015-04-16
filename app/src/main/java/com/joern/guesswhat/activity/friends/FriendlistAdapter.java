package com.joern.guesswhat.activity.friends;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joern.guesswhat.R;
import com.joern.guesswhat.common.SessionHelper;
import com.joern.guesswhat.database.FriendshipDao;
import com.joern.guesswhat.database.FriendshipDaoImpl;
import com.joern.guesswhat.model.FriendshipRequestType;
import com.joern.guesswhat.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joern on 14.04.2015.
 */
public class FriendlistAdapter extends BaseAdapter{

    private List<User> friendList;

    private Context context;
    private LayoutInflater inflater;

    private class ViewHolder{
        public ImageView iv_profilePicture;
        public TextView tv_name;
        public TextView tv_eMail;
    }

    public FriendlistAdapter(Context context, FriendshipRequestType friendshipRequester){

        this.context = context;
        this.friendList = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public User getItem(int position) {
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

        User friend = friendList.get(position);

        Drawable profilePicture = getProfilePicture();
        if(profilePicture != null){
            viewHolder.iv_profilePicture.setImageDrawable(profilePicture);
        }
        viewHolder.tv_name.setText(friend.getName());
        viewHolder.tv_eMail.setText(friend.getEmail());

        return convertView;
    }

    private Drawable getProfilePicture() {

        // TODO
        return null;
    }


    public void  reload(){

        friendList.clear();

        User sessionUser = SessionHelper.getSessionUser(context);
        FriendshipDao dao = new FriendshipDaoImpl(context);
        List<User> fList = dao.getFriends(sessionUser);

        friendList.addAll(fList);
    }
}