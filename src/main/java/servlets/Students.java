package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(urlPatterns = "/students")
public class Students extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        // Mysql connector.getConnector().connect();
        String row = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:13306/GritAcademy","user", "user");
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            row = row + "<table class=\"view\"><tr><th>ID</th><th>First name</th><th>Last name</th><th>City</th><th>Interests</th></tr>";
            while (rs.next()){
                int idStudent = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String city = "";
                String interests = "";
                if(!interests.equals("null"))
                    interests = rs.getString(5);
                if(!city.equals("null"))
                    city = rs.getString(4);
                row = row +"<tr><td>"+idStudent+"</td><td>"+firstName+"</td><td>"+lastName+"</td><td>"+city+"</td><td>"+interests+"</td></tr>";
            }
            row = row+"</table>";
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        PrintWriter out = response.getWriter();
        String html ="<html><head><link rel=\"stylesheet\" href=\"styles.css\"><title>Home page</title></head><body>" +
                "<div class=\"topnav center\">" +
                "<a href=\"/index.html\">Home</a>&nbsp" +
                "<a class=\"current\" href=\"/students\" >Students</a>&nbsp" +
                "<a href=\"/courses\">Courses</a>&nbsp" +
                "<a href=\"/students-in-courses\">Registration list</a>&nbsp" +
                "</div><h2>All students</h2>"+row+"</body></html>";
        out.println(html);
    }
}
