package com.watermelon.pojo;

import java.io.Serializable;

public class BaseBean
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String __VIEWSTATE;
  private String __VIEWSTATEGENERATOR;
  private String __EVENTVALIDATION;
  
  public String get__VIEWSTATE()
  {
    return this.__VIEWSTATE;
  }
  
  public void set__VIEWSTATE(String __VIEWSTATE)
  {
    this.__VIEWSTATE = __VIEWSTATE;
  }
  
  public String get__VIEWSTATEGENERATOR()
  {
    return this.__VIEWSTATEGENERATOR;
  }
  
  public void set__VIEWSTATEGENERATOR(String __VIEWSTATEGENERATOR)
  {
    this.__VIEWSTATEGENERATOR = __VIEWSTATEGENERATOR;
  }
  
  public String get__EVENTVALIDATION()
  {
    return this.__EVENTVALIDATION;
  }
  
  public void set__EVENTVALIDATION(String __EVENTVALIDATION)
  {
    this.__EVENTVALIDATION = __EVENTVALIDATION;
  }
}
