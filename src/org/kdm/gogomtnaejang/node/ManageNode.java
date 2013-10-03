package org.kdm.gogomtnaejang.node;

import java.util.ArrayList;

import org.kdm.gogomtnaejang.db.ManageSQLite;
import org.kdm.gogomtnaejang.network.ManageNetwork;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;

public class ManageNode {
	private static ManageNode mManageNode;
	private Context mContext;
	private SparseArray<Path> pathList;
	private SparseArray<Node> nodeList;
	private SparseArray<InfoNode> infoNodeList;

	private ManageNode() {
	}

	public static ManageNode getInst(Context context) {
		if (mManageNode == null) {
			mManageNode = new ManageNode();
			/*
			 * try{ mManageNode.initData(context); }catch(Exception ex){
			 * ex.printStackTrace(); mManageNode.downloadAllData(); try{
			 * mManageNode.initData(context); } catch(Exception doubleEx){
			 * doubleEx.printStackTrace(); } }
			 */
		}

		mManageNode.setContext(context);
		return mManageNode;
	}
	
	public static ManageNode getInstWithNoContext(){
		if (mManageNode == null){
			mManageNode = new ManageNode();
		}
		mManageNode.inputInfoNodeData();
		return mManageNode;
	}

	public void inputInfoNodeData() {
		infoNodeList = new SparseArray<InfoNode>();
		InfoNode infoNode;

		infoNode = new InfoNode(100);
		infoNode.name = "내장산국립공원사무소";
		infoNode.address = "전라북도 정읍시 내장동 59-10 ";
		infoNode.telNum = "063-538-7875";
		infoNode.detailDescription = "내장산국립공원사무소입니다.";
		infoNode.lat = 35.493345f;
		infoNode.lng = 126.927849f;
		infoNode.imageURL = "info_image_100";
		infoNode.thumbImageURL = "info_thumb_image_100";
		infoNodeList.put(100, infoNode);

		infoNode = new InfoNode(101);
		infoNode.name = "내장산국립공원백암사무소";
		infoNode.address = "전남 장성군 북하면 약수리 252-1";
		infoNode.telNum = "061-392-7288";
		infoNode.detailDescription = "내장산 백암사무소입니다.";
		infoNode.lat = 35.434985f;
		infoNode.lng = 126.878930f;
		infoNode.imageURL = "info_image_101";
		infoNode.thumbImageURL = "info_thumb_image_101";
		infoNodeList.put(101, infoNode);

		infoNode = new InfoNode(102);
		infoNode.name = "내장산탐방안내소";
		infoNode.address = "전라북도 정읍시 내장동 673-1";
		infoNode.telNum = "063-538-7874";
		infoNode.detailDescription = "내장산 탐방안내소입니다.";
		infoNode.lat = 35.487874f;
		infoNode.lng = 126.907481f;
		infoNode.imageURL = "info_image_102";
		infoNode.thumbImageURL = "info_thumb_image_102";
		infoNodeList.put(102, infoNode);

		infoNode = new InfoNode(103);
		infoNode.name = "내장탐방지원센터";
		infoNode.address = "전라북도 정읍시 내장동 산 256";
		infoNode.telNum = "063-538-7878";
		infoNode.detailDescription = "매표소앞 내장탐방지원센터입니다.";
		infoNode.lat = 35.489836f;
		infoNode.lng = 126.927848f;
		infoNode.imageURL = "info_image_103";
		infoNode.thumbImageURL = "info_thumb_image_103";
		infoNodeList.put(103, infoNode);

		infoNode = new InfoNode(104);
		infoNode.name = "서래탐방지원센터";
		infoNode.address = "전라북도 정읍시 내장동 169-3";
		infoNode.telNum = "063-538-7875";
		infoNode.detailDescription = "내장산 서래탐방지원센터 입니다.\r\n"
				+ "  서래삼거리, 서래봉, 불출봉으로 갈 수 있는 탐방로의 시작부분입니다.";
		infoNode.lat = 35.507049f;
		infoNode.lng = 126.907283f;
		infoNode.imageURL = "info_image_104";
		infoNode.thumbImageURL = "info_thumb_image_104";
		infoNodeList.put(104, infoNode);

		infoNode = new InfoNode(105);
		infoNode.name = "백양탐방지원센터";
		infoNode.address = "전라남도 장성군 북하면 약수리 148-2";
		infoNode.telNum = "061-392-7288";
		infoNode.detailDescription = "내장산 백양탐방지원센터입니다.";
		infoNode.lat = 35.429152f;
		infoNode.lng = 126.879578f;
		infoNode.imageURL = "info_image_105";
		infoNode.thumbImageURL = "info_thumb_image_105";
		infoNodeList.put(105, infoNode);

		infoNode = new InfoNode(106);
		infoNode.name = "남창탐방지원센터";
		infoNode.address = "전라남도 장성군 북하면 신성리 281-2";
		infoNode.telNum = "061-392-7088";
		infoNode.detailDescription = "내장산 남창탐방지원센터입니다.";
		infoNode.lat = 35.433492f;
		infoNode.lng = 126.849334f;
		infoNode.imageURL = "NO IMAGE";
		infoNode.thumbImageURL = "NO IMAGE";
		infoNodeList.put(106, infoNode);

		infoNode = new InfoNode(107);
		infoNode.name = "내장야영장";
		infoNode.address = "전라북도 정읍시 내장동 92-2번지";
		infoNode.telNum = "063-538-7875";
		infoNode.detailDescription = "1984년 설치되어 운영되고 있는 야영장으로 내장천 옆에 위치하고 있으며, 최대 50동까지 수용가능합니다. 차량진입은 불가하며(주차장 별도) 시설물로는 음수대 및 샤워시설이 구비되어 있습니다. 위치는 제3주차장 건너편이며, 주소는 전북 정읍시 내장동 92-2번지 입니다. \r\n"
				+ "  음수대, 샤워기 있음, 전기 사용하는 곳 없음";
		infoNode.lat = 35.498405f;
		infoNode.lng = 126.918069f;
		infoNode.imageURL = "info_image_107";
		infoNode.thumbImageURL = "info_thumb_image_107";
		infoNodeList.put(107, infoNode);

		infoNode = new InfoNode(108);
		infoNode.name = "가인야영장";
		infoNode.address = "전남 장성군 북하면 약수리 108";
		infoNode.telNum = "061-392-7288";
		infoNode.detailDescription = "백양사내에 위치한 가인야영장은 백암산에 둘러싸여 있으며, 차량진입이 가능하다. \r\n"
				+ "  시설은 음수대(2개소)와 화장실이 설치되어 있으며, 지정된 장소를 이용하면 된다. 전기 사용가능. 텐트 대여 안됨.";
		infoNode.lat = 35.432929f;
		infoNode.lng = 126.881385f;
		infoNode.imageURL = "info_image_108";
		infoNode.thumbImageURL = "info_thumb_image_108";
		infoNodeList.put(108, infoNode);

		infoNode = new InfoNode(109);
		infoNode.name = "내장주차장(구 제1주차장)";
		infoNode.address = "전북 정읍시 내장동 52 ";
		infoNode.telNum = "063-538-7875~6 ";
		infoNode.detailDescription = "내장산 제1주차장 입니다. 주차수용대수 약150대";
		infoNode.lat = 35.496310f;
		infoNode.lng = 126.921844f;
		infoNode.imageURL = "info_image_109";
		infoNode.thumbImageURL = "info_thumb_image_109";
		infoNodeList.put(109, infoNode);
		
		infoNode = new InfoNode(110);
		infoNode.name = "봉룡주차장(구 제2주차장)";
		infoNode.address = "전북 정읍시 내장동 84-4";
		infoNode.telNum = "063-538-7875";
		infoNode.detailDescription = "내장산 제2주차장입니다. 주차가능대수(대) 226대";
		infoNode.lat = 35.496310f;
		infoNode.lng = 126.921844f;
		infoNode.imageURL = "info_image_110";
		infoNode.thumbImageURL = "info_thumb_image_110";
		infoNodeList.put(110, infoNode);

		infoNode = new InfoNode(111);
		infoNode.name = "야영장주차장(구 제3주차장)";
		infoNode.address = "전북 정읍시 내장동 산 249 ";
		infoNode.telNum = "063-538-7875";
		infoNode.detailDescription = "내장산 제3주차장입니다. 주차가능대수(대) 120대";
		infoNode.lat = 35.496180f;
		infoNode.lng = 126.918857f;
		infoNode.imageURL = "info_image_111";
		infoNode.thumbImageURL = "info_thumb_image_111";
		infoNodeList.put(111, infoNode);

		infoNode = new InfoNode(112);
		infoNode.name = "내장호주차장(구 제4,5주차장)";
		infoNode.address = "전북 정읍시 내장동 169-1, 232";
		infoNode.telNum = "063-538-7875";
		infoNode.detailDescription = "내장호 주차장입니다. 무료주차장으로 주차가능대수 대형 약363대, 소형 약258대입니다.";
		infoNode.lat = 35.507590f;
		infoNode.lng = 126.907646f;
		infoNode.imageURL = "info_image_112";
		infoNode.thumbImageURL = "info_thumb_image_112";
		infoNodeList.put(112, infoNode);

		infoNode = new InfoNode(113);
		infoNode.name = "추령주차장";
		infoNode.address = "전북 순창군 복흥면 서마리 25-1";
		infoNode.telNum = "063-538-7875";
		infoNode.detailDescription = "무료주차장으로 주차가능대수는 대형 약12대, 소형 약118대입니다.";
		infoNode.lat = 35.476347f;
		infoNode.lng = 126.952539f;
		infoNode.imageURL = "NO IMAGE";
		infoNode.thumbImageURL = "NO IMAGE";
		infoNodeList.put(113, infoNode);

		infoNode = new InfoNode(114);
		infoNode.name = "백양제1주차장";
		infoNode.address = "전남 장성군 북하면 약수리 259-2";
		infoNode.telNum = "061-392-7428";
		infoNode.detailDescription = "내장산백암사무소 앞에 위치하고 있으며, 소유주 직영";
		infoNode.lat = 35.425026f;
		infoNode.lng = 126.878565f;
		infoNode.imageURL = "info_image_114";
		infoNode.thumbImageURL = "info_thumb_image_114";
		infoNodeList.put(114, infoNode);

		infoNode = new InfoNode(115);
		infoNode.name = "백양제2주차장";
		infoNode.address = "전남 장성군 북하면 약수리 275";
		infoNode.telNum = "061-392-7822";
		infoNode.detailDescription = "백양사에서 운영";
		infoNode.lat = 35.423434f;
		infoNode.lng = 126.878081f;
		infoNode.imageURL = "info_image_115";
		infoNode.thumbImageURL = "info_thumb_image_115";
		infoNodeList.put(115, infoNode);

		infoNode = new InfoNode(116);
		infoNode.name = "백양제3주차장";
		infoNode.address = "전남 장성군 북하면 약수리 291 ";
		infoNode.telNum = "061-392-7822 ";
		infoNode.detailDescription = "백앙사에서 운영";
		infoNode.lat = 35.419466f;
		infoNode.lng = 126.878855f;
		infoNode.imageURL = "info_image_116";
		infoNode.thumbImageURL = "info_thumb_image_116";
		infoNodeList.put(116, infoNode);

		infoNode = new InfoNode(117);
		infoNode.name = "백양제4주차장";
		infoNode.address = "전남 장성군 북하면 신성리 148 ";
		infoNode.telNum = "061-392-7822";
		infoNode.detailDescription = "백양사에서 운영";
		infoNode.lat = 35.423972f;
		infoNode.lng = 126.850378f;
		infoNode.imageURL = "info_image_117";
		infoNode.thumbImageURL = "info_thumb_image_117";
		infoNodeList.put(117, infoNode);

		infoNode = new InfoNode(118);
		infoNode.name = "남창주차장";
		infoNode.address = "전남 장성군 북하면 신성리 426-1";
		infoNode.telNum = "061-392-7288";
		infoNode.detailDescription = "남창지구 주차장으로 입암산, 남창계곡 방향, 가을, 여름성수기 요금징수";
		infoNode.lat = 35.458270f;
		infoNode.lng = 126.841081f;
		infoNode.imageURL = "NO IMAGE";
		infoNode.thumbImageURL = "NO IMAGE";
		infoNodeList.put(118, infoNode);
	}

