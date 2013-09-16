package org.kdm.gogomtnaejang.climbmt;

import java.util.ArrayList;

import org.kdm.gogomtnaejang.node.ManageNode;
import org.kdm.gogomtnaejang.node.Path;
import org.kdm.gogomtnaejang.node.SimpleNode;

import android.util.Log;

import com.nhn.android.maps.overlay.NMapPathData;
import com.nhn.android.maps.overlay.NMapPathLineStyle;
import com.nhn.android.mapviewer.overlay.NMapPathDataOverlay;

public class ManageOverlay {
	private ManageOverlay mManageOverlay = null;
	
	private ManageOverlay(){}
	
	public ManageOverlay getInst(){
		if(mManageOverlay == null)
			mManageOverlay = new ManageOverlay();
		
		return mManageOverlay;
	}
	
}
