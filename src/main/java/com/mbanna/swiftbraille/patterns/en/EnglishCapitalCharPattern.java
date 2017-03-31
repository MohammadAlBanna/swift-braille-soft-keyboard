package com.mbanna.swiftbraille.patterns.en;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.content.Context;

import com.mbanna.swiftbraille.Common;
import com.mbanna.swiftbraille.patterns.Pattern;
import com.mbanna.swiftbraille.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class EnglishCapitalCharPattern extends Pattern {
    private Context context;
    private String patternPronounce = null;
    private String finalResultPattern = null;

    //--------------------------------------------------------------------------------------------//
    public EnglishCapitalCharPattern(Context context){
        this.context = context;
        Common.currentLanguage = Common.ENGLISH_LANGUAGE_INPUT_METHOD;
        Common.currentLocaleLanguage = Common.englishLocale;
        Common.isRTL = false;
    }
    //--------------------------------------------------------------------------------------------//
    @SuppressWarnings("All")
    public void setPattern(Set pattern) {
        if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(1))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "A";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(2))) {
            Common.currentLocaleLanguage = Common.currentSystemLocaleLanguage;
            finalResultPattern = ",";
            patternPronounce = context.getString(R.string.comma_pattern);
        } else if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(3))) {
            Common.currentLocaleLanguage = Common.currentSystemLocaleLanguage;
            finalResultPattern = "'";
            patternPronounce = context.getString(R.string.apostrophe_pattern);
        } else if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(4))) {
            finalResultPattern = "@";
            patternPronounce = context.getString(R.string.at_pattern);
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 2))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "B";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 4))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "C";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 4))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "I";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "E";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 3))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "K";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 4, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "D";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 4))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "F";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "H";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 4, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "J";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 3))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "L";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 4))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "M";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "O";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 4))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "S";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 5))) {
            Common.currentLocaleLanguage = Common.currentSystemLocaleLanguage;
            finalResultPattern = "!";
            patternPronounce = context.getString(R.string.exclamation_mark_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 6))) {
            Common.currentLocaleLanguage = Common.currentSystemLocaleLanguage;
            finalResultPattern = "?";
            patternPronounce = context.getString(R.string.question_mark_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 5, 6))) {
            Common.currentLocaleLanguage = Common.currentSystemLocaleLanguage;
            finalResultPattern = ".";
            patternPronounce = context.getString(R.string.dot_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 6))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "U";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 4, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "G";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "N";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 4))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "P";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "R";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "T";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 6))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "V";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "W";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 4, 6))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "X";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 5, 6))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "Z";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 3, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "Y";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
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
        return context.getString(R.string.english_capital_letters_keyboard);
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
        return new EnglishNumbersPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern previousPattern(Context context){
        return new EnglishSmallCharPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
}
