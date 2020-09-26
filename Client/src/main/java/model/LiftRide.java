package model;

public class LiftRide {
  private String resortId;
  private String dayId;
  private String skierId;
  private String liftId;
  private String time;

  public LiftRide(String resortId, String dayId, String skierId, String liftId, String time) {
    this.resortId = resortId;
    this.dayId = dayId;
    this.skierId = skierId;
    this.liftId = liftId;
    this.time = time;
  }

  public String getResortId() {
    return resortId;
  }

  public void setResortId(String resortId) {
    this.resortId = resortId;
  }

  public String getDayId() {
    return dayId;
  }

  public void setDayId(String dayId) {
    this.dayId = dayId;
  }

  public String getSkierId() {
    return skierId;
  }

  public void setSkierId(String skierId) {
    this.skierId = skierId;
  }

  public String getLiftId() {
    return liftId;
  }

  public void setLiftId(String liftId) {
    this.liftId = liftId;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return "{" +
        "resortId: " + resortId  +
        ", dayId: " + dayId +
        ", skierId: " + skierId  +
        ", liftId: " + liftId  +
        ", time: " + time  +
        '}';
  }
}
