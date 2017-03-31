package com.mbanna.swiftbraille.surfacelayout;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.content.Context;
import android.content.res.Configuration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.mbanna.swiftbraille.opsbars.BottomButtonsBar;
import com.mbanna.swiftbraille.BrailleDot;
import com.mbanna.swiftbraille.KeyboardSurface;
import com.mbanna.swiftbraille.Common;
import com.mbanna.swiftbraille.R;
import com.mbanna.swiftbraille.opsbars.RightLeftButtonsBar;
import com.mbanna.swiftbraille.opsbars.TopButtonsBar;

//This is responsible of where the dots will be distributed on the screen
public class BrailleTwoLinesLayout extends BrailleLayout {
    Context context;
    KeyboardSurface keyboardSurface;
    BrailleDot brailleDot1, brailleDot2, brailleDot3, brailleDot4, brailleDot5, brailleDot6;
    boolean showBrailleDotsFromRight, showOperationsButtons;
    int theFullDotRadius, keyboardHeightSelected, keyboardWidthSelected;
    int[] screenDimensions;
    RelativeLayout brailleLayoutContainer;

    //Operations bars
    TopButtonsBar topButtonsBar;
    BottomButtonsBar bottomButtonsBar;
    RightLeftButtonsBar rightLeftButtonsBar;

