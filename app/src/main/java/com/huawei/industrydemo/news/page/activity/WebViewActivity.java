/*
 *     Copyright 2021. Huawei Technologies Co., Ltd. All rights reserved.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.huawei.industrydemo.news.page.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.huawei.industrydemo.news.R;
import com.huawei.industrydemo.news.base.BaseActivity;

import static com.huawei.industrydemo.news.constants.KeyConstants.URL_TYPE;
import static com.huawei.industrydemo.news.constants.KeyConstants.WEB_URL;

/**
 * WebView Activity<br/>
 * Intent parameters to be transferred:<br/>
 * "isShowFloat"(Optional, Default value: true)<br/>
 * WEB_URL(Required)<br/>
 * URL_TYPE(Optional, Default value: Others)<br/>
 * "useBrowser"(Optional, Default value: false)<br/>
 *
 * @version [Ecommerce-Demo 1.0.2.300, 2021/3/30]
 * @since [Ecommerce-Demo 1.0.2.300]
 */
public class WebViewActivity extends BaseActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        if (intent == null) {
            Toast.makeText(this, R.string.url_error, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String url = intent.getStringExtra(WEB_URL);
        if (url == null || url.isEmpty()) {
            Toast.makeText(this, R.string.url_error, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String urlType = intent.getStringExtra(URL_TYPE);
        if (urlType == null || urlType.isEmpty()) {
            urlType = "Others";
        }

        boolean useBrowser = intent.getBooleanExtra("useBrowser", false);
        initWebView(url, useBrowser);
    }

    static class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            super.shouldOverrideUrlLoading(view, url);
            view.loadUrl(url);
            return true;
        }
    }

    static class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(String url, boolean useBrowser) {
        mWebView = findViewById(R.id.web_view);
        mWebView.loadUrl(url);
        if (!useBrowser) {
            mWebView.setWebViewClient(new MyWebViewClient());
        }

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true); // DOM Storage

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.setWebChromeClient(new MyWebChromeClient());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}