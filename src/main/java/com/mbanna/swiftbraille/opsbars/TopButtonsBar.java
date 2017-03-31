package com.mbanna.swiftbraille.opsbars;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.mbanna.swiftbraille.BrailleIME;
import com.mbanna.swiftbraille.Common;
import com.mbanna.swiftbraille.KeyboardSurface;
import com.mbanna.swiftbraille.MyAccessibilityEventHandler;
import com.mbanna.swiftbraille.R;

public class TopButtonsBar extends RelativeLayout implements View.OnTouchListener {
    Context context;
    BrailleIME brailleIME;
    KeyboardSurface keyboardSurface;
    ImageButton changeKeyboardTypeUpButton, changeKeyboardTypeDownButton, copyButton, pasteButton,
            removeLastWordButton, removeFullTextButton, changeKeyboardLanguageButton;
    boolean interceptView = false, speechChangeKeyboardTypeUpButtonDesc = true, speechChangeKeyboardTypeDownButtonDesc = true,
            speechCopyButtonDesc = true, speechPasteButtonDesc = true, speechRemoveLastWordButtonDesc = true,
            speechRemoveFullTextButtonDesc = true, speechChangeKeyboardLanguageButtonDesc = true, viewAsPortrait;
    int interceptedPosX, interceptedPosY, viewXPosition = 0, viewYPosition = 0;
    Vibrator vibrator = null;
    Handler playDescSoundHandler = new Handler();
    Runnable playDescSoundHandlerRunnable = null;
    final int PLAY_DESC_SOUND_AFTER = 300;

    //Used to detect if the touch was inside the view or not
    Rect viewRect = new Rect();
    Rect copyButtonRect = new Rect();
    Rect pasteButtonRect = new Rect();
    Rect removeLastWordButtonRect = new Rect();
    Rect removeFullTextButtonRect = new Rect();
    Rect changeKeyboardTypeUpButtonRect = new Rect();
    Rect changeKeyboardTypeDownButtonRect = new Rect();
    Rect changeKeyboardLanguageButtonRect = new Rect();


