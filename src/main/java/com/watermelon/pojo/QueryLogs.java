package com.watermelon.pojo;

public class QueryLogs
  extends BaseBean
{
  private static final long serialVersionUID = 1L;
  private String subCmd;
  private String subBookNo;
  private String chooseDate;
  private String ddlDate;
  private String ddlRoom;
  
  public String getSubCmd()
  {
    return this.subCmd;
  }
  
  public void setSubCmd(String subCmd)
  {
    this.subCmd = subCmd;
  }
  
  public String getSubBookNo()
  {
    return this.subBookNo;
  }
  
  public void setSubBookNo(String subBookNo)
  {
    this.subBookNo = subBookNo;
  }
  
  public String getChooseDate()
  {
    return this.chooseDate;
  }
  
  public void setChooseDate(String chooseDate)
  {
    this.chooseDate = chooseDate;
  }
  
  public String getDdlDate()
  {
    return this.ddlDate;
  }
  
  public void setDdlDate(String ddlDate)
  {
    this.ddlDate = ddlDate;
  }
  
  public String getDdlRoom()
  {
    return this.ddlRoom;
  }
  
  public void setDdlRoom(String ddlRoom)
  {
    this.ddlRoom = ddlRoom;
  }
}