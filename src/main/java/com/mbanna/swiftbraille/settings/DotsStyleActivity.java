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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.mbanna.swiftbraille.Common;
import com.mbanna.swiftbraille.R;
import com.mbanna.swiftbraille.WebViewActivity;
import com.mbanna.swiftbraille.colorpickerview.dialog.ColorPickerDialogFragment;

import java.util.ArrayList;
import java.util.Locale;

public class DotsStyleActivity extends AppCompatActivity implements ColorPickerDialogFragment.ColorPickerDialogListener, AdapterView.OnItemSelectedListener, CheckBox.OnCheckedChangeListener {
    Button dotFillColor, dotStrokeColor, resetSettingsBtn;
    Spinner dotRadiusPortraitSpinner, dotRadiusLandscapeSpinner;
    CheckBox fillDotOnTouch, viewBrailleDotNumber;
    ArrayAdapter<String> portraitDotRadiusAdapter, landscapeDotRadiusAdapter;

    //Default values
    int previousDotFillColor = 0;
    int previousDotStrokeColor = 0;
    int FILL_DOT_COLOR_DIALOG = 1;
    int STROKE_DOT_COLOR_DIALOG = 2;

    //-------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings_dots_style);

            //Show back button
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
            }

            //Speak the title of the activity
            Common.defaultTextSpeech.speechText(getString(R.string.dot_style));

            //Buttons declaration
            dotFillColor = (Button) findViewById(R.id.dotFillColor);
            previousDotFillColor = Common.dotFillColor;
            dotFillColor.setBackgroundColor(previousDotFillColor);
            dotStrokeColor = (Button) findViewById(R.id.dotStrokeColor);
            previousDotStrokeColor = Common.dotStrokeColor;
            dotStrokeColor.setBackgroundColor(previousDotStrokeColor);
            resetSettingsBtn = (Button) findViewById(R.id.resetSettingsBtn);

            //Spinner declaration
            dotRadiusPortraitSpinner = (Spinner) findViewById(R.id.dotRadiusPortraitSpinner);
            dotRadiusLandscapeSpinner = (Spinner) findViewById(R.id.dotRadiusLandscapeSpinner);

            //Portrait dots radius array list
            int thePortraitDefaultDotRadius = Common.getDefaultDotRadius(0);
            ArrayList<String> portraitDotRadiusList = new ArrayList<>();
            for (int x = Common.MIN_DOT_RADIUS; x <= (thePortraitDefaultDotRadius + 20); x++) {
                portraitDotRadiusList.add(String.format(Locale.getDefault(), "%d", x));
            }

            //Portrait dots radius adapter
            portraitDotRadiusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, portraitDotRadiusList);
            portraitDotRadiusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dotRadiusPortraitSpinner.setAdapter(portraitDotRadiusAdapter);
            dotRadiusPortraitSpinner.setSelection(portraitDotRadiusAdapter.getPosition(String.format(Locale.getDefault(), "%d", Common.getSettingInt("dotRadius", thePortraitDefaultDotRadius))), true);

            //Landscape dots radius array list
            int theLandscapeDefaultDotRadius = Common.getDefaultDotRadius(1);
            ArrayList<String> landscapeDotRadiusList = new ArrayList<>();
            for (int x = Common.MIN_DOT_RADIUS; x <= (theLandscapeDefaultDotRadius + 20); x++) {
                landscapeDotRadiusList.add(String.format(Locale.getDefault(), "%d", x));
            }

            //Landscape dots radius adapter
            landscapeDotRadiusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, landscapeDotRadiusList);
            landscapeDotRadiusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dotRadiusLandscapeSpinner.setAdapter(landscapeDotRadiusAdapter);
            dotRadiusLandscapeSpinner.setSelection(landscapeDotRadiusAdapter.getPosition(String.format(Locale.getDefault(), "%d", Common.getSettingInt("dotLandscapeRadius", theLandscapeDefaultDotRadius))), true);

            //Switches declaration
            fillDotOnTouch = (CheckBox) findViewById(R.id.fillDotOnTouch);
            fillDotOnTouch.setChecked(Common.fillDotOnTouch);
            fillDotOnTouch.setOnCheckedChangeListener(this);
            viewBrailleDotNumber = (CheckBox) findViewById(R.id.viewBrailleDotNumber);
            viewBrailleDotNumber.setChecked(Common.viewBrailleDotNumber);

            //Add listeners to spinners
            initListeners(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    public void onDotFillColorPickerAction(View v) {
        try {
            //Get stored fill color
            ColorPickerDialogFragment f = ColorPickerDialogFragment.newInstance(FILL_DOT_COLOR_DIALOG,
                    getString(R.string.color_picker_fill_dot_title), getString(R.string.color_picker_select_color), previousDotFillColor, true);
            f.show(getFragmentManager(), "dotFillColorDialog");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    public void onDotStrokeColorPickerAction(View v) {
        try {
            //Get stored stroke color
            ColorPickerDialogFragment f = ColorPickerDialogFragment.newInstance(STROKE_DOT_COLOR_DIALOG,
                    getString(R.string.color_picker_stroke_dot_title), getString(R.string.color_picker_select_color), previousDotStrokeColor, true);
            f.show(getFragmentManager(), "dotStrokeColorDialog");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    public void onResetSettingsBtnAction(View v) {
        try {
            //I have to remove the listener, if I setSelect it will fire the event again
            removeListeners();

            //Save all default values
            Common.putSettingInt("dotFillColor", Common.getMyColor(R.color.defaultDotFillColor));
            Common.putSettingInt("dotStrokeColor", Common.getMyColor(R.color.defaultDotStrokeColor));
            int thePortraitDefaultDotRadius = Common.getDefaultDotRadius(0);
            Common.putSettingInt("dotRadius", thePortraitDefaultDotRadius);
            int theLandscapeDefaultDotRadius = Common.getDefaultDotRadius(1);
            Common.putSettingInt("dotLandscapeRadius", theLandscapeDefaultDotRadius);
            Common.putSettingBoolean("fillDotOnTouch", Common.DEFAULT_FILL_DOT_ON_TOUCH);
            Common.putSettingBoolean("viewBrailleDotNumber", Common.DEFAULT_VIEW_DOT_NUMBER);

            //Apply the changes on current screen
            previousDotFillColor = Common.getMyColor(R.color.defaultDotFillColor);
            previousDotStrokeColor = Common.getMyColor(R.color.defaultDotStrokeColor);
            dotFillColor.setBackgroundColor(Common.getMyColor(R.color.defaultDotFillColor));
            fillDotOnTouch.setChecked(Common.DEFAULT_FILL_DOT_ON_TOUCH);
            viewBrailleDotNumber.setChecked(Common.DEFAULT_VIEW_DOT_NUMBER);
            dotStrokeColor.setBackgroundColor(Common.getMyColor(R.color.defaultDotStrokeColor));
            dotRadiusPortraitSpinner.setSelection(portraitDotRadiusAdapter.getPosition(String.format(Locale.getDefault(), "%d", thePortraitDefaultDotRadius)), true);
            dotRadiusLandscapeSpinner.setSelection(landscapeDotRadiusAdapter.getPosition(String.format(Locale.getDefault(), "%d", theLandscapeDefaultDotRadius)), true);

            Common.areSettingsChanged = true;

            if (!Common.defaultTextSpeech.isArabicSupported() && Common.currentSystemLanguage.equals("ar") && Common.getSoundPath("ar_message_reset_settings") != -1) {
                Common.runPatternSoundPronounce(Common.getSoundPath("ar_message_reset_settings"), true);
            } else {
                Common.defaultTextSpeech.speechText(getString(R.string.reset_settings_done), true);
            }

            //Back the listener again
            initListeners(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    @Override
    public void onColorSelected(int dialogId, int color) {
        try {
            switch (dialogId) {
                //Fill Color Dialog
                case 1:
                    previousDotFillColor = color;
                    Common.putSettingInt("dotFillColor", color);
                    Common.areSettingsChanged = true;
                    dotFillColor.setBackgroundColor(color);
                    break;
                //Stroke Color Dialog
                case 2:
                    previousDotStrokeColor = color;
                    Common.putSettingInt("dotStrokeColor", color);
                    Common.areSettingsChanged = true;
                    dotStrokeColor.setBackgroundColor(color);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    @Override
    public void onDialogDismissed(int dialogId) {
        //Do nothing
    }

    //-------------------------------------------------------------------------//
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        try {
            switch (buttonView.getId()) {
                case R.id.fillDotOnTouch:
                    if (isChecked) {
                        Common.putSettingBoolean("fillDotOnTouch", true);
                    } else {
                        Common.putSettingBoolean("fillDotOnTouch", false);
                    }
                    Common.areSettingsChanged = true;
                    break;
                case R.id.viewBrailleDotNumber:
                    if (isChecked) {
                        Common.putSettingBoolean("viewBrailleDotNumber", true);
                    } else {
                        Common.putSettingBoolean("viewBrailleDotNumber", false);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        try {
            String finalSelectedItem;
            switch (parent.getId()) {
                case R.id.dotRadiusPortraitSpinner:
                    finalSelectedItem = parent.getItemAtPosition(position).toString();
                    Common.putSettingInt("dotRadius", Integer.valueOf(finalSelectedItem));
                    Common.areSettingsChanged = true;
                    Common.defaultTextSpeech.speechText(finalSelectedItem);
                    break;
                case R.id.dotRadiusLandscapeSpinner:
                    finalSelectedItem = parent.getItemAtPosition(position).toString();
                    Common.putSettingInt("dotLandscapeRadius", Integer.valueOf(finalSelectedItem));
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
        //Do nothing
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
                    Common.previousActivity = DotsLayoutActivity.class;
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
                    dotRadiusPortraitSpinner.setOnItemSelectedListener(listener);
                    dotRadiusLandscapeSpinner.setOnItemSelectedListener(listener);
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
            dotRadiusPortraitSpinner.setOnItemSelectedListener(null);
            dotRadiusLandscapeSpinner.setOnItemSelectedListener(null);
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
