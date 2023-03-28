package com.jkurajpuriya.newsapps.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.jkurajpuriya.newsapps.R;

public class NewsDetailsActivity extends AppCompatActivity {
    WebView webView;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        getSupportActionBar().hide();




        dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_box);
        dialog.setCancelable(false);
        if (dialog.getWindow()!=null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        }
        dialog.show();

        webView=(WebView)findViewById(R.id.webView);
        webView.loadUrl(getIntent().getStringExtra("wb"));
        dialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}