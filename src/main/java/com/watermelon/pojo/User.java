package com.watermelon.pojo;

import java.io.Serializable;

public class User
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String txt_LoginID;
  private String txt_Password;
  private String email;
  private String nickName;
  
  public String getTxt_LoginID()
  {
    return this.txt_LoginID;
  }
  
  public void setTxt_LoginID(String txt_LoginID)
  {
    this.txt_LoginID = txt_LoginID;
  }
  
  public String getTxt_Password()
  {
    return this.txt_Password;
  }
  
  public void setTxt_Password(String txt_Password)
  {
    this.txt_Password = txt_Password;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public String getNickName()
  {
    return this.nickName;
  }
  
  public void setNickName(String nickName)
  {
    this.nickName = nickName;
  }
}
