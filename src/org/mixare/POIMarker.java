/*
 * Copyright (C) 2010- Peer internet solutions
 * 
 * This file is part of mixare.
 * 
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. 
 * 
 * You should have received a copy of the GNU General Public License along with 
 * this program. If not, see <http://www.gnu.org/licenses/>
 */

package org.mixare;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;

import org.mixare.lib.MixUtils;
import org.mixare.lib.gui.PaintScreen;
import org.mixare.lib.gui.ScreenObj;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * This markers represent the points of interest.
 * On the screen they appear as circles, since this
 * class inherits the draw method of the Marker.
 * 
 * @author hannes
 * 
 */
public class POIMarker extends LocalMarker {

	public static final int MAX_OBJECTS = 20;
	public static final int OSM_URL_MAX_OBJECTS = 5;
	
	private Rect textBoxRect = new Rect();

	public POIMarker(String id, String title, double latitude, double longitude,
			double altitude, String URL, int type, int color) {
		super(id, title, latitude, longitude, altitude, URL, type, color);

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
	public void drawCircle(PaintScreen dw) {
		if (isVisible) {
			float maxHeight = dw.getHeight();
			dw.setStrokeWidth(maxHeight / 100f);
			dw.setFill(false);

				dw.setColor(getColour());
			
			// draw circle with radius depending on distance
			// 0.44 is approx. vertical fov in radians
			double angle = 2.0 * Math.atan2(10, distance);
			double radius = Math.max(
					Math.min(angle / 0.44 * maxHeight, maxHeight),
					maxHeight / 25f);

			/*
			 * distance 100 is the threshold to convert from circle to another
			 * shape
			 */
			if (distance < 100.0)
				otherShape(dw);
			else
				dw.paintCircle(cMarker.x, cMarker.y, (float) radius);

		}
	}

	@Override
	public void drawTextBlock(PaintScreen dw) {
		//내장산 관련 마커는 여기서 그려짐
		float maxHeight = Math.round(dw.getHeight() / 10f) + 1;
		// TODO: change textblock only when distance changes

		String textStr = "";
		double d = distance;	
		
		textBlock = new NaeJangTextObj(title, Math.round(maxHeight / 2f) + 1, 250, dw, underline);
		textBlock.setDistance(distance);
		
		if (isVisible) {
			
			// based on the distance set the colour
			if (distance < 100.0) {
				textBlock.setBgColor(Color.argb(128, 52, 52, 52));
				textBlock.setBorderColor(Color.rgb(255, 104, 91));
			} else {
				textBlock.setBgColor(Color.argb(128, 0, 0, 0));
				textBlock.setBorderColor(Color.rgb(255, 255, 255));
			}
			//dw.setColor(DataSource.getColor(type));

			txtLab.prepare(textBlock);
			dw.setStrokeWidth(1f);
			dw.setFill(true);		
			dw.paintObj(txtLab, signMarker.x - txtLab.getWidth() / 2, signMarker.y + maxHeight, 0, 1);
		}		
		
	}

	public void otherShape(PaintScreen dw) {
		// This is to draw new shape, triangle
		float currentAngle = MixUtils.getAngle(cMarker.x, cMarker.y,
				signMarker.x, signMarker.y);
		float maxHeight = Math.round(dw.getHeight() / 10f) + 1;

		dw.setColor(getColour());
		float radius = maxHeight / 1.5f;
		dw.setStrokeWidth(dw.getHeight() / 100f);
		dw.setFill(false);

		Path tri = new Path();
		float x = 0;
		float y = 0;
		tri.moveTo(x, y);
		tri.lineTo(x - radius, y - radius);
		tri.lineTo(x + radius, y - radius);

		tri.close();
		dw.paintPath(tri, cMarker.x, cMarker.y, radius * 2, radius * 2,
				currentAngle + 90, 1);
	}

}

class NaeJangTextObj implements ScreenObj, Parcelable{
	String txt;
	float fontSize;
	float width, height;
	float areaWidth, areaHeight;
	String lines[];
	float lineWidths[];
	float lineHeight;
	float maxLineWidth;
	float pad;
	int borderColor, bgColor, textColor, textShadowColor;
	boolean underline;
	String distance;

	public NaeJangTextObj(String txtInit, float fontSizeInit, float maxWidth,
			PaintScreen dw, boolean underline) {
		this(txtInit, fontSizeInit, maxWidth, Color.rgb(255, 255, 255), Color
				.argb(128, 0, 0, 0), Color.rgb(255, 255, 255), Color.argb(64, 0, 0, 0),
				dw.getTextAsc() / 2, dw, underline);
	}

	public NaeJangTextObj(String txtInit, float fontSizeInit, float maxWidth,
			int borderColor, int bgColor, int textColor, int textShadowColor, float pad,
			PaintScreen dw, boolean underline) {

		this.borderColor = borderColor;
		this.bgColor = bgColor;
		this.textColor = textColor;
		this.textShadowColor = textShadowColor;
		this.pad = pad;
		this.underline = underline;

		try {
			prepTxt(txtInit, fontSizeInit, maxWidth, dw);
		} catch (Exception ex) {
			ex.printStackTrace();
			prepTxt("TEXT PARSE ERROR", 12, 200, dw);
		}
	}

