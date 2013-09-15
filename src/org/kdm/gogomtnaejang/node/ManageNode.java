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
	
	private Path getPathData(int id){
		return pathList.get(id);
	}	
	
	public ArrayList<Path> getTrackData(int id){
		ArrayList<Path> ret = new ArrayList<Path>();
		ArrayList<Integer> indexList = ManageTrackInfo.getInst().getOneTrackList(id);
		
		for(int i=0; i<indexList.size(); i++){
			ret.add(getPathData(indexList.get(i)));
		}
		
		if(ret.size() == 0){
			Log.e("Err","Error occured in getTrackData. return value will NULL");
			return null;	
		}
		
		return ret;
		
	}
}
