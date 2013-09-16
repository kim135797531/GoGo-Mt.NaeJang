package org.kdm.gogomtnaejang.node;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class InfoNode {
	public int id;
	public String name="No name";
	public String detailDescription="No description";
	public String telNum="063-000-0000";
	public float lat=0.0f;
	public float lng=0.0f;
	public ArrayList<Bitmap> pictureList=null;
	
	public InfoNode(int id){
		this.id = id;
	}
}
