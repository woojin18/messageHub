package kr.co.uplus.cm.common.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.uplus.cm.common.utils.SpringUtils;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class MultipartFileDTO implements Serializable {

    private static final long serialVersionUID = 2200868390496389698L;

    private MultipartFile file;

    private String paramString;

    @SuppressWarnings({ "unchecked", "unused" })
    public Map<String, Object> getParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        HttpServletRequest request = SpringUtils.getCurrentRequest();
        ObjectMapper mapper = new ObjectMapper();
        try {
            params = mapper.readValue(this.paramString, Map.class);
        } catch (Exception e) {
            log.error("{} getParams : {}", this.getClass(), e);
        }

        return params;
    }

}
