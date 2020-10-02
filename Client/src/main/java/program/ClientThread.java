package program;

import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import io.swagger.client.model.SkierVertical;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;


/**
 * The thread that will execute the POST and GET requests.
 */

public class ClientThread implements Runnable {
  private SkiersApi skiersApi = new SkiersApi();
  private Random rand = new Random();
  private int successRequestCount = 0;
  private int errorRequestCount = 0;
  private int startSkierId;
  private int endSkierId;
  private int startTime;
  private int endTime;
  private int dayId;
  private String resortId;
  private int numLifts;
  private int numGetRequest;
  private CountDownLatch latch;
  private RecordKeeper recordKeeper;


  /**
   * The constructor for the client threads use to send requests to the server
   * @param startSkierId the start skier id
   * @param endSkierId the end skier id
   * @param startTime the phase start time
   * @param endTime the phase end time
   * @param dayId the ski day id
   * @param resortId the resort id
   * @param numLifts num of lifts at the resort
   * @param numGetRequest the number of get requests to perform
   * @param latch the count down latch
   * @param recordKeeper object to keep record of the requests
   */
  public ClientThread(int startSkierId, int endSkierId, int startTime, int endTime,
      int dayId, String resortId, int numLifts, int numGetRequest, CountDownLatch latch,
      RecordKeeper recordKeeper) {
    this.startSkierId = startSkierId;
    this.endSkierId = endSkierId;
    this.startTime = startTime;
    this.endTime = endTime;
    this.dayId = dayId;
    this.resortId = resortId;
    this.numLifts = numLifts;
    this.numGetRequest = numGetRequest;
    this.latch = latch;
    this.recordKeeper = recordKeeper;
  }


  /**
   * When an object implementing interface <code>Runnable</code> is used to create a thread,
   * starting the thread causes the object's
   * <code>run</code> method to be called in that separately executing
   * thread.
   * <p>
   * The general contract of the method <code>run</code> is that it may take any action whatsoever.
   *
   * @see java.lang.Thread #run()
   */
  public void run() {
    List<List<String>> tempCsvData = new ArrayList<>();
    this.runAllPostRequests(tempCsvData);
    this.runAllGetRequests(tempCsvData);
    this.recordKeeper.recordData(successRequestCount, errorRequestCount, tempCsvData);
    this.latch.countDown();
  }

  /**
   * Executes the post requests
   * @param tempCsvData the thread record keeper
   */
  private void runAllPostRequests(List<List<String>> tempCsvData) {
    int numPostRequest = 100;
    for (int i = 0; i < numPostRequest; i++) {
      long postStartTime = System.currentTimeMillis();
      int responseCode = postLiftRide();
      long postEndTime = System.currentTimeMillis();
      long latency = postEndTime - postStartTime;
      if (responseCode == 200 || responseCode == 201) {
        successRequestCount += 1;
      } else {
        errorRequestCount += 1;
      }
      tempCsvData.add(Arrays.asList(Long.toString(postStartTime), "POST", Long.toString(latency),
          Integer.toString(responseCode)));
    }
  }

  /**
   * Executes the get requests
   * @param tempCsvData the thread record keeper
   */
  private void runAllGetRequests(List<List<String>> tempCsvData) {
    for (int i = 0; i < numGetRequest; i++) {
      String skierId =
          Integer.toString(rand.ints(startSkierId, endSkierId + 1)
              .findFirst().getAsInt());
      long getStartTime = System.currentTimeMillis();
      int responseCode = this.getSkierDayVertical(skierId);
      long getEndTime = System.currentTimeMillis();
      long latency = getEndTime - getStartTime;
      if (responseCode == 200 || responseCode == 201) {
        successRequestCount += 1;
      } else {
        errorRequestCount += 1;
      }
      tempCsvData.add(Arrays.asList(Long.toString(getStartTime), "GET", Long.toString(latency),
          Integer.toString(responseCode)));
    }
  }


  /**
   * Sends a post request to the server to store a new liftride
   * @return the response code for the request
   */
  private int postLiftRide() {
    int responseCode = 400;
    ApiResponse<Void> response = null;
    int minNumOfLifts = 5;
    int skierId = rand.ints(startSkierId, endSkierId + 1).findFirst().getAsInt();
    int time = rand.ints(startTime, endTime + 1).findFirst().getAsInt();
    int lift = rand.ints(minNumOfLifts, numLifts + 1).findFirst().getAsInt();
    LiftRide liftRide = new LiftRide(resortId, Integer.toString(dayId),
        Integer.toString(lift), Integer.toString(skierId), Integer.toString(time));
    try {
      response  = skiersApi.writeNewLiftRideWithHttpInfo(liftRide);
      responseCode = response.getStatusCode();
    } catch (ApiException e) {
      responseCode = e.getCode();
      e.printStackTrace();
    }
    return responseCode;
  }


  /**
   * Get request for the vertical of a for a skier on a specific day
   * @param skierId the skier id
   * @return the response code for the request
   */
  private int getSkierDayVertical(String skierId) {
    int responseCode = 400;
    ApiResponse<SkierVertical> response = null;
    try {
      response = skiersApi.getSkierDayVerticalWithHttpInfo(resortId, Integer.toString(dayId), skierId);
      responseCode = response.getStatusCode();
    } catch (ApiException e) {
      responseCode = e.getCode();
      e.printStackTrace();
    }
    return responseCode;
  }
}
