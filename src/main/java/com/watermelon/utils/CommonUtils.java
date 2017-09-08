package com.watermelon.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.http.cookie.Cookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CommonUtils
{
  public static String getNextDate(Date date)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(5, 1);
    Date nextDate = calendar.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    return sdf.format(nextDate);
  }
  
  public static String getSessionId(List<Cookie> cookies)
  {
    StringBuffer sb = new StringBuffer();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("ASP.NET_SessionId"))
      {
        sb.append(cookie.getName());
        sb.append("=");
        sb.append(cookie.getValue());
      }
    }
    return sb.toString();
  }
  
  public static String toJson(Object obj)
  {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    return gson.toJson(obj);
  }
  
  public static Map<String, String> beanToMap(Object obj)
  {
    if (obj == null) {
      return null;
    }
    Map<String, String> map = new HashMap();
    try
    {
      BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
      for (PropertyDescriptor property : propertyDescriptors)
      {
        String key = property.getName();
        if (!key.equals("class"))
        {
          Method getter = property.getReadMethod();
          String value = (String)getter.invoke(obj, new Object[0]);
          map.put(key, value);
        }
      }
    }
    catch (Exception e)
    {
      System.out.println("bean转换map失败" + e);
    }
    return map;
  }
  
  public static String[] getSeat(String result)
  {
    Document document = Jsoup.parse(result);
    Element tables = document.getElementById("DataListBookSeat");
    return tables.text().trim().split("\\s");
  }
  
  public static String getRandomSeat(String[] seats)
  {
    Random random = new Random();
    int randomInt = random.nextInt(seats.length);
    return seats[randomInt];
  }
  
  public static boolean isEmpty(Object obj)
  {
	  if(obj==null||"".equals(obj))
	  {
		  return true;
	  }
	  return false;
  }
  
  public static Date getDate(String year,String month,String day,String hour,String minute,String second) throws ParseException
  {
	  StringBuilder sb=new StringBuilder();
	  sb.append(year);
	  sb.append("-");
	  sb.append(month);
	  sb.append("-");
	  sb.append(day);
	  sb.append(" ");
	  sb.append(hour);
	  sb.append(":");
	  sb.append(minute);
	  sb.append(":");
	  sb.append(second);
      String dateStr=sb.toString();
      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return sdf.parse(dateStr);
  }
  
  public static JsonObject jsonToObject(String json)
  {
	  Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	  return gson.fromJson(json, JsonObject.class);
  }
}