package org.kdm.gogomtnaejang;

import org.kdm.gogomtnaejang.StartLoadingActivity.ProgressThread;
import org.kdm.gogomtnaejang.climbmt.ManageTrackInfo;
import org.kdm.gogomtnaejang.climbmt.TrackListActivity;
import org.kdm.gogomtnaejang.community.BoardListActivity;
import org.kdm.gogomtnaejang.community.ManageDocument;
import org.kdm.gogomtnaejang.information.InfoListActivity;
import org.kdm.gogomtnaejang.node.ManageNode;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	private ImageView trackListButton = null;
	private ImageView infoReadButton = null;
	private ImageView boardListButton = null;
	private ImageView ARButton = null;
	private Handler handler;
	private ProgressDialog dialog;
	private ProgressThread pthread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initButton();
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				int total = msg.getData().getInt("total");
				dialog.setProgress(total);
				if (total >= 100) {
					dismissDialog(1);
					startActivity(new Intent(getBaseContext(), TrackListActivity.class));
				}
			}
		};
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
			dialog.setMessage("등산로 정보를 준비하고 있습니다...");
		}
		return dialog;
	}
	
	class ProgressThread extends Thread {
		private Handler handler;

		public ProgressThread(Handler handler) {
			this.handler = handler;
		}

		public void run() {
			try {
				ManageNode.getInst(MainActivity.this).initData(MainActivity.this, handler);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void initButton(){
		trackListButton = (ImageView) findViewById(R.id.trackListButton);
		infoReadButton = (ImageView) findViewById(R.id.infoReadButton);
		boardListButton = (ImageView) findViewById(R.id.boardListButton);
		ARButton = (ImageView) findViewById(R.id.ARButton);
		
		trackListButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				showDialog(1);
			}
		});
		infoReadButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), InfoListActivity.class));
			}
		});
		boardListButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), BoardListActivity.class));
			}
		});
		ARButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), org.mixare.MixView.class));
			}
		});
	}
	
}
