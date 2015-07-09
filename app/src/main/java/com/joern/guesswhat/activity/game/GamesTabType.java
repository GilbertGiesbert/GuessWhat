package com.joern.guesswhat.activity.game;

import android.content.Context;

import com.joern.guesswhat.R;

/**
 * Created by joern on 09.07.2015.
 */
public enum GamesTabType {

    REVIEW(0, R.string.games_tab_review_title, R.string.games_tab_review_subtitle),
    PLAY(1, R.string.games_tab_play_title, R.string.games_tab_play_subtitle),
    CREATE(2, R.string.games_tab_create_title, R.string.games_tab_create_subtitle);

    private int tabPosition;
    private int titleResource;
    private int subtitleResource;

    private GamesTabType(int tabPosition, int titleResource, int subtitleResource) {
        this.tabPosition = tabPosition;
        this.titleResource = titleResource;
        this.subtitleResource = subtitleResource;
    }

    public int getTabPosition(){
        return tabPosition;
    }

    public String getTitle(Context context){
        return context.getResources().getString(titleResource);
    }

    public String getSubtitle(Context context){
        return context.getResources().getString(subtitleResource);
    }

    public static GamesTabType valueOf(int position){

        for(GamesTabType type: GamesTabType.values()){
            if(type.getTabPosition() == position){
                return type;
            }
        }
        return null;
    }
}