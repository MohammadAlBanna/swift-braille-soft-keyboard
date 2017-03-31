package com.mbanna.swiftbraille;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mbanna.swiftbraille.settings.AboutActivity;
import com.mbanna.swiftbraille.settings.SettingsActivity;

import java.util.Locale;

//The main screen of the app shortcut icon
public class MainActivity extends AppCompatActivity {
    TextView swiftBrailleIsDefaultKeyboard, swiftBrailleIsNotDefaultKeyboard;
    ImageView imageStatusUnhappyFace, imageStatusHappyFace;
    final Handler handler = new Handler();
    LinearLayout swiftBrailleStatusContainer;

    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            swiftBrailleIsDefaultKeyboard = (TextView) findViewById(R.id.swiftBrailleIsDefaultKeyboard);
            swiftBrailleIsNotDefaultKeyboard = (TextView) findViewById(R.id.swiftBrailleIsNotDefaultKeyboard);
            imageStatusHappyFace = (ImageView) findViewById(R.id.imageStatusHappyFace);
            imageStatusUnhappyFace = (ImageView) findViewById(R.id.imageStatusUnhappyFace);
            swiftBrailleStatusContainer = (LinearLayout) findViewById(R.id.swiftBrailleStatusContainer);

            //If the user changes the current system language
            Common.currentSystemLanguage = Locale.getDefault().getLanguage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void activateBtnAction(View v) {
        try {
            Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void setDefaultBtnAction(View v) {
        try {
            InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
            imeManager.showInputMethodPicker();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void customizeBtnAction(View v) {
        try {
            startActivity(new Intent(this, SettingsActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    //Check if SwiftBraille is the default keyboard
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                String isSwiftBrailleTheDefaultKeyboard = Settings.Secure.getString(getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);
                if (isSwiftBrailleTheDefaultKeyboard != null && isSwiftBrailleTheDefaultKeyboard.equals("com.mbanna.swiftbraille/.BrailleIME")) {
                    //SwiftBraille is the default keyboard
                    swiftBrailleIsNotDefaultKeyboard.setVisibility(View.GONE);
                    imageStatusUnhappyFace.setVisibility(View.GONE);

                    swiftBrailleIsDefaultKeyboard.setVisibility(View.VISIBLE);
                    imageStatusHappyFace.setVisibility(View.VISIBLE);
                } else {
                    //SwiftBraille is not the default the keyboard
                    swiftBrailleIsDefaultKeyboard.setVisibility(View.GONE);
                    imageStatusHappyFace.setVisibility(View.GONE);

                    swiftBrailleIsNotDefaultKeyboard.setVisibility(View.VISIBLE);
                    imageStatusUnhappyFace.setVisibility(View.VISIBLE);
                }

                //Visible the container
                if (!swiftBrailleStatusContainer.isShown()) {
                    swiftBrailleStatusContainer.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        handler.postDelayed(runnable, 500);
    }

    //--------------------------------------------------------------------------------------------//
    //Inflate the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //--------------------------------------------------------------------------------------------//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.howToItem:
                    Common.previousActivity = MainActivity.class;
                    Intent howToIntent = new Intent(this, WebViewActivity.class);
                    howToIntent.putExtra("activity_title", getString(R.string.how_to_item));
                    howToIntent.putExtra("web_url", getString(R.string.how_to_link));
                    startActivity(howToIntent);
                    break;

                case R.id.whatsNewItem:
                    Common.previousActivity = MainActivity.class;
                    Intent whatsNewIntent = new Intent(this, WebViewActivity.class);
                    whatsNewIntent.putExtra("activity_title", getString(R.string.whats_new_item));
                    whatsNewIntent.putExtra("web_url", getString(R.string.whats_new_link));
                    startActivity(whatsNewIntent);
                    break;

                case R.id.shareItem:
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.swiftbraille_store_link));
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.swiftbraille_share_text)));
                    break;

                case R.id.joinTestingProgram:
                    Common.previousActivity = MainActivity.class;
                    Intent testingProgramIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.join_testing_program_link)));
                    startActivity(testingProgramIntent);
                    break;

                case R.id.aboutItem:
                    Common.previousActivity = MainActivity.class;
                    Intent intent = new Intent(this, AboutActivity.class);
                    startActivity(intent);
                    break;

                case R.id.licenceItem:
                    Common.previousActivity = MainActivity.class;
                    Intent licenceIntent = new Intent(this, WebViewActivity.class);
                    licenceIntent.putExtra("activity_title", getString(R.string.licence_item));
                    licenceIntent.putExtra("web_url", getString(R.string.licence_link));
                    startActivity(licenceIntent);
                    break;

                case R.id.privacyPolicyItem:
                    Common.previousActivity = MainActivity.class;
                    Intent privacyPolicyIntent = new Intent(this, WebViewActivity.class);
                    privacyPolicyIntent.putExtra("activity_title", getString(R.string.privacy_policy_item));
                    privacyPolicyIntent.putExtra("web_url", getString(R.string.privacy_policy_link));
                    startActivity(privacyPolicyIntent);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }
}
