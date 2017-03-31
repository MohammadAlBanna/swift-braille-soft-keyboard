package com.mbanna.swiftbraille.surfacelayout;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mbanna.swiftbraille.BrailleDot;
import com.mbanna.swiftbraille.Common;
import com.mbanna.swiftbraille.MyAccessibilityEventHandler;
import com.mbanna.swiftbraille.R;

public abstract class BrailleLayout {
    //Braille objects
    BrailleDot brailleDot1;
    BrailleDot brailleDot2;
    BrailleDot brailleDot3;
    BrailleDot brailleDot4;
    BrailleDot brailleDot5;
    BrailleDot brailleDot6;

    //Dots settings
    int dotRadius = 60;
    int dotFillColor = 0;
    int dotStrokeColor = 0;
    boolean viewBrailleDotNumber = true;
    boolean fillDotOnTouchSettings = false;
    RelativeLayout brailleLayoutContainer = null;

    //--------------------------------------------------------------------------------------------//
    //This class responsible for render Braille dots, and their container
    public BrailleLayout(Context context) {
        try {
            //Dots view accessibility handler
            MyAccessibilityEventHandler myAccessibilityEventHandler = new MyAccessibilityEventHandler();

            //Load dot settings
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                dotRadius = Common.dotRadius;
            } else {
                dotRadius = Common.dotLandscapeRadius;
            }

            dotFillColor = Common.dotFillColor;
            dotStrokeColor = Common.dotStrokeColor;
            fillDotOnTouchSettings = Common.fillDotOnTouch;
            viewBrailleDotNumber = Common.viewBrailleDotNumber;

            //Braille dot 1
            brailleDot1 = new BrailleDot(context);
            brailleDot1.setId(R.id.braille_dot_1);
            brailleDot1.setRadius(dotRadius);
            brailleDot1.setFillColor(dotFillColor);
            brailleDot1.setStrokeColor(dotStrokeColor);
            brailleDot1.setViewBrailleDotNumber(viewBrailleDotNumber);
            brailleDot1.setDotTextNumber(1);
            brailleDot1.setFillDotOnTouchSettings(fillDotOnTouchSettings);
            brailleDot1.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_YES);
            brailleDot1.setAccessibilityDelegate(myAccessibilityEventHandler);

