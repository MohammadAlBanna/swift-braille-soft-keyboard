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

public class EnglishNumbersPattern extends Pattern {
    private Context context;
    private String patternPronounce = null;
    private String finalResultPattern = null;
    //--------------------------------------------------------------------------------------------//
    public EnglishNumbersPattern(Context context){
        this.context = context;
        Common.isRTL = false;
        Common.currentLanguage = Common.ENGLISH_LANGUAGE_INPUT_METHOD;
        Common.currentLocaleLanguage = Common.englishLocale;
    }
    //--------------------------------------------------------------------------------------------//
    @SuppressWarnings("All")
    public void setPattern(Set pattern) {
        if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(1))) {
            finalResultPattern = "1";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 2))) {
            finalResultPattern = "2";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 4))) {
            finalResultPattern = "3";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 5))) {
            finalResultPattern = "5";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 4))) {
            finalResultPattern = "9";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 4, 5))) {
            finalResultPattern = "4";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 4))) {
            finalResultPattern = "6";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 5))) {
            finalResultPattern = "8";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 4, 5))) {
            finalResultPattern = "0";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 4, 5))) {
            finalResultPattern = "7";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(3, 4, 5, 6))) {
            finalResultPattern = "#";
            patternPronounce = context.getString(R.string.hash_pattern);
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
        return context.getString(R.string.numbers_keyboard);
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
        return new EnglishMathPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern previousPattern(Context context){
        return new EnglishCapitalCharPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
}
