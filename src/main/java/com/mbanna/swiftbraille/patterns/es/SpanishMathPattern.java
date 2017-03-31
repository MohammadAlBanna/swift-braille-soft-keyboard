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

public class SpanishMathPattern extends Pattern {
    private Context context;
    private String patternPronounce = null;
    private String finalResultPattern = null;
    private InputConnection inputConnection = null;

    //--------------------------------------------------------------------------------------------//
    public SpanishMathPattern(Context context, InputConnection inputConnection){
        this.context = context;
        Common.currentLanguage = Common.SPANISH_LANGUAGE_INPUT_METHOD;
        Common.currentLocaleLanguage = Common.spanishLocale;
        Common.isRTL = false;
        this.inputConnection = inputConnection;
    }
    //--------------------------------------------------------------------------------------------//
    @SuppressWarnings("All")
    public void setPattern(Set pattern) {
        if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(2))) {
            finalResultPattern = ",";
            patternPronounce = context.getString(R.string.comma_pattern);
        } else if (pattern.size() == 1 && pattern.containsAll(Arrays.asList(3))) {
            finalResultPattern = ".";
            patternPronounce = context.getString(R.string.decimal_dot_pattern);
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(3, 6))) {
            finalResultPattern = "-";
            patternPronounce = context.getString(R.string.minus_pattern);
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(3, 4))) {
            finalResultPattern = "÷";
            patternPronounce = context.getString(R.string.divided_by_pattern);
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(3, 5))) {
            finalResultPattern = "*";
            patternPronounce = context.getString(R.string.asterisk_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 5))) {
            finalResultPattern = ">";
            patternPronounce = context.getString(R.string.greater_than_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 4, 6))) {
            finalResultPattern = "<";
            patternPronounce = context.getString(R.string.less_than_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 5))) {
            finalResultPattern = "+";
            patternPronounce = context.getString(R.string.plus_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3 ,6))) {
            finalResultPattern = "×";
            patternPronounce = context.getString(R.string.multiply_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 6))) {
            finalResultPattern = "(";
            patternPronounce = context.getString(R.string.opening_parenthesis_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(3, 4, 5))) {
            finalResultPattern = ")";
            patternPronounce = context.getString(R.string.closing_parenthesis_pattern);
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 5, 6))) {
            finalResultPattern = "=";
            patternPronounce = context.getString(R.string.equals_pattern);
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
        return patternPronounce;
    }
    //--------------------------------------------------------------------------------------------//
    public String getClassTitle(){
        return context.getString(R.string.math_symbols_keyboard);
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
        return new SpanishSpecialPattern(context, inputConnection);
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern previousPattern(Context context){
        return new SpanishNumbersPattern(context, inputConnection);
    }
    //--------------------------------------------------------------------------------------------//
}
