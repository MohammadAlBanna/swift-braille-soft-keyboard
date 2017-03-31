package com.mbanna.swiftbraille.opsbars;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.mbanna.swiftbraille.Common;
import com.mbanna.swiftbraille.MyAccessibilityEventHandler;
import com.mbanna.swiftbraille.R;
import com.mbanna.swiftbraille.surfacelayout.BrailleLayout;

public class RightLeftButtonsBar extends RelativeLayout implements View.OnTouchListener {
    ImageButton makeKeyboardFullWidth, moveKeyboardRightLeft;
    Context context;
    RelativeLayout surfaceContainer;
    BrailleLayout brailleLayout;
    boolean interceptView = false, speechMoveKeyboardRightLeftDesc = true, speechMakeKeyboardFullWidthDesc = true;
    int interceptedPosX, interceptedPosY, viewXPosition = 0, viewYPosition = 0;
    Vibrator vibrator = null;
    Handler playDescSoundHandler = new Handler();
    Runnable playDescSoundHandlerRunnable = null;
    final int PLAY_DESC_SOUND_AFTER = 300;

    //Used to detect if the touch was inside the view or not
    Rect viewRect = new Rect();
    Rect makeKeyboardFullWidthRect = new Rect();
    Rect moveKeyboardRightLeftRect = new Rect();

    //--------------------------------------------------------------------------------------------//
    public RightLeftButtonsBar(Context context, BrailleLayout brailleLayout) {
        super(context);
        try {
            this.context = context;
            this.surfaceContainer = brailleLayout.getBrailleLayoutContainer(context);
            this.brailleLayout = brailleLayout;
            this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            MyAccessibilityEventHandler myAccessibilityEventHandler = new MyAccessibilityEventHandler();
            setOnTouchListener(this);

            //views declarations
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.ops_buttons_right_left, this, true);
            makeKeyboardFullWidth = (ImageButton) view.findViewById(R.id.makeKeyboardFullWidth);
            moveKeyboardRightLeft = (ImageButton) view.findViewById(R.id.moveKeyboardRightLeft);

            if (Common.startKeyboardContainerFromRight) {
                moveKeyboardRightLeft.setTag("right");
                moveKeyboardRightLeft.setImageResource(R.mipmap.ic_keyboard_arrow_left_white_48dp);
            } else {
                moveKeyboardRightLeft.setTag("left");
                moveKeyboardRightLeft.setImageResource(R.mipmap.ic_keyboard_arrow_right_white_48dp);
            }

            //Listeners
            makeKeyboardFullWidth.setAccessibilityDelegate(myAccessibilityEventHandler);
            makeKeyboardFullWidth.setOnTouchListener(this);

            moveKeyboardRightLeft.setAccessibilityDelegate(myAccessibilityEventHandler);
            moveKeyboardRightLeft.setOnTouchListener(this);

            //To get the width and height after before the view got rendered
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        makeKeyboardFullWidthRect.set(makeKeyboardFullWidth.getLeft(), makeKeyboardFullWidth.getTop(),
                                makeKeyboardFullWidth.getRight(), makeKeyboardFullWidth.getTop() + makeKeyboardFullWidth.getHeight());

                        moveKeyboardRightLeftRect.set(moveKeyboardRightLeft.getLeft(), moveKeyboardRightLeft.getTop(),
                                moveKeyboardRightLeft.getRight(), moveKeyboardRightLeft.getTop() + moveKeyboardRightLeft.getHeight());
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    //Tag: is the current direction
    private void changeDirectionBtn(String tag) {
        try {
            if (tag.equals("left")) {
                moveKeyboardRightLeft.setImageResource(R.mipmap.ic_keyboard_arrow_left_white_48dp);
                ((LayoutParams) surfaceContainer.getLayoutParams()).leftMargin = ((RelativeLayout) surfaceContainer.getParent()).getWidth() - surfaceContainer.getWidth();
                ((LayoutParams) surfaceContainer.getLayoutParams()).rightMargin = 0;
                moveKeyboardRightLeft.setTag("right");
                Common.startKeyboardContainerFromRight = true;
                //Speech it
                Common.defaultTextSpeech.speechText(context.getString(R.string.keyboard_is_top_right));
            } else if (tag.equals("right")) {
                moveKeyboardRightLeft.setImageResource(R.mipmap.ic_keyboard_arrow_right_white_48dp);
                ((LayoutParams) surfaceContainer.getLayoutParams()).rightMargin = ((RelativeLayout) surfaceContainer.getParent()).getWidth() - surfaceContainer.getWidth();
                ((LayoutParams) surfaceContainer.getLayoutParams()).leftMargin = 0;
                moveKeyboardRightLeft.setTag("left");
                Common.startKeyboardContainerFromRight = false;
                //Speech it
                Common.defaultTextSpeech.speechText(context.getString(R.string.keyboard_is_top_left));
            }

            surfaceContainer.removeAllViews();
            brailleLayout.renderBrailleLayout();
            requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        try {
            int action = event.getActionMasked();
            //Action down
            if (!Common.isTouchToExploreEnabled() && action == MotionEvent.ACTION_DOWN) {
                viewRect.set(view.getLeft(), view.getTop(), view.getRight(), view.getTop() + view.getHeight());

                //If the tap over a button, intercept it and get the X and Y of the parent, not the child
                interceptView = view.getId() != R.id.right_left_ops_btns_bar;
                if (interceptView) {
                    viewXPosition = interceptedPosX;
                    viewYPosition = interceptedPosY;
                } else {
                    viewXPosition = (int) event.getX();
                    viewYPosition = (int) event.getY();
                }

                if (speechMakeKeyboardFullWidthDesc && makeKeyboardFullWidthRect.contains(viewXPosition, viewYPosition)) {
                    playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                    playDescSoundHandlerRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Common.defaultTextSpeech.speechText(makeKeyboardFullWidth.getContentDescription().toString());
                        }
                    };
                    playDescSoundHandler.postDelayed(playDescSoundHandlerRunnable, PLAY_DESC_SOUND_AFTER);
                    speechMakeKeyboardFullWidthDesc = false;
                    vibrator.vibrate(100);
                } else if (speechMoveKeyboardRightLeftDesc && moveKeyboardRightLeftRect.contains(viewXPosition, viewYPosition)) {
                    playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                    playDescSoundHandlerRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Common.defaultTextSpeech.speechText(moveKeyboardRightLeft.getContentDescription().toString());
                        }
                    };
                    playDescSoundHandler.postDelayed(playDescSoundHandlerRunnable, PLAY_DESC_SOUND_AFTER);
                    speechMoveKeyboardRightLeftDesc = false;
                    vibrator.vibrate(100);
                }
            } else if (action == MotionEvent.ACTION_DOWN) {
                viewRect.set(view.getLeft(), view.getTop(), view.getRight(), view.getTop() + view.getHeight());

                //If the tap over a button, intercept it and get the X and Y of the parent, not the child
                interceptView = view.getId() != R.id.right_left_ops_btns_bar;
                if (interceptView) {
                    viewXPosition = interceptedPosX;
                    viewYPosition = interceptedPosY;
                } else {
                    viewXPosition = (int) event.getX();
                    viewYPosition = (int) event.getY();
                }
            }

