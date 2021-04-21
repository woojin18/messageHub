package kr.co.uplus.cm.sendMessage.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PushRequestDto implements Serializable {

    private static final long serialVersionUID = 442095694418981188L;

    //발신번호(대체발송 발신번호)
    //필수(FIXME : 대체발송이 아닐수도 있는데 대체발송 발신번호가 필수다...)
    // pattern: ^[0-9-]{1,20}$
    // example: 15441234
    @NotNull(message="발신번호는 필수입니다.")
    @NotBlank(message="발신번호는 필수입니다.")
    @Pattern(regexp="^[0-9-]{1,20}$", message="발신번호 형식에 맞지 않습니다.(예:15441234)")
    private String callback;

    //캠페인 ID(태그)
    //pattern: ^[a-zA-Z0-9-_]{0,20}$
    @Pattern(regexp="^[0-9-]{1,20}$", message="태그(캠페인 ID) 형식에 맞지 않습니다.")
    private String campaignId;

    //부서 코드(사용안함)
    //pattern: ^[a-zA-Z0-9-_]{0,20}$
    //private String deptCode;

    //메시지 내용
    //필수
    //example: Push 테스트 메시지
    @NotNull(message="메시지 내용은 필수입니다.")
    @NotBlank(message="메시지 내용은 필수입니다.")
    private String msg;

    //이미지URL
    //maxLength: 200
    //minLength: 0
    //example: http://
    @Size(min=0, max=200, message="이미지URL 사이즈는 0~200자입니다.")
    private String imageUrl;

    //파일 아이디
    //(추후 생길 수 있다. G/W에 올려져있는 File ID, push 대체발송 MMS 항목이 있다면 필요하다.)
    //private String fileId;

    //앱 아이디
    //필수
    @NotNull(message="앱 아이디는 필수입니다.")
    @NotBlank(message="앱 아이디는 필수입니다.")
    private String appId;

    //(수신자 정보 리스트)
    //필수
    //FIXME 전체발송같은 경우도 있는데 한번에 보낼 수 있는 푸시가 최대 10개??
    @NotNull(message="수신자 정보 리스트는 필수입니다.")
    @Size(min=0, max=10, message="수신자 정보는 최대 10개까지 가능합니다.")
    private List<PushRecvInfo> recvInfoLst;

    //(대체발송리스트 정보)
    private List<PushFbInfo> fbInfoLst;

}

