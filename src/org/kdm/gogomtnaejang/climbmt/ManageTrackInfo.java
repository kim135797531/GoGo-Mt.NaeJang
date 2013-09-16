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
		Track track;
		
		track = new Track(0);
		track.thumbImageID = "track_thumb_image_0";
		track.imageID = "track_image_0";
		track.title = "자연관찰로코스";
		track.description = "일주문~벽련암~원적암~내장사~일주문";
		track.time = "1시간 20분";
		track.distance = "3.56km";
		track.trackData.add(29);
		track.trackData.add(23);
		track.trackData.add(3);
		track.trackData.add(2);
		trackList.put(track.id, track);
		
		track = new Track(1);
		track.thumbImageID = "track_thumb_image_1";
		track.imageID = "track_image_1";
		track.title = "서래봉코스";
		track.description = "일주문~벽련암~서래봉~불출봉~원적암~내장사~일주문";
		track.time = "4시간";
		track.distance = "5.65km";
		track.trackData.add(29);
		track.trackData.add(37);
		track.trackData.add(28);
		track.trackData.add(30);
		track.trackData.add(22);
		track.trackData.add(3);
		track.trackData.add(2);
		trackList.put(track.id, track);
		
		track = new Track(2);
		track.thumbImageID = "track_thumb_image_2";
		track.imageID = "track_image_2";
		track.title = "백양사종주코스";
		track.description = "백양사~약사암~백학봉~상왕봉~사자봉~가인마을";
		track.time = "5시간 20분";
		track.distance = "8.4km";
		track.trackData.add(43);
		track.trackData.add(40);
		track.trackData.add(39);
		track.trackData.add(42);
		track.trackData.add(41);
		track.trackData.add(38);
		trackList.put(track.id, track);
		
		track = new Track(3);
		track.thumbImageID = "track_thumb_image_3";
		track.imageID = "track_image_3";
		track.title = "전망대코스";
		track.description = "탐방안내소~케이블카~전망대~내장사~탐방안내소";
		track.time = "50분";
		track.distance = "1.81km";
		track.trackData.add(13);
		track.trackData.add(24);
		track.trackData.add(2);
		trackList.put(track.id, track);
		
		track = new Track(4);
		track.thumbImageID = "track_thumb_image_4";
		track.imageID = "track_image_4";
		track.title = "장성새재코스";
		track.description = "전남대수련원~새재갈림길~새재~입암통제소";
		track.time = "2시간 50분";
		track.distance = "6.4km";
		track.trackData.add(48);
		track.trackData.add(49);
		track.trackData.add(50);
		track.trackData.add(9);
		trackList.put(track.id, track);
		
		track = new Track(5);
		track.thumbImageID = "track_thumb_image_5";
		track.imageID = "track_image_5";
		track.title = "신선봉코스";
		track.description = "일주문~내장사~금선계곡~신선봉~까치봉~내장사~일주문";
		track.time = "5시간";
		track.distance = "7.76km";
		track.trackData.add(2);
		track.trackData.add(24);
		track.trackData.add(25);
		track.trackData.add(26);
		track.trackData.add(35);
		track.trackData.add(33);
		track.trackData.add(32);
		track.trackData.add(27);
		track.trackData.add(55);
		trackList.put(track.id, track);	
		
		track = new Track(6);
		track.thumbImageID = "track_thumb_image_6";
		track.imageID = "track_image_6";
		track.title = "몽계폭포~백양사코스";
		track.description = "전남대수련원~몽계폭포~능선사거리~운문암~백양사";
		track.time = "3시간 30분";
		track.distance = "6.2km";
		track.trackData.add(47);
		track.trackData.add(46);
		track.trackData.add(45);
		track.trackData.add(44);
		track.trackData.add(43);
		trackList.put(track.id, track);
		
		track = new Track(7);
		track.thumbImageID = "track_thumb_image_7";
		track.imageID = "track_image_7";
		track.title = "백양사~내장사종주코스";
		track.description = "백양사~약사암~백학봉~상왕봉~순창새재~소둥근재~까치봉~내장사";
		track.time = "7시간";
		track.distance = "12km";
		track.trackData.add(43);
		track.trackData.add(40);
		track.trackData.add(39);
		track.trackData.add(42);
		track.trackData.add(15);
		track.trackData.add(14);
		track.trackData.add(27);
		track.trackData.add(55);
		track.trackData.add(25);
		track.trackData.add(24);
		trackList.put(track.id, track);	
		
		track = new Track(8);
		track.thumbImageID = "track_thumb_image_8";
		track.imageID = "track_image_8";
		track.title = "입암산코스";
		track.description = "전남대수련원~은선동계곡~갓바위(정상)~북문~남문~산성골계곡~새재갈림길~전남대수련원";
		track.time = "4시간 20분";
		track.distance = "10.1km";
		track.trackData.add(48);
		track.trackData.add(49);
		track.trackData.add(16);
		track.trackData.add(1);
		track.trackData.add(17);
		track.trackData.add(18);
		track.trackData.add(21);
		track.trackData.add(20);
		track.trackData.add(19);
		trackList.put(track.id, track);	
		
		track = new Track(9);
		track.thumbImageID = "track_thumb_image_9";
		track.imageID = "track_image_9";
		track.title = "능선일주코스";
		track.description = "일주문~서래봉~불출봉~망해봉~연지봉~까치봉~신선봉~연자봉~장군봉~동구리";
		track.time = "7시간";
		track.distance = "11.7km";
		track.trackData.add(29);
		track.trackData.add(37);
		track.trackData.add(28);
		track.trackData.add(30);
		track.trackData.add(57);
		track.trackData.add(27);
		track.trackData.add(32);
		track.trackData.add(33);
		track.trackData.add(35);
		track.trackData.add(31);
		track.trackData.add(36);
		trackList.put(track.id, track);	
	}
	
	public Track getOneTrack(int trackID){
		return trackList.get(trackID);
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
