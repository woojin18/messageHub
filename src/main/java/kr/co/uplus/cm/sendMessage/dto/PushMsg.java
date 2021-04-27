package kr.co.uplus.cm.sendMessage.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PushMsg implements Serializable {

    private static final long serialVersionUID = -3306498316147120946L;

    //제목
    //example: title01
    private String title;

    //내용
    //example: body01
    private String body;

}
