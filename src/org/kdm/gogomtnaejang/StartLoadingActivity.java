package org.kdm.gogomtnaejang;

import org.kdm.gogomtnaejang.climbmt.ManageTrackInfo;
import org.kdm.gogomtnaejang.db.ManageSQLite;
import org.kdm.gogomtnaejang.node.ManageNode;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

public class StartLoadingActivity extends Activity {
	private static long DELAY = 1000;
	
	public static String BASE_DIR = null;
	public static String BASE_DATABASE_DIR = null;
	public static String BASE_SDCARD_DIR = null;
	private static AssetManager am = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setResult(Activity.RESULT_OK);
		setContentView(R.layout.activity_start_loading);
		
		initManageFileSystem();
		ManageNode.getInst(this);
		ManageTrackInfo.getInst(this);

		new Handler().postDelayed(
				new Runnable() {					
					@Override
					public void run() {
						Intent postLoading = new Intent(StartLoadingActivity.this, MainActivity.class);
						StartLoadingActivity.this.startActivity(postLoading);
						StartLoadingActivity.this.finish();
					}
				}, DELAY);
	}
	
	public static AssetManager getAssetManager(){
		return am;
	}
	
	private void initManageFileSystem(){
		BASE_DIR = getFilesDir().getPath();
		BASE_DATABASE_DIR = getDatabasePath(ManageSQLite.dbName).getPath();
		BASE_SDCARD_DIR = Environment.getExternalStorageDirectory().getPath();
		am = getAssets();
	}
}
