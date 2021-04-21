package kr.co.uplus.cm.sendMessage.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PushFbInfo implements Serializable {

    private static final long serialVersionUID = -700546682899809983L;

    //채널
    //필수
    //example: MMS
    @NotNull(message="대체발송시 채널정보는 필수입니다.")
    @NotBlank(message="대체발송시 채널정보는 필수입니다.")
    private String ch;

    //제목
    //필수
    //example: 1
    @NotNull(message="대체발송시 대체발송메시지 제목은 필수입니다.")
    @NotBlank(message="대체발송시 대체발송메시지 제목은 필수입니다.")
    private String title;

    //메세지내용
    //필수
    //example: MMS 메시지 내용...
    @NotNull(message="대체발송시 대체발송메시지 내용은 필수입니다.")
    @NotBlank(message="대체발송시 대체발송메시지 내용은 필수입니다.")
    private String msg;



}
