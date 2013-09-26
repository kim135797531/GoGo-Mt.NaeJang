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
import org.kdm.gogomtnaejang.community.BoardDocument;

import android.os.AsyncTask;

public class GetOneDocument extends AsyncTask<Integer, Void, BoardDocument> {

	@Override
	protected BoardDocument doInBackground(Integer... params) {
		BoardDocument ret;
		ArrayList<NameValuePair> idValuePair = new ArrayList<NameValuePair>();
		JSONArray documentArray = null;
		
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			idValuePair.add(new BasicNameValuePair("u_document_id", params[0].toString()));
			HttpPost httpPost = new HttpPost(ManageNetwork.SERVER_SRC
					+ "/works/jeongueop/getOneDocument.php");
			httpPost.setEntity(new UrlEncodedFormEntity(idValuePair, "UTF-8"));
			HttpResponse httpResponse = httpClient.execute(httpPost);

			HttpEntity entity = httpResponse.getEntity();
			String JSONString = EntityUtils.toString(entity);
			documentArray = new JSONArray(JSONString);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		ret = null;
		try {
			JSONObject documentObject = documentArray.getJSONObject(0);
			ret = new BoardDocument();
			ret.id = documentObject.getInt("id");
			ret.category = documentObject.getInt("category");
			ret.nickName = documentObject.getString("nickname");
			ret.password = documentObject.getString("password");
			ret.title = documentObject.getString("title");
			ret.time = documentObject.getString("time");
			ret.content = documentObject.getString("content");
			ret.imageURL = documentObject.getString("imageURL");
			ret.thumbImageURL = documentObject.getString("thumbImageURL");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	@Override
	protected void onPostExecute(BoardDocument result) {
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

}
