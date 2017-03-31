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

public class EnglishSpecialPattern extends Pattern {
    private Context context;
    private String patternPronounce = null;
    private String finalResultPattern = null;
    //--------------------------------------------------------------------------------------------//
    public EnglishSpecialPattern(Context context){
        this.context = context;
        Common.currentLanguage = Common.ENGLISH_LANGUAGE_INPUT_METHOD;
        Common.currentLocaleLanguage = Common.englishLocale;
        Common.isRTL = false;
    }
    //--------------------------------------------------------------------------------------------//
    @SuppressWarnings("All")
    public void setPattern(Set pattern) {
        if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(2))) {
            finalResultPattern = ",";
            patternPronounce = context.getString(R.string.comma_pattern);
        } else if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(3))) {
            finalResultPattern = "'";
            patternPronounce = context.getString(R.string.apostrophe_pattern);
        } else if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(4))) {
            finalResultPattern = "@";
            patternPronounce = context.getString(R.string.at_pattern);
        }  else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 5))) {
            finalResultPattern = ":";
            patternPronounce = context.getString(R.string.colon_pattern);
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 3))) {
            finalResultPattern = ";";
            patternPronounce = context.getString(R.string.semicolon_pattern);
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(3, 4))) {
            finalResultPattern = "/";
            patternPronounce = context.getString(R.string.slash_pattern);
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 6))) {
            finalResultPattern = "\\";
            patternPronounce = context.getString(R.string.back_slash_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 5))) {
            finalResultPattern = ">";
            patternPronounce = context.getString(R.string.right_arrowhead_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 4, 6))) {
            finalResultPattern = "<";
            patternPronounce = context.getString(R.string.left_arrowhead_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 5, 6))) {
            finalResultPattern = ".";
            patternPronounce = context.getString(R.string.dot_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 6))) {
            finalResultPattern = "(";
            patternPronounce = context.getString(R.string.opening_parenthesis_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(3, 4, 5))) {
            finalResultPattern = ")";
            patternPronounce = context.getString(R.string.closing_parenthesis_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(4, 5, 6))) {
            finalResultPattern = "_";
            patternPronounce = context.getString(R.string.underscore_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(3, 5, 6))) {
            finalResultPattern = "%";
            patternPronounce = context.getString(R.string.percent_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 6))) {
            finalResultPattern = "?";
            patternPronounce = context.getString(R.string.question_mark_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 5))) {
            finalResultPattern = "!";
            patternPronounce = context.getString(R.string.exclamation_mark_pattern);
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 5, 6))) {
            finalResultPattern = "\"";
            patternPronounce = context.getString(R.string.double_quotation_pattern);
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(3, 4, 5, 6))) {
            finalResultPattern = "#";
            patternPronounce = context.getString(R.string.hash_pattern);
        } else if (pattern.size() == 6 && pattern.containsAll(Arrays.asList(1, 2, 3, 4, 5, 6))) {
            finalResultPattern = "&";
            patternPronounce = context.getString(R.string.and_pattern);
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
        return context.getString(R.string.special_symbols_keyboard);
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
        return new EnglishSmallCharPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern previousPattern(Context context){
        return new EnglishMathPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
}
