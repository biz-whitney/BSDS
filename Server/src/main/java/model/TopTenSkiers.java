package model;

import java.util.List;

public class TopTenSkiers {
  private List<SkierVertical> skiers;

  public TopTenSkiers(List<SkierVertical> skiers) {
    this.skiers = skiers;
  }

  public List<SkierVertical> getSkiers() {
    return skiers;
  }

  public void setSkiers(List<SkierVertical> skiers) {
    this.skiers = skiers;
  }
}