    //--------------------------------------------------------------------------------------------//
    public TopButtonsBar(Context context, KeyboardSurface keyboardSurface, int inflatedLayout) {
        super(context);
        try {
            this.context = context;
            this.brailleIME = keyboardSurface.brailleIME;
            this.keyboardSurface = keyboardSurface;
            this.viewAsPortrait = Common.selectedDotsLayout == Common.PERKINS_DOTS_LAYOUT && !Common.isTablet && context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
            this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            MyAccessibilityEventHandler myAccessibilityEventHandler = new MyAccessibilityEventHandler();
            setOnTouchListener(this);

            //Views declarations
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(inflatedLayout, this, true);
            copyButton = (ImageButton) view.findViewById(R.id.copyButton);
            pasteButton = (ImageButton) view.findViewById(R.id.pasteButton);
            removeLastWordButton = (ImageButton) view.findViewById(R.id.removeLastWordButton);
            removeFullTextButton = (ImageButton) view.findViewById(R.id.removeFullTextButton);
            changeKeyboardTypeUpButton = (ImageButton) view.findViewById(R.id.changeKeyboardTypeUpButton);
            changeKeyboardTypeDownButton = (ImageButton) view.findViewById(R.id.changeKeyboardTypeDownButton);
            changeKeyboardLanguageButton = (ImageButton) view.findViewById(R.id.changeKeyboardLanguageButton);

            //Listeners
            changeKeyboardTypeUpButton.setAccessibilityDelegate(myAccessibilityEventHandler);
            changeKeyboardTypeUpButton.setOnTouchListener(this);

            changeKeyboardTypeDownButton.setAccessibilityDelegate(myAccessibilityEventHandler);
            changeKeyboardTypeDownButton.setOnTouchListener(this);

            copyButton.setAccessibilityDelegate(myAccessibilityEventHandler);
            copyButton.setOnTouchListener(this);

            pasteButton.setAccessibilityDelegate(myAccessibilityEventHandler);
            pasteButton.setOnTouchListener(this);

            removeLastWordButton.setAccessibilityDelegate(myAccessibilityEventHandler);
            removeLastWordButton.setOnTouchListener(this);

            removeFullTextButton.setAccessibilityDelegate(myAccessibilityEventHandler);
            removeFullTextButton.setOnTouchListener(this);

            changeKeyboardLanguageButton.setAccessibilityDelegate(myAccessibilityEventHandler);
            changeKeyboardLanguageButton.setOnTouchListener(this);

            //To get the width and height after before the view got rendered and set it on rectangles
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (viewAsPortrait) {
                            //Perkins dots layout
                            changeKeyboardTypeUpButtonRect.set(changeKeyboardTypeUpButton.getLeft(), Common.getRelativeTop(changeKeyboardTypeUpButton),
                                    changeKeyboardTypeUpButton.getRight(), Common.getRelativeTop(changeKeyboardTypeUpButton) + changeKeyboardTypeUpButton.getHeight());

                            changeKeyboardTypeDownButtonRect.set(changeKeyboardTypeDownButton.getLeft(), Common.getRelativeTop(changeKeyboardTypeDownButton),
                                    changeKeyboardTypeDownButton.getRight(), Common.getRelativeTop(changeKeyboardTypeDownButton) + changeKeyboardTypeUpButton.getHeight());

                            copyButtonRect.set(copyButton.getLeft(), Common.getRelativeTop(copyButton),
                                    copyButton.getRight(), Common.getRelativeTop(copyButton) + copyButton.getHeight());

                            pasteButtonRect.set(pasteButton.getLeft(), Common.getRelativeTop(pasteButton),
                                    pasteButton.getRight(), Common.getRelativeTop(pasteButton) + pasteButton.getHeight());

                            removeLastWordButtonRect.set(removeLastWordButton.getLeft(), Common.getRelativeTop(removeLastWordButton),
                                    removeLastWordButton.getRight(), Common.getRelativeTop(removeLastWordButton) + removeLastWordButton.getHeight());

                            removeFullTextButtonRect.set(removeFullTextButton.getLeft(), Common.getRelativeTop(removeFullTextButton),
                                    removeFullTextButton.getRight(), Common.getRelativeTop(removeFullTextButton) + removeFullTextButton.getHeight());

                            changeKeyboardLanguageButtonRect.set(changeKeyboardLanguageButton.getLeft(), Common.getRelativeTop(changeKeyboardLanguageButton),
                                    changeKeyboardLanguageButton.getRight(), Common.getRelativeTop(changeKeyboardLanguageButton) + changeKeyboardLanguageButton.getHeight());
                        } else {
                            //Other dots layouts
                            changeKeyboardTypeUpButtonRect.set(Common.getRelativeLeft(changeKeyboardTypeUpButton), changeKeyboardTypeUpButton.getTop(),
                                    Common.getRelativeLeft(changeKeyboardTypeUpButton) + changeKeyboardTypeUpButton.getWidth(), changeKeyboardTypeUpButton.getBottom());

                            changeKeyboardTypeDownButtonRect.set(Common.getRelativeLeft(changeKeyboardTypeDownButton), changeKeyboardTypeDownButton.getTop(),
                                    Common.getRelativeLeft(changeKeyboardTypeDownButton) + changeKeyboardTypeDownButton.getWidth(), changeKeyboardTypeDownButton.getBottom());

                            copyButtonRect.set(Common.getRelativeLeft(copyButton), copyButton.getTop(),
                                    Common.getRelativeLeft(copyButton) + copyButton.getWidth(), copyButton.getBottom());

                            pasteButtonRect.set(Common.getRelativeLeft(pasteButton), pasteButton.getTop(),
                                    Common.getRelativeLeft(pasteButton) + pasteButton.getWidth(), pasteButton.getBottom());

                            removeLastWordButtonRect.set(Common.getRelativeLeft(removeLastWordButton), removeLastWordButton.getTop(),
                                    Common.getRelativeLeft(removeLastWordButton) + removeLastWordButton.getWidth(), removeLastWordButton.getBottom());

                            removeFullTextButtonRect.set(Common.getRelativeLeft(removeFullTextButton), removeFullTextButton.getTop(),
                                    Common.getRelativeLeft(removeFullTextButton) + removeFullTextButton.getWidth(), removeFullTextButton.getBottom());

                            changeKeyboardLanguageButtonRect.set(Common.getRelativeLeft(changeKeyboardLanguageButton), changeKeyboardLanguageButton.getTop(),
                                    Common.getRelativeLeft(changeKeyboardLanguageButton) + changeKeyboardLanguageButton.getWidth(), changeKeyboardLanguageButton.getBottom());
                        }
                    }
                });
            }
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
                //For perkins view
                if (viewAsPortrait) {
                    viewRect.set(view.getLeft(), Common.getRelativeTop(view), view.getRight(), Common.getRelativeTop(view) + view.getHeight());
                } else {
                    viewRect.set(Common.getRelativeLeft(view), view.getTop(), Common.getRelativeLeft(view) + view.getWidth(), view.getBottom());
                }

                //If the tap over a button, intercept it and get the X and Y of the parent, not the child
                interceptView = view.getId() != R.id.top_ops_btns_bar;
                if (interceptView) {
                    viewXPosition = interceptedPosX;
                    viewYPosition = interceptedPosY;
                } else {
                    viewXPosition = (int) event.getX();
                    viewYPosition = (int) event.getY();
                }

                if (speechChangeKeyboardTypeUpButtonDesc && changeKeyboardTypeUpButtonRect.contains(viewXPosition, viewYPosition)) {
                    playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                    playDescSoundHandlerRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Common.defaultTextSpeech.speechText(changeKeyboardTypeUpButton.getContentDescription().toString());
                        }
                    };
                    playDescSoundHandler.postDelayed(playDescSoundHandlerRunnable, PLAY_DESC_SOUND_AFTER);
                    speechChangeKeyboardTypeUpButtonDesc = false;
                    vibrator.vibrate(100);
                } else if (speechChangeKeyboardTypeDownButtonDesc && changeKeyboardTypeDownButtonRect.contains(viewXPosition, viewYPosition)) {
                    playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                    playDescSoundHandlerRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Common.defaultTextSpeech.speechText(changeKeyboardTypeDownButton.getContentDescription().toString());
                        }
                    };
                    playDescSoundHandler.postDelayed(playDescSoundHandlerRunnable, PLAY_DESC_SOUND_AFTER);
                    speechChangeKeyboardTypeDownButtonDesc = false;
                    vibrator.vibrate(100);
                } else if (speechCopyButtonDesc && copyButtonRect.contains(viewXPosition, viewYPosition)) {
                    playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                    playDescSoundHandlerRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Common.defaultTextSpeech.speechText(copyButton.getContentDescription().toString());
                        }
                    };
                    playDescSoundHandler.postDelayed(playDescSoundHandlerRunnable, PLAY_DESC_SOUND_AFTER);
                    speechCopyButtonDesc = false;
                    vibrator.vibrate(100);
                } else if (speechPasteButtonDesc && pasteButtonRect.contains(viewXPosition, viewYPosition)) {
                    playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                    playDescSoundHandlerRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Common.defaultTextSpeech.speechText(pasteButton.getContentDescription().toString());
                        }
                    };
                    playDescSoundHandler.postDelayed(playDescSoundHandlerRunnable, PLAY_DESC_SOUND_AFTER);
                    speechPasteButtonDesc = false;
                    vibrator.vibrate(100);
                } else if (speechRemoveLastWordButtonDesc && removeLastWordButtonRect.contains(viewXPosition, viewYPosition)) {
                    playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                    playDescSoundHandlerRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Common.defaultTextSpeech.speechText(removeLastWordButton.getContentDescription().toString());
                        }
                    };
                    playDescSoundHandler.postDelayed(playDescSoundHandlerRunnable, PLAY_DESC_SOUND_AFTER);
                    speechRemoveLastWordButtonDesc = false;
                    vibrator.vibrate(100);
                } else if (speechRemoveFullTextButtonDesc && removeFullTextButtonRect.contains(viewXPosition, viewYPosition)) {
                    playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                    playDescSoundHandlerRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Common.defaultTextSpeech.speechText(removeFullTextButton.getContentDescription().toString());
                        }
                    };
                    playDescSoundHandler.postDelayed(playDescSoundHandlerRunnable, PLAY_DESC_SOUND_AFTER);
                    speechRemoveFullTextButtonDesc = false;
                    vibrator.vibrate(100);
                } else if (speechChangeKeyboardLanguageButtonDesc && changeKeyboardLanguageButtonRect.contains(viewXPosition, viewYPosition)) {
                    playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                    playDescSoundHandlerRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Common.defaultTextSpeech.speechText(changeKeyboardLanguageButton.getContentDescription().toString());
                        }
                    };
                    playDescSoundHandler.postDelayed(playDescSoundHandlerRunnable, PLAY_DESC_SOUND_AFTER);
                    speechChangeKeyboardLanguageButtonDesc = false;
                    vibrator.vibrate(100);
                }
            } else if (action == MotionEvent.ACTION_DOWN) {
                if (viewAsPortrait) {
                    viewRect.set(view.getLeft(), Common.getRelativeTop(view), view.getRight(), Common.getRelativeTop(view) + view.getHeight());
                } else {
                    viewRect.set(Common.getRelativeLeft(view), view.getTop(), Common.getRelativeLeft(view) + view.getWidth(), view.getBottom());
                }

                //If the tap over a button, intercept it and get the X and Y of the parent, not the child
                interceptView = view.getId() != R.id.top_ops_btns_bar;
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

                if (speechChangeKeyboardTypeUpButtonDesc && changeKeyboardTypeUpButtonRect.contains(viewXPosition, viewYPosition)) {
                    Common.defaultTextSpeech.speechText(changeKeyboardTypeUpButton.getContentDescription().toString());
                    vibrator.vibrate(100);
                    speechChangeKeyboardTypeUpButtonDesc = false;
                    speechChangeKeyboardTypeDownButtonDesc = true;
                    speechCopyButtonDesc = true;
                    speechPasteButtonDesc = true;
                    speechRemoveLastWordButtonDesc = true;
                    speechRemoveFullTextButtonDesc = true;
                    speechChangeKeyboardLanguageButtonDesc = true;
                } else if (speechChangeKeyboardTypeDownButtonDesc && changeKeyboardTypeDownButtonRect.contains(viewXPosition, viewYPosition)) {
                    Common.defaultTextSpeech.speechText(changeKeyboardTypeDownButton.getContentDescription().toString());
                    vibrator.vibrate(100);
                    speechChangeKeyboardTypeUpButtonDesc = true;
                    speechChangeKeyboardTypeDownButtonDesc = false;
                    speechCopyButtonDesc = true;
                    speechPasteButtonDesc = true;
                    speechRemoveLastWordButtonDesc = true;
                    speechRemoveFullTextButtonDesc = true;
                    speechChangeKeyboardLanguageButtonDesc = true;
                } else if (speechCopyButtonDesc && copyButtonRect.contains(viewXPosition, viewYPosition)) {
                    Common.defaultTextSpeech.speechText(copyButton.getContentDescription().toString());
                    vibrator.vibrate(100);
                    speechChangeKeyboardTypeUpButtonDesc = true;
                    speechChangeKeyboardTypeDownButtonDesc = true;
                    speechCopyButtonDesc = false;
                    speechPasteButtonDesc = true;
                    speechRemoveLastWordButtonDesc = true;
                    speechRemoveFullTextButtonDesc = true;
                    speechChangeKeyboardLanguageButtonDesc = true;
                } else if (speechPasteButtonDesc && pasteButtonRect.contains(viewXPosition, viewYPosition)) {
                    Common.defaultTextSpeech.speechText(pasteButton.getContentDescription().toString());
                    vibrator.vibrate(100);
                    speechChangeKeyboardTypeUpButtonDesc = true;
                    speechChangeKeyboardTypeDownButtonDesc = true;
                    speechCopyButtonDesc = true;
                    speechPasteButtonDesc = false;
                    speechRemoveLastWordButtonDesc = true;
                    speechRemoveFullTextButtonDesc = true;
                    speechChangeKeyboardLanguageButtonDesc = true;
                } else if (speechRemoveLastWordButtonDesc && removeLastWordButtonRect.contains(viewXPosition, viewYPosition)) {
                    Common.defaultTextSpeech.speechText(removeLastWordButton.getContentDescription().toString());
                    vibrator.vibrate(100);
                    speechChangeKeyboardTypeUpButtonDesc = true;
                    speechChangeKeyboardTypeDownButtonDesc = true;
                    speechCopyButtonDesc = true;
                    speechPasteButtonDesc = true;
                    speechRemoveLastWordButtonDesc = false;
                    speechRemoveFullTextButtonDesc = true;
                    speechChangeKeyboardLanguageButtonDesc = true;
                } else if (speechRemoveFullTextButtonDesc && removeFullTextButtonRect.contains(viewXPosition, viewYPosition)) {
                    Common.defaultTextSpeech.speechText(removeFullTextButton.getContentDescription().toString());
                    vibrator.vibrate(100);
                    speechChangeKeyboardTypeUpButtonDesc = true;
                    speechChangeKeyboardTypeDownButtonDesc = true;
                    speechCopyButtonDesc = true;
                    speechPasteButtonDesc = true;
                    speechRemoveLastWordButtonDesc = true;
                    speechRemoveFullTextButtonDesc = false;
                    speechChangeKeyboardLanguageButtonDesc = true;
                } else if (speechChangeKeyboardLanguageButtonDesc && changeKeyboardLanguageButtonRect.contains(viewXPosition, viewYPosition)) {
                    Common.defaultTextSpeech.speechText(changeKeyboardLanguageButton.getContentDescription().toString());
                    vibrator.vibrate(100);
                    speechChangeKeyboardTypeUpButtonDesc = true;
                    speechChangeKeyboardTypeDownButtonDesc = true;
                    speechCopyButtonDesc = true;
                    speechPasteButtonDesc = true;
                    speechRemoveLastWordButtonDesc = true;
                    speechRemoveFullTextButtonDesc = true;
                    speechChangeKeyboardLanguageButtonDesc = false;
                }
            }

            //Action up
            if ((action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_HOVER_EXIT) && viewRect.contains(viewXPosition, viewYPosition)) {
                if (view.getId() != R.id.top_ops_btns_bar) {
                    interceptView = false;
                }

                switch (view.getId()) {
                    case R.id.changeKeyboardTypeUpButton:
                        playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                        Common.changeToPreviousKeyboardType(keyboardSurface);
                        break;

                    case R.id.changeKeyboardTypeDownButton:
                        playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                        Common.changeToNextKeyboardType(keyboardSurface);
                        break;

                    case R.id.changeKeyboardLanguageButton:
                        playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                        Common.changeKeyboardLanguage(keyboardSurface, brailleIME.getCurrentInputConnection());
                        break;

                    case R.id.copyButton:
                        playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                        Common.copyText(brailleIME.getCurrentInputConnection());
                        break;

                    case R.id.pasteButton:
                        playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                        Common.pasteText(brailleIME.getCurrentInputConnection());
                        break;

                    case R.id.removeLastWordButton:
                        playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                        Common.removeLastWordFromText(brailleIME.getCurrentInputConnection());
                        break;

                    case R.id.removeFullTextButton:
                        playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                        Common.removeFullText(brailleIME.getCurrentInputConnection());
                        break;
                }

                // Speech all icons again
                speechChangeKeyboardTypeUpButtonDesc = true;
                speechChangeKeyboardTypeDownButtonDesc = true;
                speechChangeKeyboardLanguageButtonDesc = true;
                speechCopyButtonDesc = true;
                speechPasteButtonDesc = true;
                speechRemoveLastWordButtonDesc = true;
                speechRemoveFullTextButtonDesc = true;
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

    @Override
    public boolean dispatchHoverEvent(MotionEvent event) {
        Common.touchInsideOpsBars = true;
        return super.dispatchHoverEvent(event);
    }

    //--------------------------------------------------------------------------------------------//
    //Intercept the touch if it happened in the view above the ops bars
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (Common.selectedDotsLayout != Common.PERKINS_DOTS_LAYOUT) {
            interceptedPosX = (int) event.getRawX(); //Because of the width of the keyboard
        } else {
            interceptedPosX = (int) event.getX();
        }
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
