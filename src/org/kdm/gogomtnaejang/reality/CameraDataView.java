package org.kdm.gogomtnaejang.reality;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.kdm.gogonaejangmt.R;
import org.mixare.lib.gui.PaintScreen;
import org.mixare.lib.marker.Marker;
import org.mixare.lib.render.Camera;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewManager;

public class CameraDataView{
	
	private ProgressDialog loadingDialog;
	private boolean isInited;
	private CameraDataView dataView;
	public static MarkerLoadingTask task;
	public static Handler mHandler;
	public static final int DOWNLOAD_FINISH = 1;
	private CameraMixActivity mActivity;
	private CameraMixContext mixContext;
	private int width, height;
	
	private CameraMixState state = new CameraMixState();
	private ArrayList<CameraMixViewPinch> pinchList = null;
	
	private Location curFix;
	private float radius = 20;
	
	private Timer refresh = null;
	private MarkerDataHandler dataHandler = null;
	private ArrayList<UIEvent> uiEvents = new ArrayList<UIEvent>();
	
	private List<Marker> markerList;


	private float addX = 0, addY = 0;	
	
	//안드로이드 카메라 아님 라이브러리 카메라임
	private static Camera cam;
	private boolean frozen;
	
	public void setActivity(CameraMixActivity mActivity){
		this.mActivity = mActivity;
	}
	
	public CameraDataView(CameraMixActivity mActivity, CameraMixContext ctx){
		this.dataView = this;
		this.mActivity = mActivity; 
		this.mixContext = ctx;
		dataHandler = new MarkerDataHandler(mixContext);
	}
	
	public CameraMixActivity getActivity(){
		return mActivity;
	}
	
	public CameraMixContext getContext(){
		return mixContext;
	}
	
	public float getRadius() {
		return radius;
	}
	public static Camera getCurCamera(){
		return cam;
	}
	
	public boolean getIsInited(){
		return isInited;
	}
	
	public void setIsInited(boolean isInited){
		this.isInited = isInited;
	}
	
	public void init(int widthInit, int heightInit){
		try{
			width = widthInit;
			height = heightInit;
			
			cam = new Camera(width, height, true);
			cam.setViewAngle(Camera.DEFAULT_VIEW_ANGLE);
		}catch(Exception e){
			
		}
		frozen = false;
		
			try{
				if(mActivity.getIsFirstRun()){
					loadingDialog = new ProgressDialog(mixContext);
					//loadingDialog.setIcon(R.drawable.my_fake_trip_small);					
					loadingDialog.setTitle("-=My Fake Trip=-");
					loadingDialog.setMessage("마이리얼트립 서버로부터 데이터를 다운받고 있습니다.");
					loadingDialog.show();	
				}				
				initHandler();			
					task = new MarkerLoadingTask(CameraMixActivity.getDataView());
					task.start();
			}catch(Exception ex){Log.e("시발",ex+"");}
			finally{
				mActivity.setIsFirstRun(false);
			}
		
	}
	
	public void initHandler(){
    	if(mHandler == null){
	    	mHandler = new Handler(){
	    		public void handleMessage(Message msg){
	    			if(msg.what == DOWNLOAD_FINISH){
	    				Log.e("시발","왜안떠핸들러");
	    				markerList = task.getList();
	    				if(pinchList == null){
	    					pinchList = new ArrayList<CameraMixViewPinch>();
		    				for(int i=0; i<markerList.size(); i++){
		    					pinchList.add(((NormalizationMarker)markerList.get(i)).getPinchView());
		    				}
	    				}
	    				for(int i=0; i<markerList.size(); i++){
	    					CameraMixViewPinch pinch = pinchList.get(i);
	    					if(pinch!=null){
	    						if(pinch.getParent()!=null){
	    							ViewManager temp = (ViewManager)pinch.getParent();
		    						temp.removeView(pinch);
		    						//temp.addView(pinch, new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		    						dataView.getActivity().addContentView(pinch, new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));		    						
		    						pinch.postInvalidate();
		    					}
	    						else{
	    							dataView.getActivity().addContentView(pinch, new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
	    						}
	    					}    					
	    				}
	    			}
	    			if(loadingDialog != null){
	    				loadingDialog.dismiss();
	    				
	    			}
	    		}
	    	};
    	}
    }
	
	public void draw(PaintScreen dw){		
		mixContext.getRM(cam.transform);
		curFix = mixContext.getLocationFinder().getCurrentLocation();
		dataHandler.onLocationChanged(curFix);
		dataHandler.updateActivationStatus(mixContext);
		for(int i = dataHandler.getMarkerCount()-1; i>=0; i--){
			NormalizationMarker ma = (NormalizationMarker) dataHandler.getMarker(i);
			dataHandler.onLocationChangedCustomMarker(curFix, ma.getMyRealTripMarker());
			ma.calcPaint(cam, addX, addY);
			ma.draw(dw);
			ma.getPinchView().postInvalidate();	
		}
		
		UIEvent evt = null;
		
		synchronized (uiEvents) {
			if(uiEvents.size()>0){
				evt = uiEvents.get(0);
				uiEvents.remove(0);
			}
		}
		
		if(evt != null){
			switch(evt.type){
			case UIEvent.CLICK:
				handleClickEvent((ClickEvent) evt);
				break;
			}
		}
	}
	
	public MarkerDataHandler getDataHandler() {
		return dataHandler;
	}
	
	boolean handleClickEvent(ClickEvent evt){
		boolean evtHandled = false;
		for(int i=0; i<dataHandler.getMarkerCount() && !evtHandled; i++){
			Marker pm = dataHandler.getMarker(i);
			evtHandled = pm.fClick(evt.x, evt.y, mixContext, state);
		}
		return evtHandled;
	}
	
	public void clickEvent(float x, float y){
		synchronized (uiEvents) {
			uiEvents.add(new ClickEvent(x, y));
		}
	}
	
	public void clearEvents(){
		synchronized (uiEvents) {
			uiEvents.clear();
		}
	}
	
	public void cancelRefreshTimer(){
		if(refresh != null){
			refresh.cancel();
		}
	}
	
}

class UIEvent {
	public static final int CLICK = 0;
	public static final int KEY = 1;

	public int type;
}


class ClickEvent extends UIEvent {
	public float x, y;

	public ClickEvent(float x, float y) {
		this.type = CLICK;
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

}

