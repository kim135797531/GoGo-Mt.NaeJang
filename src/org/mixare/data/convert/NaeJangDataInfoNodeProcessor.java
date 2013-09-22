package org.mixare.data.convert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kdm.gogomtnaejang.node.InfoNode;
import org.kdm.gogomtnaejang.node.ManageNode;
import org.kdm.gogomtnaejang.node.Node;
import org.mixare.MixView;
import org.mixare.POIMarker;
import org.mixare.data.DataHandler;
import org.mixare.lib.HtmlUnescape;
import org.mixare.lib.marker.Marker;

import android.util.Log;
import android.util.SparseArray;

public class NaeJangDataInfoNodeProcessor extends DataHandler implements DataProcessor{

	public static final int MAX_JSON_OBJECTS = 1000;
	
	@Override
	public String[] getUrlMatch() {
		String[] str = new String[1]; //only use this data source if all the others don't match
		str[0] = "InfoNode";
		return str;
	}

	@Override
	public String[] getDataMatch() {
		String[] str = new String[1]; //only use this data source if all the others don't match
		str[0] = "InfoNode";
		return str;
	}
	
	@Override
	public boolean matchesRequiredType(String type) {
		return true; //this datasources has no required type, it will always match.
	}

	@Override
	public List<Marker> load(String rawData, int taskId, int colour) throws JSONException {
		List<Marker> markers = new ArrayList<Marker>();
		JSONArray dataArray = new JSONArray(rawData);
		
		int top = Math.min(MAX_JSON_OBJECTS, dataArray.length());

		for (int i = 0; i < top; i++) {
			JSONObject jo = dataArray.getJSONObject(i);
			
			Marker ma = null;
			if (jo.has("name") && jo.has("lat") && jo.has("lng")
					&& jo.has("alt") && jo.has("link")) {
	
				Log.v(MixView.TAG, "processing NaeJang JSON Object");
		
				//no unique ID is provided by the web service according to http://www.geonames.org/export/wikipedia-webservice.html
				ma = new POIMarker(
						jo.getString("id"),
						HtmlUnescape.unescapeHTML(jo.getString("name"), 0), 
						jo.getDouble("lat"), 
						jo.getDouble("lng"), 
						jo.getDouble("alt"), 
						"http://"+jo.getString("link"), 
						taskId, colour);
				markers.add(ma);
			}
		}
		return markers;
	}	
}
