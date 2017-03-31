package com.mbanna.swiftbraille;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.mbanna.swiftbraille.patterns.es.SpanishCapitalCharPattern;
import com.mbanna.swiftbraille.patterns.es.SpanishNumbersPattern;
import com.mbanna.swiftbraille.patterns.es.SpanishSpecialPattern;
import com.mbanna.swiftbraille.patterns.fr.FrenchCapitalCharPattern;
import com.mbanna.swiftbraille.patterns.fr.FrenchNumbersPattern;
import com.mbanna.swiftbraille.patterns.fr.FrenchSpecialPattern;
import com.mbanna.swiftbraille.surfacelayout.BrailleCellLayout;
import com.mbanna.swiftbraille.surfacelayout.BrailleLayout;
import com.mbanna.swiftbraille.surfacelayout.BraillePerkinsLayout;
import com.mbanna.swiftbraille.surfacelayout.BrailleTwoLinesLayout;
import com.mbanna.swiftbraille.patterns.Pattern;
import com.mbanna.swiftbraille.patterns.ar.ArabicSpecialPattern;
import com.mbanna.swiftbraille.patterns.en.EnglishSpecialPattern;
import com.mbanna.swiftbraille.patterns.ar.ArabicNumbersPattern;
import com.mbanna.swiftbraille.patterns.en.EnglishCapitalCharPattern;
import com.mbanna.swiftbraille.patterns.en.EnglishNumbersPattern;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.os.Vibrator;

//The layout that will render Braille Dots and touches events
public class KeyboardSurface extends RelativeLayout implements View.OnTouchListener {
    //Common
    public BrailleIME brailleIME;
    Context context;
    BrailleLayout brailleLayout;

    //BrailleDots info
    BrailleDot brailleDot1 = null, brailleDot2 = null, brailleDot3 = null, brailleDot4 = null,
            brailleDot5 = null, brailleDot6 = null;
    int brailleDotRadius, brailleDotRadiusDoubled;

    //Selected dots pattern
    long currentSystemTime;
    ArrayList<Integer> selectedPatternArrayList = new ArrayList<>();
    FixedArrayList savedLastMove = new FixedArrayList(2); //For stop a little bit over the dot to be in the selected pattern
    Vibrator vibrator = null;
    public Pattern patternSurface;
    Pattern previousPatternSurface; //For Braille indicators
    int previousBrailleIndicator = -1; //For Braille indicators
    @SuppressWarnings("unchecked")
    Set<Integer> selectedPattern = new HashSet(selectedPatternArrayList);
    int miSecondsToCaptureMovesDot = 0;

    //Settings
    boolean playAlwaysStoredVoices, vibrationOnDotTap, capitalizeFirstWord, showOperationsButtons,
            isGesturesEnabled, isInsideBrailleDot;
    int brailleDotSound, selectedDotsPeriod;

    //For play selected music
    boolean playBrailleDot1Sound = true, playBrailleDot2Sound = true, playBrailleDot3Sound = true,
            playBrailleDot4Sound = true, playBrailleDot5Sound = true, playBrailleDot6Sound = true;

    //Detect special moves inside free area
    boolean isSpecialMove, isMainFingerUp = true, isPointersFingersUp = true;
    boolean rotateGestures = false; //This is when the user choose perkins in portrait layout
    float specialMoveX1, specialMoveX2, specialMoveY1, specialMoveY2, specialMovePointer2X1,
            specialMovePointer2X2, specialMovePointer2Y1, specialMovePointer2Y2;

    //Get double tap gesture
    GestureDoubleTap gestureDoubleTap = new GestureDoubleTap();
    GestureDetector gestureDetector;

