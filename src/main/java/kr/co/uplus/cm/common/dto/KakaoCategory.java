package kr.co.uplus.cm.common.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;

@Getter
public class KakaoCategory {

	private String code;
	private String name;
	private List childs = new ArrayList();
	
	public KakaoCategory(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public void addChild(KakaoCategory child) {
		this.childs.add(child);
	}
}
