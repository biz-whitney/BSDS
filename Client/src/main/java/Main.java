import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import model.RecordKeeper;


/**
 * The main entry point to running the client.
 */
public class Main {
  private static final int PHASE_ONE_START = 1;
  private static final int PHASE_ONE_END = 90;
  private static final int PHASE_TWO_START = 91;
  private static final int PHASE_TWO_END = 360;
  private static final int PHASE_THREE_START = 361;
  private static final int PHASE_THREE_END = 420;
  private RecordKeeper recordKeeper = new RecordKeeper();
  @Parameter(names={"--threads", "-t"}, description = "max number of threads to run (max 256)")
  public int maxThreads = 256;
  @Parameter(names = {"--skiers", "-s"}, description = "number of skiers to generate lift rides "
      + "for")
  public int numSkiers = 50000;
  @Parameter(names = {"--lifts", "-l"}, description = "number of ski lifts (range 5 - 60")
  public int numLifts = 40;
  @Parameter(names = {"--day", "--d"}, description = "the ski day number")
  public int dayId = 1;
  @Parameter(names = {"--resort", "-r"}, description = "the resort name which is the resortId")
  public String resortId = "SilverMt";
  @Parameter (names = {"--port", "-p"}, description = "IP/port address of the server")
  public int port = 8080;


  /**
   * Phase one part of a ski day
   * @throws InterruptedException thrown if any issues are encounter while spawning the threads
   */
  public void phaseOne() throws InterruptedException {
    int numGetRequest = 5;
    int numOfThreads = this.maxThreads / 4;
    int tenPercent = (int) Math.ceil(numOfThreads * 0.1);
    CountDownLatch  countDownLatch = new CountDownLatch(tenPercent);
    Thread[] t = this.spawnThreads(numOfThreads, PHASE_ONE_START, PHASE_ONE_END, numGetRequest,
        countDownLatch);
    countDownLatch.await();
    this.phaseTwo();
  }

  /**
   * Phase two part of a ski day
   * @throws InterruptedException thrown if any issues are encounter while spawning the threads
   */
  public void phaseTwo() throws InterruptedException {
    int numGetRequest = 5;
    int tenPercent = (int) Math.ceil(this.maxThreads * 0.1);
    CountDownLatch  countDownLatch = new CountDownLatch(tenPercent);
    Thread[] t = spawnThreads(this.maxThreads, PHASE_TWO_START, PHASE_TWO_END, numGetRequest,
        countDownLatch);
    countDownLatch.await();
    this.phaseThree();
  }

  /**
   * Phase three part of a ski day
   * @throws InterruptedException thrown if any issues are encounter while spawning the threads
   */
  public void phaseThree() throws InterruptedException {
    int numGetRequest = 10;
    int numOfThreads = this.maxThreads / 4;
    CountDownLatch countDownLatch = new CountDownLatch(numOfThreads);
    Thread[] t = spawnThreads(numOfThreads, PHASE_THREE_START, PHASE_THREE_END, numGetRequest,
        countDownLatch);
    countDownLatch.await();
  }


  /**
   * Spawns the threads for each phase
   * @param numThreads the number of threads to spawn
   * @param phaseStart phase start time
   * @param phaseEnd phase end time
   * @param numGetRequest the number of get request for the phase
   * @param countDownLatch countDownLatch to keep track of the number of threads that are done
   * @return array of the threads that were spawned for the phase
   * @throws InterruptedException thrown if any issues are encounter while spawning the threads
   */
  private Thread[] spawnThreads(int numThreads, int phaseStart, int phaseEnd, int numGetRequest,
      CountDownLatch countDownLatch) throws InterruptedException {
    int skierRange = this.numSkiers / numThreads;
    Thread[] t = new Thread[numThreads];
    for (int i = 0; i < numThreads; i++) {
      int startId = i * skierRange + 1;
      int endId = (i + 1) * skierRange;
      t[i] = new Thread(new ClientThread(startId, endId, phaseStart,
          phaseEnd, dayId, resortId, numLifts, numGetRequest, countDownLatch,
          recordKeeper));
      t[i].start();
    }
    return t;
  }

  /**
   * Saves the recordData to a csv file
   * @throws IOException thrown if errors are encountered while saving data to csv
   */
  public void saveRecordToCSV() throws IOException {
    FileWriter csvWriter = new FileWriter("requestRecord.csv");
    csvWriter.append("start_time");
    csvWriter.append(",");
    csvWriter.append("request_types");
    csvWriter.append(",");
    csvWriter.append("latency");
    csvWriter.append(",");
    csvWriter.append("response_code");
    csvWriter.append("\n");
    for (List<String> rowData : this.recordKeeper.getCsvFileBuilder()) {
      csvWriter.append(String.join(",", rowData));
      csvWriter.append("\n");
    }
    csvWriter.flush();
    csvWriter.close();
  }


