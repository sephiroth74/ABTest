package it.sephiroth.android.library.ab;

import it.sephiroth.android.library.ab.ABSettingsFactory.ABSettingsManager;
import android.content.Context;
import android.util.Log;


public class AB {

	public enum Group {
		GroupA, GroupB
	};

	public static final String VERSION = "2.0.2";

	public static final boolean LOG_ENABLED = false;
	
	public static final String LOG_TAG = "AB";
	
	/** The global static instance */
	private static AB instance;
	
	/** the current used manager for reading/writing uuid */
	private final ABSettingsManager manager;
	
	/** the hashcode associated to this instance */
	private final int hashCode;
	
	/** the group associated to this instance */
	private Group group;

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
	 * Returns the {@link Group} assigned to the current instance
	 * @return
	 */
	public Group getGroup() {
		if ( null == group ) {
			group = generateGroup();
		}
		return group;
	}
	
	/**
	 * Executes a new AB test.<br />
	 * 
	 * @param name the label associated to this test
	 * @param action the action to be executed
	 */
	public void doABTest( CharSequence name, ABTest action ) {
		action.run( name, getGroup() );
	}
	
	/**
	 * Based on the uuid let's generate
	 * the current Group
	 * @return
	 */
	private Group generateGroup() {
		if ( hashCode % 2 == 0 ) {
			return Group.GroupA;
		} else {
			return Group.GroupB;
		}
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

}
