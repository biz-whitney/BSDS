package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "servlet.ResortServlet")
public class ResortServlet extends HttpServlet {

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
  }


  /**
   * Gets skier vertical data
   * @param request the request object
   * @param response the response object
   * @throws ServletException
   * @throws IOException
   */
  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
      response.setContentType("text/plain");
      String urlPath = request.getPathInfo();
      String[] resorts = request.getParameterValues("resort");

      if (urlPath == null || urlPath.length() == 0) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().write("missing paramterers");
        return;
      }

      String[] urlParts = urlPath.split("/");
      if (!isUrlValid(urlParts)) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      } else {
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("200");
      }
  }

  /**
   * Validates the get request url
   * @param urlPath the url for the get request
   * @return boolean if url is valid, otherwise false
   */
  private boolean isUrlValid(String[] urlPath) {
    if (!urlPath[1].equals("day") || !urlPath[2].equals("top10vert") ) {
      return false;
    }
    return true;
  }
}
