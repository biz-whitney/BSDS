package servlet;


import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.LiftRide;
import dto.Message;
import dto.TotalVerticalDay;


@WebServlet(name = "servlet.SkierServlet")
public class SkierServlet extends HttpServlet {
  private Gson gson = new Gson();

  /**
   * Post a lift ride to the DB
   * @param request the request object
   * @param response the response object
   * @throws ServletException
   * @throws IOException
   */
  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json");
    String urlPath = request.getPathInfo();
    String[] urlParts = urlPath.split("/");
    if (urlPath == null || urlPath.length() == 0) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.getWriter().write("missing paramterers");
      return;
    }
    if (!isPostUrlValid(urlParts)) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      Message message = new Message("Error");
      response.getWriter().write(gson.toJson(message));
    } else {
      response.setStatus(HttpServletResponse.SC_OK);
      BufferedReader bufferedReader = request.getReader();
      String lifeRideString = readStringIn(bufferedReader);
      LiftRide liftRide = gson.fromJson(lifeRideString, LiftRide.class);
    }
  }


  /**
   * Gets skier vertical data
   * @param req the request object
   * @param res the response object
   * @throws ServletException
   * @throws IOException
   */
  protected void doGet(HttpServletRequest req,
      HttpServletResponse res)
      throws ServletException, IOException {
    res.setContentType("application/json");
    String urlPath = req.getPathInfo();

    String[] resorts = req.getParameterValues("resort");

    if (urlPath == null || urlPath.length() == 0) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      res.getWriter().write("missing paramterers");
      return;
    }
    String[] urlParts = urlPath.split("/");
    if (!isUrlValid(urlParts)) {
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      Message message = new Message("Error");
      res.getWriter().write(gson.toJson(message));
    } else {
      res.setStatus(HttpServletResponse.SC_OK);
      TotalVerticalDay data = new TotalVerticalDay("resort", 100);
      res.getWriter().write(gson.toJson(data));
    }
  }

  /**
   * Validates if the url is correct
   * @param urlPath
   * @return
   */
  private boolean isPostUrlValid(String[] urlPath) {
    if (!urlPath[1].equals("liftrides")) {
      return false;
    }
    return true;
  }

  /**
   * Reads in the lift ride data
   * @param buffIn the buffer to store the data
   * @return string of the lift ride data
   * @throws IOException
   */
  private String readStringIn(BufferedReader buffIn) throws IOException {
    StringBuilder everything = new StringBuilder();
    String line;
    while( (line = buffIn.readLine()) != null) {
      everything.append(line);
    }
    return everything.toString();
  }


  /**
   * Validates the get request url
   * @param urlPath the url for the get request
   * @return boolean if url is valid, otherwise false
   */
  private boolean isUrlValid(String[] urlPath) {
    // TODO: validate the request url path according to the API spec
    // urlPath  = "/1/seasons/2019/day/1/skier/123"
    // urlParts = [, 1, seasons, 2019, day, 1, skier, 123]
    if (urlPath.length == 6) {
      try {
        int dayId = Integer.parseInt(urlPath[3]);
        int skierId = Integer.parseInt(urlPath[5]);
        if (dayId < 1 || skierId < 1) {
          return false;
        }
      } catch (RuntimeException e) {
        return false;
      }
      if (!urlPath[2].equals("days") || !urlPath[4].equals("skiers")) {
        return false;
      }
    } else {
      try {
        int skierId = Integer.parseInt(urlPath[1]);
        if (skierId < 1) {
          return false;
        }
      } catch (RuntimeException e) {
        return false;
      }
      if (!urlPath[2].equals("vertical")) {
        return false;
      }
    }
    return true;
  }
}