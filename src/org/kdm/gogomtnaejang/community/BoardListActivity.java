package org.kdm.gogomtnaejang.community;

import java.util.ArrayList;

import org.kdm.gogomtnaejang.MainActivity;
import org.kdm.gogomtnaejang.StartLoadingActivity;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	private int curCategory;
	private ArrayList<BoardDocument> documentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community_list);
		initCommunityButton();
		initCommunityList();
		initCommunityView();
		initOnItemClickListener();
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
				changeCategoryFunc();
			}
		});
		communityWriteButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent writeDocument = new Intent(BoardListActivity.this, BoardWriteActivity.class);
				BoardListActivity.this.startActivity(writeDocument);				
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
		if(getEndNum(curPage) >= ManageDocument.getInst().getMaxDocumentCount()){
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
	
	private void changeCategoryFunc(){
		//TODO: 다이얼로그 띄워야함
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

		if (ret > ManageDocument.getInst().getMaxDocumentCount()) {
			return ManageDocument.getInst().getMaxDocumentCount();
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
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent r = new Intent(BoardListActivity.this,
						BoardReadActivity.class);
				int intID = (int) id;
				r.putExtra("documentID", intID);
				startActivity(r);
			}
		});
	}
}
