package org.kdm.gogomtnaejang.network;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class GetOneImageFunc extends AsyncTask<String, Void, Bitmap>{

	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bm = null;
		try{
			URL url = new URL(params[0]);
			URLConnection conn = url.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			/*
	        BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;
			bm = BitmapFactory.decodeStream(bis,null,options);
			*/
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		}catch(Exception ex){
			return null;
		}
		return bm;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	
	
}
