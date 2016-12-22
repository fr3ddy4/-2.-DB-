import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class MyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MyServlet() {
        super();
    }
    // path for tomcat : /Users/RuslanFarkhutdinov/IdeaProjects/hello_servlet_world/localhost/apache-tomcat-8.5.9/bin
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection c = null;
        Statement stmt = null;
        String ques = "", opt1 = "", opt2 = "", opt3 = "";
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/database1",
                            "postgres", "password");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Interview;" );
            ques = rs.getString("QUES");
            opt1 = rs.getString("OPT1");
            opt2 = rs.getString("OPT2");
            opt3 = rs.getString("OPT3");
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        String username = request.getParameter("uname"),
                page = "<html>\n" +
                        "<head>\n" +
                        "    <meta charset=\"utf-8\">\n" +
                        "    <title>Introduction</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "<form name=\"test\" method=\"GET\" action=\"servlet.html\">\n" +
                        "    <p><b>" + ques + " Hey, " + username + "!" + "</b></p>\n" +
                        "    <p><input name=\"Vote\" type=\"radio\" value=\"Option1\">" + opt1 + "</p>\n" +
                        "    <p><input name=\"Vote\" type=\"radio\" value=\"Option2\">" + opt2 + "</p>\n" +
                        "    <p><input name=\"Vote\" type=\"radio\" value=\"Option3\"" + opt3 + "</p>\n" +
                        "    <p><input type=\"submit\" value=\"Vote\"></p>\n" +
                        "</form>\n" +
                        "</body>\n" +
                        "</html>";

        response.getWriter().println("<!DOCTYPE HTML>");
        response.getWriter().println(page);
        response.getWriter().close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
