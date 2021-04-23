package kr.co.uplus.cm.common.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
        private ImgLimitSize limitSize = new ImgLimitSize();
        private ImgResize resize = new ImgResize();

        @Data
        @Component
        @ConfigurationProperties(prefix="file-props.img.limit-size")
        public static class ImgLimitSize {
            private long defaultSize;
            private long mms;
            private long push;
            private long friendtalkNormal;
            private long friendtalkWide;
            private long rcs;
        }

        @Data
        @Component
        @ConfigurationProperties(prefix="file-props.img.resize")
        public static class ImgResize {
            private int pushWidth;
            private int pushHeight;
            private int rcsWidth;
            private int rcsHeight;
            private int friendtalkNomalWidth;
            private int friendtalkNomalHeight;
            private int friendtalkWideWidth;
            private int friendtalkWideHeight;
            private int mmsWidth;
            private int mmsHeight;
        }

        public long getChLimitSize(String ch) {
            if("MMS".equalsIgnoreCase(ch)) {
                return this.limitSize.mms;
            } else if("PUSH".equalsIgnoreCase(ch)) {
                return this.limitSize.push;
            } else if("FRIENDTALK".equalsIgnoreCase(ch)) {
                return this.limitSize.friendtalkNormal;
            } else if("FRIENDTALK_WIDE".equalsIgnoreCase(ch)) {
                return this.limitSize.friendtalkWide;
            } else if("RCS".equalsIgnoreCase(ch)) {
                return this.limitSize.rcs;
            } else {
                return this.limitSize.defaultSize;
            }
        }

        public Map<String, Integer> getChResize(String ch) {

            Map<String, Integer> resizeMap = new HashMap<String, Integer>();

            if("MMS".equalsIgnoreCase(ch)) {
                resizeMap.put(Const.IMG_RESIZE_WIDTH, this.resize.mmsWidth);
                resizeMap.put(Const.IMG_RESIZE_HEIGHT, this.resize.mmsHeight);
            } else if("PUSH".equalsIgnoreCase(ch)) {
                resizeMap.put(Const.IMG_RESIZE_WIDTH, this.resize.pushWidth);
                resizeMap.put(Const.IMG_RESIZE_HEIGHT, this.resize.pushHeight);
            } else if("FRIENDTALK".equalsIgnoreCase(ch)) {
                resizeMap.put(Const.IMG_RESIZE_WIDTH, this.resize.friendtalkNomalWidth);
                resizeMap.put(Const.IMG_RESIZE_HEIGHT, this.resize.friendtalkNomalHeight);
            } else if("FRIENDTALK_WIDE".equalsIgnoreCase(ch)) {
                resizeMap.put(Const.IMG_RESIZE_WIDTH, this.resize.friendtalkWideWidth);
                resizeMap.put(Const.IMG_RESIZE_HEIGHT, this.resize.friendtalkWideHeight);
            } else if("RCS".equalsIgnoreCase(ch)) {
                resizeMap.put(Const.IMG_RESIZE_WIDTH, this.resize.rcsWidth);
                resizeMap.put(Const.IMG_RESIZE_HEIGHT, this.resize.rcsHeight);
            }

            return resizeMap;
        }

    }

}
