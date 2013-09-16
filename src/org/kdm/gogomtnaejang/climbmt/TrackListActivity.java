package org.kdm.gogomtnaejang.climbmt;

import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TrackListActivity extends Activity{
	
	private TrackListAdapter mAdapter;
	private ListView mTrackListView;
	private RelativeLayout footer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track_list);
		initTrackList();
		initTrackListView();
		initOnItemClickListener();
	}
	
	private void initTrackList(){
		mAdapter = new TrackListAdapter(this, ManageTrackInfo.getInst(this).getAllTrackList());
	}
	
	private void initTrackListView(){
		mTrackListView = (ListView) findViewById(R.id.climbList);
		footer = (RelativeLayout) getLayoutInflater().inflate(R.layout.footer, null);
		mTrackListView.addFooterView(footer, null, false);
		mTrackListView.setAdapter(mAdapter);
	}
	
	private void initOnItemClickListener(){
		mTrackListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id){
				Intent r = new Intent(TrackListActivity.this, TrackListDetailActivity.class);
				int intId = (int) id; 
				r.putExtra("trackID", intId);
				startActivity(r);
			}
		});
	}
}
