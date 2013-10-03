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
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class CheckCommentPasswordAndDelete extends AsyncTask<String, Void, Boolean> {

	@Override
	protected Boolean doInBackground(String... params) {
		ArrayList<NameValuePair> idValuePair = new ArrayList<NameValuePair>();
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			idValuePair.add(new BasicNameValuePair("u_comment_id", params[0]));
			idValuePair.add(new BasicNameValuePair("u_password", params[1]));
			HttpPost httpPost = new HttpPost(ManageNetwork.SERVER_SRC
					+ "/works/jeongueop/checkCommentPasswordAndDelete.php");
			httpPost.setEntity(new UrlEncodedFormEntity(idValuePair, "UTF-8"));
			HttpResponse httpResponse = httpClient.execute(httpPost);

			HttpEntity entity = httpResponse.getEntity();
			String JSONString = EntityUtils.toString(entity);
			JSONObject isValidPassword = new JSONObject(JSONString);
			String ret = isValidPassword.getString("is_valid");
			
			if(ret.equals("1"))
				return true;
			
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

}
