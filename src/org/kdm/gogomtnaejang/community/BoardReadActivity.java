package org.kdm.gogomtnaejang.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kdm.gogomtnaejang.network.ManageNetwork;
import org.kdm.gogomtnaejang.volley.FadeInNetworkImageView;
import org.kdm.gogomtnaejang.volley.ManageVolley;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

public class BoardReadActivity extends Activity {

	private int documentID;
	private BoardDocument document;

	private ScrollView communityDocumentScrollView;

	private TextView communityDocumentTitle;
	private TextView communityDocumentCategory;
	private TextView communityDocumentNickName;
	private TextView communityDocumentTime;
	private FadeInNetworkImageView communityDocumentImage;
	private TextView communityDocumentContent;

	private ImageView detailCloseButton;
	private Button communityDocumentDelete;

	private BoardCommentAdapter mAdapter;
	private ArrayList<BoardComment> commentList;
	public static LinearLayout communityCommentList;
	private EditText communityCommentNickName;
	private EditText communityCommentPassword;
	private EditText communityCommentContent;
	private Button communityCommentWriteButton;

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
		initCommentList();
		initWriteComment();
		loadComment();
	}

	private void initCommentList() {
		commentList = new ArrayList<BoardComment>();
		mAdapter = new BoardCommentAdapter(this, commentList);
		communityCommentList = (LinearLayout) findViewById(R.id.community_document_comment);
	}

	private void loadComment() {
		RequestQueue queue = ManageVolley.getInst(this).getRequestQueue();
		queue.cancelAll(this);
		StringRequest myReq = new StringRequest(Method.POST,
				ManageNetwork.SERVER_SRC
						+ "/works/jeongueop/getAllCommentListByNum.php",
				createLoadCommentListSuccessListener(), null) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("u_document_id", ((Integer) documentID).toString());
				return params;
			}
		};
		queue.add(myReq);
	}

	private Response.Listener<String> createLoadCommentListSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (response.equalsIgnoreCase("null"))
					return;

				try {
					commentList.clear();
					JSONArray documentEntries = new JSONArray(response);
					JSONObject entry;
					for (int i = 0; i < documentEntries.length(); i++) {
						entry = documentEntries.getJSONObject(i);

						BoardComment boardComment = new BoardComment();
						boardComment.id = entry.getInt("id");
						boardComment.nickName = entry.getString("nickname");
						boardComment.time = entry.getString("time");
						boardComment.comment = entry.getString("comment");
						commentList.add(boardComment);
					}
					mAdapter.setItem(commentList);
					mAdapter.notifyDataSetChanged();
					updateCommentLayout();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	}

	private void updateCommentLayout() {
		if (((LinearLayout) communityCommentList).getChildCount() > 0)
			((LinearLayout) communityCommentList).removeAllViews();

		for (int i = 0; i < mAdapter.getCount(); i++) {
			View item = mAdapter.getView(i, null, null);
			communityCommentList.addView(item);
		}
	}

	private void initWriteComment() {
		communityCommentNickName = (EditText) findViewById(R.id.community_document_comment_write_nickname);
		communityCommentPassword = (EditText) findViewById(R.id.community_document_comment_write_password);
		communityCommentContent = (EditText) findViewById(R.id.community_document_comment_write_comment);
		communityCommentWriteButton = (Button) findViewById(R.id.community_document_comment_write_button);

		communityCommentWriteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RequestQueue queue = ManageVolley.getInst(
						BoardReadActivity.this).getRequestQueue();
				queue.cancelAll(this);
				StringRequest myReq;
				myReq = new StringRequest(Method.POST, ManageNetwork.SERVER_SRC
						+ "/works/jeongueop/insertNewComment.php",
						createInsertCommentSuccessListener(), null) {
					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						Map<String, String> params = new HashMap<String, String>();
						params.put("u_document_id",
								((Integer) documentID).toString());
						params.put("u_nickname", communityCommentNickName
								.getText().toString());
						params.put("u_password", communityCommentPassword
								.getText().toString());
						params.put("u_comment", communityCommentContent
								.getText().toString());

						return params;
					}
				};

				queue.add(myReq);
			}
		});

	}

	private Response.Listener<String> createInsertCommentSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					loadComment();
					communityCommentNickName.setText("");
					communityCommentPassword.setText("");
					communityCommentContent.setText("");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}

	private void initDescription() {
		communityDocumentScrollView = (ScrollView) findViewById(R.id.community_document_scrollView);
		communityDocumentTitle = (TextView) findViewById(R.id.community_document_title);
		communityDocumentCategory = (TextView) findViewById(R.id.community_document_category);
		communityDocumentNickName = (TextView) findViewById(R.id.community_document_nickname);
		communityDocumentTime = (TextView) findViewById(R.id.community_document_time);
		communityDocumentContent = (TextView) findViewById(R.id.community_document_content);

		communityDocumentScrollView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
			    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
				return false;
			}
		});
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
		communityDocumentImage = (FadeInNetworkImageView) findViewById(R.id.community_document_image);
		ImageLoader mImageLoader = ManageVolley.getInst(this).getImageLoader();
		if (document.imageURL.equalsIgnoreCase("NO IMAGE")) {
			communityDocumentImage.setImageUrl(ManageNetwork.SERVER_SRC
					+ "/works/jeongueop/image/app_icon.png", mImageLoader);
		} else {
			communityDocumentImage.setImageUrl(ManageNetwork.SERVER_SRC
					+ "/works/jeongueop/" + document.imageURL, mImageLoader);
		}

		/*
		 * communityDocumentImage = (ImageView)
		 * findViewById(R.id.community_document_image);
		 * communityDocumentImage.setImageBitmap(ManageNetwork.getInst()
		 * .downloadOneImageFunc( ManageNetwork.SERVER_SRC + "/works/jeongueop/"
		 * + document.imageURL));
		 * 
		 * if (document.imageURL.equalsIgnoreCase("No Image")) {
		 * communityDocumentImage.setImageResource(R.drawable.app_icon); }
		 */
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
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(
				R.layout.dialog_document_delete_password, null);

		AlertDialog.Builder alertFarBuilder = new AlertDialog.Builder(this);
		alertFarBuilder.setView(layout);
		alertFarBuilder.setMessage("글 작성시 입력하신 비밀번호를 입력해 주세요.");
		alertFarBuilder.setPositiveButton("삭제",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						EditText input = (EditText) layout
								.findViewById(R.id.CommunityWritePasswordDialog);
						String password = input.getText().toString();
						boolean isValid = ManageNetwork.getInst()
								.checkDocumentPasswordAndDelete(documentID,
										password);
						if (isValid) {
							Toast.makeText(BoardReadActivity.this,
									"글이 삭제되었습니다.", Toast.LENGTH_LONG).show();
							dialog.dismiss();
							finish();
						} else {
							Toast.makeText(BoardReadActivity.this,
									"비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
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