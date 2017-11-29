package com.falcon.helloandroid.Weights;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.falcon.helloandroid.R;

/**
 * Created by micha on 22.11.2017.
 */

public class SettingsFragment extends PreferenceFragment{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.weight_settings);
    }
}
