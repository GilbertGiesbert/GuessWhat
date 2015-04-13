package com.joern.guesswhat.activity.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.joern.guesswhat.database.UserDaoImpl;

/**
 * Created by joern on 13.04.2015.
 */
public class LoginTask extends AsyncTask<String, String, String>{

    private static final String LOG_TAG = LoginTask.class.getSimpleName();

    private Context context;
    private ProgressDialog dialog;
    private LoginResultListener loginResultListener;

    public LoginTask(Context context, LoginResultListener loginResultListener){
        super();
        this.context=context;
        this.loginResultListener = loginResultListener;
    }

    @Override
    protected void onPreExecute() {
        dialog=new ProgressDialog(context);
        dialog.setTitle("Login");
        dialog.setMessage("Processing login...");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d(LOG_TAG, "doInBackground()");

        String result = null;

        if(params == null || params.length < 2){
            Log.d(LOG_TAG, "missing user name, email or password in params");

        }else{

            String userName = params[0];
            String password = params[1];

            UserDaoImpl userDao = new UserDaoImpl(context);


        }


        return result;
    }


}
