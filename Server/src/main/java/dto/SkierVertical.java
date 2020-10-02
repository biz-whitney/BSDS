package dto;

public class SkierVertical {
  private String skierID;
  private int totalVert;

  public SkierVertical(String skierID, int totalVert) {
    this.skierID = skierID;
    this.totalVert = totalVert;
  }

  public String getSkierID() {
    return skierID;
  }

  public void setSkierID(String skierID) {
    this.skierID = skierID;
  }

  public int getTotalVert() {
    return totalVert;
  }

  public void setTotalVert(int totalVert) {
    this.totalVert = totalVert;
  }
}
