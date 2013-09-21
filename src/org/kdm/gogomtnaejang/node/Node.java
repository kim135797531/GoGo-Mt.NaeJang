package org.kdm.gogomtnaejang.node;

public class Node {
	public int id;
	public String name="No name";
	public float lat=0.0f;
	public float lng=0.0f;
	public float alt=0.0f;
	
	public Node(int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public float getLat(){
		return lng;
	}
	
	public float getAlt(){
		return alt;
	}
}
