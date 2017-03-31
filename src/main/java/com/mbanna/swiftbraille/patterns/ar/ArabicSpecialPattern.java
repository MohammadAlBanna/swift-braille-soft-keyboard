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

//Contains all general symbols in Arabic syntax
public class ArabicSpecialPattern extends Pattern {
    private String finalResultPattern = null;
    private String patternPronounce = null;
    private int soundResources = -1;
    private Context context;

    //--------------------------------------------------------------------------------------------//
    public ArabicSpecialPattern(Context context){
        this.context = context;
        Common.currentLanguage = Common.ARABIC_LANGUAGE_INPUT_METHOD;
        Common.currentLocaleLanguage = Common.arabicLocale;
        Common.isRTL = true;
    }
    //--------------------------------------------------------------------------------------------//
    @SuppressWarnings("All")
    public void setPattern(Set pattern) {
        if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(4))) {
            finalResultPattern = "@";
            patternPronounce = context.getString(R.string.at_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_at","raw",context.getPackageName());
        } else if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(5))) {
            finalResultPattern = "،";
            patternPronounce = context.getString(R.string.comma_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_comma", "raw", context.getPackageName());
        } else if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(3))) {
            finalResultPattern = "'";
            patternPronounce = context.getString(R.string.apostrophe_pattern);
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 5))) {
            finalResultPattern = ":";
            patternPronounce = context.getString(R.string.comma_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_colon","raw",context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(5, 6))) {
            finalResultPattern = "؛";
            patternPronounce = context.getString(R.string.semicolon_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_semi_colon","raw",context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(3, 4))) {
            finalResultPattern = "/";
            patternPronounce = context.getString(R.string.slash_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_slash","raw",context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 6))) {
            finalResultPattern = "\\";
            patternPronounce = context.getString(R.string.back_slash_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_back_slash","raw",context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 5))) {
            finalResultPattern = ">";
            patternPronounce = context.getString(R.string.right_arrowhead_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 4, 6))) {
            finalResultPattern = "<";
            patternPronounce = context.getString(R.string.left_arrowhead_pattern);
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(3, 5, 6))) {
            finalResultPattern = "%";
            patternPronounce = context.getString(R.string.percent_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_percent","raw",context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 6))) {
            finalResultPattern = "؟";
            patternPronounce = context.getString(R.string.question_mark_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_question","raw",context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 5))) {
            finalResultPattern = "!";
            patternPronounce = context.getString(R.string.exclamation_mark_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_exclamation","raw",context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 5, 6))) {
            finalResultPattern = ".";
            patternPronounce = context.getString(R.string.dot_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_dot","raw",context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 6))) {
            finalResultPattern = "(";
            patternPronounce = context.getString(R.string.opening_parenthesis_pattern);
            soundResources = context.getResources().getIdentifier("ar_math_open_paren", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(3, 4, 5))) {
            finalResultPattern = ")";
            patternPronounce = context.getString(R.string.closing_parenthesis_pattern);
            soundResources = context.getResources().getIdentifier("ar_math_closing_paren","raw",context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(4, 5, 6))) {
            finalResultPattern = "_";
            patternPronounce = context.getString(R.string.underscore_pattern);
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 5, 6))) {
            finalResultPattern = "\"";
            patternPronounce = context.getString(R.string.question_mark_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_double_quotation","raw",context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(3, 4, 5, 6))) {
            finalResultPattern = "#";
            patternPronounce = context.getString(R.string.hash_pattern);
            soundResources = context.getResources().getIdentifier("ar_num_hash","raw",context.getPackageName());
        } else if (pattern.size() == 6 && pattern.containsAll(Arrays.asList(1, 2, 3, 4, 5, 6))) {
            finalResultPattern = "&";
            patternPronounce = context.getString(R.string.and_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_and","raw",context.getPackageName());
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
        return context.getString(R.string.special_symbols_keyboard);
    }
    //--------------------------------------------------------------------------------------------//
    public int getClassTitleSoundPath(){
        int classTitleSound = context.getResources().getIdentifier("ar_message_special_keyboard","raw",context.getPackageName());
        if(classTitleSound != 0){
            return classTitleSound;
        } else{
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
        return new ArabicCharPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern previousPattern(Context context){
        return new ArabicMathPattern(context);
    }
}
