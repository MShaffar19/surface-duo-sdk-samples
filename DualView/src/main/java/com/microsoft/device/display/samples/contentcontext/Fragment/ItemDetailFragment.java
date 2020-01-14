/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.contentcontext.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.microsoft.device.display.samples.contentcontext.Item;
import com.microsoft.device.display.samples.contentcontext.R;

public class ItemDetailFragment extends Fragment {
    private WebView mWebView;
    private double lat, lng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Item item = getArguments().getParcelable(Item.KEY);
        lat = item.getLocation().x;
        lng = item.getLocation().y;
        String title = String.valueOf(item);
        getActivity().setTitle(title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail,
                container, false);
        mWebView = view.findViewById(R.id.web_view);
        setupWebView();
        return view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(ItemDetailFragment.this, "AndroidFunction");
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("file:///android_asset/googlemap.html");
    }

    public static ItemDetailFragment newInstance(Item item) {
        ItemDetailFragment fragment = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Item.KEY, item);
        fragment.setArguments(args);
        return fragment;
    }

    @JavascriptInterface
    public double getLat() {
        return lat;
    }

    @JavascriptInterface
    public double getLng() {
        return lng;
    }
}
