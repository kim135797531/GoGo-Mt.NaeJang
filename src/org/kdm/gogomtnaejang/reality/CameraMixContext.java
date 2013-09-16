package org.kdm.gogomtnaejang.reality;

import org.kmd.gogomtnaejang.reality.location.LocationFinder;
import org.kmd.gogomtnaejang.reality.location.LocationFinderFactory;
import org.mixare.lib.MixContextInterface;
import org.mixare.lib.render.Matrix;

import android.content.ContextWrapper;

public class CameraMixContext extends ContextWrapper implements MixContextInterface{

	/** Responsible for all location tasks */
	private LocationFinder locationFinder;
	private static CameraMixContext mContext;
	
	private Matrix rotationM = new Matrix();
	
	private CameraMixActivity mixView;
	public CameraMixContext(CameraMixActivity appCtx){
		super(appCtx);
		mContext = this;
		mixView = appCtx;
		rotationM.toIdentity();
		getLocationFinder().switchOn();		
		getLocationFinder().findLocation();
	}
	
	public static CameraMixContext getContext(){
		return mContext;
	}
	
	public void getRM(Matrix dest) {
		synchronized (rotationM) {
			dest.set(rotationM);
		}
	}
	
	private void setActualMixView(CameraMixActivity mv){
		synchronized (mixView) {
			this.mixView = mv;
		}
	}
	
	public void doResume(CameraMixActivity mixView){
		setActualMixView(mixView);
	}
	
	public CameraMixActivity getActualMixView() {
		synchronized (mixView) {
			return this.mixView;
		}
	}	
	
	public LocationFinder getLocationFinder() {
		if (this.locationFinder == null) {
			locationFinder = LocationFinderFactory.makeLocationFinder(this);
		}
		return locationFinder;
	}
	
	public void updateSmoothRotation(Matrix smoothR) {
		synchronized (rotationM) {
			rotationM.set(smoothR);
		}
	}

	@Override
	public void loadMixViewWebPage(String url) throws Exception {
	}
}
