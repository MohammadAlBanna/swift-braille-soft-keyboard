package com.mbanna.swiftbraille.patterns.es;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.content.Context;
import android.view.inputmethod.InputConnection;

import com.mbanna.swiftbraille.Common;
import com.mbanna.swiftbraille.patterns.Pattern;
import com.mbanna.swiftbraille.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class SpanishCapitalCharPattern extends Pattern {
    private Context context;
    private String patternPronounce = null;
    private String finalResultPattern = null;
    private InputConnection inputConnection = null;
    //--------------------------------------------------------------------------------------------//
    public SpanishCapitalCharPattern(Context context, InputConnection inputConnection){
        this.context = context;
        Common.currentLanguage = Common.SPANISH_LANGUAGE_INPUT_METHOD;
        Common.currentLocaleLanguage = Common.spanishLocale;
        Common.isRTL = false;
        this.inputConnection = inputConnection;
    }
    //--------------------------------------------------------------------------------------------//
    @SuppressWarnings("All")
    public void setPattern(Set pattern) {
        if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(1))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "A";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(2))) {
            Common.currentLocaleLanguage = Common.currentSystemLocaleLanguage;
            finalResultPattern = ",";
            patternPronounce = context.getString(R.string.comma_pattern);
        } else if (pattern.size() == 1 && pattern.containsAll(Arrays.asList(3))) {
            Common.currentLocaleLanguage = Common.currentSystemLocaleLanguage;
            finalResultPattern = ".";
            patternPronounce = context.getString(R.string.dot_pattern);
        } else if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(4))) {
            Common.currentLocaleLanguage = Common.currentSystemLocaleLanguage;
            finalResultPattern = "'";
            patternPronounce = context.getString(R.string.apostrophe_pattern);
        } else if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(5))) {
            finalResultPattern = "@";
            patternPronounce = context.getString(R.string.at_pattern);
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 2))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "B";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 4))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "C";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 4))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "I";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 6))) {
            if(Common.isFirstSentenceIndicate(inputConnection)){
                finalResultPattern = "¿";
                patternPronounce = context.getString(R.string.es_open_question);
            } else{
                finalResultPattern = "?";
                patternPronounce = context.getString(R.string.es_close_question);
            }
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "E";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 3))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "K";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(3, 4))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "Í";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 4, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "D";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 4))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "F";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "H";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 4, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "J";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 3))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "L";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 4))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "M";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "O";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 4))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "S";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 5))) {
            if(Common.isFirstSentenceIndicate(inputConnection)){
                finalResultPattern = "¡";
                patternPronounce = context.getString(R.string.es_open_exclamation);
            } else{
                finalResultPattern = "!";
                patternPronounce = context.getString(R.string.es_close_exclamation);
            }
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 6))) {
            Common.currentLocaleLanguage = Common.currentSystemLocaleLanguage;
            finalResultPattern = "?";
            patternPronounce = context.getString(R.string.question_mark_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "U";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 4, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "G";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "N";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 4))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "P";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "R";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "T";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "V";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "W";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 4, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "X";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 5, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "Z";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 4, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "É";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 5, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "Ü";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 3, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "Y";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 3, 5, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "Á";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(2, 3, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "Ú";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "Ñ";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "Q";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else {
            finalResultPattern = null;
            patternPronounce = null;
        }
    }
    //--------------------------------------------------------------------------------------------//
    public String getPatternResult() {
        return finalResultPattern;
    }
    //--------------------------------------------------------------------------------------------//
    public String getPatternPronounce(){
        return patternPronounce;
    }
    //--------------------------------------------------------------------------------------------//
    public String getClassTitle(){
        Common.currentLocaleLanguage = Common.currentSystemLocaleLanguage;
        return context.getString(R.string.spanish_capital_letters_keyboard);
    }
    //--------------------------------------------------------------------------------------------//
    public int getClassTitleSoundPath() {
        return -1;
    }
    //--------------------------------------------------------------------------------------------//
    public int getPatternSoundPronounce(){
        return -1;
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern nextPattern(Context context){
        return new SpanishNumbersPattern(context, inputConnection);
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern previousPattern(Context context){
        return new SpanishSmallCharPattern(context, inputConnection);
    }
    //--------------------------------------------------------------------------------------------//
}
