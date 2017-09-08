package com.watermelon.pojo;

public class Seat
  extends BaseBean
{
  private static final long serialVersionUID = 1L;
  private String hidBookDate;
  private String hidRrId;
  private String subCmd;
  private String txtBookDate;
  private String selReadingRoom;
  private String roomName;
  
  public String getRoomName()
  {
    return this.roomName;
  }
  
  public void setRoomName(String roomName)
  {
    this.roomName = roomName;
  }
  
  public String getHidBookDate()
  {
    return this.hidBookDate;
  }
  
  public void setHidBookDate(String hidBookDate)
  {
    this.hidBookDate = hidBookDate;
  }
  
  public String getHidRrId()
  {
    return this.hidRrId;
  }
  
  public void setHidRrId(String hidRrId)
  {
    this.hidRrId = hidRrId;
  }
  
  public String getSubCmd()
  {
    return this.subCmd;
  }
  
  public void setSubCmd(String subCmd)
  {
    this.subCmd = subCmd;
  }
  
  public String getTxtBookDate()
  {
    return this.txtBookDate;
  }
  
  public void setTxtBookDate(String txtBookDate)
  {
    this.txtBookDate = txtBookDate;
  }
  
  public String getSelReadingRoom()
  {
    return this.selReadingRoom;
  }
  
  public void setSelReadingRoom(String selReadingRoom)
  {
    this.selReadingRoom = selReadingRoom;
  }
}