package com.mbanna.swiftbraille;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.widget.Spinner;
import android.widget.Toast;

import com.mbanna.swiftbraille.patterns.ar.ArabicCharPattern;
import com.mbanna.swiftbraille.patterns.ar.ArabicNumbersPattern;
import com.mbanna.swiftbraille.patterns.en.EnglishNumbersPattern;
import com.mbanna.swiftbraille.patterns.en.EnglishSmallCharPattern;
import com.mbanna.swiftbraille.patterns.es.SpanishNumbersPattern;
import com.mbanna.swiftbraille.patterns.es.SpanishSmallCharPattern;
import com.mbanna.swiftbraille.patterns.fr.FrenchNumbersPattern;
import com.mbanna.swiftbraille.patterns.fr.FrenchSmallCharPattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

//Class contains most common functions
public class Common extends Application {

    //Common variables
    public static Class previousActivity = MainActivity.class; //For parent activities
    public static boolean isRTL = false;
    private static MediaPlayer soundOfPronounce;
    private static Context context;
    public static int BRAILLE_CELL_FREE_AREA = 50;
    public static int MIN_DIST_GESTURE = 50;
    public static int MIN_DIST_SPECIAL_SHORT_MOVE = 50;
    public static int MIN_DIST_SPECIAL_LONG_MOVE = 50;
    public static int MIN_DOT_RADIUS = 20;
    public static boolean isTablet = false;
    public static int opsBarsWidth;
    public static int opsBarsHeight;
    public static int brailleDotMargin;
    public static int brailleDotStrokeSize;
    static AlertDialog gotItAlertDialogBox;
    static AccessibilityManager accessibilityManager;
    public static boolean touchInsideOpsBars = false;
    public static DefaultTextSpeech defaultTextSpeech;

    //Dots layout constants
    public static final int BRAILLE_CELL_DOTS_LAYOUT = 0;
    public static final int PERKINS_DOTS_LAYOUT = 1;
    public static final int TWO_ROWS_DOTS_LAYOUT = 2;

    //Languages constants
    public static final int ENGLISH_LANGUAGE_INPUT_METHOD = 0;
    public static final int ARABIC_LANGUAGE_INPUT_METHOD = 1;
    public static final int SPANISH_LANGUAGE_INPUT_METHOD = 2;
    public static final int FRENCH_LANGUAGE_INPUT_METHOD = 3;

    //Default settings constants
    public static final int DEFAULT_PORTRAIT_KEYBOARD_HEIGHT = 100;
    public static final int DEFAULT_LANDSCAPE_KEYBOARD_HEIGHT = 100;
    public static final int DEFAULT_PORTRAIT_KEYBOARD_WIDTH = 100;
    public static final int DEFAULT_LANDSCAPE_KEYBOARD_WIDTH = 100;
    public static final boolean DEFAULT_KEYBOARD_RIGHT_POSITION = false;
    public static final boolean DEFAULT_FILL_DOT_ON_TOUCH = false;
    public static final boolean DEFAULT_VOICE_INPUT = true;
    public static final boolean DEFAULT_TALK_BACK_CHAR = true;
    public static final boolean DEFAULT_VIEW_DOT_NUMBER = true;
    public static final int DEFAULT_STOP_OVER_DOT_TIME = 0; //When I move over it
    public static final boolean DEFAULT_READ_TEXT_AFTER_CLOSE_KEYBOARD = false;
    public static final boolean DEFAULT_PLAY_ALWAYS_STORED_VOICES = false;
    public static final boolean DEFAULT_DOTS_VIBRATION = true;
    public static final boolean DEFAULT_CAPITAL_FIRST_CHAR_FROM_SENTENCE = false;
    public static final boolean DEFAULT_START_DOTS_FROM_RIGHT = false;
    public static final boolean DEFAULT_MAKE_DOT_2_5_HIGHER = false;
    public static final boolean DEFAULT_HIDE_KEYBOARD_IN_NEWLINE = false;
    public static final boolean DEFAULT_PREVENT_KEYBOARD_ROTATION = false;
    public static final boolean DEFAULT_SHOW_OPERATIONS_BTNS = true;
    public static final boolean DEFAULT_ACTIVATE_GESTURES = true;
    public static final int DEFAULT_DOTS_LAYOUT = 0; //0: Braille cell layout || 1: Perkins || 2: Two rows

    //Default input methods
    public static final int DEFAULT_NUM_KEYBOARD_LANGUAGE = ENGLISH_LANGUAGE_INPUT_METHOD; //For default settings
    public static final boolean DEFAULT_ARABIC_INPUT_METHOD = Locale.getDefault().getLanguage().equals(new Locale("ar").getLanguage());
    public static final boolean DEFAULT_ENGLISH_INPUT_METHOD = true;
    public static final boolean DEFAULT_SPANISH_INPUT_METHOD = Locale.getDefault().getLanguage().equals(new Locale("es").getLanguage());
    public static final boolean DEFAULT_FRENCH_INPUT_METHOD = Locale.getDefault().getLanguage().equals(new Locale("fr").getLanguage());
    public static final int DEFAULT_BRAILLE_DOTS_SOUND = 2;
    public static final int DEFAULT_GET_SELECTED_DOTS_NUMBER_PERIOD = 500; //In milliseconds
    public static Locale currentLocaleLanguage = new Locale("en"); // When the user changes the keyboard types or keyboard language
    public static String TAG = "SwiftBraille";
    public static int SDK_VERSION = Build.VERSION.SDK_INT;
    public static boolean areSettingsChanged = false;
    public static Toast toast;
    public static boolean speakHiddenKeyboard = true;
    public static SharedPreferences sharedPreferences;
    public static AudioManager audioManager;
    public static boolean isInputTextSingleLine = false; //If the user types new line, hide the keyboard
    public static boolean isNumericInputText = false; //If the user types new line, hide the keyboard

