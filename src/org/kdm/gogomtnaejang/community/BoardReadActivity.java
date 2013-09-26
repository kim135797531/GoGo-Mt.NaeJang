package org.kdm.gogomtnaejang.community;

import org.kdm.gogomtnaejang.network.ManageNetwork;
import org.kdm.gogonaejangmt.R;
import org.kdm.gogonaejangmt.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BoardReadActivity extends Activity {

	private int documentID;
	private BoardDocument document;

	private TextView communityDocumentTitle;
	private TextView communityDocumentCategory;
	private TextView communityDocumentNickName;
	private TextView communityDocumentTime;
	private ImageView communityDocumentImage;
	private TextView communityDocumentContent;

	private ImageView detailCloseButton;
	private Button communityDocumentDelete;

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
		communityDocumentNickName = (TextView) findViewById(R.id.community_document_nickname);
		communityDocumentTime = (TextView) findViewById(R.id.community_document_time);
		communityDocumentContent = (TextView) findViewById(R.id.community_document_content);

		communityDocumentTitle.setText("제목 : " + document.title);
		communityDocumentCategory
				.setText("카테고리 : "
						+ ManageDocument.getInst().getCategoryString(
								document.category));
		communityDocumentNickName.setText("작성자 : " + document.nickName);
		communityDocumentTime.setText("작성 시각 : " + document.time);
		communityDocumentContent.setText(document.content);
	}

	private void initImage() {
		communityDocumentImage = (ImageView) findViewById(R.id.community_document_image);
		communityDocumentImage.setImageBitmap(ManageNetwork.getInst()
				.downloadOneImageFunc(
						ManageNetwork.SERVER_SRC + "/works/jeongueop/"
								+ document.imageURL));

		if (document.imageURL.equalsIgnoreCase("No Image")) {
			communityDocumentImage.setImageResource(R.drawable.app_icon);
		}
	}

	private void initButton() {
		detailCloseButton = (ImageView) findViewById(R.id.detailCloseButton);
		detailCloseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		communityDocumentDelete = (Button) findViewById(R.id.community_document_delete);
		communityDocumentDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checkPassword();
			}
		});
	}

	private void checkPassword() {
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.dialog_document_delete_password, null);
		
		AlertDialog.Builder alertFarBuilder = new AlertDialog.Builder(this);
		alertFarBuilder.setView(layout);
		alertFarBuilder.setMessage("글 작성시 입력하신 비밀번호를 입력해 주세요.");
		alertFarBuilder.setPositiveButton("삭제",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						EditText input = (EditText)layout.findViewById(R.id.CommunityWritePasswordDialog);
						String password = input.getText().toString();						
						boolean isValid = ManageNetwork.getInst().checkDocumentPasswordAndDelete(documentID, password);
						if(isValid){
							Toast.makeText(BoardReadActivity.this, "글이 삭제되었습니다.", Toast.LENGTH_LONG).show();
							dialog.dismiss();
							finish();
						}
						else{
							Toast.makeText(BoardReadActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
						}
					}
				});
		alertFarBuilder.setNegativeButton("취소",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		AlertDialog alert1 = alertFarBuilder.create();
		alert1.setTitle("글 삭제");
		alert1.show();
	}
}