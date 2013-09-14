package org.kdm.gogomtnaejang.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.kdm.gogomtnaejang.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper{
	private SQLiteDatabase db;
	
	public OpenHelper(Context context, String name, CursorFactory factory, int version)
	throws IOException{
		super(context, name, null, version);
        boolean dbexist = checkdatabase();
        if (dbexist) {
            System.out.println("Database exists");
            opendatabase(); 
        } else {
            System.out.println("Database doesn't exist");
            createdatabase();
        }
	}
	
	public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if(dbexist) {
            System.out.println("Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch(IOException e) {
                throw new Error("Error copying database");
            }
        }
    }   

    private boolean checkdatabase() {
        boolean checkdb = false;
        try {
            String myPath = MainActivity.BASE_DATABASE_DIR;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = MainActivity.getAssetManager().open(ManageSQLite.dbName);

        // Path to the just created empty db
        String outfilename = MainActivity.BASE_DATABASE_DIR;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = MainActivity.BASE_DATABASE_DIR;
        db = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if(db != null) {
            db.close();
        }
        super.close();
    } 
	
	@Override
	public void onCreate(SQLiteDatabase db){
		this.db = db;
	}	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
