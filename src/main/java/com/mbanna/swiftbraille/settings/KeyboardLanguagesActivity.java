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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.mbanna.swiftbraille.Common;
import com.mbanna.swiftbraille.R;
import com.mbanna.swiftbraille.WebViewActivity;

import java.util.ArrayList;

@SuppressWarnings("all")
public class KeyboardLanguagesActivity extends AppCompatActivity implements CheckBox.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    CheckBox arabicLanguage, englishLanguage, spanishLanguage, frenchLanguage;
    Spinner defaultKeyboardLanguageSpinner;
    ArrayAdapter defaultKeyboardLanguageAdapter;
    ArrayList<Integer> defaultSelectedLanguagesTags;
    ArrayList<String> defaultSelectedLanguages;

    //-------------------------------------------------------------------------//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings_keyboard_languages);

            //Show back button
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
            }

            //Speak the title of the activity
            Common.defaultTextSpeech.speechText(getString(R.string.keyboard_languages));

            //Declarations
            defaultKeyboardLanguageSpinner = (Spinner) findViewById(R.id.defaultKeyboardLanguageSpinner);

            arabicLanguage = (CheckBox) findViewById(R.id.arabicLanguage);
            arabicLanguage.setChecked(Common.isArabicInputMethodActivated);
            arabicLanguage.setOnCheckedChangeListener(this);

            englishLanguage = (CheckBox) findViewById(R.id.englishLanguage);
            englishLanguage.setChecked(Common.isEnglishInputMethodActivated);
            englishLanguage.setOnCheckedChangeListener(this);

            spanishLanguage = (CheckBox) findViewById(R.id.spanishLanguage);
            spanishLanguage.setChecked(Common.isSpanishInputMethodActivated);
            spanishLanguage.setOnCheckedChangeListener(this);

            frenchLanguage = (CheckBox) findViewById(R.id.frenchLanguage);
            frenchLanguage.setChecked(Common.isFrenchInputMethodActivated);
            frenchLanguage.setOnCheckedChangeListener(this);

            //Set all check languages in the default list
            defaultSelectedLanguages = new ArrayList<>();
            defaultSelectedLanguagesTags = new ArrayList<>();
            if (englishLanguage.isChecked()) {
                defaultSelectedLanguages.add(englishLanguage.getText().toString());
                defaultSelectedLanguagesTags.add(Integer.valueOf((String) englishLanguage.getTag()));
            }

            if (arabicLanguage.isChecked()) {
                defaultSelectedLanguages.add(arabicLanguage.getText().toString());
                defaultSelectedLanguagesTags.add(Integer.valueOf((String) arabicLanguage.getTag()));
            }

            if (spanishLanguage.isChecked()) {
                defaultSelectedLanguages.add(spanishLanguage.getText().toString());
                defaultSelectedLanguagesTags.add(Integer.valueOf((String) spanishLanguage.getTag()));
            }

            if (frenchLanguage.isChecked()) {
                defaultSelectedLanguages.add(frenchLanguage.getText().toString());
                defaultSelectedLanguagesTags.add(Integer.valueOf((String) frenchLanguage.getTag()));
            }

            defaultKeyboardLanguageAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, defaultSelectedLanguages);
            defaultKeyboardLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            defaultKeyboardLanguageSpinner.setAdapter(defaultKeyboardLanguageAdapter);

            //Set selection of current default keyboard language
            //English
            int defaultKeyboardLangNumber = Common.defaultKeyboardLanguage;
            if(defaultKeyboardLangNumber == Common.ENGLISH_LANGUAGE_INPUT_METHOD){
                defaultKeyboardLanguageSpinner.setSelection(Common.getIndexFromSpinnerByValue(defaultKeyboardLanguageSpinner, englishLanguage.getText().toString()), true);
            } //Arabic
            else if(defaultKeyboardLangNumber == Common.ARABIC_LANGUAGE_INPUT_METHOD){
                defaultKeyboardLanguageSpinner.setSelection(Common.getIndexFromSpinnerByValue(defaultKeyboardLanguageSpinner, arabicLanguage.getText().toString()), true);
            } //Spanish
            else if(defaultKeyboardLangNumber == Common.SPANISH_LANGUAGE_INPUT_METHOD){
                defaultKeyboardLanguageSpinner.setSelection(Common.getIndexFromSpinnerByValue(defaultKeyboardLanguageSpinner, spanishLanguage.getText().toString()), true);
            } //French
            else if(defaultKeyboardLangNumber == Common.FRENCH_LANGUAGE_INPUT_METHOD){
                defaultKeyboardLanguageSpinner.setSelection(Common.getIndexFromSpinnerByValue(defaultKeyboardLanguageSpinner, frenchLanguage.getText().toString()), true);
            }

            //Initilaize the listeners
            initListeners(this);
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
                case R.id.defaultKeyboardLanguageSpinner:
                    finalSelectedItem = parent.getItemAtPosition(position).toString();
                    Common.defaultTextSpeech.speechText(finalSelectedItem);
                    Common.putSettingInt("defaultKeyboardLanguage", defaultSelectedLanguagesTags.get(position));
                    Common.areSettingsChanged = true;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        try {
            //Check if all languages not selected
            if(isNonLanguageSelected()){
                Common.showGotItAlertDialog(this, getString(R.string.caution), getString(R.string.one_lang_must_selected));
                buttonView.setChecked(true);
                return;
            }

            //Handle checks events
            switch (buttonView.getId()) {
                case R.id.arabicLanguage:
                    if (isChecked) {
                        Common.putSettingBoolean("arabicInputMethod", true);
                        defaultSelectedLanguages.add(arabicLanguage.getText().toString());
                        defaultSelectedLanguagesTags.add(Integer.valueOf((String) arabicLanguage.getTag()));
                    } else {
                        Common.putSettingBoolean("arabicInputMethod", false);
                        defaultSelectedLanguages.remove(arabicLanguage.getText().toString());
                        defaultSelectedLanguagesTags.remove(Integer.valueOf((String) arabicLanguage.getTag()));
                        reSaveDefaultKeyboardLanguage(Integer.valueOf((String) arabicLanguage.getTag()));
                    }
                    defaultKeyboardLanguageAdapter.notifyDataSetChanged();
                    Common.areSettingsChanged = true;
                    break;

                case R.id.englishLanguage:
                    if (isChecked) {
                        Common.putSettingBoolean("englishInputMethod", true);
                        defaultSelectedLanguages.add(englishLanguage.getText().toString());
                        defaultSelectedLanguagesTags.add(Integer.valueOf((String) englishLanguage.getTag()));
                    } else {
                        Common.putSettingBoolean("englishInputMethod", false);
                        defaultSelectedLanguages.remove(englishLanguage.getText().toString());
                        defaultSelectedLanguagesTags.remove(Integer.valueOf((String) englishLanguage.getTag()));
                        reSaveDefaultKeyboardLanguage(Integer.valueOf((String) englishLanguage.getTag()));
                    }
                    defaultKeyboardLanguageAdapter.notifyDataSetChanged();
                    Common.areSettingsChanged = true;
                    break;

                case R.id.spanishLanguage:
                    if (isChecked) {
                        Common.putSettingBoolean("spanishInputMethod", true);
                        defaultSelectedLanguages.add(spanishLanguage.getText().toString());
                        defaultSelectedLanguagesTags.add(Integer.valueOf((String) spanishLanguage.getTag()));
                    } else {
                        Common.putSettingBoolean("spanishInputMethod", false);
                        defaultSelectedLanguages.remove(spanishLanguage.getText().toString());
                        defaultSelectedLanguagesTags.remove(Integer.valueOf((String) spanishLanguage.getTag()));
                        reSaveDefaultKeyboardLanguage(Integer.valueOf((String) spanishLanguage.getTag()));
                    }
                    defaultKeyboardLanguageAdapter.notifyDataSetChanged();
                    Common.areSettingsChanged = true;
                    break;

                case R.id.frenchLanguage:
                    if (isChecked) {
                        Common.putSettingBoolean("frenchInputMethod", true);
                        defaultSelectedLanguages.add(frenchLanguage.getText().toString());
                        defaultSelectedLanguagesTags.add(Integer.valueOf((String) frenchLanguage.getTag()));
                    } else {
                        Common.putSettingBoolean("frenchInputMethod", false);
                        defaultSelectedLanguages.remove(frenchLanguage.getText().toString());
                        defaultSelectedLanguagesTags.remove(Integer.valueOf((String) frenchLanguage.getTag()));
                        reSaveDefaultKeyboardLanguage(Integer.valueOf((String) frenchLanguage.getTag()));
                    }
                    defaultKeyboardLanguageAdapter.notifyDataSetChanged();
                    Common.areSettingsChanged = true;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.settings_menu, menu);
        } catch (Exception e) {
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
                    Common.previousActivity = KeyboardLanguagesActivity.class;
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
    //Check if not language is selected
    private boolean isNonLanguageSelected(){
        return  !englishLanguage.isChecked() && !arabicLanguage.isChecked() && !spanishLanguage.isChecked() && !frenchLanguage.isChecked();
    }
    //-------------------------------------------------------------------------//
    //When the user changes the activated input methods, what if unchecked the default language
    private void reSaveDefaultKeyboardLanguage(int unChecked){
        try{
            if(unChecked == Common.defaultKeyboardLanguage){
                //Remove listeners
                removeListeners();

                defaultKeyboardLanguageAdapter.notifyDataSetChanged();
                if(englishLanguage.isChecked()){
                    Common.putSettingInt("defaultKeyboardLanguage", Common.ENGLISH_LANGUAGE_INPUT_METHOD);
                    defaultKeyboardLanguageSpinner.setSelection(Common.getIndexFromSpinnerByValue(defaultKeyboardLanguageSpinner, englishLanguage.getText().toString()), true);
                } else if(arabicLanguage.isChecked()){
                    Common.putSettingInt("defaultKeyboardLanguage", Common.ARABIC_LANGUAGE_INPUT_METHOD);
                    defaultKeyboardLanguageSpinner.setSelection(Common.getIndexFromSpinnerByValue(defaultKeyboardLanguageSpinner, arabicLanguage.getText().toString()), true);
                } else if(spanishLanguage.isChecked()){
                    Common.putSettingInt("defaultKeyboardLanguage", Common.SPANISH_LANGUAGE_INPUT_METHOD);
                    defaultKeyboardLanguageSpinner.setSelection(Common.getIndexFromSpinnerByValue(defaultKeyboardLanguageSpinner, spanishLanguage.getText().toString()), true);
                } else if(frenchLanguage.isChecked()){
                    Common.putSettingInt("defaultKeyboardLanguage", Common.FRENCH_LANGUAGE_INPUT_METHOD);
                    defaultKeyboardLanguageSpinner.setSelection(Common.getIndexFromSpinnerByValue(defaultKeyboardLanguageSpinner, frenchLanguage.getText().toString()), true);
                }

                //Re-initialize listeners to spinners
                initListeners(this);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------//
    //Init listeners from spinners
    private void initListeners(final AdapterView.OnItemSelectedListener listener) {
        try{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    defaultKeyboardLanguageSpinner.setOnItemSelectedListener(listener);
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
            defaultKeyboardLanguageSpinner.setOnItemSelectedListener(null);
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

    //-------------------------------------------------------------------------//
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Do nothing
    }
}
