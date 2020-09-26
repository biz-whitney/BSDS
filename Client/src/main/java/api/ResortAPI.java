package api;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ResortAPI {
  private static final String BASEURL = "http://ec2-user@ec2-54-221-155-158.compute-1.amazonaws.com"
      + ":8080/Server_war/resort/day/top10vert";

  /**
   * Get the top 10 skier vertical totals for this day
   * @param days resorts to query by
   * @param resorts days number in the season
   * @return status code
   * @throws URISyntaxException
   * @throws IOException
   */
  public int getToTenVert(List<String> days, List<String> resorts)
      throws URISyntaxException, IOException {
    URIBuilder uriBuilder = new URIBuilder(BASEURL);
    this.generateResortsQueryParams(uriBuilder, days, "day");
    this.generateResortsQueryParams(uriBuilder, resorts, "resort");
    return httpGetRequest(uriBuilder.toString());

  }

  /**
   * helper method for generating the query parameters
   * @param uriBuilder the url builder
   * @param queryList the list of resorts to add as the query parameters
   */
  private void generateResortsQueryParams(URIBuilder uriBuilder, List<String> queryList, String type) {
    for (String obj: queryList) {
      uriBuilder = uriBuilder.addParameter(type, obj);
    }
  }

  /**
   * Carries out the get quest
   * @param url the url for the get request
   * @return the status code for the request
   * @throws IOException
   */
  private int httpGetRequest(String url) throws IOException {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(url);
    HttpResponse httpResponse = null;
    Scanner sc = null;
    try {
      httpResponse = httpClient.execute(httpGet);
      sc = new Scanner(httpResponse.getEntity().getContent());
      httpClient.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    httpClient.close();
    return sc.nextInt();
  }
}
