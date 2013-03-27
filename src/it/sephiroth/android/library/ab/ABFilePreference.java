package it.sephiroth.android.library.ab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import android.content.Context;

class ABFilePreference extends ABSettingsFactory.ABSettingsManager {
	
	final String uuid;

	ABFilePreference( Context context ) throws IOException {
		uuid = generate( context );
	}
	
	@Override
	public String getUUID() {
		return uuid;
	}
	
	private String generate( Context context ) throws IOException {
		File filesdir = context.getFilesDir();
		File parent = new File( filesdir, DIR_NAME );
		
		if( !parent.exists() ) {
			parent.mkdir();
		}
		
		File file = new File( parent, FILE_NAME );
		
		if( !file.exists() ) {
			return createFile( file );
		} else {
			return readFile( file );
		}
	}
	
	private String createFile( File file ) throws IOException {
		String uuid = generateNew().toString();
		file.createNewFile();
		FileOutputStream stream = new FileOutputStream( file );
		stream.write( uuid.getBytes() );
		stream.flush();
		stream.close();
		return uuid;
	}
	
	private String readFile( File file ) throws IOException {
		FileInputStream stream = new FileInputStream( file );
		String uuid = readStream( stream );
		stream.close();
		return uuid;
	}
	
	private String readStream( final InputStream pInputStream ) throws IOException {
		final StringBuilder sb = new StringBuilder();
		final Scanner sc = new Scanner( pInputStream );
		while ( sc.hasNextLine() ) {
			sb.append( sc.nextLine() );
		}
		return sb.toString();
	}	
}
