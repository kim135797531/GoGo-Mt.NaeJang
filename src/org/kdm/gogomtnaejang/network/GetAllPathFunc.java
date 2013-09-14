package org.kdm.gogomtnaejang.network;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kdm.gogomtnaejang.node.Path;

import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;

public class GetAllPathFunc extends AsyncTask<Void, Void, SparseArray<Path>>{

	@Override
	protected SparseArray<Path> doInBackground(Void... params) {
		Log.e("err","startDownload");
		JSONArray jsonArray = null;
		SparseArray<Path> pathList = null;
		
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(
					ManageNetwork.SERVER_SRC+"/works/jeongueop/GetAllPath.php");
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			HttpEntity entity = httpResponse.getEntity();
			String JSONString = EntityUtils.toString(entity);
			jsonArray = new JSONArray(JSONString);
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}

		pathList = new SparseArray<Path>();
				
		try{			
			for(int i=0; i<jsonArray.length(); i++){
				JSONObject nodeObject = jsonArray.getJSONObject(i);
				int id;
				double lat, lng;

				id = nodeObject.getInt("id");
				lat = nodeObject.getDouble("lat");
				lng = nodeObject.getDouble("lng");
				
				if(pathList.get(id) == null){
					pathList.put(id, new Path(id));
				}
				pathList.get(id).putSimpleNode(lat, lng);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}	
		Log.e("err","endDownload");
		return pathList;
	}

	@Override
	protected void onPostExecute(SparseArray<Path> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

}
