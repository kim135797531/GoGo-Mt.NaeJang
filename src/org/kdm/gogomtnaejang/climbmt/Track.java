package org.kdm.gogomtnaejang.climbmt;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Track {
	public int id;
	public String thumbImageID;
	public String imageID;
	public String title;
	public String description;
	public String time;
	public String distance;
	public String detailDescription;
	
	public ArrayList<Integer> trackData = null;

	public Track(int id){
		this.id = id;
		trackData = new ArrayList<Integer>();
	}
}