    //Common settings from storage
    //Advanced settings
    public static boolean preventKeyboardRotation = DEFAULT_PREVENT_KEYBOARD_ROTATION;
    public static boolean activateGestures = DEFAULT_ACTIVATE_GESTURES;
    public static boolean vibrationOnDotTap = DEFAULT_DOTS_VIBRATION;
    public static boolean capitalizeFirstWord = DEFAULT_CAPITAL_FIRST_CHAR_FROM_SENTENCE;
    public static boolean showBrailleDotsFromTheRight = DEFAULT_START_DOTS_FROM_RIGHT;
    public static boolean newLineHideKeyboard = DEFAULT_HIDE_KEYBOARD_IN_NEWLINE;
    public static boolean showOperationsButtons = DEFAULT_SHOW_OPERATIONS_BTNS;
    public static int stopOverDotTime = DEFAULT_STOP_OVER_DOT_TIME;
    public static int selectedDotsNumbersPeriod = DEFAULT_GET_SELECTED_DOTS_NUMBER_PERIOD;
    public static boolean makeDot2And5Higher = DEFAULT_MAKE_DOT_2_5_HIGHER;

    //Dots Layout
    public static int selectedDotsLayout = DEFAULT_DOTS_LAYOUT;

    //Dots Style
    public static int dotRadius = MIN_DOT_RADIUS;
    public static int dotLandscapeRadius = MIN_DOT_RADIUS;
    public static int dotFillColor = 0;
    public static int dotStrokeColor = 0;
    public static boolean fillDotOnTouch = DEFAULT_FILL_DOT_ON_TOUCH;
    public static boolean viewBrailleDotNumber = DEFAULT_VIEW_DOT_NUMBER;

    //Keyboard Dimension
    public static boolean startKeyboardContainerFromRight = true;
    public static int keyboardHeightPortrait = 100;
    public static int keyboardHeightLandscape = 100;
    public static int keyboardWidthPortrait = 100;
    public static int keyboardWidthLandscape = 100;

    //Keyboard Languages
    public static String currentSystemLanguage = Locale.getDefault().getLanguage();
    public static Locale currentSystemLocaleLanguage = new Locale(currentSystemLanguage);
    public static Locale englishLocale = new Locale("en");
    public static Locale arabicLocale = new Locale("ar");
    public static Locale spanishLocale = new Locale("es");
    public static Locale frenchLocale = new Locale("fr");
    public static boolean isArabicInputMethodActivated = DEFAULT_ARABIC_INPUT_METHOD;
    public static boolean isEnglishInputMethodActivated = DEFAULT_ENGLISH_INPUT_METHOD;
    public static boolean isSpanishInputMethodActivated = DEFAULT_SPANISH_INPUT_METHOD;
    public static boolean isFrenchInputMethodActivated = DEFAULT_FRENCH_INPUT_METHOD;
    public static int defaultKeyboardLanguage = ENGLISH_LANGUAGE_INPUT_METHOD;
    public static int currentLanguage = defaultKeyboardLanguage;

    //Sounds
    public static boolean activeVoiceInput = DEFAULT_VOICE_INPUT;
    public static boolean talkBackChar = DEFAULT_TALK_BACK_CHAR;
    public static int defaultBrailleDotSound = DEFAULT_BRAILLE_DOTS_SOUND;
    public static boolean playAlwaysStoredVoices = DEFAULT_PLAY_ALWAYS_STORED_VOICES;
    public static boolean readTextAfterCloseKeyboard = DEFAULT_READ_TEXT_AFTER_CLOSE_KEYBOARD;

    //--------------------------------------------------------------------------------//
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        isTablet = context.getResources().getBoolean(R.bool.is_tablet);
        accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        opsBarsWidth = Math.round(context.getResources().getDimension(R.dimen.operations_bar_width));
        opsBarsHeight = Math.round(context.getResources().getDimension(R.dimen.operations_bar_height));
        brailleDotMargin = Math.round(context.getResources().getDimension(R.dimen.braille_dot_margin));
        MIN_DOT_RADIUS = Math.round(context.getResources().getDimension(R.dimen.min_dot_radius));
        MIN_DIST_GESTURE = Math.round(context.getResources().getDimension(R.dimen.min_dist_gesture));
        MIN_DIST_SPECIAL_SHORT_MOVE = Math.round(context.getResources().getDimension(R.dimen.min_dist_special_short_move));
        MIN_DIST_SPECIAL_LONG_MOVE = Math.round(context.getResources().getDimension(R.dimen.min_dis_special_long_move));
        brailleDotStrokeSize = Math.round(context.getResources().getDimension(R.dimen.braille_dot_stroke));
        BRAILLE_CELL_FREE_AREA = Math.round(context.getResources().getDimension(R.dimen.braille_cell_free_area));

        //Get most needed settings
        refreshSettings(true);

        //Force check and reset settings for older versions of SwiftBraille
        boolean isForceSettingsHasReset = getSettingBoolean("isForceSettingsHasReset", false);
        if (!isForceSettingsHasReset) {
            forceResetSettings();
        }

