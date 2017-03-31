package com.mbanna.swiftbraille;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

//This class to use the selected text to speech engine by the user
public class DefaultTextSpeech implements TextToSpeech.OnInitListener {

    private TextToSpeech tts = null;
    private boolean isActive = false;
    private String defaultTTS = null;
    private Context context;
    private Locale arabicLanguage =  new Locale("ar");
    private boolean reConnectToTTS = true;

    //--------------------------------------------------------------------------------------------//
    DefaultTextSpeech(Context context) {
        try{
            defaultTTS = getMyDefaultTTS();
            tts = new TextToSpeech(context, this, defaultTTS);
            this.context = context;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------//
    @Override
    public void onInit(int status) {
        try {
            if (status != TextToSpeech.ERROR) {
                isActive = true;
            } else {
                isActive = false;
                Log.e(Common.TAG, "TTS onInit: " + status);
            }
            //If I lost the connection to the TTS, try to re connect with it
            reConnectToTTS = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("All")
    public void speechText(String text) {
        try {
            if(tts == null && reConnectToTTS){
                defaultTTS = getMyDefaultTTS();
                tts = new TextToSpeech(context, this, defaultTTS);
                reConnectToTTS = false;
            }

            if (isActive && !Common.isTouchToExploreEnabled() && isLangAvailable(Common.currentSystemLocaleLanguage)) {
                //Set the text language to the engine
                tts.setLanguage(Common.currentSystemLocaleLanguage);
                //Stop played sound
                tts.stop();
                //Start to speak
                if (Common.SDK_VERSION <= 20) {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "SwiftBraille");
                }
            } else if(!isLangAvailable(Common.currentSystemLocaleLanguage) || !isActive || !Common.isTouchToExploreEnabled()){
                Common.makeToast(text, Toast.LENGTH_SHORT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------//
    //This to speech the text that written by the keyboard, maybe the locale on phone is Arabic, but the user writing in English!
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("All")
    public void speechLocaleKeyboardText(String text) {
        try {
            if(tts == null && reConnectToTTS){
                defaultTTS = getMyDefaultTTS();
                tts = new TextToSpeech(context, this, defaultTTS);
                reConnectToTTS = false;
            }

            if (isActive && !Common.isTouchToExploreEnabled() && isLangAvailable(Common.currentLocaleLanguage)) {
                //Set the text language to the engine
                tts.setLanguage(Common.currentLocaleLanguage);
                //Stop played sound
                tts.stop();
                //Start to speak
                if (Common.SDK_VERSION <= 20) {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "SwiftBraille");
                }
            } else if(!isLangAvailable(Common.currentLocaleLanguage) || !isActive || !Common.isTouchToExploreEnabled()){
                Common.makeToast(text, Toast.LENGTH_SHORT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------//
    //Some sounds screen reader doesn't recognize them, I need to force speech them from SwiftBraille itself
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("All")
    public void speechText(String text, boolean showToast) {
        try {
            if(tts == null && reConnectToTTS){
                defaultTTS = getMyDefaultTTS();
                tts = new TextToSpeech(context, this, defaultTTS);
                reConnectToTTS = false;
            }

            if (isActive && !Common.isTouchToExploreEnabled() && isLangAvailable(Common.currentSystemLocaleLanguage)) {
                //Set the text language to the engine
                tts.setLanguage(Common.currentSystemLocaleLanguage);
                //Stop played sound
                tts.stop();
                //Start to speak
                if (Common.SDK_VERSION <= 20) {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, Common.TAG);
                }
            } else if(!isLangAvailable(Common.currentSystemLocaleLanguage) || showToast || !isActive || !Common.isTouchToExploreEnabled()){
                //If the toast was opened, call my TTS to speech my text
                if(!Common.makeToast(text, Toast.LENGTH_SHORT)){
                    speechText(text);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------//
    //If the user changed the default TTS in the run time
     void checkEngineDefaultChanged() {
        try {
            if (defaultTTS != null && !defaultTTS.equals(getMyDefaultTTS())) {
                destroy();
                defaultTTS = getMyDefaultTTS();
                tts = new TextToSpeech(context, this, defaultTTS);
                reConnectToTTS = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------//
    public boolean isArabicSupported() {
        try {
            return isActive && tts != null && tts.isLanguageAvailable(arabicLanguage) == TextToSpeech.LANG_AVAILABLE;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //--------------------------------------------------------------------------------------------//
    private boolean isLangAvailable(Locale locale){
        try {
            return isActive && tts != null && tts.isLanguageAvailable(locale) == TextToSpeech.LANG_AVAILABLE;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //--------------------------------------------------------------------------------------------//
    //For settings screen, get list to all installed tts engines to select default one for SwiftBraille
    public List<TextToSpeech.EngineInfo> getListOfEngines(){
        if(tts != null){
            return tts.getEngines();
        }
        return null;
    }

    //--------------------------------------------------------------------------------------------//
    //Get selected tts for SwiftBraille
    public String getMyDefaultTTS(){
        if(tts != null) {
            return Common.getSettingString("defaultTTSEngine", tts.getDefaultEngine());
        } else{
            return Common.getSettingString("defaultTTSEngine", null);
        }
    }
    //--------------------------------------------------------------------------------------------//
    void destroy() {
        try {
            if(tts != null){
                tts.stop();
                tts.shutdown();
                tts = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
