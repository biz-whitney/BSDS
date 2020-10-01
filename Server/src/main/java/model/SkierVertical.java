package model;

public class SkierVertical {
  private String skierId;
  private int totalVertical;

  public SkierVertical(String skierId, int totalVertical) {
    this.skierId = skierId;
    this.totalVertical = totalVertical;
  }

  public String getSkierId() {
    return skierId;
  }

  public void setSkierId(String skierId) {
    this.skierId = skierId;
  }

  public int getTotalVertical() {
    return totalVertical;
  }

  public void setTotalVertical(int totalVertical) {
    this.totalVertical = totalVertical;
  }
}
