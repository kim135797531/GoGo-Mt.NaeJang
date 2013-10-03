package org.kdm.gogomtnaejang.climbmt;

import java.util.ArrayList;

import android.util.SparseArray;

public class ManageTrackInfo {
	
	private static ManageTrackInfo mManageTrackInfo = null;
	private SparseArray<Track> trackList = null;
	
	private ManageTrackInfo(){
		trackList = new SparseArray<Track>();
		inputData(trackList);
	}
	
	public static ManageTrackInfo getInst(){
		if(mManageTrackInfo == null)
			mManageTrackInfo = new ManageTrackInfo();
		
		return mManageTrackInfo;
	}

	private void inputData(SparseArray<Track> trackList){
		Track track;
		
		track = new Track(0);
		track.thumbImageID = "track_thumb_image_0";
		track.imageID = "track_image_0";
		track.title = "자연관찰로코스";
		track.description = "일주문~벽련암~원적암~내장사~일주문";
		track.time = "1시간 20분";
		track.distance = "3.56km";
		track.detailDescription = "<우리나라 국립공원 중 최초로 조성된 자연 관찰로로써 노약자와 어린이 탐방객들에게 적합한 산행코스>\r\n"
				+"- 탐방안내소를 출발하여 내장사-원적암-사랑의다리-벽련암-일주문을 거치는 탐방코스\r\n"
				+"- 완만한 경사로 노약자와 어린이들에게 적합한 탐방코스\r\n"
				+"원적골자연관찰로코스는 일주문을 통과하여 내장사를 지나 원적암-사랑의다리-벽련암-일주문으로 돌아오는 코스로써 거리는 약 3.56km, 소요시간은 약 1시간 20분이다. 원적계곡을 따라 조성되어 있으며 평탄한 탐방로로써 노약자와 어린이에게 적합한 코스이다. 또한 자연관찰을 필요로 하는 학생들에게 인기가 높은 탐방코스이다.";
		track.trackData.add(29);
		track.trackData.add(23);
		track.trackData.add(3);
		track.trackData.add(2);
		trackList.put(track.id, track);
		
		track = new Track(1);
		track.thumbImageID = "track_thumb_image_1";
		track.imageID = "track_image_1";
		track.title = "서래봉코스";
		track.description = "일주문~벽련암~서래봉~불출봉~원적암~내장사~일주문";
		track.time = "4시간";
		track.distance = "5.65km";
		track.detailDescription = "<농가구 써레를 닮은 기암괴석의 서래봉을 올라 부처가 출연한 불출봉을 산행하는 내장산의 대표코스>\r\n"
				+"- 내장사 일주문에서 벽련암~서래봉~불출봉~원적암을 거치는 내장산 대표탐방코스(왕복5.65km, 4시간소요)\r\n"
				+"- 내장산 산행코스 중 탐방객이 가장 많이 찾는 대표코스\r\n"
				+"- 내장산에서 가장 많은 탐방객이 이용하는 코스로 탐방안내소를 출발하여 서래봉(624m), 불출봉(622m)을 지나 탐방안내소로 내려오는 코스입니다. 소요시간은 3시간이고 거리는 5.65km이다. 탐방안내소뿐만 아니라 4주차장 부근인 서래탐방지원센터에서도 산행이 가능하다.";
		track.trackData.add(29);
		track.trackData.add(37);
		track.trackData.add(28);
		track.trackData.add(30);
		track.trackData.add(22);
		track.trackData.add(3);
		track.trackData.add(2);
		trackList.put(track.id, track);
		
		track = new Track(2);
		track.thumbImageID = "track_thumb_image_2";
		track.imageID = "track_image_2";
		track.title = "백양사종주코스";
		track.description = "백양사~약사암~백학봉~상왕봉~사자봉~가인마을";
		track.time = "5시간 20분";
		track.distance = "8.4km";
		track.detailDescription = "<백암산 전경을 감상하기에 최적의 코스로, 천연기념물 153호로 지정된 비자나무 숲의 맑은 공기를 마시며 종주하는 대표 탐방코스>\r\n"
				+"- 백양사에서 약사암, 백학봉, 상왕봉, 사자봉 등 능선을 따라 종주하는 코스(8.4km, 5시간 20분 소요)\r\n"
				+"- 계단, 급경사지 및 능선을 따라 종주하는 코스로 다소 어려움\r\n"
				+"- 백암산 종주를 목적으로 하거나 경관을 감상하는 탐방코스로 추천\r\n"
				+"백암산종주 코스는 백양사를 시작으로 약사암, 운문암 등의 암자를 둘러 보며 산행할 수 있다. 학바위를 오르는 구간(약 400m)은 경사가 심하여 어려우나 학바위에 오르면 백암산 최고의 전경을 감상할 수 있다. 또한, 백학봉, 상왕봉, 사자봉을 잇는 능선을 따라 걷는 구간은 다양한 경관을 만날 수 있고, 천연기념물 91호로 지정된 굴거리나무 군락을 만날 수 있다.";
		track.trackData.add(43);
		track.trackData.add(40);
		track.trackData.add(39);
		track.trackData.add(42);
		track.trackData.add(41);
		track.trackData.add(38);
		trackList.put(track.id, track);
		
		track = new Track(3);
		track.thumbImageID = "track_thumb_image_3";
		track.imageID = "track_image_3";
		track.title = "전망대코스";
		track.description = "탐방안내소~케이블카~전망대~내장사~탐방안내소";
		track.time = "50분";
		track.distance = "1.81km";
		track.detailDescription = "<내장산에 숨겨져 있던 아름다운 자태를 볼 수 있는 전망대 코스>\r\n"
				+"- 케이블카를 이용하여 노약자와 어린이들에게 적합한 전망대 코스\r\n"
				+"- 천연기념물91호로 지정된 굴거리나무군락지를 볼 수 있는 탐방 코스\r\n"
				+"노약자나 어린이들이 선호하는 코스로 케이블카를 이용하여 연자봉 중턱에 위치한 전망대를 돌아보는 코스이다. 거리는 1.81km이며 시간은 약 50분이 소요된다. 케이블카를 타고 올라갈 때 보이는 내장산의 숨겨졌던 모습이 그 자태를 드러내 감탄을 자아낸다.";
		track.trackData.add(13);
		track.trackData.add(24);
		track.trackData.add(2);
		trackList.put(track.id, track);
		
		track = new Track(4);
		track.thumbImageID = "track_thumb_image_4";
		track.imageID = "track_image_4";
		track.title = "장성새재코스";
		track.description = "전남대수련원~새재갈림길~새재~입암통제소";
		track.time = "2시간 50분";
		track.distance = "6.4km";
		track.detailDescription = "<전라남도와 전라북도를 연결하는 오솔길을 중심으로 계곡을 따라 걸으며 남녀노소 누구나 탐방하기에 최적인 탐방코스>\r\n"
				+"전남 장성에서 출발하여 새재를 따라 전북 정읍으로 가는 탐방코스(6.4km, 2시간 20분 소요)\r\n"
				+"완만한 경사를 따라가는 산책코스로 온가족이 함께 부담 없이 탐방가능\r\n"
				+"길이 넓고 완만하여 남녀노소 누구에게나 쉬운 산행으로 추천하는 탐방코스\r\n"
				+"내장산국립공원 장성새재 코스는 전남 장성에서 전북 정읍으로 넘어가는 고개로 옛 선비들이 한양으로 과거보러 가는 길이기도 하다. 과거에 군사 작전도로로 사용했던 곳으로 넓고 완만한 경사 때문에 가벼운 등산 코스로 추천할 수 있다. 또한, 내장산국립공원백암사무소의 탐방프로그램 중 옛 선비의 과거길을 따라 떠나는 탐방해설프로그램을 추천할 수 있다.";
		track.trackData.add(48);
		track.trackData.add(49);
		track.trackData.add(50);
		track.trackData.add(9);
		trackList.put(track.id, track);
		
		track = new Track(5);
		track.thumbImageID = "track_thumb_image_5";
		track.imageID = "track_image_5";
		track.title = "신선봉코스";
		track.description = "일주문~내장사~금선계곡~신선봉~까치봉~내장사~일주문";
		track.time = "5시간";
		track.distance = "7.76km";
		track.detailDescription = "<내장산의 가장 높은 봉우리로써, 내장산의 9봉뿐만 아니라 절경을 즐길 수 있는 코스>\r\n"
				+"- 탐방안내소를 출발하여 금선계곡-신선삼거리-신선봉-까치봉-내장사를 거치는 탐방코스\r\n"
				+"- 내장산의 주봉으로써 내장산9봉을 모두 볼 수 있는 코스\r\n"
				+"- 기암괴석인 금선대를 비롯하여 내장산의 전설이 깃들어 있어 눈과 귀가 즐거워지는 코스\r\n"
				+"신선봉코스는 탐방안내소에서 금선계곡을 따라 걷다가 안내표지판을 따라 신선봉쪽으로 오르다보면 넓은 삼거리를 만나게 되고 신선봉을 지나 까치봉으로 해서 다시 탐방안내소로 하산하는 코스이다. 신선봉은 해발 763m로 내장산의 주봉으로써 내장산의 9봉을 조망할 수 있으며, 까치봉은 해발 717m로 서쪽 중심부에 2개의 암봉으로 되어있는 내장산의 제2봉으로서 백암산을 연결하는 주봉이다. 옛날, 신선들이 놀이를 즐겼다고 전해지는 금선대를 비롯하여 숲속에서 아름다운 새들의 울음소리를 들으면서 시원한 물에 발을 담그고 있으면 여름에 더위를 잊을 수 있는 곳이다.";
		track.trackData.add(2);
		track.trackData.add(24);
		track.trackData.add(25);
		track.trackData.add(26);
		track.trackData.add(35);
		track.trackData.add(33);
		track.trackData.add(32);
		track.trackData.add(27);
		track.trackData.add(55);
		trackList.put(track.id, track);	
		
		track = new Track(6);
		track.thumbImageID = "track_thumb_image_6";
		track.imageID = "track_image_6";
		track.title = "몽계폭포~백양사코스";
		track.description = "전남대수련원~몽계폭포~능선사거리~운문암~백양사";
		track.time = "3시간 30분";
		track.distance = "6.2km";
		track.detailDescription = "<내장산 국립공원의 대표적인 폭포인 몽계폭포의 절경과 시원한 계곡을 따라 산행할 수 있는 탐방코스>\r\n"
				+"- 전남대 수련원에서 몽계폭포를 지나 백양사로 내려오는 탐방코스(6.2km, 3시간 30분 소요)\r\n"
				+"- 여름이면 폭포를 찾아, 가을이면 단풍을 찾아 탐방객들이 많이 찾는 코스\r\n"
				+"- 옛 선비의 발자취와 남쪽의 대표적인 수행처 운문암을 만나는 탐방코스\r\n"
				+"몽계폭포~백양사 코스는 내장산국립공원에서 손꼽히는 남창계곡과 백양계곡을 따라 산행할 수 있으며, 여름이면 몽계폭포, 가을이면 단풍을 찾는 탐방객이 많이 찾는 코스로 여유로운 산행에 추천할 수 있다. 또한, 남창탐방지원센터에서 몽계폭포까지 800m의 코스는 가볍게 돌아보는 산행 코스로 추천할 수 있다.";
		track.trackData.add(47);
		track.trackData.add(46);
		track.trackData.add(45);
		track.trackData.add(44);
		track.trackData.add(43);
		trackList.put(track.id, track);
		
		track = new Track(7);
		track.thumbImageID = "track_thumb_image_7";
		track.imageID = "track_image_7";
		track.title = "백양사~내장사종주코스";
		track.description = "백양사~약사암~백학봉~상왕봉~순창새재~소둥근재~까치봉~내장사";
		track.detailDescription = "<전라남도에서 올라 전라북도로 내려오다. 내장산국립공원 남부와 북부 모두를 아우르는 산행코스 백양사~내장사 종주코스>\r\n"
				+"- 전남 장성 백양사에서 백학봉~상왕봉~까치봉을 거쳐 전북 정읍 내장사로 내려오는 탐방코스\r\n"
				+"- 코스 : 백양사~약사암~백학봉~상왕봉~순창새재~소등근재~까치봉~내장사\r\n"
				+"초보 등산객에게는 다소 버거운 탐방 코스로써 거리는 12km, 소요시간은 약 7시간이 걸리는 코스이다. 초보산행객보다는 산행에 숙련된 등산객에게 추천하며, 산행 난이도가 높지만 백암산과 내장산의 절경을 동시에 느낄 수 있는 코스이다.";
		track.time = "7시간";
		track.distance = "12km";
		track.trackData.add(43);
		track.trackData.add(40);
		track.trackData.add(39);
		track.trackData.add(42);
		track.trackData.add(15);
		track.trackData.add(14);
		track.trackData.add(27);
		track.trackData.add(55);
		track.trackData.add(25);
		track.trackData.add(24);
		trackList.put(track.id, track);	
		
		track = new Track(8);
		track.thumbImageID = "track_thumb_image_8";
		track.imageID = "track_image_8";
		track.title = "입암산코스";
		track.description = "전남대수련원~은선동계곡~갓바위(정상)~북문~남문~산성골계곡~새재갈림길~전남대수련원";
		track.time = "4시간 20분";
		track.distance = "10.1km";
		track.detailDescription = "<호국의 성지 입암산성(사적 348호)을 따라 선조들의 나라사랑을 느끼며 수려한 계곡을 따라 자연을 만끽할 수 있는 탐방코스>\r\n"
				+"- 입암산성 갈림길을 중심으로 남문, 갓바위, 은선동 계곡으로 순환하는 코스\r\n"
				+"- 전체적으로 경사가 완만하여 산행초보자도 부담 없이 탐방가능\r\n"
				+"- 가벼운 산행이나 호국의 성지 순례를 목적으로 하는 탐방에 추천\r\n"
				+"입암산 코스는 약 10km의 다소 긴 등산 코스이나 전체적으로 경사가 완면하고, 정상부인 갓바위에서 바라보면 호남평야와 변산반도, 무등산 등 주변경관이 뛰어나 산행 초보자 및 수려한 경관을 찾는 탐방객에게 추천할 수 있다. 또한, 내장산국립공원백암사무소의 탐방프로그램 중 입암산성과 선조들의 나라사랑 탐방해설프로그램을 추천할 수 있다.";
		track.trackData.add(48);
		track.trackData.add(49);
		track.trackData.add(16);
		track.trackData.add(1);
		track.trackData.add(17);
		track.trackData.add(18);
		track.trackData.add(21);
		track.trackData.add(20);
		track.trackData.add(19);
		trackList.put(track.id, track);	
		
		track = new Track(9);
		track.thumbImageID = "track_thumb_image_9";
		track.imageID = "track_image_9";
		track.title = "능선일주코스";
		track.description = "일주문~서래봉~불출봉~망해봉~연지봉~까치봉~신선봉~연자봉~장군봉~동구리";
		track.time = "7시간";
		track.distance = "11.7km";
		track.detailDescription = "<내장산의 8봉우리를 둘러보다. 완주의 쾌감을 느끼게 해주는 능선일주코스>\r\n"
				+"- 내장사 일주문~서래봉~불출봉~망해봉~연지봉~까치봉~신선봉~연자봉~장군봉~동구리를 거치는 탐방코스\r\n"
				+"- 능선까지 올라가는 탐방로에서 난이도가 높으나 능선에 올라서면 다소 어렵지 않게 산행을 즐길 수 있는 코스\r\n"
				+"능선일주는 코스명 그대로 내장산의 월영봉을 제외한 8개의 봉우리를 모두 만날 볼 수 있는 코스이다. 그러므로 많은 시간이 소요되며 그만큼 체력분배도 필수인 코스이다.";
		track.trackData.add(29);
		track.trackData.add(37);
		track.trackData.add(28);
		track.trackData.add(30);
		track.trackData.add(57);
		track.trackData.add(27);
		track.trackData.add(32);
		track.trackData.add(33);
		track.trackData.add(35);
		track.trackData.add(31);
		track.trackData.add(36);
		trackList.put(track.id, track);	
		
		track = new Track(10);
		track.thumbImageID = "NO IMAGE";
		track.imageID = "NO IMAGE";
		track.title = "전체경로보기";
		track.description = "전체 경로입니다.";
		track.time = "X";
		track.distance = "X";
		track.detailDescription = "내장산의 탐방 가능한 모든 경로입니다.\r\n";
		for(int i=1; i<=58; i++)
			track.trackData.add(i);
		trackList.put(track.id, track);	
	}
	
	public Track getOneTrack(int trackID){
		return trackList.get(trackID);
	}
	
	public ArrayList<Integer> getOneTrackList(int trackID){
		return trackList.get(trackID).trackData;
	}
	
	public ArrayList<Track> getAllTrackList(){
		if(trackList != null){
			ArrayList<Track> ret = new ArrayList<Track>();
			for(int i=0; i<trackList.size(); i++){
				ret.add(trackList.get(i));
			}
			return ret;
		}
		
		return null;
	}	

	
}
