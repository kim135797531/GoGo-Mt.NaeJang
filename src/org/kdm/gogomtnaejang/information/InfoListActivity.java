package org.kdm.gogomtnaejang.information;

import java.util.ArrayList;

import org.kdm.gogomtnaejang.node.InfoNode;
import org.kdm.gogomtnaejang.node.ManageNode;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.app.ProgressDialog;
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

public class InfoListActivity extends Activity {

	private InfoListAdapter mAdapter;
	private ListView mInfoListView;
	private Button infoPrevButton;
	private TextView infoPageNumView;
	private Button infoNextButton;
	private RelativeLayout footer;

	private int curPage;
	private ArrayList<InfoNode> infoList;
	private ProgressDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSpinnerDialog();
		setContentView(R.layout.activity_info_list);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				initInfoButton();
				initInfoList();
				initInfoView();
				initOnItemClickListener();
				if(loadingDialog != null)
					loadingDialog.dismiss();
			}
		}, 300);

		ManageNode.getInst(this).inputInfoNodeData();
	}

	private void initSpinnerDialog() {
		loadingDialog = new ProgressDialog(this);
		loadingDialog.setIcon(R.drawable.app_icon);
		loadingDialog.setTitle("가자! 내장산");
		loadingDialog.setMessage("편의시설 정보를 불러오고 있습니다.");
		loadingDialog.show();
	}

	private void initInfoButton() {
		infoPrevButton = (Button) findViewById(R.id.infoPrevButton);
		infoNextButton = (Button) findViewById(R.id.infoNextButton);
		infoPageNumView = (TextView) findViewById(R.id.infoPageNum);

		infoPrevButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				prevButtonFunc();
			}
		});

		infoNextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				nextButtonFunc();
			}
		});
	}

	private void prevButtonFunc() {
		if (curPage == 11) {
			Toast.makeText(this, "첫 페이지입니다.", Toast.LENGTH_LONG).show();
		} else {
			curPage--;
			infoPageNumView.setText(((Integer)(curPage-10)).toString());
			int first = getFirstNum(curPage);
			int end = getEndNum(curPage);
			//mAdapter.setItem(ManageNode.getInst(this).getRangeInfoDocument(first, end));
			mAdapter = new InfoListAdapter(this, ManageNode.getInst(this).getRangeInfoDocument(first, end));
			mInfoListView.setAdapter(mAdapter);
		}
	}

	private void nextButtonFunc() {
		if (getEndNum(curPage) >= ManageNode.getInst(this).getMaxInfoNodeCount()+100) {
			Toast.makeText(this, "마지막 페이지입니다.", Toast.LENGTH_LONG).show();
		} else {
			curPage++;
			infoPageNumView.setText(((Integer)(curPage-10)).toString());
			int first = getFirstNum(curPage);
			int end = getEndNum(curPage);
			//mAdapter.setItem(ManageNode.getInst(this).getRangeInfoDocument(first, end));
			mAdapter = new InfoListAdapter(this, ManageNode.getInst(this).getRangeInfoDocument(first, end));
			mInfoListView.setAdapter(mAdapter);
			
		}
	}

	private void initInfoList() {
		curPage = 11;
		int first = getFirstNum(curPage);
		int end = getEndNum(curPage);
		infoList = ManageNode.getInst(this).getRangeInfoDocument(first, end);
		mAdapter = new InfoListAdapter(this, infoList);
	}

	private int getFirstNum(int curPage) {
		return (curPage - 1) * 10;
	}

	private int getEndNum(int curPage) {
		int ret = (curPage * 10);

		if (ret > ManageNode.getInst(this).getMaxInfoNodeCount()+100) {
			return ManageNode.getInst(this).getMaxInfoNodeCount()+100;
		} else {
			return (curPage * 10);
		}
	}

	private void initInfoView() {
		mInfoListView = (ListView) findViewById(R.id.infoList);
		footer = (RelativeLayout) getLayoutInflater().inflate(R.layout.footer,
				null);
		mInfoListView.addFooterView(footer, null, false);
		mInfoListView.setAdapter(mAdapter);
	}

	private void initOnItemClickListener() {
		mInfoListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent r = new Intent(InfoListActivity.this,
						InfoListDetailActivity.class);
				int intID = (int) id;
				r.putExtra("infoID", intID);
				startActivity(r);
			}

		});
	}
}