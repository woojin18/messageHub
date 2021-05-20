package kr.co.uplus.cm.sendMessage.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class FrndTalkRequestData implements Serializable {

    private static final long serialVersionUID = 6390575618917564778L;

    //발신번호(대체발송 발신번호)
    //필수
    // pattern: ^[0-9-]{1,20}$
    // example: 15441234
    @NotBlank(message="발신번호는 필수입니다.")
    @Pattern(regexp="^([0-9-]{1,20})$", message="발신번호 형식에 맞지 않습니다.(예:15441234)")
    private String callback;

    //캠페인 ID(태그)
    //pattern: ^[a-zA-Z0-9-_]{0,20}$
    @Pattern(regexp="^[a-zA-Z0-9-_]{0,20}$", message="태그(캠페인 ID) 형식에 맞지 않습니다.")
    private String campaignId;

    //부서 코드(사용안함)
    //pattern: ^[a-zA-Z0-9-_]{0,20}$
    //private String deptCode;

    //와이드 이미지 여부
    //pattern: ^Y$|^N$
    //example: N
    @Pattern(regexp="^Y$|^N$", message="와이드 이미지 여부 형식에 맞지 않습니다.(예:N)")
    private String wideImageYn;

    //파일 아이디
    private String fileId;

    //광고 표기 여부
    //pattern: ^Y$|^N$
    //example: N
    @NotBlank(message="광고 표기 여부는 필수입니다.")
    @Pattern(regexp="^Y$|^N$", message="광고 표기 여부 형식에 맞지 않습니다.(예:N)")
    private String adFlag;

    //메시지
    //필수
    //example: string
    @NotBlank(message="메시지 정보는 필수입니다.")
    private String msg;

    //이미지정보
    private ImageInfo image = new ImageInfo();

    //카카오톡 발신 프로필키
    //maxLength: 200
    //example: N
    @NotBlank(message="카카오톡 발신 프로필키는 필수입니다.")
    @Length(max=200, message = "유효하지 않은 카카오톡 발신 프로필키입니다.(카카오톡 발신 프로필키 최대 200자)")
    private String senderKey;

    //웹 요청 아이디(CM_WEB_MSG.WEB_REQ_ID 로 하며 테스트발송시 Null로 한다.)
    @Length(max=20, message = "유효하지 않은 웹 요청 아이디입니다.(웹 요청 아이디 최대 20자)")
    private String webReqId;

    //버튼 리스트
    @NotNull(message="버튼 리스트는 필수입니다.")
    private List<ButtonsInfo> buttons = new ArrayList<ButtonsInfo>();

    //(수신자 정보 리스트)
    // API 서버측에 넘겨서 수신성공/수신실패를 받고 데이터 처리하는 방식이 아니라
    // 넘겨줄때 데이터를 쪼개서 넘겨달라는 요청으로 수신자 정보 리스트는 전체 유효성체크하지 않는다.
    //필수
    //@NotNull(message="수신자 정보 리스트는 필수입니다.")
    //@Size(min=0, max=10, message="수신자 정보는 최대 10개까지 가능합니다.")
    private List<RecvInfo> recvInfoLst;

    //대체발송리스트 정보
    private List<FbInfo> fbInfoLst;

}










