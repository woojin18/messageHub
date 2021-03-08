package kr.co.uplus.cloud.sample.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class RegexUtils {
	
	/**
	 * 전화번호 정규식
	 * 이통사: 010, 011, 017, 018, 019
	 * 개인번호: 050
	 * 지역번호: 02, 031, 032, 033, 041, 042, 043, 044, 051, 052, 053, 054, 055, 061, 062, 063, 064
	 * 인터넷: 070, 080
	 */
	private final static String tel_regex = "^((0(1(0|1|6|7|8|9)))|(0(2|3(1|2|3)|4(1|2|3|4)|5(0|1|2|3|4|5)|6(1|2|3|4)|70|80))).*$";
	public static String formatTelStr(String str) {
		if (str == null) {
			return null;
		}
		if (str.matches(tel_regex)) {
			if (str.startsWith("02")) {
				return str.replaceFirst("(\\d{2})([0-9*]+)(\\d{4})", "$1-$2-$3");
			} else {
				return str.replaceFirst("(\\d{3})([0-9*]+)(\\d{4})", "$1-$2-$3");
			}
		}
		if (str.length() == 8) {
			// 그 외 8자리 15447788등
			return str.replaceFirst("(\\d{4})(\\d{4})", "$1-$2");
		}
		// 나머지 무시
		return str;
	}

	public static void main(String[] args) {
		String tels[] = {
			"023456677"
			, "0234316125"
			, "0523376868"
			, "05233776868"
			, "01033445566"
			, "0178532656"
			, "15447788"
		};
		for (int t=0; t<tels.length; t++) {
			System.out.println(formatTelStr(tels[t]));
		}
	}
	
	/**
	 * url http or https
	 */
	public static boolean validHttp(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		return str.startsWith("http://") || str.startsWith("https://");
	}
	
	/**
	 * 날짜 정규식
	 */
	private static Pattern DATE_PATTERN = Pattern.compile(
		      "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
		      + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
		      + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
		      + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");
	public static boolean validDate(String str) {
		return DATE_PATTERN.matcher(str).matches();
	}
	
	/**
	 * 숫자
	 */
	public static boolean validOnlyNum(String str) {
		return str.matches("\\d+");
	}
	
	private static Pattern VALI_SPLIT = Pattern.compile("\\{{2}(.*?)\\}{2}", Pattern.DOTALL);
//	private static Pattern VALI_SPLIT = Pattern.compile("\\{{2}(.*?)\\}{2}");
	
	public static boolean cellValiableCheck(String valiableText) {
		boolean rtn = true;
		Matcher matcher = VALI_SPLIT.matcher(valiableText);
		
		while (matcher.find()) {
			if ("".equals(matcher.group(1)) || !Pattern.matches("^[가-힣ㄱ-ㅎㅏ-ㅣA-Za-z0-9_]*$", matcher.group(1))) {
				return false;
			}
			
			if (matcher.group(1) == null) break;
		}
		
		return rtn;
	}
	
	public static List<String> getParamListByRegex(String str) {
		List<String> rtnParam = new ArrayList<>();
		Matcher matcher = VALI_SPLIT.matcher(str);
		
		while (matcher.find()) {
			if (!"".equals(matcher.group(1)) && Pattern.matches("^[가-힣ㄱ-ㅎㅏ-ㅣA-Za-z0-9_]*$", matcher.group(1))) {
				rtnParam.add(matcher.group(1));
			}
			
			if (matcher.group(1) == null) break;
		}
		
		return rtnParam;
		
	}
}
