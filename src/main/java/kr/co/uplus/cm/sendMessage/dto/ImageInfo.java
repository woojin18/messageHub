package kr.co.uplus.cm.sendMessage.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class ImageInfo implements Serializable {

    private static final long serialVersionUID = -3124231305040278356L;

    //이미지 URL
    //pattern: ^[a-zA-Z0-9-_:/]{1,300}$
    //example: http://
    @NotBlank(message="이미지 정보 입력 시 이미지 URL은 필수입니다.")
    @Pattern(regexp="^[a-zA-Z0-9-_:/]{1,300}$", message="이미지 URL - URL 형식에 맞지 않습니다.")
    private String imageUrl;

    //이미지 링크
    //pattern: ^[a-zA-Z0-9-_:/]{1,300}$
    //example: http://
    @NotBlank(message="이미지 정보 입력 시 이미지 링크는 필수입니다.")
    @Pattern(regexp="^[a-zA-Z0-9-_:/]{1,300}$", message="이미지 링크 - URL 형식에 맞지 않습니다.")
    private String imageLink;

}
