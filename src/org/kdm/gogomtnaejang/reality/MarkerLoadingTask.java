package org.kdm.gogomtnaejang.reality;

import java.util.List;

import org.mixare.lib.marker.Marker;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MarkerLoadingTask extends Thread{
	public static final int DOWNLIAD_FINISH = 1;
	private CameraDataView dataView;
	private List<Marker> markerList;
	public static Handler mHandler;
	public MarkerLoadingTask(CameraDataView dataView){
		this.dataView = dataView;
	}
	
	public List<Marker> getList(){
		return markerList;
	}
	
	private void onPostExecute(){
		MarkerDataHandler dataHandler = dataView.getDataHandler();	
		markerList = dataHandler.getMarkers();
		Log.e("시발","8마커에 뷰속성 추가");		
		for (int i = 0; i < markerList.size(); i++) {
			NormalizationMarker curMarker = (NormalizationMarker) markerList.get(i);
			curMarker.setView(dataView.getContext());
		}			
		Log.e("시발","9마커에 뷰속성 추가 끝, 핸들러 요청");		
		Message msg = new Message();
		msg.what = CameraDataView.DOWNLOAD_FINISH;
		if(CameraDataView.mHandler != null){
			CameraDataView.mHandler.sendMessage(msg);
		}	
	}
	
	public void run(){
		Log.e("시발","1");
		MarkerDataHandler dataHandler = dataView.getDataHandler();
		dataHandler.addMarkers();
		onPostExecute();		
	}
}