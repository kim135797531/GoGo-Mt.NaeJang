package org.kdm.gogomtnaejang.network;

import java.util.ArrayList;

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

public class GetTextListFunc extends AsyncTask<Integer, Void, ArrayList<String>>{

	@Override
	protected ArrayList<String> doInBackground(Integer... params) {
		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<String> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

}
