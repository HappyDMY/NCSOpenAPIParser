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
		HashMap parsedDataMap = new HashMap<String, List>();

		try {
			//0202020201_13v1


			//HashMap jsonResutl = api.getResult("02020202", "01");
			HashMap jsonResutl = api.getResult("0801030201_14v1");
			System.out.println("main - "+ jsonResutl);


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
