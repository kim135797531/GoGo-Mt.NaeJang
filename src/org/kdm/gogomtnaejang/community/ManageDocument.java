package org.kdm.gogomtnaejang.community;

import java.util.ArrayList;

import org.kdm.gogomtnaejang.network.ManageNetwork;

import android.util.SparseArray;

public class ManageDocument {
	
	private static ManageDocument mManageDocument = null;
	private SparseArray<BoardDocument> mDocumentList = null;
	
	private int maxDocumentCountFromServer;
	
	private ManageDocument(){
		mDocumentList = new SparseArray<BoardDocument>();
		maxDocumentCountFromServer = ManageNetwork.getInst().downloadDocumentCount(0);
	};
	
	public static ManageDocument getInst(){
		if(mManageDocument == null)
			mManageDocument = new ManageDocument();
		
		return mManageDocument;
	}
	
	public void setDocument(int id, BoardDocument document){
		mDocumentList.setValueAt(id, document);
	}
	
	public BoardDocument getDocument(int id){
		return mDocumentList.get(id);
	}
	
	public int getMaxDocumentCount(){
		return maxDocumentCountFromServer;
	}

	public ArrayList<BoardDocument> getRangeDocument(int category, int first, int end){
		return ManageNetwork.getInst().downloadRangeDocument(category, first, end);
	}
}