        //Text to speech object for all the app
        defaultTextSpeech = new DefaultTextSpeech(context);
    }

    //--------------------------------------------------------------------------------------------//
    //If the user changes the TTS engine
    public static void reInitDefaultTextSpeech() {
        defaultTextSpeech.destroy();
        defaultTextSpeech = new DefaultTextSpeech(context);
    }

    //--------------------------------------------------------------------------------------------//
    //To calculate the free space and make special moves (Gestures)
    public static int[] getScreenDimension() {
        try {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            return new int[]{width, height};
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new int[]{0, 0};
    }

    //--------------------------------------------------------------------------------//
    //To calculate the default braille dot based on screen width
    //theMode = 0=>Portrait or 1=>Landscape
    public static int getDefaultDotRadius(int theMode) {
        try {
            int theWidth, theHeight, selectedKeyboardHeight, selectedKeyboardWidth, theFinalValue = 0;
            int[] getScreenDimensionResults = getScreenDimension();
            boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
            boolean isLandscape = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

            keyboardHeightPortrait = getSettingInt("defaultKeyboardHeight", DEFAULT_PORTRAIT_KEYBOARD_HEIGHT);
            keyboardHeightLandscape = getSettingInt("defaultKeyboardLandscapeHeight", DEFAULT_LANDSCAPE_KEYBOARD_HEIGHT);
            keyboardWidthPortrait = getSettingInt("defaultKeyboardWidth", DEFAULT_PORTRAIT_KEYBOARD_WIDTH);
            keyboardWidthLandscape = getSettingInt("defaultKeyboardLandscapeWidth", DEFAULT_LANDSCAPE_KEYBOARD_WIDTH);
            selectedDotsLayout = getSettingInt("selectedDotsLayout", DEFAULT_DOTS_LAYOUT);
            makeDot2And5Higher = getSettingBoolean("makeDot2And5Higher", DEFAULT_MAKE_DOT_2_5_HIGHER);
            boolean isOpsBarsActivated = getSettingBoolean("showOperationsButtons", DEFAULT_SHOW_OPERATIONS_BTNS);

            //I need to get the screen sizes in portrait and landscape, even when the user changes the settings in portrait or landscape
            if (theMode == 0 && isPortrait) {
                theWidth = getScreenDimensionResults[0];
                theHeight = getScreenDimensionResults[1];
                selectedKeyboardHeight = keyboardHeightPortrait;
                selectedKeyboardWidth = keyboardWidthPortrait;
            } else if (theMode == 0 && isLandscape) {
                theWidth = getScreenDimensionResults[1];
                theHeight = getScreenDimensionResults[0];
                selectedKeyboardHeight = keyboardHeightPortrait;
                selectedKeyboardWidth = keyboardWidthPortrait;
            } else if (theMode == 1 && isPortrait) {
                theWidth = getScreenDimensionResults[1];
                theHeight = getScreenDimensionResults[0];
                selectedKeyboardHeight = keyboardHeightLandscape;
                selectedKeyboardWidth = keyboardWidthLandscape;
            } else if (theMode == 1 && isLandscape) {
                theWidth = getScreenDimensionResults[0];
                theHeight = getScreenDimensionResults[1];
                selectedKeyboardHeight = keyboardHeightLandscape;
                selectedKeyboardWidth = keyboardWidthLandscape;
            } else {
                theWidth = getScreenDimensionResults[0];
                theHeight = getScreenDimensionResults[1];
                selectedKeyboardHeight = keyboardHeightPortrait;
                selectedKeyboardWidth = keyboardWidthPortrait;
            }

            //If perkins layout, divide by 6 because all dots in one line
            if (selectedDotsLayout == PERKINS_DOTS_LAYOUT) {
                int divideFactor;
                if(makeDot2And5Higher && selectedKeyboardHeight == 50){
                    divideFactor = 8;
                } else if(makeDot2And5Higher && selectedKeyboardHeight == 70){
                    divideFactor = 7;
                } else if(makeDot2And5Higher){
                    divideFactor = 5;
                } else {
                    divideFactor = 6;
                }

                if(isTablet){
                    theFinalValue = ((((theWidth) / divideFactor) / 2) - (brailleDotStrokeSize * 2) - (brailleDotMargin/2) - (brailleDotMargin/2) );
                } else{
                    if (theWidth > theHeight) {
                        theFinalValue = ((((theWidth) / 6) / 2) - (brailleDotStrokeSize * 2) - (brailleDotMargin/2) - (brailleDotMargin/2));
                    } else {
                        theHeight = (int) (theHeight * ((float) selectedKeyboardHeight / 100));
                        theFinalValue = ((((theHeight) / 6) / 2) - (brailleDotStrokeSize * 2) - (brailleDotMargin/2) - (brailleDotMargin/2));
                    }
                }

                if(makeDot2And5Higher && isOpsBarsActivated && theMode == 1 && selectedKeyboardHeight != 100){
                    theFinalValue = theFinalValue - (opsBarsHeight / 6);
                }
            } else if(selectedDotsLayout == BRAILLE_CELL_DOTS_LAYOUT) {
                theWidth = (int) (theWidth * ((float) selectedKeyboardWidth / 100));
                theHeight = (int) (theHeight * ((float) selectedKeyboardHeight / 100));

                //I need the dots margin to be considered if the keyboard height is full
                if(selectedKeyboardHeight == 100){
                    theFinalValue = (((theHeight) / 3) / 2) - (brailleDotStrokeSize * 2) - (brailleDotMargin/2) - (brailleDotMargin/3);
                } else{
                    //Don't take the margin in the consideration if the keyboard height is not %100
                    theFinalValue = (((theHeight) / 3) / 2) - (brailleDotStrokeSize * 2) - (brailleDotMargin/2);
                }

                //If the ops bars opened, /3 (three dots vertically)
                if(isOpsBarsActivated){
                    theFinalValue = theFinalValue - (opsBarsHeight / 3);
                }

                //Check if the width is too short and the dots are too close together
                if(( (theFinalValue * 4) + (brailleDotMargin/2) + (brailleDotMargin/3) + (brailleDotStrokeSize * 2) ) >= theWidth - BRAILLE_CELL_FREE_AREA){
                    theFinalValue = (int) (theFinalValue - theFinalValue * 0.3);
                }
            } else if(selectedDotsLayout == TWO_ROWS_DOTS_LAYOUT){
                theWidth = (int) (theWidth * ((float) selectedKeyboardWidth / 100));
                theHeight = (int) (theHeight * ((float) selectedKeyboardHeight / 100));

                //Take the less side and use it
                if(theWidth < theHeight){
                    theFinalValue = (((theWidth) / 3) / 2) - (brailleDotStrokeSize * 2) - (brailleDotMargin/2) - (brailleDotMargin/2);
                } else{
                    theFinalValue = (((theHeight) / 3) / 2) - (brailleDotStrokeSize * 2) - (brailleDotMargin/2) - (brailleDotMargin/2);
                }

                //Don't take the ops bars heights in tables in consideration
                if(isOpsBarsActivated && !isTablet && (theMode == 1 && selectedKeyboardHeight <= 70)){
                    theFinalValue = theFinalValue - (opsBarsHeight / 3);
                }
            }

            return theFinalValue >= MIN_DOT_RADIUS ? theFinalValue : MIN_DOT_RADIUS;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //--------------------------------------------------------------------------------//
    //To remove the last char and speech it
    public static void removeLastCharFromText(InputConnection inputConnection) {
        try {
            String deletedChar = String.valueOf(inputConnection.getTextBeforeCursor(1, 0));
            if (deletedChar != null && deletedChar.length() > 0) {
                switch (deletedChar) {
                    case " ":
                        deletedChar = context.getString(R.string.space);
                        break;
                    case "\n":
                        deletedChar = context.getString(R.string.new_line);
                        break;
                    case "×":
                        deletedChar = context.getString(R.string.multiply_pattern);
                        break;
                    case "*":
                        deletedChar = context.getString(R.string.asterisk_pattern);
                        break;
                    case ",":
                    case "،":
                        deletedChar = context.getString(R.string.comma_pattern);
                        break;
                }

                //Check if really the character removed
                boolean isCharRemoved = inputConnection.deleteSurroundingText(1, 0);
                if (isCharRemoved) {
                    if ((playAlwaysStoredVoices && currentSystemLanguage.equals("ar") && getSoundPath("ar_message_backspace") != -1) || (!defaultTextSpeech.isArabicSupported() && currentSystemLanguage.equals("ar") && getSoundPath("ar_message_backspace") != -1)) {
                        runPatternSoundPronounce(getSoundPath("ar_message_backspace"), true);
                    } else {
                        defaultTextSpeech.speechText(context.getString(R.string.back_space, deletedChar));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------//
    //To type space character
    public static void typeSpaceChar(InputConnection inputConnection) {
        try {
            boolean textCommitted = inputConnection.commitText(" ", 1);
            if (talkBackChar) {
                String theLastWord = getTheLastWord(inputConnection);
                if (textCommitted && theLastWord != null) {
                    defaultTextSpeech.speechText(theLastWord);
                } else if (textCommitted) {
                    if ((playAlwaysStoredVoices && currentSystemLanguage.equals("ar") && getSoundPath("ar_message_space") != -1) || (!defaultTextSpeech.isArabicSupported() && currentSystemLanguage.equals("ar") && getSoundPath("ar_message_space") != -1)) {
                        runPatternSoundPronounce(getSoundPath("ar_message_space"), true);
                    } else {
                        defaultTextSpeech.speechText(context.getString(R.string.space));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------//
    public static void copyText(InputConnection inputConnection) {
        try {
            ClipData myClip = ClipData.newPlainText("SwiftBraille Copied", getAllInputText(inputConnection));
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(myClip);
            defaultTextSpeech.speechText(context.getString(R.string.text_copied));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------//
    public static void pasteText(InputConnection inputConnection) {
        try {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData abc = clipboardManager.getPrimaryClip();
            ClipData.Item myItem = abc.getItemAt(0);
            if (myItem != null) {
                String text = myItem.getText().toString();
                inputConnection.commitText(text, 1);
                defaultTextSpeech.speechText(context.getString(R.string.text_pasted));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------//
    public static String getAllInputText(InputConnection inputConnection) {
        String allText = "";
        try {
            ExtractedText extractedText = inputConnection.getExtractedText(new ExtractedTextRequest(), 0);
            if (extractedText != null) {
                allText = extractedText.text.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allText;
    }

    //--------------------------------------------------------------------------------//
    //Type the next letter in capital, if it's the first in the sentence, after questions mark, full stop, excalamation mark
    public static boolean isFirstSentenceIndicate(InputConnection inputConnection) {
        try {
            CharSequence lastTwoChars = inputConnection.getTextBeforeCursor(2, 0);
            if (lastTwoChars != null) {
                String twoChars = lastTwoChars.toString();
                return twoChars.length() == 0 || twoChars.trim().equals("?") || twoChars.trim().equals(".") || twoChars.trim().equals("!");
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //--------------------------------------------------------------------------------//
    @SuppressWarnings("all")
    public static int getMyColor(int neededColor) {
        if (SDK_VERSION >= 23) {
            return ContextCompat.getColor(context, neededColor);
        } else {
            return context.getResources().getColor(neededColor);
        }
    }

    //--------------------------------------------------------------------------------//
    //Show text as string
    public static boolean makeToast(String showText, int showLong) {
        if (toast == null) {
            toast = Toast.makeText(context, showText, showLong);
        } else {
            toast.cancel();
            toast = Toast.makeText(context, showText, showLong);
            toast.setText(showText);
            toast.setDuration(showLong);
        }

        //Check if the toast is still visible to show or not
        if (!toast.getView().isShown()) {
            toast.show();
            return true;
        }
        return false;
    }

    //--------------------------------------------------------------------------------//
    //To get the last written word when the user types a space
    @Nullable
    public static String getTheLastWord(InputConnection inputConnection) {
        try {
            String lastWord = null;
            String[] allWords = getAllInputText(inputConnection).replace("\n", " ").split(" ");
            if (allWords.length > 0 && allWords[allWords.length - 1].length() > 1) {
                lastWord = allWords[allWords.length - 1];
            }
            return lastWord;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //--------------------------------------------------------------------------------//
    //If the language not supported in TTS engine, try to check and run pre-stored sound file
    public static void runPatternSoundPronounce(int resources, boolean interruption) {
        try {
            if (soundOfPronounce != null && interruption) {
                soundOfPronounce.stop();
                soundOfPronounce.reset();
                soundOfPronounce.release();
                soundOfPronounce = null;
            }

            soundOfPronounce = new MediaPlayer();
            soundOfPronounce.setDataSource(context, Uri.parse("android.resource://com.mbanna.swiftbraille/" + resources));
            soundOfPronounce.prepare();
            soundOfPronounce.setVolume(1.0f, 1.0f);
            soundOfPronounce.start();

            //Free the memory
            soundOfPronounce.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    soundOfPronounce = null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------//
    //Get the path of stored sounds in RAW folder
    public static int getSoundPath(String fileName) {
        int thePath = context.getResources().getIdentifier(fileName, "raw", context.getPackageName());
        if (thePath == 0) {
            return -1;
        } else {
            return thePath;
        }
    }

    //--------------------------------------------------------------------------------------------//
    public static int getSettingInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    //--------------------------------------------------------------------------------------------//
    public static boolean getSettingBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    //--------------------------------------------------------------------------------------------//
    public static void putSettingInt(String key, int defValue) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, defValue);
            editor.commit(); //Don't replace with apply, I need that to be done immediately
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public static void putSettingString(String key, String defValue) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, defValue);
            editor.commit(); //Don't replace with apply, I need that to be done immediately
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public static String getSettingString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    //--------------------------------------------------------------------------------------------//
    public static void putSettingBoolean(String key, boolean defValue) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, defValue);
            editor.commit(); //Don't replace with apply, I need that to be done immediately
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public static void playBrailleDotSound(int soundSettings, String brailleDotNumber) {
        try {
            if (soundSettings == 1) {
                runPatternSoundPronounce(context.getResources().getIdentifier("tick_sound", "raw", context.getPackageName()), false);
            } else if (soundSettings == 2) {
                runPatternSoundPronounce(context.getResources().getIdentifier(brailleDotNumber + "_dot_music", "raw", context.getPackageName()), false);
            } else if (soundSettings == 3) {
                //If the current system language is Arabic
                if (currentSystemLanguage.equals("ar")) {
                    runPatternSoundPronounce(context.getResources().getIdentifier("ar_num_" + brailleDotNumber, "raw", context.getPackageName()), false);
                } else {
                    defaultTextSpeech.speechText(brailleDotNumber, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------//
    //Remove full text from text edit via the input connection
    public static void removeFullText(InputConnection inputConnection) {
        try {
            String fullTextToDelete = getAllInputText(inputConnection);
            int fullTextLength = fullTextToDelete.length();
            if (fullTextLength == 0) {
                fullTextLength = 1000000;
            }
            boolean textCommitted = inputConnection.deleteSurroundingText(fullTextLength, fullTextLength);
            if (textCommitted) {
                defaultTextSpeech.speechText(context.getString(R.string.full_text_deletion), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------//
    //To change the current keyboard language, using gestures or top buttons
    public static void changeKeyboardLanguage(KeyboardSurface keyboardSurface, InputConnection inputConnection) {
        try {
            ArrayList<Integer> activatedLanguages = new ArrayList<>();

            //Add activated languages to the list
            if(isEnglishInputMethodActivated){
                activatedLanguages.add(ENGLISH_LANGUAGE_INPUT_METHOD);
            }

            if(isArabicInputMethodActivated){
                activatedLanguages.add(ARABIC_LANGUAGE_INPUT_METHOD);
            }

            if(isSpanishInputMethodActivated){
                activatedLanguages.add(SPANISH_LANGUAGE_INPUT_METHOD);
            }

            if(isFrenchInputMethodActivated){
                activatedLanguages.add(FRENCH_LANGUAGE_INPUT_METHOD);
            }

            //Check the next language
            int nextLang;
            Iterator activatedLanguagesIterator;
            if(activatedLanguages.indexOf(currentLanguage) < activatedLanguages.size() - 1){
                activatedLanguagesIterator = activatedLanguages.listIterator(activatedLanguages.indexOf(currentLanguage));
                activatedLanguagesIterator.next(); //For initialization to first position purpose
                //I don't need the current iterator, move to the next one
                nextLang = (int)activatedLanguagesIterator.next();
            } else{
                activatedLanguagesIterator = activatedLanguages.iterator();
                //I need the current iterator
                nextLang = (int)activatedLanguagesIterator.next();
            }

            //Change to the next language
            if(nextLang == ENGLISH_LANGUAGE_INPUT_METHOD){
                keyboardSurface.patternSurface = new EnglishSmallCharPattern(context);
            } else if(nextLang == ARABIC_LANGUAGE_INPUT_METHOD){
                keyboardSurface.patternSurface = new ArabicCharPattern(context);
            } else if(nextLang == SPANISH_LANGUAGE_INPUT_METHOD){
                keyboardSurface.patternSurface = new SpanishSmallCharPattern(context, inputConnection);
            } else if(nextLang == FRENCH_LANGUAGE_INPUT_METHOD){
                keyboardSurface.patternSurface = new FrenchSmallCharPattern(context);
            }

            //Speech current keyboard language
            speechCurrentKeyboardLanguage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------//
    //Change keyboard types to the next pattern
    public static void changeToNextKeyboardType(KeyboardSurface keyboardSurface) {
        try {
            //Load next pattern
            keyboardSurface.patternSurface = keyboardSurface.patternSurface.nextPattern(context);
            //Speech the class title
            keyboardSurface.patternSurface.speechClassTitle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------//
    //Change keyboard types to the previous pattern
    public static void changeToPreviousKeyboardType(KeyboardSurface keyboardSurface) {
        try {
            //Load next pattern
            keyboardSurface.patternSurface = keyboardSurface.patternSurface.previousPattern(context);
            //Speech the class title
            keyboardSurface.patternSurface.speechClassTitle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------//
    public static void makeNewLine(BrailleIME brailleIME) {
        try {
            //Check if the new line to hide the keyboard in single line
            if (newLineHideKeyboard && isInputTextSingleLine) {
                brailleIME.requestHideSelf(0);
                return;
            }

            boolean textCommitted = brailleIME.getCurrentInputConnection().commitText("\n", 1);
            if (textCommitted) {
                if ((playAlwaysStoredVoices && currentSystemLanguage.equals("ar") && getSoundPath("ar_message_newline") != -1) || (!defaultTextSpeech.isArabicSupported() && currentSystemLanguage.equals("ar") && getSoundPath("ar_message_newline") != -1)) {
                    runPatternSoundPronounce(getSoundPath("ar_message_newline"), true);
                } else {
                    defaultTextSpeech.speechText(context.getString(R.string.new_line));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    public static int getIndexFromSpinnerByValue(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    //----------------------------------------------------------------------------------------------//
    //Remove a word from text edit via the input connection
    public static void removeLastWordFromText(InputConnection inputConnection) {
        try {
            String fullWordToDelete = getTheLastWord(inputConnection);
            if (fullWordToDelete != null) {
                boolean textCommitted = inputConnection.deleteSurroundingText(fullWordToDelete.length() + 1, 0);
                if (textCommitted) {
                    defaultTextSpeech.speechText(context.getString(R.string.word_deletion, fullWordToDelete), true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //---------------------------------------------------------------------------------------------//
    //To speech current keyboard language
    public static void speechCurrentKeyboardLanguage() {
        if (currentLanguage == ARABIC_LANGUAGE_INPUT_METHOD) {
            if ((playAlwaysStoredVoices && currentSystemLanguage.equals("ar")) || (!defaultTextSpeech.isArabicSupported() && currentSystemLanguage.equals("ar"))) {
                runPatternSoundPronounce(getSoundPath("ar_message_ar_keyboard"), true);
            } else {
                defaultTextSpeech.speechText(context.getString(R.string.arabic_current_language), true);
            }
        } else if (currentLanguage == ENGLISH_LANGUAGE_INPUT_METHOD) {
            if ((playAlwaysStoredVoices && currentSystemLanguage.equals("ar")) || (!defaultTextSpeech.isArabicSupported() && currentSystemLanguage.equals("ar"))) {
                runPatternSoundPronounce(getSoundPath("ar_message_en_keyboard"), true);
            } else {
                defaultTextSpeech.speechText(context.getString(R.string.english_current_language), true);
            }
        } else if (currentLanguage == SPANISH_LANGUAGE_INPUT_METHOD) {
            defaultTextSpeech.speechText(context.getString(R.string.spanish_current_language), true);
        } else if (currentLanguage == FRENCH_LANGUAGE_INPUT_METHOD) {
            defaultTextSpeech.speechText(context.getString(R.string.french_current_language), true);
        }
    }

    //--------------------------------------------------------------------------------------------//
    //Show dialog message when the users unchecked all languages
    public static void showGotItAlertDialog(Context context, String boxTitle, String boxMessage) {
        try {
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(context);
            myBuilder.setCancelable(true).setPositiveButton(context.getString(R.string.got_it), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gotItAlertDialogBox.hide();
                }
            });

            gotItAlertDialogBox = myBuilder.create();
            gotItAlertDialogBox.setTitle(boxTitle);
            gotItAlertDialogBox.setMessage(boxMessage);
            gotItAlertDialogBox.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //---------------------------------------------------------------------------------------------//
    public static boolean isTouchToExploreEnabled() {
        return accessibilityManager.isTouchExplorationEnabled();
    }

    //---------------------------------------------------------------------------------------------//
    //Get or Refresh most used settings
    public static void refreshSettings(boolean forceRefresh) {
        if (forceRefresh) {
            //Advanced settings
            preventKeyboardRotation = getSettingBoolean("preventKeyboardRotation", DEFAULT_PREVENT_KEYBOARD_ROTATION);
            activateGestures = getSettingBoolean("activateGestures", DEFAULT_ACTIVATE_GESTURES);
            vibrationOnDotTap = getSettingBoolean("vibrationOnDotTap", DEFAULT_DOTS_VIBRATION);
            capitalizeFirstWord = getSettingBoolean("capitalizeFirstWord", DEFAULT_CAPITAL_FIRST_CHAR_FROM_SENTENCE);
            showBrailleDotsFromTheRight = getSettingBoolean("showBrailleDotsFromTheRight", DEFAULT_START_DOTS_FROM_RIGHT);
            makeDot2And5Higher = getSettingBoolean("makeDot2And5Higher", DEFAULT_MAKE_DOT_2_5_HIGHER);
            newLineHideKeyboard = getSettingBoolean("newLineHideKeyboard", DEFAULT_HIDE_KEYBOARD_IN_NEWLINE);
            showOperationsButtons = getSettingBoolean("showOperationsButtons", DEFAULT_SHOW_OPERATIONS_BTNS);
            stopOverDotTime = getSettingInt("stopOverDotTime", DEFAULT_STOP_OVER_DOT_TIME);
            selectedDotsNumbersPeriod = getSettingInt("selectedDotsNumbersPeriod", DEFAULT_GET_SELECTED_DOTS_NUMBER_PERIOD);

            //Dots Layout
            selectedDotsLayout = getSettingInt("selectedDotsLayout", DEFAULT_DOTS_LAYOUT);

            //Dots Style
            dotRadius = getSettingInt("dotRadius", getDefaultDotRadius(0));
            dotLandscapeRadius = getSettingInt("dotLandscapeRadius", getDefaultDotRadius(1));
            dotFillColor = getSettingInt("dotFillColor", getMyColor(R.color.defaultDotFillColor));
            dotStrokeColor = getSettingInt("dotStrokeColor", getMyColor(R.color.defaultDotStrokeColor));
            fillDotOnTouch = getSettingBoolean("fillDotOnTouch", DEFAULT_FILL_DOT_ON_TOUCH);
            viewBrailleDotNumber = getSettingBoolean("viewBrailleDotNumber", DEFAULT_VIEW_DOT_NUMBER);

            //Keyboard Dimension
            startKeyboardContainerFromRight = getSettingBoolean("startKeyboardFromRight", DEFAULT_KEYBOARD_RIGHT_POSITION);
            keyboardHeightPortrait = getSettingInt("defaultKeyboardHeight", DEFAULT_PORTRAIT_KEYBOARD_HEIGHT);
            keyboardHeightLandscape = getSettingInt("defaultKeyboardLandscapeHeight", DEFAULT_LANDSCAPE_KEYBOARD_HEIGHT);
            keyboardWidthPortrait = getSettingInt("defaultKeyboardWidth", DEFAULT_PORTRAIT_KEYBOARD_WIDTH);
            keyboardWidthLandscape = getSettingInt("defaultKeyboardLandscapeWidth", DEFAULT_LANDSCAPE_KEYBOARD_WIDTH);

            //Keyboard Languages
            isArabicInputMethodActivated = getSettingBoolean("arabicInputMethod", DEFAULT_ARABIC_INPUT_METHOD);
            isEnglishInputMethodActivated = getSettingBoolean("englishInputMethod", DEFAULT_ENGLISH_INPUT_METHOD);
            isSpanishInputMethodActivated = getSettingBoolean("spanishInputMethod", DEFAULT_SPANISH_INPUT_METHOD);
            isFrenchInputMethodActivated = getSettingBoolean("frenchInputMethod", DEFAULT_FRENCH_INPUT_METHOD);
            defaultKeyboardLanguage = getSettingInt("defaultKeyboardLanguage", DEFAULT_NUM_KEYBOARD_LANGUAGE);

            //Sounds
            activeVoiceInput = getSettingBoolean("activeVoiceInput", DEFAULT_VOICE_INPUT);
            talkBackChar = getSettingBoolean("talkBackChar", DEFAULT_TALK_BACK_CHAR);
            defaultBrailleDotSound = getSettingInt("defaultBrailleDotSound", DEFAULT_BRAILLE_DOTS_SOUND);
            playAlwaysStoredVoices = getSettingBoolean("playAlwaysStoredVoices", DEFAULT_PLAY_ALWAYS_STORED_VOICES);
            readTextAfterCloseKeyboard = getSettingBoolean("readTextAfterCloseKeyboard", DEFAULT_READ_TEXT_AFTER_CLOSE_KEYBOARD);
        }
    }

    //--------------------------------------------------------------------------------------------//
    //Reset all settings from settings activity
    public static void resetAllSettings() {
        //Advanced settings
        putSettingBoolean("preventKeyboardRotation", DEFAULT_PREVENT_KEYBOARD_ROTATION);
        putSettingBoolean("activateGestures", DEFAULT_ACTIVATE_GESTURES);
        putSettingBoolean("vibrationOnDotTap", DEFAULT_DOTS_VIBRATION);
        putSettingBoolean("capitalizeFirstWord", DEFAULT_CAPITAL_FIRST_CHAR_FROM_SENTENCE);
        putSettingBoolean("showBrailleDotsFromTheRight", DEFAULT_START_DOTS_FROM_RIGHT);
        putSettingBoolean("makeDot2And5Higher", DEFAULT_MAKE_DOT_2_5_HIGHER);
        putSettingBoolean("newLineHideKeyboard", DEFAULT_HIDE_KEYBOARD_IN_NEWLINE);
        putSettingBoolean("showOperationsButtons", DEFAULT_SHOW_OPERATIONS_BTNS);
        putSettingInt("stopOverDotTime", DEFAULT_STOP_OVER_DOT_TIME);
        putSettingInt("selectedDotsNumbersPeriod", DEFAULT_GET_SELECTED_DOTS_NUMBER_PERIOD);

        //Dots Layout
        putSettingInt("selectedDotsLayout", DEFAULT_DOTS_LAYOUT);

        //Keyboard Dimension
        putSettingBoolean("startKeyboardFromRight", DEFAULT_KEYBOARD_RIGHT_POSITION);
        putSettingInt("defaultKeyboardHeight", DEFAULT_PORTRAIT_KEYBOARD_HEIGHT);
        putSettingInt("defaultKeyboardLandscapeHeight", DEFAULT_LANDSCAPE_KEYBOARD_HEIGHT);
        putSettingInt("defaultKeyboardWidth", DEFAULT_PORTRAIT_KEYBOARD_WIDTH);
        putSettingInt("defaultKeyboardLandscapeWidth", DEFAULT_LANDSCAPE_KEYBOARD_WIDTH);

        //Keyboard Languages
        putSettingBoolean("arabicInputMethod", DEFAULT_ARABIC_INPUT_METHOD);
        putSettingBoolean("englishInputMethod", DEFAULT_ENGLISH_INPUT_METHOD);
        putSettingBoolean("spanishInputMethod", DEFAULT_SPANISH_INPUT_METHOD);
        putSettingBoolean("frenchInputMethod", DEFAULT_FRENCH_INPUT_METHOD);
        putSettingInt("defaultKeyboardLanguage", DEFAULT_NUM_KEYBOARD_LANGUAGE);

        //Sounds
        putSettingBoolean("activeVoiceInput", DEFAULT_VOICE_INPUT);
        putSettingBoolean("talkBackChar", DEFAULT_TALK_BACK_CHAR);
        putSettingInt("defaultBrailleDotSound", DEFAULT_BRAILLE_DOTS_SOUND);
        putSettingBoolean("playAlwaysStoredVoices", DEFAULT_PLAY_ALWAYS_STORED_VOICES);
        putSettingBoolean("readTextAfterCloseKeyboard", DEFAULT_READ_TEXT_AFTER_CLOSE_KEYBOARD);
        reInitDefaultTextSpeech();

        //Let this the last one because I need the other to be finished
        //Dots Style
        putSettingInt("dotRadius", getDefaultDotRadius(0));
        putSettingInt("dotLandscapeRadius", getDefaultDotRadius(1));
        putSettingInt("dotFillColor", getMyColor(R.color.defaultDotFillColor));
        putSettingInt("dotStrokeColor", getMyColor(R.color.defaultDotStrokeColor));
        putSettingBoolean("fillDotOnTouch", DEFAULT_FILL_DOT_ON_TOUCH);
        putSettingBoolean("viewBrailleDotNumber", DEFAULT_VIEW_DOT_NUMBER);
    }

    //--------------------------------------------------------------------------------------------//
    //Check old settings and reset them because of core changes
    private void forceResetSettings() {
        int previousDefaultKeyboardHeight = getSettingInt("defaultKeyboardHeight", DEFAULT_PORTRAIT_KEYBOARD_HEIGHT);
        int previousDefaultKeyboardLandscapeHeight = getSettingInt("defaultKeyboardLandscapeHeight", DEFAULT_PORTRAIT_KEYBOARD_HEIGHT);

        //Check previous portrait keyboard height settings
        if (previousDefaultKeyboardHeight == 0 || previousDefaultKeyboardHeight == 1) {
            Common.putSettingInt("defaultKeyboardHeight", 50);
        } else if (previousDefaultKeyboardHeight == 2) {
            Common.putSettingInt("defaultKeyboardHeight", 70);
        } else if (previousDefaultKeyboardHeight == 3) {
            Common.putSettingInt("defaultKeyboardHeight", 100);
        }

        //Check previous landscape keyboard height settings
        if (previousDefaultKeyboardLandscapeHeight == 0 || previousDefaultKeyboardLandscapeHeight == 1) {
            Common.putSettingInt("defaultKeyboardLandscapeHeight", 50);
        } else if (previousDefaultKeyboardLandscapeHeight == 2) {
            Common.putSettingInt("defaultKeyboardLandscapeHeight", 70);
        } else if (previousDefaultKeyboardLandscapeHeight == 3) {
            Common.putSettingInt("defaultKeyboardLandscapeHeight", 100);
        }

        //Add a confirmation
        putSettingBoolean("isForceSettingsHasReset", true);
    }

    //--------------------------------------------------------------------------------------------//
    //Set current language based on the activated input methods and the default keyboard
    public static void setPatternSurfaceLanguage(KeyboardSurface keyboardSurface, InputConnection inputConnection) {
        try {
            if (isEnglishInputMethodActivated && defaultKeyboardLanguage == ENGLISH_LANGUAGE_INPUT_METHOD) {
                keyboardSurface.patternSurface = !isNumericInputText ? new EnglishSmallCharPattern(context) : new EnglishNumbersPattern(context);
            } else if (isArabicInputMethodActivated && defaultKeyboardLanguage == ARABIC_LANGUAGE_INPUT_METHOD) {
                keyboardSurface.patternSurface = !isNumericInputText ? new ArabicCharPattern(context) : new ArabicNumbersPattern(context);
            } else if (isSpanishInputMethodActivated && defaultKeyboardLanguage == SPANISH_LANGUAGE_INPUT_METHOD) {
                keyboardSurface.patternSurface = !isNumericInputText ? new SpanishSmallCharPattern(context, inputConnection) : new SpanishNumbersPattern(context, inputConnection);
            } else if (isFrenchInputMethodActivated && defaultKeyboardLanguage == FRENCH_LANGUAGE_INPUT_METHOD) {
                keyboardSurface.patternSurface = !isNumericInputText ? new FrenchSmallCharPattern(context) : new FrenchNumbersPattern(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    //If the user changes the system language
    public static void checkSystemLanguageAndLocale() {
        if (!currentSystemLanguage.equals(Locale.getDefault().getLanguage())) {
            currentSystemLanguage = Locale.getDefault().getLanguage();
            currentSystemLocaleLanguage = new Locale(currentSystemLanguage);
        }
    }

    //--------------------------------------------------------------------------------------------//
    //Used usually to get the touch on ops bars to speech the buttons texts or get dots X position
    public static int getRelativeLeft(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getLeft();
        else
            return myView.getLeft() + getRelativeLeft((View) myView.getParent());
    }
    //--------------------------------------------------------------------------------------------//
    //Used usually to get the touch on ops bars to speech the buttons texts or get dots Y position
    public static int getRelativeTop(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());
    }
}