            //Action move
            if (!Common.isTouchToExploreEnabled() && action == MotionEvent.ACTION_MOVE) {
                //If the tap over a button, intercept it and get the X and Y of the parent, not the child
                if (interceptView) {
                    viewXPosition = interceptedPosX;
                    viewYPosition = interceptedPosY;
                } else {
                    viewXPosition = (int) event.getX();
                    viewYPosition = (int) event.getY();
                }

                if (speechMakeKeyboardFullWidthDesc && makeKeyboardFullWidthRect.contains(viewXPosition, viewYPosition)) {
                    Common.defaultTextSpeech.speechText(makeKeyboardFullWidth.getContentDescription().toString());
                    vibrator.vibrate(100);
                    speechMakeKeyboardFullWidthDesc = false;
                    speechMoveKeyboardRightLeftDesc = true;
                } else if (speechMoveKeyboardRightLeftDesc && moveKeyboardRightLeftRect.contains(viewXPosition, viewYPosition)) {
                    Common.defaultTextSpeech.speechText(moveKeyboardRightLeft.getContentDescription().toString());
                    vibrator.vibrate(100);
                    speechMakeKeyboardFullWidthDesc = true;
                    speechMoveKeyboardRightLeftDesc = false;
                }
            }

            //Action up
            if ((action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_HOVER_EXIT) && viewRect.contains(viewXPosition, viewYPosition)) {
                if (view.getId() != R.id.right_left_ops_btns_bar) {
                    interceptView = false;
                }
                switch (view.getId()) {
                    case R.id.makeKeyboardFullWidth:
                        playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                        ((LayoutParams) surfaceContainer.getLayoutParams()).width = ((RelativeLayout) surfaceContainer.getParent()).getWidth();
                        ((LayoutParams) surfaceContainer.getLayoutParams()).leftMargin = 0;
                        ((LayoutParams) surfaceContainer.getLayoutParams()).rightMargin = 0;
                        Common.putSettingInt("defaultKeyboardWidth", 100);
                        Common.putSettingInt("defaultKeyboardLandscapeWidth", 100);
                        Common.refreshSettings(true);
                        //Speech it
                        Common.defaultTextSpeech.speechText(context.getString(R.string.keyboard_is_full_width));
                        //Refresh the screen
                        surfaceContainer.removeAllViews();
                        brailleLayout.renderBrailleLayoutContainer(context);
                        brailleLayout.renderBrailleLayout();
                        requestLayout();
                        break;

                    case R.id.moveKeyboardRightLeft:
                        playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                        changeDirectionBtn(moveKeyboardRightLeft.getTag().toString());
                        break;
                }

                speechMakeKeyboardFullWidthDesc = true;
                speechMoveKeyboardRightLeftDesc = true;
                viewXPosition = 0;
                viewYPosition = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //--------------------------------------------------------------------------------------------//
    //Because of the screen reader
    @Override
    public boolean onHoverEvent(MotionEvent event) {
        if (Common.isTouchToExploreEnabled()) {
            return onTouchEvent(event);
        } else {
            return super.onHoverEvent(event);
        }
    }

    //--------------------------------------------------------------------------------------------//
    //To make sure the gestures out side the ops bars
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Common.touchInsideOpsBars = true;
        return super.dispatchTouchEvent(event);
    }

    //To make sure the gestures out side the ops bars
    @Override
    public boolean dispatchHoverEvent(MotionEvent event) {
        Common.touchInsideOpsBars = true;
        return super.dispatchHoverEvent(event);
    }

    //--------------------------------------------------------------------------------------------//
    //Intercept the touch if it happened in the view above the ops bars
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        interceptedPosX = (int) event.getX();
        interceptedPosY = (int) event.getY();
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        if (Common.isTouchToExploreEnabled()) {
            return onInterceptTouchEvent(event);
        } else {
            return super.onInterceptHoverEvent(event);
        }
    }
}
