package com.yadishot.tuchgram.Calligraphy;

import android.app.Application;

import com.yadishot.tuchgram.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ApplicationConfig extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/sans.ttf").setFontAttrId(R.attr.fontPath).build());
    }
}
