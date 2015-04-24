package com.joern.guesswhat.activity.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.joern.guesswhat.database.UserDao;
import com.joern.guesswhat.database.UserDaoImpl;
import com.joern.guesswhat.model.User;

/**
 * Created by joern on 13.04.2015.
 */
public class LoginTask extends AsyncTask<String, String, LoginResult>{

    private static final String LOG_TAG = LoginTask.class.getSimpleName();

    private Context context;
    private ProgressDialog dialog;
    private LoginTaskListener loginTaskListener;

    public LoginTask(Context context, LoginTaskListener loginTaskListener){
        super();
        this.context=context;
        this.loginTaskListener = loginTaskListener;
    }

    @Override
    protected void onPreExecute() {
        dialog=new ProgressDialog(context);
        dialog.setTitle("Login");
        dialog.setMessage("Processing login...");
        dialog.show();
    }

    @Override
    protected LoginResult doInBackground(String... params) {
        Log.d(LOG_TAG, "doInBackground()");

        LoginResult result = new LoginResult();

        if(params == null || params.length < 2){
            result.setErrorMessage("Internal error");

        }else{

            String userName = params[0];
            String password = params[1];

            UserDao userDao = new UserDaoImpl(context);
            User user = userDao.readUser(userName);

            if(user == null) {
                result.setErrorMessage("No such user!");
            }else{

                Integer passwordHash = PasswordFactory.buildPasswordHash(password, user);
                if (user.getPasswordHash() == passwordHash) {
                    result.setLoginSuccessful(true);
                    result.setUser(user);
                }else{
                    result.setErrorMessage("Wrong password!");
                }
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(LoginResult result) {

        loginTaskListener.onLoginTaskDone(result);
        dialog.dismiss();
    }
}