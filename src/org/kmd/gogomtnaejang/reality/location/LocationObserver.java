/*
 * Copyright (C) 2012- Peer internet solutions & Finalist IT Group
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
package org.kmd.gogomtnaejang.reality.location;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

class LocationObserver implements LocationListener {
	
	private LocationMgrImpl myController;

	public LocationObserver(LocationMgrImpl myController) {
		super();
		this.myController=myController;
	}
	
	
	public void onLocationChanged(Location location) {

		try {
			//addWalkingPathPosition(location);
			myController.setPosition(location);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/*
	private void addWalkingPathPosition(Location location) {
		MixMap.addWalkingPathPosition(new GeoPoint((int) (location.getLatitude() * 1E6),(int) (location.getLongitude() * 1E6)));
	}
	*/
	public void onProviderDisabled(String provider) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
	
	
}
