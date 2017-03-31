package com.mbanna.swiftbraille;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.view.View;
import android.view.accessibility.AccessibilityEvent;

//This class to handle accessibility of buttons if the user activate talk back or other screen readers
public class MyAccessibilityEventHandler extends View.AccessibilityDelegate {
    @Override
    public boolean dispatchPopulateAccessibilityEvent(View v, AccessibilityEvent event) {
        //Send no event if the touch was inside Braille dots
        switch (v.getId()){
            case R.id.braille_dot_1:
            case R.id.braille_dot_2:
            case R.id.braille_dot_3:
            case R.id.braille_dot_4:
            case R.id.braille_dot_5:
            case R.id.braille_dot_6:
                event.setEventType(0); //Don't handle anything here!!
                break;
        }
        return true;
    }
}
