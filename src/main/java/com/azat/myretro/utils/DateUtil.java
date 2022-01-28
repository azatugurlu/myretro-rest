package com.azat.myretro.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {

	public static Date truncate(Date date) {
		return DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
	}

	public static Date getFirstDayOfMonth(Date date) {
		return DateUtils.setDays(truncate(date), 1);
	}

	public static int compare(Date d1, Date d2) {
		return truncate(d1).compareTo(truncate(d2));
	}

	public static boolean isGreater(Date d1, Date d2) {
		return truncate(d1).compareTo(truncate(d2)) == 1;
	}

	public static boolean isGreaterThanEqual(Date d1, Date d2) {
		return isGreater(d1, d2) || isEqual(d1, d2);
	}

	public static boolean isLess(Date d1, Date d2) {
		return truncate(d1).compareTo(truncate(d2)) == -1;
	}

	public static boolean isLessThanEqual(Date d1, Date d2) {
		return isLess(d1, d2) || isEqual(d1, d2);
	}

	public static boolean isEqual(Date d1, Date d2) {
		return truncate(d1).compareTo(truncate(d2)) == 0;
	}

	public static Date getLastDayOfMonth(Date date) {
		Calendar c = DateUtils.toCalendar(truncate(date));
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return truncate(c.getTime());
	}

	public static Date getOneDayAgo(Date date) {
		Calendar cal = DateUtils.toCalendar(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	public static Date getOneWeekAgo(Date date) {
		Calendar cal = DateUtils.toCalendar(date);
		cal.add(Calendar.DAY_OF_MONTH, -7);
		return cal.getTime();
	}

	public static Date getOneMonthAgo(Date date) {
		Calendar cal = DateUtils.toCalendar(date);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}
}
