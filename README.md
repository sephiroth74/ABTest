ABTest
======

Simple library for ab testing in android


## Usage (gradle)

Add this line to your dependecy group:

	compile 'it.sephiroth.android.library.ab:library:+'

## Example


This library is meant for very simple AB testing.

The library can be initialized everywhere in your code:

		AB alphaBetaTest = AB.getInstance( this );
		
You can retrieve the current group in this way:
		
		Log.d( LOG_TAG, "group: " + alphaBetaTest.getGroup() );
		
And you can perform an ab test in this way:
		
		alphaBetaTest.doABTest( "test-action", new ABTest() {
			
			@Override
			public void B() {
				Log.i( LOG_TAG, getName() + " = B" );
			}
			
			@Override
			public void A() {
				Log.i( LOG_TAG, getName() + " = A" );
			}
		} );