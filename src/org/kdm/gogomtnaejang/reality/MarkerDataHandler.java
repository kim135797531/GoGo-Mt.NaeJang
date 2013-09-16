package org.kdm.gogomtnaejang.reality;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.mixare.lib.marker.Marker;
import org.mixare.lib.reality.PhysicalPlace;
import org.mixare.lib.render.MixVector;

import android.location.Location;
import android.util.Log;

import com.nhn.android.data.DataManager;

public class MarkerDataHandler {

	private List<Marker> markerList = new ArrayList<Marker>();
	private CameraMixContext mixContext;
	
	public MarkerDataHandler(CameraMixContext mixContext){
		this.mixContext = mixContext;
	}
	
	public void setMarkers(List<Marker> markers){
		this.markerList = markers;
	}

	public void addMarkers() {
		Log.e("시발","2");		
		List<Marker> markers= downloadDrawResults();
		
		for (Marker ma : markers) {
			if (!markerList.contains(ma))
				markerList.add(ma);
		}
	}
	
	public List<Marker> getMarkers(){
		return markerList;
	}

	public int getMarkerCount() {
		return markerList.size();
	}

	public Marker getMarker(int index) {
		return markerList.get(index);
	}

	public void updateActivationStatus(CameraMixContext mixContext) {

		Hashtable<Class, Integer> map = new Hashtable<Class, Integer>();

		for (Marker ma : markerList) {

			Class<? extends Marker> mClass = ma.getClass();
			map.put(mClass, (map.get(mClass) != null) ? map.get(mClass) + 1 : 1);

			boolean belowMax = (map.get(mClass) <= ma.getMaxObjects());
			ma.setActive((belowMax));
		}
	}

	public void onLocationChanged(Location location) {
		updateDistances(location);
		sortMarkerList();
		for (Marker ma : markerList) {
			ma.update(location);
		}
	}

	public void updateDistances(Location location) {
		for (Marker ma : markerList) {
			float[] dist = new float[3];
			Location.distanceBetween(ma.getLatitude(), ma.getLongitude(),
					location.getLatitude(), location.getLongitude(), dist);
			ma.setDistance(dist[0]);
		}
	}
	
	public void onLocationChangedCustomMarker(Location location, ArrayList<MyRealTripMarker> customMarker) {
		updateDistancesCustomMarker(location, customMarker);
		sortMarkerList();
		for (Marker ma : customMarker) {
			ma.update(location);
		}
	}

	public void updateDistancesCustomMarker(Location location, ArrayList<MyRealTripMarker> customMarker) {
		for (Marker ma : customMarker) {
			float[] dist = new float[3];
			Location.distanceBetween(ma.getLatitude(), ma.getLongitude(),
					location.getLatitude(), location.getLongitude(), dist);
			ma.setDistance(dist[0]);
		}
	}	

	public void sortMarkerList() {
		Collections.sort(markerList);
	}

	private List<Marker> downloadDrawResults() {
		List<Marker> angleMarkerList = new ArrayList<Marker>();		
		Log.e("시발","3데이터핸들러에서 다운요청");				
		HashMap<Integer, SimpleOffer> list;
		//list = DataManager.getIns().getSimpleOffers();
		list = null;
		
		if(list != null){
		Integer[] ids = new Integer[list.size()];
		list.keySet().toArray(ids);
		
		List<Integer> idsList = new ArrayList<Integer>();
		for(int id: ids){
			idsList.add(id);
		}
		Collections.shuffle(idsList);
		Log.e("시발","7마커검사,추가시작");
		for (int id : idsList) {
			SimpleOffer offer = list.get(id);
			if (offer.lat == 0 && offer.lng == 0)
				continue;
			
			MyRealTripMarker myMarker = new MyRealTripMarker(((Integer) offer.id).toString(), offer.country, offer.isoCode, offer.lat, offer.lng, 0.0);
			MixVector locationVector = new MixVector();
			PhysicalPlace.convLocToVec(mixContext.getLocationFinder().getCurrentLocation(), new PhysicalPlace(offer.lat, offer.lng,	0.0), locationVector);
			int normalizationMarkerAngle = normalizationAngle(locationVector);
			
			boolean isAddedFlag = false;
			for(int i=0; i<angleMarkerList.size(); i++){
				if( ((NormalizationMarker)angleMarkerList.get(i)).getAngle() == normalizationMarkerAngle){
					((NormalizationMarker)angleMarkerList.get(i)).addMarker(myMarker);
					isAddedFlag = true;			
					break;
				}
			}			
			if(isAddedFlag == false){					
				NormalizationMarker newNormalMarker = new NormalizationMarker(((Integer) offer.id).toString(), offer.country, offer.isoCode, offer.lat, offer.lng, 0.0);
				newNormalMarker.addMarker(myMarker);
				newNormalMarker.setAngle(normalizationMarkerAngle);
				angleMarkerList.add(newNormalMarker);
			}
		}	
		}
		return angleMarkerList;
	}
	
	private int normalizationAngle(MixVector locationVector){
		final double PI = 3.1416;
		double markerAngle = Math.atan2(locationVector.z, locationVector.x);
		markerAngle+=PI;
		markerAngle*=10;
		int nomalizationMarkerAngle = (int) Math.floor(markerAngle/2);
		return nomalizationMarkerAngle;
	}
}
