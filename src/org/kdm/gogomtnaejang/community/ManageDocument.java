package org.kdm.gogomtnaejang.community;

import java.util.ArrayList;

import org.kdm.gogomtnaejang.climbmt.ManageTrackInfo;
import org.kdm.gogomtnaejang.network.ManageNetwork;

import android.content.Context;

public class ManageDocument {

	public static final int NUM_OF_CATEGORY = 13;
	
	public static final int CATEGORY_ALL = 0;
	private static final int CATEGORY_FREE = 1;
	private static final int CATEGORY_NEAR = 2;
	private static final int CATEGORY_MT_0 = 3;
	private static final int CATEGORY_MT_1 = 4;
	private static final int CATEGORY_MT_2 = 5;
	private static final int CATEGORY_MT_3 = 6;
	private static final int CATEGORY_MT_4 = 7;
	private static final int CATEGORY_MT_5 = 8;
	private static final int CATEGORY_MT_6 = 9;
	private static final int CATEGORY_MT_7 = 10;
	private static final int CATEGORY_MT_8 = 11;
	private static final int CATEGORY_MT_9 = 12;
	
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
	
	public int getMaxDocumentCount(int category){
		maxDocumentCountFromServer = ManageNetwork.getInst().downloadDocumentCount(category);
		return maxDocumentCountFromServer;
	}
	
	public BoardDocument getDocument(int documentID){
		return ManageNetwork.getInst().downloadOneDocument(documentID);
	}
	
	public String getCategoryString(int category){
		switch(category){
		case CATEGORY_ALL:
			return "전체";
		case CATEGORY_FREE:
			return "자유롭게";
		case CATEGORY_NEAR:
			return "주변 시설 관련";
		case CATEGORY_MT_0:
			return ManageTrackInfo.getInst().getOneTrack(0).title;
		case CATEGORY_MT_1:
			return ManageTrackInfo.getInst().getOneTrack(1).title;
		case CATEGORY_MT_2:
			return ManageTrackInfo.getInst().getOneTrack(2).title;
		case CATEGORY_MT_3:
			return ManageTrackInfo.getInst().getOneTrack(3).title;
		case CATEGORY_MT_4:
			return ManageTrackInfo.getInst().getOneTrack(4).title;
		case CATEGORY_MT_5:
			return ManageTrackInfo.getInst().getOneTrack(5).title;
		case CATEGORY_MT_6:
			return ManageTrackInfo.getInst().getOneTrack(6).title;
		case CATEGORY_MT_7:
			return ManageTrackInfo.getInst().getOneTrack(7).title;
		case CATEGORY_MT_8:
			return ManageTrackInfo.getInst().getOneTrack(8).title;
		case CATEGORY_MT_9:
			return ManageTrackInfo.getInst().getOneTrack(9).title;			
		default:
			return "No Category";
		}
	}
}
