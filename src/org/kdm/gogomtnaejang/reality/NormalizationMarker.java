package org.kdm.gogomtnaejang.reality;

import java.util.ArrayList;

import org.mixare.lib.gui.PaintScreen;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;

public class NormalizationMarker extends LocalMarker{
	public static final int MAX_OBJECTS = 100;
	private ArrayList<MyRealTripMarker> markerList;
	private ArrayList<Integer> markerIndexList;
	private ArrayList<Double> markerDistanceList;
	private int angleNormalization;
	private CameraMixViewPinch pinch;
	private TextObj textObj;
	private int screenWidth;
	
	public NormalizationMarker(String id, String title, String isoCode, double latitude, double longitude, double altitude){
		super(id,title,isoCode,latitude,longitude,altitude);
		markerList = new ArrayList<MyRealTripMarker>();
		markerIndexList = new ArrayList<Integer>();
		markerDistanceList = new ArrayList<Double>();		
		cMarker.x = -1000;
		cMarker.y = -1000;
		screenWidth = CameraMixActivity.getdWindow().getWidth();
	}
	private static int[] convertIntegers(ArrayList<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}
	private static double[] convertDoubles(ArrayList<Double> doubles)
	{
		double[] ret = new double[doubles.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = doubles.get(i).doubleValue();
	    }
	    return ret;
	}	
	
	public void setView(final CameraMixContext mContext){
		pinch = new CameraMixViewPinch(mContext);
		pinch.setVisibility(View.INVISIBLE);		
		pinch.init(isoCode, title, 100);
		pinch.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				for(int i=0; i<markerList.size(); i++){
					markerList.get(i).calcPaint(CameraDataView.getCurCamera(), 0, 0);					
					int index = Integer.parseInt(markerList.get(i).getID());
					double distance = markerList.get(i).getDistance();
					markerIndexList.add(index);
					markerDistanceList.add(distance);
					}				
				Intent intent = new Intent(CameraMixContext.getContext(), null);				
				intent.putExtra("markerIndexList", convertIntegers(markerIndexList));
				intent.putExtra("markerDistanceList", convertDoubles(markerDistanceList));
				CameraMixContext.getContext().startActivity(intent);
				markerIndexList.clear();
				markerDistanceList.clear();
			}
		});
		if(markerList.size()==1){
			textObj = new TextObj(title, 350, false);					
		}
		else{
			textObj = new TextObj(title+"외 "+(markerList.size()-1)+"곳", 270, false);			
		}
		textObj.setViewSize(pinch.getViewWidth(), pinch.getViewHeight());
		textObj.setBitmapLocation(pinch.getBitmapWidth(), pinch.getBitmapHeight());
	}
	
	@Override
	public void draw(PaintScreen dw){
		if(pinch.isInited() && isVisible && isActive()){
			pinch.setX(cMarker.x);
			pinch.setY(cMarker.y);
			pinch.setXY(cMarker.x, cMarker.y);
			pinch.setRotation( (((float)90/(float)screenWidth)*cMarker.x)-45);
			pinch.postInvalidate();			
			
			int[] pinchLocation = new int[2];
			pinch.getLocationOnScreen(pinchLocation);
			
			//textObj.setViewLocation(cMarker.x, cMarker.y);
			textObj.setViewLocation(pinchLocation[0], pinchLocation[1]);
			textObj.setBitmapLocation(pinch.getBitmapWidth(), pinch.getBitmapHeight());
			textObj.setDistance(distance);			
			textObj.paint(dw.getCanvas());	
		}
		else{
			pinch.setX(-1000);
			pinch.setY(-1000);
		}
	}
	
	public CameraMixViewPinch getPinchView(){
		return pinch;
	}
	
	public void setAngle(int angle){
		this.angleNormalization = angle;
	}
	
	public int getAngle(){
		return angleNormalization;
	}
	
	public ArrayList<MyRealTripMarker> getMyRealTripMarker(){
		return markerList;
	}
	
	public void addMarker(MyRealTripMarker marker){
		markerList.add(marker);
	}
	
	@Override
	public void update(Location curGPSFix) {
		super.update(curGPSFix);		
	}
	
	@Override
	public int getMaxObjects() {
		return MAX_OBJECTS;
	}
	
	public float getX(){
		if(cMarker != null)
			return cMarker.x;
		
		return -1;
	}
	
	public float getY(){
		if(cMarker != null)
			return cMarker.y;
		
		return -1;
	}
}

