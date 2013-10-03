package org.kdm.gogomtnaejang.information;

import org.kdm.gogomtnaejang.network.ManageNetwork;
import org.kdm.gogomtnaejang.node.InfoNode;
import org.kdm.gogomtnaejang.node.ManageNode;
import org.kdm.gogomtnaejang.volley.FadeInNetworkImageView;
import org.kdm.gogomtnaejang.volley.ManageVolley;
import org.kdm.gogonaejangmt.R;

import com.android.volley.toolbox.ImageLoader;

import android.R.style;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoListDetailActivity extends Activity {

	private int infoID;
	private InfoNode infoNode;
	private TextView infoNameView;
	private FadeInNetworkImageView infoImageView;
	private TextView infoAddressView;
	private TextView infoTelNumView;
	private TextView infoDescriptionView;
	private ImageView infoDetailCloseButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		infoID = intent.getIntExtra("infoID", 0);
		infoNode = ManageNode.getInst(this).getOneInfoDocument(infoID);

		setContentView(R.layout.activity_info_list_detail);
		initDescription();
		initImage();
		initButton();
	}

	private void initDescription() {
		infoNameView = (TextView) findViewById(R.id.info_name);
		infoAddressView = (TextView) findViewById(R.id.info_address);
		infoTelNumView = (TextView) findViewById(R.id.info_telnum);
		infoDescriptionView = (TextView) findViewById(R.id.info_description);
		
		infoNameView.setText(infoNode.name);
		infoAddressView.setText("주소 : "+infoNode.address);
		infoTelNumView.setText("전화번호 : "+infoNode.telNum);
		Linkify.addLinks(infoTelNumView, Linkify.PHONE_NUMBERS);
		infoDescriptionView.setText("설명 : "+infoNode.detailDescription);
	}
	
	private void initImage(){
		infoImageView = (FadeInNetworkImageView) findViewById(R.id.info_image);
		ImageLoader mImageLoader = ManageVolley.getInst(this).getImageLoader();
			if (infoNode.imageURL.equalsIgnoreCase("NO IMAGE")) {
				infoImageView.setImageUrl(ManageNetwork.SERVER_SRC
						+ "/works/jeongueop/image/app_icon.png", mImageLoader);
			} else {
				infoImageView.setImageUrl(ManageNetwork.SERVER_SRC
						+ "/works/jeongueop/image/info/" + infoNode.imageURL
						+ ".png", mImageLoader);
			}
			/*
		infoImageView = (ImageView) findViewById(R.id.info_image);
		infoImageView.setImageResource(getBitmapResource(infoNode.imageURL));
		*/
	}
	private int getBitmapResource(String imageID){		
		return getResources().getIdentifier(imageID,"drawable", "org.kdm.gogonaejangmt");
	}
	
	private void initButton(){
		infoDetailCloseButton = (ImageView) findViewById(R.id.info_detailCloseButton);
		infoDetailCloseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
	}
}
