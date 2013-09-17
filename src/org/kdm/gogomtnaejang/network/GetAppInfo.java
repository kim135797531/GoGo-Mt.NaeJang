package org.kdm.gogomtnaejang.network;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

public class GetAppInfo extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... params) {
		String ret;
		ArrayList<NameValuePair> idValuePair = new ArrayList<NameValuePair>();
		JSONArray infoArray = null;
		
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			idValuePair.add(new BasicNameValuePair("u_name", params[0]));
			HttpPost httpPost = new HttpPost(ManageNetwork.SERVER_SRC
					+ "/works/jeongueop/getAppInfo.php");
			httpPost.setEntity(new UrlEncodedFormEntity(idValuePair, "UTF-8"));
			HttpResponse httpResponse = httpClient.execute(httpPost);

			HttpEntity entity = httpResponse.getEntity();
			String JSONString = EntityUtils.toString(entity);
			infoArray = new JSONArray(JSONString);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		try {
			JSONObject infoObject = infoArray.getJSONObject(0);
			ret = infoObject.getString("value");
		} catch (Exception ex) {
			ex.printStackTrace();
			ret = "0.10";
		}
		return ret;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

}
