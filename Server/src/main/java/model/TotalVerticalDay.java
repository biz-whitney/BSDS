package model;

public class TotalVerticalDay {
  private String resortId;
  private int totalVertical;

  public TotalVerticalDay(String resortId, int totalVertical) {
    this.resortId = resortId;
    this.totalVertical = totalVertical;
  }

  public String getResortId() {
    return resortId;
  }

  public void setResortId(String resortId) {
    this.resortId = resortId;
  }

  public int getTotalVertical() {
    return totalVertical;
  }

  public void setTotalVertical(int totalVertical) {
    this.totalVertical = totalVertical;
  }
}
