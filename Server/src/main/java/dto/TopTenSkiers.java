package dto;

import java.util.List;

public class TopTenSkiers {
  private List<SkierVertical> topTenSkier;

  public TopTenSkiers(List<SkierVertical> topTenSkier) {
    this.topTenSkier = topTenSkier;
  }

  public List<SkierVertical> getTopTenSkier() {
    return topTenSkier;
  }

  public void setTopTenSkier(List<SkierVertical> topTenSkier) {
    this.topTenSkier = topTenSkier;
  }
}
