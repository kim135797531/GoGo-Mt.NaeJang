package org.kdm.gogomtnaejang.network;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.kdm.gogomtnaejang.MainActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class UploadDocumentFunc extends AsyncTask<String, Void, Void> {
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Void v) {
		super.onPostExecute(v);
	}

	@Override
	protected Void doInBackground(String... args) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		Bitmap uploadBitmap=null;
		try {
			nameValuePairs.add(new BasicNameValuePair("u_category",
					args[0]));
			nameValuePairs.add(new BasicNameValuePair("u_title",
					args[1]));
			nameValuePairs.add(new BasicNameValuePair("u_content",
					args[2]));
			nameValuePairs.add(new BasicNameValuePair("u_imei",
					args[3]));		

			if(args[4].equalsIgnoreCase("NO IMAGE")){
				nameValuePairs.add(new BasicNameValuePair("image", "NO IMAGE"));
			}
			else{
		        BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				uploadBitmap = BitmapFactory.decodeFile(args[4], options);
				
				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				uploadBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
				byte[] ba = bao.toByteArray();
				String ba1 = Base64.encodeBytes(ba);
				nameValuePairs.add(new BasicNameValuePair("image", ba1));				
			}

		} catch (Exception e) {
			Log.e("New Question", "Error in make data pair");
		}
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					ManageNetwork.SERVER_SRC+"/works/jeongueop/insertNewDocument.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			httpClient.execute(httppost);			
			uploadBitmap.recycle();
			uploadBitmap = null;

		} catch (Exception e) {
			Log.e("New Question", "Error post data to server");
		}
		return null;
	}
}
