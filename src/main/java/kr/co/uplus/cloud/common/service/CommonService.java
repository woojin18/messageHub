package kr.co.uplus.cloud.common.service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.uplus.cloud.common.consts.DB;
import kr.co.uplus.cloud.common.dto.RestResult;
import kr.co.uplus.cloud.utils.CommonUtils;
import kr.co.uplus.cloud.utils.DateUtil;
import kr.co.uplus.cloud.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CommonService {

    @Autowired
    GeneralDao generalDao;

    @Autowired
    Environment env;

    @Value("${file.props.img.upload-path}")
    String imgUploadPath;

    @Value("${file.props.img.limit-size}")
    long imgUploadLimitSize;

    @Value("${file.props.img.permit-exten}")
    String imgUploadPermitExten;

    @Value("${file.props.excel.permit-exten}")
    String excelUploadPermitExten;

    //엑셀파일에서 엑셀데이터 가져오기
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


    // 파일업로드
    public RestResult<Object> uploadImgFile(MultipartFile files, String useCh, String loginId) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        //빈값 확인
        if(StringUtils.isBlank(useCh)) {
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

        //String uplaodFilePath = uplaodFile.getParentFile().toString();
        String uplaodFileFullPath = uplaodFile.getAbsolutePath();
        String uplaodFileName = uplaodFile.getName();

        //TODO : 이미지가 서버가 따로 존재. 로컬만 테스트를 위해 경로를 현프로젝트 밑으로..
        //TODO : 추후 이미지서버를 알면 업로드 로직을 변경하자
        if(Arrays.stream(env.getActiveProfiles()).anyMatch(
                env -> env.equalsIgnoreCase("local"))){
            uplaodFileFullPath = "/assets/images/uploadImage/" + uplaodFile.getName();
        }


        log.info("======================================");
        log.info("File Origianl name : {}", files.getOriginalFilename());
        //log.info("File Upload Path : {}", destinationFile.getAbsolutePath());
        //log.info("File Upload name : {}", fileName);
        log.info("File Upload Path : {}", uplaodFileFullPath);
        log.info("File Upload name : {}", uplaodFileName);
        log.info("======================================");

        //DB 등록
        Map<String, Object> seqParams = new HashMap<>();
        seqParams.put("corpId", "TEST_CORP_ID");    //TODO : 고객 ID(로그인세션에서 가져오자)
        seqParams.put("useChInfo", useCh);
        seqParams.put("originFileName", fileName);
        seqParams.put("imageFileName", uplaodFileName);
        seqParams.put("imageFilePath", uplaodFileFullPath);
        seqParams.put("loginId", loginId);
        generalDao.insertGernal("common.insertImageFile", seqParams);

        rtn.setSuccess(true);
        rtn.setData(seqParams);

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
    @SuppressWarnings("unchecked")
    public RestResult<Object> deleteImageFile(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_IMAGE_LIST, params);
        String uploadDirPath = this.imgUploadPath;

        //TODO : 이미지가 서버가 따로 존재. 로컬만 테스트를 위해 경로를 현프로젝트 밑으로..
        //TODO : 추후 이미지서버를 알면 업로드 로직을 변경하자
        if(Arrays.stream(env.getActiveProfiles()).anyMatch(
                env -> env.equalsIgnoreCase("local"))){
            Path prjRelPath = Paths.get("");
            String prjAbsPath = prjRelPath.toAbsolutePath().toString();
            uploadDirPath = prjAbsPath + this.imgUploadPath;
        }

        if(CollectionUtils.isEmpty(rtnList)) {
            rtn.setSuccess(false);
            rtn.setMessage("삭제할 데이터가 존재하지 않습니다.");
            return rtn;
        }

        File file = null;
        Map<String, Object> imgMap = null;

        //파일삭제(파일삭제여부와 관계없이 로우삭제->파일삭제 후 DB 삭제시 이슈)
        for(Object imgObj :rtnList) {
            try {
                imgMap = (Map<String, Object>) imgObj;
                if(imgMap.containsKey("imageFileName")
                        && CommonUtils.isNotEmptyObject(imgMap.get("imageFileName"))) {
                    file = new File(uploadDirPath+imgMap.get("imageFileName"));
                    if( file.exists() ){
                        file.delete();
                    }
                }
            } catch (Exception e) {
                log.error("File Delete Error : {}", e);
            }
        }

        //데이터삭제
        int resultCnt = generalDao.deleteGernal(DB.QRY_DELETE_IMAGE, params);
        if (resultCnt <= 0) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
        } else {
            rtn.setSuccess(true);
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

}
