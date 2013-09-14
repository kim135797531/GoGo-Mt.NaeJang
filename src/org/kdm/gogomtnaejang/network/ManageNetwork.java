package org.kdm.gogomtnaejang.network;

import org.kdm.gogomtnaejang.node.Path;

import android.content.Context;
import android.util.SparseArray;

public class ManageNetwork {

	private static ManageNetwork mInstance = null;
	public static final String SERVER_SRC = "http://kim135797531.cafe24.com";
	
	public static ManageNetwork getInst(){
		if(mInstance == null){
			mInstance = new ManageNetwork();
		}
		
		return mInstance;
	}
	
	public SparseArray<Path> downloadAllPathData(){
		SparseArray<Path> ret = null;
		try{
			ret = new GetAllPathFunc().execute().get();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ret;
	}
}
