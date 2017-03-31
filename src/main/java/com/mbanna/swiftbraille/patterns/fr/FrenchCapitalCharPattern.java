package com.mbanna.swiftbraille.patterns.fr;

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

public class FrenchCapitalCharPattern extends Pattern {
    private Context context;
    private String patternPronounce = null;
    private String finalResultPattern = null;

    //--------------------------------------------------------------------------------------------//
    public FrenchCapitalCharPattern(Context context){
        this.context = context;
        Common.currentLanguage = Common.FRENCH_LANGUAGE_INPUT_METHOD;
        Common.currentLocaleLanguage = Common.frenchLocale;
        Common.isRTL = false;
    }
    //--------------------------------------------------------------------------------------------//
    @SuppressWarnings("All")
    public void setPattern(Set pattern) {
        if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(1))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
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
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "B";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 4))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "C";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 4))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "I";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "E";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Â";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 3))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "K";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(3, 4))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Ì";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 4, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "D";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 4))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "F";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "H";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 4, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "J";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 3))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "L";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 4))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "M";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "O";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 4))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
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
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "U";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Ê";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 4, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Î";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Û";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 4, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Œ";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(3, 4, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Ä";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(3, 4, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Ò";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 4, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "G";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "N";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 4))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "P";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "R";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "T";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "V";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "W";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 4, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "X";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Z";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 4, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "È";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Ô";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 4, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Ë";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Ü";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 3, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Y";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 3, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "À";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 3, 4, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Ç";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(2, 3, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Ù";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(2, 3, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Ù";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "Ï";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 6 && pattern.containsAll(Arrays.asList(1, 2, 3, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "É";
            patternPronounce = context.getString(R.string.in_uppercase) + " " + finalResultPattern;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
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
        return context.getString(R.string.french_capital_letters_keyboard);
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
        return new FrenchNumbersPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern previousPattern(Context context){
        return new FrenchSmallCharPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
}
