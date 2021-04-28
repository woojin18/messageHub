package kr.co.uplus.cm.sendMessage.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class PushRequestData implements Serializable {

    private static final long serialVersionUID = 442095694418981188L;

    //발신번호(대체발송 발신번호)
    // pattern: ^[0-9-]{1,20}$
    // example: 15441234
    @Pattern(regexp="^(\s*|[0-9-]{1,20})$", message="발신번호 형식에 맞지 않습니다.(예:15441234)")
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
    //example: Push 테스트 메시지
    @NotNull(message="메시지 정보는 필수입니다.")
    private PushMsg msg;

    //앱 아이디
    //필수
    //example: appId01
    @NotBlank(message="앱 아이디는 필수입니다.")
    private String appId;

    //부가정보
    //가변데이터
    //ex) {"imageUrl":"http://...", "data1" : "테스트 1", "data2" : "테스트 2", ...}
    private Map<String, String> ext = new HashMap<String, String>();

    //파일 아이디
    private String fileId;

    //웹 요청 아이디(웹에서 요청 시 사용)
    //(웹에서 요청 시 사용)
    private String webReqId;

    //발송정책
    private String serviceCode;

    //(수신자 정보 리스트)
    // API 서버측에 넘겨서 성공/실패를 받고 서버측에서 수신자 정보로 비동기화 처리를 하는 방식이 아니라
    // 넘겨줄때 동기화로 일부 넘겨서 성공/실패를 받고 나머지를 비동기화 처리를 해댤라는 요청으로
    // 수신자 정보 리스트는 유효성체크하지 않는다.
    //필수
    //@NotNull(message="수신자 정보 리스트는 필수입니다.")
    //@Size(min=0, max=10, message="수신자 정보는 최대 10개까지 가능합니다.")
    private List<PushRecvInfo> recvInfoLst;

    //(대체발송리스트 정보)
    private List<PushFbInfo> fbInfoLst;

}

