package com.mbanna.swiftbraille;

/**
 * Author: Mohammad M. AlBanna
 * Copyright to © SwiftBraille.com
 */

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

//The main web view which will handle all links to be opened in this activity
public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_web_view);

            //Show back button
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
            }

            WebView webView = (WebView) findViewById(R.id.webView);
            final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressPar);

            //Get sent data
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                //Change the activity title
                final String activityTitle = bundle.getString("activity_title");
                setTitle(activityTitle);

                //Get the link to load
                String webViewLink = bundle.getString("web_url");
                if (webView != null) {
                    webView.loadUrl(webViewLink);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setSupportMultipleWindows(true);
                    webView.setWebChromeClient(new WebChromeClient() {
                        @Override
                        public void onProgressChanged(WebView view, int progress) {
                            setTitle(getString(R.string.loading_text));
                            assert progressBar != null;
                            progressBar.setProgress(progress);
                            if (progress == 100)
                                setTitle(activityTitle);
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------//
    //Back to the activity that opened the URL
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            if (item.getItemId() == android.R.id.home) {
                startActivity(new Intent(this, Common.previousActivity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}



