package kr.co.uplus.cm.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtils {
	private static final String DF_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
	private static final String DF_YMD_FORMAT_STR = "yyyy-MM-dd";
	private static final DateTimeFormatter YMD = DateTimeFormat.forPattern("yyyyMMdd");
	private static final DateTimeFormatter YMDHMS = DateTimeFormat.forPattern("yyyyMMddHHmmss");

	private static final DateTimeFormatter dfFormat = DateTimeFormat.forPattern(DF_FORMAT_STR);
	private static final DateTimeFormatter dfYmdHmFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
	private static final DateTimeFormatter dfYmdFormat = DateTimeFormat.forPattern(DF_YMD_FORMAT_STR);

	public static DateTime str2dateYMD(String str) {
		return str2date(str, YMD);
	}

	public static DateTime str2dateYMDHMS(String str) {
		return str2date(str, YMDHMS);
	}

	public static DateTime str2date(String str, DateTimeFormatter fmt) {
		return DateTime.parse(str, fmt);
	}

	public static String date2strYMD() {
		return dtime2str(DateTime.now(), YMD);
	}

	public static String date2strYMDHMS() {
		return dtime2str(DateTime.now(), YMDHMS);
	}

	public static String date2strYMD(Date date) {
		return dtime2str(new DateTime(date), YMD);
	}

	public static String date2strYMDHMS(Date date) {
		return dtime2str(new DateTime(date), YMDHMS);
	}

	public static String dtime2str(DateTime date, DateTimeFormatter fmt) {
		return date.toString(fmt);
	}

	public static int diffDays(DateTime begin, DateTime end) {
		return Days.daysBetween(begin, end).getDays();
	}

	public static int diffMinutes(DateTime begin, DateTime end) {
		return Minutes.minutesBetween(begin, end).getMinutes();
	}

	public static String dfmtDate2str(Date date) {
		return new DateTime(date).toString(dfFormat);
	}

	public static DateTime date2strYmdhms(String str) {
		return new DateTime(str);
	}

	public static DateTime date2strYmdhmsfmt(String str) {
		return str2date(str, dfFormat);
	}

	public static String dYmdHmfmtDate2str(Date date) {
		return new DateTime(date).toString(dfYmdHmFormat);
	}

	public static String dYmdfmtDate2str(Date date) {
		return new DateTime(date).toString(dfYmdFormat);
	}

	public static DateTime str2dataDYmdfmt(String str) {
		return new DateTime(str);
//		return DateTime.parse(dt.toString(DF_YMD_FORMAT_STR), dfYmdFormat);
	}

	public static String strDateFormatString(String str) {
		if (StringUtils.isEmpty(str))
			return "";

		return dfmtDate2str(org.apache.http.client.utils.DateUtils.parseDate(str));

	}

	/**
	 * 날짜 형식을 받아서 오늘 날짜를 해당 형식으로 반환
	 *
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
}
