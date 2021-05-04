package kr.co.uplus.cm.common.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import kr.co.uplus.cm.common.consts.Const;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix="file-props")
public class FileProperties implements Serializable {

    private static final long serialVersionUID = -8862341986732227634L;

    private ImgFileProperties img = new ImgFileProperties();
    private ExcelFileProperties excel = new ExcelFileProperties();

    @Data
    @Component
    @ConfigurationProperties(prefix="file-props.excel")
    public static class ExcelFileProperties {
        private String permitExten;
    }

    @Data
    @Component
    @ConfigurationProperties(prefix="file-props.img")
    public static class ImgFileProperties {
        private String uploadPath;
        private String permitExten;
        private long limitSize;
        private List<Object> imgPropList = new ArrayList<Object>();

        @SuppressWarnings("unchecked")
        public long getChLimitSize(String ch) {
            Long limitSize = NumberUtils.LONG_ZERO;
            HashMap<String, Object> map = new HashMap<>();
            for(Object obj : this.imgPropList) {
                map = (HashMap<String, Object>) obj;
                if(ch.equalsIgnoreCase((String) map.get("ch"))) {
                    limitSize = Long.parseLong((String) map.get("limitSize"));
                    break;
                }
            }
            return limitSize;
        }

        @SuppressWarnings("unchecked")
        public Map<String, Integer> getChResize(String ch) {
            Map<String, Integer> resizeMap = new HashMap<String, Integer>();
            HashMap<String, Object> map = new HashMap<>();
            for(Object obj : this.imgPropList) {
                map = (HashMap<String, Object>) obj;
                if(ch.equalsIgnoreCase((String) map.get("ch"))) {
                    resizeMap.put(Const.IMG_RESIZE_WIDTH, Integer.parseInt((String) map.get("resizeWidth")));
                    resizeMap.put(Const.IMG_RESIZE_HEIGHT, Integer.parseInt((String) map.get("resizeHeight")));
                    break;
                }
            }
            return resizeMap;
        }
    }

}
