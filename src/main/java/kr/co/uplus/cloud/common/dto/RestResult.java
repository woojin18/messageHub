package kr.co.uplus.cloud.common.dto;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import kr.co.uplus.cloud.common.consts.ResultCode;
import kr.co.uplus.cm.dto.PageDto;
import kr.co.uplus.cm.utils.CommonUtils;

public class RestResult<T> implements IResult<ResultCode, T> {
    protected boolean success = true;
    protected ResultCode code;
    protected String message;
    protected T data;
    protected Map<String, Object> pageInfo;
    protected PageDto pageDto = new PageDto();  //TODO : 곧 삭제예정(pageInfo 사용하세요.)

    public RestResult() {
    }
    public RestResult(boolean success) {
        this.success = success;
    }
    public boolean isSuccess() {
        return success;
    }
    public RestResult<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }
    public ResultCode getCode() {
        return code;
    }
    public RestResult<T> setCode(ResultCode code) {
        this.code = code;
        return this;
    }
    public String getMessage() {
        return message;
    }
    public RestResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }
    public T getData() {
        return data;
    }
    public RestResult<T> setData(T data) {
        this.data = data;
        return this;
    }
    public Map<String, Object> getPageInfo() {
        return pageInfo;
    }
    public void setPageInfo(Map<String, Object> pageInfo) {
        this.pageInfo = pageInfo;
    }
    /**
     * 페이징 구성요성 return
     * @param params
     * @return  {
     *      pagingYn-페이징여부
     *      pageNo-페이지번호
     *      listSize-페이지당 리스트노출 수
     *      offset-시작점
     * }
     * @throws Exception
     */
    public Map<String, Object> setPageProps(Map<String, Object> params) throws Exception {
        Map<String, Object> pageInfo = new HashMap<String, Object>();
        Integer pageNo = null;
        Integer listSize = null;

        if(params != null) {
            if(params.containsKey("pageNo")
                    && StringUtils.isNotBlank(CommonUtils.getString(params.get("pageNo")))) {
                pageNo = CommonUtils.getInt(params.get("pageNo"));
            }
            if(params.containsKey("listSize")
                    && StringUtils.isNotBlank(CommonUtils.getString(params.get("listSize")))) {
                listSize = CommonUtils.getInt(params.get("listSize"));
            }
            if(pageNo != null && listSize != null && pageNo > 0 && listSize > 0) {
                pageInfo.put("pageNo", pageNo);
                pageInfo.put("listSize", listSize);
                pageInfo.put("pagingYn", "Y");
                pageInfo.put("offset", (pageNo-1)*listSize);
            }
        }
        this.setPageInfo(pageInfo);
        params.putAll(pageInfo);

        return pageInfo;
    }


    public PageDto getPageDto() {
        return pageDto;
    }
    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
