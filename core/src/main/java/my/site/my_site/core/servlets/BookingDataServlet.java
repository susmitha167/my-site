package my.site.my_site.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/bookinginformation",
                "sling.servlet.methods=POST"
        }
)
public class BookingDataServlet extends SlingAllMethodsServlet {

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        JSONObject jsonResponse = new JSONObject();

        try {
            // Traveller Details
            String title = request.getParameter("title");
            String firstName = request.getParameter("first-name");
            String lastName = request.getParameter("last-name");
            String dob = request.getParameter("dob");

            // Additional Requests
            String mealPreference = request.getParameter("meal-preference");


            // Terms Agreement
            String termsAccepted = request.getParameter("terms");

            // Basic validation
            if (firstName == null || lastName == null) {
                response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
                jsonResponse.put("error", "Missing required fields.");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            ResourceResolver resolver = request.getResourceResolver();
            Session session = resolver.adaptTo(Session.class);

            Resource parent = resolver.getResource("/content/usergenerated/bookinginformation");

            if (parent == null) {
                Resource userGen = resolver.getResource("/content/usergenerated");
                if (userGen != null) {
                    Map<String, Object> folderProps = new HashMap<>();
                    folderProps.put("jcr:primaryType", "sling:Folder");
                    parent = resolver.create(userGen, "bookinginformation", folderProps);
                    resolver.commit(); // Use resolver.commit() when using Resource API
                }
            }

            if (parent != null) {
                String nodeName = "booking-" + System.currentTimeMillis();
                Node bookingNode = parent.adaptTo(Node.class).addNode(nodeName, "nt:unstructured");

                bookingNode.setProperty("title", title);
                bookingNode.setProperty("firstName", firstName);
                bookingNode.setProperty("lastName", lastName);
                bookingNode.setProperty("dob", dob);
                bookingNode.setProperty("mealPreference", mealPreference);
                bookingNode.setProperty("termsAccepted", termsAccepted != null ? "true" : "false");

                session.save();

                response.setStatus(SlingHttpServletResponse.SC_OK);
                response.sendRedirect("/content/mysite/us/payform.html");
            } else {
                response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                jsonResponse.put("error", "Unable to create booking node.");
            }

        } catch (Exception e) {
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);//"Something went wrong on the server side. The request couldn't be processed."
            try {
                jsonResponse.put("error", "Server error: " + e.getMessage());
            } catch (JSONException ex) {
                throw new RuntimeException(ex);// "error": "Server error: Null pointer exception"

            }
        }
    }
}
