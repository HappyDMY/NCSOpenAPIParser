
//package sncs.com.basic.competencyUnit.web;
package com.practice;
/* Java 샘플 코드 */


//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
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
import java.util.*;

public class NCSOpenAPI {
	private static Map parsedData;
	private static JSONParser jsonParser = new JSONParser();

	public NCSOpenAPI(){
		parsedData = new HashMap();
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

		parsedData.put("ncsDiv", Arrays.asList(ncsDiv));
		parsedData.put("categoryCd",Arrays.asList(categoryCd));
		parsedData.put("divisionCd",Arrays.asList(divisionCd));
		parsedData.put("sectionCd",Arrays.asList(sectionCd));
		parsedData.put("subSectionCd",Arrays.asList(subSectionCd));
		parsedData.put("comptUnitCd",Arrays.asList(comptUnitCd));
		parsedData.put("devYear",Arrays.asList(devYear));
		parsedData.put("devVer",Arrays.asList(devVer));
		parsedData.put("learningModuleRegYn",Arrays.asList(learningModuleRegYn));
		parsedData.put("learningModuleNm",Arrays.asList(learningModuleNm));

//		parsedData.put("ncsDiv", ncsDiv);
//		parsedData.put("categoryCd",categoryCd);
//		parsedData.put("divisionCd",divisionCd);
//		parsedData.put("sectionCd",sectionCd);
//		parsedData.put("subSectionCd",subSectionCd);
//		parsedData.put("comptUnitCd",comptUnitCd);
//		parsedData.put("devYear",devYear);
//		parsedData.put("devVer",devVer);
//		parsedData.put("learningModuleRegYn",learningModuleRegYn);
//		parsedData.put("learningModuleNm",learningModuleNm);


		//api 3~8번 까지 루프하며 데이터 받아오기
		for (int i = 2 ; i <= 8; i++){
			try {


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
//				response.setContentType("text/html;charset=UTF-8");



				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json;charset=utf-8");
				System.out.println("API"+i+" Response code: " + conn.getResponseCode());
				System.out.println("request URL: " + url);


				BufferedReader rd;//결과값
				if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
					rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
					System.out.println(" API("+i+")로 응답 잘 받음 -----"+ ncsClCd);
				} else {
					rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
					System.out.println("API("+i+")로 응답 받지 못하였습니다.  " );
				}
				StringBuilder resultData = new StringBuilder();
				String line;
				while ((line = rd.readLine()) != null) {
					resultData.append(line);
//					System.out.println("+111111111111111111111111111111111111로 응답 잘 받음 " );
				}
				rd.close();
				conn.disconnect();
//				System.out.println("+22222222222222222222222222 응답 잘 받음 " );


				//받은 데이터 파씽
				//JSONParse에 json데이터를 넣어 파싱한 다음 JSONObject로 변환한다.
				JSONObject jsonObj = (JSONObject) jsonParser.parse(resultData.toString()); //API로 받은 데이터 obj

//				System.out.println("+33333333333333333333333333 응답 잘 받음 " );
				JSONObject JSONDataObj = new JSONObject();// API 에서 필요한것 꺼내쓸 obj

//				System.out.println("+4444444444444444444444 응답 잘 받음 " );
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
//				parsedData.put("isThereData", false);
					parsedData.put("ncsClCd",  Arrays.asList(ncsClCd));
					break;
				}
//			parsedData.put("isThereData",[true]);

				switch(i){
					case 2 : ParseApi2(dataArray, ncsClCd);
						break;
					case 3 : ParseApi3(dataArray, ncsClCd);
						break;
					case 4 : ParseApi4(dataArray, ncsClCd);
						break;
					case 5 : ParseApi5(dataArray, ncsClCd);
						break;
					case 6 : ParseApi6(dataArray);
						break;
					case 7 : ParseApi7(JSONDataObj);
						break;
					case 8 : ParseApi8(dataArray);
						break;
				}


			}catch (Exception e){
				e.printStackTrace();
				System.out.println("오류난 번호 - "+ncsClCd);
			}

		}//api 2~8번 까지 루프 end

		return (HashMap) this.parsedData;
	}

	public static void ParseApi2(JSONArray dataArray, String ncsClCd) throws ParseException {
		//System.out.println(" 받은데이터 ------- \n" + dataArray.toJSONString());

		//버전이 같은 데이터 찾아서 값할당
		for (Object data: dataArray) {
			JSONObject dataObject = (JSONObject) jsonParser.parse(data.toString());
			if (dataObject.get("dutyCd").equals(ncsClCd.substring(0,8))){
				parsedData.put("jobNm", Arrays.asList(dataObject.get("dutyNm")));
			}

		}

		System.out.println("---2----------------------------------------------------------\n");
	}

	public static void ParseApi3(JSONArray dataArray, String ncsClCd) throws ParseException {
		//System.out.println(" 받은데이터 ------- \n" + dataArray.toJSONString());

		//버전이 같은 데이터 찾아서 값할당
		for (Object data: dataArray) {
			JSONObject dataObject = (JSONObject) jsonParser.parse(data.toString());
			if (dataObject.get("ncsClCd").equals(ncsClCd)){
				String compUnitName = dataObject.get("compUnitName").toString();
				String compUnitLevel = dataObject.get("compUnitLevel").toString();
				String comptUnitDef = dataObject.get("compUnitDef").toString();
				parsedData.put("comptUnitNm", Arrays.asList(compUnitName.substring(3)));//능력단위명
				parsedData.put("compUnitLevel", Arrays.asList(compUnitLevel));//능력단위 레벨
				parsedData.put("comptUnitDef",Arrays.asList(comptUnitDef));//정의
			}

		}

		System.out.println("---3----------------------------------------------------------\n");
	}

	public static void ParseApi4(JSONArray dataArray, String ncsClCd) throws ParseException {
		//System.out.println(" 받은데이터 ------- \n" + dataArray.toJSONString());

		List comptUnitEleNm = new ArrayList<>();
		List comptUnitEleCd = new ArrayList<>();

		//버전이 같은 데이터 찾아서 값할당
		for (Object data: dataArray) {
			JSONObject dataObject = (JSONObject) jsonParser.parse(data.toString());
			if (dataObject.get("ncsClCd").equals(ncsClCd)){
				comptUnitEleNm.add(dataObject.get("compUnitFactrName").toString().substring(2));
				comptUnitEleCd.add(dataObject.get("compUnitFactrNo").toString());
			}

		}

		parsedData.put("comptUnitEleNm",comptUnitEleNm);//능력단위 요소명
		parsedData.put("comptUnitEleCd",comptUnitEleCd);//능력단위 코드
		System.out.println("---4----------------------------------------------------------\n");
	}


	public static void ParseApi5(JSONArray dataArray, String ncsClCd){
		//System.out.println(" 받은데이터 ------- \n" + dataArray.toJSONString());

		List suhaengCd = new ArrayList<>();
		List suhaengNm = new ArrayList<>();
		List suhaengCdCount = new ArrayList<>();

		StringBuilder knowSB = new StringBuilder();
		StringBuilder skillSB = new StringBuilder();
		StringBuilder attiSB = new StringBuilder();

		for (Object data: dataArray) {
			JSONObject dataObject = null;
			try {
				dataObject = (JSONObject) jsonParser.parse(data.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//버전이 같은 데이터 찾아서 값할당
			if (dataObject.get("ncsClCd").equals(ncsClCd)){

				if(dataObject.get("gbnCd").equals("00")){//수행준거
					suhaengCdCount.add(dataObject.get("compUnitFactrNo").toString());//능력단위 요소 번호
					suhaengNm.add(dataObject.get("gbnVal").toString().substring(2));//수행준거 내용
					suhaengCd.add(dataObject.get("gbnVal").toString().substring(0,1));//수행준거 번호

				}else if (dataObject.get("gbnCd").equals("01")){//지식일떄
					knowSB.append("- "+dataObject.get("gbnVal").toString().substring(2)+"\n");

				}else if (dataObject.get("gbnCd").equals("02")){//기술일때
					skillSB.append("- "+dataObject.get("gbnVal").toString().substring(2)+"\n");

				}else if (dataObject.get("gbnCd").equals("03")){//태도일때
					attiSB.append("- "+dataObject.get("gbnVal").toString().substring(2)+"\n");

				}
			}

		}//for end


		System.out.println(knowSB.length()-1);
		System.out.println(skillSB.length()-1);
		System.out.println(attiSB.length()-1);
		List know = Arrays.asList(knowSB.substring(0, (knowSB.length()-1)));
		List skill =Arrays.asList(skillSB.substring(0, (skillSB.length()-1)));
		List atti = Arrays.asList(attiSB.substring(0, (attiSB.length()-1)));


//		System.out.println(suhaengCdCount);
//		System.out.println(suhaengNm);
//		System.out.println(suhaengCd);
//		System.out.println(Arrays.asList(know.substring(0, know.length()-1)));
//		System.out.println(Arrays.asList(skill.substring(0, skill.length()-1)));
//		System.out.println(Arrays.asList(atti.substring(0, atti.length()-1)));

		parsedData.put("suhaengCdCount",suhaengCdCount);
		parsedData.put("suhaengNm",suhaengNm);
		parsedData.put("suhaengCd",suhaengCd);

		//substring(0, know.length()-1) 해주는 이유는 마지막 \n 없애려고
		parsedData.put("know",know);
		parsedData.put("skill",skill);
		parsedData.put("atti",atti);

		System.out.println("---5----------------------------------------------------------\n");
	}

	public static void ParseApi6(JSONArray dataArray) throws ParseException {
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
		parsedData.put("consNm120",Arrays.asList(consNm120.substring(0, consNm120.length()-1)));
		parsedData.put("pdsdocNm",Arrays.asList(pdsdocNm.substring(0, pdsdocNm.length()-1)));
		parsedData.put("equipNm",Arrays.asList(equipNm.substring(0, equipNm.length()-1)));
		parsedData.put("stuffNm",Arrays.asList(stuffNm.substring(0, stuffNm.length()-1)));

		System.out.println("---6----------------------------------------------------------\n");
	}

	public static void ParseApi7(JSONObject JSONDataObj) throws ParseException {
//		System.out.println(" 받은데이터 ------- \n" + JSONDataObj.toJSONString());

		StringBuilder consNm180 = new StringBuilder();//평가시 고려사항

		ArrayList assessItem1Yn = new ArrayList();//과정평가
		ArrayList assessItem2Yn = new ArrayList();//결과평가
		String assessNmStr = "A.,B.,C.,D.,E.,F.,G.,H.,I.,J.,K.,L.,Z.";
		List assessNm = Arrays.asList(assessNmStr.split(","));

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
//			System.out.println(evalData);
			if(evalData.get("evalTypeCd").equals("01")){//과정평가일때
				if(evalData.get("evalMethCd").equals("99")){
					assessItem1Yn.set(12, "Y");
				}else{
					int idx = Integer.parseInt(String.valueOf(evalData.get("evalMethCd")))-1;
					assessItem1Yn.set(idx, "Y");
				}
			}else if (evalData.get("evalTypeCd").equals("02")){//결과평가일때
				if(evalData.get("evalMethCd").equals("99")){
					assessItem2Yn.set(12, "Y");
				}else{
					int idx = Integer.parseInt(String.valueOf(evalData.get("evalMethCd")))-1;
					assessItem2Yn.set(idx, "Y");
				}

			}

		}//for end
		parsedData.put("consNm180",Arrays.asList(consNm180.substring(0, consNm180.length()-1)));
		parsedData.put("assessNm", assessNm);
		parsedData.put("assessItem1Yn",assessItem1Yn);
		parsedData.put("assessItem2Yn",assessItem2Yn);
		System.out.println("---7----------------------------------------------------------\n");
	}

	public static void ParseApi8(JSONArray dataArray) throws ParseException {
//		System.out.println(" 받은데이터 ------- \n" + dataArray.toJSONString());

		ArrayList useYn160 = new ArrayList();//Y
		ArrayList seqNo = new ArrayList();
		for(int i =0; i<10; i++){
			useYn160.add("N");
			seqNo.add(Integer.toString(i+1));
		}

		String mainDomainString = "의사소통능력, 수리능력, 문제해결능력, 자기개발능력, 자원관리능력, 대인관계능력, 정보능력, 기술능력, 조직이해능력, 직업윤리";
		String subDomainString  =   "문서이해능력, 문서작성능력, 경청능력, 의사표현능력, 기초외국어능력\n" +
				"기초연산능력, 기초통계능력, 도표분석능력, 도표작성능력\n" +
				"사고력, 문제처리능력\n" +
				"자아인식능력, 자기관리능력, 경력개발능력\n" +
				"시간관리능력, 예산관리능력, 물적자원관리능력, 인적자원관리능력\n" +
				"팀웍능력, 리더십능력, 갈등관리능력, 협상능력, 고객서비스능력\n" +
				"시간관리능력, 예산관리능력, 물적자원관리능력, 인적자원관리능력\n" +
				"기술이해능력, 기술선택능력, 기술적용능력\n" +
				"국제감각, 조직체제이해능력, 경영이해능력, 업무이해능력\n" +
				"근로윤리, 공동체윤리";
		List mainDomain = Arrays.asList(mainDomainString.split(", ")); //직업기초능력
		List subDomain = Arrays.asList(subDomainString.replace(", ",",").split("\n"));//하위능력
//
//		for(Object aa :subDomain){
//			System.out.println(aa.toString());
//		}

		//[의사소통능력,  수리능력,  문제해결능력,  자기개발능력,  자원관리능력,  대인관계능력,  정보능력,  기술능력,  조직이해능력,  직업윤리]
		//System.out.println(mainDomain.toString());

		for (Object data: dataArray) {
			JSONObject dataObject = (JSONObject) jsonParser.parse(data.toString());
			int idx = Integer.parseInt(dataObject.get("mainNo").toString())-1;
			useYn160.set(idx, "Y");

//			System.out.println(dataObject.get("mainName")+" "+dataObject.get("mainNo")+" "+dataObject.get("subName")+" "+dataObject.get("subNo"));

		}//for end
		parsedData.put("useYn160",useYn160);
		parsedData.put("seqNo",seqNo);
		parsedData.put("mainDomain",mainDomain);
		parsedData.put("subDomain",subDomain);

		System.out.println("---8----------------------------------------------------------\n");
	}

}//class end