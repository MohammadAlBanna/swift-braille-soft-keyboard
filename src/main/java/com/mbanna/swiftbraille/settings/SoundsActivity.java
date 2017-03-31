package com.mbanna.swiftbraille.settings;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import com.mbanna.swiftbraille.Common;
import com.mbanna.swiftbraille.R;
import com.mbanna.swiftbraille.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

public class SoundsActivity extends AppCompatActivity implements CheckBox.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    Spinner soundOverBrailleDot, listOfAllTTSs;
    ArrayList<String> ttsEnginesListPackages;
    ArrayAdapter<String> listOfAllTTSsAdapter, dotSoundSpinnerAdapter;
    CheckBox activateVoiceSoundOnOpsBar, talkBackChar, playAlwaysStoredVoices, readTextAfterCloseKeyboard;

    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings_sounds);

            //Show back button
            android.support.v7.app.ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
            }

            //Speech the title of the activity
            Common.defaultTextSpeech.speechText(getString(R.string.sounds_item));

            //Declaration
            soundOverBrailleDot = (Spinner)findViewById(R.id.soundOverBrailleDot);
            listOfAllTTSs = (Spinner)findViewById(R.id.listOfAllTTSs);

            activateVoiceSoundOnOpsBar = (CheckBox)findViewById(R.id.activateVoiceSoundOnOpsBar);
            activateVoiceSoundOnOpsBar.setChecked(Common.activeVoiceInput);
            activateVoiceSoundOnOpsBar.setOnCheckedChangeListener(this);

            talkBackChar = (CheckBox)findViewById(R.id.talkBackChar);
            talkBackChar.setChecked(Common.talkBackChar);
            talkBackChar.setOnCheckedChangeListener(this);

            playAlwaysStoredVoices = (CheckBox)findViewById(R.id.playAlwaysStoredVoices);
            playAlwaysStoredVoices.setChecked(Common.playAlwaysStoredVoices);
            playAlwaysStoredVoices.setOnCheckedChangeListener(this);

            //Hide play always stored voices if Arabic input method not activated
            if(!Common.isArabicInputMethodActivated){
                playAlwaysStoredVoices.setVisibility(View.GONE);
            }

            readTextAfterCloseKeyboard = (CheckBox)findViewById(R.id.readTextAfterCloseKeyboard);
            readTextAfterCloseKeyboard.setChecked(Common.readTextAfterCloseKeyboard);
            readTextAfterCloseKeyboard.setOnCheckedChangeListener(this);

            //selected default tts engine
            List<TextToSpeech.EngineInfo> listInstalledEngines = Common.defaultTextSpeech.getListOfEngines();
            ArrayList<String> ttsEnginesList = new ArrayList<>();
            ttsEnginesListPackages = new ArrayList<>();
            if (listInstalledEngines != null) {
                for (int i = 0; i < listInstalledEngines.size(); i++) {
                    ttsEnginesList.add(listInstalledEngines.get(i).label);
                    ttsEnginesListPackages.add(listInstalledEngines.get(i).name);
                }
            }

            //set the adapter for engines spinner
            listOfAllTTSsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ttsEnginesList);
            listOfAllTTSsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listOfAllTTSs.setAdapter(listOfAllTTSsAdapter);
            //Maybe the default tts has been deleted from user device
            int positionOfSelectedTTS = ttsEnginesListPackages.indexOf(Common.defaultTextSpeech.getMyDefaultTTS());
            if (positionOfSelectedTTS != -1) {
                listOfAllTTSs.setSelection(positionOfSelectedTTS, true);
            } else {
                listOfAllTTSs.setSelection(0, true);
            }

            //Selected sound over braille
            ArrayList<String> soundsBrailleDotList = new ArrayList<>();
            soundsBrailleDotList.add(getString(R.string.no_braille_dot_sound));
            soundsBrailleDotList.add(getString(R.string.tick_braille_dot_sound));
            soundsBrailleDotList.add(getString(R.string.dorime_braille_dot_sound));
            soundsBrailleDotList.add(getString(R.string.number_braille_dot_sound));

            //Set the adapter for sound over braille dot
            dotSoundSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, soundsBrailleDotList);
            dotSoundSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            soundOverBrailleDot.setAdapter(dotSoundSpinnerAdapter);
            soundOverBrailleDot.setSelection(Common.defaultBrailleDotSound, true);

            //Add listeners
            initListeners(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------//
    public void onResetSettingsBtnAction(View v){
        try{
            //Remove listeners
            removeListeners();

            Common.reInitDefaultTextSpeech();
            Common.putSettingInt("defaultBrailleDotSound", Common.DEFAULT_BRAILLE_DOTS_SOUND);
            Common.putSettingBoolean("activeVoiceInput", Common.DEFAULT_VOICE_INPUT);
            Common.putSettingBoolean("talkBackChar", Common.DEFAULT_TALK_BACK_CHAR);
            Common.putSettingBoolean("playAlwaysStoredVoices", Common.DEFAULT_PLAY_ALWAYS_STORED_VOICES);
            Common.putSettingBoolean("readTextAfterCloseKeyboard", Common.DEFAULT_READ_TEXT_AFTER_CLOSE_KEYBOARD);

            soundOverBrailleDot.setSelection(Common.DEFAULT_BRAILLE_DOTS_SOUND, true);
            activateVoiceSoundOnOpsBar.setChecked(Common.DEFAULT_VOICE_INPUT);
            talkBackChar.setChecked(Common.DEFAULT_TALK_BACK_CHAR);
            playAlwaysStoredVoices.setChecked(Common.DEFAULT_PLAY_ALWAYS_STORED_VOICES);
            readTextAfterCloseKeyboard.setChecked(Common.DEFAULT_READ_TEXT_AFTER_CLOSE_KEYBOARD);

            Common.areSettingsChanged = true;

            if (!Common.defaultTextSpeech.isArabicSupported() && Common.currentSystemLanguage.equals("ar") && Common.getSoundPath("ar_message_reset_settings") != -1) {
                Common.runPatternSoundPronounce(Common.getSoundPath("ar_message_reset_settings"), true);
            } else {
                Common.defaultTextSpeech.speechText(getString(R.string.reset_settings_done), true);
            }

            //Recreate the activity, because of removing and re-init the
            recreate();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------//
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        try{
            switch (buttonView.getId()) {
                case R.id.activateVoiceSoundOnOpsBar:
                    if (isChecked) {
                        //Check if operations bars are on
                        if(!Common.showOperationsButtons){
                            Common.showGotItAlertDialog(this, getString(R.string.caution), getString(R.string.ops_bars_off_for_voice_input));
                        }
                        Common.putSettingBoolean("activeVoiceInput", true);
                    } else {
                        Common.putSettingBoolean("activeVoiceInput", false);
                    }
                    Common.areSettingsChanged = true;
                    break;
                case R.id.talkBackChar:
                    if (isChecked) {
                        Common.putSettingBoolean("talkBackChar", true);
                    } else {
                        Common.putSettingBoolean("talkBackChar", false);
                    }
                    Common.areSettingsChanged = true;
                    break;
                case R.id.playAlwaysStoredVoices:
                    if (isChecked) {
                        Common.putSettingBoolean("playAlwaysStoredVoices", true);
                    } else {
                        Common.putSettingBoolean("playAlwaysStoredVoices", false);
                    }
                    Common.areSettingsChanged = true;
                    break;
                case R.id.readTextAfterCloseKeyboard:
                    if (isChecked) {
                        Common.putSettingBoolean("readTextAfterCloseKeyboard", true);
                    } else {
                        Common.putSettingBoolean("readTextAfterCloseKeyboard", false);
                    }
                    Common.areSettingsChanged = true;
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------//
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try{
            String finalSelectedItem;
            switch (parent.getId()) {
                case R.id.listOfAllTTSs:
                    finalSelectedItem = parent.getItemAtPosition(position).toString();
                    Common.putSettingString("defaultTTSEngine", ttsEnginesListPackages.get(listOfAllTTSsAdapter.getPosition(finalSelectedItem)));
                    Common.areSettingsChanged = true;
                    //Now change the tts with a new object
                    Common.reInitDefaultTextSpeech();
                    Common.defaultTextSpeech.speechText(finalSelectedItem);
                    break;
                case R.id.soundOverBrailleDot:
                    finalSelectedItem = parent.getItemAtPosition(position).toString();
                    Common.putSettingInt("defaultBrailleDotSound", position);
                    Common.areSettingsChanged = true;
                    Common.defaultTextSpeech.speechText(finalSelectedItem);
                    break;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------//
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Do nothing
    }
    //--------------------------------------------------------------------------------------------//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try{
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.settings_menu, menu);
        } catch(Exception e){
            e.printStackTrace();
        }
        return super.onCreateOptionsMenu(menu);
    }
    //--------------------------------------------------------------------------------------------//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.howToItem:
                    Common.previousActivity = SoundsActivity.class;
                    Intent howToIntent = new Intent(this, WebViewActivity.class);
                    howToIntent.putExtra("activity_title", getString(R.string.how_to_item));
                    howToIntent.putExtra("web_url", getString(R.string.how_to_link));
                    startActivity(howToIntent);
                    break;
                case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(this);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    //--------------------------------------------------------------------------------------------//
    //Init listeners from spinners
    private void initListeners(final AdapterView.OnItemSelectedListener listener) {
        try{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    soundOverBrailleDot.setOnItemSelectedListener(listener);
                    listOfAllTTSs.setOnItemSelectedListener(listener);
                }
            }, 1000);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------//
    //Remove all listeners from spinners
    private void removeListeners() {
        try{
            soundOverBrailleDot.setOnItemSelectedListener(null);
            listOfAllTTSs.setOnItemSelectedListener(null);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------//
    @Override
    public void onDestroy() {
        try{
            Common.speakHiddenKeyboard = true;
            //Refresh settings
            Common.refreshSettings(Common.areSettingsChanged);
        } catch (Exception e){
            e.printStackTrace();
        }
        super.onDestroy();
    }


}