    //---------------------------------------------------------//
    //User finished enter Braille pattern
    private Handler selectedPatternHandler = new Handler();
    private Runnable selectedPatternRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                if (!isSpecialMove && selectedPattern.size() > 0) {
                    patternSurface.setPattern(selectedPattern);
                    if (patternSurface.getPatternResult() != null) {
                        if (capitalizeFirstWord && (Common.currentLanguage == Common.ENGLISH_LANGUAGE_INPUT_METHOD || Common.currentLanguage == Common.SPANISH_LANGUAGE_INPUT_METHOD) && Common.isFirstSentenceIndicate(brailleIME.getCurrentInputConnection())) {
                            boolean textCommitted = brailleIME.getCurrentInputConnection().commitText(patternSurface.getPatternResult().toUpperCase(), 1);
                            //Say the capital sound
                            if (textCommitted && Common.talkBackChar) {
                                Common.defaultTextSpeech.speechLocaleKeyboardText(context.getString(R.string.in_uppercase) + " " + patternSurface.getPatternResult().toUpperCase());
                            }
                        } else {
                            boolean textCommitted = brailleIME.getCurrentInputConnection().commitText(patternSurface.getPatternResult(), 1);
                            //Say the sound
                            if (textCommitted && Common.talkBackChar) {
                                if ((!Common.isTouchToExploreEnabled() && playAlwaysStoredVoices && patternSurface.getPatternSoundPronounce() != -1) || (!Common.defaultTextSpeech.isArabicSupported() && Common.currentLanguage == Common.ARABIC_LANGUAGE_INPUT_METHOD && patternSurface.getPatternSoundPronounce() != -1)) {
                                    Common.runPatternSoundPronounce(patternSurface.getPatternSoundPronounce(), true);
                                } else {
                                    Common.defaultTextSpeech.speechLocaleKeyboardText(patternSurface.getPatternPronounce());
                                }
                            }
                        }

                        //If it was indicator and the user typed what he needed, back to previous keyboard
                        if (previousPatternSurface != null) {
                            patternSurface = previousPatternSurface;
                            previousPatternSurface = null;
                            previousBrailleIndicator = -1;
                        }
                    } else if (previousBrailleIndicator == -1 || previousBrailleIndicator != patternSurface.brailleIndicatorType(selectedPattern)) {
                        //This not a Braille Code, check if this is an indicator
                        if (patternSurface.brailleIndicatorType(selectedPattern) == 0) {
                            previousBrailleIndicator = 0;
                            previousPatternSurface = patternSurface;
                            //Capital letters
                            if (Common.currentLanguage == Common.ENGLISH_LANGUAGE_INPUT_METHOD) {
                                patternSurface = new EnglishCapitalCharPattern(context);
                            } else if(Common.currentLanguage == Common.SPANISH_LANGUAGE_INPUT_METHOD){
                                patternSurface = new SpanishCapitalCharPattern(context, brailleIME.getCurrentInputConnection());
                            } else if(Common.currentLanguage == Common.FRENCH_LANGUAGE_INPUT_METHOD){
                                patternSurface = new FrenchCapitalCharPattern(context);
                            }
                            //Speech the class title
                            patternSurface.speechClassTitle();
                        } else if (patternSurface.brailleIndicatorType(selectedPattern) == 1) {
                            //Numbers keyboard
                            previousBrailleIndicator = 1;
                            previousPatternSurface = patternSurface;
                            if(Common.currentLanguage == Common.ENGLISH_LANGUAGE_INPUT_METHOD) {
                                patternSurface = new EnglishNumbersPattern(context);
                            } else if (Common.currentLanguage == Common.ARABIC_LANGUAGE_INPUT_METHOD) {
                                patternSurface = new ArabicNumbersPattern(context);
                            } else if(Common.currentLanguage == Common.SPANISH_LANGUAGE_INPUT_METHOD){
                                patternSurface = new SpanishNumbersPattern(context, brailleIME.getCurrentInputConnection());
                            } else if(Common.currentLanguage == Common.FRENCH_LANGUAGE_INPUT_METHOD){
                                patternSurface = new FrenchNumbersPattern(context);
                            }
                            //Speech the class title
                            patternSurface.speechClassTitle();
                        } else if (patternSurface.brailleIndicatorType(selectedPattern) == 2) {
                            //For general symbols keyboard
                            previousBrailleIndicator = 2;
                            previousPatternSurface = patternSurface;
                            if(Common.currentLanguage == Common.ENGLISH_LANGUAGE_INPUT_METHOD) {
                                patternSurface = new EnglishSpecialPattern(context);
                            } else if (Common.currentLanguage == Common.ARABIC_LANGUAGE_INPUT_METHOD) {
                                patternSurface = new ArabicSpecialPattern(context);
                            } else if(Common.currentLanguage == Common.SPANISH_LANGUAGE_INPUT_METHOD){
                                patternSurface = new SpanishSpecialPattern(context, brailleIME.getCurrentInputConnection());
                            } else if(Common.currentLanguage == Common.FRENCH_LANGUAGE_INPUT_METHOD){
                                patternSurface = new FrenchSpecialPattern(context);
                            }
                            //Speech the class title
                            patternSurface.speechClassTitle();
                        } else {
                            //not a braille code or even indicator
                            if ((!Common.isTouchToExploreEnabled() && playAlwaysStoredVoices && Common.currentSystemLanguage.equals("ar") && Common.getSoundPath("ar_message_not_braille_code") != -1) || (!Common.defaultTextSpeech.isArabicSupported() && Common.currentSystemLanguage.equals("ar") && Common.getSoundPath("ar_message_not_braille_code") != -1)) {
                                Common.runPatternSoundPronounce(Common.getSoundPath("ar_message_not_braille_code"), true);
                            } else {
                                Common.defaultTextSpeech.speechText(context.getString(R.string.not_braille_code), true);
                            }
                        }
                    } else {
                        patternSurface = previousPatternSurface;
                        //Speech the class title
                        patternSurface.speechClassTitle();
                        previousPatternSurface = null;
                        previousBrailleIndicator = -1;
                    }
                }

