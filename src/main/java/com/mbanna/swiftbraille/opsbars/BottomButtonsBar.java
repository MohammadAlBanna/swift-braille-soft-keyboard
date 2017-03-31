package com.mbanna.swiftbraille.opsbars;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build;
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

public class BottomButtonsBar extends RelativeLayout implements View.OnTouchListener {
    Context context;
    BrailleIME brailleIME;
    ImageButton voiceInputButton, closeKeyboardButton, newLineButton, spaceCharButton, backspaceButton;
    View voiceInputSeparator;
    boolean interceptView = false, viewAsPortrait, speechCloseKeyboardButtonDesc = true, speechNewLineButtonDesc = true,
            speechSpaceCharButtonDesc = true, speechBackspaceButtonDesc = true, speechVoiceInputButtonDesc = true;
    int interceptedPosX, interceptedPosY, viewXPosition = 0, viewYPosition = 0;
    Vibrator vibrator = null;
    Handler playDescSoundHandler = new Handler();
    Runnable playDescSoundHandlerRunnable = null;
    final int PLAY_DESC_SOUND_AFTER = 300;

    //Used to detect if the touch was inside the view or not
    Rect viewRect = new Rect();
    Rect closeKeyboardButtonRect = new Rect();
    Rect newLineButtonRect = new Rect();
    Rect spaceCharButtonRect = new Rect();
    Rect backspaceButtonRect = new Rect();
    Rect voiceInputButtonRect = new Rect();

