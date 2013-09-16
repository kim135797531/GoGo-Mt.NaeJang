package org.kdm.gogomtnaejang.reality;

public class SimpleOffer {
	public int id = -1;
	public String title = "";
	public double lat = -1;
	public double lng = -1;
	public String country = "";
	public String isoCode = "";
	
	public SimpleOffer(int id, String title, double lat, double lng, String country, String isoCode){
		this.id = id;
		this.title = title;
		this.lat = lat;
		this.lng = lng;
		this.country = country;		
		this.isoCode = isoCode;
	}
}
