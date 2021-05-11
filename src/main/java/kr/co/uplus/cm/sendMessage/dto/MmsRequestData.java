package kr.co.uplus.cm.sendMessage.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class MmsRequestData implements Serializable {

    private static final long serialVersionUID = 3946448516794444460L;

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

    //메시지
    //필수
    //example: MMS 테스트 제목
    @NotBlank(message="메시지 제목은 필수입니다.")
    private String title;

    //메시지
    //필수
    //example: MMS 테스트 메시지
    @NotBlank(message="메시지 정보는 필수입니다.")
    private String msg;

    //File IDs
    private List<String> fileIdLst = new ArrayList<String>();

    //웹 요청 아이디(CM_WEB_MSG.WEB_REQ_ID 로 하며 테스트발송시 Null로 한다.)
    private String webReqId;

    //(수신자 정보 리스트)
    // API 서버측에 넘겨서 수신성공/수신실패를 받고 데이터 처리하는 방식이 아니라
    // 넘겨줄때 데이터를 쪼개서 넘겨달라는 요청으로 수신자 정보 리스트는 전체 유효성체크하지 않는다.
    //필수
    //@NotNull(message="수신자 정보 리스트는 필수입니다.")
    //@Size(min=0, max=10, message="수신자 정보는 최대 10개까지 가능합니다.")
    private List<RecvInfo> recvInfoLst;

    //(대체발송리스트 정보)
    //미사용
    //private List<FbInfo> fbInfoLst;


}



