package org.kdm.gogomtnaejang.community;

import java.util.ArrayList;

import org.kdm.gogomtnaejang.network.ManageNetwork;
import org.kdm.gogomtnaejang.volley.FadeInNetworkImageView;
import org.kdm.gogonaejangmt.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

public class BoardCommentAdapter extends BaseAdapter {

	private Context mContext = null;
	private ArrayList<BoardComment> commentList = null;
	
	public BoardCommentAdapter(Context context,
			ArrayList<BoardComment> commentList) {
		this.mContext = context;
		this.commentList = commentList;
	}

	@Override
	public int getCount() {
		return commentList.size();
	}

	@Override
	public Object getItem(int position) {
		return commentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void setItem(ArrayList<BoardComment> commentList) {
		this.commentList = commentList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int finalPosition = position;
		RelativeLayout ret = null;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ret = (RelativeLayout) layoutInflater.inflate(
					R.layout.view_comment_list_item, null);
		} else {
			ret = (RelativeLayout) convertView;
		}

		TextView infoName = (TextView) ret.findViewById(R.id.commentListName);
		TextView infoDescription = (TextView) ret
				.findViewById(R.id.commentListComment);		
		ImageView communityCommentDeleteButton = (ImageView) ret.findViewById(R.id.commentListDeleteButton);

		BoardComment comment = commentList.get(position);
		infoName.setText(comment.nickName);
		infoDescription.setText(comment.comment);
		communityCommentDeleteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checkPassword(finalPosition);
			}
		});
		
		return ret;
	}
	
	private void checkPassword(int position) {
		final int finalPosition = position;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(
				R.layout.dialog_document_delete_password, null);

		AlertDialog.Builder alertFarBuilder = new AlertDialog.Builder(mContext);
		alertFarBuilder.setView(layout);
		alertFarBuilder.setMessage("댓글 작성시 입력하신 비밀번호를 입력해 주세요.");
		alertFarBuilder.setPositiveButton("삭제",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						EditText input = (EditText) layout
								.findViewById(R.id.CommunityWritePasswordDialog);
						String password = input.getText().toString();
						boolean isValid = ManageNetwork.getInst()
								.checkCommentPasswordAndDelete(commentList.get(finalPosition).id,
										password);
						if (isValid) {
							Toast.makeText(mContext,
									"댓글이 삭제되었습니다.", Toast.LENGTH_LONG).show();
							commentList.remove(finalPosition);
							LinearLayout ll = BoardReadActivity.communityCommentList;
							if(((LinearLayout) ll).getChildCount() > 0) 
							    ((LinearLayout) ll).removeAllViews(); 

							for (int i = 0; i < BoardCommentAdapter.this.getCount(); i++) {
								  View item = BoardCommentAdapter.this.getView(i, null, null);
								  ll.addView(item);
							}
							
							dialog.dismiss();
						} else {
							Toast.makeText(mContext,
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
		alert1.setTitle("댓글 삭제");
		alert1.show();
	}
}
