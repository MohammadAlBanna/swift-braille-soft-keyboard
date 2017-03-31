package com.mbanna.swiftbraille.settings;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
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

public class AdvancedActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CheckBox.OnCheckedChangeListener {
    CheckBox activateGestures, vibrationOnDotTap, capitalizeFirstWord, showBrailleDotsFromRight, preventKeyboardRotation,
            newLineHideKeyboard, showOperationsButtons, makeDot2And5Higher;
    Spinner stopOverDotSpinner, selectedDotsPeriodSpinner;
    ArrayAdapter<String> stopOverDotSpinnerAdapter, selectedDotsPeriodSpinnerAdapter;

    //-------------------------------------------------------------------------//
    @SuppressWarnings("all")
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings_advanced);

            //Show back button
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
            }

            //Speak the title of the activity
            Common.defaultTextSpeech.speechText(getString(R.string.advanced_item));

            //Switches declaration
            activateGestures = (CheckBox) findViewById(R.id.activateGestures);
            activateGestures.setChecked(Common.activateGestures);
            activateGestures.setOnCheckedChangeListener(this);

            vibrationOnDotTap = (CheckBox) findViewById(R.id.vibrationOnDotTap);
            vibrationOnDotTap.setChecked(Common.vibrationOnDotTap);
            vibrationOnDotTap.setOnCheckedChangeListener(this);

            capitalizeFirstWord = (CheckBox) findViewById(R.id.capitalizeFirstWord);
            capitalizeFirstWord.setChecked(Common.capitalizeFirstWord);
            capitalizeFirstWord.setOnCheckedChangeListener(this);

            showBrailleDotsFromRight = (CheckBox) findViewById(R.id.showBrailleDotsFromRight);
            showBrailleDotsFromRight.setChecked(Common.showBrailleDotsFromTheRight);
            showBrailleDotsFromRight.setOnCheckedChangeListener(this);

            if(Common.selectedDotsLayout != Common.BRAILLE_CELL_DOTS_LAYOUT){
                showBrailleDotsFromRight.setVisibility(View.GONE);
            }

            makeDot2And5Higher = (CheckBox) findViewById(R.id.makeDot2And5Higher);
            makeDot2And5Higher.setChecked(Common.makeDot2And5Higher);
            makeDot2And5Higher.setOnCheckedChangeListener(this);

            if(Common.selectedDotsLayout != Common.PERKINS_DOTS_LAYOUT){
                makeDot2And5Higher.setVisibility(View.GONE);
            }

            preventKeyboardRotation = (CheckBox) findViewById(R.id.preventKeyboardRotation);
            //Prevent keyboard rotation
            if (Common.SDK_VERSION >= Build.VERSION_CODES.M) {
                if (!Settings.System.canWrite(this)) {
                    preventKeyboardRotation.setChecked(false);
                } else {
                    preventKeyboardRotation.setChecked(Common.preventKeyboardRotation);
                }
            } else {
                preventKeyboardRotation.setChecked(Common.preventKeyboardRotation);
            }
            preventKeyboardRotation.setOnCheckedChangeListener(this);

            newLineHideKeyboard = (CheckBox) findViewById(R.id.newLineHideKeyboard);
            newLineHideKeyboard.setChecked(Common.newLineHideKeyboard);
            newLineHideKeyboard.setOnCheckedChangeListener(this);

            showOperationsButtons = (CheckBox) findViewById(R.id.showOperationsButtons);
            showOperationsButtons.setChecked(Common.showOperationsButtons);
            showOperationsButtons.setOnCheckedChangeListener(this);

            //Spinners declaration
            stopOverDotSpinner = (Spinner) findViewById(R.id.stopOverDotSpinner);
            selectedDotsPeriodSpinner = (Spinner) findViewById(R.id.selectedDotsPeriodSpinner);

            //Stop over dot times
            ArrayList<String> stopOverDotTimeList = new ArrayList<>();
            stopOverDotTimeList.add(getString(R.string.no_stop_when_hover));
            stopOverDotTimeList.add(getString(R.string.stop_half_second));
            stopOverDotTimeList.add(getString(R.string.stop_one_second));

            //Selected dots period
            ArrayList<String> selectedDotsNumbersList = new ArrayList<>();
            selectedDotsNumbersList.add(getString(R.string.no_period_selected_dots));
            selectedDotsNumbersList.add(getString(R.string.milliseconds, 100));
            selectedDotsNumbersList.add(getString(R.string.milliseconds, 200));
            selectedDotsNumbersList.add(getString(R.string.milliseconds, 300));
            selectedDotsNumbersList.add(getString(R.string.milliseconds, 400));
            selectedDotsNumbersList.add(getString(R.string.after_half_second));
            selectedDotsNumbersList.add(getString(R.string.after_one_second));
            selectedDotsNumbersList.add(getString(R.string.after_one_and_half_second));
            selectedDotsNumbersList.add(getString(R.string.after_two_seconds));

            //Set the adapter for the stop over dot time
            int previousStopOverDotTime = Common.stopOverDotTime;
            if (previousStopOverDotTime != 0 && previousStopOverDotTime != 500 && previousStopOverDotTime != 1000) {
                previousStopOverDotTime = 0;
            }

            stopOverDotSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stopOverDotTimeList);
            stopOverDotSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stopOverDotSpinner.setAdapter(stopOverDotSpinnerAdapter);
            if (previousStopOverDotTime == 500) {
                stopOverDotSpinner.setSelection(1, true);
            } else if (previousStopOverDotTime == 1000) {
                stopOverDotSpinner.setSelection(2, true);
            } else {
                stopOverDotSpinner.setSelection(0, true);
            }

            //Set the adapter for the selected dots period
            int previousSelectedDotNumberPeriod = Common.selectedDotsNumbersPeriod;
            if (previousSelectedDotNumberPeriod != 0 && previousSelectedDotNumberPeriod != 100 && previousSelectedDotNumberPeriod != 200 && previousSelectedDotNumberPeriod != 300
                    && previousSelectedDotNumberPeriod != 400 && previousSelectedDotNumberPeriod != 500 &&
                    previousSelectedDotNumberPeriod != 1000 && previousSelectedDotNumberPeriod != 1500 && previousSelectedDotNumberPeriod != 2000) {
                previousSelectedDotNumberPeriod = 500;
            }

            selectedDotsPeriodSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, selectedDotsNumbersList);
            selectedDotsPeriodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            selectedDotsPeriodSpinner.setAdapter(selectedDotsPeriodSpinnerAdapter);
            if (previousSelectedDotNumberPeriod == 0) {
                selectedDotsPeriodSpinner.setSelection(0, true);
            } else if (previousSelectedDotNumberPeriod == 100) {
                selectedDotsPeriodSpinner.setSelection(1, true);
            } else if (previousSelectedDotNumberPeriod == 200) {
                selectedDotsPeriodSpinner.setSelection(2, true);
            } else if (previousSelectedDotNumberPeriod == 300) {
                selectedDotsPeriodSpinner.setSelection(3, true);
            } else if (previousSelectedDotNumberPeriod == 400) {
                selectedDotsPeriodSpinner.setSelection(4, true);
            } else if (previousSelectedDotNumberPeriod == 500) {
                selectedDotsPeriodSpinner.setSelection(5, true);
            } else if (previousSelectedDotNumberPeriod == 1000) {
                selectedDotsPeriodSpinner.setSelection(6, true);
            } else if (previousSelectedDotNumberPeriod == 1500) {
                selectedDotsPeriodSpinner.setSelection(7, true);
            } else if (previousSelectedDotNumberPeriod == 2000) {
                selectedDotsPeriodSpinner.setSelection(8, true);
            } else {
                selectedDotsPeriodSpinner.setSelection(0, true);
            }

            //Add listeners to spinners
            initListeners(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    public void onResetSettingsBtnAction(View v) {
        try {
            //Remove listeners
            removeListeners();

            Common.putSettingBoolean("activateGestures", Common.DEFAULT_ACTIVATE_GESTURES);
            Common.putSettingBoolean("vibrationOnDotTap", Common.DEFAULT_DOTS_VIBRATION);
            Common.putSettingBoolean("capitalizeFirstWord", Common.DEFAULT_CAPITAL_FIRST_CHAR_FROM_SENTENCE);
            Common.putSettingBoolean("showBrailleDotsFromTheRight", Common.DEFAULT_START_DOTS_FROM_RIGHT);
            Common.putSettingBoolean("makeDot2And5Higher", Common.DEFAULT_MAKE_DOT_2_5_HIGHER);
            Common.putSettingBoolean("newLineHideKeyboard", Common.DEFAULT_HIDE_KEYBOARD_IN_NEWLINE);
            Common.putSettingBoolean("preventKeyboardRotation", Common.DEFAULT_PREVENT_KEYBOARD_ROTATION);
            Common.putSettingBoolean("showOperationsButtons", Common.DEFAULT_SHOW_OPERATIONS_BTNS);
            Common.putSettingInt("stopOverDotTime", Common.DEFAULT_STOP_OVER_DOT_TIME);
            Common.putSettingInt("selectedDotsNumbersPeriod", Common.DEFAULT_GET_SELECTED_DOTS_NUMBER_PERIOD);

            stopOverDotSpinner.setSelection(Common.DEFAULT_STOP_OVER_DOT_TIME, true);
            selectedDotsPeriodSpinner.setSelection(5, true); //Half a second
            activateGestures.setChecked(Common.DEFAULT_ACTIVATE_GESTURES);
            showBrailleDotsFromRight.setChecked(Common.DEFAULT_START_DOTS_FROM_RIGHT);
            preventKeyboardRotation.setChecked(Common.DEFAULT_PREVENT_KEYBOARD_ROTATION);
            newLineHideKeyboard.setChecked(Common.DEFAULT_HIDE_KEYBOARD_IN_NEWLINE);
            vibrationOnDotTap.setChecked(Common.DEFAULT_DOTS_VIBRATION);
            capitalizeFirstWord.setChecked(Common.DEFAULT_CAPITAL_FIRST_CHAR_FROM_SENTENCE);
            makeDot2And5Higher.setChecked(Common.DEFAULT_MAKE_DOT_2_5_HIGHER);
            showOperationsButtons.setChecked(Common.DEFAULT_SHOW_OPERATIONS_BTNS);

            Common.areSettingsChanged = true;

            if (!Common.defaultTextSpeech.isArabicSupported() && Common.currentSystemLanguage.equals("ar") && Common.getSoundPath("ar_message_reset_settings") != -1) {
                Common.runPatternSoundPronounce(Common.getSoundPath("ar_message_reset_settings"), true);
            } else {
                Common.defaultTextSpeech.speechText(getString(R.string.reset_settings_done), true);
            }

            //Back listeners
            initListeners(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        try {
            switch (buttonView.getId()) {
                case R.id.activateGestures:
                    if (isChecked) {
                        Common.putSettingBoolean("activateGestures", true);
                    } else {
                        Common.putSettingBoolean("activateGestures", false);
                        //Check of the gestures and ops bars are inactivated
                        if(!showOperationsButtons.isChecked()){
                            Common.showGotItAlertDialog(this, getString(R.string.caution), getString(R.string.gestures_and_ops_bars_inactivated));
                        }
                    }
                    Common.areSettingsChanged = true;
                    break;

                case R.id.showOperationsButtons:
                    if (isChecked) {
                        Common.putSettingBoolean("showOperationsButtons", true);
                    } else {
                        Common.putSettingBoolean("showOperationsButtons", false);
                        //Check of the gestures and ops bars are inactivated
                        if(!activateGestures.isChecked()){
                            Common.showGotItAlertDialog(this, getString(R.string.caution), getString(R.string.gestures_and_ops_bars_inactivated));
                        }
                    }
                    //Change the dot radius the default
                    Common.putSettingInt("dotRadius", Common.getDefaultDotRadius(0));
                    Common.putSettingInt("dotLandscapeRadius", Common.getDefaultDotRadius(1));
                    Common.areSettingsChanged = true;
                    break;

                case R.id.vibrationOnDotTap:
                    if (isChecked) {
                        Common.putSettingBoolean("vibrationOnDotTap", true);
                    } else {
                        Common.putSettingBoolean("vibrationOnDotTap", false);
                    }
                    Common.areSettingsChanged = true;
                    break;

                case R.id.capitalizeFirstWord:
                    if (isChecked) {
                        Common.putSettingBoolean("capitalizeFirstWord", true);
                    } else {
                        Common.putSettingBoolean("capitalizeFirstWord", false);
                    }
                    Common.areSettingsChanged = true;
                    break;

                case R.id.showBrailleDotsFromRight:
                    if (isChecked) {
                        Common.putSettingBoolean("showBrailleDotsFromTheRight", true);
                    } else {
                        Common.putSettingBoolean("showBrailleDotsFromTheRight", false);
                    }
                    Common.areSettingsChanged = true;
                    break;

                case R.id.makeDot2And5Higher:
                    if (isChecked) {
                        Common.putSettingBoolean("makeDot2And5Higher", true);
                    } else {
                        Common.putSettingBoolean("makeDot2And5Higher", false);
                    }

                    //Change the dot radius the default
                    Common.putSettingInt("dotRadius", Common.getDefaultDotRadius(0));
                    Common.putSettingInt("dotLandscapeRadius", Common.getDefaultDotRadius(1));
                    Common.areSettingsChanged = true;
                    break;

                case R.id.newLineHideKeyboard:
                    if (isChecked) {
                        Common.putSettingBoolean("newLineHideKeyboard", true);
                    } else {
                        Common.putSettingBoolean("newLineHideKeyboard", false);
                    }
                    Common.areSettingsChanged = true;
                    break;

                case R.id.preventKeyboardRotation:
                    if (isChecked) {
                        //Check the permission of changing the settings values
                        if (Common.SDK_VERSION >= Build.VERSION_CODES.M) {
                            if (!Settings.System.canWrite(this)) {
                                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                                intent.setData(Uri.parse("package:com.mbanna.swiftbraille"));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Common.putSettingBoolean("preventKeyboardRotation", true);
                            }
                        } else {
                            Common.putSettingBoolean("preventKeyboardRotation", true);
                        }
                    } else {
                        Common.putSettingBoolean("preventKeyboardRotation", false);
                    }
                    Common.areSettingsChanged = true;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            String finalSelectedItem;
            switch (parent.getId()) {
                case R.id.stopOverDotSpinner:
                    finalSelectedItem = parent.getItemAtPosition(position).toString();
                    if (position == 0) {
                        Common.putSettingInt("stopOverDotTime", 0);
                    } else if (position == 1) {
                        Common.putSettingInt("stopOverDotTime", 500);
                    } else if (position == 2) {
                        Common.putSettingInt("stopOverDotTime", 1000);
                    }
                    Common.areSettingsChanged = true;
                    Common.defaultTextSpeech.speechText(finalSelectedItem);
                    break;

                case R.id.selectedDotsPeriodSpinner:
                    finalSelectedItem = parent.getItemAtPosition(position).toString();
                    if (position == 0) {
                        Common.putSettingInt("selectedDotsNumbersPeriod", 0);
                    } else if (position == 1) {
                        Common.putSettingInt("selectedDotsNumbersPeriod", 100);
                    } else if (position == 2) {
                        Common.putSettingInt("selectedDotsNumbersPeriod", 200);
                    } else if (position == 3) {
                        Common.putSettingInt("selectedDotsNumbersPeriod", 300);
                    } else if (position == 4) {
                        Common.putSettingInt("selectedDotsNumbersPeriod", 400);
                    } else if (position == 5) {
                        Common.putSettingInt("selectedDotsNumbersPeriod", 500);
                    } else if (position == 6) {
                        Common.putSettingInt("selectedDotsNumbersPeriod", 1000);
                    } else if (position == 7) {
                        Common.putSettingInt("selectedDotsNumbersPeriod", 1500);
                    } else if (position == 8) {
                        Common.putSettingInt("selectedDotsNumbersPeriod", 2000);
                    }
                    Common.areSettingsChanged = true;
                    Common.defaultTextSpeech.speechText(finalSelectedItem);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Nothing
    }
    //-------------------------------------------------------------------------//
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
    //-------------------------------------------------------------------------//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.howToItem:
                    Common.previousActivity = AdvancedActivity.class;
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
    //-------------------------------------------------------------------------//
    //Init listeners from spinners
    private void initListeners(final AdapterView.OnItemSelectedListener listener) {
        try{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopOverDotSpinner.setOnItemSelectedListener(listener);
                    selectedDotsPeriodSpinner.setOnItemSelectedListener(listener);
                }
            }, 500);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------//
    //Remove all listeners from spinners
    private void removeListeners() {
        try{
            stopOverDotSpinner.setOnItemSelectedListener(null);
            selectedDotsPeriodSpinner.setOnItemSelectedListener(null);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------//
    @Override
    public void onDestroy() {
        try {
            Common.speakHiddenKeyboard = true;
            //Refresh settings
            Common.refreshSettings(Common.areSettingsChanged);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
