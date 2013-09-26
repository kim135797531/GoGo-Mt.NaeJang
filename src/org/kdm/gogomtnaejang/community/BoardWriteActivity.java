package org.kdm.gogomtnaejang.community;

import org.kdm.gogomtnaejang.network.ManageNetwork;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	private EditText communityWriteNickName;
	private EditText communityWritePassword;
	private EditText communityWriteSubject;
	private EditText communityWriteContent;
	
	private Handler handler;
	private ProgressDialog progressDialog;
	
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
				document.nickName = communityWriteNickName.getText().toString();
				document.password = communityWritePassword.getText().toString();
				document.title = communityWriteSubject.getText().toString();
				document.content = communityWriteContent.getText().toString();
				if(document.nickName.length() == 0){
					Toast.makeText(BoardWriteActivity.this, "닉네임을 입력해 주세요.", Toast.LENGTH_LONG).show();
					return;
				}
				else if(document.nickName.length() > 10){
					Toast.makeText(BoardWriteActivity.this, "닉네임은 10자 이내이어야 합니다.", Toast.LENGTH_LONG).show();
					return;
				}
				else if(document.password.length() == 0){
					Toast.makeText(BoardWriteActivity.this, "비밀번호를 입력해 주세요.", Toast.LENGTH_LONG).show();
					return;
				}
				else if(document.password.length() > 20){
					Toast.makeText(BoardWriteActivity.this, "비밀번호는 20자 이내이어야 합니다.", Toast.LENGTH_LONG).show();
					return;
				}
				else if(document.title.length() == 0){
					Toast.makeText(BoardWriteActivity.this, "제목을 입력해 주세요.", Toast.LENGTH_LONG).show();
					return;
				}
				else if(document.content.length() == 0){
					Toast.makeText(BoardWriteActivity.this, "내용을 입력해 주세요.", Toast.LENGTH_LONG).show();
					return;
				}

				showDialog(1);
				if (imageURI == null) {
					document.imageURL = "NO IMAGE";
				} else {
					document.imageURL = imageURI.toString();
				}
				ManageNetwork.getInst().sendDocumentFunc(document);
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
		communityWriteNickName = (EditText) findViewById(R.id.CommunityWriteNickName);
		communityWritePassword = (EditText) findViewById(R.id.CommunityWritePassword);
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
	
	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == 1) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setTitle("가자! 내장산");
			progressDialog.setIcon(R.drawable.app_icon);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setIndeterminate(false);
			progressDialog.setMessage("전송중입니다...");
			return progressDialog;
		}
			
		return null;
	}
}