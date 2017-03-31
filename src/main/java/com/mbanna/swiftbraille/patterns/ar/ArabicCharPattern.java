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

//Contains all Arabic letters and related
public class ArabicCharPattern extends Pattern {
    private String finalResultPattern = null;
    private String patternPronounce = null;
    private int soundResources = -1;
    private Context context;
    //--------------------------------------------------------------------------------------------//
    public ArabicCharPattern(Context context) {
        this.context = context;
        Common.currentLanguage = Common.ARABIC_LANGUAGE_INPUT_METHOD;
        Common.currentLocaleLanguage = Common.arabicLocale;
        Common.isRTL = true;
    }
    //--------------------------------------------------------------------------------------------//
    @SuppressWarnings("All")
    public void setPattern(Set pattern) {
        if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(1))) {
            finalResultPattern = "ا";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_alf", "raw", context.getPackageName());
        } else if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(2))) {
            finalResultPattern = "َ";
            patternPronounce = context.getString(R.string.ar_pattern_2);
            soundResources = context.getResources().getIdentifier("ar_punc_1", "raw", context.getPackageName());
        } else if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(3))) {
            finalResultPattern = "ء";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_hamzaa", "raw", context.getPackageName());
        } else if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(5))) {
            finalResultPattern = "،";
            patternPronounce = context.getString(R.string.comma_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_comma", "raw", context.getPackageName());
        } else if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(6))) {
            finalResultPattern = "ّ";
            patternPronounce = context.getString(R.string.ar_pattern_6);
            soundResources = context.getResources().getIdentifier("ar_punc_2", "raw", context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 3))) {
            finalResultPattern = "ك";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_kaaf", "raw", context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 5))) {
            finalResultPattern = "ِ";
            patternPronounce = context.getString(R.string.ar_pattern_1_5);
            soundResources = context.getResources().getIdentifier("ar_punc_4", "raw", context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 6))) {
            finalResultPattern = "ة";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_taa_marbota", "raw", context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(1, 2))) {
            finalResultPattern = "ب";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_baa", "raw", context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 4))) {
            finalResultPattern = "ي";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_yaa", "raw", context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 5))) {
            finalResultPattern = "ْ";
            patternPronounce = context.getString(R.string.ar_pattern_2_5);
            soundResources = context.getResources().getIdentifier("ar_punc_5", "raw", context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 3))) {
            finalResultPattern = "ً";
            patternPronounce = context.getString(R.string.ar_pattern_2_3);
            soundResources = context.getResources().getIdentifier("ar_punc_8", "raw", context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(3, 4))) {
            finalResultPattern = "أ";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_hamza_fawq_alf", "raw", context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(2, 6))) {
            finalResultPattern = "ٌ";
            patternPronounce = context.getString(R.string.ar_pattern_2_6);
            soundResources = context.getResources().getIdentifier("ar_punc_6", "raw", context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(4, 6))) {
            finalResultPattern = "إ";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_hamza_taht_alf", "raw", context.getPackageName());
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(3, 5))) {
            finalResultPattern = "ٍ";
            patternPronounce = context.getString(R.string.ar_pattern_3_5);
            soundResources = context.getResources().getIdentifier("ar_punc_7", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 4, 5))) {
            finalResultPattern = "ج";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_geem", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 5, 6))) {
            finalResultPattern = "ح";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_7aa", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 4))) {
            finalResultPattern = "س";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_seen", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 5))) {
            finalResultPattern = "!";
            patternPronounce = context.getString(R.string.exclamation_mark_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_exclamation", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 3, 6))) {
            finalResultPattern = "؟";
            patternPronounce = context.getString(R.string.question_mark_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_question", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 4, 6))) {
            finalResultPattern = "ش";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_sheen", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 4, 5))) {
            finalResultPattern = "د";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_dal", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 6))) {
            finalResultPattern = "غ";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_ghain", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 3))) {
            finalResultPattern = "ل";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_lam", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 4))) {
            finalResultPattern = "م";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_meem", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 5))) {
            finalResultPattern = "ى";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_alf_maqsoora", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(3, 4, 5))) {
            finalResultPattern = "آ";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_alf_mamdoda", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 4))) {
            finalResultPattern = "ف";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_faa", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 3, 6))) {
            finalResultPattern = "ُ";
            patternPronounce = context.getString(R.string.ar_pattern_1_3_6);
            soundResources = context.getResources().getIdentifier("ar_punc_3", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(1, 2, 5))) {
            finalResultPattern = "ه";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_haa", "raw", context.getPackageName());
        } else if (pattern.size() == 3 && pattern.containsAll(Arrays.asList(2, 5, 6))) {
            finalResultPattern = ".";
            patternPronounce = context.getString(R.string.dot_pattern);
            soundResources = context.getResources().getIdentifier("ar_symb_dot", "raw", context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 4, 5))) {
            finalResultPattern = "ت";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_taa", "raw", context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 4, 5, 6))) {
            finalResultPattern = "ث";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_thaa", "raw", context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 4, 6))) {
            finalResultPattern = "خ";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_5aa", "raw", context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 3, 4, 6))) {
            finalResultPattern = "ذ";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_thal", "raw", context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 5))) {
            finalResultPattern = "ر";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_raa", "raw", context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 5, 6))) {
            finalResultPattern = "ز";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_zain", "raw", context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 4, 6))) {
            finalResultPattern = "ض";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_dad", "raw", context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 3, 4, 5))) {
            finalResultPattern = "ن";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_noon", "raw", context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(2, 4, 5, 6))) {
            finalResultPattern = "و";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_waw", "raw", context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 5, 6))) {
            finalResultPattern = "ؤ";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_hamza_fawq_alwaw", "raw", context.getPackageName());
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(1, 2, 3, 6))) {
            finalResultPattern = "لا";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_lam_alf", "raw", context.getPackageName());
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 3, 4, 6))) {
            finalResultPattern = "ص";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_sad", "raw", context.getPackageName());
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(2, 3, 4, 5, 6))) {
            finalResultPattern = "ط";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_ttaa", "raw", context.getPackageName());
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 3, 5, 6))) {
            finalResultPattern = "ع";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_3een", "raw", context.getPackageName());
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 2, 3, 4, 5))) {
            finalResultPattern = "ق";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_kkaf", "raw", context.getPackageName());
        } else if (pattern.size() == 5 && pattern.containsAll(Arrays.asList(1, 3, 4, 5, 6))) {
            finalResultPattern = "ئ";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_hamzaa_alf_maqsora", "raw", context.getPackageName());
        } else if (pattern.size() == 6 && pattern.containsAll(Arrays.asList(1, 2, 3, 4, 5, 6))) {
            finalResultPattern = "ظ";
            patternPronounce = null;
            soundResources = context.getResources().getIdentifier("ar_char_thhaa", "raw", context.getPackageName());
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
    public String getPatternPronounce() {
        return patternPronounce != null ? patternPronounce : finalResultPattern;
    }
    //--------------------------------------------------------------------------------------------//
    public String getClassTitle() {
        return context.getString(R.string.arabic_current_language);
    }
    //--------------------------------------------------------------------------------------------//
    public int getClassTitleSoundPath() {
        int classTitleSound = context.getResources().getIdentifier("ar_message_ar_keyboard", "raw", context.getPackageName());
        if (classTitleSound != 0) {
            return classTitleSound;
        } else {
            return -1;
        }
    }
    //--------------------------------------------------------------------------------------------//
    public int getPatternSoundPronounce() {
        if (soundResources != 0) {
            return soundResources;
        } else {
            return -1;
        }
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern nextPattern(Context context){
        return new ArabicNumbersPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
    public Pattern previousPattern(Context context){
        return new ArabicSpecialPattern(context);
    }
    //--------------------------------------------------------------------------------------------//
}
