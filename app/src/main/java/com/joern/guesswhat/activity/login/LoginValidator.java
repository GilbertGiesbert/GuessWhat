package com.joern.guesswhat.activity.login;

import android.content.Context;

import com.joern.guesswhat.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by joern on 24.04.2015.
 */
public class LoginValidator {

    public enum InputType{
        GENERAL, EMAIL, USER_NAME_FOR_REGISTRATION, PASSWORD_FOR_REGISTRATION
    }

    private static final int MINIMUM_PASSWORD_LENGTH = 8;

    private static final String REGEX_VALID_USERNAME = "^[A-Za-z._0-9]+$";

    private static final String REGEX_VALID_EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";

    private static final String REGEX_CONTAINS_LETTRS = "^.*[A-Za-z]+.*$";

    private static final String REGEX_CONTAINS_NUMBERS = "^.*[0-9]+.*$";

    public static String validateInput(String input, InputType inputType, Context context){

        String validationFeedback = null;

        switch(inputType){

            case GENERAL:
                if(!isNotEmpty(input)) {
                    validationFeedback = context.getResources().getString(R.string._validation_hint_fieldEmpty);
                }
                break;

            case EMAIL:
                if(!isNotEmpty(input)) {
                    validationFeedback = context.getResources().getString(R.string._validation_hint_fieldEmpty);
                }else if(!matchesRegex(input, REGEX_VALID_EMAIL)){
                    validationFeedback = context.getResources().getString(R.string._validation_hint_invalidEmail);
                }
                break;

            case USER_NAME_FOR_REGISTRATION:

                if(!isNotEmpty(input)){
                    validationFeedback = context.getResources().getString(R.string._validation_hint_fieldEmpty);

                }else if(!matchesRegex(input, REGEX_VALID_USERNAME)){
                    validationFeedback = context.getResources().getString(R.string._validation_hint_validUserName);
                }
                break;

            case PASSWORD_FOR_REGISTRATION:
                if(!isNotEmpty(input)){
                    validationFeedback = context.getResources().getString(R.string._validation_hint_fieldEmpty);

                }else if(!isValidPassword(input)){
                    validationFeedback = context.getResources().getString(R.string._validation_hint_validPassword);
                }
                break;
        }
        return validationFeedback;
    }

    private static boolean isNotEmpty(String input){
        return input != null && !input.trim().isEmpty();
    }

    private static boolean isValidPassword(String password){
        return isNotEmpty(password) &&
                password.length() >= MINIMUM_PASSWORD_LENGTH &&
                matchesRegex(password,REGEX_CONTAINS_LETTRS) &&
                matchesRegex(password, REGEX_CONTAINS_NUMBERS);
    }

    private static boolean matchesRegex(String input, String regex){

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

    public static String validatePasswordConfirmation(String password, String passwordConfirmation, Context context){

        String validationFeedback = null;

        if(passwordConfirmation == null || !passwordConfirmation.equals(password)){
            validationFeedback = context.getResources().getString(R.string._validation_hint_passwordMismatch);
        }
        return validationFeedback;
    }
}