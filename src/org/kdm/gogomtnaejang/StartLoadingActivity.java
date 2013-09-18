package org.kdm.gogomtnaejang;

import org.kdm.gogomtnaejang.climbmt.ManageTrackInfo;
import org.kdm.gogomtnaejang.community.ManageDocument;
import org.kdm.gogomtnaejang.db.ManageSQLite;
import org.kdm.gogomtnaejang.network.ManageNetwork;
import org.kdm.gogomtnaejang.node.ManageNode;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

public class StartLoadingActivity extends Activity {
	private static final double app_version = 0.15d;
	private boolean old_version = false;
	
	public static String BASE_DIR = null;
	public static String BASE_DATABASE_DIR = null;
	public static String BASE_SDCARD_DIR = null;
	private static AssetManager am = null;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setResult(Activity.RESULT_OK);
		setContentView(R.layout.activity_start_loading);
		
		new Handler().postDelayed(
				new Runnable() {					
					@Override
					public void run() {
						checkAppVer();
						initManageFileSystem();
						ManageNode.getInst(StartLoadingActivity.this);
						ManageTrackInfo.getInst();
						ManageDocument.getInst();
						
						if(old_version == false){
							Intent postLoading = new Intent(StartLoadingActivity.this, MainActivity.class);
							StartLoadingActivity.this.startActivity(postLoading);
							StartLoadingActivity.this.finish();
						}
					}
				},200);
		
	}
	
	public void finishActivity(){
		Intent postLoading = new Intent(StartLoadingActivity.this, MainActivity.class);
		StartLoadingActivity.this.startActivity(postLoading);
		StartLoadingActivity.this.finish();
	}
	
	public static AssetManager getAssetManager(){
		return am;
	}
	
	public void initManageFileSystem(){
		BASE_DIR = getFilesDir().getPath();
		BASE_DATABASE_DIR = getDatabasePath(ManageSQLite.dbName).getPath();
		BASE_SDCARD_DIR = Environment.getExternalStorageDirectory().getPath();
		am = getAssets();
	}	

	public void checkAppVer(){
		String server_version_string = ManageNetwork.getInst().downloadAppVersion("app_version");
		double server_version = Double.parseDouble(server_version_string);
		if(server_version > app_version){
			old_version = true;
			AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
			alt_bld.setTitle("최신 버전이 확인되었습니다.")
			.setMessage("업데이트 하시겠습니까?\n현재 버전 : "+app_version+"\n서버 버전 : "+server_version)
			.setIcon(R.drawable.app_icon)
			.setPositiveButton("확인",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,	int id) {
						startActivity(new Intent(Intent.ACTION_DEFAULT, Uri.parse("http://kim135797531.cafe24.com/works/app/GoGoMtNaeJang.apk")));
					}
			}).setNegativeButton("취소", 
				new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.exit(0);
					}
				}).create();
	        AlertDialog alert = alt_bld.create();		
	        alert.show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}