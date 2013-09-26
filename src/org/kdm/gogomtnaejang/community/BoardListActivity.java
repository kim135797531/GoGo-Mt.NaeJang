package org.kdm.gogomtnaejang.community;

import java.util.ArrayList;

import org.kdm.gogomtnaejang.MainActivity;
import org.kdm.gogomtnaejang.StartLoadingActivity;
import org.kdm.gogomtnaejang.climbmt.ManageTrackInfo;
import org.kdm.gogomtnaejang.node.ManageNode;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BoardListActivity extends Activity {

	private BoardListAdapter mAdapter;
	private ListView mCommunityListView;
	private Button communityPrevButton;
	private TextView communityPageNumView;
	private Button communityNextButton;
	private Button communityCategoryButton;
	private Button communityWriteButton;
	private RelativeLayout footer;

	private int curPage;
	private static int curCategory;
	private ArrayList<BoardDocument> documentList;

	private ProgressDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSpinnerDialog();
		setContentView(R.layout.activity_community_list);
		new Handler().postDelayed(
				new Runnable() {					
					@Override
					public void run(){
						initCommunityButton();
						initCommunityList();
						initCommunityView();
						initOnItemClickListener();
						initCategoryDialog();
						if(loadingDialog != null)
							loadingDialog.dismiss();
					}
				},300);
	}
	
	private void initSpinnerDialog(){
		loadingDialog = new ProgressDialog(this);
		loadingDialog.setIcon(R.drawable.app_icon);					
		loadingDialog.setTitle("가자! 내장산");
		loadingDialog.setMessage("온라인 커뮤니티에 접속하고 있습니다...");
		loadingDialog.show();
	}
	
	private void initCommunityButton(){
		communityPrevButton = (Button) findViewById(R.id.communityPrevButton);
		communityPageNumView = (TextView) findViewById(R.id.communityPageNum);
		communityNextButton = (Button) findViewById(R.id.communityNextButton);
		communityCategoryButton = (Button) findViewById(R.id.communityCategoryButton);
		communityWriteButton = (Button) findViewById(R.id.communityWriteButton);
		
		communityPrevButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				prevButtonFunc();
			}
		});
		communityNextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				nextButtonFunc();
			}
		});
		communityCategoryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initCategoryDialog().show();
			}
		});
		communityWriteButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent writeDocument = new Intent(BoardListActivity.this, BoardWriteActivity.class);
				BoardListActivity.this.startActivityForResult(writeDocument, 0);				
			}
		});
	}
	
	private void prevButtonFunc(){
		if(curPage == 1){
			Toast.makeText(this, "첫 페이지입니다.", Toast.LENGTH_LONG).show();
		}
		else{
			curPage--;
			communityPageNumView.setText(((Integer)curPage).toString());
			int first = getFirstNum(curPage);
			int end = getEndNum(curPage);
			mAdapter.setItem(ManageDocument.getInst().getRangeDocument(curCategory, first, end));
			mAdapter.notifyDataSetInvalidated();
		}
	}
	
	private void nextButtonFunc(){
		if(getEndNum(curPage) >= ManageDocument.getInst().getMaxDocumentCount(curCategory)){
			Toast.makeText(this, "마지막 페이지입니다.", Toast.LENGTH_LONG).show();
		}
		else{
			curPage++;
			communityPageNumView.setText(((Integer)curPage).toString());
			int first = getFirstNum(curPage);
			int end = getEndNum(curPage);
			mAdapter.setItem(ManageDocument.getInst().getRangeDocument(curCategory, first, end));
			mAdapter.notifyDataSetInvalidated();
		}
	}
	
	private void curPageRefershFunc(){
		int first = getFirstNum(curPage);
		int end = getEndNum(curPage);
		mAdapter.setItem(ManageDocument.getInst().getRangeDocument(curCategory, first, end));
		mAdapter.notifyDataSetInvalidated();	
	}
	
	private void changeCategoryFunc(int category){
		curPage = 1;
		curCategory = category;
		ManageDocument.getInst().getMaxDocumentCount(category);
		int first = getFirstNum(curPage);
		int end = getEndNum(curPage);
		documentList = ManageDocument.getInst().getRangeDocument(category, first, end);
		mAdapter.setItem(documentList);
		mAdapter.notifyDataSetInvalidated();
	}

	private void initCommunityList() {
		curPage = 1;
		curCategory = 0;
		int first = getFirstNum(curPage);
		int end = getEndNum(curPage);
		documentList = ManageDocument.getInst().getRangeDocument(0, first, end);
		mAdapter = new BoardListAdapter(this, documentList);
	}

	private int getFirstNum(int curPage) {
		return (curPage - 1) * 10;
	}

	private int getEndNum(int curPage) {
		int ret = (curPage * 10);

		if (ret > ManageDocument.getInst().getMaxDocumentCount(curCategory)) {
			return ManageDocument.getInst().getMaxDocumentCount(curCategory);
		} else {
			return (curPage * 10);
		}
	}

	private void initCommunityView() {
		mCommunityListView = (ListView) findViewById(R.id.communityList);
		footer = (RelativeLayout) getLayoutInflater().inflate(R.layout.footer,
				null);
		mCommunityListView.addFooterView(footer, null, false);
		mCommunityListView.setAdapter(mAdapter);
	}

	private void initOnItemClickListener() {
		mCommunityListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,	int position, long id) {
				if(id != -1){
					Intent r = new Intent(BoardListActivity.this,
							BoardReadActivity.class);
					int intID = (int) id;
					r.putExtra("documentID", intID);
					startActivityForResult(r, 0);
				}
			}
		});
	}
	
	private Dialog initCategoryDialog(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String[] categoryList = new String[ManageDocument.NUM_OF_CATEGORY];
		categoryList[0] = "전체 보기";
		for(int i=1; i<ManageDocument.NUM_OF_CATEGORY; i++)
			categoryList[i] = ManageDocument.getInst().getCategoryString(i);
		
	    builder.setSingleChoiceItems(categoryList, 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				BoardListActivity.setCurCategory(which);
			}
	    });
	    builder.setTitle("카테고리를 선택하세요");
	    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int category = BoardListActivity.getCurCategory();
				changeCategoryFunc(category);				
			}
		});
	    Dialog dia = builder.create();
	    return dia;
	}
	
	public static int getCurCategory(){
		return curCategory;
	}
	
	public static void setCurCategory(int category){
		curCategory = category;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		curPageRefershFunc();
	}
}
