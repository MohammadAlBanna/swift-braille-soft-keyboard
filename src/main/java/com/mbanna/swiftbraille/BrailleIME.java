package com.mbanna.swiftbraille;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.annotation.TargetApi;
import android.inputmethodservice.InputMethodService;
import android.os.Build;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.provider.Settings;
import com.mbanna.swiftbraille.voiceime.VoiceRecognitionTrigger;

//View the BrailleSurface layout on input fields / The keyboard
public class BrailleIME extends InputMethodService {

    InputConnection ic;
    KeyboardSurface keyboardSurface;
    boolean readTextAfterCloseKeyboard, preventKeyboardRotation = false;
    public VoiceRecognitionTrigger mVoiceRecognitionTrigger;
    int rotationState = 0; // 0=> don't rotate || 1=>rotate

    //-------------------------------------------------------------------------//
    @Override
    public void onCreate() {
        super.onCreate();
        try{
            keyboardSurface = new KeyboardSurface(this, this);
            readTextAfterCloseKeyboard = Common.readTextAfterCloseKeyboard;
            mVoiceRecognitionTrigger = new VoiceRecognitionTrigger(this);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    @Override
    public void onStartInput(EditorInfo info, boolean restarting) {
        try {
            //Check the input text accepts multi-lines or not
            Common.isInputTextSingleLine = 0 == (info.inputType & InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            Common.isNumericInputText = 2 == (info.inputType & InputType.TYPE_CLASS_NUMBER);
            super.onStartInputView(info, restarting);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    @Override
    public View onCreateInputView() {
        try{
            keyboardSurface = new KeyboardSurface(this, this);
            return keyboardSurface;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    //-------------------------------------------------------------------------//
    //Notify voice recognition feature
    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);
        if (mVoiceRecognitionTrigger != null) {
            mVoiceRecognitionTrigger.onStartInputView();
        }
    }

    //-------------------------------------------------------------------------//
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onWindowShown() {
        try {
            //When the user changes the settings, refresh the view with a new object
            if (Common.areSettingsChanged) {
                keyboardSurface = new KeyboardSurface(this, this);
                setInputView(keyboardSurface);
                readTextAfterCloseKeyboard = Common.readTextAfterCloseKeyboard;
                Common.areSettingsChanged = false;
            }

            //Reset the keyboard every time the user open the keyboard
            Common.setPatternSurfaceLanguage(keyboardSurface, getCurrentInputConnection());

            //Check the rotation of the keyboard
            preventKeyboardRotation = Common.preventKeyboardRotation;

            //Get the user option of rotation
            if (preventKeyboardRotation) {
                //Get the previous state of screen rotation
                rotationState = Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
                if (Common.SDK_VERSION >= Build.VERSION_CODES.M) {
                    if (Settings.System.canWrite(this)) {
                        Settings.System.putInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
                    }
                } else {
                    Settings.System.putInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
                }
            }

            //Check engine changes
            Common.defaultTextSpeech.checkEngineDefaultChanged();

            //Set the cursor to the end of the edit text
            InputConnection inputConnection = getCurrentInputConnection();
            if (inputConnection != null) {
                String allText = Common.getAllInputText(inputConnection);
                inputConnection.setSelection(allText.length(), allText.length());
            }

            //Show the toast and speech
            //If the input type is numeric, speech the numbers keyboard
            if (Common.isNumericInputText) {
                if (Common.currentLanguage == Common.ENGLISH_LANGUAGE_INPUT_METHOD) {
                    if (!Common.defaultTextSpeech.isArabicSupported() && Common.currentSystemLanguage.equals("ar") && Common.getSoundPath("numbers_keyboard") != -1) {
                        Common.runPatternSoundPronounce(Common.getSoundPath("numbers_keyboard"), true);
                    } else {
                        Common.defaultTextSpeech.speechText(getString(R.string.numbers_keyboard), true);
                    }
                } else {
                    if (!Common.defaultTextSpeech.isArabicSupported() && Common.currentSystemLanguage.equals("ar") && Common.getSoundPath("numbers_keyboard") != -1) {
                        Common.runPatternSoundPronounce(Common.getSoundPath("numbers_keyboard"), true);
                    } else {
                        Common.defaultTextSpeech.speechText(getString(R.string.app_shows_ar_keyboard), true);
                    }
                }
            } else {
                if (Common.currentLanguage == Common.ENGLISH_LANGUAGE_INPUT_METHOD) {
                    if (!Common.defaultTextSpeech.isArabicSupported() && Common.currentSystemLanguage.equals("ar") && Common.getSoundPath("ar_message_en_keyboard") != -1) {
                        Common.runPatternSoundPronounce(Common.getSoundPath("ar_message_en_keyboard"), true);
                    } else {
                        Common.defaultTextSpeech.speechText(getString(R.string.app_shows_en_keyboard), true);
                    }
                } else if(Common.currentLanguage == Common.ARABIC_LANGUAGE_INPUT_METHOD) {
                    if (!Common.defaultTextSpeech.isArabicSupported() && Common.currentSystemLanguage.equals("ar") && Common.getSoundPath("ar_message_ar_keyboard") != -1) {
                        Common.runPatternSoundPronounce(Common.getSoundPath("ar_message_ar_keyboard"), true);
                    } else {
                        Common.defaultTextSpeech.speechText(getString(R.string.app_shows_ar_keyboard), true);
                    }
                } else if(Common.currentLanguage == Common.SPANISH_LANGUAGE_INPUT_METHOD){
                    Common.defaultTextSpeech.speechText(getString(R.string.app_shows_es_keyboard), true);
                } else if(Common.currentLanguage == Common.FRENCH_LANGUAGE_INPUT_METHOD){
                    Common.defaultTextSpeech.speechText(getString(R.string.app_shows_fr_keyboard), true);
                }
            }
            super.onWindowShown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onFinishInputView(boolean finishingInput) {
        try {
            ic = getCurrentInputConnection();
            //Because of the keyboard will be hidden when the user want to open settings from floating menu
            if (Common.speakHiddenKeyboard && ic != null && !finishingInput) {
                String fullText = Common.getAllInputText(ic);
                //Say the sound when the keyboard
                if (readTextAfterCloseKeyboard && fullText.length() > 0 && (Common.currentSystemLanguage.equals("en") || (Common.currentSystemLanguage.equals("ar") && Common.defaultTextSpeech.isArabicSupported()))) {
                    Common.defaultTextSpeech.speechText(getString(R.string.app_is_hidden) + getString(R.string.app_final_text) + "  " + fullText, true);
                } else {
                    if (!Common.defaultTextSpeech.isArabicSupported() && Common.currentSystemLanguage.equals("ar") && Common.getSoundPath("ar_message_keyboard_hidden") != -1) {
                        Common.runPatternSoundPronounce(Common.getSoundPath("ar_message_keyboard_hidden"), true);
                    } else {
                        Common.defaultTextSpeech.speechText(getString(R.string.app_is_hidden), true);
                    }
                }

                if (preventKeyboardRotation) {
                    if (Common.SDK_VERSION >= Build.VERSION_CODES.M) {
                        if (Settings.System.canWrite(this)) {
                            Settings.System.putInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, rotationState);
                        }
                    } else {
                        Settings.System.putInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, rotationState);
                    }
                }

                //Make editor action, like search or anything else!!
                sendDefaultEditorAction(true);
            }
           super.onFinishInputView(finishingInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    @Override
    public void onDestroy() {
        try {
            //Clear voice recognition
            if (mVoiceRecognitionTrigger != null) {
                mVoiceRecognitionTrigger.unregister(this);
            }

            //Release what I don't need now
            if (keyboardSurface != null) {
                Common.defaultTextSpeech.destroy();
            }
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
