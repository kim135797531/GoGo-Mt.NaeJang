package org.kdm.gogomtnaejang;

import org.kdm.gogomtnaejang.climbmt.ClimbMapActivity;
import org.kdm.gogomtnaejang.db.ManageSQLite;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;

public class MainActivity extends Activity {
	
	public static String BASE_DIR = null;
	public static String BASE_DATABASE_DIR = null;
	public static String BASE_SDCARD_DIR = null;
	private static AssetManager am = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initManageFileSystem();
		setContentView(R.layout.activity_start_loading);
		startActivityForResult(new Intent(this, StartLoadingActivity.class),0);
	}
	
	private void initManageFileSystem(){
		BASE_DIR = getFilesDir().getPath();
		BASE_DATABASE_DIR = getDatabasePath(ManageSQLite.dbName).getPath();
		BASE_SDCARD_DIR = Environment.getExternalStorageDirectory().getPath();
		am = getAssets();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		setContentView(R.layout.activity_main);
		startActivity(new Intent(this, ClimbMapActivity.class));
	}

	public static AssetManager getAssetManager(){
		return am;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
