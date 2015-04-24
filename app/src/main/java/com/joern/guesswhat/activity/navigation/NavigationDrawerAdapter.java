package com.joern.guesswhat.activity.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joern.guesswhat.R;

public class NavigationDrawerAdapter extends BaseAdapter {

    private Context context;

    static class ViewHolder{
        public ImageView icon;
        public TextView text;
    }

    public NavigationDrawerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return NavigationDrawerItem.values().length;
    }

    @Override
    public Object getItem(int position) {
        return NavigationDrawerItem.values()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null){

            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.navigation_row, parent, false);

            viewHolder.icon = (ImageView) convertView.findViewById(R.id.iv_navigationItem_icon);
            viewHolder.text = (TextView) convertView.findViewById(R.id.tv_navigationItem_text);
            convertView.setTag(viewHolder);
        }

        NavigationDrawerItem item = NavigationDrawerItem.values()[position];

        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.icon.setImageResource(item.getIconId());
        viewHolder.text.setText(context.getResources().getString(item.getLabelId()));

        return convertView;
    }
}