                //Clear other arrays
                selectedPattern.clear();
                savedLastMove.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    //--------------------------------------------------------------------------------------------//
    //Render the layout and BrailleSurface
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public KeyboardSurface(BrailleIME brailleIME, Context context) {
        super(context);
        try {
            this.context = context;
            this.brailleIME = brailleIME;
            setLayoutDirection(LAYOUT_DIRECTION_LTR);

            //Start loading settings
            miSecondsToCaptureMovesDot = Common.stopOverDotTime;
            playAlwaysStoredVoices = Common.playAlwaysStoredVoices;
            vibrationOnDotTap = Common.vibrationOnDotTap;
            capitalizeFirstWord = Common.capitalizeFirstWord;
            brailleDotSound = Common.defaultBrailleDotSound;
            selectedDotsPeriod = Common.selectedDotsNumbersPeriod;
            showOperationsButtons = Common.showOperationsButtons;
            isGesturesEnabled = Common.activateGestures;
            int selectedDotsLayout = Common.selectedDotsLayout;
            rotateGestures = this.context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && Common.selectedDotsLayout == Common.PERKINS_DOTS_LAYOUT;

            //If the user changes the current system language
            Common.checkSystemLanguageAndLocale();

            //Vibration on dot tap
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

            //Detect double tap gesture to hide the keyboard
            gestureDetector = new GestureDetector(context, gestureDoubleTap);
            setOnTouchListener(this);
            setBackgroundColor(Common.getMyColor(R.color.swiftBrailleBG));

            //Add the surface container to the keyboard surface
            //Get the braille layout
            if (selectedDotsLayout == Common.PERKINS_DOTS_LAYOUT) {
                //Perkins layout
                brailleLayout = new BraillePerkinsLayout(context, this);
            } else if (selectedDotsLayout == Common.TWO_ROWS_DOTS_LAYOUT) {
                //Two rows layout
                brailleLayout = new BrailleTwoLinesLayout(context, this);
            } else {
                //Braille cell layout
                brailleLayout = new BrailleCellLayout(context, this);
            }

            RelativeLayout brailleLayoutContainer = brailleLayout.renderBrailleLayoutContainer(context);
            brailleDot1 = brailleLayout.getBrailleDot1();
            brailleDot2 = brailleLayout.getBrailleDot2();
            brailleDot3 = brailleLayout.getBrailleDot3();
            brailleDot4 = brailleLayout.getBrailleDot4();
            brailleDot5 = brailleLayout.getBrailleDot5();
            brailleDot6 = brailleLayout.getBrailleDot6();
            brailleDotRadius = brailleDot1.getRadius();
            brailleDotRadiusDoubled = brailleDotRadius * brailleDotRadius;

            //Show and hide Arabic/English keyboards types based on current keyboard language
            if (patternSurface == null) {
                Common.setPatternSurfaceLanguage(this, brailleIME.getCurrentInputConnection());
            }
            addView(brailleLayoutContainer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            //This is just if the user selected perkins layout, and he uses the device in portrait mode
            if (brailleDot1 == null) {
                return false;
            }

            int action = event.getActionMasked();
            int pointerCount = event.getPointerCount();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_HOVER_ENTER:
                    isMainFingerUp = false;

                    //Clear the last post
                    selectedPatternHandler.removeCallbacks(selectedPatternRunnable);
                    boolean isDownDot1Selected = (event.getX() - (brailleDot1.getX() + brailleDotRadius)) * (event.getX() - (brailleDot1.getX() + brailleDotRadius)) + (event.getY() - (brailleDot1.getY() + brailleDotRadius)) * (event.getY() - (brailleDot1.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                    boolean isDownDot2Selected = (event.getX() - (brailleDot2.getX() + brailleDotRadius)) * (event.getX() - (brailleDot2.getX() + brailleDotRadius)) + (event.getY() - (brailleDot2.getY() + brailleDotRadius)) * (event.getY() - (brailleDot2.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                    boolean isDownDot3Selected = (event.getX() - (brailleDot3.getX() + brailleDotRadius)) * (event.getX() - (brailleDot3.getX() + brailleDotRadius)) + (event.getY() - (brailleDot3.getY() + brailleDotRadius)) * (event.getY() - (brailleDot3.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                    boolean isDownDot4Selected = (event.getX() - (brailleDot4.getX() + brailleDotRadius)) * (event.getX() - (brailleDot4.getX() + brailleDotRadius)) + (event.getY() - (brailleDot4.getY() + brailleDotRadius)) * (event.getY() - (brailleDot4.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                    boolean isDownDot5Selected = (event.getX() - (brailleDot5.getX() + brailleDotRadius)) * (event.getX() - (brailleDot5.getX() + brailleDotRadius)) + (event.getY() - (brailleDot5.getY() + brailleDotRadius)) * (event.getY() - (brailleDot5.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                    boolean isDownDot6Selected = (event.getX() - (brailleDot6.getX() + brailleDotRadius)) * (event.getX() - (brailleDot6.getX() + brailleDotRadius)) + (event.getY() - (brailleDot6.getY() + brailleDotRadius)) * (event.getY() - (brailleDot6.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;

                    if (isDownDot1Selected) {
                        isInsideBrailleDot = true;
                        selectedPattern.add(1);
                        brailleDot1.fillTheDot();
                        if (vibrationOnDotTap) {
                            vibrator.vibrate(100);
                        }

                        //Braille Dot sound
                        if (brailleDotSound != 0 && playBrailleDot1Sound) {
                            playBrailleDot1Sound = false;
                            Common.playBrailleDotSound(brailleDotSound, "one");
                        }
                    }

                    if (isDownDot2Selected) {
                        isInsideBrailleDot = true;
                        selectedPattern.add(2);
                        brailleDot2.fillTheDot();
                        if (vibrationOnDotTap) {
                            vibrator.vibrate(100);
                        }

                        //Braille Dot sound
                        if (brailleDotSound != 0 && playBrailleDot2Sound) {
                            playBrailleDot2Sound = false;
                            Common.playBrailleDotSound(brailleDotSound, "two");
                        }
                    }

                    if (isDownDot3Selected) {
                        isInsideBrailleDot = true;
                        selectedPattern.add(3);
                        brailleDot3.fillTheDot();
                        if (vibrationOnDotTap) {
                            vibrator.vibrate(100);
                        }

                        //Braille Dot sound
                        if (brailleDotSound != 0 && playBrailleDot3Sound) {
                            playBrailleDot3Sound = false;
                            Common.playBrailleDotSound(brailleDotSound, "three");
                        }
                    }

                    if (isDownDot4Selected) {
                        isInsideBrailleDot = true;
                        selectedPattern.add(4);
                        brailleDot4.fillTheDot();
                        if (vibrationOnDotTap) {
                            vibrator.vibrate(100);
                        }

                        //Braille Dot sound
                        if (brailleDotSound != 0 && playBrailleDot4Sound) {
                            playBrailleDot4Sound = false;
                            Common.playBrailleDotSound(brailleDotSound, "four");
                        }
                    }

                    if (isDownDot5Selected) {
                        isInsideBrailleDot = true;
                        selectedPattern.add(5);
                        brailleDot5.fillTheDot();
                        if (vibrationOnDotTap) {
                            vibrator.vibrate(100);
                        }

                        //Braille Dot sound
                        if (brailleDotSound != 0 && playBrailleDot5Sound) {
                            playBrailleDot5Sound = false;
                            Common.playBrailleDotSound(brailleDotSound, "five");
                        }
                    }

                    if (isDownDot6Selected) {
                        isInsideBrailleDot = true;
                        selectedPattern.add(6);
                        brailleDot6.fillTheDot();
                        if (vibrationOnDotTap) {
                            vibrator.vibrate(100);
                        }

                        //Braille Dot sound
                        if (brailleDotSound != 0 && playBrailleDot6Sound) {
                            playBrailleDot6Sound = false;
                            Common.playBrailleDotSound(brailleDotSound, "six");
                        }
                    }

                    //Check for special moves in free area
                    if (!isInsideBrailleDot && !Common.touchInsideOpsBars) {
                        if (rotateGestures) {
                            specialMoveX1 = event.getY(0);
                            specialMoveY1 = event.getX(0);
                        } else {
                            specialMoveX1 = event.getX(0);
                            specialMoveY1 = event.getY(0);
                        }
                    } else {
                        specialMoveX1 = 0;
                        specialMoveY1 = 0;
                    }
                    break;
                case (MotionEvent.ACTION_POINTER_DOWN):
                    isPointersFingersUp = false;

                    //Clear the last post
                    selectedPatternHandler.removeCallbacks(selectedPatternRunnable);

                    //To handle multi-touch
                    for (int pointers = 1; pointers < pointerCount; pointers++) {
                        boolean isPointerDownDot1Selected = (event.getX(pointers) - (brailleDot1.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot1.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot1.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot1.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                        boolean isPointerDownDot2Selected = (event.getX(pointers) - (brailleDot2.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot2.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot2.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot2.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                        boolean isPointerDownDot3Selected = (event.getX(pointers) - (brailleDot3.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot3.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot3.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot3.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                        boolean isPointerDownDot4Selected = (event.getX(pointers) - (brailleDot4.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot4.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot4.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot4.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                        boolean isPointerDownDot5Selected = (event.getX(pointers) - (brailleDot5.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot5.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot5.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot5.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                        boolean isPointerDownDot6Selected = (event.getX(pointers) - (brailleDot6.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot6.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot6.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot6.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;

                        if (isPointerDownDot1Selected) {
                            isInsideBrailleDot = true;
                            selectedPattern.add(1);
                            brailleDot1.fillTheDot();
                            if (vibrationOnDotTap) {
                                vibrator.vibrate(100);
                            }

                            //Braille Dot sound
                            if (brailleDotSound != 0 && playBrailleDot1Sound) {
                                playBrailleDot1Sound = false;
                                Common.playBrailleDotSound(brailleDotSound, "one");
                            }
                        }

                        if (isPointerDownDot2Selected) {
                            isInsideBrailleDot = true;
                            selectedPattern.add(2);
                            brailleDot2.fillTheDot();
                            if (vibrationOnDotTap) {
                                vibrator.vibrate(100);
                            }

                            //Braille Dot sound
                            if (brailleDotSound != 0 && playBrailleDot2Sound) {
                                playBrailleDot2Sound = false;
                                Common.playBrailleDotSound(brailleDotSound, "two");
                            }
                        }

                        if (isPointerDownDot3Selected) {
                            isInsideBrailleDot = true;
                            selectedPattern.add(3);
                            brailleDot3.fillTheDot();
                            if (vibrationOnDotTap) {
                                vibrator.vibrate(100);
                            }

                            //Braille Dot sound
                            if (brailleDotSound != 0 && playBrailleDot3Sound) {
                                playBrailleDot3Sound = false;
                                Common.playBrailleDotSound(brailleDotSound, "three");
                            }
                        }

                        if (isPointerDownDot4Selected) {
                            isInsideBrailleDot = true;
                            selectedPattern.add(4);
                            brailleDot4.fillTheDot();
                            if (vibrationOnDotTap) {
                                vibrator.vibrate(100);
                            }

                            //Braille Dot sound
                            if (brailleDotSound != 0 && playBrailleDot4Sound) {
                                playBrailleDot4Sound = false;
                                Common.playBrailleDotSound(brailleDotSound, "four");
                            }
                        }

                        if (isPointerDownDot5Selected) {
                            isInsideBrailleDot = true;
                            selectedPattern.add(5);
                            brailleDot5.fillTheDot();
                            if (vibrationOnDotTap) {
                                vibrator.vibrate(100);
                            }

                            //Braille Dot sound
                            if (brailleDotSound != 0 && playBrailleDot5Sound) {
                                playBrailleDot5Sound = false;
                                Common.playBrailleDotSound(brailleDotSound, "five");
                            }
                        }

                        if (isPointerDownDot6Selected) {
                            isInsideBrailleDot = true;
                            selectedPattern.add(6);
                            brailleDot6.fillTheDot();
                            if (vibrationOnDotTap) {
                                vibrator.vibrate(100);
                            }

                            //Braille Dot sound
                            if (brailleDotSound != 0 && playBrailleDot6Sound) {
                                playBrailleDot6Sound = false;
                                Common.playBrailleDotSound(brailleDotSound, "six");
                            }
                        }
                    } //End for loop

                    //The second down finger
                    //Check for special moves in free area
                    if (!isInsideBrailleDot && !Common.touchInsideOpsBars) {
                        if (rotateGestures) {
                            specialMovePointer2X1 = event.getY(1);
                            specialMovePointer2Y1 = event.getX(1);
                        } else {
                            specialMovePointer2X1 = event.getX(1);
                            specialMovePointer2Y1 = event.getY(1);
                        }
                    } else {
                        specialMovePointer2X1 = 0;
                        specialMovePointer2Y1 = 0;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_HOVER_MOVE:
                    //Must be outside of the free area, because of gestures
                    if (specialMoveX1 == 0 && specialMovePointer2X1 == 0) {
                        for (int pointers = 0; pointers < pointerCount; pointers++) {
                            boolean isMovePointerDot1Selected = (event.getX(pointers) - (brailleDot1.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot1.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot1.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot1.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                            boolean isMovePointerDot2Selected = (event.getX(pointers) - (brailleDot2.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot2.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot2.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot2.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                            boolean isMovePointerDot3Selected = (event.getX(pointers) - (brailleDot3.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot3.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot3.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot3.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                            boolean isMovePointerDot4Selected = (event.getX(pointers) - (brailleDot4.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot4.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot4.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot4.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                            boolean isMovePointerDot5Selected = (event.getX(pointers) - (brailleDot5.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot5.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot5.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot5.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                            boolean isMovePointerDot6Selected = (event.getX(pointers) - (brailleDot6.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot6.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot6.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot6.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;

                            if (isMovePointerDot1Selected) {
                                currentSystemTime = System.currentTimeMillis();
                                savedLastMove.addItem(currentSystemTime);
                                if (savedLastMove.size() >= 2 && ((long) savedLastMove.get(1) - (long) savedLastMove.get(0)) >= miSecondsToCaptureMovesDot) {
                                    selectedPattern.add(1);
                                    brailleDot1.fillTheDot();
                                    if (vibrationOnDotTap) {
                                        vibrator.vibrate(100);
                                    }

                                    //Braille Dot sound
                                    if (brailleDotSound != 0 && playBrailleDot1Sound) {
                                        playBrailleDot1Sound = false;
                                        Common.playBrailleDotSound(brailleDotSound, "one");
                                    }
                                }
                            }

                            if (isMovePointerDot2Selected) {
                                currentSystemTime = System.currentTimeMillis();
                                savedLastMove.addItem(currentSystemTime);
                                if (savedLastMove.size() >= 2 && ((long) savedLastMove.get(1) - (long) savedLastMove.get(0)) >= miSecondsToCaptureMovesDot) {
                                    selectedPattern.add(2);
                                    brailleDot2.fillTheDot();
                                    if (vibrationOnDotTap) {
                                        vibrator.vibrate(100);
                                    }

                                    //Braille Dot sound
                                    if (brailleDotSound != 0 && playBrailleDot2Sound) {
                                        playBrailleDot2Sound = false;
                                        Common.playBrailleDotSound(brailleDotSound, "two");
                                    }
                                }
                            }

                            if (isMovePointerDot3Selected) {
                                currentSystemTime = System.currentTimeMillis();
                                savedLastMove.addItem(currentSystemTime);
                                if (savedLastMove.size() >= 2 && ((long) savedLastMove.get(1) - (long) savedLastMove.get(0)) >= miSecondsToCaptureMovesDot) {
                                    selectedPattern.add(3);
                                    brailleDot3.fillTheDot();
                                    if (vibrationOnDotTap) {
                                        vibrator.vibrate(100);
                                    }

                                    //Braille Dot sound
                                    if (brailleDotSound != 0 && playBrailleDot3Sound) {
                                        playBrailleDot3Sound = false;
                                        Common.playBrailleDotSound(brailleDotSound, "three");
                                    }
                                }
                            }

                            if (isMovePointerDot4Selected) {
                                currentSystemTime = System.currentTimeMillis();
                                savedLastMove.addItem(currentSystemTime);
                                if (savedLastMove.size() >= 2 && ((long) savedLastMove.get(1) - (long) savedLastMove.get(0)) >= miSecondsToCaptureMovesDot) {
                                    selectedPattern.add(4);
                                    brailleDot4.fillTheDot();
                                    if (vibrationOnDotTap) {
                                        vibrator.vibrate(100);
                                    }

                                    //Braille Dot sound
                                    if (brailleDotSound != 0 && playBrailleDot4Sound) {
                                        playBrailleDot4Sound = false;
                                        Common.playBrailleDotSound(brailleDotSound, "four");
                                    }
                                }
                            }

                            if (isMovePointerDot5Selected) {
                                currentSystemTime = System.currentTimeMillis();
                                savedLastMove.addItem(currentSystemTime);
                                if (savedLastMove.size() >= 2 && ((long) savedLastMove.get(1) - (long) savedLastMove.get(0)) >= miSecondsToCaptureMovesDot) {
                                    selectedPattern.add(5);
                                    brailleDot5.fillTheDot();
                                    if (vibrationOnDotTap) {
                                        vibrator.vibrate(100);
                                    }

                                    //Braille Dot sound
                                    if (brailleDotSound != 0 && playBrailleDot5Sound) {
                                        playBrailleDot5Sound = false;
                                        Common.playBrailleDotSound(brailleDotSound, "five");
                                    }
                                }
                            }

                            if (isMovePointerDot6Selected) {
                                currentSystemTime = System.currentTimeMillis();
                                savedLastMove.addItem(currentSystemTime);
                                if (savedLastMove.size() >= 2 && ((long) savedLastMove.get(1) - (long) savedLastMove.get(0)) >= miSecondsToCaptureMovesDot) {
                                    selectedPattern.add(6);
                                    brailleDot6.fillTheDot();
                                    if (vibrationOnDotTap) {
                                        vibrator.vibrate(100);
                                    }

                                    //Braille Dot sound
                                    if (brailleDotSound != 0 && playBrailleDot6Sound) {
                                        playBrailleDot6Sound = false;
                                        Common.playBrailleDotSound(brailleDotSound, "six");
                                    }
                                }
                            }

                            //Clear the last moves if the finger moves inside free areas
                            if (!isMovePointerDot1Selected && !isMovePointerDot2Selected && !isMovePointerDot3Selected && !isMovePointerDot4Selected && !isMovePointerDot5Selected && !isMovePointerDot6Selected) {
                                savedLastMove.clear();

                                //Cancel the vibration
                                if (vibrationOnDotTap) {
                                    vibrator.cancel();
                                }
                            }
                        } //End for loop
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    isPointersFingersUp = true;
                    for (int pointers = 1; pointers < pointerCount; pointers++) {
                        boolean isPointerUpDot1Selected = (event.getX(pointers) - (brailleDot1.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot1.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot1.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot1.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                        boolean isPointerUpDot2Selected = (event.getX(pointers) - (brailleDot2.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot2.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot2.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot2.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                        boolean isPointerUpDot3Selected = (event.getX(pointers) - (brailleDot3.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot3.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot3.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot3.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                        boolean isPointerUpDot4Selected = (event.getX(pointers) - (brailleDot4.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot4.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot4.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot4.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                        boolean isPointerUpDot5Selected = (event.getX(pointers) - (brailleDot5.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot5.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot5.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot5.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                        boolean isPointerUpDot6Selected = (event.getX(pointers) - (brailleDot6.getX() + brailleDotRadius)) * (event.getX(pointers) - (brailleDot6.getX() + brailleDotRadius)) + (event.getY(pointers) - (brailleDot6.getY() + brailleDotRadius)) * (event.getY(pointers) - (brailleDot6.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;

                        if (isPointerUpDot1Selected) {
                            selectedPattern.add(1);
                        }

                        if (isPointerUpDot2Selected) {
                            selectedPattern.add(2);
                        }

                        if (isPointerUpDot3Selected) {
                            selectedPattern.add(3);
                        }

                        if (isPointerUpDot4Selected) {
                            selectedPattern.add(4);
                        }

                        if (isPointerUpDot5Selected) {
                            selectedPattern.add(5);
                        }

                        if (isPointerUpDot6Selected) {
                            selectedPattern.add(6);
                        }
                    } //end for loop

                    if (!isInsideBrailleDot && !Common.touchInsideOpsBars) {
                        if (rotateGestures) {
                            specialMovePointer2X2 = event.getY(1);
                            specialMovePointer2Y2 = event.getX(1);
                        } else {
                            specialMovePointer2X2 = event.getX(1);
                            specialMovePointer2Y2 = event.getY(1);
                        }
                    } else {
                        specialMovePointer2X2 = 0;
                        specialMovePointer2Y2 = 0;
                    }

                    //Send patterns when all fingers up
                    sendPatterns();

                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_HOVER_EXIT:
                    isMainFingerUp = true;
                    isInsideBrailleDot = false;

                    //Special Character moves in free area
                    if (rotateGestures) {
                        specialMoveX2 = event.getY();
                        specialMoveY2 = event.getX();
                    } else {
                        specialMoveX2 = event.getX();
                        specialMoveY2 = event.getY();
                    }

                    isSpecialMove = specialMoves();
                    //Don't get the character if the first touch was inside free area
                    if (specialMoveX1 != 0) {
                        selectedPattern.clear();
                    } else {
                        //-------------------------------------//
                        //Get the dot when the user up his finger
                        //Don't remove this, if the user selected stop over dots, I need to get the action up
                        boolean isUpDot1Selected = (event.getX() - (brailleDot1.getX() + brailleDotRadius)) * (event.getX() - (brailleDot1.getX() + brailleDotRadius)) + (event.getY() - (brailleDot1.getY() + brailleDotRadius)) * (event.getY() - (brailleDot1.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                        boolean isUpDot2Selected = (event.getX() - (brailleDot2.getX() + brailleDotRadius)) * (event.getX() - (brailleDot2.getX() + brailleDotRadius)) + (event.getY() - (brailleDot2.getY() + brailleDotRadius)) * (event.getY() - (brailleDot2.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                        boolean isUpDot3Selected = (event.getX() - (brailleDot3.getX() + brailleDotRadius)) * (event.getX() - (brailleDot3.getX() + brailleDotRadius)) + (event.getY() - (brailleDot3.getY() + brailleDotRadius)) * (event.getY() - (brailleDot3.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                        boolean isUpDot4Selected = (event.getX() - (brailleDot4.getX() + brailleDotRadius)) * (event.getX() - (brailleDot4.getX() + brailleDotRadius)) + (event.getY() - (brailleDot4.getY() + brailleDotRadius)) * (event.getY() - (brailleDot4.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                        boolean isUpDot5Selected = (event.getX() - (brailleDot5.getX() + brailleDotRadius)) * (event.getX() - (brailleDot5.getX() + brailleDotRadius)) + (event.getY() - (brailleDot5.getY() + brailleDotRadius)) * (event.getY() - (brailleDot5.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;
                        boolean isUpDot6Selected = (event.getX() - (brailleDot6.getX() + brailleDotRadius)) * (event.getX() - (brailleDot6.getX() + brailleDotRadius)) + (event.getY() - (brailleDot6.getY() + brailleDotRadius)) * (event.getY() - (brailleDot6.getY() + brailleDotRadius)) <= brailleDotRadiusDoubled;

                        if (isUpDot1Selected) {
                            selectedPattern.add(1);
                            brailleDot1.fillTheDot();
                            if (vibrationOnDotTap) {
                                vibrator.vibrate(100);
                            }

                            //Braille Dot sound
                            if (brailleDotSound != 0 && playBrailleDot1Sound) {
                                playBrailleDot1Sound = false;
                                Common.playBrailleDotSound(brailleDotSound, "one");
                            }
                        }

                        if (isUpDot2Selected) {
                            selectedPattern.add(2);
                            brailleDot2.fillTheDot();
                            if (vibrationOnDotTap) {
                                vibrator.vibrate(100);
                            }

                            //Braille Dot sound
                            if (brailleDotSound != 0 && playBrailleDot2Sound) {
                                playBrailleDot2Sound = false;
                                Common.playBrailleDotSound(brailleDotSound, "two");
                            }
                        }

                        if (isUpDot3Selected) {
                            selectedPattern.add(3);
                            brailleDot3.fillTheDot();
                            if (vibrationOnDotTap) {
                                vibrator.vibrate(100);
                            }

                            //Braille Dot sound
                            if (brailleDotSound != 0 && playBrailleDot3Sound) {
                                playBrailleDot3Sound = false;
                                Common.playBrailleDotSound(brailleDotSound, "three");
                            }
                        }

                        if (isUpDot4Selected) {
                            selectedPattern.add(4);
                            brailleDot4.fillTheDot();
                            if (vibrationOnDotTap) {
                                vibrator.vibrate(100);
                            }

                            //Braille Dot sound
                            if (brailleDotSound != 0 && playBrailleDot4Sound) {
                                playBrailleDot4Sound = false;
                                Common.playBrailleDotSound(brailleDotSound, "four");
                            }
                        }

                        if (isUpDot5Selected) {
                            selectedPattern.add(5);
                            brailleDot5.fillTheDot();
                            if (vibrationOnDotTap) {
                                vibrator.vibrate(100);
                            }

                            //Braille Dot sound
                            if (brailleDotSound != 0 && playBrailleDot5Sound) {
                                playBrailleDot5Sound = false;
                                Common.playBrailleDotSound(brailleDotSound, "five");
                            }
                        }

                        if (isUpDot6Selected) {
                            selectedPattern.add(6);
                            brailleDot6.fillTheDot();
                            if (vibrationOnDotTap) {
                                vibrator.vibrate(100);
                            }

                            //Braille Dot sound
                            if (brailleDotSound != 0 && playBrailleDot6Sound) {
                                playBrailleDot6Sound = false;
                                Common.playBrailleDotSound(brailleDotSound, "six");
                            }
                        }

                        playBrailleDot1Sound = true;
                        playBrailleDot2Sound = true;
                        playBrailleDot3Sound = true;
                        playBrailleDot4Sound = true;
                        playBrailleDot5Sound = true;
                        playBrailleDot6Sound = true;

                        brailleDot1.clearFillTheDot();
                        brailleDot2.clearFillTheDot();
                        brailleDot3.clearFillTheDot();
                        brailleDot4.clearFillTheDot();
                        brailleDot5.clearFillTheDot();
                        brailleDot6.clearFillTheDot();
                    }

                    //Send patterns when all fingers up
                    sendPatterns();

                    //Clear special moves
                    specialMoveX1 = 0;
                    specialMoveX2 = 0;
                    specialMoveY1 = 0;
                    specialMoveY2 = 0;
                    specialMovePointer2X1 = 0;
                    specialMovePointer2Y1 = 0;
                    specialMovePointer2X2 = 0;
                    specialMovePointer2Y2 = 0;
                    Common.touchInsideOpsBars = false;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //--------------------------------------------------------------------------------------------//
    //Declare all special moves
    private boolean specialMoves() {
        try {
            if (!isGesturesEnabled || Common.touchInsideOpsBars) {
                return false;
            }

            float deltaX = specialMoveX2 - specialMoveX1;
            float deltaY = specialMoveY2 - specialMoveY1;
            float deltaPointer2X = specialMovePointer2X2 - specialMovePointer2X1;
            float deltaPointer2Y = specialMovePointer2Y2 - specialMovePointer2Y1;
            boolean isDeltaXGesture = Math.abs(deltaX) > Common.MIN_DIST_GESTURE;
            boolean isDeltaYGesture = Math.abs(deltaY) > Common.MIN_DIST_GESTURE;
            boolean isDeltaPointer2XGesture = Math.abs(deltaPointer2X) > Common.MIN_DIST_GESTURE;
            boolean isDeltaPointer2YGesture = Math.abs(deltaPointer2Y) > Common.MIN_DIST_GESTURE;

            if (specialMoveX1 != 0) {
                //Two Pointers
                //------------
                //Long Y
                if (specialMovePointer2Y1 != specialMovePointer2Y2 && isDeltaPointer2YGesture && (Math.abs(deltaPointer2Y) >= Common.MIN_DIST_SPECIAL_LONG_MOVE || Math.abs(deltaPointer2Y) < Common.MIN_DIST_SPECIAL_LONG_MOVE)) {
                    Common.changeKeyboardLanguage(this, brailleIME.getCurrentInputConnection());
                }
                //Short X
                else if (specialMovePointer2X1 != specialMovePointer2X2 && isDeltaPointer2XGesture && Math.abs(deltaPointer2X) >= Common.MIN_DIST_SPECIAL_SHORT_MOVE) {
                    //Left to Right
                    if (specialMovePointer2X2 > specialMovePointer2X1) {
                        if (Common.isRTL) {
                            Common.removeLastWordFromText(brailleIME.getCurrentInputConnection());
                        } else {
                            Common.removeFullText(brailleIME.getCurrentInputConnection());
                        }
                    }
                    //Right to Left
                    else {
                        if (Common.isRTL) {
                            Common.removeFullText(brailleIME.getCurrentInputConnection());
                        } else {
                            Common.removeLastWordFromText(brailleIME.getCurrentInputConnection());
                        }
                    }
                }

                //One Pointer
                //-----------
                //Long Y Moves
                else if (specialMovePointer2Y1 == 0 && isDeltaYGesture && Math.abs(deltaY) >= Common.MIN_DIST_SPECIAL_LONG_MOVE) {
                    //Up to Bottom
                    boolean changeKeyboardToNext = rotateGestures ? specialMoveY2 < specialMoveY1 : specialMoveY2 > specialMoveY1;
                    if (changeKeyboardToNext) {
                        Common.changeToNextKeyboardType(this);
                    }
                    //Bottom to Up
                    else {
                        Common.changeToPreviousKeyboardType(this);
                    }
                }
                //Short Y Moves
                else if (specialMovePointer2Y1 == 0 && isDeltaYGesture && Math.abs(deltaY) >= Common.MIN_DIST_SPECIAL_SHORT_MOVE) {
                    //Up to Bottom
                    boolean makeNewLine = rotateGestures ? specialMoveY2 < specialMoveY1 : specialMoveY2 > specialMoveY1;
                    if (makeNewLine) {
                        Common.makeNewLine(brailleIME);
                    }
                }
                //Short X Moves
                else if (specialMovePointer2X1 == 0 && isDeltaXGesture && Math.abs(deltaX) >= Common.MIN_DIST_SPECIAL_SHORT_MOVE) {
                    //Left to right
                    if (specialMoveX2 > specialMoveX1) {
                        if (Common.isRTL) {
                            Common.removeLastCharFromText(brailleIME.getCurrentInputConnection());
                        } else {
                            Common.typeSpaceChar(brailleIME.getCurrentInputConnection());
                        }
                    }
                    //Right to Left
                    else {
                        if (Common.isRTL) {
                            Common.typeSpaceChar(brailleIME.getCurrentInputConnection());
                        } else {
                            Common.removeLastCharFromText(brailleIME.getCurrentInputConnection());
                        }
                    }
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //--------------------------------------------------------------------------------------------//
    //Because of touch to explore feature of TalkBack that converts single touch to hover event
    private long clickTime = 0;
    private long lastClickTime = 0;
    private boolean firstTap = false;
    private float xPosition;
    private float yPosition;

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        try {
            if (Common.isTouchToExploreEnabled()) {
                if (event.getActionMasked() == MotionEvent.ACTION_HOVER_ENTER) {
                    clickTime = System.currentTimeMillis();
                }
                //This is double tap
                if (event.getActionMasked() == MotionEvent.ACTION_HOVER_ENTER && Math.abs(xPosition - event.getX()) < 20 && Math.abs(yPosition - event.getY()) < 20 && firstTap && clickTime - lastClickTime < 1000) {
                    lastClickTime = 0;
                    firstTap = false;
                    gestureDoubleTap.onDoubleTap(event);
                    return false;
                } //This is long single tap
                else if (event.getActionMasked() == MotionEvent.ACTION_HOVER_EXIT) {
                    firstTap = true;
                    lastClickTime = clickTime;
                    xPosition = event.getX();
                    yPosition = event.getY();
                }
                return onTouchEvent(event);
            } else {
                return super.onHoverEvent(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return super.onHoverEvent(event);
        }
    }

    //--------------------------------------------------------------------------------------------//
    //Send patterns when all fingers up
    private void sendPatterns() {
        if (isMainFingerUp && isPointersFingersUp) {
            //Clear the last up
            selectedPatternHandler.removeCallbacks(selectedPatternRunnable);
            //Send the selected pattern
            selectedPatternHandler.postDelayed(selectedPatternRunnable, selectedDotsPeriod);
        }
    }

    //--------------------------------------------------------------------------------------------//
    //To handle double tap and hide the keyboard
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    //--------------------------------------------------------------------------------------------//
    //To handle double tap and hide the keyboard
    boolean isDoubleTap = true;
    public class GestureDoubleTap extends GestureDetector.SimpleOnGestureListener {

        //Because of fast switch between keyboards, sometimes it hide the keyboard
        @Override
        public boolean onDown(MotionEvent e) {
            isDoubleTap = true;
            return super.onDown(e);
        }

        //Because of fast switch between keyboards, sometimes it hide the keyboard
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            isDoubleTap = false;
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            try {
                //Check for special moves in free area
                boolean doubleTapInsideBrailleDot = (event.getX() - (brailleDot1.getX() + brailleDotRadius * 1.5)) * (event.getX() - (brailleDot1.getX() + brailleDotRadius * 1.5)) + (event.getY() - (brailleDot1.getY() + brailleDotRadius * 1.5)) * (event.getY() - (brailleDot1.getY() + brailleDotRadius * 1.5)) <= brailleDotRadiusDoubled * 1.5 ||
                        (event.getX() - (brailleDot2.getX() + brailleDotRadius * 1.5)) * (event.getX() - (brailleDot2.getX() + brailleDotRadius * 1.5)) + (event.getY() - (brailleDot2.getY() + brailleDotRadius * 1.5)) * (event.getY() - (brailleDot2.getY() + brailleDotRadius * 1.5)) <= brailleDotRadiusDoubled * 1.5 ||
                        (event.getX() - (brailleDot3.getX() + brailleDotRadius * 1.5)) * (event.getX() - (brailleDot3.getX() + brailleDotRadius * 1.5)) + (event.getY() - (brailleDot3.getY() + brailleDotRadius * 1.5)) * (event.getY() - (brailleDot3.getY() + brailleDotRadius * 1.5)) <= brailleDotRadiusDoubled * 1.5 ||
                        (event.getX() - (brailleDot4.getX() + brailleDotRadius * 1.5)) * (event.getX() - (brailleDot4.getX() + brailleDotRadius * 1.5)) + (event.getY() - (brailleDot4.getY() + brailleDotRadius * 1.5)) * (event.getY() - (brailleDot4.getY() + brailleDotRadius * 1.5)) <= brailleDotRadiusDoubled * 1.5 ||
                        (event.getX() - (brailleDot5.getX() + brailleDotRadius * 1.5)) * (event.getX() - (brailleDot5.getX() + brailleDotRadius * 1.5)) + (event.getY() - (brailleDot5.getY() + brailleDotRadius * 1.5)) * (event.getY() - (brailleDot5.getY() + brailleDotRadius * 1.5)) <= brailleDotRadiusDoubled * 1.5 ||
                        (event.getX() - (brailleDot6.getX() + brailleDotRadius * 1.5)) * (event.getX() - (brailleDot6.getX() + brailleDotRadius * 1.5)) + (event.getY() - (brailleDot6.getY() + brailleDotRadius * 1.5)) * (event.getY() - (brailleDot6.getY() + brailleDotRadius * 1.5)) <= brailleDotRadiusDoubled * 1.5;

                if (isDoubleTap && !doubleTapInsideBrailleDot && !Common.touchInsideOpsBars) {
                    brailleIME.requestHideSelf(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }
}
