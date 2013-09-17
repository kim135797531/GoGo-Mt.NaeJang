package org.kdm.gogomtnaejang.community;

import java.util.ArrayList;

import org.kdm.gogomtnaejang.network.ManageNetwork;

import android.util.SparseArray;

public class ManageDocument {
	
	private static final int CATEGORY_FREE = 0;
	private static final int CATEGORY_MT = 1;
	private static final int CATEGORY_NEAR = 2;
	
	private static ManageDocument mManageDocument = null;
	
	private int maxDocumentCountFromServer;
	
	private ManageDocument(){
		maxDocumentCountFromServer = ManageNetwork.getInst().downloadDocumentCount(0);
	};
	
	public static ManageDocument getInst(){
		if(mManageDocument == null)
			mManageDocument = new ManageDocument();
		
		return mManageDocument;
	}
	
	public int getMaxDocumentCount(){
		return maxDocumentCountFromServer;
	}

	public ArrayList<BoardDocument> getRangeDocument(int category, int first, int end){
		return ManageNetwork.getInst().downloadRangeDocument(category, first, end);
	}
	
	public String getCategoryString(int category){
		switch(category){
		case CATEGORY_FREE:
			return "잡담";
		case CATEGORY_MT:
			return "내장산 풍경";
		case CATEGORY_NEAR:
			return "주변 시설 관련";
		default:
			return "No Category";
		}
	}
}
