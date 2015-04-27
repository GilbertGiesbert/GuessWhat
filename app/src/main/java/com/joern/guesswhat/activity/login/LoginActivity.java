package com.joern.guesswhat.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.joern.guesswhat.R;
import com.joern.guesswhat.activity.game.GamesActivity;
import com.joern.guesswhat.common.SessionHelper;

/**
 * Created by joern on 13.04.2015.
 */
public class LoginActivity extends ActionBarActivity implements View.OnClickListener, LoginTaskListener {

    private enum State{
        INITIAL, SIGN_IN, CREATE_ACCOUNT, RECOVER_PASSWORD
    }

    private State state;

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    private TextView tv_inputHint;
    private Button bt_signIn, bt_createAccount, bt_recoverPassword;
    private EditText et_userName, et_email, et_password, et_passwordConfirm;
    private LinearLayout ll_buttonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);

        tv_inputHint = (TextView) findViewById(R.id.tv_editHint);

        et_userName = (EditText) findViewById(R.id.et_userName);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_passwordConfirm = (EditText) findViewById(R.id.et_passwordRepeat);

        ll_buttonContainer = (LinearLayout) findViewById(R.id.ll_buttonContainer);

        bt_signIn = (Button) findViewById(R.id.bt_signIn);
        bt_createAccount = (Button) findViewById(R.id.bt_createAccount);
        bt_recoverPassword = (Button) findViewById(R.id.bt_recoverPassword);

        bt_signIn.setOnClickListener(this);
        bt_createAccount.setOnClickListener(this);
        bt_recoverPassword.setOnClickListener(this);

        initImeActionListeners();

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

            case R.id.bt_signIn:
                if(State.SIGN_IN.equals(state)){
                    doSignIn();
                }else{
                    state = State.SIGN_IN;
                    showSignIn();
                }
                break;

            case R.id.bt_createAccount:
                if(State.CREATE_ACCOUNT.equals(state)){
                    doCreateAccount();
                }else{
                    state = State.CREATE_ACCOUNT;
                    showCreateAccount();
                }
                break;

            case R.id.bt_recoverPassword:
                if(State.RECOVER_PASSWORD.equals(state)){
                    doRecoverPassword();
                }else{
                    state = State.RECOVER_PASSWORD;
                    showPasswordRecoveryFields();
                }
                break;
        }
    }

    private void initImeActionListeners(){

        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean actionHandled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    onClick(bt_signIn);
                    actionHandled = true;
                }
                return actionHandled;
            }
        });

        et_passwordConfirm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean actionHandled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    onClick(bt_createAccount);
                    actionHandled = true;
                }
                return actionHandled;
            }
        });

        et_email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean actionHandled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    onClick(bt_recoverPassword);
                    actionHandled = true;
                }
                return actionHandled;
            }
        });
    }

    private void showSignIn(){
        Log.d(LOG_TAG, "showSignIn()");

        et_userName.setVisibility(View.VISIBLE);
        et_password.setVisibility(View.VISIBLE);

        et_userName.setError(null);
        et_password.setError(null);

        et_userName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        et_password.setImeOptions(EditorInfo.IME_ACTION_GO);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_buttonContainer.getLayoutParams();
        params.gravity = Gravity.RIGHT;

        bt_createAccount.setVisibility(View.GONE);
        bt_recoverPassword.setVisibility(View.GONE);
    }

    private void showCreateAccount(){
        Log.d(LOG_TAG, "showCreateAccount()");

        tv_inputHint.setVisibility(View.VISIBLE);
        tv_inputHint.setText(getResources().getString(R.string.login_tv_hintCreateAccount));

        et_userName.setVisibility(View.VISIBLE);
        et_email.setVisibility(View.VISIBLE);
        et_password.setVisibility(View.VISIBLE);
        et_passwordConfirm.setVisibility(View.VISIBLE);

        et_userName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        et_email.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        et_password.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        et_passwordConfirm.setImeOptions(EditorInfo.IME_ACTION_GO);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_buttonContainer.getLayoutParams();
        params.gravity = Gravity.RIGHT;

        bt_createAccount.setText(getResources().getString(R.string.login_bt_createAccount));

        bt_signIn.setVisibility(View.GONE);
        bt_recoverPassword.setVisibility(View.GONE);
    }

    private void showPasswordRecoveryFields(){
        Log.d(LOG_TAG, "showPasswordRecoveryFields()");

        tv_inputHint.setVisibility(View.VISIBLE);
        tv_inputHint.setText(getResources().getString(R.string.login_tv_hintRecoverPassword));

        et_email.setVisibility(View.VISIBLE);
        et_email.setError(null);
        et_email.setImeOptions(EditorInfo.IME_ACTION_GO);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_buttonContainer.getLayoutParams();
        params.gravity = Gravity.RIGHT;

        bt_signIn.setVisibility(View.GONE);
        bt_createAccount.setVisibility(View.GONE);

        bt_recoverPassword.setText(getResources().getString(R.string.login_bt_recoverPassword));
    }



    private void doSignIn() {

        String userName = et_userName.getText().toString();
        String password = et_password.getText().toString();

        et_userName.setError(LoginValidator.validateInput(userName, LoginValidator.InputType.GENERAL, this));
        et_password.setError(LoginValidator.validateInput(password, LoginValidator.InputType.GENERAL, this));

        if( et_userName.getError() == null && et_password.getError() == null){
            LoginTask loginTask = new LoginTask(LoginTask.TaskType.LOGIN, this, this);
            loginTask.execute(userName, password);
        }
    }

    private void doCreateAccount(){

        String userName = et_userName.getText().toString();
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String passwordRepeat = et_passwordConfirm.getText().toString();

        et_userName.setError(LoginValidator.validateInput(userName, LoginValidator.InputType.USER_NAME_FOR_REGISTRATION, this));
        et_email.setError(LoginValidator.validateInput(email, LoginValidator.InputType.EMAIL, this));
        et_password.setError(LoginValidator.validateInput(password, LoginValidator.InputType.PASSWORD_FOR_REGISTRATION, this));
        et_passwordConfirm.setError(LoginValidator.validatePasswordConfirmation(password, passwordRepeat, this));


        if( et_userName.getError() == null &&
            et_email.getError() == null &&
            et_password.getError() == null &&
            et_passwordConfirm.getError() == null){

            LoginTask loginTask = new LoginTask(LoginTask.TaskType.REGISTRATION, this, this);
            loginTask.execute(userName, email, password);
        }
    }

    private void doRecoverPassword(){

        String email = et_email.getText().toString();
        et_email.setError(LoginValidator.validateInput(email, LoginValidator.InputType.EMAIL, this));

        if( et_email.getError() == null){

            LoginTask loginTask = new LoginTask(LoginTask.TaskType.PASSWORD_RECOVERY, this, this);
            loginTask.execute(email);
        }
    }

    @Override
    public void onLoginTaskDone(LoginTaskResult result) {

        if(!result.isSuccessful()){
            Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
        }else{

            if(LoginTask.TaskType.LOGIN.equals(result.getType()) || LoginTask.TaskType.REGISTRATION.equals(result.getType())){
                SessionHelper.startSession(this, result.getUser());
                goToMain();
            }else{
                resetToInitialView();
            }
        }
    }

    private void goToMain(){
        Log.d(LOG_TAG, "goToMain()");
        Intent intent = new Intent(this, GamesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void resetToInitialView() {
        Log.d(LOG_TAG, "resetToInitialView()");

        tv_inputHint.setVisibility(View.GONE);
        tv_inputHint.setText("");

        et_userName.setVisibility(View.GONE);
        et_email.setVisibility(View.GONE);
        et_password.setVisibility(View.GONE);
        et_passwordConfirm.setVisibility(View.GONE);

        et_userName.setText("");
        et_email.setText("");
        et_password.setText("");
        et_passwordConfirm.setText("");

        et_userName.setError(null);
        et_email.setError(null);
        et_password.setError(null);
        et_passwordConfirm.setError(null);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_buttonContainer.getLayoutParams();
        params.gravity = Gravity.CENTER;

        bt_signIn.setVisibility(View.VISIBLE);
        bt_createAccount.setVisibility(View.VISIBLE);
        bt_recoverPassword.setVisibility(View.VISIBLE);

        bt_createAccount.setText(getResources().getString(R.string.login_bt_newHere));
        bt_recoverPassword.setText(getResources().getString(R.string.login_bt_lostPassword));
    }
}