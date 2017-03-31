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

//Just contain English small characters pattern
public class FrenchSmallCharPattern extends Pattern {
    private Context context;
    private String patternPronounce = null;
    private String finalResultPattern = null;
    //--------------------------------------------------------------------------------------------//
    public FrenchSmallCharPattern(Context context){
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
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "b";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 4))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "c";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "e";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "â";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 4))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "i";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 3))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "k";
            patternPronounce = null;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(3, 4))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "ì";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 4, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "d";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 4))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "f";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "h";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 4, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "j";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 3))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "l";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 4))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "m";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "o";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 4))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
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
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "u";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "ê";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 4, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "î";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "û";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 4, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "œ";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(3, 4, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "ä";
            patternPronounce = null;
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(3, 4, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "ò";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 4, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "g";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "n";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 4))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "p";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "r";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "t";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "v";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "w";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 4, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "x";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "z";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 4, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "è";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "ô";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 4, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "ë";
            patternPronounce = null;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "ü";
            patternPronounce = null;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 3, 4, 5))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "q";
            patternPronounce = null;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 3, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "y";
            patternPronounce = null;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 3, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "à";
            patternPronounce = null;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 3, 4, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "ç";
            patternPronounce = null;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(2, 3, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "ù";
            patternPronounce = null;
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "ï";
            patternPronounce = null;
        } else if (pattern.size() == 6 && pattern.containsAll(Arrays.asList(1, 2, 3, 4, 5, 6))) {
            Common.currentLocaleLanguage = Common.frenchLocale;
            finalResultPattern = "é";
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
        return context.getString(R.string.french_small_letters_keyboard);
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
        return new FrenchCapitalCharPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern previousPattern(Context context){
        return new FrenchSpecialPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
}
