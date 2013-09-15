package org.kdm.gogomtnaejang.climbmt;

import java.util.ArrayList;

import android.text.TextUtils.TruncateAt;
import android.util.SparseArray;

public class ManageTrackInfo {
	
	private static ManageTrackInfo mManageTrackInfo = null;
	private SparseArray<ArrayList<Integer>> trackList = null;
	
	private ManageTrackInfo(){
		trackList = new SparseArray<ArrayList<Integer>>();
		inputData(trackList);
	}
	
	public static ManageTrackInfo getInst(){
		if(mManageTrackInfo == null)
			mManageTrackInfo = new ManageTrackInfo();
		
		return mManageTrackInfo;
	}

	private void inputData(SparseArray<ArrayList<Integer>> trackList){
		ArrayList<Integer> track_1 = new ArrayList<Integer>();
		track_1.add(11);
		track_1.add(22);
		track_1.add(33);
		trackList.put(1, track_1);
	}
	
	public ArrayList<Integer> getOneTrackList(int id){
		return trackList.get(id);
	}
	
	public SparseArray<ArrayList<Integer>> getAllTrackList(){
		if(trackList != null){
			return trackList;
		}
		
		return null;
	}	

	
}