  /**
   * prints out the request for part 1 of the requirements
   * @param startPhasesTime the start time of the requests
   * @param endPhasesTime the end time of the requests
   */
  public void printPartOneResult(long startPhasesTime, long endPhasesTime) {
    double wallTime = endPhasesTime - startPhasesTime;
    double throughput = this.recordKeeper.getTotalResponse() / wallTime;
    System.out.println("PART 1 RESULTS");
    System.out.println("Number of successful requests sent: " + this.recordKeeper.getSuccessCount());
    System.out.println("Number of unsuccessful requests sent: " + this.recordKeeper.getErrorCount());
    System.out.println("Wall Time: " + wallTime);
    System.out.println("Throughput: " + throughput);
    System.out.println();
  }

  /**
   * prints out the request for part 2 of the requirements
   * @throws IOException thrown if errors are encountered while saving data to csv
   */
  public void printPartTwoResult(double wallTime) throws IOException {
    this.saveRecordToCSV();
    List<Integer> getList = this.getRequestTimes( "GET");
    List<Integer>  postList = this.getRequestTimes("POST");
//    List<Integer> allList = new ArrayList<>(getList);
//    allList.addAll(postList);
//    int totalTime = getList.stream().mapToInt(a -> a).sum();
    System.out.println("PART 2 RESULTS");
    System.out.println("Mean Post time: " + postList.stream().mapToDouble(a -> a).average().getAsDouble());
    System.out.println("Mean Get time: " + getList.stream().mapToDouble(a -> a).average().getAsDouble());
    System.out.println("Median Post time: " + this.calculateMedian(postList));
    System.out.println("Median Get time: " + this.calculateMedian(getList));
    System.out.println("Total Wall: " + wallTime);
    System.out.println("Throughput: " + (getList.size() + postList.size()) / wallTime);
    System.out.println("p99 For Post: " + this.calculate99Percentile(postList));
    System.out.println("p99 For Get: " + this.calculate99Percentile(getList));
    System.out.println("Max Post time: " + postList.stream().mapToDouble(a -> a).max().getAsDouble());
    System.out.println("Max Get time: " + getList.stream().mapToDouble(a -> a).max().getAsDouble());
  }

  /**
   * Calculates the median for a list of request times
   * @param list the list of request times
   * @return the median time for a request
   */
  private double calculateMedian(List<Integer> list) {
    list.sort(Comparator.comparingInt(Integer::intValue));
    double median = list.get(list.size()/2);
    if (list.size() % 2 == 0) {
      median = (median + list.get(list.size() / 2-1)) / 2;
    }
    return median;
  }


  /**
   * Calculates the 99 percentile
   * @param listOfTimes list of request times
   * @return the 99 percentile time
   */
  private double calculate99Percentile(List<Integer> listOfTimes) {
    double time99 = listOfTimes.stream().mapToInt(a -> a).sum() * .99;
    listOfTimes.sort(Comparator.comparingInt(Integer::intValue));
    int runningSum = 0;
    for (Integer time: listOfTimes) {
      if (runningSum + time >= time99) {
        return time;
      }
      runningSum += time;
    }
    return listOfTimes.get(listOfTimes.size() - 1);
  }

  /**
   * Organizes the request times into a list
   * @param requestType the type of request
   * @return a list of the request time based on the request type
   */
  private List<Integer> getRequestTimes(String requestType) {
    List<List<String>> requestTypeList =
        this.recordKeeper.getCsvFileBuilder().stream().filter(
            row -> row.get(1).equals(requestType)).collect(
            Collectors.toList());
    List<Integer> result = new ArrayList<>();
    for (List<String> row: requestTypeList) {
      result.add(Integer.parseInt(row.get(2)));
    }
    return result;
  }


  /**
   * Checks the command line arguments
   * @throws RuntimeException if the arguments are incorrect
   */
  private void checkArguments() throws RuntimeException {
    if (maxThreads < 1 || maxThreads > 264) {
      throw new RuntimeException("Number of threads needs to be between 1 and 264");
    }
    if (numLifts < 5 || numLifts > 60) {
      throw new RuntimeException("Number of lifts needs to be between 5 and 60");
    }
  }

  /**
   * Entry point to the client
   * @param args list of command line arguments
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    Main main = new Main();
    JCommander.newBuilder().addObject(main).build().parse(args);
    main.checkArguments();
    long startPhasesTime = System.currentTimeMillis();
    main.phaseOne();
    long endPhasesTime = System.currentTimeMillis();
    double wallTime = endPhasesTime - startPhasesTime;
    main.printPartOneResult(startPhasesTime, endPhasesTime);
    main.printPartTwoResult(wallTime);
  }
}
