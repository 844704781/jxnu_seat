
package com.watermelon.pojo;

public class Login
  extends BaseBean
{
  private static final long serialVersionUID = 1L;
  private String subCmd;
  
  public String getSubCmd()
  {
    return this.subCmd;
  }
  
  public void setSubCmd(String subCmd)
  {
    this.subCmd = subCmd;
  }
}