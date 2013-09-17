package org.kdm.gogomtnaejang.node;

import java.util.ArrayList;

import org.kdm.gogomtnaejang.climbmt.ManageTrackInfo;
import org.kdm.gogomtnaejang.db.ManageSQLite;
import org.kdm.gogomtnaejang.network.ManageNetwork;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

public class ManageNode{
	private static ManageNode mManageNode;
	private Context mContext;
	private SparseArray<Path> pathList;
	private SparseArray<InfoNode> infoNodeList;
	
	private ManageNode(){}
	
	public static ManageNode getInst(Context context){
		if(mManageNode == null){
			mManageNode = new ManageNode();
			try{
				mManageNode.initData(context);
			}catch(Exception ex){
				ex.printStackTrace();
				mManageNode.downloadAllData();
				try{
					mManageNode.initData(context);
				}
				catch(Exception doubleEx){
					doubleEx.printStackTrace();
				}
			}			
		}
		
		mManageNode.setContext(context);
		return mManageNode;
	}
	
	public void inputInfoNodeData(){
		infoNodeList = new SparseArray<InfoNode>();
		InfoNode infoNode;
		
		infoNode = new InfoNode(0);
		infoNode.id = 0;
		infoNode.name = "편의시설 0";
		infoNode.telNum = "063-831-2292";
		infoNode.detailDescription = "편의시설0 입니다.";
		infoNode.lat = 35.46481345f;
		infoNode.lng = 126.8311239f;		
		infoNodeList.put(0, infoNode);
		
		infoNode = new InfoNode(1);
		infoNode.id = 1;
		infoNode.name = "편의시설 1";
		infoNode.telNum = "010-5512-4610";
		infoNode.detailDescription = "편의시설1 입니다.";
		infoNode.lat = 35.46426046f;
		infoNode.lng = 126.8321937f;
		infoNodeList.put(1, infoNode);
	}
		
	public void setContext(Context context){
		mContext = context;
	}
	
	public Context getContext(){
		return mContext;
	}
	
	private void initData(Context context) throws Exception{
		pathList = ManageSQLite.getInst(context).getAllPath();
		if(pathList == null){
			throw new Exception();
		}
	}
	
	private void downloadAllData(){
		pathList = ManageNetwork.getInst().downloadAllPathData();
		ManageSQLite.getInst(mContext).setAllPath(pathList);
	}
	
	public Path getPathData(int id){
		return pathList.get(id);
	}	
}
