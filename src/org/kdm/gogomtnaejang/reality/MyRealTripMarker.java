package org.kdm.gogomtnaejang.reality;

import org.mixare.lib.gui.PaintScreen;

import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;

public class MyRealTripMarker extends LocalMarker{
	public static final int MAX_OBJECTS = 20;
	
	public MyRealTripMarker(String id, String title, String isoCode, double latitude, double longitude, double altitude){
		super(id,title,isoCode,latitude,longitude,altitude);
	}
	
	@Override
	public void update(Location curGPSFix) {
		super.update(curGPSFix);		
	}
	
	@Override
	public int getMaxObjects() {
		return MAX_OBJECTS;
	}
	
	@Override
	public void draw(PaintScreen dw){
		drawCircle(dw);
		drawTextBlock(dw);
	}

	@Override
	public void drawCircle(PaintScreen dw) {
		if (isVisible) {
			float maxHeight = dw.getHeight();
			dw.setStrokeWidth(maxHeight / 100f);
			dw.setFill(false);
			dw.setColor(Color.RED);
			double angle = 2.0 * Math.atan2(10, distance);
			double radius = Math.max(Math.min(angle / 0.44 * maxHeight, maxHeight),	maxHeight / 25f);
			dw.paintCircle(cMarker.x, cMarker.y, (float) radius);
		}
	}
	
	@Override
	public void drawTextBlock(PaintScreen dw) {
		if (isVisible) {
			Paint paint2= new Paint();
			paint2.setTextSize(30);
			paint2.setColor(Color.RED);
			dw.getCanvas().drawText(super.getID(), cMarker.x+50, cMarker.y, paint2);
		}

	}
}
