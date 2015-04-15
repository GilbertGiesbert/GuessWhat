package com.joern.guesswhat.activity.friends;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joern.guesswhat.R;
import com.joern.guesswhat.common.SessionHelper;
import com.joern.guesswhat.database.FriendshipDao;
import com.joern.guesswhat.database.FriendshipDaoImpl;
import com.joern.guesswhat.model.Friendship;
import com.joern.guesswhat.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joern on 14.04.2015.
 */
public class PendingFriendshipsAdapter extends BaseAdapter{

    private List<Friendship> pendingFriendships;
    private PendingFriendshipType type;

    private Context context;
    private LayoutInflater inflater;

    public PendingFriendshipsAdapter(Context context, PendingFriendshipType type){
        this.context = context;
        this.type = type;

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

            convertView = inflater.inflate(R.layout.pendingfriends_row, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(viewHolder);

        }else{

            viewHolder = (ViewHolder) convertView.getTag();
        }

        Friendship friendship = pendingFriendships.get(position);

        if(PendingFriendshipType.RECEIVED.equals(type)){
            viewHolder.tv.setText(friendship.geteMailRequester());
        }else{
            viewHolder.tv.setText(friendship.geteMailAcceptor());
        }

        return convertView;
    }

    private class ViewHolder{
        public TextView tv;
    }

    public PendingFriendshipType getType() {
        return type;
    }

    public void setType(PendingFriendshipType type) {
        this.type = type;
    }

    public void  reload(){

        pendingFriendships.clear();

        User sessionUser = SessionHelper.getSessionUser(context);
        FriendshipDao dao = new FriendshipDaoImpl(context);
        List<Friendship> fList = dao.getAllPendingFriendships(sessionUser, type);

        pendingFriendships.addAll(fList);
    }
}