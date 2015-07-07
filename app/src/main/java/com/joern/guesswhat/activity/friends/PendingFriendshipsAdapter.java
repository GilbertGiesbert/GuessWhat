package com.joern.guesswhat.activity.friends;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joern.guesswhat.R;
import com.joern.guesswhat.common.SessionHelper;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.FriendshipState;
import com.joern.guesswhat.model.User;
import com.joern.guesswhat.persistence.database.FriendshipDao;
import com.joern.guesswhat.persistence.database.FriendshipDaoImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joern on 14.04.2015.
 */
public class PendingFriendshipsAdapter extends BaseAdapter{

    private List<Friendship> pendingFriendships;
    private FriendshipState state;

    private Context context;
    private LayoutInflater inflater;

    public PendingFriendshipsAdapter(Context context, FriendshipState state){
        this.context = context;
        this.state = state;

        pendingFriendships = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return pendingFriendships.size();
    }

    @Override
    public Friendship getItem(int position) {
        return pendingFriendships.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null){

            convertView = inflater.inflate(R.layout.pendingfriends_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tv_friend = (TextView) convertView.findViewById(R.id.tv_friend);
            viewHolder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Friendship friendship = pendingFriendships.get(position);
        viewHolder.tv_friend.setText(friendship.getFriend().getName());

        return convertView;
    }

    private class ViewHolder{
        public TextView tv_friend;
        public TextView tv_state;
    }

    public FriendshipState getState() {
        return state;
    }

    public void setState(FriendshipState state) {
        this.state = state;
    }

    public void  reload(){

        pendingFriendships.clear();

        User sessionUser = SessionHelper.getSessionUser(context);
        FriendshipDao dao = new FriendshipDaoImpl(context);

        List<Friendship> fList = dao.getFriendships(sessionUser, state);
        pendingFriendships.addAll(fList);
    }
}