package kr.co.uplus.cloud.sample.dto;

public interface IResult<S, T> {
	public boolean isSuccess() ;
	public S getCode();
	public String getMessage();
	public T getData();
}
