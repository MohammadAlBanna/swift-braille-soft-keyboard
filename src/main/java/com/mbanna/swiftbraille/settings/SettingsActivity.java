package com.mbanna.swiftbraille.settings;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.mbanna.swiftbraille.Common;
import com.mbanna.swiftbraille.R;
import com.mbanna.swiftbraille.WebViewActivity;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);

            //Show back button
            android.support.v7.app.ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
            }

            //If the user changes the current system language
            Common.currentSystemLanguage = Locale.getDefault().getLanguage();

            //Speech the screen title
            if (!Common.defaultTextSpeech.isArabicSupported() && Common.currentSystemLanguage.equals("ar") && Common.getSoundPath("ar_message_settings_activity") != -1) {
                Common.runPatternSoundPronounce(Common.getSoundPath("ar_message_settings_activity"), true);
            } else {
                Common.defaultTextSpeech.speechText(getString(R.string.settings_title));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    Common.previousActivity = SettingsActivity.class;
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
    public void onDotsLayoutItemAction(View v){
        try{
            startActivity(new Intent(this, DotsLayoutActivity.class));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------//
    public void onDotsStyleItemAction(View v){
        try{
            startActivity(new Intent(this, DotsStyleActivity.class));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------//
    public void onKeyboardLanguagesItemAction(View v){
        try{
            startActivity(new Intent(this, KeyboardLanguagesActivity.class));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------//
    public void onKeyboardDimensionItemAction(View v){
        try{
            startActivity(new Intent(this, KeyboardDimensionActivity.class));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------//
    public void onSoundsItemAction(View v){
        try{
            startActivity(new Intent(this, SoundsActivity.class));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------//
    public void onAdvancedItemAction(View v){
        try{
            startActivity(new Intent(this, AdvancedActivity.class));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------//
    @SuppressWarnings("All")
    public void onSpecialToolsItemAction(View v){
        try{
            Common.showGotItAlertDialog(this, getString(R.string.coming_soon_title), getString(R.string.special_tools_coming_soon));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------//
    public void onAboutItemAction(View v){
        try{
            Common.previousActivity = SettingsActivity.class;
            startActivity(new Intent(this, AboutActivity.class));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------//
    public void onResetSettingsBtnAction(View v){
        try{
            Common.resetAllSettings();
            Common.areSettingsChanged = true;
            if (!Common.defaultTextSpeech.isArabicSupported() && Common.currentSystemLanguage.equals("ar") && Common.getSoundPath("ar_message_reset_settings") != -1) {
                Common.runPatternSoundPronounce(Common.getSoundPath("ar_message_reset_settings"), true);
            } else {
                Common.defaultTextSpeech.speechText(getString(R.string.reset_settings_done), true);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onDestroy() {
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
