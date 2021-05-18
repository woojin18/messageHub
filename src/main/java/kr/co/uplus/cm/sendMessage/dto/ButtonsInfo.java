package kr.co.uplus.cm.sendMessage.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class ButtonsInfo implements Serializable {

    private static final long serialVersionUID = 4119763102360686940L;

    //버튼 명
    //example: 1
    @NotBlank(message="버튼 명은 필수입니다.")
    private String name;

    //버튼 타입
    //pattern: ^WL$|^AL$|^BT$|^BK$|^MD$
    //example: WL
    @NotBlank(message="버튼 타입은 필수입니다.")
    @Pattern(regexp="^WL$|^AL$|^BT$|^BK$|^MD$ ", message="버튼 타입 형식에 맞지 않습니다.")
    private String type;

    //PC URL
    //example: http://
    //버튼 타입 WL일 경우 필수
    private String url_pc;

    //MOBILE URL
    //example: http://
    //버튼 타입 WL일 경우 필수
    private String url_mobile;

    //안드로이드 스키마
    //example: schema://an.com
    //버튼 타입 AL일 경우 필수
    private String scheme_android;

    //PC URL
    //example: schema://ios.com
    //버튼 타입 AL일 경우 필수
    private String scheme_ios;

}
