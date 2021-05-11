package kr.co.uplus.cm.sendMessage.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class RecvInfo implements Serializable {

    private static final long serialVersionUID = -1289372282301427871L;

    //클라이언트키(client쪽에서는 필요 없으나 G/W에서 필수라 임의값을 넣어주자(idx))
    //필수
    //pattern: ^[0-9-_]{1,20}$
    //example: 1
    //@NotBlank(message="클라이언트키는 필수입니다.")
    //@Pattern(regexp="^[0-9-]{1,20}$", message="클라이언트키 형식에 맞지 않습니다.")
    private String cliKey;

    //수신번호
    //pattern: ^[0-9-]{1,20}$
    //example: 01012341234
    //@Pattern(regexp="^[0-9-]{1,20}$", message="수신번호 형식에 맞지 않습니다.(예:01012341234)")
    private String phone;

    //cuid(APP 로그인ID)
    //필수
    //@NotBlank(message="APP 로그인ID는 필수입니다.")
    private String cuid;

    //변수 매핑
    //가변데이터
    //example: {"additionalProp1": "string","additionalProp2": "string","additionalProp3": "string"}
    private Map<String, String> mergeData = new HashMap<String, String>();

}