	public int getMaxInfoNodeCount() {
		return infoNodeList.size();
	}

	public void setContext(Context context) {
		mContext = context;
	}

	public Context getContext() {
		return mContext;
	}

	public void initData(Context context, Handler handler) throws Exception {
		try {
			if (pathList != null)
				return;
			pathList = ManageSQLite.getInst(context).getAllPath(handler);
			if (pathList == null) {
				throw new Exception();
			}
		} catch (Exception ex) {

		} finally {
			Message msg = handler.obtainMessage();
			Bundle bundle = new Bundle();
			bundle.putInt("total", 100);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	}

	private void downloadAllData() {
		pathList = ManageNetwork.getInst().downloadAllPathData();
		ManageSQLite.getInst(mContext).setAllPath(pathList);
	}

	public Path getPathData(int id) {
		return pathList.get(id);
	}

	public ArrayList<InfoNode> getRangeInfoDocument(int start, int end) {
		ArrayList<InfoNode> ret = new ArrayList<InfoNode>();
		for (int i = start; i < end; i++)
			ret.add(infoNodeList.get(i));

		return ret;
	}

	public InfoNode getOneInfoDocument(int id) {
		return infoNodeList.get(id);
	}
	
	public SparseArray<Node> getAllNode(){
		return nodeList;
	}
	
	public SparseArray<InfoNode> getAllInfoNode(){
		return infoNodeList;
	}
}
