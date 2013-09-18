package org.kdm.gogomtnaejang.network;

import java.net.URI;
import java.util.ArrayList;

import org.kdm.gogomtnaejang.community.BoardDocument;
import org.kdm.gogomtnaejang.node.Path;

import android.graphics.Bitmap;
import android.net.Uri;
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
	
	public int downloadDocumentCount(int category){
		int ret;
		try{
			ret = new GetDocumentCount().execute(category).get();
		}catch(Exception ex){
			ex.printStackTrace();
			ret = 0;
		}
		return ret;
	}
	
	public Bitmap downloadOneImageFunc(String url){
		Bitmap ret;
		try{
			ret = new GetOneImageFunc().execute(url).get();
		}catch(Exception ex){
			ex.printStackTrace();
			ret = null;
		}
		return ret;
	}
	
	public ArrayList<BoardDocument> downloadRangeDocument(int category, int first, int end){
		ArrayList<BoardDocument> ret;
		try{
			ret = new GetRangeDocument().execute(category, first, end).get();
			if(ret == null){
				throw new Exception("No Data");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			BoardDocument noData = new BoardDocument();
			noData.title = "자료가 없습니다.";
			noData.thumbImageURL="NO IMAGE";
			noData.thumbImageURL="NO IMAGE";
			ret = new ArrayList<BoardDocument>();
			ret.add(noData);
		}
		return ret;
	}
	
	public String downloadAppVersion(String name){
		String ret;
		try{
			ret = new GetAppInfo().execute(name).get();
		}catch(Exception ex){
			ex.printStackTrace();
			ret = "0.10";
		}
		return ret;
	}
	
	public BoardDocument downloadOneDocument(int documentID){
		BoardDocument ret;
		try{
			ret = new GetOneDocument().execute(documentID).get();
		}catch(Exception ex){
			ex.printStackTrace();
			ret = null;
		}
		return ret;	
	}
	
	public void sendDocumentFunc(BoardDocument document){
		try{
			new UploadDocumentFunc().execute(
					((Integer)document.category).toString(),
					document.title,
					document.content,
					document.IMEI,
					getImagePath(document.imageURL)).get();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private String getImagePath(String imageURL) {
		URI imageUri = null;
		try {
			imageUri = new URI(imageURL);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return imageUri.getPath();
	}
}
