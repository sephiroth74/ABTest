package it.sephiroth.android.library.ab;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import android.content.Context;

class ABSettingsFactory {

	static abstract class ABSettingsManager {
		protected static final String DIR_NAME = "ab";
		protected static final String FILE_NAME = ".alpha-beta";
		protected static final String PREF_NAME = "alphabeta";
		
		public abstract String getUUID();
		
		protected final UUID generateNew() {
			return UUID.randomUUID();
		}
	}

	public static ABSettingsManager create( Context context ) {
		if ( canUseFileSystem( context ) ) {
			try {
				return new ABFilePreference( context );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		return new ABSharedPreference( context );
	}

	private static boolean canUseFileSystem( Context context ) {

		File filesdir = context.getFilesDir();
		if ( filesdir != null ) {
			if ( filesdir.canRead() && filesdir.canWrite() ) {
				return true;
			}
		}

		return false;
	}

}
