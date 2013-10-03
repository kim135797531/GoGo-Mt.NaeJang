package org.kdm.gogomtnaejang.network;

import java.net.URI;
import java.util.ArrayList;

import org.kdm.gogomtnaejang.community.BoardDocument;
import org.kdm.gogomtnaejang.node.Path;

import android.content.Context;
import android.graphics.Bitmap;
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
					document.nickName,
					document.password,
					document.title,
					document.content,
					getImagePath(document.imageURL)).get();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private String getImagePath(String imageURL) {
		URI imageUri = null;
		if(imageURL.equalsIgnoreCase("NO IMAGE")){
			return imageURL;
		}
		try {
			imageUri = new URI(imageURL);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return imageUri.getPath();
	}
		
	public boolean checkDocumentPasswordAndDelete(int documentID, String password){
		try{
			return new CheckDocumentPasswordAndDelete().execute(((Integer)documentID).toString(), password).get();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean checkCommentPasswordAndDelete(int commentID, String password){
		try{
			return new CheckCommentPasswordAndDelete().execute(((Integer)commentID).toString(), password).get();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
}
