package it.sephiroth.android.library.ab;

import it.sephiroth.android.library.ab.AB.Group;

public abstract class ABTest {
	
	private CharSequence name;
	
	public abstract void A();
	public abstract void B();
	
	public final CharSequence getName() {
		return name;
	}
	
	protected final void run( CharSequence name, Group group ) {
		
		this.name = name;
		
		if( group == Group.GroupA ) {
			A();
		} else {
			B();
		}
	}

}
