package com.joern.guesswhat.activity.login;

import android.content.Context;

import com.joern.guesswhat.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by joern on 24.04.2015.
 */
public class InputValidator {

    public enum InputType{
        LOGIN_USER_NAME, LOGIN_PASSWORD,
        REGISTRATION_USER_NAME, REGISTRATION_EMAIL, REGISTRATION_PASSWORD, REGISTRATION_PASSWORD_CONFIRMATION,
        PASSWORD_RECOVERY_EMAIL
    }

    private static final String REGEX_VALID_USERNAME = "[A-Za-z._0-9]+";

    private static final String REGEX_VALID_EMAIL = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$";

    public static String validateInput(String input, InputType inputType, Context context){

        String validationFeedback = null;

        switch(inputType){

            case LOGIN_USER_NAME:

                if(!isNotEmpty(input)){
                    validationFeedback = context.getResources().getString(R.string._validation_hint_fieldEmpty);

                }else if(!matchesRegex(input, REGEX_VALID_USERNAME)){
                    validationFeedback = context.getResources().getString(R.string._validation_hint_validUserName);
                }
                break;
        }
        return validationFeedback;
    }

    public static boolean isNotEmpty(String input){
        return input != null && !input.trim().isEmpty();
    }

    public static boolean matchesRegex(String input, String regex){

        Pattern pattern;
        try{
            pattern = Pattern.compile(regex);
        }catch(Exception ex){

            // in case input is null or regex is invalid
            return false;
        }
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}