	public static final Parcelable.Creator<NaeJangTextObj> CREATOR = new Parcelable.Creator<NaeJangTextObj>() {
		public NaeJangTextObj createFromParcel(Parcel in) {
			return new NaeJangTextObj(in);
		}

		public NaeJangTextObj[] newArray(int size) {
			return new NaeJangTextObj[size];
		}
	};	

	public NaeJangTextObj(Parcel in){
		readParcel(in);
	}
	
	public void setDistance(double distance){
		if(distance<1000.0){
			this.distance = "(약 "+ (int)(distance) +"m)";
		}
		else{
			this.distance = "(약 "+ (int)(distance)/1000 +"km)";
		}
	}

	private void prepTxt(String txtInit, float fontSizeInit, float maxWidth,
			PaintScreen dw) {
		dw.setFontSize(fontSizeInit);

		txt = txtInit;
		fontSize = fontSizeInit;
		areaWidth = maxWidth - pad * 2;
		lineHeight = dw.getTextAsc() + dw.getTextDesc()
				+ dw.getTextLead();

		calc(dw);
		
		/*
		ArrayList<String> lineList = new ArrayList<String>();

		BreakIterator boundary = BreakIterator.getWordInstance();
		boundary.setText(txt);

		int start = boundary.first();
		int end = boundary.next();
		int prevEnd = start;
		while (end != BreakIterator.DONE) {
			String line = txt.substring(start, end);
			String prevLine = txt.substring(start, prevEnd);
			float lineWidth = dw.getTextWidth(line);
			if (lineWidth > areaWidth) {
				// If the first word is longer than lineWidth 
				// prevLine is empty and should be ignored
				if(prevLine.length()>0)
					lineList.add(prevLine);

				start = prevEnd;
			}

			prevEnd = end;
			end = boundary.next();
		}
		String line = txt.substring(start, prevEnd);
		lineList.add(line);

		lines = new String[lineList.size()];
		lineWidths = new float[lineList.size()];
		lineList.toArray(lines);

		maxLineWidth = 0;
		for (int i = 0; i < lines.length; i++) {
			lineWidths[i] = dw.getTextWidth(lines[i]);
			if (maxLineWidth < lineWidths[i])
				maxLineWidth = lineWidths[i];
		}
		areaWidth = maxLineWidth;
		areaHeight = lineHeight * lines.length;

		width = areaWidth + pad * 2;
		height = areaHeight + pad * 2;
		*/
	}
	
	private void calc(PaintScreen dw){
		if(distance != null){
			if(dw.getTextWidth(txt) > dw.getTextWidth(distance)){
				maxLineWidth = dw.getTextWidth(txt);
				if(areaWidth < maxLineWidth){
					areaWidth = maxLineWidth;
				}				
			}
			else{
				maxLineWidth = dw.getTextWidth(distance);
				if(areaWidth > maxLineWidth){
					areaWidth = maxLineWidth;
				}					
			}
			
		}				
		areaHeight = lineHeight*2;
	
		width = areaWidth + pad * 2;
		height = areaHeight + pad * 2;
	}

	public void paint(PaintScreen dw) {
		dw.setFontSize(fontSize);

		dw.setFill(true);
		dw.setColor(bgColor);
		dw.paintRect(0, 0, width, height);

		dw.setFill(false);
		dw.setColor(borderColor);
		dw.paintRect(0, 0, width, height);
		
		dw.setFill(true);
		dw.setStrokeWidth(0);
		dw.setColor(textColor);
		dw.paintText(pad, pad + dw.getTextAsc(), txt, underline);
		dw.paintText(pad, pad + lineHeight + dw.getTextAsc(), distance, underline);

		/*
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			
			// actual text

			dw.setFill(true);
			dw.setStrokeWidth(0);
			dw.setColor(textColor);
			dw.paintText(pad, pad + lineHeight * i + dw.getTextAsc(), line, underline);

		}
		*/
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

	@Override
	public int describeContents() {
		return 0;

	 }

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(txt);
		dest.writeFloat(fontSize);
		dest.writeFloat(width);
		dest.writeFloat(height);
		dest.writeFloat(areaWidth);
		dest.writeFloat(areaHeight);
		dest.writeList(Arrays.asList(lines));
		dest.writeList(Arrays.asList(lineWidths));
		dest.writeFloat(lineHeight);
		dest.writeFloat(maxLineWidth);
		dest.writeFloat(pad);
		dest.writeInt(borderColor);
		dest.writeInt(bgColor);
		dest.writeInt(textColor);
		dest.writeInt(textShadowColor);
		dest.writeString(String.valueOf(underline));
	}

	public void readParcel(Parcel in){
		txt = in.readString();
		fontSize = in.readFloat();
		width = in.readFloat();
		height = in.readFloat();
		areaWidth = in.readFloat();
		areaHeight = in.readFloat();
		lines = in.createStringArray();
		lineWidths = in.createFloatArray();
		lineHeight = in.readFloat();
		maxLineWidth = in.readFloat();
		pad = in.readFloat();
		borderColor = in.readInt();
		bgColor = in.readInt();
		textColor = in.readInt();
		textShadowColor = in.readInt();
		underline = Boolean.getBoolean(in.readString());
	}
}