            //Braille dot 2
            brailleDot2 = new BrailleDot(context);
            brailleDot2.setId(R.id.braille_dot_2);
            brailleDot2.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_YES);
            brailleDot2.setAccessibilityDelegate(myAccessibilityEventHandler);
            brailleDot2.setRadius(dotRadius);
            brailleDot2.setFillColor(dotFillColor);
            brailleDot2.setStrokeColor(dotStrokeColor);
            brailleDot2.setDotTextNumber(2);
            brailleDot2.setViewBrailleDotNumber(viewBrailleDotNumber);
            brailleDot2.setFillDotOnTouchSettings(fillDotOnTouchSettings);

            //Braille dot 3
            brailleDot3 = new BrailleDot(context);
            brailleDot3.setId(R.id.braille_dot_3);
            brailleDot3.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_YES);
            brailleDot3.setAccessibilityDelegate(myAccessibilityEventHandler);
            brailleDot3.setRadius(dotRadius);
            brailleDot3.setFillColor(dotFillColor);
            brailleDot3.setStrokeColor(dotStrokeColor);
            brailleDot3.setDotTextNumber(3);
            brailleDot3.setViewBrailleDotNumber(viewBrailleDotNumber);
            brailleDot3.setFillDotOnTouchSettings(fillDotOnTouchSettings);

            //Braille dot 4
            brailleDot4 = new BrailleDot(context);
            brailleDot4.setId(R.id.braille_dot_4);
            brailleDot4.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_YES);
            brailleDot4.setAccessibilityDelegate(myAccessibilityEventHandler);
            brailleDot4.setRadius(dotRadius);
            brailleDot4.setFillColor(dotFillColor);
            brailleDot4.setStrokeColor(dotStrokeColor);
            brailleDot4.setDotTextNumber(4);
            brailleDot4.setFillDotOnTouchSettings(fillDotOnTouchSettings);
            brailleDot4.setViewBrailleDotNumber(viewBrailleDotNumber);

            //Braille dot 5
            brailleDot5 = new BrailleDot(context);
            brailleDot5.setId(R.id.braille_dot_5);
            brailleDot5.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_YES);
            brailleDot5.setAccessibilityDelegate(myAccessibilityEventHandler);
            brailleDot5.setRadius(dotRadius);
            brailleDot5.setFillColor(dotFillColor);
            brailleDot5.setStrokeColor(dotStrokeColor);
            brailleDot5.setDotTextNumber(5);
            brailleDot5.setViewBrailleDotNumber(viewBrailleDotNumber);
            brailleDot5.setFillDotOnTouchSettings(fillDotOnTouchSettings);

            //Braille dot 6
            brailleDot6 = new BrailleDot(context);
            brailleDot6.setId(R.id.braille_dot_6);
            brailleDot6.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_YES);
            brailleDot6.setAccessibilityDelegate(myAccessibilityEventHandler);
            brailleDot6.setRadius(dotRadius);
            brailleDot6.setFillColor(dotFillColor);
            brailleDot6.setStrokeColor(dotStrokeColor);
            brailleDot6.setDotTextNumber(6);
            brailleDot6.setViewBrailleDotNumber(viewBrailleDotNumber);
            brailleDot6.setFillDotOnTouchSettings(fillDotOnTouchSettings);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //---------------------------------------------------------------------------------------------//
    public BrailleDot getBrailleDot1() {
        return brailleDot1;
    }

    //---------------------------------------------------------------------------------------------//
    public BrailleDot getBrailleDot2() {
        return brailleDot2;
    }

    //---------------------------------------------------------------------------------------------//
    public BrailleDot getBrailleDot3() {
        return brailleDot3;
    }

    //---------------------------------------------------------------------------------------------//
    public BrailleDot getBrailleDot4() {
        return brailleDot4;
    }

    //---------------------------------------------------------------------------------------------//
    public BrailleDot getBrailleDot5() {
        return brailleDot5;
    }

    //---------------------------------------------------------------------------------------------//
    public BrailleDot getBrailleDot6() {
        return brailleDot6;
    }

    //---------------------------------------------------------------------------------------------//
    //Render everything related to the keyboard, braille dots and ops bars
    public abstract void renderBrailleLayout();

    //---------------------------------------------------------------------------------------------//
    //The container responsible for settings the width and height of the Braille container, 
    //plus the top border
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public RelativeLayout renderBrailleLayoutContainer(Context context) {
        try {
            if (brailleLayoutContainer == null) {
                brailleLayoutContainer = new RelativeLayout(context);
                brailleLayoutContainer.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                int keyboardHeightSelected, keyboardWidthSelected, miniWidth, miniHeight;
                int fullScreenWidth = Common.getScreenDimension()[0];
                int fullScreenHeight = Common.getScreenDimension()[1];

                int selectedDotsLayout = Common.selectedDotsLayout;
                if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    keyboardHeightSelected = Common.keyboardHeightPortrait;
                    keyboardWidthSelected = Common.keyboardWidthPortrait;
                } else {
                    keyboardHeightSelected = Common.keyboardHeightLandscape;
                    keyboardWidthSelected = Common.keyboardWidthLandscape;
                }

                //Check the height of the keyboard
                if (keyboardHeightSelected != 100) {
                    miniHeight = (int) (fullScreenHeight * ((float) keyboardHeightSelected / 100));
                    //Add top border
                    RelativeLayout.LayoutParams topBorderKeyboardParam = new RelativeLayout.LayoutParams(
                            new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 2)
                    );
                    topBorderKeyboardParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    View topBorderView = new View(context);
                    topBorderView.setBackgroundColor(Common.getMyColor(R.color.swiftBrailleTopBorder));
                    brailleLayoutContainer.addView(topBorderView, topBorderKeyboardParam);
                } else {
                    miniHeight = ViewGroup.LayoutParams.MATCH_PARENT; //Full height
                }

                //Keyboard width
                if (Common.isTablet && selectedDotsLayout != Common.PERKINS_DOTS_LAYOUT) {
                    if (keyboardWidthSelected != 100) {
                        miniWidth = (int) (fullScreenWidth * ((float) keyboardWidthSelected / 100));

                        //ADd top left or right border
                        RelativeLayout.LayoutParams rightLeftBorderKeyboardParam = new RelativeLayout.LayoutParams(
                                new ViewGroup.LayoutParams(2, RelativeLayout.LayoutParams.MATCH_PARENT)
                        );
                        if(Common.startKeyboardContainerFromRight){
                            rightLeftBorderKeyboardParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        } else{
                            rightLeftBorderKeyboardParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        }
                        View rightLeftBorderView = new View(context);
                        rightLeftBorderView.setBackgroundColor(Common.getMyColor(R.color.swiftBrailleTopBorder));
                        brailleLayoutContainer.addView(rightLeftBorderView, rightLeftBorderKeyboardParam);
                    } else {
                        miniWidth = ViewGroup.LayoutParams.MATCH_PARENT; //Full width
                    }
                } else {
                    miniWidth = ViewGroup.LayoutParams.MATCH_PARENT; //Full width
                }

                //Set layout params
                RelativeLayout.LayoutParams surfaceContainerParams = new RelativeLayout.LayoutParams(
                        new ViewGroup.LayoutParams(miniWidth, miniHeight)
                );

                //Check if to start the container from right
                if (Common.startKeyboardContainerFromRight && selectedDotsLayout != Common.PERKINS_DOTS_LAYOUT) {
                    surfaceContainerParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                }

                brailleLayoutContainer.setLayoutParams(surfaceContainerParams);
            }
            return brailleLayoutContainer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //---------------------------------------------------------------------------------------------//
    public RelativeLayout getBrailleLayoutContainer(Context context) {
        try {
            if (brailleLayoutContainer == null) {
                return renderBrailleLayoutContainer(context);
            } else {
                return brailleLayoutContainer;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
