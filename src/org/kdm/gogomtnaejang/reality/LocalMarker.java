package org.kdm.gogomtnaejang.reality;



import java.text.DecimalFormat;

import org.mixare.lib.MixContextInterface;
import org.mixare.lib.MixStateInterface;
import org.mixare.lib.MixUtils;
import org.mixare.lib.gui.Label;
import org.mixare.lib.gui.PaintScreen;
import org.mixare.lib.gui.TextObj;
import org.mixare.lib.marker.Marker;
import org.mixare.lib.marker.draw.ParcelableProperty;
import org.mixare.lib.marker.draw.PrimitiveProperty;
import org.mixare.lib.reality.PhysicalPlace;
import org.mixare.lib.render.Camera;
import org.mixare.lib.render.MixVector;

import android.graphics.Bitmap;
import android.location.Location;

public abstract class LocalMarker implements Marker{
	protected String ID;
	protected String title;
	protected String isoCode;
	protected PhysicalPlace mGeoLoc;
	
	protected double distance;
	
	private boolean active;
	
	protected boolean isVisible;
	
	public MixVector cMarker = new MixVector();
	protected MixVector signMarker = new MixVector();
	protected MixVector locationVector = new MixVector();
	private MixVector origin = new MixVector(0,0,0);
	private MixVector upV = new MixVector(0,1,0);

	public Label txtLab = new Label();
	protected TextObj textBlock;	
	
	public LocalMarker(String id, String title, String isoCode, double latitude, double longitude, double altitude){
		super();
		this.active = false;
		this.title = title;
		this.isoCode = isoCode;
		this.mGeoLoc = new PhysicalPlace(latitude,longitude,altitude);
		this.ID = id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public double getLatitude(){
		return mGeoLoc.getLatitude();
	}
	
	public double getLongitude(){
		return mGeoLoc.getLongitude();
	}
	
	public double getAltitude(){
		return mGeoLoc.getAltitude();
	}
	
	public MixVector getLocationVector(){
		return locationVector;
	}
	
	private void cCMarker(MixVector originalPoint, Camera viewCam, float addX, float addY) {
		MixVector tmpa = new MixVector(originalPoint);
		MixVector tmpc = new MixVector(upV);
		tmpa.add(locationVector); //3 
		tmpc.add(locationVector); //3
		tmpa.sub(viewCam.lco); //4
		tmpc.sub(viewCam.lco); //4
		tmpa.prod(viewCam.transform); //5
		tmpc.prod(viewCam.transform); //5

		MixVector tmpb = new MixVector();
		viewCam.projectPoint(tmpa, tmpb, addX, addY); //6
		cMarker.set(tmpb); //7
		viewCam.projectPoint(tmpc, tmpb, addX, addY); //6
		signMarker.set(tmpb); //7
	}
	
	private void calcV(Camera viewCam){
		isVisible = false;
		if(cMarker.z < -1f){
			isVisible = true;
		}
	}
	
	public void update(Location curGPSFix)	{
		if(mGeoLoc.getAltitude()==0.0)
			mGeoLoc.setAltitude(curGPSFix.getAltitude());
		
		PhysicalPlace.convLocToVec(curGPSFix, mGeoLoc, locationVector);
	}
	
	public void calcPaint(Camera viewCam, float addX, float addY){
		cCMarker(origin, viewCam, addX, addY);
		calcV(viewCam);
	}
	
	public void drawCircle(PaintScreen dw){
		if(isVisible){
			float maxHeight = dw.getHeight();
			dw.setStrokeWidth(maxHeight / 100f);
			dw.setFill(false);
			double angle = 2.0*Math.atan2(10,distance);
			double radius = Math.max(Math.min(angle/0.44 * maxHeight, maxHeight),maxHeight/25f);
			dw.paintCircle(cMarker.x, cMarker.y, (float)radius);			
		}
	}
	
	public void drawTextBlock(PaintScreen dw) {
		float maxHeight = Math.round(dw.getHeight() / 10f) + 1;
		String textStr="";

		double d = distance;
		DecimalFormat df = new DecimalFormat("@#");
		if(d<1000.0) {
			textStr = title + " ("+ df.format(d) + "m)";			
		}
		else {
			d=d/1000.0;
			textStr = title + " (" + df.format(d) + "km)";
		}

		textBlock = new TextObj(textStr, Math.round(maxHeight / 2f) + 1, 500, dw, false);

		if (isVisible) {
			float currentAngle = MixUtils.getAngle(cMarker.x, cMarker.y, signMarker.x, signMarker.y);

			txtLab.prepare(textBlock);

			dw.setStrokeWidth(1f);
			dw.setFill(true);
			dw.paintObj(txtLab, signMarker.x - txtLab.getWidth()/ 2, signMarker.y + maxHeight, currentAngle + 90, 1);
		}
	}
	
	@Override
	public boolean fClick(float x, float y, MixContextInterface ctx, MixStateInterface state) {
		boolean evtHandled = false;
		return evtHandled;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	@Override
	public String getURL(){
		return null;
	}


	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	public int compareTo(Marker another) {

		Marker leftPm = this;
		Marker rightPm = another;

		return Double.compare(leftPm.getDistance(), rightPm.getDistance());

	}

	@Override
	public boolean equals (Object marker) {
		return this.ID.equals(((Marker) marker).getID());
	}
	
	@Override
	public int hashCode() {
		return this.ID.hashCode();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	abstract public int getMaxObjects();
	
	public void setImage(Bitmap image){
	}
	
	public Bitmap getImage(){
		return null;
	}	
	
	@Override
	public void setTxtLab(Label txtLab) {
	}

	@Override
	public Label getTxtLab() {
		return null;
	}
	
	public void setExtras(String name, PrimitiveProperty primitiveProperty){
		//nothing to add
	}

	public void setExtras(String name, ParcelableProperty parcelableProperty){
		//nothing to add
	}	


	@Override
	public int getColour() {
		return 0;
	}
}
