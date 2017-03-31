package com.mbanna.swiftbraille.patterns;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.content.Context;

import com.mbanna.swiftbraille.Common;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

//The base class of all keyboards types
public abstract class Pattern {
    public abstract void setPattern(Set pattern); //To add it to array & show it in toast

    public abstract String getPatternResult(); //To show it in toast

    public abstract String getPatternPronounce(); //Just for speak it, and showing in the toast or speech it

    public abstract String getClassTitle(); //Get current keyboard to tell the user about current pattern

    public abstract int getPatternSoundPronounce(); //If there's sound, play it

    public abstract int getClassTitleSoundPath(); //Run the sound of current keyboard in Arabic

    public abstract Pattern nextPattern(Context context); //Get next pattern keyboard

    public abstract Pattern previousPattern(Context context); //Get previous pattern keyboard
    //--------------------------------------------------------------------------------------------//
    //To speech current keyboard number pattern
    public void speechClassTitle() {
        //Speech or show the toast
        if ((Common.playAlwaysStoredVoices && Common.currentSystemLanguage.equals("ar") && getClassTitleSoundPath() != -1) || (!Common.defaultTextSpeech.isArabicSupported() && Common.currentSystemLanguage.equals("ar") && getClassTitleSoundPath() != -1)) {
            Common.runPatternSoundPronounce(getClassTitleSoundPath(), true);
        } else {
            Common.defaultTextSpeech.speechText(getClassTitle(), true);
        }
    }

    //--------------------------------------------------------------------------------------------//
    //Check if this is an indicator Braille code
    // 0=> Capital
    // 1=> Numbers
    // 2=> Symbols
    // -1=> Nothing
    public int brailleIndicatorType(Set<Integer> pattern) {
        if (pattern.size() == 1 && pattern.containsAll(Collections.singletonList(6))) {
            return 0;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(4, 6))) {
            return 0;
        } else if (pattern.size() == 2 && pattern.containsAll(Arrays.asList(5, 6))) {
            return 2;
        } else if (pattern.size() == 4 && pattern.containsAll(Arrays.asList(3, 4, 5, 6))) {
            return 1;
        }
        return -1;
    }
    //--------------------------------------------------------------------------------------------//
}
