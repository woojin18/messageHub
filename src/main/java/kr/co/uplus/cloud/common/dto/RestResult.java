package kr.co.uplus.cloud.common.dto;

import java.util.Map;

import kr.co.uplus.cloud.sample.consts.ResultCode;

public class RestResult<T> implements IResult<ResultCode, T> {
    protected boolean success = true;
    protected ResultCode code;
    protected String message;
    protected T data;
    protected Map<String, Object> pageInfo;

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

}
