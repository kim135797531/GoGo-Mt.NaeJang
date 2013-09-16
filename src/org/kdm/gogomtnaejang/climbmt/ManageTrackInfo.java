package org.kdm.gogomtnaejang.climbmt;

import java.util.ArrayList;

import org.kdm.gogomtnaejang.MainActivity;
import org.kdm.gogonaejangmt.R;

import android.content.Context;
import android.util.SparseArray;

public class ManageTrackInfo {
	
	private static ManageTrackInfo mManageTrackInfo = null;
	private SparseArray<Track> trackList = null;
	
	private ManageTrackInfo(Context context){
		trackList = new SparseArray<Track>();
		inputData(context, trackList);
	}
	
	public static ManageTrackInfo getInst(Context context){
		if(mManageTrackInfo == null)
			mManageTrackInfo = new ManageTrackInfo(context);
		
		return mManageTrackInfo;
	}

	private void inputData(Context context, SparseArray<Track> trackList){
		Track track_0 = new Track(0);
		track_0.thumbImageID = "ic_launcher";
		track_0.imageID = "ic_launcher";
		track_0.title = "제목 테스트 1";
		track_0.description = "1->2->3";
		ArrayList<Integer> trackData_0 = new ArrayList<Integer>();
		for(int i=1; i<50; i++){
			trackData_0.add(i);
		}
		track_0.trackData = trackData_0;
		trackList.put(0, track_0);
		trackList.put(1, track_0);
	}
	
	public ArrayList<Integer> getOneTrackList(int trackID){
		return trackList.get(trackID).trackData;
	}
	
	public ArrayList<Track> getAllTrackList(){
		if(trackList != null){
			ArrayList<Track> ret = new ArrayList<Track>();
			for(int i=0; i<trackList.size(); i++){
				ret.add(trackList.get(i));
			}
			return ret;
		}
		
		return null;
	}	

	
}
