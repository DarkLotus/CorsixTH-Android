// Copyright (C) 2012 Alan Woolley
// 
// See LICENSE.TXT for full license
//
package uk.co.armedpineapple.corsixth;

import java.util.Properties;

import com.flurry.android.FlurryAgent;

import android.app.Activity;
import android.util.Log;

public abstract class CTHActivity extends Activity {

	boolean trackingSession = false;

	@SuppressWarnings("nls")
	@Override
	protected void onStart() {
		super.onStart();

		Properties p = getCthApplication().getProperties();
		
		// Check if Flurry is enabled
		
		if (p != null && p.containsKey("flurry.key")) {
			Log.d(getClass().getSimpleName(), "Starting Flurry Session");
			FlurryAgent.setCaptureUncaughtExceptions(Boolean.parseBoolean(p
					.getProperty("flurry.catcherrors", "false")));
			FlurryAgent.onStartSession(this, p.getProperty("flurry.key"));

			// Log Flurry events if app.debug=true
			FlurryAgent.setLogEnabled(Boolean.parseBoolean(p.getProperty(
					"app.debug", "false")));
			trackingSession = true;
		} else {
			Log.d(getClass().getSimpleName(), "Flurry is not enabled");
		}

	}

	@Override
	protected void onStop() {
		super.onStop();
		if (trackingSession) {
			FlurryAgent.onEndSession(this);
		}

	}

	public CorsixTHApplication getCthApplication() {
		return (CorsixTHApplication) getApplication();
	}

}
