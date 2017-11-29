package com.falcon.helloandroid.Weights;

import android.preference.PreferenceActivity;

import com.falcon.helloandroid.R;

import java.util.List;

public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.headers_settings, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SettingsFragment.class.getName().equals(fragmentName);
    }
}