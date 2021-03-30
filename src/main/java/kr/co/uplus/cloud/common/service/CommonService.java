package kr.co.uplus.cloud.common.service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

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
        seqParams.put("attach_file_seq", "");
        seqParams.put("corp_id", "TEST_CORP_ID");    //TODO : 고객 ID(로그인세션에서 가져오자)
        seqParams.put("use_ch_info", useCh);
        seqParams.put("origin_file_name", fileName);
        seqParams.put("attach_file_name", uplaodFileName);
        seqParams.put("attach_file_path", uplaodFileFullPath);
        seqParams.put("reg_id", loginId);    //TODO : 로그인 ID(로그인세션에서 가져오자)
        seqParams.put("upd_id", loginId);    //TODO : 로그인 ID(로그인세션에서 가져오자)
        generalDao.insertGernal("common.insertImageFile", seqParams);

        rtn.setSuccess(true);
        rtn.setData(seqParams);

        return rtn;
    }

    //고객사별 이미지 조회
    public RestResult<Object> selectImageList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        Map<String, Object> pageInfo = (Map<String, Object>) params.get("pageInfo");

        if(pageInfo != null && !pageInfo.isEmpty()) {
            int selPage     = CommonUtils.getInt(pageInfo.get("selPage"));
            int selPageCnt  = CommonUtils.getInt(pageInfo.get("selPageCnt"));

            int rowNum = generalDao.selectGernalCount("code.selectCodeTypeList_count", params);
            pageInfo.put("rowNum", rowNum);

            RowBounds rowBounds = new RowBounds((selPage-1)*selPageCnt, selPageCnt);
            params.put("rowBounds", rowBounds);

            rtn.setPageInfo(pageInfo);
        }

        List<Object> rtnList = generalDao.selectGernalList("common.selectImageList", params);
        rtn.setData(rtnList);

        return rtn;
    }


}
