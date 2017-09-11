package com.watermelon.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class TestUtils {
	public static void main(String[] args) {

		Long a = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		Long b = System.currentTimeMillis();
		String str = CommonUtils.formatDateToString(date);
		System.out.println(str);
		Long c = System.currentTimeMillis();
		LocalDateTime now = LocalDateTime.now();
		Long d = System.currentTimeMillis();
		System.out.println(now);
		Long e = System.currentTimeMillis();
		Date date1 = new Date();
		Long f = System.currentTimeMillis();
		String st1r = CommonUtils.formatDateToString(date1);
		System.out.println(st1r);

		System.out.println(b - a);
		System.out.println(d - c);
		
		System.out.println(f-e);

	}
}
