package kr.co.uplus.cloud.sample.consts;

import java.util.HashMap;
import java.util.Map;

public enum UserStatus {
	USE("사용")
	, NOTUSE("중지")
	, LOCK("차단");

	private String value;
	private String desc;
	private UserStatus(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public String getDesc() {
		return desc;
	}

	private static final Map<String, UserStatus> lookup = new HashMap<String, UserStatus>();
	static {
		for (UserStatus e : UserStatus.values()) {
			lookup.put(e.getValue(), e);
		}
	}
	public static UserStatus find(String value) {
		return lookup.get(value);
	}
}