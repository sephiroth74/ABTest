package it.sephiroth.android.library.ab;

public abstract class ABTest {
	
	private CharSequence name;
	
	public abstract void A();
	public abstract void B();
	
	public final CharSequence getName() {
		return name;
	}
	
	protected final void run( CharSequence name, int group ) {
		
		this.name = name;
		
		if( group == 0 ) {
			A();
		} else {
			B();
		}
	}

}
