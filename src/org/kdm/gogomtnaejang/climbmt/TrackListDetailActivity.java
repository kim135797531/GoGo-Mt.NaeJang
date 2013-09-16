package org.kdm.gogomtnaejang.climbmt;

import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TrackListDetailActivity extends Activity{
	
	int id;
	Button climbMapButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//id = savedInstanceState.getInt("id");
		id=0;
		setContentView(R.layout.activity_track_list_detail);
		initButton();
	}
	
	private void initButton(){
		climbMapButton = (Button) findViewById(R.id.climbMapButton);
		climbMapButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent r = new Intent(TrackListDetailActivity.this, ClimbMapActivity.class);
				r.putExtra("id", id);
				startActivity(r);
			}
		});
	}
}
