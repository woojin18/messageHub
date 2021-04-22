package kr.co.uplus.cm.common.service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${file.props.img.upload-path}")
    String imgUploadPath;

    @Value("${file.props.img.limit-size}")
    long imgUploadLimitSize;

    @Value("${file.props.img.permit-exten}")
    String imgUploadPermitExten;

    @Value("${file.props.excel.permit-exten}")
    String excelUploadPermitExten;

    /**
     * 엑셀파일에서 엑셀데이터 가져오기
     * @param excelFile
     * @param offset
     * @param colKeys
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getExcelDataList(MultipartFile excelFile, int offset, List<String> colKeys) throws Exception {
        List<Map<String, Object>> excelList = new ArrayList<>();
        Map<String, Object> excelInfo = new HashMap<String, Object>();

        String fileName = excelFile.getOriginalFilename();
        String fileExten = fileName.substring(fileName.lastIndexOf(".") + 1);

        //엑셀 업로드 확장자 유효성 체크
        if(Stream.of(excelUploadPermitExten.split(","))
                .map(String::trim)
                .noneMatch(s->s.toLowerCase().contains(fileExten.toLowerCase()))) {
            log.error("{} 허용된지 않은 엑셀 업로드 확장자======>", this.getClass());
            log.error("원본 파일명 : {}", fileName);
            log.error("파일 확장자 : {}", fileExten);
            throw new Exception("허용된지 않은 엑셀 업로드 확장자");
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

            for(int j=0; j<colKeys.size(); j++) {
                excelInfo.put(colKeys.get(j), getExcelCellValue(row.getCell(j)));
            }
            excelList.add(excelInfo);
        }

        return excelList;
    }

    /**
     * 파일업로드
     * @param files
     * @param useCh
     * @param loginId
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> uploadImgFile(MultipartFile files, String[] useCh, String loginId) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        //빈값 확인
        if(useCh == null || useCh.length == 0) {
            rtn.setSuccess(false);
            rtn.setMessage("사용채널 정보가 존재하지 않습니다.");
        }
        if(files == null) {
            rtn.setSuccess(false);
            rtn.setMessage("업로드할 파일이 존재하지 않습니다.");
        }

        String pattern = "[\"!@#$%^&'*]";
        String fileName = files.getOriginalFilename().replaceAll(pattern, "");  //원본 파일명
        String fileExten = fileName.substring(fileName.lastIndexOf(".") + 1);

        //이미지 업로드 확장자 유효성 체크
        if(Stream.of(imgUploadPermitExten.split(","))
                .map(String::trim)
                .noneMatch(s->s.toLowerCase().contains(fileExten.toLowerCase()))) {
            rtn.setSuccess(false);
            rtn.setMessage("허용되지 않는 확장자입니다.");
            return rtn;
        }

        //이미지 업로드 용량 유효성 체크
        if(files.getSize() > imgUploadLimitSize) {
            rtn.setSuccess(false);
            rtn.setMessage("허용되는 업로드 용량 크기를 초과하였습니다.");
            return rtn;
        }

        Path prjRelPath = Paths.get("");
        String currentYmd = DateUtil.getCurrnetDate("yyyyMMdd");
        String prjAbsPath = "";
        String uploadDirPath = this.imgUploadPath;
        String convertUUID = UUID.randomUUID().toString().replace("-", ""); // 해쉬명 생성

        //이미지 업로드 임시 폴더
        prjAbsPath = prjRelPath.toAbsolutePath().toString();
        uploadDirPath = prjAbsPath + this.imgUploadPath + convertUUID + File.separator;

        File uploadDir = new File(uploadDirPath);

        //folder 없으면 생성
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            File uplaodFile = File.createTempFile(currentYmd+"_", "."+fileExten, uploadDir);  //업로드된 파일정보
            FileCopyUtils.copy(files.getInputStream(), new FileOutputStream(uplaodFile));

            //채널별 분기작업
            String oriFileFullPath = uplaodFile.getAbsolutePath();
            String oriFileName = uplaodFile.getName().replaceFirst("[.][^.]+$", "");

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = null;

            //API 정보
            Map<String, String> headerMap = null;
            JSONObject reqFileObject = null;
            Map<String, Object> resultMap = null;
            Map<String, Object> dataMap = null;
            ObjectMapper mapper = null;

            String apiKey = this.getApiKey("TEST_CORP", "TEST_PROJECT");  //TODO : 수정
            String svcId = this.getSvcId();
            String resizePath = "";
            String apiUrl = "";
            String imgUrl = "";
            String fileId = "";

            for(String ch : useCh) {
                //TODO: 삭제 (API : 포탈연동 오류)
                if(StringUtils.equals("FRIENDTALK", ch)) {
                    continue;
                }

                /** 파일 resize */
                resizePath = ImageUtil.imageResize(
                    oriFileFullPath
                    , uploadDirPath
                    , oriFileName+"_"+ch+"."+fileExten
                    , fileExten
                    , Const.CH_IMAGE_RESIZE.get(ch+"_W")
                    , Const.CH_IMAGE_RESIZE.get(ch+"_H"));

                /** API Send */
                //set Header Info
                headerMap = new HashMap<String, String>();
                headerMap.put("apiKey", apiKey);
                headerMap.put("svcId", svcId);
                headerMap.put("ch", ch);

                //set reqFile Info
                reqFileObject = new JSONObject();
                reqFileObject.put("fileId", CommonUtils.getCommonId(Const.IMG_PREFIX, 5));
                reqFileObject.put("wideYn", "N");

                //send
                apiUrl = Const.FILE_UPLOAD_API_URL+ch;
                resultMap = apiInterface.sendImg(
                    apiUrl
                    , headerMap
                    , new File(resizePath)
                    , reqFileObject.toJSONString());

                //send result
                imgUrl = "";
                if(!CommonUtils.isEmptyValue(resultMap, "rslt")
                        && StringUtils.equals(Const.API_SUCCESS, CommonUtils.getString(resultMap.get("rslt")))) {
                    if(!CommonUtils.isEmptyValue(resultMap, "data")) {
                        mapper = new ObjectMapper();
                        dataMap = mapper.convertValue(resultMap.get("data"), Map.class);
                        if(!CommonUtils.isEmptyValue(dataMap, "imgUrl")
                                && !CommonUtils.isEmptyValue(dataMap, "fileId")) {
                            imgUrl = (String) dataMap.get("imgUrl");
                            fileId = (String) dataMap.get("fileId");
                        }
                    }
                }

                if(StringUtils.isBlank(imgUrl) || StringUtils.isBlank(fileId)) {
                    if(!CommonUtils.isEmptyValue(resultMap, "rsltDesc")){
                        throw new Exception((String) resultMap.get("rsltDesc"));
                    } else {
                        throw new Exception("API 파일 전송 실패");
                    }
                }

                //useChInfo set
                jsonObject = new JSONObject();
                jsonObject.put("CH", ch);
                jsonObject.put("URL", imgUrl);
                jsonObject.put("FILE_ID", fileId);
                jsonArray.add(jsonObject);
            }

            //DB 등록
            Map<String, Object> params = new HashMap<>();
            params.put("corpId", "TEST_CORP_ID");    //TODO : 고객 ID(로그인세션에서 가져오자)
            params.put("useChInfo", jsonArray.toJSONString());
            params.put("originFileName", fileName);    //TODO : 삭제 원본파일은 저장하지 않는다. DB 변경되면 삭제
            params.put("imageFileName", uplaodFile.getName());    //TODO : 삭제 원본파일은 저장하지 않는다. DB 변경되면 삭제
            params.put("imageFilePath", oriFileFullPath);    //TODO : 삭제 원본파일은 저장하지 않는다. DB 변경되면 삭제
            params.put("loginId", loginId);
            generalDao.insertGernal(DB.QRY_INSERT_IMAGE_FILE, params);

        } catch (Exception e) {
            log.error("{} Error : {}", this.getClass(), e);
            throw new Exception(e);
        } finally {
            deleteFolder(uploadDirPath);
        }

        return rtn;
    }

    //고객사별 이미지 조회
    public RestResult<Object> selectImageList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
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
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> deleteImageFile(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        //삭제전 사용 템플릿 있는지 확인
        int useCnt = generalDao.selectGernalCount(DB.QTY_SELECT_FILE_ID_USE_CNT, params);
        if(useCnt > 0) {
            rtn.setSuccess(false);
            rtn.setMessage("이미지 파일을 사용하고 있는 템플릿이 존재하여 삭제할 수 없습니다.");
            rtn.setData(params);
            return rtn;
        }

        /** 삭제처리 */
        //TODO : API 삭제요청


        //DB 삭제처리
        int resultCnt = generalDao.deleteGernal(DB.QRY_DELETE_IMAGE, params);

        if (resultCnt <= 0) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
        } else {
            rtn.setData(params);
        }

        return rtn;
    }

    // 파일업로드
    public RestResult<Object> uploadFile(MultipartFile files, String loginId) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        if(files == null) {
            rtn.setSuccess(false);
            rtn.setMessage("업로드할 파일이 존재하지 않습니다.");
        }

        String pattern = "[\"!@#$%^&'*]";
        String fileName = files.getOriginalFilename().replaceAll(pattern, "");  //원본 파일명
        String fileExten = fileName.substring(fileName.lastIndexOf(".") + 1);

        //이미지 업로드 용량 유효성 체크
        if(files.getSize() > imgUploadLimitSize) {
            rtn.setSuccess(false);
            rtn.setMessage("허용되는 업로드 용량 크기를 초과하였습니다.");
            return rtn;
        }

        Path prjRelPath = Paths.get("");
        String currentYmd = DateUtil.getCurrnetDate("yyyyMMdd");
        String prjAbsPath = "";
        String uploadDirPath = this.imgUploadPath;

        //TODO : 이미지가 서버가 따로 존재. 로컬만 테스트를 위해 경로를 현프로젝트 밑으로..
        //TODO : 추후 이미지서버를 알면 업로드 로직을 변경하자
        if(Arrays.stream(env.getActiveProfiles()).anyMatch(
                env -> env.equalsIgnoreCase("local"))){
            prjAbsPath = prjRelPath.toAbsolutePath().toString();
            uploadDirPath = prjAbsPath + this.imgUploadPath;
        }

        File uploadDir = new File(uploadDirPath);

        //folder 없으면 생성
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        File uplaodFile = File.createTempFile(currentYmd+"_", "."+fileExten, uploadDir);  //업로드된 파일정보
        FileCopyUtils.copy(files.getInputStream(), new FileOutputStream(uplaodFile));

        String attachFileName = uplaodFile.getName();
        String attachFilePath = uplaodFile.getAbsolutePath();

        //TODO : 이미지가 서버가 따로 존재. 로컬만 테스트를 위해 경로를 현프로젝트 밑으로..
        //TODO : 추후 이미지서버를 알면 업로드 로직을 변경하자
        if(Arrays.stream(env.getActiveProfiles()).anyMatch(
                env -> env.equalsIgnoreCase("local"))){
        	attachFilePath = "/assets/images/uploadImage/" + uplaodFile.getName();
        }

        //DB 등록
        Map<String, Object> seqParams = new HashMap<>();
        seqParams.put("attachFileName", attachFileName);
        seqParams.put("attachFilePath", attachFilePath);
        seqParams.put("loginId", loginId);
        generalDao.insertGernal("common.insertAttachFile", seqParams);

        rtn.setSuccess(true);
        rtn.setData(seqParams);

        return rtn;
    }

    /**
     * TODO : CM_APIKEY 테이블 에서 가져옴
     * - 1. APIKEY 관리 작업안됨
     * - 2. status 정의 없음
     * - 3. 중복시 유일키 가져오는 방식 정의 없음.
     * - 4. WEB_YN 후 ROWNUM 1
     * API Key 가져오기
     * @return
     */
    public String getApiKey(String corpId, String projectId) {
        String apiKey = "1";
        return apiKey;
    }

    /**
     * FIXME : 뭔지 모른다 알아보는중
     * TODO : G/W 삭제 예정 -> 추후 사용 한곳 알기위해 사용
     * SVC ID 가져오기
     * @return
     */
    public String getSvcId() {
        String apiKey = "WEB01";
        return apiKey;
    }

    /**
     * get excel cell value
     * @param cell
     * @return
     */
    private Object getExcelCellValue(Cell cell) {
        if(cell == null) {
            return "";
        } else if(cell.getCellTypeEnum() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if(cell.getCellTypeEnum() == CellType.FORMULA) {
            return cell.getCellFormula();
        } else if(cell.getCellTypeEnum() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if(cell.getCellTypeEnum() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        } else if(cell.getCellTypeEnum() == CellType.BLANK) {
            return "";
        } else {
            return "";
        }
    }

    private static void deleteFolder(String path) {
        File folder = new File(path);
        try {
            if(folder.exists()){
                File[] folder_list = folder.listFiles(); //파일리스트 얻어오기
                for (int i = 0; i < folder_list.length; i++) {
                    if(folder_list[i].isFile()) {
                        folder_list[i].delete();
                    }else {
                        deleteFolder(folder_list[i].getPath());
                    }
                    folder_list[i].delete();
                }
                folder.delete(); //폴더 삭제
            }
        } catch (Exception e) {
            log.error("deleteFolder Error : {}", e);
        }
    }

}
