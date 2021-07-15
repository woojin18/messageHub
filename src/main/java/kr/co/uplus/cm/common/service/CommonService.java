package kr.co.uplus.cm.common.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.model.AuthUser;
import kr.co.uplus.cm.common.utils.SpringUtils;
import kr.co.uplus.cm.config.ApiConfig;
import kr.co.uplus.cm.login.service.AuthService;
import kr.co.uplus.cm.utils.ApiInterface;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.DateUtil;
import kr.co.uplus.cm.utils.GeneralDao;
import kr.co.uplus.cm.utils.ImageUtil;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CommonService {

    @Autowired
    GeneralDao generalDao;

    @Autowired
    Environment env;

    @Autowired
    ApiInterface apiInterface;

    @Autowired
    AuthService authSvc;

    @Deprecated
    String imgUploadPath;

    @Deprecated
    long imgUploadLimitSize;

    /**
     * 엑셀파일에서 엑셀데이터 가져오기
     *
     * @param excelFile
     * @param offset
     * @param colKeys
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getExcelDataList(MultipartFile excelFile, int offset, List<String> colKeys)
            throws Exception {
        List<Map<String, Object>> excelList = new ArrayList<>();
        Map<String, Object> excelInfo = new HashMap<String, Object>();

        String fileName = excelFile.getOriginalFilename();
        String fileExten = fileName.substring(fileName.lastIndexOf(".") + 1);

        //get Excel Set Value
        Map<String, Object> excelUploadSet = (Map<String, Object>) selectFileUploadSet(Const.FileUploadSet.EXCEL);
        String excelPermitExten = CommonUtils.getStrValue(excelUploadSet, Const.FileUploadSetKey.PERMIT_EXTEN);

        // 엑셀 업로드 확장자 유효성 체크
        if (Stream.of(excelPermitExten.split(",")).map(String::trim)
                .noneMatch(s -> s.toLowerCase().contains(fileExten.toLowerCase()))) {
            log.error("{} 허용되지 않은 엑셀 업로드 확장자======>", this.getClass());
            log.error("원본 파일명 : {}", fileName);
            log.error("파일 확장자 : {}", fileExten);
            throw new Exception("허용되지 않은 엑셀 업로드 확장자");
        }

        Workbook workbook = null;
        if (fileExten.equals("xlsx")) {
            workbook = new XSSFWorkbook(excelFile.getInputStream());
        } else if (fileExten.equals("xls")) {
            workbook = new HSSFWorkbook(excelFile.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        Row row = null;
        for (int i = offset; i < worksheet.getPhysicalNumberOfRows(); i++) {
            row = worksheet.getRow(i);
            excelInfo = new HashMap<String, Object>();

            for (int j = 0; j < colKeys.size(); j++) {
                excelInfo.put(colKeys.get(j), getExcelCellValue(row.getCell(j)));
            }
            excelList.add(excelInfo);
        }

        return excelList;
    }

    /**
     * 파일업로드
     * @param files
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> uploadImgFile(MultipartFile files, Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        String corpId = CommonUtils.getStrValue(params, "corpId");
        String projectId = CommonUtils.getStrValue(params, "projectId");
        ArrayList<String> useCh = (ArrayList<String>) params.get("useCh");
        String wideYn = StringUtils.defaultString(CommonUtils.getStrValue(params, "wideYn"), Const.COMM_NO);
        String loginId = CommonUtils.getStrValue(params, "userId");

        // 빈값 확인
        if (StringUtils.isBlank(corpId)) {
            throw new Exception("유효하지 않은 corpId");
        }
        if (StringUtils.isBlank(projectId)) {
            throw new Exception("유효하지 않은 projectId");
        }
        if (StringUtils.isBlank(loginId)) {
            throw new Exception("유효하지 않은 loginId");
        }
        if (useCh == null || useCh.size() == 0) {
            rtn.setSuccess(false);
            rtn.setMessage("사용채널 정보가 존재하지 않습니다.");
            return rtn;
        }
        if (files == null) {
            rtn.setSuccess(false);
            rtn.setMessage("업로드할 파일이 존재하지 않습니다.");
            return rtn;
        }

        //get Image Set Value
        Map<String, Object> imgUploadSet = (Map<String, Object>) selectFileUploadSet(Const.FileUploadSet.SEND_IMAGE);
        String imgPermitExten = CommonUtils.getStrValue(imgUploadSet, Const.FileUploadSetKey.PERMIT_EXTEN);

        //get File Prop
        List<Object> imgSetInfoList = selectImgUploadChSet();

        String pattern = "[\"!@#$%^&'.*]";
        String preFileName = getFileNameExt(files.getOriginalFilename(),0).replaceAll(pattern, "");
        String fileExten = getFileNameExt(files.getOriginalFilename(),1);
        String originFileName = preFileName+"."+fileExten;

        // 이미지 업로드 확장자 유효성 체크
        if (Stream.of(imgPermitExten.split(",")).map(String::trim)
                .noneMatch(s -> s.toLowerCase().contains(fileExten.toLowerCase()))) {
            rtn.setSuccess(false);
            rtn.setMessage("허용되지 않는 확장자입니다.");
            return rtn;
        }

        try {
            InputStream resizeImgStream = null;
            BufferedImage image = ImageIO.read(files.getInputStream());
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = null;

            // API 정보
            Map<String, String> headerMap = null;
            JSONObject reqFileObject = null;
            Map<String, Object> resultMap = null;
            Map<String, Object> dataMap = null;
            ObjectMapper mapper = null;
            Map<String, Integer> resizeInfo = null;

            String apiKey = this.getApiKey(corpId, projectId);
            String apiUrl = "";
            String imgUrl = "";
            String fileId = CommonUtils.getCommonId(Const.FILE_UPLOAD_PREFIX, 5); // 모든 채널 한개의 fileId 사용
            String responseFileId = "";
            String chNm = "";
            String wideImgYn = Const.COMM_NO;
            String apiWideYn = Const.COMM_NO;

            for (String ch : useCh) {
                chNm = ch;
                if (StringUtils.equals("FRIENDTALK", ch) && StringUtils.equals(Const.COMM_YES, wideYn)) {
                    chNm += "_WIDE";
                    apiWideYn = Const.COMM_YES;
                    wideImgYn = Const.COMM_YES;
                } else {
                    apiWideYn = Const.COMM_NO;
                }

                /** 파일 resize */
                resizeInfo = getChResize(imgSetInfoList, chNm);
                if (resizeInfo == null || !resizeInfo.containsKey(Const.IMG_RESIZE_WIDTH)
                        || !resizeInfo.containsKey(Const.IMG_RESIZE_HEIGHT)) {
                    throw new Exception("유효하지 않은 이미지 리사이즈 정보 : 채널(" + chNm + ")");
                }
                resizeImgStream = ImageUtil.imageResize(image, fileExten, resizeInfo.get(Const.IMG_RESIZE_WIDTH),
                        resizeInfo.get(Const.IMG_RESIZE_HEIGHT));
                if (resizeImgStream == null) {
                    throw new Exception("유효하지 않은 리사이즈 이미지");
                }

                /** API Send */
                // set Header Info
                headerMap = new HashMap<String, String>();
                headerMap.put("apiKey", apiKey);
                headerMap.put("ch", ch);

                // set reqFile Info
                reqFileObject = new JSONObject();
                reqFileObject.put("fileId", fileId);
                reqFileObject.put("wideYn", apiWideYn);

                // send
                apiUrl = ApiConfig.FILE_UPLOAD_API_URI + ch.toLowerCase();
                resultMap = apiInterface.sendStreamImg(apiUrl, headerMap, resizeImgStream, originFileName, reqFileObject.toJSONString());

                // send result
                imgUrl = "";
                if (!CommonUtils.isEmptyValue(resultMap, ApiConfig.GW_RESULT_CODE_FIELD_NM)
                        && StringUtils.equals(ApiConfig.GW_API_SUCCESS, CommonUtils.getString(resultMap.get(ApiConfig.GW_RESULT_CODE_FIELD_NM)))) {
                    if (!CommonUtils.isEmptyValue(resultMap, ApiConfig.COMMON_DATA_FIELD_NM)) {
                        mapper = new ObjectMapper();
                        dataMap = mapper.convertValue(resultMap.get(ApiConfig.COMMON_DATA_FIELD_NM), Map.class);
                        if (!CommonUtils.isEmptyValue(dataMap, "imgUrl")
                                && !CommonUtils.isEmptyValue(dataMap, "fileId")) {
                            imgUrl = (String) dataMap.get("imgUrl");
                            responseFileId = (String) dataMap.get("fileId");
                        }
                    }
                }

                if (StringUtils.isBlank(imgUrl) || StringUtils.isBlank(fileId)
                        || !StringUtils.equals(responseFileId, fileId)) {
                    if (!CommonUtils.isEmptyValue(resultMap, ApiConfig.GW_RESULT_MESSAGE_FIELD_NM)) {
                        throw new Exception((String) resultMap.get(ApiConfig.GW_RESULT_MESSAGE_FIELD_NM));
                    } else {
                        throw new Exception("API 파일 전송 실패");
                    }
                }

                // useChInfo set
                jsonObject = new JSONObject();
                jsonObject.put("CH", ch);
                jsonObject.put("URL", imgUrl);
                jsonArray.add(jsonObject);
            }

            // DB 등록
            Map<String, Object> sParams = new HashMap<>();
            sParams.put("fileId", fileId);
            sParams.put("corpId", corpId);
            sParams.put("useChInfo", jsonArray.toJSONString());
            sParams.put("wideImgYn", wideImgYn);
            sParams.put("originFileName", originFileName);
            sParams.put("loginId", loginId);
            generalDao.insertGernal(DB.QRY_INSERT_IMAGE_FILE, sParams);

        } catch (Exception e) {
            log.error("{} Error : {}", this.getClass(), e);
            throw new Exception(e);
        }

        return rtn;
    }

    /**
     * 고객사별 이미지 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectImageList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        if (params.containsKey("pageNo") && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize") && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if (rtn.getPageInfo() != null) {
                // 카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_IMAGE_LIST_CNT, params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_IMAGE_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * 이미지 삭제
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> deleteImageFile(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        // 삭제전 사용 템플릿 있는지 확인
        int useCnt = generalDao.selectGernalCount(DB.QRY_SELECT_FILE_ID_USE_CNT, params);
        if (useCnt > 0) {
            rtn.setSuccess(false);
            rtn.setMessage("이미지 파일을 사용하고 있는 템플릿이 존재하여 삭제할 수 없습니다.");
            rtn.setData(params);
            return rtn;
        }

        // DB 삭제처리
        int resultCnt = generalDao.deleteGernal(DB.QRY_DELETE_IMAGE, params);

        if (resultCnt <= 0) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
        } else {
            rtn.setData(params);
        }

        return rtn;
    }

    /**
     * 이미지 URL 정보 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectImageUrlInfo(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        rtn.setData(generalDao.selectGernalObject(DB.QRY_SELECT_IMAGE_URL_INFO, params));
        return rtn;
    }

    // 파일업로드
    public RestResult<Object> uploadFile(MultipartFile files, String loginId) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        if (files == null) {
            rtn.setSuccess(false);
            rtn.setMessage("업로드할 파일이 존재하지 않습니다.");
        }

//        String pattern = "[\"!@#$%^&'*]";
//        String fileName = files.getOriginalFilename().replaceAll(pattern, ""); // 원본 파일명
//        String fileExten = fileName.substring(fileName.lastIndexOf(".") + 1);

        String pattern = "[\"!@#$%^&'.*]";
        String preFileName = getFileNameExt(files.getOriginalFilename(),0).replaceAll(pattern, "");
        String fileExten = getFileNameExt(files.getOriginalFilename(),1);
        String fileName = preFileName+"."+fileExten;

        // 이미지 업로드 용량 유효성 체크
        if (files.getSize() > this.imgUploadLimitSize) {
            rtn.setSuccess(false);
            rtn.setMessage("허용되는 업로드 용량 크기를 초과하였습니다.");
            return rtn;
        }

        Path prjRelPath = Paths.get("");
        String currentYmd = DateUtil.getCurrentDate("yyyyMMdd");
        String prjAbsPath = "";
        String uploadDirPath = this.imgUploadPath;

        // TODO : 이미지가 서버가 따로 존재. 로컬만 테스트를 위해 경로를 현프로젝트 밑으로..
        // TODO : 추후 이미지서버를 알면 업로드 로직을 변경하자
        if (Arrays.stream(env.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("local"))) {
            prjAbsPath = prjRelPath.toAbsolutePath().toString();
            uploadDirPath = prjAbsPath + imgUploadPath;
        }

        File uploadDir = new File(uploadDirPath);

        // folder 없으면 생성
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        File uplaodFile = File.createTempFile(currentYmd + "_", "." + fileExten, uploadDir); // 업로드된 파일정보
        FileCopyUtils.copy(files.getInputStream(), new FileOutputStream(uplaodFile));

        String attachFileName = uplaodFile.getName();
        String attachFilePath = uplaodFile.getAbsolutePath();

        // TODO : 이미지가 서버가 따로 존재. 로컬만 테스트를 위해 경로를 현프로젝트 밑으로..
        // TODO : 추후 이미지서버를 알면 업로드 로직을 변경하자
        if (Arrays.stream(env.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("local"))) {
            attachFilePath = "/assets/images/uploadImage/" + uplaodFile.getName();
        }

        // DB 등록
        Map<String, Object> seqParams = new HashMap<>();
        seqParams.put("attachFileName", attachFileName);
        seqParams.put("attachFilePath", attachFilePath);
        seqParams.put("loginId", loginId);
        generalDao.insertGernal("common.insertAttachFile", seqParams);

        rtn.setSuccess(true);
        rtn.setData(seqParams);

        return rtn;
    }

 // 파일 업로드 및 테이블 인서트
 	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
 	public String uploadFile(MultipartFile files, String userId, String uploadDirPath) throws Exception {
 		String fileName = "";	//원본 파일명
 		File destinationFile = null;	//업로드된 파일정보

 		File uploadDir = new File(uploadDirPath);
 		String filePath = "";
 		String pattern = "[\"!@#$%^&'.*]";

 		if(!uploadDir.exists()) {
 			uploadDir.mkdirs();
 		}

 		String preFileName = getFileNameExt(files.getOriginalFilename(),0).replaceAll(pattern, "");
 		String ext = getFileNameExt(files.getOriginalFilename(),1);
// 		fileName = files.getOriginalFilename().replaceAll(pattern, "");
 		fileName = preFileName+"."+ext;
 		destinationFile = File.createTempFile("upload", fileName, uploadDir);
 		FileCopyUtils.copy(files.getInputStream(), new FileOutputStream(destinationFile));

 		// FILEINFO isert
 		Map<String, Object> saveMap = new HashMap<String, Object>();

 		filePath = destinationFile.getAbsolutePath().replaceAll("\\\\", "/");
 		saveMap.put("attach_file_name", fileName);
 		saveMap.put("attach_file_path", filePath);
 		saveMap.put("userId", userId);
 		String rtnSeqString = this.insertFileInfo(saveMap);

 		return rtnSeqString;
 	}

 	// 파일 업로드 후 인서트
 	public String insertFileInfo(Map<String, Object> saveMap) throws Exception {
 		String seq = CommonUtils.getCommonId("FLE", 5);

 		saveMap.put("attach_file_seq", seq);

 		generalDao.insertGernal(DB.QRY_INSERT_FILE_INFO, saveMap);

 		return seq;
 	}

    /**
     * get API Key
     * @return
     * @throws Exception
     */
    public String getApiKey(String corpId, String projectId) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("corpId", corpId);
        params.put("projectId", projectId);
        String apiKey = CommonUtils.getString(generalDao.selectGernalObject(DB.QRY_SELECT_API_KEY, params));

        if (StringUtils.isBlank(apiKey)) {
            log.error("{}.getApiKey no result search for api key. corpID : {}, projectId : {}"
                    , this.getClass(), corpId, projectId);
            throw new Exception("API 키에 대한 검색 결과 없음.");
        }

        return apiKey;
    }

    /**
     * 등록자(reg_id) 설정
     *
     * @return
     */
    public Map<String, Object> setRegId(HttpServletRequest request, Map<String, Object> params) {
        AuthUser user = (AuthUser) authSvc.loadUserByUsername(request.getHeader("loginId"));
        params.put("regId", user.getLoginId());
        return params;
    }

    /**
     * 수정자(upd_id) 설정
     *
     * @return
     */
    public Map<String, Object> setUpdId(HttpServletRequest request, Map<String, Object> params) {
        AuthUser user = (AuthUser) authSvc.loadUserByUsername(request.getHeader("loginId"));
        params.put("updId", user.getLoginId());
        return params;
    }

    /**
     * 이미지업로드 채널 설정 조회
     * @throws Exception
     */
    public List<Object> selectImgUploadChSet() throws Exception {
        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_IMG_UPLOAD_CH_SET, null);

        if(rtnList == null || rtnList.size() == 0) {
            log.error("{}.selectImgUploadChSet no result search for image upload Channel Set Value. corpID : {}", this.getClass());
            throw new Exception("이미지업로드 채널 설정 검색 결과 없음.");
        }
        return rtnList;
    }

    /**
     * 파일업로드 설정 조회
     * @throws Exception
     */
    public Object selectFileUploadSet(String fileUploadSet) throws Exception {
        HashMap<String, Object> params = new HashMap<>();
        params.put("codeVal1", fileUploadSet);
        params.put("codeName2", Const.FileUploadSetKey.PERMIT_EXTEN);

        Object rtnObj = generalDao.selectGernalObject(DB.QRY_SELECT_FILE_UPLOAD_SET, params);
        if(rtnObj == null || CommonUtils.isEmptyObject(rtnObj)) {
            log.error("{}.selectImageUploadSet no result search for image upload Set Value. corpID : {}", this.getClass());
            throw new Exception("이미지업로드 설정 검색 결과 없음.");
        }

        return rtnObj;
    }

    /**
     * 사용자 정보 Set
     * @param params
     * @return
     */
    public Map<String, Object> setUserInfo(Map<String, Object> params) {
        HashMap<String, Object> sParams = new HashMap<String, Object>(params);
        HttpServletRequest request = SpringUtils.getCurrentRequest();
        String loginId = request.getHeader("loginId");

        if(StringUtils.isNotBlank(loginId)) {
            AuthUser authUser = (AuthUser) authSvc.loadUserByUsername(request.getHeader("loginId"));
            String userId = StringUtils.defaultIfBlank(authUser.getUserId(), "");
            String corpId = StringUtils.defaultIfBlank(authUser.getCorpId(), "");

            Cookie[] cookies = request.getCookies();
            String key = "project";
            String projectId = "";

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(key)) {
                        projectId = cookie.getValue();
                        break;
                    }
                }
            }

            sParams.put("userId", userId);
            sParams.put("corpId", corpId);
            sParams.put("projectId", projectId);
        }

        return sParams;
    }

    /**
     * get 이미지 업로드 채널별 용량제한 정보
     * @param imgSetInfoList
     * @param ch
     * @return
     */
    @SuppressWarnings({ "unchecked", "unused" })
    private long getChLimitSize(List<Object> imgSetInfoList, String ch) {
        Long limitSize = NumberUtils.LONG_ZERO;
        HashMap<String, Object> map = new HashMap<>();
        for(Object obj : imgSetInfoList) {
            map = (HashMap<String, Object>) obj;
            if(ch.equalsIgnoreCase((String) map.get("ch"))) {
                limitSize = Long.parseLong((String) map.get("limitSize"));
                break;
            }
        }
        return limitSize;
    }

    /**
     * get 이미지 업로드 채널별 리사이즈 정보
     * @param imgSetInfoList
     * @param ch
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Integer> getChResize(List<Object> imgSetInfoList, String ch) {
        Map<String, Integer> resizeMap = new HashMap<String, Integer>();
        HashMap<String, Object> map = new HashMap<>();
        for(Object obj : imgSetInfoList) {
            map = (HashMap<String, Object>) obj;
            if(ch.equalsIgnoreCase((String) map.get("ch"))) {
                resizeMap.put(Const.IMG_RESIZE_WIDTH, Integer.parseInt((String) map.get("resizeWidth")));
                resizeMap.put(Const.IMG_RESIZE_HEIGHT, Integer.parseInt((String) map.get("resizeHeight")));
                break;
            }
        }
        return resizeMap;
    }

    /**
     * get excel cell value
     *
     * @param cell
     * @return
     */
    private Object getExcelCellValue(Cell cell) {
        if (cell == null) {
            return "";
        } else if (cell.getCellTypeEnum() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
            return cell.getCellFormula();
        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        } else if (cell.getCellTypeEnum() == CellType.BLANK) {
            return "";
        } else {
            return "";
        }
    }

    public RestResult<Object> selectCodeList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CODE, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * 파일명과 확장자를 구분해서가져오기
     * @param fullFileName
     * @param flag (0:파일명, 1:확장자)
     * @return
     */
    public static String getFileNameExt(String fullFileName,int flag) {
        if(flag<=0) flag = 0;
        else flag = 1;
        int pos = fullFileName.lastIndexOf( "." );
        String ext = fullFileName.substring( pos + 1 );
        String fileName = fullFileName.substring(0, pos);
        String[] fileNameExt = {fileName,ext};
        return fileNameExt[flag];
    }

    /**
     * REDIS 테이블 동기화처리
     * @param cmdTgt : 업데이트할 테이블 명
     * @return
     */
    public void updateCmCmdForRedis(String cmdTgt) throws Exception {
    	HashMap<String, Object> saveMap = new HashMap<>();
    	saveMap.put("cmdTgt", cmdTgt);

        generalDao.updateGernal("common.updateCmCmdForRedis", saveMap);
    }
}