    //--------------------------------------------------------------------------------------------//
    public BrailleTwoLinesLayout(Context context, KeyboardSurface keyboardSurface) {
        super(context);
        try {
            this.context = context;
            this.keyboardSurface = keyboardSurface;
            this.brailleLayoutContainer = getBrailleLayoutContainer(context);
            this.screenDimensions = Common.getScreenDimension();

            //Get Braille dots with their settings
            this.brailleDot1 = getBrailleDot1();
            this.brailleDot2 = getBrailleDot2();
            this.brailleDot3 = getBrailleDot3();
            this.brailleDot4 = getBrailleDot4();
            this.brailleDot5 = getBrailleDot5();
            this.brailleDot6 = getBrailleDot6();
            this.theFullDotRadius = getBrailleDot1().getFullRadius();
            this.showBrailleDotsFromRight = Common.showBrailleDotsFromTheRight;
            this.showOperationsButtons = Common.showOperationsButtons;

            //Render the keyboard
            renderBrailleLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    @Override
    public void renderBrailleLayout() {
        try {
            int surfaceContainerWidth = brailleLayoutContainer.getLayoutParams().width;
            int surfaceContainerHeight = brailleLayoutContainer.getLayoutParams().height;

            //In match parent, the width is -1
            if(surfaceContainerWidth < 0){
                surfaceContainerWidth = screenDimensions[0];
            }
            //In match parent, the height is -1
            if(surfaceContainerHeight < 0){
                surfaceContainerHeight = screenDimensions[1];
            }

            //Select keyboard height and width
            if(this.context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                keyboardHeightSelected = Common.keyboardHeightPortrait;
                keyboardWidthSelected = Common.keyboardWidthPortrait;
            } else{
                keyboardHeightSelected = Common.keyboardHeightLandscape;
                keyboardWidthSelected = Common.keyboardWidthLandscape;
            }

            //Braille dot 4 position
            RelativeLayout.LayoutParams brailleDots4Params = new RelativeLayout.LayoutParams(
                    new ViewGroup.LayoutParams(theFullDotRadius, theFullDotRadius)
            );
            brailleDots4Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            brailleDots4Params.rightMargin = Common.brailleDotMargin;
            brailleDot4.setLayoutParams(brailleDots4Params);

            //Braille dot 5 position
            RelativeLayout.LayoutParams brailleDots5Params = new RelativeLayout.LayoutParams(
                    new ViewGroup.LayoutParams(theFullDotRadius, theFullDotRadius)
            );
            brailleDot5.setLayoutParams(brailleDots5Params);

            //Braille dot 6 position
            RelativeLayout.LayoutParams brailleDots6Params = new RelativeLayout.LayoutParams(
                    new ViewGroup.LayoutParams(theFullDotRadius, theFullDotRadius)
            );
            brailleDots6Params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            brailleDots6Params.leftMargin = Common.brailleDotMargin;
            brailleDot6.setLayoutParams(brailleDots6Params);

            //Braille dot 1 position
            RelativeLayout.LayoutParams brailleDots1Params = new RelativeLayout.LayoutParams(
                    new ViewGroup.LayoutParams(theFullDotRadius, theFullDotRadius)
            );
            brailleDots1Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            brailleDots1Params.bottomMargin = Common.brailleDotMargin;
            brailleDots1Params.rightMargin = Common.brailleDotMargin;
            brailleDot1.setLayoutParams(brailleDots1Params);

            //Braille dot 2 position
            RelativeLayout.LayoutParams brailleDots2Params = new RelativeLayout.LayoutParams(
                    new ViewGroup.LayoutParams(theFullDotRadius, theFullDotRadius)
            );
            brailleDots2Params.bottomMargin = Common.brailleDotMargin;
            brailleDot2.setLayoutParams(brailleDots2Params);

            //Braille dot 3 position
            RelativeLayout.LayoutParams brailleDots3Params = new RelativeLayout.LayoutParams(
                    new ViewGroup.LayoutParams(theFullDotRadius, theFullDotRadius)
            );
            brailleDots3Params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            brailleDots3Params.bottomMargin = Common.brailleDotMargin;
            brailleDots3Params.leftMargin = Common.brailleDotMargin;
            brailleDot3.setLayoutParams(brailleDots3Params);

            //Show top and bottom buttons bars
            RelativeLayout.LayoutParams bottomButtonsBarParams = null;
            RelativeLayout.LayoutParams topButtonsBarParams = null;
            RelativeLayout.LayoutParams rightLeftButtonsBarParams = null;

            if (showOperationsButtons) {
                //If the user has changed the keyboard height
                bottomButtonsBar = new BottomButtonsBar(context, keyboardSurface, R.layout.ops_buttons_bottom);
                bottomButtonsBar.setId(R.id.bottom_ops_btns_bar);
                topButtonsBar = new TopButtonsBar(context, keyboardSurface, R.layout.ops_buttons_top);
                topButtonsBar.setId(R.id.top_ops_btns_bar);
                rightLeftButtonsBar = new RightLeftButtonsBar(context, this);
                rightLeftButtonsBar.setId(R.id.right_left_ops_btns_bar);

                //Top buttons bar
                topButtonsBarParams = new RelativeLayout.LayoutParams(
                        new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                );

                //Bottom buttons bar
                bottomButtonsBarParams = new RelativeLayout.LayoutParams(
                        new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                );

                //Right left buttons bar
                rightLeftButtonsBarParams = new RelativeLayout.LayoutParams(
                        new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT)
                );

                //Load right left buttons bar
                if (Common.isTablet && keyboardWidthSelected != 100) {
                    if (Common.startKeyboardContainerFromRight) {
                        //Make sure the top and bottom bars are besides the right/left buttons bars
                        topButtonsBarParams.addRule(RelativeLayout.RIGHT_OF, rightLeftButtonsBar.getId());
                        bottomButtonsBarParams.addRule(RelativeLayout.RIGHT_OF, rightLeftButtonsBar.getId());
                        rightLeftButtonsBarParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

                        //Braille dots beside the right left buttons bar
                        brailleDot3.getLayoutParams().leftMargin = Common.opsBarsWidth + Common.brailleDotMargin;
                        brailleDot6.getLayoutParams().leftMargin = Common.opsBarsWidth + Common.brailleDotMargin;
                        brailleDot2.getLayoutParams().leftMargin = surfaceContainerWidth/2 - brailleDot2.getRadius() + (Common.opsBarsWidth - Common.brailleDotMargin)/2;
                        brailleDot5.getLayoutParams().leftMargin = surfaceContainerWidth/2 - brailleDot5.getRadius() + (Common.opsBarsWidth - Common.brailleDotMargin)/2;
                    } else {
                        //Make sure the top and bottom bars are besides the right/left buttons bars
                        topButtonsBarParams.addRule(RelativeLayout.LEFT_OF, rightLeftButtonsBar.getId());
                        bottomButtonsBarParams.addRule(RelativeLayout.LEFT_OF, rightLeftButtonsBar.getId());
                        rightLeftButtonsBarParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                        //Braille dots beside the right left buttons bar
                        brailleDot1.getLayoutParams().rightMargin = Common.opsBarsWidth + Common.brailleDotMargin;
                        brailleDot4.getLayoutParams().rightMargin = Common.opsBarsWidth + Common.brailleDotMargin;
                        brailleDot2.getLayoutParams().leftMargin = (surfaceContainerWidth/2) - brailleDot2.getRadius() - Common.opsBarsWidth + Common.brailleDotMargin;
                        brailleDot5.getLayoutParams().leftMargin = (surfaceContainerWidth/2) - brailleDot5.getRadius() - Common.opsBarsWidth + Common.brailleDotMargin;
                    }

                } else {
                    brailleDot2.getLayoutParams().leftMargin = (surfaceContainerWidth/2) - (brailleDot5.getRadius() + Common.brailleDotMargin);
                    brailleDot5.getLayoutParams().leftMargin = (surfaceContainerWidth/2) - (brailleDot5.getRadius() + Common.brailleDotMargin);
                }

                //Bottom buttons bar
                if (keyboardHeightSelected == 100) {
                    bottomButtonsBarParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    brailleDot4.getLayoutParams().addRule(RelativeLayout.ABOVE, bottomButtonsBar.getId());
                    brailleDot5.getLayoutParams().addRule(RelativeLayout.ABOVE, bottomButtonsBar.getId());
                    brailleDot6.getLayoutParams().addRule(RelativeLayout.ABOVE, bottomButtonsBar.getId());

                    brailleDot4.getLayoutParams().bottomMargin = Common.brailleDotMargin;
                    brailleDot5.getLayoutParams().bottomMargin = Common.brailleDotMargin;
                    brailleDot6.getLayoutParams().bottomMargin = Common.brailleDotMargin;

                    brailleDot1.getLayoutParams().addRule(RelativeLayout.ABOVE, brailleDot4.getId());
                    brailleDot2.getLayoutParams().addRule(RelativeLayout.ABOVE, brailleDot5.getId());
                    brailleDot3.getLayoutParams().addRule(RelativeLayout.ABOVE, brailleDot6.getId());
                } else {
                    bottomButtonsBarParams.topMargin = surfaceContainerHeight - Common.opsBarsHeight;
                    brailleDot4.getLayoutParams().topMargin = bottomButtonsBarParams.topMargin - theFullDotRadius - Common.brailleDotMargin;
                    brailleDot5.getLayoutParams().topMargin = bottomButtonsBarParams.topMargin - theFullDotRadius - Common.brailleDotMargin;
                    brailleDot6.getLayoutParams().topMargin = bottomButtonsBarParams.topMargin - theFullDotRadius - Common.brailleDotMargin;

                    brailleDot1.getLayoutParams().topMargin = bottomButtonsBarParams.topMargin - (Common.brailleDotMargin * 2) - (theFullDotRadius * 2);
                    brailleDot2.getLayoutParams().topMargin = bottomButtonsBarParams.topMargin - (Common.brailleDotMargin * 2) - (theFullDotRadius * 2);
                    brailleDot3.getLayoutParams().topMargin = bottomButtonsBarParams.topMargin - (Common.brailleDotMargin * 2) - (theFullDotRadius * 2);
                }
            } else {
                brailleDot2.getLayoutParams().addRule(RelativeLayout.CENTER_HORIZONTAL);
                brailleDot5.getLayoutParams().addRule(RelativeLayout.CENTER_HORIZONTAL);

                //If the user has changed the keyboard height
                if (keyboardHeightSelected != 100) {
                    brailleDot4.getLayoutParams().topMargin = surfaceContainerHeight - Common.brailleDotMargin - theFullDotRadius;
                    brailleDot5.getLayoutParams().topMargin = surfaceContainerHeight - Common.brailleDotMargin - theFullDotRadius;
                    brailleDot6.getLayoutParams().topMargin = surfaceContainerHeight - Common.brailleDotMargin - theFullDotRadius;

                    brailleDot1.getLayoutParams().topMargin = surfaceContainerHeight - (Common.brailleDotMargin * 2) - (theFullDotRadius * 2);
                    brailleDot2.getLayoutParams().topMargin = surfaceContainerHeight - (Common.brailleDotMargin * 2) - (theFullDotRadius * 2);
                    brailleDot3.getLayoutParams().topMargin = surfaceContainerHeight - (Common.brailleDotMargin * 2) - (theFullDotRadius * 2);
                } else {
                    //No need to padding in operations bars
                    brailleLayoutContainer.setPadding(0, 0, 0, Common.brailleDotMargin);

                    brailleDot4.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    brailleDot5.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    brailleDot6.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

                    brailleDot1.getLayoutParams().addRule(RelativeLayout.ABOVE, brailleDot4.getId());
                    brailleDot2.getLayoutParams().addRule(RelativeLayout.ABOVE, brailleDot5.getId());
                    brailleDot3.getLayoutParams().addRule(RelativeLayout.ABOVE, brailleDot6.getId());

                }
            }

            //Add top buttons bar
            if (topButtonsBar != null && topButtonsBarParams != null) {
                brailleLayoutContainer.addView(topButtonsBar, topButtonsBarParams);
            }

            //Braille dots
            brailleLayoutContainer.addView(brailleDot1, brailleDot1.getLayoutParams());
            brailleLayoutContainer.addView(brailleDot2, brailleDot2.getLayoutParams());
            brailleLayoutContainer.addView(brailleDot3, brailleDot3.getLayoutParams());
            brailleLayoutContainer.addView(brailleDot4, brailleDot4.getLayoutParams());
            brailleLayoutContainer.addView(brailleDot5, brailleDot5.getLayoutParams());
            brailleLayoutContainer.addView(brailleDot6, brailleDot6.getLayoutParams());

            //Add bottom buttons bar
            if (bottomButtonsBar != null && bottomButtonsBarParams != null) {
                brailleLayoutContainer.addView(bottomButtonsBar, bottomButtonsBarParams);
            }

            //Add right left buttons bar
            if(Common.isTablet && keyboardWidthSelected != 100 && rightLeftButtonsBar != null && rightLeftButtonsBarParams != null){
                brailleLayoutContainer.addView(rightLeftButtonsBar, rightLeftButtonsBarParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
