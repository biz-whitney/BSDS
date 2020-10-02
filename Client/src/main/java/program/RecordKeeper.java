package program;

import java.util.ArrayList;
import java.util.List;

/**
 * This class keeps record of the client's interaction with the server.
 */
public class RecordKeeper {
  private int errorCount = 0;
  private int successCount = 0;
  private int totalResponse = 0;
  private List<List<String>> csvFileBuilder = new ArrayList<>();

  /**
   * Records the data for the client interaction
   * @param successCount the number of successful requests
   * @param errorCount the number of unsuccessful requests
   * @param csvData the records for the csv file
   */
  public synchronized void recordData(int successCount, int errorCount,
      List<List<String>> csvData) {
    this.successCount += successCount;
    this.errorCount += errorCount;
    this.totalResponse += errorCount + successCount;
    this.csvFileBuilder.addAll(csvData);
  }

  public int getErrorCount() {
    return errorCount;
  }

  public void setErrorCount(int errorCount) {
    this.errorCount = errorCount;
  }

  public int getSuccessCount() {
    return successCount;
  }

  public void setSuccessCount(int successCount) {
    this.successCount = successCount;
  }

  public int getTotalResponse() {
    return totalResponse;
  }

  public void setTotalResponse(int totalResponse) {
    this.totalResponse = totalResponse;
  }

  public List<List<String>> getCsvFileBuilder() {
    return csvFileBuilder;
  }

  public void setCsvFileBuilder(List<List<String>> csvFileBuilder) {
    this.csvFileBuilder = csvFileBuilder;
  }
}

