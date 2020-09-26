package api;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;
import model.LiftRide;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class SkierAPI {
  private static final String BASEURL = "http://ec2-user@ec2-54-221-155-158.compute-1.amazonaws.com"
      + ":8080/Server_war/skiers/";


  /**
   * get the total vertical for the skier for the specified ski day
   * @param resortId ID of the resort the skier is at
   * @param dayId ID number of ski day in the ski season
   * @param skierId ID of the skier riding the lift
   * @return successful operation - total vertical for the day returned
   */
  public int getSkierDayVertical(String resortId, String dayId, String skierId)
      throws URISyntaxException, IOException {
    String url = BASEURL + resortId + "/days/" + dayId + "/skiers/" + skierId;
    URIBuilder uriBuilder = new URIBuilder(url);
    return httpGetRequest(uriBuilder.toString());
  }


  /**
   * get the total vertical for the skier the specified resort.
   * @param skierId ID the skier to retrieve data for
   * @param resort resort to filter by
   * @return successful operation
   * @throws URISyntaxException
   * @throws IOException
   */
  public int getSkierResortTotals(String skierId, List<String> resort)
      throws URISyntaxException, IOException {
    String url = BASEURL + skierId + "/vertical";
    URIBuilder uriBuilder = new URIBuilder(url);
    generateResortsQueryParams(uriBuilder, resort);
    return httpGetRequest(uriBuilder.toString());
  }


  /**
   * information for new lift ride event
   * @param body the info for the new lift ride
   * @return successful operation
   * @throws IOException
   * @throws URISyntaxException
   */
  public int writeNewLiftRide(LiftRide body) throws IOException, URISyntaxException {
    String url = BASEURL + "liftrides";
    URIBuilder uriBuilder = new URIBuilder(url);
    CloseableHttpClient client = HttpClients.createDefault();
    HttpPost httpPost = new HttpPost(uriBuilder.toString());
    StringEntity entity = new StringEntity(body.toString());
    httpPost.setEntity(entity);
    httpPost.setHeader("Accept", "application/json");
    httpPost.setHeader("Content-type", "application/json");
    CloseableHttpResponse response = client.execute(httpPost);
    Scanner sc = new Scanner(response.getEntity().getContent());
    client.close();
    return sc.nextInt();
  }


  /**
   * Carries out the get quest
   * @param url the url for the get request
   * @return the status code for the request
   * @throws IOException
   */
  private int httpGetRequest(String url) throws IOException {
//    System.out.println(url);
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


  /**
   * helper method for generating the query parameters
   * @param uriBuilder the url builder
   * @param resort the list of resorts to add as the query parameters
   */
  private void generateResortsQueryParams(URIBuilder uriBuilder, List<String> resort) {
    for (String r: resort) {
      uriBuilder = uriBuilder.addParameter("resort", r);
    }
  }
}
