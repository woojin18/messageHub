package kr.co.uplus.cm.project.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.ApiInterface;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class ChannelService {

	@Autowired
	private GeneralDao generalDao;
	
	@Autowired
	private ApiInterface apiInterface; 

	// RCS 브랜드 조회
	public RestResult<?> selectRcsBrandList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_BRANDLIST, params);

		rtn.setData(rtnList);

		return rtn;
	}

	// RCS 브랜드 템플릿 조회
	public RestResult<?> selectRcsRegTmpltList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_REGTMPLTLIST, params);

		rtn.setData(rtnList);

		return rtn;
	}

	// RCS 브랜드 발신번호 상세 조회
	public RestResult<?> selectRcsCallbackList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_CALLBACKLIST, params);

		rtn.setData(rtnList);

		return rtn;
	}
	
	// API KEY 중복 체크 후 카테고리 가져오기
	@SuppressWarnings("unchecked")
	public RestResult<?> checkApiKey(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		params.put("apiId", "1111");
		params.put("apiSercret", "1111");
		
//		Map<String, Object> result = this.api("/console/v1/brand/categories", params, "GET", "application");
		
		Map<String, Object> getHeaderMap = new HashMap<String, Object>();
		getHeaderMap.put("apiId", "111");
		getHeaderMap.put("apiSercret", "111");
		getHeaderMap.put("svcId", "WEB01");
		
		Map<String, Object> result = apiInterface.get("/console/v1/brand/categories", getHeaderMap);
		
		rtn.setData(result);
		
		return rtn;
	}
	
	// 파일업로드 API
	public RestResult<?> fileUploadToApi(MultipartFile files) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		Map<String, Object> result = this.apiFileUpload("/console/v1/brand/file", files, "POST", "multipart");
		
		rtn.setData(result);
		
		return rtn;
	}
	
	/**
	 * API 통신 
	 * @param urlText		호출 URL 뒷부분
	 * @param params		APIKEY 등 발신 정보 들
	 * @param type			GET, POST 타입
	 * @return				MAP 타입으로 리턴
	 * @throws Exception
	 */
	public Map<String, Object> api(String urlText, Map<String, Object> params, String type, String contentType) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String apiUrl = "http://erp.ectech.co.kr:40931";
		String result = "";
		
		JSONParser jParser = new JSONParser();
		JSONObject jObj = new JSONObject();
		
		try {
			URL url = new URL(apiUrl + urlText);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			// 파라미터가 아닌 헤더에 세팅해줘야함
			con.setRequestProperty("apiId", CommonUtils.getString(params.get("apiId")));
			con.setRequestProperty("apiSercret", CommonUtils.getString(params.get("apiSercret")));
			con.setRequestProperty("svcId", "WEB01");
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestMethod(type.toUpperCase());	// get post
			
			result = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")).readLine();
			
			jObj = (JSONObject) jParser.parse(result);
			
			returnMap.put("rslt", jObj.get("rslt"));
			returnMap.put("data", jObj.get("data"));
		} catch (Exception e) {
			returnMap.put("rslt", "error");
			returnMap.put("data", e.getMessage());
		}
		
		return returnMap;
	}
	
	/**
	 * 파일 업로드API 통신 
	 * @param urlText		호출 URL 뒷부분
	 * @param params		APIKEY 등 발신 정보 들
	 * @param type			GET, POST 타입
	 * @return				MAP 타입으로 리턴
	 * @throws Exception
	 */
	public Map<String, Object> apiFileUpload(String urlText, MultipartFile multipartFile, String type, String contentType) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String apiUrl = "http://erp.ectech.co.kr:40931";
		String result = "";
		
		JSONParser jParser = new JSONParser();
		JSONObject jObj = new JSONObject();
		
		String boundary = "----";
        String LINE_FEED = "\r\n";
        String charset = "UTF-8";


		
//		try {
			URL url = new URL(apiUrl + urlText);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
////			con.setRequestProperty("Content-Type", "multipart/form-data");
//			con.setRequestProperty("Content-Type", "multipart/form-data;charset=utf-8;boundary=" + boundary);
//			con.setDoInput(true);
//			con.setDoOutput(true);
//			con.setUseCaches(false);
//			
//			PrintWriter writer;
//			OutputStream outputStream;
//			File file = new File(multipartFile.getOriginalFilename());
//			multipartFile.transferTo(file);
//			
//			outputStream = con.getOutputStream();
//			writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
//			
//			 /** 파일 데이터를 넣는 부분**/
//			writer.append("--" + boundary).append(LINE_FEED);
//			writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"").append(LINE_FEED);
//			writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append(LINE_FEED);
//			writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
//			writer.append(LINE_FEED);
//			writer.flush();
//			
//			
//			FileInputStream inputStream = new FileInputStream(file);
//			byte[] buffer = new byte[(int)file.length()];
//			int bytesRead = -1;
//			while ((bytesRead = inputStream.read(buffer)) != -1) {
//			    outputStream.write(buffer, 0, bytesRead);
//			}
//			outputStream.flush();
//			inputStream.close();
//			writer.append(LINE_FEED);
//			writer.flush();
//			
//			writer.append("--" + boundary + "--").append(LINE_FEED);
//			writer.close();
			
			//--------------------------------------------------------------------------------------------------------------------------------------------
