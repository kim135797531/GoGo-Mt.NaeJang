package org.kdm.gogomtnaejang.information;

import org.kdm.gogomtnaejang.node.ManageNode;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class InfoReadActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		ManageNode.getInst(this).inputInfoNodeData();
		Toast.makeText(this, "현재 개발중입니다.", Toast.LENGTH_LONG).show();
	}
}
