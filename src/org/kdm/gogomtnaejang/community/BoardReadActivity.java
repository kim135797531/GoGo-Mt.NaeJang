package org.kdm.gogomtnaejang.community;

import org.kdm.gogomtnaejang.climbmt.ClimbMapActivity;
import org.kdm.gogomtnaejang.climbmt.TrackListDetailActivity;
import org.kdm.gogomtnaejang.network.ManageNetwork;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BoardReadActivity extends Activity {

	private int documentID;
	private BoardDocument document;

	private TextView communityDocumentTitle;
	private TextView communityDocumentCategory;
	private TextView communityDocumentTime;
	private ImageView communityDocumentImage;
	private TextView communityDocumentContent;

	private ImageView detailCloseButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community_read);

		Intent intent = getIntent();
		documentID = intent.getIntExtra("documentID", 0);
		document = ManageDocument.getInst().getDocument(documentID);

		setContentView(R.layout.activity_community_read);
		initDescription();
		initImage();
		initButton();
	}

	private void initDescription() {
		communityDocumentTitle = (TextView) findViewById(R.id.community_document_title);
		communityDocumentCategory = (TextView) findViewById(R.id.community_document_category);
		communityDocumentTime = (TextView) findViewById(R.id.community_document_time);
		communityDocumentContent = (TextView) findViewById(R.id.community_document_content);

		communityDocumentTitle.setText("제목 : " + document.title);
		communityDocumentCategory
				.setText("카테고리 : "
						+ ManageDocument.getInst().getCategoryString(
								document.category));
		communityDocumentTime.setText("작성 시각 : " + document.time);
		communityDocumentContent.setText(document.content);
	}

	private void initImage() {
		communityDocumentImage = (ImageView) findViewById(R.id.community_document_image);
		communityDocumentImage.setImageBitmap(ManageNetwork.getInst()
				.downloadOneImageFunc(document.imageURL));

		if (document.imageURL.equalsIgnoreCase("No Image")) {
			communityDocumentImage.setImageResource(R.drawable.app_icon);
		} else {
			try {
				Bitmap bitmap = ManageNetwork.getInst().downloadOneImageFunc(
						document.imageURL);
				communityDocumentImage.setImageBitmap(bitmap);
			} catch (Exception ex) {
				communityDocumentImage.setImageResource(R.drawable.app_icon);
			}
		}
	}

	private void initButton(){
		detailCloseButton = (ImageView) findViewById(R.id.detailCloseButton);
		detailCloseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
	}
}