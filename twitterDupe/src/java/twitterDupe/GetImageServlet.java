
package twitterDupe;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;



/**
 *
 * @author sagenixon
 */
@WebServlet("/getImage")
public class GetImageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int tweetId = Integer.parseInt(request.getParameter("tweetId"));
            //byte[] imageData = DatabaseConnection.getImageDataByTweetId(tweetId);
            byte[] imageData = DatabaseConnection.getImageDataByTweetId(tweetId);


        response.setContentType("image/jpeg"); // Adjust content type based on image type
        response.setContentLength(imageData.length);

        try (OutputStream out = response.getOutputStream()) {
            out.write(imageData);
        }
    }
}
