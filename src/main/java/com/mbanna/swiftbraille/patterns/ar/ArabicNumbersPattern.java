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

//Contains all numbers in Hendi for Arabic keyboard
public class ArabicNumbersPattern extends Pattern {
    private Context context;
    private int soundResources = -1;
    private String patternPronounce = null;
    private String finalResultPattern = null;

    //--------------------------------------------------------------------------------------------//
    public ArabicNumbersPattern(Context context){
        this.context = context;
        Common.currentLanguage = Common.ARABIC_LANGUAGE_INPUT_METHOD;
        Common.currentLocaleLanguage = Common.arabicLocale;
        Common.isRTL = true;
    }
    //--------------------------------------------------------------------------------------------//
    @SuppressWarnings("All")
    public void setPattern(Set pattern) {
        if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(1))) {
            finalResultPattern = "١";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_num_one","raw",context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 2))) {
            finalResultPattern = "٢";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_num_two","raw",context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 4))) {
            finalResultPattern = "٣";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_num_three","raw",context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 5))) {
            finalResultPattern = "٥";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_num_five","raw",context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 4))) {
            finalResultPattern = "٩";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_num_nine","raw",context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 4, 5))) {
            finalResultPattern = "٤";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_num_four","raw",context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 4))) {
            finalResultPattern = "٦";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_num_six","raw",context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 5))) {
            finalResultPattern = "٨";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_num_eight","raw",context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 4, 5))) {
            finalResultPattern = "٠";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_num_zero","raw",context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(3, 4, 5, 6))) {
            finalResultPattern = "#";
            patternPronounce = null;
            patternPronounce = context.getString(R.string.hash_pattern);
            soundResources = context.getResources().getIdentifier("ar_num_hash","raw",context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 4, 5))) {
            finalResultPattern = "٧";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_num_7","raw",context.getPackageName());
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
        return patternPronounce != null ? patternPronounce : finalResultPattern;
    }
    //--------------------------------------------------------------------------------------------//
    public String getClassTitle(){
        return context.getString(R.string.numbers_keyboard);
    }
    //--------------------------------------------------------------------------------------------//
    public int getClassTitleSoundPath() {
        int classTitleSound = context.getResources().getIdentifier("ar_message_numbers_keyboard", "raw", context.getPackageName());
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
        return new ArabicMathPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern previousPattern(Context context){
        return new ArabicCharPattern(context);
    }
}
