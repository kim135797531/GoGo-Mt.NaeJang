package org.kdm.gogomtnaejang.node;

import java.util.ArrayList;

public class Path {
	private int id;
	private ArrayList<SimpleNode> path;
	
	public Path(int id) {
		this.id = id;
		path = new ArrayList<SimpleNode>();
	}
	
	public ArrayList<SimpleNode> getPath(){
		return path;
	}
	
	public ArrayList<SimpleNode> getAllSimpleNode(){
		return path;
	}
	
	public void putSimpleNode (double lat, double lng){
		path.add(new SimpleNode(lat, lng));
	}
}
