package com.practice;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NCSDataPaser {

	public static void main(String[] args) {
		NCSOpenAPI api = new NCSOpenAPI();
		String NCS_ncsClCd = "";
		try {
			File file = new File("H:\\회사\\00.작업메모\\NCS\\NCS_ncsClCds.txt");
			//입력 스트림 생성
			FileReader filereader = new FileReader(file);
			StringBuilder textSb = new StringBuilder();
			int singleCh = 0;
			while((singleCh = filereader.read()) != -1){
//				System.out.print((char)singleCh);
				textSb.append((char)singleCh);
			}
			filereader.close();

			List<String> NCS_ncsClCds = new ArrayList();
			NCS_ncsClCds=  Arrays.asList(textSb.toString().split("\n"));
//			Map jsonResutl = api.getResult("0201020103_18v2");

			for (Object NCS_ncsClCdLine :NCS_ncsClCds){
				NCS_ncsClCd = NCS_ncsClCdLine.toString().trim();
//				System.out.println(NCS_ncsClCd.toString());
				Map Resutlttttttttttttttt = api.getResult(NCS_ncsClCd.toString() );
			}
//
//			Map jsonResutl = api.getResult("2404020408_18v1");
//			Map jsonResutl = api.getResult("0201020103_18v2");
//			Map jsonResutl = api.getResult("0201020103_18v2");

//			System.out.println("main - "+ jsonResutl);

		} catch (IOException | ParseException e) {
			e.printStackTrace();
			System.out.println("--메인에서보느 오류 --"+NCS_ncsClCd);
		} finally {

		}
	}
}
