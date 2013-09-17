package org.kdm.gogomtnaejang.community;

import java.util.ArrayList;

import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class BoardListActivity extends Activity{
	
	private BoardListAdapter mAdapter;
	private ListView mCommunityListView;
	private RelativeLayout footer;
	
	private int curPage;
	private ArrayList<BoardDocument> documentList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community_list);
		initCommunityList();
		initCommunityView();
		initOnItemClickListener();
	}
	
	private void initCommunityList(){
		curPage = 1;
		int first = getFirstNum(curPage);
		int end = getEndNum(curPage);
		documentList = ManageDocument.getInst().getRangeDocument(0, first, end);
		mAdapter = new BoardListAdapter(this, documentList);
	}
	
	private int getFirstNum(int curPage){
		return (curPage-1)*10;
	}
	
	private int getEndNum(int curPage){
		int ret = (curPage*10);
		
		if(ret > ManageDocument.getInst().getMaxDocumentCount()){
			return ManageDocument.getInst().getMaxDocumentCount();
		}
		else{
			return (curPage*10);
		}
	}
	
	private void initCommunityView(){
		mCommunityListView = (ListView) findViewById(R.id.communityList);
		footer = (RelativeLayout) getLayoutInflater().inflate(R.layout.footer, null);
		mCommunityListView.addFooterView(footer, null, false);
		mCommunityListView.setAdapter(mAdapter);
	}
	
	private void initOnItemClickListener(){
		mCommunityListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id){
				Intent r = new Intent(BoardListActivity.this, BoardReadActivity.class);
				int intID = (int) id; 
				r.putExtra("docuID", intID);
				startActivity(r);
			}
		});
	}
}
