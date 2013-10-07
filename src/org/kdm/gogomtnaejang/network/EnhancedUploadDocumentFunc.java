package org.kdm.gogomtnaejang.network;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.kdm.gogomtnaejang.community.BoardWriteActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

public class EnhancedUploadDocumentFunc extends EnhancedAsyncTask<String, Void, Void, Activity>{

	public EnhancedUploadDocumentFunc(Activity target) {
		super(target);
	}

	@Override
	protected Void doInBackground(Activity target, String... params) {
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		Bitmap uploadBitmap=null;
		try {
			nameValuePairs.add(new BasicNameValuePair("u_category",
					params[0]));
			nameValuePairs.add(new BasicNameValuePair("u_nickname",
					params[1]));
			nameValuePairs.add(new BasicNameValuePair("u_password",
					params[2]));
			nameValuePairs.add(new BasicNameValuePair("u_title",
					params[3]));
			nameValuePairs.add(new BasicNameValuePair("u_content",
					params[4]));

			if(params[5].equalsIgnoreCase("NO IMAGE")){
				nameValuePairs.add(new BasicNameValuePair("image", "NO IMAGE"));
			}
			else{
		        BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				uploadBitmap = BitmapFactory.decodeFile(params[5], options);
				
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
			HttpPost httppost = new HttpPost(ManageNetwork.SERVER_SRC+"/works/jeongueop/insertNewDocument.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			httpClient.execute(httppost);
			uploadBitmap.recycle();
			uploadBitmap = null;

		} catch (Exception e) {
			Log.e("New Question", "Error post data to server");
		}
		return null;
	}

	@Override
	protected void onPostExecute(Activity target, Void result) {
		super.onPostExecute(target, result);

		Toast.makeText(target, "글이 등록되었습니다.",Toast.LENGTH_LONG).show();
		target.finish();
	}
	
}