package com.practice;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		NCSOpenAPI api = new NCSOpenAPI();
		HashMap<String, List> jsonResutl = new HashMap<String, List>();

		try {
			//0202020201_13v1


			//HashMap jsonResutl = api.getResult("02020202", "01");
			jsonResutl = api.getResult("0801030201_14v1");
			System.out.println("main - "+ jsonResutl);

			/*
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
		String learningModuleNm = "미개발";*/
//
//			System.out.println("assessItem2Yn = "+jsonResutl.get("assessItem2Yn").get(0));
//			System.out.println("ncsDiv = "+jsonResutl.get("ncsDiv").get(0));
//			System.out.println("categoryCd = "+jsonResutl.get("categoryCd").get(0));
//			System.out.println("divisionCd = "+jsonResutl.get("divisionCd").get(0));
//			System.out.println("sectionCd = "+jsonResutl.get("sectionCd").get(0));
//			System.out.println("subSectionCd = "+jsonResutl.get("subSectionCd").get(0));
//			System.out.println("comptUnitCd = "+jsonResutl.get("comptUnitCd").get(0));
//			System.out.println("devYear = "+jsonResutl.get("devYear").get(0));
//			System.out.println("devVer = "+jsonResutl.get("devVer").get(0));
//			System.out.println("learningModuleRegYn = "+jsonResutl.get("learningModuleRegYn").get(0));
//			System.out.println("learningModuleNm = "+jsonResutl.get("learningModuleNm").get(0));

            JSONParser jsonParse = new JSONParser();
            //JSONParse에 json데이터를 넣어 파싱한 다음 JSONObject로 변환한다.
//            JSONObject jsonObj = (JSONObject) jsonParse.parse(jsonResutl.toString());
//
//            //JSONObject에서 PersonsArray를 get하여 JSONArray에 저장한다.
//            JSONArray dataArray = (JSONArray) jsonObj.get("data");
//            System.out.println("-------------------------------------------------------------");
//            System.out.println(dataArray.toJSONString());
//
//

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
}
