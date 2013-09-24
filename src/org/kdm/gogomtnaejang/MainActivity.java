package org.kdm.gogomtnaejang;

import org.kdm.gogomtnaejang.climbmt.TrackListActivity;
import org.kdm.gogomtnaejang.community.BoardListActivity;
import org.kdm.gogomtnaejang.information.InfoListActivity;
import org.kdm.gogomtnaejang.node.ManageNode;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.app.AlertDialog;
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
	private ImageView ARInfoButton = null;
	private Handler handler;
	private AlertDialog infoDialog;
	private ProgressDialog progressDialog;
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
				progressDialog.setProgress(total);
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
			progressDialog = new ProgressDialog(this);
			progressDialog.setTitle("가자! 내장산");
			progressDialog.setIcon(R.drawable.app_icon);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setIndeterminate(false);
			progressDialog.setMessage("등산로 정보를 준비하고 있습니다...");
			return progressDialog;
		}
			
		return null;
		
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
		ARInfoButton = (ImageView) findViewById(R.id.ARinfoButton);
		
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
		ARInfoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
				ab.setIcon(R.drawable.app_icon);
				ab.setTitle("증강현실이 뭐죠?");
				ab.setMessage("  증강현실(Augmented Reality)이란 사용자가 눈으로 보는 실세계에 가상 물체를 겹쳐 보여주는 기술입니다. \r\n\r\n"
						+ "  가자! 내장산 앱에서는 GPS를 사용하여 사용자의 현재 위치를 파악하고, 그 정보를 바탕으로 내장산의 주요 포인트를 카메라 영상에 겹쳐 표시합니다. \r\n\r\n"
						+ "  따라서 사용자는 내장산 화면 위에 정보가 둥둥 떠있는 느낌을 받을 수 있습니다. \r\n"
						+ "\r\n"
						+ "라이선스 고지: 가자! 내장산의 증강현실은 GPLv3를 따르는 오픈 소스 프로젝트 Mixare를 이용하여 만들어졌습니다.");
				ab.setPositiveButton(android.R.string.ok, null);
				ab.show();
			}
		});
	}
	
}
