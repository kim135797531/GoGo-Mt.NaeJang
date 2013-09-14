package org.kdm.gogomtnaejang.network;

public class ManageDB {

	private static ManageDB mInstance = null;
	public static final String SERVER_SRC = "http://kim135797531.cafe24.com";
	
	public ManageDB getInst(){
		if(mInstance == null){
			mInstance = new ManageDB();
		}
		
		return mInstance;
	}
}
