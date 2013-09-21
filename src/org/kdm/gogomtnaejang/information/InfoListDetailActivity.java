package org.kdm.gogomtnaejang.information;

import org.kdm.gogomtnaejang.node.InfoNode;
import org.kdm.gogomtnaejang.node.ManageNode;
import org.kdm.gogonaejangmt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class InfoListDetailActivity extends Activity{
	
	private int infoID;
	private InfoNode infoNode;
	private TextView infoNameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		infoID = intent.getIntExtra("infoID", 0);
		infoNode = ManageNode.getInst(this).getOneInfoDocument(infoID);
		
		setContentView(R.layout.activity_info_list_detail);
		infoNameView = (TextView) findViewById(R.id.info_name);
		infoNameView.setText(infoNode.name);		
	}
}
