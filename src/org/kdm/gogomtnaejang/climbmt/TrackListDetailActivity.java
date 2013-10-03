package org.kdm.gogomtnaejang.climbmt;

import org.kdm.gogomtnaejang.network.ManageNetwork;
import org.kdm.gogomtnaejang.volley.FadeInNetworkImageView;
import org.kdm.gogomtnaejang.volley.ManageVolley;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

public class TrackListDetailActivity extends Activity{
	
	private int trackID;
	private Track track;
	
	private TextView track_title;
	private FadeInNetworkImageView track_image;
	private TextView track_description;
	private TextView track_time;
	private TextView track_distance;
	private TextView track_detailDescription;
	private Button climbMapButton;
	private ImageView detailCloseButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		trackID = intent.getIntExtra("trackID", 0);
		track = ManageTrackInfo.getInst().getOneTrack(trackID);
		
		setContentView(R.layout.activity_track_list_detail);
		initDescription();
		initImage();
		initButton();
	}
	
	private void initDescription(){
		track_title = (TextView) findViewById(R.id.track_title);
		track_description = (TextView) findViewById(R.id.track_description);
		track_time = (TextView) findViewById(R.id.track_time);
		track_distance = (TextView) findViewById(R.id.track_distance);
		track_detailDescription = (TextView) findViewById(R.id.track_detailDescription);
		
		track_title.setText("등산로 : "+track.title);
		track_description.setText("경로 : "+track.description);
		track_time.setText("예상 소요 시간 : "+track.time);
		track_distance.setText("총 길이 : "+track.distance);
		track_detailDescription.setText("설명 : "+track.detailDescription);
	}
	
	private void initImage(){
		//track_image = (ImageView) findViewById(R.id.track_image);
		//track_image.setImageResource(getBitmapResource(track.imageID));
		
		track_image = (FadeInNetworkImageView) findViewById(R.id.track_image);
		ImageLoader mImageLoader = ManageVolley.getInst(this).getImageLoader();
			if (track.imageID.equalsIgnoreCase("NO IMAGE")) {
				track_image.setImageUrl(ManageNetwork.SERVER_SRC
						+ "/works/jeongueop/image/app_icon.png", mImageLoader);
			} else {
				track_image.setImageUrl(ManageNetwork.SERVER_SRC
						+ "/works/jeongueop/image/track/" + track.imageID
						+ ".png", mImageLoader);
			}
	}
	
	private int getBitmapResource(String thumbImageID){		
		return getResources().getIdentifier(thumbImageID,"drawable", "org.kdm.gogonaejangmt");
	}
	
	private void initButton(){
		climbMapButton = (Button) findViewById(R.id.climbMapButton);
		climbMapButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent r = new Intent(TrackListDetailActivity.this, ClimbMapActivity.class);
				r.putExtra("callActivity", ClimbMapActivity.CALL_BY_TRACK_LIST_DETAIL_ACTIVITY);
				r.putExtra("trackID", trackID);
				startActivity(r);
			}
		});
		detailCloseButton = (ImageView) findViewById(R.id.detailCloseButton);
		detailCloseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
	}
}
