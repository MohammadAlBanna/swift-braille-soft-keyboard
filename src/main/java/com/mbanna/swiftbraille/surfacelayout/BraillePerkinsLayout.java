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
import com.mbanna.swiftbraille.opsbars.TopButtonsBar;

//This is responsible of where the dots will be distributed on the screen
public class BraillePerkinsLayout extends BrailleLayout {
    //Braille objects
    BrailleDot brailleDot1, brailleDot2, brailleDot3, brailleDot4, brailleDot5, brailleDot6;
    int theFullDotRadius, keyboardHeightSelected, brailleDotMargin;
    int[] screenDimensions;
    boolean showOperationsButtons, viewAsPortrait;
    Context context;
    KeyboardSurface keyboardSurface;
    RelativeLayout brailleLayoutContainer;

    //Operations bars
    TopButtonsBar topButtonsBar;
    BottomButtonsBar bottomButtonsBar;
    //--------------------------------------------------------------------------------------------//
    public BraillePerkinsLayout(Context context, KeyboardSurface keyboardSurface){
        super(context);
        try{
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
            this.showOperationsButtons = Common.showOperationsButtons;
            this.brailleDotMargin = Common.brailleDotMargin;
            viewAsPortrait = !Common.isTablet && context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

            if(this.context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                this.keyboardHeightSelected = Common.keyboardHeightPortrait;
            } else{
                this.keyboardHeightSelected = Common.keyboardHeightLandscape;
            }

            //Render the keyboard
            renderBrailleLayout();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------//
    @Override
    public void renderBrailleLayout() {
        try{
            int surfaceContainerHeight = brailleLayoutContainer.getLayoutParams().height;

            //In match parent, the height is -1
            if(surfaceContainerHeight < 0){
                surfaceContainerHeight = screenDimensions[1];
            }

            //Braille dot 3 position
            RelativeLayout.LayoutParams brailleDots3Params = new RelativeLayout.LayoutParams(
                    new ViewGroup.LayoutParams(theFullDotRadius, theFullDotRadius)
            );
            brailleDot3.setLayoutParams(brailleDots3Params);

            //Braille dot 2 position
            RelativeLayout.LayoutParams brailleDots2Params = new RelativeLayout.LayoutParams(
                    new ViewGroup.LayoutParams(theFullDotRadius, theFullDotRadius)
            );
            brailleDot2.setLayoutParams(brailleDots2Params);

            //Braille dot 1 position
            RelativeLayout.LayoutParams brailleDots1Params = new RelativeLayout.LayoutParams(
                    new ViewGroup.LayoutParams(theFullDotRadius, theFullDotRadius)
            );
            brailleDot1.setLayoutParams(brailleDots1Params);

            //Braille dot 6 position
            RelativeLayout.LayoutParams brailleDots6Params = new RelativeLayout.LayoutParams(
                    new ViewGroup.LayoutParams(theFullDotRadius, theFullDotRadius)
            );
            brailleDot6.setLayoutParams(brailleDots6Params);

            //Braille dot 5 position
            RelativeLayout.LayoutParams brailleDots5Params = new RelativeLayout.LayoutParams(
                    new ViewGroup.LayoutParams(theFullDotRadius, theFullDotRadius)
            );
            brailleDot5.setLayoutParams(brailleDots5Params);

            //Braille dot 4 position
            RelativeLayout.LayoutParams brailleDots4Params = new RelativeLayout.LayoutParams(
                    new ViewGroup.LayoutParams(theFullDotRadius, theFullDotRadius)
            );
            brailleDot4.setLayoutParams(brailleDots4Params);


            if(viewAsPortrait){
                //Rotation all dots
                brailleDot1.setRotation(90f);
                brailleDot2.setRotation(90f);
                brailleDot3.setRotation(90f);
                brailleDot4.setRotation(90f);
                brailleDot5.setRotation(90f);
                brailleDot6.setRotation(90f);
            }

            //Show top and bottom buttons bars
            RelativeLayout.LayoutParams bottomButtonsBarParams = null;
            RelativeLayout.LayoutParams topButtonsBarParams = null;
            if(showOperationsButtons){
                //If the user rotates his device
                if(viewAsPortrait){
                    topButtonsBar = new TopButtonsBar(context, keyboardSurface, R.layout.ops_buttons_top_90_rotated);
                    bottomButtonsBar = new BottomButtonsBar(context, keyboardSurface, R.layout.ops_buttons_bottom_90_rotated);
                } else{
                    topButtonsBar = new TopButtonsBar(context, keyboardSurface, R.layout.ops_buttons_top);
                    bottomButtonsBar = new BottomButtonsBar(context, keyboardSurface, R.layout.ops_buttons_bottom);
                }

                topButtonsBar.setId(R.id.top_ops_btns_bar);
                bottomButtonsBar.setId(R.id.bottom_ops_btns_bar);

                if(viewAsPortrait){
                    //Top buttons bar
                    topButtonsBarParams = new RelativeLayout.LayoutParams(
                            new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT)
                    );

                    //Bottom buttons bar
                    bottomButtonsBarParams = new RelativeLayout.LayoutParams(
                            new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT)
                    );
                } else{
                    //Top buttons bar
                    topButtonsBarParams = new RelativeLayout.LayoutParams(
                            new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                    );

                    //Bottom buttons bar
                    bottomButtonsBarParams = new RelativeLayout.LayoutParams(
                            new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                    );

                    brailleDot2.getLayoutParams().addRule(RelativeLayout.RIGHT_OF, brailleDot3.getId());
                    brailleDot1.getLayoutParams().addRule(RelativeLayout.RIGHT_OF, brailleDot2.getId());
                    brailleDot6.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    brailleDot5.getLayoutParams().addRule(RelativeLayout.LEFT_OF, brailleDot6.getId());
                    brailleDot4.getLayoutParams().addRule(RelativeLayout.LEFT_OF, brailleDot5.getId());
                }

                if(keyboardHeightSelected == 100){
                    if(viewAsPortrait){
                        topButtonsBarParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        brailleDot3.getLayoutParams().addRule(RelativeLayout.RIGHT_OF, bottomButtonsBar.getId());
                        brailleDot2.getLayoutParams().addRule(RelativeLayout.RIGHT_OF, bottomButtonsBar.getId());
                        brailleDot2.getLayoutParams().addRule(RelativeLayout.BELOW, brailleDot3.getId());
                        brailleDot1.getLayoutParams().addRule(RelativeLayout.RIGHT_OF, bottomButtonsBar.getId());
                        brailleDot1.getLayoutParams().addRule(RelativeLayout.BELOW, brailleDot2.getId());

                        brailleDot6.getLayoutParams().addRule(RelativeLayout.RIGHT_OF, bottomButtonsBar.getId());
                        brailleDot6.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        brailleDot5.getLayoutParams().addRule(RelativeLayout.RIGHT_OF, bottomButtonsBar.getId());
                        brailleDot5.getLayoutParams().addRule(RelativeLayout.ABOVE, brailleDot6.getId());
                        brailleDot4.getLayoutParams().addRule(RelativeLayout.RIGHT_OF, bottomButtonsBar.getId());
                        brailleDot4.getLayoutParams().addRule(RelativeLayout.ABOVE, brailleDot5.getId());

                        if(Common.makeDot2And5Higher){
                            brailleDot2.getLayoutParams().leftMargin =  theFullDotRadius;
                            brailleDot5.getLayoutParams().leftMargin = theFullDotRadius;
                            brailleDot3.getLayoutParams().topMargin =  brailleDotMargin;
                            brailleDot3.getLayoutParams().leftMargin =  brailleDotMargin;
                            brailleDot1.getLayoutParams().leftMargin =  brailleDotMargin;
                            brailleDot4.getLayoutParams().leftMargin =  brailleDotMargin;
                            brailleDot6.getLayoutParams().leftMargin =  brailleDotMargin;
                            brailleDot6.getLayoutParams().bottomMargin =  brailleDotMargin;
                        } else{
                            brailleDot1.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot2.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot3.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot4.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot5.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot6.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                        }

                    } else{
                        bottomButtonsBarParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        brailleDot1.getLayoutParams().addRule(RelativeLayout.ABOVE, bottomButtonsBar.getId());
                        brailleDot2.getLayoutParams().addRule(RelativeLayout.ABOVE, bottomButtonsBar.getId());
                        brailleDot3.getLayoutParams().addRule(RelativeLayout.ABOVE, bottomButtonsBar.getId());
                        brailleDot4.getLayoutParams().addRule(RelativeLayout.ABOVE, bottomButtonsBar.getId());
                        brailleDot5.getLayoutParams().addRule(RelativeLayout.ABOVE, bottomButtonsBar.getId());
                        brailleDot6.getLayoutParams().addRule(RelativeLayout.ABOVE, bottomButtonsBar.getId());

                        if(Common.makeDot2And5Higher){
                            brailleDot2.getLayoutParams().bottomMargin =  theFullDotRadius;
                            brailleDot5.getLayoutParams().bottomMargin = theFullDotRadius;
                            brailleDot1.getLayoutParams().bottomMargin =  brailleDotMargin;
                            brailleDot4.getLayoutParams().bottomMargin =  brailleDotMargin;
                            brailleDot3.getLayoutParams().bottomMargin =  brailleDotMargin;
                            brailleDot3.getLayoutParams().leftMargin =  brailleDotMargin;
                            brailleDot6.getLayoutParams().bottomMargin =  brailleDotMargin;
                            brailleDot6.getLayoutParams().rightMargin =  brailleDotMargin;
                        } else{
                            brailleDot1.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot2.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot3.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot4.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot5.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot6.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                        }
                    }
                } else {
                    bottomButtonsBarParams.topMargin = surfaceContainerHeight - Common.opsBarsHeight;
                    brailleDot1.getLayoutParams().topMargin =  bottomButtonsBarParams.topMargin - theFullDotRadius - brailleDotMargin;
                    brailleDot3.getLayoutParams().topMargin =  bottomButtonsBarParams.topMargin - theFullDotRadius - brailleDotMargin;
                    brailleDot3.getLayoutParams().leftMargin = brailleDotMargin;
                    brailleDot4.getLayoutParams().topMargin =  bottomButtonsBarParams.topMargin - theFullDotRadius - brailleDotMargin;
                    brailleDot6.getLayoutParams().topMargin =  bottomButtonsBarParams.topMargin - theFullDotRadius - brailleDotMargin;
                    brailleDot6.getLayoutParams().rightMargin = brailleDotMargin;

                    if(Common.makeDot2And5Higher){
                        brailleDot2.getLayoutParams().topMargin =  bottomButtonsBarParams.topMargin - (theFullDotRadius * 2) - brailleDotMargin;
                        brailleDot5.getLayoutParams().topMargin =  bottomButtonsBarParams.topMargin - (theFullDotRadius * 2) - brailleDotMargin;
                    } else{
                        brailleDot2.getLayoutParams().topMargin =  bottomButtonsBarParams.topMargin - theFullDotRadius - brailleDotMargin;
                        brailleDot2.getLayoutParams().rightMargin =  brailleDotMargin * 2;
                        brailleDot2.getLayoutParams().leftMargin =  brailleDotMargin * 2;
                        brailleDot5.getLayoutParams().topMargin =  bottomButtonsBarParams.topMargin - theFullDotRadius - brailleDotMargin;
                        brailleDot5.getLayoutParams().rightMargin =  brailleDotMargin * 2;
                        brailleDot5.getLayoutParams().leftMargin =  brailleDotMargin * 2;
                    }
                }
            } else {
                brailleDots3Params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                //If the user has changed the keyboard height
                if (keyboardHeightSelected != 100) {
                    brailleDot2.getLayoutParams().addRule(RelativeLayout.RIGHT_OF, brailleDot3.getId());
                    brailleDot1.getLayoutParams().addRule(RelativeLayout.RIGHT_OF, brailleDot2.getId());
                    brailleDot6.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    brailleDot5.getLayoutParams().addRule(RelativeLayout.LEFT_OF, brailleDot6.getId());
                    brailleDot4.getLayoutParams().addRule(RelativeLayout.LEFT_OF, brailleDot5.getId());
                    brailleDot1.getLayoutParams().topMargin = surfaceContainerHeight - brailleDotMargin - theFullDotRadius;
                    brailleDot2.getLayoutParams().rightMargin = brailleDotMargin * 2;
                    brailleDot2.getLayoutParams().leftMargin = brailleDotMargin * 2;
                    brailleDot3.getLayoutParams().topMargin = surfaceContainerHeight - brailleDotMargin - theFullDotRadius;
                    brailleDot3.getLayoutParams().leftMargin = brailleDotMargin;
                    brailleDot4.getLayoutParams().topMargin = surfaceContainerHeight - brailleDotMargin - theFullDotRadius;
                    brailleDot5.getLayoutParams().rightMargin = brailleDotMargin * 2;
                    brailleDot5.getLayoutParams().leftMargin = brailleDotMargin * 2;
                    brailleDot6.getLayoutParams().topMargin = surfaceContainerHeight - brailleDotMargin - theFullDotRadius;
                    brailleDot6.getLayoutParams().rightMargin = brailleDotMargin;
                    if(Common.makeDot2And5Higher){
                        brailleDot2.getLayoutParams().topMargin = surfaceContainerHeight - brailleDotMargin - (theFullDotRadius * 2);
                        brailleDot5.getLayoutParams().topMargin = surfaceContainerHeight - brailleDotMargin - (theFullDotRadius * 2);
                    } else{
                        brailleDot2.getLayoutParams().topMargin = surfaceContainerHeight - brailleDotMargin - theFullDotRadius;
                        brailleDot5.getLayoutParams().topMargin = surfaceContainerHeight - brailleDotMargin - theFullDotRadius;
                    }
                } else {
                    if (viewAsPortrait) {
                        //If the user won't to change the layout of his phone :(((((((
                        brailleDot2.getLayoutParams().addRule(RelativeLayout.BELOW, brailleDot3.getId());
                        brailleDot2.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        brailleDot1.getLayoutParams().addRule(RelativeLayout.BELOW, brailleDot2.getId());
                        brailleDot1.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        brailleDot6.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        brailleDot6.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        brailleDot5.getLayoutParams().addRule(RelativeLayout.ABOVE, brailleDot6.getId());
                        brailleDot5.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        brailleDot4.getLayoutParams().addRule(RelativeLayout.ABOVE, brailleDot5.getId());
                        brailleDot4.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_LEFT);

                        if(Common.makeDot2And5Higher){
                            brailleDot2.getLayoutParams().leftMargin =  theFullDotRadius;
                            brailleDot5.getLayoutParams().leftMargin = theFullDotRadius;
                            brailleDot3.getLayoutParams().topMargin =  brailleDotMargin;
                            brailleDot3.getLayoutParams().leftMargin =  brailleDotMargin;
                            brailleDot1.getLayoutParams().leftMargin =  brailleDotMargin;
                            brailleDot4.getLayoutParams().leftMargin =  brailleDotMargin;
                            brailleDot6.getLayoutParams().leftMargin =  brailleDotMargin;
                            brailleDot6.getLayoutParams().bottomMargin =  brailleDotMargin;
                        } else{
                            brailleDot1.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot2.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot3.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot4.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot5.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot6.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                        }
                    } else{
                        brailleDot6.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        brailleDot6.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        brailleDot5.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        brailleDot5.getLayoutParams().addRule(RelativeLayout.LEFT_OF, brailleDot6.getId());
                        brailleDot4.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        brailleDot4.getLayoutParams().addRule(RelativeLayout.LEFT_OF, brailleDot5.getId());
                        brailleDot3.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        brailleDot2.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        brailleDot2.getLayoutParams().addRule(RelativeLayout.RIGHT_OF, brailleDot3.getId());
                        brailleDot1.getLayoutParams().addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        brailleDot1.getLayoutParams().addRule(RelativeLayout.RIGHT_OF, brailleDot2.getId());

                        if(Common.makeDot2And5Higher){
                            brailleDot2.getLayoutParams().bottomMargin =  theFullDotRadius;
                            brailleDot5.getLayoutParams().bottomMargin = theFullDotRadius;
                            brailleDot1.getLayoutParams().bottomMargin =  brailleDotMargin;
                            brailleDot4.getLayoutParams().bottomMargin =  brailleDotMargin;
                            brailleDot3.getLayoutParams().bottomMargin =  brailleDotMargin;
                            brailleDot3.getLayoutParams().leftMargin =  brailleDotMargin;
                            brailleDot6.getLayoutParams().bottomMargin =  brailleDotMargin;
                            brailleDot6.getLayoutParams().rightMargin =  brailleDotMargin;
                        } else{
                            brailleDot1.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot2.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot3.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot4.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot5.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                            brailleDot6.getLayoutParams().setMargins(brailleDotMargin, brailleDotMargin, brailleDotMargin, brailleDotMargin);
                        }
                    }
                }
            }

            //Add top buttons bar
            if(topButtonsBar != null && topButtonsBarParams != null){
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
            if(bottomButtonsBar != null && bottomButtonsBarParams != null){
                brailleLayoutContainer.addView(bottomButtonsBar, bottomButtonsBarParams);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
