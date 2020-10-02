/*
 * Ski Data API for NEU Seattle distributed systems course
 * An API for an emulation of skier managment system for RFID tagged lift tickets. Basis for CS6650 Assignments for 2019
 *
 * OpenAPI spec version: 1.13
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
/**
 * LiftRide
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2020-10-02T15:51:36Z[GMT]")
public class LiftRide {
  @SerializedName("resortID")
  private String resortID = null;

  @SerializedName("dayID")
  private String dayID = null;

  @SerializedName("skierID")
  private String skierID = null;

  @SerializedName("time")
  private String time = null;

  @SerializedName("liftID")
  private String liftID = null;

  public LiftRide resortID(String resortID) {
    this.resortID = resortID;
    return this;
  }

  public LiftRide(String resortID, String dayID, String skierID, String time, String liftID) {
    this.resortID = resortID;
    this.dayID = dayID;
    this.skierID = skierID;
    this.time = time;
    this.liftID = liftID;
  }

  /**
   * Get resortID
   * @return resortID
  **/
  @Schema(example = "Mission Ridge", description = "")
  public String getResortID() {
    return resortID;
  }

  public void setResortID(String resortID) {
    this.resortID = resortID;
  }

  public LiftRide dayID(String dayID) {
    this.dayID = dayID;
    return this;
  }

   /**
   * Get dayID
   * @return dayID
  **/
  @Schema(example = "23", description = "")
  public String getDayID() {
    return dayID;
  }

  public void setDayID(String dayID) {
    this.dayID = dayID;
  }

  public LiftRide skierID(String skierID) {
    this.skierID = skierID;
    return this;
  }

   /**
   * Get skierID
   * @return skierID
  **/
  @Schema(example = "7889", description = "")
  public String getSkierID() {
    return skierID;
  }

  public void setSkierID(String skierID) {
    this.skierID = skierID;
  }

  public LiftRide time(String time) {
    this.time = time;
    return this;
  }

   /**
   * Get time
   * @return time
  **/
  @Schema(example = "217", description = "")
  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public LiftRide liftID(String liftID) {
    this.liftID = liftID;
    return this;
  }

   /**
   * Get liftID
   * @return liftID
  **/
  @Schema(example = "21", description = "")
  public String getLiftID() {
    return liftID;
  }

  public void setLiftID(String liftID) {
    this.liftID = liftID;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LiftRide liftRide = (LiftRide) o;
    return Objects.equals(this.resortID, liftRide.resortID) &&
        Objects.equals(this.dayID, liftRide.dayID) &&
        Objects.equals(this.skierID, liftRide.skierID) &&
        Objects.equals(this.time, liftRide.time) &&
        Objects.equals(this.liftID, liftRide.liftID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resortID, dayID, skierID, time, liftID);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LiftRide {\n");
    
    sb.append("    resortID: ").append(toIndentedString(resortID)).append("\n");
    sb.append("    dayID: ").append(toIndentedString(dayID)).append("\n");
    sb.append("    skierID: ").append(toIndentedString(skierID)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
    sb.append("    liftID: ").append(toIndentedString(liftID)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
