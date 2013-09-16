package org.kdm.gogomtnaejang;

import org.kdm.gogomtnaejang.climbmt.ClimbMapActivity;
import org.kdm.gogomtnaejang.climbmt.TrackListActivity;
import org.kdm.gogomtnaejang.community.BoardListActivity;
import org.kdm.gogomtnaejang.information.InfoReadActivity;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	private ImageView trackListButton = null;
	private ImageView climbMapButton = null;
	private ImageView infoReadButton = null;
	private ImageView boardListButton = null;
	private ImageView ARButton = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initButton();
	}
	
	private void initButton(){
		trackListButton = (ImageView) findViewById(R.id.trackListButton);
		//climbMapButton = (ImageView) findViewById(R.id.climbMapButton);
		infoReadButton = (ImageView) findViewById(R.id.infoReadButton);
		boardListButton = (ImageView) findViewById(R.id.boardListButton);
		ARButton = (ImageView) findViewById(R.id.ARButton);
		
		trackListButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), TrackListActivity.class));
			}
		});
		/*		climbMapButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), ClimbMapActivity.class));
			}
		});
		*/
		infoReadButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), InfoReadActivity.class));
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
				//startActivity(new Intent(getBaseContext(), ARActivity.class));
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
