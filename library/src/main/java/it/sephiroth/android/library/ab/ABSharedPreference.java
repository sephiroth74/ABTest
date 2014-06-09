package it.sephiroth.android.library.ab;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

class ABSharedPreference extends ABSettingsFactory.ABSettingsManager {

	private final String uuid;
	private static final String KEY_UUID = "ab-uuid";

	public ABSharedPreference ( Context context ) {
		uuid = generate( context );
	}

	private String generate( Context context ) {

		SharedPreferences prefs = getSharedPreferences( context );
		String string;

		if ( prefs.contains( KEY_UUID ) ) {
			string = prefs.getString( KEY_UUID, null );
			if ( null != string ) {
				return string;
			}
		}

		string = generateNew().toString();
		Editor editor = prefs.edit();
		editor.putString( KEY_UUID, string );
		editor.commit();

		return string;
	}

	private SharedPreferences getSharedPreferences( Context context ) {
		if ( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB ) {
			return getSharedPreferencesNewApi( context );
		} else {
			return getSharedPreferencesOldApi( context );
		}
	}

	@TargetApi ( Build.VERSION_CODES.HONEYCOMB )
	private SharedPreferences getSharedPreferencesNewApi( Context context ) {
		return context.getSharedPreferences( PREF_NAME, Context.MODE_MULTI_PROCESS );
	}

	private SharedPreferences getSharedPreferencesOldApi( Context context ) {
		return context.getSharedPreferences( PREF_NAME, Context.MODE_PRIVATE );
	}

	@Override
	public String getUUID() {
		return uuid;
	}

}
