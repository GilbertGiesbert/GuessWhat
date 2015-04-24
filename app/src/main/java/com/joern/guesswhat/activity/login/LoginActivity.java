package com.joern.guesswhat.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.main.MainActivity;
import com.joern.guesswhat.common.SessionHelper;
import com.joern.guesswhat.database.UserDao;
import com.joern.guesswhat.database.UserDaoImpl;
import com.joern.guesswhat.model.User;

/**
 * Created by joern on 13.04.2015.
 */
public class LoginActivity extends ActionBarActivity implements View.OnClickListener, LoginTaskListener {

    private enum State{
        INITIAL, LOGIN, REGISTRATION, PASSWORD_RECOVERY
    }

    private State state;

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    private TextView tv_editHint;
    private Button bt_login, bt_register, bt_recoverPassword, bt_submit;
    private EditText et_userName, et_email, et_password, et_passwordRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);

        tv_editHint = (TextView) findViewById(R.id.tv_editHint);

        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (Button) findViewById(R.id.bt_register);
        bt_recoverPassword = (Button) findViewById(R.id.bt_recoverPassword);
        bt_submit = (Button) findViewById(R.id.bt_submit);

        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        bt_recoverPassword.setOnClickListener(this);
        bt_submit.setOnClickListener(this);

        et_userName = (EditText) findViewById(R.id.et_userName);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_passwordRepeat = (EditText) findViewById(R.id.et_passwordRepeat);

        et_passwordRepeat.setImeOptions(EditorInfo.IME_ACTION_GO);

        setImeActionListeners();

        state = State.INITIAL;
        resetToInitialView();
    }

    @Override
    public void onBackPressed(){
        Log.d(LOG_TAG, "onBackPressed()");

        if(State.INITIAL.equals(state)){
            super.onBackPressed();

        }else{
            state = State.INITIAL;
            resetToInitialView();
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "onClick()");

        switch(v.getId()){

            case R.id.bt_login:
                state = State.LOGIN;
                showLoginFields();
                break;

            case R.id.bt_register:
                state = State.REGISTRATION;
                showRegistrationFields();
                break;

            case R.id.bt_recoverPassword:
                state = State.PASSWORD_RECOVERY;
                showPasswordRecoveryFields();
                break;

            case R.id.bt_submit:
                bt_submit();
                break;
        }
    }

    private void setImeActionListeners(){

        // for login
        et_userName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean actionHandled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    bt_submit();
                    actionHandled = true;
                }
                return actionHandled;
            }
        });

        // for registration
        et_passwordRepeat.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean actionHandled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    bt_submit();
                    actionHandled = true;
                }
                return actionHandled;
            }
        });

        // for password recovery
        et_email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean actionHandled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    bt_submit();
                    actionHandled = true;
                }
                return actionHandled;
            }
        });
    }

    private void showLoginFields(){
        Log.d(LOG_TAG, "showLoginFields()");

        tv_editHint.setVisibility(View.VISIBLE);
        tv_editHint.setText(getResources().getString(R.string.login_tv_hintLoginExistingUser));

        bt_login.setVisibility(View.GONE);
        bt_register.setVisibility(View.GONE);
        bt_recoverPassword.setVisibility(View.GONE);
        bt_submit.setVisibility(View.VISIBLE);
        bt_submit.setText(getResources().getString(R.string.login_bt_login));

        et_userName.setVisibility(View.VISIBLE);
        et_password.setVisibility(View.VISIBLE);

        et_userName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        et_password.setImeOptions(EditorInfo.IME_ACTION_GO);
    }

    private void showRegistrationFields(){
        Log.d(LOG_TAG, "showRegistrationFields()");

        tv_editHint.setVisibility(View.VISIBLE);
        tv_editHint.setText(getResources().getString(R.string.login_tv_hintNewUser));

        bt_login.setVisibility(View.GONE);
        bt_register.setVisibility(View.GONE);
        bt_recoverPassword.setVisibility(View.GONE);
        bt_submit.setVisibility(View.VISIBLE);
        bt_submit.setText(getResources().getString(R.string.login_bt_register));

        et_userName.setVisibility(View.VISIBLE);
        et_email.setVisibility(View.VISIBLE);
        et_password.setVisibility(View.VISIBLE);
        et_passwordRepeat.setVisibility(View.VISIBLE);

        et_email.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        et_password.setImeOptions(EditorInfo.IME_ACTION_NEXT);
    }

    private void showPasswordRecoveryFields(){
        Log.d(LOG_TAG, "showPasswordRecoveryFields()");

        tv_editHint.setVisibility(View.VISIBLE);
        tv_editHint.setText(getResources().getString(R.string.login_tv_hintRecoveryMail));

        bt_login.setVisibility(View.GONE);
        bt_register.setVisibility(View.GONE);
        bt_recoverPassword.setVisibility(View.GONE);
        bt_submit.setVisibility(View.VISIBLE);
        bt_submit.setText(getResources().getString(R.string.login_bt_recoverPassword));

        et_email.setVisibility(View.VISIBLE);
        et_email.setImeOptions(EditorInfo.IME_ACTION_SEND);
    }

    private void bt_submit() {

        switch(state){

            case LOGIN:
                doLogin();
                break;

            case REGISTRATION:
                doRegisterNewUser();
                break;

            case PASSWORD_RECOVERY:
                doPasswordRecovery();
                break;

            default:
                resetToInitialView();
                break;
        }

        if( State.LOGIN.equals(state) ){

            doRegisterNewUser();

        }else if( View.VISIBLE == et_password.getVisibility() ){



        }else{
            doPasswordRecovery();
        }
    }

    private void doRegisterNewUser(){

        String userName = et_userName.getText().toString();
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String passwordRepeat = et_passwordRepeat.getText().toString();

        boolean inputErrors = false;

        if(userName.isEmpty()){
            inputErrors = true;
            et_userName.setError("Empty name");

        }else if(!email.contains("@")) {
            inputErrors = true;
            et_email.setError("Invalid mail");

        }else if(password.isEmpty()){

            inputErrors = true;
            et_password.setError("Empty password");

        }else if(!password.equals(passwordRepeat)){
            inputErrors = true;
            et_passwordRepeat.setError("Password mismatch");
        }

        if(!inputErrors){

            UserDao userDao = new UserDaoImpl(this);

            Integer passwordHash = PasswordFactory.buildPasswordHash(password, userName, email);
            if(passwordHash == null){
                Toast.makeText(this, "Failed to register.", Toast.LENGTH_SHORT).show();
            }else{

                boolean userCreated = userDao.createUser(userName, email, passwordHash);
                if(userCreated){

                    User user = userDao.readUser(userName);
                    SessionHelper.startSession(this, user);
                    goToMain();

                }else{

                    if(userDao.readUser(userName) != null){

                        et_email.setError("User name already assigned");

                    }else{

                        Toast.makeText(this, "Failed to create new user", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void doLogin() {

        String userName = et_userName.getText().toString();
        String password = et_password.getText().toString();

        LoginTask loginTask = new LoginTask(this, this);
        loginTask.execute(userName, password);
    }

    @Override
    public void onLoginTaskDone(LoginResult result) {

        if(result.isLoginSuccessful()){
            SessionHelper.startSession(this, result.getUser());
            goToMain();

        }else{
            Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void doPasswordRecovery(){

        String email = et_email.getText().toString();

        UserDao userDao = new UserDaoImpl(this);
        User user = userDao.readUser(email);

        String msg = "Processing password recovery";

        if(user != null){

            msg = "Password recovery mail will be sent to "+email;
        }

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        resetToInitialView();
    }

    private void goToMain(){
        Log.d(LOG_TAG, "goToMain()");
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void resetToInitialView() {
        Log.d(LOG_TAG, "resetToInitialView()");

        tv_editHint.setVisibility(View.GONE);
        tv_editHint.setText("");

        bt_login.setVisibility(View.VISIBLE);
        bt_register.setVisibility(View.VISIBLE);
        bt_recoverPassword.setVisibility(View.VISIBLE);
        bt_submit.setVisibility(View.GONE);
        bt_submit.setText("");

        et_userName.setVisibility(View.GONE);
        et_email.setVisibility(View.GONE);
        et_password.setVisibility(View.GONE);
        et_passwordRepeat.setVisibility(View.GONE);

        et_userName.setText("");
        et_email.setText("");
        et_password.setText("");
        et_passwordRepeat.setText("");

    }
}