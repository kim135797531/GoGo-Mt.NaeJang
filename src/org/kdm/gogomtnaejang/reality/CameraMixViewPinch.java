package org.kdm.gogomtnaejang.reality;

import org.kdm.gogonaejangmt.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

public class CameraMixViewPinch extends View{
	private float x, y;
	private int width, height;
	private String isoCode;
	private String title;
	private int distance;
	private boolean isInited;
	private Bitmap bitmap;
	Paint paint = new Paint();
	Rect rect = new Rect();
	
	public CameraMixViewPinch(Context mContext) {
		super(mContext);
		x=y=width=height=1;
		isInited=false;
	}
	
	public void init(String isoCode, String title, int distance){
		this.isoCode = isoCode;
		this.title = title;
		this.distance = distance;
		//BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.drawable.arrow_up_small);
		//bitmap = bd.getBitmap();

		setVisibility(View.VISIBLE);
		isInited=true;
	}
	
	public boolean isInited(){
		return isInited;
	}
	
	@Override
	protected void onDraw(Canvas canvas){		
		paint.setColor(Color.RED);
		paint.setTextSize(50);
		rect.left = width/2-(bitmap.getWidth()/2);
		rect.top = 0;
		rect.right = rect.left+bitmap.getWidth();
		if(y<(float)CameraMixActivity.getdWindow().getHeight()/4){
			rect.bottom = (int) (rect.top + 0.75*bitmap.getHeight());
		}
		else if(y<(float)CameraMixActivity.getdWindow().getHeight()/2){
			rect.bottom = (int) (rect.top+((3*bitmap.getHeight()/(float)CameraMixActivity.getdWindow().getHeight())*y));			
		}
		else{
			rect.bottom = (int) (rect.top + 1.5*bitmap.getHeight());
		}
		
		//setViewSize(rect.right, rect.bottom);
		canvas.drawBitmap(bitmap, null, rect, null);
		//canvas.drawBitmap(bitmap, width/2-(bitmap.getWidth()/2), 0, null);
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    try{
	    		width = bitmap.getWidth();
	    		//height =bitmap.getHeight();    	
	    		height= 1000;
	    }catch(Exception e){};
	    
	    setViewSize(width, height);
	    setMeasuredDimension(width, height);  
	}
	
	public void setXY(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	private void setViewSize(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public int getViewWidth(){
		return width;
	}
	
	public int getViewHeight(){
		return height;
	}
	
	public int getBitmapWidth(){
		return bitmap.getWidth();
	}
	
	public int getBitmapHeight(){
		return bitmap.getHeight();
	}
}