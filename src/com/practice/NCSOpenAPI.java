
package com.practice;
/* Java 샘플 코드 */


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NCSOpenAPI {
	private static HashMap parsedData;
	private static JSONParser jsonParser = new JSONParser();

	public NCSOpenAPI(){
		 parsedData = new HashMap<String, List>();
	}

	public HashMap getResult(String ncsClCd) throws IOException, ParseException {
//		0202020201_13v1
		String ncsDiv = "N";
		//기본 데이터 정제
		String dutyCd  = ncsClCd.substring(0,8); //020202020 api변수
		String categoryCd = ncsClCd.substring(0,2);
		String divisionCd = ncsClCd.substring(2,4);
		String sectionCd = ncsClCd.substring(4,6);
		String subSectionCd = ncsClCd.substring(6,8);
		String comptUnitCd = ncsClCd.substring(8,10);
		String devYear = "20"+ncsClCd.substring(11,13); //2013
		String devVer = ncsClCd.substring(14,15);
		String learningModuleRegYn = "N";
		String learningModuleNm = "미개발";

		parsedData.put("ncsDiv",ncsDiv);
		parsedData.put("categoryCd",categoryCd);
		parsedData.put("divisionCd",divisionCd);
		parsedData.put("sectionCd",sectionCd);
		parsedData.put("subSectionCd",subSectionCd);
		parsedData.put("comptUnitCd",comptUnitCd);
		parsedData.put("devYear",devYear);
		parsedData.put("devVer",devVer);
		parsedData.put("learningModuleRegYn",learningModuleRegYn);
		parsedData.put("learningModuleNm",learningModuleNm);


		//api 3~8번 까지 루프하며 데이터 받아오기
		for (int i = 3 ; i <= 7; i++){

			//StringBuilder urlBuilder = new StringBuilder("http://www.ncs.go.kr/api/openapi3.do"); /*URL*/
			String URL = "http://www.ncs.go.kr/api/openapi"+i+".do";
			StringBuilder urlBuilder = new StringBuilder(URL); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=zpqGsw0p66Eq75HKhvWEFfacjuho3lyI7Nnu1b87UiF1qsWJKKPIJy1%2Fyr6RlDNdbBXwryoZmVZ2QTLADzALmQ%3D%3D"); /*Service Key*/
			urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
			urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
			urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("999", "UTF-8")); /*한 페이지 결과 수*/
			urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*페이지 타입*/
			//urlBuilder.append("&" + URLEncoder.encode("dutyCd","UTF-8") + "=" + URLEncoder.encode("01010101", "UTF-8")); /*직무코드 - [NCS 분류체계 조회]의 ncs대/중/소/세분류 코드를 문자렬로 만든값*/
			urlBuilder.append("&" + URLEncoder.encode("dutyCd","UTF-8") + "=" + URLEncoder.encode(dutyCd, "UTF-8")); /*직무코드 - [NCS 분류체계 조회]의 ncs대/중/소/세분류 코드를 문자렬로 만든값*/
			urlBuilder.append("&" + URLEncoder.encode("compUnitCd","UTF-8") + "=" + URLEncoder.encode(comptUnitCd, "UTF-8")); /*능력단위 코드 ex) 01*/
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			System.out.println("API"+i+" Response code: " + conn.getResponseCode());
			System.out.println("request URL: " + url);


			BufferedReader rd;//결과값
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				System.out.println(" API("+i+")로 응답 잘 받음 " );
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				System.out.println("API("+i+")로 응답 받지 못하였습니다.  " );
			}
			StringBuilder resultData = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				resultData.append(line);
			}
			rd.close();
			conn.disconnect();


			//받은 데이터 파씽
			//JSONParse에 json데이터를 넣어 파싱한 다음 JSONObject로 변환한다.
			JSONObject jsonObj = (JSONObject) jsonParser.parse(resultData.toString()); //API로 받은 데이터 obj
			JSONObject JSONDataObj = new JSONObject();// API 에서 필요한것 꺼내쓸 obj
			JSONArray dataArray = new JSONArray();// API 에서 필요한것 꺼내쓸 array
			//JSONObject에서 PersonsArray를 get하여 JSONArray에 저장한다.
			if(i==7){
				JSONDataObj = (JSONObject) jsonObj.get("data");
			} else {
				 dataArray = (JSONArray) jsonObj.get("data");

			}

			if (dataArray.size()==0&JSONDataObj.isEmpty()){
				System.out.println(ncsClCd + "으로 받은 데이터가 없습니다.");
				System.out.println("Data : " + dataArray);
				parsedData.clear();
				parsedData.put("isThereData", false);
				parsedData.put("ncsClCd", ncsClCd);
				break;
			}
			parsedData.put("isThereData",true);

			switch(i){
				case 3 : ParseApi3(dataArray, ncsClCd);
					break;
				case 4 : ParseApi4(dataArray, ncsClCd);
					break;
				case 5 : ParseApi5(dataArray, ncsClCd);
					break;
				case 6 : ParseApi6(dataArray, ncsClCd);
					break;
				case 7 : ParseApi7(JSONDataObj, ncsClCd);
					break;
				case 8 : ParseApi8(dataArray, ncsClCd);
					break;
			}

		}//api 2~8번 까지 루프 end 

		return this.parsedData;
	}

	public static void ParseApi3(JSONArray dataArray, String ncsClCd) throws IOException, ParseException {
		//System.out.println(" 받은데이터 ------- \n" + dataArray.toJSONString());

		//버전이 같은 데이터 찾아서 값할당
		for (Object data: dataArray) {
			JSONObject dataObject = (JSONObject) jsonParser.parse(data.toString());
			if (dataObject.get("ncsClCd").equals(ncsClCd)){
				String compUnitName = dataObject.get("compUnitName").toString();
				String compUnitLevel = dataObject.get("compUnitLevel").toString();
				String comptUnitDef = dataObject.get("compUnitDef").toString();
				parsedData.put("comptUnitNm",compUnitName);
				parsedData.put("compUnitLevel",compUnitLevel);
				parsedData.put("comptUnitDef",comptUnitDef);
			}

		}

		System.out.println("---3----------------------------------------------------------\n");
	}

	public static void ParseApi4(JSONArray dataArray, String ncsClCd) throws IOException, ParseException {
		//System.out.println(" 받은데이터 ------- \n" + dataArray.toJSONString());

		//버전이 같은 데이터 찾아서 값할당
		for (Object data: dataArray) {
			JSONObject dataObject = (JSONObject) jsonParser.parse(data.toString());
			if (dataObject.get("ncsClCd").equals(ncsClCd)){
				String comptUnitEleNm = dataObject.get("compUnitFactrName").toString();
				String comptUnitEleCd = dataObject.get("compUnitFactrNo").toString();
				parsedData.put("comptUnitEleNm",comptUnitEleNm);
				parsedData.put("comptUnitEleCd",comptUnitEleCd);
			}

		}
		System.out.println("---4----------------------------------------------------------\n");
	}

	public static void ParseApi5(JSONArray dataArray, String ncsClCd) throws IOException, ParseException {
		//System.out.println(" 받은데이터 ------- \n" + dataArray.toJSONString());

		ArrayList<String> suhaengCd = new ArrayList<>();
		ArrayList<String> suhaengNm = new ArrayList<>();
		ArrayList<String> suhaengCdCount = new ArrayList<>();

		StringBuilder know = new StringBuilder();
		StringBuilder skill = new StringBuilder();
		StringBuilder atti = new StringBuilder();

		for (Object data: dataArray) {
			JSONObject dataObject = (JSONObject) jsonParser.parse(data.toString());
			//버전이 같은 데이터 찾아서 값할당
			if (dataObject.get("ncsClCd").equals(ncsClCd)){

				if(dataObject.get("gbnCd").equals("00")){//수행준거
					suhaengCdCount.add(dataObject.get("compUnitFactrNo").toString());//능력단위 요소 번호
					suhaengNm.add(dataObject.get("gbnVal").toString().substring(2));//수행준거 내용
					suhaengCd.add(dataObject.get("gbnVal").toString().substring(0,1));//수행준거 번호

				}else if (dataObject.get("gbnCd").equals("01")){//지식일떄
					know.append("- "+dataObject.get("gbnVal").toString().substring(2)+"\n");

				}else if (dataObject.get("gbnCd").equals("02")){//기술일때
					skill.append("- "+dataObject.get("gbnVal").toString().substring(2)+"\n");

				}else if (dataObject.get("gbnCd").equals("03")){//태도일때
					atti.append("- "+dataObject.get("gbnVal").toString().substring(2)+"\n");

				}


			}

		}//for end

		parsedData.put("suhaengCdCount",suhaengCdCount.toString());
		parsedData.put("suhaengNm",suhaengNm.toString());
		parsedData.put("suhaengCd",suhaengCd.toString());

		//substring(0, know.length()-1) 해주는 이유는 마지막 \n 없애려고
		parsedData.put("know",know.substring(0, know.length()-1));
		parsedData.put("skill",skill.substring(0, skill.length()-1));
		parsedData.put("atti",atti.substring(0, atti.length()-1));

		System.out.println("---5----------------------------------------------------------\n");
	}

	public static void ParseApi6(JSONArray dataArray, String ncsClCd) throws IOException, ParseException {
		//System.out.println(" 받은데이터 ------- \n" + dataArray.toJSONString());

		StringBuilder consNm120 = new StringBuilder();
		StringBuilder pdsdocNm = new StringBuilder();
		StringBuilder equipNm = new StringBuilder();
		StringBuilder stuffNm = new StringBuilder();

		for (Object data: dataArray) {
			JSONObject dataObject = (JSONObject) jsonParser.parse(data.toString());

			if (dataObject.get("itemCd").equals("01")){//고려사항
				consNm120.append("- "+dataObject.get("defText").toString()+"\n");

			}else if (dataObject.get("itemCd").equals("02")){//자료 및 관련 서류
				pdsdocNm.append("- "+dataObject.get("defText").toString()+"\n");

			}else if (dataObject.get("itemCd").equals("03")){//장비 및 도구
				equipNm.append("- "+dataObject.get("defText").toString()+"\n");

			}else if (dataObject.get("itemCd").equals("04")){//재료
				stuffNm.append("- "+dataObject.get("defText").toString()+"\n");

			}

		}//for end

		//substring(0, know.length()-1) 해주는 이유는 마지막 \n 없애려고
		parsedData.put("consNm120",consNm120.substring(0, consNm120.length()-1));
		parsedData.put("pdsdocNm",pdsdocNm.substring(0, pdsdocNm.length()-1));
		parsedData.put("equipNm",equipNm.substring(0, equipNm.length()-1));
		parsedData.put("stuffNm",stuffNm.substring(0, stuffNm.length()-1));

		System.out.println("---6----------------------------------------------------------\n");
	}

	public static void ParseApi7(JSONObject JSONDataObj, String ncsClCd) throws IOException, ParseException {
//		System.out.println(" 받은데이터 ------- \n" + JSONDataObj.toJSONString());

		StringBuilder consNm180 = new StringBuilder();//평가시 고려사항

		ArrayList assessItem1Yn = new ArrayList();//과정평가
		ArrayList assessItem2Yn = new ArrayList();//결과평가

		JSONDataObj.get("evalData");


		for (Object consNm180s: (JSONArray)JSONDataObj.get("csdrData")) {
			JSONObject dataObject = (JSONObject) jsonParser.parse(consNm180s.toString());


			consNm180.append("- "+dataObject.get("defText").toString()+"\n");
		}//for end

		for(int i =0; i<13; i++){
			assessItem1Yn.add("N");
			assessItem2Yn.add("N");
		}

		for (Object evalDatas: (JSONArray)JSONDataObj.get("evalData")) {
			JSONObject evalData = (JSONObject) jsonParser.parse(evalDatas.toString());
			System.out.println(evalData);
			if(evalData.get("evalTypeCd").equals("01")){//과정평가일때
				System.out.println(evalData.get("evalMethCd"));
				int idx = Integer.parseInt(String.valueOf(evalData.get("evalMethCd")))-1;
				assessItem1Yn.set(idx, "Y");
			}else if (evalData.get("evalTypeCd").equals("02")){//결과평가일때
				int idx = Integer.parseInt(String.valueOf(evalData.get("evalMethCd")))-1;
				System.out.println(evalData.get("evalMethCd"));
				assessItem2Yn.set(idx, "Y");

			}

		}//for end
		parsedData.put("consNm180",consNm180.substring(0, consNm180.length()-1));
		parsedData.put("assessItem1Yn",assessItem1Yn);
		parsedData.put("assessItem2Yn",assessItem2Yn);
		System.out.println("---7----------------------------------------------------------\n");
	}

	public static void ParseApi8(JSONArray dataArray, String ncsClCd) throws IOException, ParseException {
//		System.out.println(" 받은데이터 ------- \n" + dataArray.toJSONString());
//
//		StringBuilder consNm180 = new StringBuilder();//평가시 고려사항
//
//		JSONDataObj.get("evalData");
//
//		for (Object consNm180s: (JSONArray)JSONDataObj.get("csdrData")) {
//			JSONObject dataObject = (JSONObject) jsonParser.parse(consNm180s.toString());
//
//			consNm180.append("- "+dataObject.get("defText").toString()+"\n");
//		}//for end
//
//		parsedData.put("consNm180",consNm180.substring(0, consNm180.length()-1));
//
//		System.out.println("---8----------------------------------------------------------\n");
	}

}//class end