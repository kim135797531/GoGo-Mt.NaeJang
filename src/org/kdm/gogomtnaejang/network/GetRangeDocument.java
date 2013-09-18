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

public class GetRangeDocument extends
		AsyncTask<Integer, Void, ArrayList<BoardDocument>> {

	@Override
	protected ArrayList<BoardDocument> doInBackground(Integer... params) {
		JSONArray jsonArray = null;
		ArrayList<NameValuePair> idValuePair = new ArrayList<NameValuePair>();
		ArrayList<BoardDocument> boardDocumentList = null;

		idValuePair.add(new BasicNameValuePair("u_startnum", ((Integer) params[1]).toString()));
		idValuePair.add(new BasicNameValuePair("u_endnum", ((Integer) params[2]).toString()));

		if (params[0] == 0) {
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(ManageNetwork.SERVER_SRC
						+ "/works/jeongueop/getAllDocumentListByNum.php");
				httpPost.setEntity(new UrlEncodedFormEntity(idValuePair, "UTF-8"));
				HttpResponse httpResponse = httpClient.execute(httpPost);

				HttpEntity entity = httpResponse.getEntity();
				String JSONString = EntityUtils.toString(entity);
				jsonArray = new JSONArray(JSONString);
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}

		} else {
			try{
				DefaultHttpClient httpClient = new DefaultHttpClient();
				idValuePair.add(new BasicNameValuePair("u_category", ((Integer) params[0]).toString()));
				
				HttpPost httpPost = new HttpPost(
						ManageNetwork.SERVER_SRC
								+ "/works/jeongueop/getSelectedCategoryAllDocumentListByNum.php");
				httpPost.setEntity(new UrlEncodedFormEntity(idValuePair, "UTF-8"));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				
				HttpEntity entity = httpResponse.getEntity();
				String JSONString = EntityUtils.toString(entity);
				jsonArray = new JSONArray(JSONString);

			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}

		boardDocumentList = new ArrayList<BoardDocument>();

		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject nodeObject = jsonArray.getJSONObject(i);

				BoardDocument boardDocument = new BoardDocument();
				boardDocument.id = nodeObject.getInt("id");
				boardDocument.category = nodeObject.getInt("category");
				boardDocument.title = nodeObject.getString("title");
				boardDocument.time = nodeObject.getString("time");
				boardDocument.content = nodeObject.getString("content");
				boardDocument.IMEI = nodeObject.getString("imei");
				boardDocument.imageURL = nodeObject.getString("imageURL");
				boardDocument.thumbImageURL = nodeObject.getString("thumbImageURL");
				boardDocumentList.add(boardDocument);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}		

		return boardDocumentList;
	}

	@Override
	protected void onPostExecute(ArrayList<BoardDocument> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

}
