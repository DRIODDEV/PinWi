/*
 * Added by Amit Sharma.
 */
package com.hatchtact.pinwi;

import android.app.Activity;
import android.os.Bundle;

/**
 * The Class ExitActivity to be called when a new Application Installer has been
 * downloaded.This Activity will just be used to Exit from the
 * Application,Instead of using the System.exit(0)
 */
public class ExitActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		finish();
	}
}
