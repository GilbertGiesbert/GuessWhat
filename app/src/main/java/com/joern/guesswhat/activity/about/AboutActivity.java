package com.joern.guesswhat.activity.about;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.widget.TextView;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.navigation.NavigationDrawerActivity;

/**
 * Created by Geheim on 24.04.2015.
 */
public class AboutActivity extends NavigationDrawerActivity {

    private static final String LOG_TAG = AboutActivity.class.getSimpleName();

    private static final int UNICODE_SMILEY_HAPPY = 0x1F603;
    private static final int UNICODE_SMILEY_SAD = 0x1F61E;

    @Override
    protected int getMainContentLayoutId() {
        return R.layout.about_activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(sessionUser == null) return;

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(LOG_TAG, "failed to get package info", e);
        }

        if(pInfo != null){

            String versionInfo = getString(R.string._version)+": "+pInfo.versionName+"\n"+
                    "Your version is up to date. ";

            TextView tv_version = (TextView) findViewById(R.id.tv_version);
            tv_version.setText(versionInfo);

            String smiley = getSmiley(UNICODE_SMILEY_HAPPY);

            // double text size for smiley
            SpannableStringBuilder ssb = new SpannableStringBuilder(smiley);
            RelativeSizeSpan span = new RelativeSizeSpan(2);
            ssb.setSpan(span, 0, smiley.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_version.append(ssb);
        }
    }

    private String getSmiley(int unicode) {
        return new String(Character.toChars(unicode));
    }
}
