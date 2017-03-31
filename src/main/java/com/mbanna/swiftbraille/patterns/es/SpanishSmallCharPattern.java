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

//Just contain English small characters pattern
public class SpanishSmallCharPattern extends Pattern {
    private Context context;
    private String patternPronounce = null;
    private String finalResultPattern = null;
    private InputConnection inputConnection = null;

    //--------------------------------------------------------------------------------------------//
    public SpanishSmallCharPattern(Context context, InputConnection inputConnection){
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
            finalResultPattern = "a";
            patternPronounce = null;
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
            finalResultPattern = "b";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 4))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "c";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "e";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 4))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "i";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 6))) {
            if(Common.isFirstSentenceIndicate(inputConnection)){
                finalResultPattern = "¿";
                patternPronounce = context.getString(R.string.es_open_question);
            } else{
                finalResultPattern = "?";
                patternPronounce = context.getString(R.string.es_close_question);
            }
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 3))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "k";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(3, 4))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "í";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 4, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "d";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 4))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "f";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "h";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 4, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "j";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 3))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "l";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 4))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "m";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "o";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 4))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "s";
            patternPronounce = null;
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
            finalResultPattern = "u";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(3, 4, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "ó";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 4, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "g";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "n";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 4))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "p";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "r";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "t";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "v";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "w";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 4, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "x";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 5, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "z";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 4, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "é";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 5, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "ü";
            patternPronounce = null;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "q";
            patternPronounce = null;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 3, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "y";
            patternPronounce = null;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 3, 5, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "á";
            patternPronounce = null;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(2, 3, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "ú";
            patternPronounce = null;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.spanishLocale;
            finalResultPattern = "ñ";
            patternPronounce = null;
        }  else {
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
        return context.getString(R.string.spanish_small_letters_keyboard);
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
        return new SpanishCapitalCharPattern(context, inputConnection);
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern previousPattern(Context context){
        return new SpanishSpecialPattern(context, inputConnection);
    }
    //--------------------------------------------------------------------------------------------//
}
