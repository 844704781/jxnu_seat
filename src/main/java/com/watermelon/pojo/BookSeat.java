package com.watermelon.pojo;

public class BookSeat
  extends BaseBean
{
  private static final long serialVersionUID = 1L;
  private String subCmd;
  private String spanSelect;
  private String seatShortNo;
  private String roomNo;
  private String seatNo;
  private String date;
  private String timeSpan;
  
  public String getSeatShortNo()
  {
    return this.seatShortNo;
  }
  
  public void setSeatShortNo(String seatShortNo)
  {
    this.seatShortNo = seatShortNo;
  }
  
  public String getRoomNo()
  {
    return this.roomNo;
  }
  
  public void setRoomNo(String roomNo)
  {
    this.roomNo = roomNo;
  }
  
  public String getSeatNo()
  {
    return getRoomNo() + getSeatShortNo();
  }
  
  public void setSeatNo(String seatNo)
  {
    this.seatNo = seatNo;
  }
  
  public String getDate()
  {
    return this.date;
  }
  
  public void setDate(String date)
  {
    this.date = date;
  }
  
  public String getTimeSpan()
  {
    return this.timeSpan;
  }
  
  public void setTimeSpan(String timeSpan)
  {
    this.timeSpan = timeSpan;
  }
  
  public String getSubCmd()
  {
    return this.subCmd;
  }
  
  public void setSubCmd(String subCmd)
  {
    this.subCmd = subCmd;
  }
  
  public String getSpanSelect()
  {
    return this.spanSelect;
  }
  
  public void setSpanSelect(String spanSelect)
  {
    this.spanSelect = spanSelect;
  }
}