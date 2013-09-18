package org.kdm.gogomtnaejang.community;

import org.kdm.gogomtnaejang.network.ManageNetwork;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BoardWriteActivity extends Activity{

	private Button communityWriteCategory;
	private Button communityWriteSendButton;
	private Button communityWritePicture;
	private TextView communityWritePicutreSrc;
	private EditText communityWriteSubject;
	private EditText communityWriteContent;	
	
	private static int curCategory = 0;
	private Uri imageURI = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community_write);
		initButton();
		initEditText();
		initCategoryDialog();
	}
	
	private void initButton(){
		communityWriteCategory = (Button) findViewById(R.id.communityWriteCategory);
		communityWriteSendButton = (Button) findViewById(R.id.communityWriteSendButton);
		communityWritePicture = (Button) findViewById(R.id.communityWritePicture);
		communityWritePicutreSrc = (TextView) findViewById(R.id.communityWritePictureSrc);
		
		communityWriteCategory.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				initCategoryDialog().show();
			}
		});		
		communityWriteSendButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				BoardDocument document = new BoardDocument();
				document.category = curCategory+1;
				document.title = communityWriteSubject.getText().toString();
				document.content = communityWriteContent.getText().toString();
				// TODO : IMEI 받아오기 구현
				document.IMEI = "123123123";
				if (imageURI == null) {
					document.imageURL = "NO IMAGE";
				} else {
					document.imageURL = imageURI.toString();
				}
				sendDocument(document);
				Toast.makeText(BoardWriteActivity.this, "글이 등록되었습니다.", Toast.LENGTH_LONG).show();
				finish();
			}
		});
		
		communityWritePicture.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {	
				Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(gallery, 0);
			}
		});
	}
	
	private void initEditText(){
		communityWriteSubject = (EditText) findViewById(R.id.CommunityWriteSubject);
		communityWriteContent = (EditText) findViewById(R.id.CommunityWriteContent);
	}
	
	private void sendDocument(BoardDocument document){
		ManageNetwork.getInst().sendDocumentFunc(document);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CANCELED)
			return;
		
		imageURI = data.getData();
		String[] path = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(imageURI, path, null, null, null);
		cursor.moveToFirst();
		int index = cursor.getColumnIndex(path[0]);
		String path2 = cursor.getString(index);
		cursor.close();
		imageURI = Uri.parse(path2);
		
		communityWritePicutreSrc.setText(path2);
		Log.e("err", path2);
	}

	private Dialog initCategoryDialog(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String[] categoryList = new String[ManageDocument.NUM_OF_CATEGORY-1];
		for(int i=0; i<ManageDocument.NUM_OF_CATEGORY-1; i++)
			categoryList[i] = ManageDocument.getInst().getCategoryString(i+1);
		
	    builder.setSingleChoiceItems(categoryList, curCategory, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				BoardWriteActivity.setCurCategory(which);
			}
	    });
	    builder.setTitle("카테고리를 선택하세요");
	    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
	    Dialog dia = builder.create();
	    return dia;
	}
	
	public static void setCurCategory(int category){
		curCategory = category;
	}
}