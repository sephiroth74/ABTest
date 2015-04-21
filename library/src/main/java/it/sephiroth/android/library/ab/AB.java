package it.sephiroth.android.library.ab;

import it.sephiroth.android.library.ab.ABSettingsFactory.ABSettingsManager;
import android.content.Context;
import android.util.Log;


public class AB {

	public static final String VERSION = "2.1.0-SNAPSHOT";

	public static final boolean LOG_ENABLED = false;
	
	public static final String LOG_TAG = "AB";
	
	/** The global static instance */
	private static AB instance;
	
	/** the current used manager for reading/writing uuid */
	private final ABSettingsManager manager;
	
	/** the hashcode associated to this instance */
	private final int hashCode;
	
	/** the group associated to this instance */
	private int group = -1;

	AB ( Context context ) {
		manager = setup( context );
		hashCode = manager.getUUID().hashCode();
		
		if( LOG_ENABLED ) {
			Log.d( LOG_TAG, "uuid: " + manager.getUUID() );
			Log.d( LOG_TAG, "hashCode: " + hashCode );
		}
	}
	
	public String getUUID() {
		return manager.getUUID();
	}

	/**
	 * Returns the integer group assigned to the current instance
	 * @return
	 */
	public int getGroup() {
		if ( group < 0 ) {
			group = generateGroup();
		}
		return group;
	}

	public int getGroup( int... percentages ) {
		if ( group < 0 ) {
			if ( !checkSum(percentages) ) throw new IllegalArgumentException("Arguments must sum to 100");
			group = generateGroup( percentages );
		}
		return group;
	}

	public void doNTest( CharSequence name, NTest action, int... percentages ) {
		if ( !checkSum(percentages) ) throw new IllegalArgumentException("Arguments must sum to 100");
		action.run( getGroup( percentages ) );
	}

	/**
	 * Based on the uuid let's generate
	 * the current Group
	 * @return
	 */
	private int generateGroup() {
		if ( hashCode % 2 == 0 ) {
			return 0;
		} else {
			return 1;
		}
	}

	private int generateGroup( int... percentages ) {
		// look at the hashcode as a number between 0 - 99
		int value = Math.abs(hashCode) % 100;

		Log.i("AB", "group name hash: " + value);

		int compareValue = 0;

		for (int i = 0; i < percentages.length; i++) {
			compareValue += percentages[i];
			if ( value < compareValue ) return i;
		}

		// we will never be at this point as we are throwing an exception if the sum of percentages
		// is != 100
		return -1;
	}


	private ABSettingsFactory.ABSettingsManager setup( Context context ) {
		return ABSettingsFactory.create( context );
	}

	/**
	 * Returns a new instance of the ab test instance
	 * @param context the current context
	 * @return
	 */
	public static AB getInstance( Context context ) {
		if ( instance == null ) {
			synchronized ( AB.class ) {
				AB inst = instance;
				if ( inst == null ) {
					synchronized ( AB.class ) {
						instance = new AB( context );
					}
				}
			}
		}
		return instance;
	}

	private boolean checkSum( int... percentages ) {
		int sum = 0;
		for ( int i : percentages ) {
			sum += i;
		}

		return sum == 100;
	}

}
