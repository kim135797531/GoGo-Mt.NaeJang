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

public class GetDocumentCount extends AsyncTask<Integer, Void, Integer> {

	@Override
	protected Integer doInBackground(Integer... params) {
		int ret;
		JSONObject countObject = null;

		if (params[0] == 0) {
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(ManageNetwork.SERVER_SRC
						+ "/works/jeongueop/getAllDocumentListCount.php");
				HttpResponse httpResponse = httpClient.execute(httpPost);

				HttpEntity entity = httpResponse.getEntity();
				String JSONString = EntityUtils.toString(entity);
				countObject = new JSONObject(JSONString);
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		} else {
			ArrayList<NameValuePair> idValuePair = new ArrayList<NameValuePair>();
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				idValuePair.add(new BasicNameValuePair("u_category", ((Integer) params[0]).toString()));
				HttpPost httpPost = new HttpPost(ManageNetwork.SERVER_SRC
						+ "/works/jeongueop/getSelectedCategoryAllDocumentListCount.php");
				httpPost.setEntity(new UrlEncodedFormEntity(idValuePair, "UTF-8"));
				HttpResponse httpResponse = httpClient.execute(httpPost);

				HttpEntity entity = httpResponse.getEntity();
				String JSONString = EntityUtils.toString(entity);
				countObject = new JSONObject(JSONString);
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}

		try {
			ret = countObject.getInt("document_count");
		} catch (Exception ex) {
			ex.printStackTrace();
			ret = 0;
		}
		return ret;

	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

}