//			con.setRequestProperty("Content-Type", "multipart/form-data");
			con.setRequestProperty("Content-Type", "multipart/form-data;charset=utf-8;boundary=" + boundary);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			
			PrintWriter writer;
			OutputStream outputStream;
			
			System.out.println("--------------------------------------!!!!!!!!!!! multipartFile :" + multipartFile);
			System.out.println("-----------------------------------@@ multipartFile = " + multipartFile.getResource());
			
			File file = this.multipartToFile(multipartFile);
			
			outputStream = con.getOutputStream();
			writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
			
			/** 파일 데이터를 넣는 부분**/
			writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"");
			writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName()));
			writer.append("Content-Transfer-Encoding: binary");
			writer.flush();
			
			System.out.println("-------------------------------------@ filename = " + file.getName());
			System.out.println("-------------------------------------@ URLConnection.guessContentTypeFromName(file.getName()) = " + URLConnection.guessContentTypeFromName(file.getName()));
			
//			FileInputStream inputStream = new FileInputStream(file);
//			System.out.println("-----------------------------------@@ 여긴가? = ");
//			
//			byte[] buffer = new byte[(int)file.length()];
//			int bytesRead = -1;
//			while ((bytesRead = inputStream.read(buffer)) != -1) {
//			    outputStream.write(buffer, 0, bytesRead);
//			}
//			System.out.println("-----------------------------------@@ 여긴가????? = ");
//			
//			outputStream.flush();
//			inputStream.close();
//			writer.flush();
//			writer.close();
			
			System.out.println("-------------------------------------@ writer2 = " + writer);

			result = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")).readLine();
			
			jObj = (JSONObject) jParser.parse(result);
			
			System.out.println("-------------------------------------@ jObj : " + jObj);
			
//			returnMap.put("rslt", jObj.get("rslt"));
//			returnMap.put("data", jObj.get("data"));
			
			System.out.println("-------------------------------------@ result : " + result);
			
//		} catch (Exception e) {
//			returnMap.put("rslt", "error");
//			returnMap.put("data", e.getMessage());
//		}
		
		System.out.println("-------------------------------------@ returnMap : " + returnMap);
		
		return returnMap;
	}
	
	
	// RCS 브랜드 등록 요청
//	[
//	  {
//	    "corpId": "string",
//	    "projectId": "string",
//	    "regBrand": {
//	      "name": "브랜드 이름",
//	      "description": "브랜드 설명",
//	      "tel": "브랜드 전화번호",
//	      "menus": {},
//	      "categoryId": "버튼 정보",
//	      "subCategoryId": "버튼 정보",
//	      "categoryOpt": "검색용 키워드",
//	      "zipCode": "우편번호",
//	      "roadAddress": "도로명주소",
//	      "detailAddress": "상세주소",
//	      "email": "이메일주소",
//	      "webSiteUrl": "홈페이지 주소"
//	    },
//	    "mainMdn": "string",
//	    "profileImgFilePath": "string",
//	    "bgImgFilePath": "string",
//	    "certiFilePath": "string",
//	    "chatbots": [
//	      {
//	        "mdn": "string",
//	        "subnum": "string",
//	        "rcsReply": "string",
//	        "subTitle": "string",
//	        "service": "string",
//	        "display": "string",
//	        "webhook": "string"
//	      }
//	    ]
//	  }
//	]
	public RestResult<?> saveRcsBrandReqForApi(Map<String, Object> params) {
		
		System.out.println("---------------------------------------------------@@" + params);
		
		// 사용체널 JSON 값 처리
		String price_info_json = "";

		price_info_json += "{";
		price_info_json += "	\"RCS\"		: \"" + CommonUtils.getString(params.get("radio_rcs")) + "\",";
		price_info_json += "	\"SMS/MMS\"	: \"" + CommonUtils.getString(params.get("radio_mms")) + "\",";
		price_info_json += "	\"PUSH\"	: \"" + CommonUtils.getString(params.get("radio_push")) + "\",";
		price_info_json += "	\"KAKAO\"	: \"" + CommonUtils.getString(params.get("radio_kko")) + "\",";
		price_info_json += "	\"MO\"		: \"" + CommonUtils.getString(params.get("radio_mo")) + "\"";
		price_info_json += "}";
		
		return null;
	}
	
	public File multipartToFile(MultipartFile mfile) throws Exception {
		File file = new File(mfile.getOriginalFilename());
		mfile.transferTo(file);
		return file;
	}
}


