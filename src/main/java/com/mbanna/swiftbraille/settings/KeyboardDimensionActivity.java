package com.mbanna.swiftbraille.settings;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.CheckBox;
import com.mbanna.swiftbraille.Common;
import com.mbanna.swiftbraille.R;
import com.mbanna.swiftbraille.WebViewActivity;

import java.util.ArrayList;
import java.util.Locale;

public class KeyboardDimensionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CheckBox.OnCheckedChangeListener {
    Spinner keyboardPortraitHeight, keyboardLandscapeHeight, keyboardPortraitWidth, keyboardLandscapeWidth;
    ArrayAdapter keyboardPortraitHeightSpinnerAdapter, keyboardLandscapeHeightSpinnerAdapter,
            keyboardPortraitWidthSpinnerAdapter, keyboardLandscapeWidthSpinnerAdapter;
    int selectedDotsLayout;
    CheckBox startKeyboardFromRight;

    //--------------------------------------------------------------------------------------------//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings_keyboard_dimension);

            //Show back button
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
            }

            //Speak the title of the activity
            Common.defaultTextSpeech.speechText(getString(R.string.keyboard_dimensions));

            //Views declarations
            keyboardPortraitHeight = (Spinner) findViewById(R.id.keyboardPortraitHeight);
            keyboardLandscapeHeight = (Spinner) findViewById(R.id.keyboardLandscapeHeight);
            keyboardPortraitWidth = (Spinner) findViewById(R.id.keyboardPortraitWidth);
            keyboardLandscapeWidth = (Spinner) findViewById(R.id.keyboardLandscapeWidth);
            startKeyboardFromRight = (CheckBox) findViewById(R.id.startKeyboardFromRight);
            selectedDotsLayout = Common.selectedDotsLayout;
            startKeyboardFromRight.setChecked(Common.startKeyboardContainerFromRight);
            startKeyboardFromRight.setOnCheckedChangeListener(this);

            //Hide this option if not tablet or perkins dots layout
            if (!Common.isTablet || Common.selectedDotsLayout == Common.PERKINS_DOTS_LAYOUT) {
                startKeyboardFromRight.setVisibility(View.GONE);
            }

            //Selected keyboard height for portrait mode
            ArrayList<String> portraitHeightsList = new ArrayList<>();
            portraitHeightsList.add(String.format(Locale.getDefault(), "%d", 50) + "%");
            portraitHeightsList.add(String.format(Locale.getDefault(), "%d", 70) + "%");
            portraitHeightsList.add(String.format(Locale.getDefault(), "%d", 100) + "%");

            //Selected keyboard height for landscape
            ArrayList<String> landscapeHeightsList = new ArrayList<>();
            if(Common.isTablet){
                landscapeHeightsList.add(String.format(Locale.getDefault(), "%d", 50) + "%");
            }

            landscapeHeightsList.add(String.format(Locale.getDefault(), "%d", 70) + "%");
            landscapeHeightsList.add(String.format(Locale.getDefault(), "%d", 100) + "%");

            //Set the adapter for keyboard height in portrait mode
            keyboardPortraitHeightSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, portraitHeightsList);
            keyboardPortraitHeightSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            keyboardPortraitHeight.setAdapter(keyboardPortraitHeightSpinnerAdapter);
            keyboardPortraitHeight.setSelection(Common.getIndexFromSpinnerByValue(keyboardPortraitHeight,String.format(Locale.getDefault(), "%d", Common.keyboardHeightPortrait) + "%")  , true);


            if(selectedDotsLayout == Common.PERKINS_DOTS_LAYOUT && !Common.isTablet){
                LinearLayout keyboardPortraitHeightContainer = (LinearLayout) findViewById(R.id.keyboardPortraitHeightContainer);
                keyboardPortraitHeightContainer.setVisibility(View.GONE);
            }

            //Set the adapter for keyboard height in landscape mode
            keyboardLandscapeHeightSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, landscapeHeightsList);
            keyboardLandscapeHeightSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            keyboardLandscapeHeight.setAdapter(keyboardLandscapeHeightSpinnerAdapter);
            keyboardLandscapeHeight.setSelection(Common.getIndexFromSpinnerByValue(keyboardLandscapeHeight,String.format(Locale.getDefault(), "%d", Common.keyboardHeightLandscape) + "%")  , true);

            //Is tablet to change the width of the keyboard and not perkins
            if(Common.isTablet && selectedDotsLayout != Common.PERKINS_DOTS_LAYOUT){
                //Selected keyboard width for portrait mode
                ArrayList<String> portraitWidthsList = new ArrayList<>();
                portraitWidthsList.add(String.format(Locale.getDefault(), "%d", 50) + "%");
                portraitWidthsList.add(String.format(Locale.getDefault(), "%d", 70) + "%");
                portraitWidthsList.add(String.format(Locale.getDefault(), "%d", 100) + "%");

                //Selected keyboard width for landscape
                ArrayList<String> landscapeWidthList = new ArrayList<>();
                landscapeWidthList.add(String.format(Locale.getDefault(), "%d", 50) + "%");
                landscapeWidthList.add(String.format(Locale.getDefault(), "%d", 70) + "%");
                landscapeWidthList.add(String.format(Locale.getDefault(), "%d", 100) + "%");

                //Set the adapter for keyboard width in portrait mode
                keyboardPortraitWidthSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, portraitWidthsList);
                keyboardPortraitWidthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                keyboardPortraitWidth.setAdapter(keyboardPortraitWidthSpinnerAdapter);
                keyboardPortraitWidth.setSelection(Common.getIndexFromSpinnerByValue(keyboardPortraitWidth,String.format(Locale.getDefault(), "%d", Common.keyboardWidthPortrait) + "%")  , true);

                //Set the adapter for keyboard height in landscape mode
                keyboardLandscapeWidthSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, landscapeWidthList);
                keyboardLandscapeWidthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                keyboardLandscapeWidth.setAdapter(keyboardLandscapeWidthSpinnerAdapter);
                keyboardLandscapeWidth.setSelection(Common.getIndexFromSpinnerByValue(keyboardLandscapeWidth,String.format(Locale.getDefault(), "%d", Common.keyboardWidthLandscape) + "%")  , true);
            } else {
                //Hide the keyboard width settings if not a tablet
                LinearLayout keyboardWidthContainer = (LinearLayout)findViewById(R.id.keyboardWidthContainer);
                keyboardWidthContainer.setVisibility(View.GONE);
            }

            //Init listeners
            initListeners(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void onResetSettingsBtnAction(View v) {
        try {
            //Remove spinner listeners
            removeListeners();

            Common.putSettingInt("defaultKeyboardHeight", Common.DEFAULT_PORTRAIT_KEYBOARD_HEIGHT);
            Common.putSettingInt("defaultKeyboardLandscapeHeight", Common.DEFAULT_LANDSCAPE_KEYBOARD_HEIGHT);
            Common.putSettingInt("defaultKeyboardWidth", Common.DEFAULT_PORTRAIT_KEYBOARD_WIDTH);
            Common.putSettingInt("defaultKeyboardLandscapeWidth", Common.DEFAULT_LANDSCAPE_KEYBOARD_WIDTH);
            Common.putSettingBoolean("startKeyboardFromRight", Common.DEFAULT_KEYBOARD_RIGHT_POSITION);

            keyboardPortraitHeight.setSelection(Common.getIndexFromSpinnerByValue(keyboardPortraitHeight,String.format(Locale.getDefault(), "%d", Common.DEFAULT_PORTRAIT_KEYBOARD_HEIGHT) + "%")  , true);
            keyboardLandscapeHeight.setSelection(Common.getIndexFromSpinnerByValue(keyboardLandscapeHeight,String.format(Locale.getDefault(), "%d", Common.DEFAULT_LANDSCAPE_KEYBOARD_HEIGHT) + "%")  , true);

            if(Common.isTablet && selectedDotsLayout != Common.PERKINS_DOTS_LAYOUT){
                keyboardPortraitWidth.setSelection(Common.getIndexFromSpinnerByValue(keyboardPortraitWidth,String.format(Locale.getDefault(), "%d", Common.DEFAULT_PORTRAIT_KEYBOARD_WIDTH) + "%")  , true);
                keyboardLandscapeWidth.setSelection(Common.getIndexFromSpinnerByValue(keyboardLandscapeWidth,String.format(Locale.getDefault(), "%d", Common.DEFAULT_LANDSCAPE_KEYBOARD_WIDTH) + "%")  , true);
            }

            Common.areSettingsChanged = true;

            if (!Common.defaultTextSpeech.isArabicSupported() && Common.currentSystemLanguage.equals("ar") && Common.getSoundPath("ar_message_reset_settings") != -1) {
                Common.runPatternSoundPronounce(Common.getSoundPath("ar_message_reset_settings"), true);
            } else {
                Common.defaultTextSpeech.speechText(getString(R.string.reset_settings_done), true);
            }

            //Back the listeners
           initListeners(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            String finalSelectedItem;
            switch (parent.getId()) {
                case R.id.keyboardPortraitHeight:
                    finalSelectedItem = parent.getItemAtPosition(position).toString();
                    Common.putSettingInt("defaultKeyboardHeight", getIntDimFromString(finalSelectedItem));
                    //Change the dots radius
                    Common.putSettingInt("dotRadius", Common.getDefaultDotRadius(0));
                    Common.areSettingsChanged = true;
                    Common.defaultTextSpeech.speechText(finalSelectedItem);
                    break;
                case R.id.keyboardLandscapeHeight:
                    finalSelectedItem = parent.getItemAtPosition(position).toString();
                    Common.putSettingInt("defaultKeyboardLandscapeHeight", getIntDimFromString(finalSelectedItem));
                    //Change the dots radius
                    Common.putSettingInt("dotLandscapeRadius", Common.getDefaultDotRadius(1));
                    Common.areSettingsChanged = true;
                    Common.defaultTextSpeech.speechText(finalSelectedItem);
                    break;
                case R.id.keyboardPortraitWidth:
                    finalSelectedItem = parent.getItemAtPosition(position).toString();
                    Common.putSettingInt("defaultKeyboardWidth", getIntDimFromString(finalSelectedItem));
                    //Change the dots radius
                    Common.putSettingInt("dotRadius", Common.getDefaultDotRadius(0));
                    Common.areSettingsChanged = true;
                    Common.defaultTextSpeech.speechText(finalSelectedItem);
                    break;
                case R.id.keyboardLandscapeWidth:
                    finalSelectedItem = parent.getItemAtPosition(position).toString();
                    Common.putSettingInt("defaultKeyboardLandscapeWidth", getIntDimFromString(finalSelectedItem));
                    //Change the dots radius
                    Common.putSettingInt("dotLandscapeRadius", Common.getDefaultDotRadius(1));
                    Common.areSettingsChanged = true;
                    Common.defaultTextSpeech.speechText(finalSelectedItem);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        try{
            switch (buttonView.getId()) {
                case R.id.startKeyboardFromRight:
                    if (isChecked) {
                        Common.putSettingBoolean("startKeyboardFromRight", true);
                    } else {
                        Common.putSettingBoolean("startKeyboardFromRight", false);
                    }
                    Common.areSettingsChanged = true;
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
                    Common.previousActivity = KeyboardDimensionActivity.class;
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
    //--------------------------------------------------------------------------------------------//
    //Init listeners from spinners
    private void initListeners(final AdapterView.OnItemSelectedListener listener) {
        try{
            //Now, back the listeners after one second
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    keyboardPortraitHeight.setOnItemSelectedListener(listener);
                    keyboardLandscapeHeight.setOnItemSelectedListener(listener);
                    keyboardPortraitWidth.setOnItemSelectedListener(listener);
                    keyboardLandscapeWidth.setOnItemSelectedListener(listener);
                }
            }, 500);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------//
    //Remove all listeners from spinners
    private void removeListeners() {
        try{
            keyboardPortraitHeight.setOnItemSelectedListener(null);
            keyboardLandscapeHeight.setOnItemSelectedListener(null);
            keyboardPortraitWidth.setOnItemSelectedListener(null);
            keyboardLandscapeWidth.setOnItemSelectedListener(null);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------//
    private int getIntDimFromString(String percentNumber){
        try{
            return Integer.parseInt(percentNumber.replace("%",""));
        } catch(Exception e){
            e.printStackTrace();
            return 1;
        }
    }
}
