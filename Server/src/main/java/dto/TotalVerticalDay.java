package dto;

public class TotalVerticalDay {
  private String resortID;
  private int totalVert;

  public TotalVerticalDay(String resortID, int totalVert) {
    this.resortID = resortID;
    this.totalVert = totalVert;
  }

  public String getResortID() {
    return resortID;
  }

  public void setResortID(String resortID) {
    this.resortID = resortID;
  }

  public int getTotalVertical() {
    return totalVert;
  }

  public void setTotalVertical(int totalVertical) {
    this.totalVert = totalVertical;
  }
}
