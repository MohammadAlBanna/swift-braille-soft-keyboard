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

//Just contain English small characters pattern
public class EnglishSmallCharPattern extends Pattern {
    private Context context;
    private String patternPronounce = null;
    private String finalResultPattern = null;
    //--------------------------------------------------------------------------------------------//
    public EnglishSmallCharPattern(Context context){
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
            finalResultPattern = "a";
            patternPronounce = null;
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
            finalResultPattern = "b";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 4))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "c";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "e";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 4))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "i";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 3))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "k";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 4, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "d";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 4))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "f";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "h";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 4, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "j";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 3))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "l";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 4))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "m";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "o";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 4))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "s";
            patternPronounce = null;
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
            finalResultPattern = "u";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 4, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "g";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "n";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 4))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "p";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "r";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "t";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 6))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "v";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "w";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 4, 6))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "x";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 5, 6))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "z";
            patternPronounce = null;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "q";
            patternPronounce = null;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 3, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.englishLocale;
            finalResultPattern = "y";
            patternPronounce = null;
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
        return patternPronounce != null ? patternPronounce : finalResultPattern;
    }
    //--------------------------------------------------------------------------------------------//
    public String getClassTitle(){
        Common.currentLocaleLanguage = Common.currentSystemLocaleLanguage;
        return context.getString(R.string.english_small_letters_keyboard);
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
        return new EnglishCapitalCharPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern previousPattern(Context context){
        return new EnglishSpecialPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
}
