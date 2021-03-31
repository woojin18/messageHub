package kr.co.uplus.cloud.common.consts;

import java.util.HashMap;
import java.util.Map;

public enum Role {
	ADMIN("ADMIN"), USER("USER");

	private String value;
	private String desc;

	private Role(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	private static final Map<String, Role> lookup = new HashMap<String, Role>();
	static {
		for (Role e : Role.values()) {
			lookup.put(e.getValue(), e);
		}
	}

	public static Role find(String value) {
		return lookup.get(value);
	}
}
