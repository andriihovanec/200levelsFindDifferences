package com.olbigames.levels200finddifferences;


import android.app.Application;

import android.content.Context;
import android.support.multidex.MultiDex;

public class AppAnalytics extends Application  {

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(newBase);
		MultiDex.install(this);
	}

	//private static final String PROPERTY_ID = "ca-app-pub-xxxxxxxxxxxxxxx";

}