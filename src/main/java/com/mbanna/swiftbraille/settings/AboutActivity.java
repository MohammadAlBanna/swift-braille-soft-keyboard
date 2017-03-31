package com.mbanna.swiftbraille.settings;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mbanna.swiftbraille.Common;
import com.mbanna.swiftbraille.R;

import java.util.Locale;

//Class will handle the version of the app and some of details
public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_about);

            //Show back button
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
            }

            //Load current version of the app
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.mbanna.swiftbraille", PackageManager.GET_META_DATA);
            String versionName = packageInfo.versionName;
            TextView appVersion = (TextView) findViewById(R.id.appVersion);
            assert appVersion != null;
            appVersion.setText(getString(R.string.version, versionName));

            //Speak the title of the activity
            Common.defaultTextSpeech.speechText(getString(R.string.about_title));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void facebookIconAction(View v) {
        try{
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SwiftBraille/"));
            startActivity(facebookIntent);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void twitterIconAction(View v) {
        try{
            Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/swiftbraille/"));
            startActivity(twitterIntent);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void youtubeIconAction(View v) {
        try{
            Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCTfi3Z3B4lFRGgCB0G5QQqg"));
            startActivity(youtubeIntent);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    public void websiteIconAction(View v) {
        try{
            Intent websiteIntent;
            if (Common.currentSystemLanguage.equals(new Locale("ar").getLanguage())) {
                websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ar.SwiftBraille.com"));
            } else {
                websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://en.SwiftBraille.com"));
            }
            startActivity(websiteIntent);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (item.getItemId() == android.R.id.home) {
                startActivity(new Intent(this, Common.previousActivity));
            }
        }
        return true;
    }
}