class TextObj{
	String txt;
	float fontSize;
	int viewWidth, viewHeight, bitmapWidth, bitmapHeight;
	float x,y;
	float width, height;
	float areaWidth, areaHeight;
	String line;
	float lineWidths[];
	float lineHeight;
	float maxLineWidth;
	int borderColor, bgColor, textColor, textShadowColor;
	boolean underline;
	float pad;
	Paint paint = new Paint();
	float fontSizeInit;
	String distance;
	float maxWidth;

	public TextObj(String txtInit, float maxWidth, boolean underline) {
		this(txtInit, maxWidth, Color.rgb(255, 255, 255), Color.argb(128, 0, 0, 0), Color.rgb(255, 255, 255), Color.argb(64, 0, 0, 0),	underline);
	}
	
	public void setDistance(double distance){
		if(distance<1000.0){
			this.distance = "(약 "+ (int)(distance) +"m)";
		}
		else{
			this.distance = "(약 "+ (int)(distance)/1000 +"km)";
		}
	}
	
	public void setViewSize(int viewWidth, int viewHeight){
		this.viewWidth = viewWidth;
		this.viewHeight = viewHeight;
	}
	
	public void setViewLocation(float x, float y){
		this.x=x;
		this.y=y;
	}
	
	public void setBitmapLocation(int bitmapWidth, int bitmapHeight){
		this.bitmapWidth = bitmapWidth;
		this.bitmapHeight = bitmapHeight;
	}

	public TextObj(String txtInit, float maxWidth, int borderColor, int bgColor, int textColor, int textShadowColor, boolean underline) {
		this.x=this.y=-100;
		this.borderColor = borderColor;
		this.bgColor = bgColor;
		this.textColor = textColor;
		this.textShadowColor = textShadowColor;
		this.underline = underline;
		this.maxWidth = maxWidth;
		
		float tempFontSize = (CameraMixActivity.getdWindow().getHeight()/10f)+1;
		tempFontSize = Math.round(tempFontSize/2f)+1;		
		this.fontSizeInit = tempFontSize;

		try {
			prepTxt(txtInit, fontSizeInit, maxWidth);
		} catch (Exception ex) {
			ex.printStackTrace();
			prepTxt("TEXT PARSE ERROR", 12, 200);
		}
	}
	private void prepTxt(String txtInit, float fontSizeInit, float maxWidth) {
		pad = -paint.ascent();
		paint.setTextSize(fontSizeInit);

		line = txtInit;
		fontSize = fontSizeInit;
		areaWidth = maxWidth - pad * 2;
		lineHeight = getTextAsc(paint) + getTextDesc(paint);
		
		calc();
	}
	
	private float getTextAsc(Paint paint){
		return -paint.ascent();
	}
	
	private float getTextDesc(Paint paint){
		return paint.descent();
	}
	
	private void calc(){
		if(distance != null){
			if(paint.measureText(line) > paint.measureText(distance)){
				maxLineWidth = paint.measureText(line);
				if(areaWidth < maxLineWidth){
					areaWidth = maxLineWidth;
				}				
			}
			else{
				maxLineWidth = paint.measureText(distance);
				if(areaWidth > maxLineWidth){
					areaWidth = maxLineWidth;
				}					
			}
			
		}				
		areaHeight = lineHeight*2;
	
		width = areaWidth + pad * 2;
		height = areaHeight + pad * 2;		
	}

	public void paint(Canvas canvas) {
		calc();
		paint.setTextSize(fontSize);
		
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(bgColor);
		canvas.drawRect(x+(bitmapWidth/2)-(width/2), y+bitmapHeight, x+(bitmapWidth/2)+(width/2), y+bitmapHeight+height, paint);
		
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(borderColor);
		canvas.drawRect(x+(bitmapWidth/2)-(width/2), y+bitmapHeight, x+(bitmapWidth/2)+(width/2)-1, y+bitmapHeight+height-1, paint);
		
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(0);
		paint.setColor(textColor);
		canvas.drawText(line, x+(bitmapWidth/2)-(width/2)+pad, y+bitmapHeight+pad+(lineHeight), paint);
		canvas.drawText(distance, x+(bitmapWidth/2)-(width/2)+pad, y+bitmapHeight+pad+(lineHeight*2), paint);
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
	public void setBorderColor(int c){
		this.borderColor=c;
	}
	public void setBgColor(int c){
		this.bgColor=c;
	}
}
