package kr.co.uplus.cloud.common.dto;

public interface IResult<S, T> {
	public boolean isSuccess() ;
	public S getCode();
	public String getMessage();
	public T getData();
}
