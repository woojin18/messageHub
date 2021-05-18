package kr.co.uplus.cm.sendMessage.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class FbInfo implements Serializable {

    private static final long serialVersionUID = -700546682899809983L;

    //채널
    //필수
    //example: SMS
    @NotBlank(message="대체발송시 채널정보는 필수입니다.")
    private String ch;

    //제목
    //필수
    //example: 1
    private String title;

    //메세지내용
    //필수
    //example: MMS 메시지 내용...
    @NotBlank(message="대체발송시 대체발송메시지 내용은 필수입니다.")
    private String msg;

    //파일 아이디
    private String fileId;

    //상품 코드
    private String productCode;

}