    //--------------------------------------------------------------------------------------------//
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public BottomButtonsBar(Context context, KeyboardSurface keyboardSurface, int inflatedLayout) {
        super(context);
        try {
            this.context = context;
            this.brailleIME = keyboardSurface.brailleIME;
            this.viewAsPortrait = Common.selectedDotsLayout == Common.PERKINS_DOTS_LAYOUT && !Common.isTablet && context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
            this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            MyAccessibilityEventHandler myAccessibilityEventHandler = new MyAccessibilityEventHandler();
            setOnTouchListener(this);

            //Views declarations
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(inflatedLayout, this, true);
            closeKeyboardButton = (ImageButton) view.findViewById(R.id.closeKeyboardButton);
            newLineButton = (ImageButton) view.findViewById(R.id.newLineButton);
            spaceCharButton = (ImageButton) view.findViewById(R.id.spaceCharButton);
            backspaceButton = (ImageButton) view.findViewById(R.id.backspaceButton);
            voiceInputButton = (ImageButton) view.findViewById(R.id.voiceInputButton);
            voiceInputSeparator = view.findViewById(R.id.voiceInputSeparator);

            //Listeners
            closeKeyboardButton.setAccessibilityDelegate(myAccessibilityEventHandler);
            closeKeyboardButton.setOnTouchListener(this);

            newLineButton.setAccessibilityDelegate(myAccessibilityEventHandler);
            newLineButton.setOnTouchListener(this);

            spaceCharButton.setAccessibilityDelegate(myAccessibilityEventHandler);
            spaceCharButton.setOnTouchListener(this);

            backspaceButton.setAccessibilityDelegate(myAccessibilityEventHandler);
            backspaceButton.setOnTouchListener(this);

            voiceInputButton.setAccessibilityDelegate(myAccessibilityEventHandler);
            voiceInputButton.setOnTouchListener(this);

            boolean isVoiceInputActivated = Common.activeVoiceInput;
            if (!isVoiceInputActivated) {
                voiceInputSeparator.setVisibility(GONE);
                voiceInputButton.setVisibility(GONE);
            } else {
                if (brailleIME.mVoiceRecognitionTrigger != null && !brailleIME.mVoiceRecognitionTrigger.isInstalled()) {
                    voiceInputSeparator.setVisibility(GONE);
                    voiceInputButton.setVisibility(GONE);
                }
            }

            //To get the width and height after before the view got rendered
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (viewAsPortrait) {
                            //Perkins dots layout
                            closeKeyboardButtonRect.set(closeKeyboardButton.getLeft(), Common.getRelativeTop(closeKeyboardButton),
                                    closeKeyboardButton.getRight(), Common.getRelativeTop(closeKeyboardButton) + closeKeyboardButton.getHeight());

                            newLineButtonRect.set(newLineButton.getLeft(), Common.getRelativeTop(newLineButton),
                                    newLineButton.getRight(), Common.getRelativeTop(newLineButton) + newLineButton.getHeight());

                            spaceCharButtonRect.set(spaceCharButton.getLeft(), Common.getRelativeTop(spaceCharButton),
                                    spaceCharButton.getRight(), Common.getRelativeTop(spaceCharButton) + spaceCharButton.getHeight());

                            backspaceButtonRect.set(backspaceButton.getLeft(), Common.getRelativeTop(backspaceButton),
                                    backspaceButton.getRight(), Common.getRelativeTop(backspaceButton) + backspaceButton.getHeight());

                            voiceInputButtonRect.set(voiceInputButton.getLeft(), Common.getRelativeTop(voiceInputButton),
                                    voiceInputButton.getRight(), Common.getRelativeTop(voiceInputButton) + voiceInputButton.getHeight());

                        } else {
                            //Other dots layouts
                            closeKeyboardButtonRect.set(Common.getRelativeLeft(closeKeyboardButton), closeKeyboardButton.getTop(),
                                    Common.getRelativeLeft(closeKeyboardButton) + closeKeyboardButton.getWidth(), closeKeyboardButton.getBottom());

                            newLineButtonRect.set(Common.getRelativeLeft(newLineButton), newLineButton.getTop(),
                                    Common.getRelativeLeft(newLineButton) + newLineButton.getWidth(), newLineButton.getBottom());

                            spaceCharButtonRect.set(Common.getRelativeLeft(spaceCharButton), spaceCharButton.getTop(),
                                    Common.getRelativeLeft(spaceCharButton) + spaceCharButton.getWidth(), spaceCharButton.getBottom());

                            backspaceButtonRect.set(Common.getRelativeLeft(backspaceButton), backspaceButton.getTop(),
                                    Common.getRelativeLeft(backspaceButton) + backspaceButton.getWidth(), backspaceButton.getBottom());

                            voiceInputButtonRect.set(Common.getRelativeLeft(voiceInputButton), voiceInputButton.getTop(),
                                    Common.getRelativeLeft(voiceInputButton) + voiceInputButton.getWidth(), voiceInputButton.getBottom());
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
                interceptView = view.getId() != R.id.bottom_ops_btns_bar;
                if (interceptView) {
                    viewXPosition = interceptedPosX;
                    viewYPosition = interceptedPosY;
                } else {
                    viewXPosition = (int) event.getX();
                    viewYPosition = (int) event.getY();
                }

                if (speechCloseKeyboardButtonDesc && closeKeyboardButtonRect.contains(viewXPosition, viewYPosition)) {
                    playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                    playDescSoundHandlerRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Common.defaultTextSpeech.speechText(closeKeyboardButton.getContentDescription().toString());
                        }
                    };
                    playDescSoundHandler.postDelayed(playDescSoundHandlerRunnable, PLAY_DESC_SOUND_AFTER);
                    speechCloseKeyboardButtonDesc = false;
                    vibrator.vibrate(100);
                } else if (speechNewLineButtonDesc && newLineButtonRect.contains(viewXPosition, viewYPosition)) {
                    playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                    playDescSoundHandlerRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Common.defaultTextSpeech.speechText(newLineButton.getContentDescription().toString());
                        }
                    };
                    playDescSoundHandler.postDelayed(playDescSoundHandlerRunnable, PLAY_DESC_SOUND_AFTER);
                    speechNewLineButtonDesc = false;
                    vibrator.vibrate(100);
                } else if (speechSpaceCharButtonDesc && spaceCharButtonRect.contains(viewXPosition, viewYPosition)) {
                    playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                    playDescSoundHandlerRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Common.defaultTextSpeech.speechText(spaceCharButton.getContentDescription().toString());
                        }
                    };
                    playDescSoundHandler.postDelayed(playDescSoundHandlerRunnable, PLAY_DESC_SOUND_AFTER);
                    speechSpaceCharButtonDesc = false;
                    vibrator.vibrate(100);
                } else if (speechBackspaceButtonDesc && backspaceButtonRect.contains(viewXPosition, viewYPosition)) {
                    playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                    playDescSoundHandlerRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Common.defaultTextSpeech.speechText(backspaceButton.getContentDescription().toString());
                        }
                    };
                    playDescSoundHandler.postDelayed(playDescSoundHandlerRunnable, PLAY_DESC_SOUND_AFTER);
                    speechBackspaceButtonDesc = false;
                    vibrator.vibrate(100);
                } else if (speechVoiceInputButtonDesc && voiceInputButtonRect.contains(viewXPosition, viewYPosition)) {
                    playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                    playDescSoundHandlerRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Common.defaultTextSpeech.speechText(voiceInputButton.getContentDescription().toString());
                        }
                    };
                    playDescSoundHandler.postDelayed(playDescSoundHandlerRunnable, PLAY_DESC_SOUND_AFTER);
                    speechVoiceInputButtonDesc = false;
                    vibrator.vibrate(100);
                }

            } else if (action == MotionEvent.ACTION_DOWN) {
                if (viewAsPortrait) {
                    viewRect.set(view.getLeft(), Common.getRelativeTop(view), view.getRight(), Common.getRelativeTop(view) + view.getHeight());
                } else {
                    viewRect.set(Common.getRelativeLeft(view), view.getTop(), Common.getRelativeLeft(view) + view.getWidth(), view.getBottom());
                }

                //If the tap over a button, intercept it and get the X and Y of the parent, not the child
                interceptView = view.getId() != R.id.bottom_ops_btns_bar;
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

                if (speechCloseKeyboardButtonDesc && closeKeyboardButtonRect.contains(viewXPosition, viewYPosition)) {
                    Common.defaultTextSpeech.speechText(closeKeyboardButton.getContentDescription().toString());
                    vibrator.vibrate(100);
                    speechCloseKeyboardButtonDesc = false;
                    speechNewLineButtonDesc = true;
                    speechSpaceCharButtonDesc = true;
                    speechBackspaceButtonDesc = true;
                    speechVoiceInputButtonDesc = true;
                } else if (speechNewLineButtonDesc && newLineButtonRect.contains(viewXPosition, viewYPosition)) {
                    Common.defaultTextSpeech.speechText(newLineButton.getContentDescription().toString());
                    vibrator.vibrate(100);
                    speechCloseKeyboardButtonDesc = true;
                    speechNewLineButtonDesc = false;
                    speechSpaceCharButtonDesc = true;
                    speechBackspaceButtonDesc = true;
                    speechVoiceInputButtonDesc = true;
                } else if (speechSpaceCharButtonDesc && spaceCharButtonRect.contains(viewXPosition, viewYPosition)) {
                    Common.defaultTextSpeech.speechText(spaceCharButton.getContentDescription().toString());
                    vibrator.vibrate(100);
                    speechCloseKeyboardButtonDesc = true;
                    speechNewLineButtonDesc = true;
                    speechSpaceCharButtonDesc = false;
                    speechBackspaceButtonDesc = true;
                    speechVoiceInputButtonDesc = true;
                } else if (speechBackspaceButtonDesc && backspaceButtonRect.contains(viewXPosition, viewYPosition)) {
                    Common.defaultTextSpeech.speechText(backspaceButton.getContentDescription().toString());
                    vibrator.vibrate(100);
                    speechCloseKeyboardButtonDesc = true;
                    speechNewLineButtonDesc = true;
                    speechSpaceCharButtonDesc = true;
                    speechBackspaceButtonDesc = false;
                    speechVoiceInputButtonDesc = true;
                } else if (speechVoiceInputButtonDesc && voiceInputButtonRect.contains(viewXPosition, viewYPosition)) {
                    Common.defaultTextSpeech.speechText(voiceInputButton.getContentDescription().toString());
                    vibrator.vibrate(100);
                    speechCloseKeyboardButtonDesc = true;
                    speechNewLineButtonDesc = true;
                    speechSpaceCharButtonDesc = true;
                    speechBackspaceButtonDesc = true;
                    speechVoiceInputButtonDesc = false;
                }
            }

            //Action up
            if ((action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_HOVER_EXIT) && viewRect.contains(viewXPosition, viewYPosition)) {
                if (view.getId() != R.id.bottom_ops_btns_bar) {
                    interceptView = false;
                }

                switch (view.getId()) {
                    case R.id.voiceInputButton:
                        playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                        Common.speakHiddenKeyboard = false;
                        brailleIME.mVoiceRecognitionTrigger.startVoiceRecognition(Common.currentLocaleLanguage.getLanguage());
                        break;

                    case R.id.closeKeyboardButton:
                        playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                        brailleIME.requestHideSelf(0);
                        break;

                    case R.id.newLineButton:
                        playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                        Common.makeNewLine(brailleIME);
                        break;

                    case R.id.spaceCharButton:
                        playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                        Common.typeSpaceChar(brailleIME.getCurrentInputConnection());
                        break;

                    case R.id.backspaceButton:
                        playDescSoundHandler.removeCallbacks(playDescSoundHandlerRunnable);
                        Common.removeLastCharFromText(brailleIME.getCurrentInputConnection());
                        break;
                }

                speechCloseKeyboardButtonDesc = true;
                speechNewLineButtonDesc = true;
                speechSpaceCharButtonDesc = true;
                speechBackspaceButtonDesc = true;
                speechVoiceInputButtonDesc = true;
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
