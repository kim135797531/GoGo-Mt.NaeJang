package org.kdm.gogomtnaejang.community;

import android.graphics.Bitmap;


public class BoardDocument {
	public int id;
	public int category;
	public String thumbImageURL = "NO IMAGE";
	public String imageURL = "NO IMAGE";
	public String title;
	public String time;
	public String content;
	public String IMEI;
	
	public Bitmap downloadedThumbImageURL;
	public Bitmap downloadedImageURL;
	
	public BoardDocument(){}
}
