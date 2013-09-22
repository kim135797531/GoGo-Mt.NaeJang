package org.kdm.gogomtnaejang;

import org.kdm.gogomtnaejang.climbmt.ManageTrackInfo;
import org.kdm.gogomtnaejang.community.ManageDocument;
import org.kdm.gogomtnaejang.db.ManageSQLite;
import org.kdm.gogomtnaejang.network.ManageNetwork;
import org.kdm.gogomtnaejang.node.ManageNode;
import org.kdm.gogomtnaejang.node.Path;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;

public class StartLoadingActivity extends Activity {
	private static final double app_version = 0.20d;
	private boolean old_version = false;

	public static String BASE_DIR = null;
	public static String BASE_DATABASE_DIR = null;
	public static String BASE_SDCARD_DIR = null;
	private static AssetManager am = null;
	private static Bundle downloadingNodeBundle = null;
	private ProgressDialog dialog;
	private ProgressThread pthread;
	private Handler handler;

	public static Bundle getDownloadingNodeBundle() {
		return downloadingNodeBundle;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setResult(Activity.RESULT_OK);
		setContentView(R.layout.activity_start_loading);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				int total = msg.getData().getInt("total");
				dialog.setProgress(total);
				if (total >= 100) {
					dismissDialog(1);
					pthread.setState(1);
					Handler finishHandler = new Handler(){
						@Override
						public void handleMessage(Message msg){
							Intent postLoading = new Intent(StartLoadingActivity.this,
									MainActivity.class);
							StartLoadingActivity.this.startActivity(postLoading);
							StartLoadingActivity.this.finish();
						}
					};
					finishHandler.sendEmptyMessageDelayed(0, 2000);
				}
			}
		};

		showDialog(1);
		checkAppVer();
		setProgress(handler,20);
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		if (id == 1) {
			pthread = new ProgressThread(handler);
			pthread.start();
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == 1) {
			dialog = new ProgressDialog(this);
			dialog.setTitle("가자! 내장산");
			dialog.setIcon(R.drawable.app_icon);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setIndeterminate(false);
			dialog.setMessage("프로그램 실행 준비중입니다...");
		}
		return dialog;
	}

	class ProgressThread extends Thread {
		private int total;
		private Handler handler;
		private int state;

		public ProgressThread(Handler handler) {
			this.handler = handler;
		}

		public void setState(int state) {
			this.state = state;
		}

		public void run() {
			Message msg;
			Bundle bundle;
			
			initManageFileSystem();
			msg = handler.obtainMessage();
			bundle = new Bundle();
			bundle.putInt("total", 20);
			msg.setData(bundle);			
			handler.sendMessage(msg);
			
			ManageTrackInfo.getInst();
			msg = handler.obtainMessage();
			bundle = new Bundle();
			bundle.putInt("total", 30);
			msg.setData(bundle);	
			handler.sendMessage(msg);
			
			ManageNode.getInst(StartLoadingActivity.this);
			if (old_version == false) {
				msg = handler.obtainMessage();
				bundle = new Bundle();
				bundle.putInt("total", 100);
				msg.setData(bundle);
				handler.sendMessage(msg);
			}

		}
	}
	
	private void setProgress(Handler handler, int progress){
		Message msg = handler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putInt("total", 10);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}

	public void finishActivity() {
		Intent postLoading = new Intent(StartLoadingActivity.this,
				MainActivity.class);
		StartLoadingActivity.this.startActivity(postLoading);
		StartLoadingActivity.this.finish();
	}

	public static AssetManager getAssetManager() {
		return am;
	}

	public void initManageFileSystem() {
		BASE_DIR = getFilesDir().getPath();
		BASE_DATABASE_DIR = getDatabasePath(ManageSQLite.dbName).getPath();
		BASE_SDCARD_DIR = Environment.getExternalStorageDirectory().getPath();
		am = getAssets();
	}

	public void checkAppVer() {
		String server_version_string = ManageNetwork.getInst().downloadAppVersion("app_version");
		if(server_version_string == null)
			return;
		
		double server_version = Double.parseDouble(server_version_string);
		if (server_version > app_version) {
			old_version = true;
			AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
			alt_bld.setTitle("최신 버전이 확인되었습니다.")
					.setMessage(
							"업데이트 하시겠습니까?\n현재 버전 : " + app_version
									+ "\n서버 버전 : " + server_version)
					.setIcon(R.drawable.app_icon)
					.setPositiveButton("확인",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									startActivity(new Intent(
											Intent.ACTION_VIEW,
											Uri.parse("http://kim135797531.cafe24.com/works/app/GoGoMtNaeJang.apk")));
								}
							})
					.setNegativeButton("취소",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
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