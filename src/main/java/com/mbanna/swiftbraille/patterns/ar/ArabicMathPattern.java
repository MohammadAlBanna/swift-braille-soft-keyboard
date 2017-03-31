package com.mbanna.swiftbraille.patterns.ar;

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

//Contains all Arabic math symbols
public class ArabicMathPattern extends Pattern {
    private String finalResultPattern = null;
    private Context context;
    private int soundResources = -1;
    private String patternPronounce = null;

    //--------------------------------------------------------------------------------------------//
    public ArabicMathPattern(Context context){
        this.context = context;
        Common.currentLanguage = Common.ARABIC_LANGUAGE_INPUT_METHOD;
        Common.currentLocaleLanguage = Common.arabicLocale;
        Common.isRTL = true;
    }
    //--------------------------------------------------------------------------------------------//
    @SuppressWarnings("All")
    public void setPattern(Set pattern) {
        if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(2))) {
            finalResultPattern = ",";
            patternPronounce = context.getString(R.string.decimal_dot_pattern);
            soundResources = context.getResources().getIdentifier("ar_math_decimal_dot", "raw", context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(3, 6))) {
            finalResultPattern = "-";
            patternPronounce = context.getString(R.string.minus_pattern);
            soundResources = context.getResources().getIdentifier("ar_math_minus","raw",context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(3, 4))) {
            finalResultPattern = "÷";
            patternPronounce = context.getString(R.string.divided_by_pattern);
            soundResources = context.getResources().getIdentifier("ar_math_ ","raw",context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(3, 5))) {
            finalResultPattern = "*";
            patternPronounce = context.getString(R.string.asterisk_pattern);
            soundResources = context.getResources().getIdentifier("ar_math_star","raw",context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 5))) {
            finalResultPattern = "+";
            patternPronounce = context.getString(R.string.plus_pattern);
            soundResources = context.getResources().getIdentifier("ar_math_plus","raw",context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 5))) {
            finalResultPattern = ">";
            patternPronounce = context.getString(R.string.greater_than_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 4, 6))) {
            finalResultPattern = "<";
            patternPronounce = context.getString(R.string.less_than_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3 ,6))) {
            finalResultPattern = "×";
            patternPronounce = context.getString(R.string.multiply_pattern);
            soundResources = context.getResources().getIdentifier("ar_math_multi","raw",context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 5, 6))) {
            finalResultPattern = ".";
            patternPronounce = context.getString(R.string.dot_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_dot","raw",context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 6))) {
            finalResultPattern = "(";
            patternPronounce = context.getString(R.string.opening_parenthesis_pattern);
            soundResources = context.getResources().getIdentifier("ar_math_open_paren","raw",context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(3, 4, 5))) {
            finalResultPattern = ")";
            patternPronounce = context.getString(R.string.closing_parenthesis_pattern);
            soundResources = context.getResources().getIdentifier("ar_math_closing_paren","raw",context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 5, 6))) {
            finalResultPattern = "=";
            patternPronounce = context.getString(R.string.equals_pattern);
            soundResources = context.getResources().getIdentifier("ar_math_equals","raw",context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(3, 4, 5, 6))) {
            finalResultPattern = "#";
            patternPronounce = context.getString(R.string.hash_pattern);
            soundResources = context.getResources().getIdentifier("ar_num_hash","raw",context.getPackageName());
        } else {
            finalResultPattern = null;
            patternPronounce = null;
            soundResources = -1;
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
        int classTitleSound = context.getResources().getIdentifier("ar_message_math_keyboard", "raw", context.getPackageName());
        if (classTitleSound != 0) {
            return classTitleSound;
        } else {
            return -1;
        }
    }
    //--------------------------------------------------------------------------------------------//
    public int getPatternSoundPronounce(){
        if(soundResources != 0){
            return soundResources;
        } else{
            return -1;
        }
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern nextPattern(Context context){
        return new ArabicSpecialPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern previousPattern(Context context){
        return new ArabicNumbersPattern(context);
    }
}
