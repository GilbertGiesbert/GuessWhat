package com.joern.guesswhat.activity.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.joern.guesswhat.R;
import com.joern.guesswhat.database.UserDao;
import com.joern.guesswhat.database.UserDaoImpl;
import com.joern.guesswhat.model.User;

/**
 * Created by joern on 13.04.2015.
 */
public class LoginTask extends AsyncTask<String, String, LoginTaskResult>{

    public enum TaskType{
        LOGIN, REGISTRATION, PASSWORD_RECOVERY
    }

    private static final String LOG_TAG = LoginTask.class.getSimpleName();

    private TaskType type;
    private Context context;
    private ProgressDialog dialog;
    private LoginTaskListener loginTaskListener;

    public LoginTask(TaskType type, Context context, LoginTaskListener loginTaskListener){
        super();
        this.type = type;
        this.context=context;
        this.loginTaskListener = loginTaskListener;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setTitle("Login");
        dialog.setMessage("Processing login...");
        dialog.show();
    }

    @Override
    protected LoginTaskResult doInBackground(String... params) {
        Log.d(LOG_TAG, "doInBackground()");

        switch(type){

            case LOGIN:
                return doLogin(params);

            case REGISTRATION:
                return doRegistration(params);

            case PASSWORD_RECOVERY:
                return doPasswordRecovery(params);
        }
        return null;
    }

    @Override
    protected void onPostExecute(LoginTaskResult result) {

        loginTaskListener.onLoginTaskDone(result);
        dialog.dismiss();
    }

    private LoginTaskResult doLogin(String... params){
        Log.d(LOG_TAG, "doLogin()");

        LoginTaskResult result = new LoginTaskResult(TaskType.LOGIN);

        if(params == null || params.length < 2){
            result.setErrorMessage(context.getString(R.string._internalError));

        }else{

            String userName = params[0];
            String password = params[1];

            UserDao userDao = new UserDaoImpl(context);
            User user = userDao.readUser(userName);

            if(user == null) {
                result.setErrorMessage(context.getString(R.string.login_errorFailedLogin));
            }else{

                Integer passwordHash = PasswordFactory.buildPasswordHash(password, user);
                if (user.getPasswordHash() == passwordHash) {
                    result.setIsSuccessful(true);
                    result.setUser(user);
                }else{
                    result.setErrorMessage(context.getString(R.string.login_errorFailedLogin));
                }
            }
        }

        return result;
    }


    private LoginTaskResult doRegistration(String[] params) {
        Log.d(LOG_TAG, "doLogin()");

        LoginTaskResult result = new LoginTaskResult(TaskType.REGISTRATION);

        if(params == null || params.length < 3){
            result.setErrorMessage(context.getString(R.string._internalError));

        }else{

            String userName = params[0];
            String email = params[1];
            String password = params[2];

            UserDao userDao = new UserDaoImpl(context);

            Integer passwordHash = PasswordFactory.buildPasswordHash(password, userName, email);
            if(passwordHash == null){
                result.setErrorMessage(context.getString(R.string._internalError));
            }else{

                boolean userCreated = userDao.createUser(userName, email, passwordHash);
                if(userCreated){

                    User user = userDao.readUser(userName);
                    result.setIsSuccessful(true);
                    result.setUser(user);

                }else{

                    if(userDao.readUser(userName) != null){
                        result.setErrorMessage(context.getString(R.string.login_userNameTaken));

                    }else if(userDao.getUserByMail(email) != null){
                        result.setErrorMessage(context.getString(R.string.login_emailTaken));

                    }else{
                        result.setErrorMessage(context.getString(R.string._internalError));
                    }
                }
            }
        }

        return result;
    }


    private LoginTaskResult doPasswordRecovery(String[] params) {
        Log.d(LOG_TAG, "doPasswordRecovery()");

        LoginTaskResult result = new LoginTaskResult(TaskType.PASSWORD_RECOVERY);

        return result;
    }
}