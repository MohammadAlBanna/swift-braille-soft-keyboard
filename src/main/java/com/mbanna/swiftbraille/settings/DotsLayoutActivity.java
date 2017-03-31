package com.mbanna.swiftbraille.settings;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mbanna.swiftbraille.Common;
import com.mbanna.swiftbraille.R;
import com.mbanna.swiftbraille.WebViewActivity;

public class DotsLayoutActivity extends AppCompatActivity {
    ImageView brailleCellCheckBox, perkinsLayoutCheckBox, twoDotsLayoutCheckBox;
    AlertDialog moreDetailsDialog = null;
    TextView deviceSupportsMultiTouch, deviceDoesNotSupportsMultiTouch;
    boolean hasSixMultiTouch = false;

    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings_dots_layout);

            //Show back button
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
            }

            //Speak the title of the activity
            Common.defaultTextSpeech.speechText(getString(R.string.dots_layout));

            //Declaration
            brailleCellCheckBox = (ImageView) findViewById(R.id.brailleCellCheckBox);
            perkinsLayoutCheckBox = (ImageView) findViewById(R.id.perkinsLayoutCheckBox);
            twoDotsLayoutCheckBox = (ImageView) findViewById(R.id.twoDotsLayoutCheckBox);

            //Check if multi-touch supported
            PackageManager pm = getPackageManager();
            hasSixMultiTouch = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND);

            //Load default layout
            int selectedDotsLayout = Common.selectedDotsLayout;
            if (selectedDotsLayout == Common.BRAILLE_CELL_DOTS_LAYOUT) {
                brailleCellCheckBox.setImageResource(R.mipmap.ic_radio_button_checked_green_24dp);
                brailleCellCheckBox.setContentDescription(getString(R.string.braille_cell_layout_selected));

                perkinsLayoutCheckBox.setImageResource(R.mipmap.ic_radio_button_unchecked_green_24dp);
                perkinsLayoutCheckBox.setContentDescription(getString(R.string.perkins_layout_not_selected));

                twoDotsLayoutCheckBox.setImageResource(R.mipmap.ic_radio_button_unchecked_green_24dp);
                twoDotsLayoutCheckBox.setContentDescription(getString(R.string.two_rows_layout_not_selected));
            } else if (selectedDotsLayout == Common.PERKINS_DOTS_LAYOUT) {
                brailleCellCheckBox.setImageResource(R.mipmap.ic_radio_button_unchecked_green_24dp);
                brailleCellCheckBox.setContentDescription(getString(R.string.braille_cell_layout_not_selected));

                perkinsLayoutCheckBox.setImageResource(R.mipmap.ic_radio_button_checked_green_24dp);
                perkinsLayoutCheckBox.setContentDescription(getString(R.string.perkins_layout_selected));

                twoDotsLayoutCheckBox.setImageResource(R.mipmap.ic_radio_button_unchecked_green_24dp);
                twoDotsLayoutCheckBox.setContentDescription(getString(R.string.two_rows_layout_not_selected));
            } else if (selectedDotsLayout == Common.TWO_ROWS_DOTS_LAYOUT) {
                brailleCellCheckBox.setImageResource(R.mipmap.ic_radio_button_unchecked_green_24dp);
                brailleCellCheckBox.setContentDescription(getString(R.string.braille_cell_layout_not_selected));

                perkinsLayoutCheckBox.setImageResource(R.mipmap.ic_radio_button_unchecked_green_24dp);
                perkinsLayoutCheckBox.setContentDescription(getString(R.string.perkins_layout_not_selected));

                twoDotsLayoutCheckBox.setImageResource(R.mipmap.ic_radio_button_checked_green_24dp);
                twoDotsLayoutCheckBox.setContentDescription(getString(R.string.two_rows_layout_selected));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void brailleCellLayoutSelectedAction(View v) {
        try {
            brailleCellCheckBox.setImageResource(R.mipmap.ic_radio_button_checked_green_24dp);
            brailleCellCheckBox.setContentDescription(getString(R.string.braille_cell_layout_selected));

            //Speech it
            Common.defaultTextSpeech.speechText(getString(R.string.braille_cell_layout_selected));

            perkinsLayoutCheckBox.setImageResource(R.mipmap.ic_radio_button_unchecked_green_24dp);
            perkinsLayoutCheckBox.setContentDescription(getString(R.string.perkins_layout_not_selected));

            twoDotsLayoutCheckBox.setImageResource(R.mipmap.ic_radio_button_unchecked_green_24dp);
            twoDotsLayoutCheckBox.setContentDescription(getString(R.string.two_rows_layout_not_selected));

            //Save settings
            Common.putSettingInt("selectedDotsLayout", Common.BRAILLE_CELL_DOTS_LAYOUT);

            //Back 2-5 dots to be default
            Common.putSettingBoolean("makeDot2And5Higher", Common.DEFAULT_MAKE_DOT_2_5_HIGHER);

            //Change the height of the keyboard
            Common.putSettingInt("defaultKeyboardHeight", Common.DEFAULT_PORTRAIT_KEYBOARD_HEIGHT);
            Common.putSettingInt("defaultKeyboardLandscapeHeight", Common.DEFAULT_LANDSCAPE_KEYBOARD_HEIGHT);
            Common.putSettingInt("defaultKeyboardWidth", Common.DEFAULT_PORTRAIT_KEYBOARD_WIDTH);
            Common.putSettingInt("defaultKeyboardLandscapeWidth", Common.DEFAULT_LANDSCAPE_KEYBOARD_WIDTH);

            //Change the dot radius the default
            Common.putSettingInt("dotRadius", Common.getDefaultDotRadius(0));
            Common.putSettingInt("dotLandscapeRadius", Common.getDefaultDotRadius(1));

            //Let Swift knows settings are changed
            Common.areSettingsChanged = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void perkinsLayoutSelectedAction(View v) {
        try {
            brailleCellCheckBox.setImageResource(R.mipmap.ic_radio_button_unchecked_green_24dp);
            brailleCellCheckBox.setContentDescription(getString(R.string.braille_cell_layout_not_selected));

            perkinsLayoutCheckBox.setImageResource(R.mipmap.ic_radio_button_checked_green_24dp);
            perkinsLayoutCheckBox.setContentDescription(getString(R.string.perkins_layout_selected));

            //Speech it
            Common.defaultTextSpeech.speechText(getString(R.string.perkins_layout_selected));

            twoDotsLayoutCheckBox.setImageResource(R.mipmap.ic_radio_button_unchecked_green_24dp);
            twoDotsLayoutCheckBox.setContentDescription(getString(R.string.two_rows_layout_not_selected));

            //Save settings
            Common.putSettingInt("selectedDotsLayout", Common.PERKINS_DOTS_LAYOUT);

            //Change the height of the keyboard
            Common.putSettingInt("defaultKeyboardHeight", Common.DEFAULT_PORTRAIT_KEYBOARD_HEIGHT);
            Common.putSettingInt("defaultKeyboardLandscapeHeight", Common.DEFAULT_LANDSCAPE_KEYBOARD_HEIGHT);
            Common.putSettingInt("defaultKeyboardWidth", Common.DEFAULT_PORTRAIT_KEYBOARD_WIDTH);
            Common.putSettingInt("defaultKeyboardLandscapeWidth", Common.DEFAULT_LANDSCAPE_KEYBOARD_WIDTH);

            //Back 2-5 dots to be default
            Common.putSettingBoolean("makeDot2And5Higher", Common.DEFAULT_MAKE_DOT_2_5_HIGHER);

            //Change the dot radius the default
            Common.putSettingInt("dotRadius", Common.getDefaultDotRadius(0));
            Common.putSettingInt("dotLandscapeRadius", Common.getDefaultDotRadius(1));

            //Let Swift knows settings are changed
            Common.areSettingsChanged = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void twoRowsLayoutSelectedAction(View v) {
        try {
            brailleCellCheckBox.setImageResource(R.mipmap.ic_radio_button_unchecked_green_24dp);
            brailleCellCheckBox.setContentDescription(getString(R.string.braille_cell_layout_not_selected));

            perkinsLayoutCheckBox.setImageResource(R.mipmap.ic_radio_button_unchecked_green_24dp);
            perkinsLayoutCheckBox.setContentDescription(getString(R.string.perkins_layout_not_selected));

            twoDotsLayoutCheckBox.setImageResource(R.mipmap.ic_radio_button_checked_green_24dp);
            twoDotsLayoutCheckBox.setContentDescription(getString(R.string.two_rows_layout_selected));

            //Speech it
            Common.defaultTextSpeech.speechText(getString(R.string.two_rows_layout_selected));

            //Back 2-5 dots to be default
            Common.putSettingBoolean("makeDot2And5Higher", Common.DEFAULT_MAKE_DOT_2_5_HIGHER);

            //Save settings
            Common.putSettingInt("selectedDotsLayout", Common.TWO_ROWS_DOTS_LAYOUT);

            //Change the height of the keyboard
            Common.putSettingInt("defaultKeyboardHeight", Common.DEFAULT_PORTRAIT_KEYBOARD_HEIGHT);
            Common.putSettingInt("defaultKeyboardLandscapeHeight", Common.DEFAULT_LANDSCAPE_KEYBOARD_HEIGHT);
            Common.putSettingInt("defaultKeyboardWidth", Common.DEFAULT_PORTRAIT_KEYBOARD_WIDTH);
            Common.putSettingInt("defaultKeyboardLandscapeWidth", Common.DEFAULT_LANDSCAPE_KEYBOARD_WIDTH);

            //Change the dot radius the default
            Common.putSettingInt("dotRadius", Common.getDefaultDotRadius(0));
            Common.putSettingInt("dotLandscapeRadius", Common.getDefaultDotRadius(1));

            //Let Swift knows settings are changed
            Common.areSettingsChanged = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    //More details layout for Braille cell dots layout
    public void viewDetailsBrailleCellLayoutAction(View v) {
        try {
            AlertDialog.Builder moreDetailsDialogBuilder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_braille_cell_details, null);
            //Show a confirmation message to the user
            deviceSupportsMultiTouch = (TextView) view.findViewById(R.id.deviceSupportsMultiTouch);
            deviceDoesNotSupportsMultiTouch = (TextView) view.findViewById(R.id.deviceDoesNotSupportsMultiTouch);
            if (hasSixMultiTouch) {
                deviceSupportsMultiTouch.setVisibility(View.VISIBLE);
            } else {
                deviceDoesNotSupportsMultiTouch.setVisibility(View.VISIBLE);
            }
            moreDetailsDialogBuilder.setView(view);
            moreDetailsDialog = moreDetailsDialogBuilder.show();
            moreDetailsDialog.setCanceledOnTouchOutside(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    //More details layout for perkins dots layout
    public void viewDetailsPerkinsLayoutAction(View v) {
        try {
            AlertDialog.Builder moreDetailsDialogBuilder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_perkins_details, null);
            //Show a confirmation message to the user
            deviceSupportsMultiTouch = (TextView) view.findViewById(R.id.deviceSupportsMultiTouch);
            deviceDoesNotSupportsMultiTouch = (TextView) view.findViewById(R.id.deviceDoesNotSupportsMultiTouch);
            if (hasSixMultiTouch) {
                deviceSupportsMultiTouch.setVisibility(View.VISIBLE);
            } else {
                deviceDoesNotSupportsMultiTouch.setVisibility(View.VISIBLE);
            }
            moreDetailsDialogBuilder.setView(view);
            moreDetailsDialog = moreDetailsDialogBuilder.show();
            moreDetailsDialog.setCanceledOnTouchOutside(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    //More details layout for two rows layout
    public void viewDetailsTwoRowsLayoutAction(View v) {
        try {
            AlertDialog.Builder moreDetailsDialogBuilder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_two_lines_details, null);
            //Show a confirmation message to the user
            deviceSupportsMultiTouch = (TextView) view.findViewById(R.id.deviceSupportsMultiTouch);
            deviceDoesNotSupportsMultiTouch = (TextView) view.findViewById(R.id.deviceDoesNotSupportsMultiTouch);
            if (hasSixMultiTouch) {
                deviceSupportsMultiTouch.setVisibility(View.VISIBLE);
            } else {
                deviceDoesNotSupportsMultiTouch.setVisibility(View.VISIBLE);
            }
            moreDetailsDialogBuilder.setView(view);
            moreDetailsDialog = moreDetailsDialogBuilder.show();
            moreDetailsDialog.setCanceledOnTouchOutside(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    //To hide all dialog
    public void closeDialogAction(View v) {
        try {
            if (moreDetailsDialog != null) {
                moreDetailsDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void onResetSettingsBtnAction(View v) {
        try {
            brailleCellCheckBox.setImageResource(R.mipmap.ic_radio_button_checked_green_24dp);
            brailleCellCheckBox.setContentDescription(getString(R.string.braille_cell_layout_selected));

            perkinsLayoutCheckBox.setImageResource(R.mipmap.ic_radio_button_unchecked_green_24dp);
            perkinsLayoutCheckBox.setContentDescription(getString(R.string.perkins_layout_not_selected));

            twoDotsLayoutCheckBox.setImageResource(R.mipmap.ic_radio_button_unchecked_green_24dp);
            twoDotsLayoutCheckBox.setContentDescription(getString(R.string.two_rows_layout_not_selected));

            //Save settings
            Common.putSettingInt("selectedDotsLayout", Common.DEFAULT_DOTS_LAYOUT);

            //Back 2-5 dots to be default
            Common.putSettingBoolean("makeDot2And5Higher", Common.DEFAULT_MAKE_DOT_2_5_HIGHER);

            //Change the height of the keyboard
            Common.putSettingInt("defaultKeyboardHeight", Common.DEFAULT_PORTRAIT_KEYBOARD_HEIGHT);
            Common.putSettingInt("defaultKeyboardLandscapeHeight", Common.DEFAULT_LANDSCAPE_KEYBOARD_HEIGHT);
            Common.putSettingInt("defaultKeyboardWidth", Common.DEFAULT_PORTRAIT_KEYBOARD_WIDTH);
            Common.putSettingInt("defaultKeyboardLandscapeWidth", Common.DEFAULT_LANDSCAPE_KEYBOARD_WIDTH);

            //Change the dot radius to the default
            Common.putSettingInt("dotRadius", Common.getDefaultDotRadius(0));
            Common.putSettingInt("dotLandscapeRadius", Common.getDefaultDotRadius(1));

            //Let Swift knows settings are changed
            Common.areSettingsChanged = true;

            //Reset settings speech
            if (!Common.defaultTextSpeech.isArabicSupported() && Common.currentSystemLanguage.equals("ar") && Common.getSoundPath("ar_message_reset_settings") != -1) {
                Common.runPatternSoundPronounce(Common.getSoundPath("ar_message_reset_settings"), true);
            } else {
                Common.defaultTextSpeech.speechText(getString(R.string.reset_settings_done), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onDestroy() {
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
