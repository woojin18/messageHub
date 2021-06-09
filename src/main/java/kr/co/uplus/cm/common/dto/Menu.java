package kr.co.uplus.cm.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Menu implements Serializable {

	private static final long serialVersionUID = -8409994022752317349L;
	
	private String roleCd;
	private String menusCd;
	private String menusName;
	private String imgTag;
	private String topMenusCd;
	private String parMenusCd;
	private String menusLevel;
	private String disOrder;
	private String svcTypeCd;
	private String fixedMenusYn;
	private String activityCd;
	private String webUrl;
	private boolean isRead = false;
	private boolean isSave = false;
	private List<Menu> children = new ArrayList<Menu>();

	public void addChild(Menu menu) {
		children.add(menu);
	}
	
}
