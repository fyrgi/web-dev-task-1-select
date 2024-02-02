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

@WebServlet(urlPatterns = "/courses")
public class Courses extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        // Mysql connector.getConnector().connect();

        String row = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:13306/GritAcademy","user", "user");
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM courses");
            row = row + "<table class=\"view\"><tr><th>ID</th><th>Course name</th><th>Points</th><th>Description</th></tr>";
            while (rs.next()){
                int idCourse = rs.getInt(1);
                String name = rs.getString(2);
                String points = rs.getString(3);
                String description = "";
                if(description.equals("null"))
                    description = rs.getString(5);
                row = row +"<td>"+idCourse+"</td><td>"+name+"</td><td>"+points+"</td><td>"+description+"</td></tr>";
            }
            row = row+"</table>";
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        PrintWriter out = response.getWriter();
        String html ="<html><head><link rel=\"stylesheet\" href=\"styles.css\"><title>Home page</title></head><body><div class=\"topnav center\"><a class=\"active\" href=\"/index.html\">Home</a>&nbsp<a href=\"/students\">Students</a>&nbsp<a href=\"/courses\" class=\"current\">Courses</a>&nbsp<a href=\"/students-in-courses\">Registration list</a>&nbsp</div><h2>All Courses</h2>"+row+"</body></html>";
        out.println(html);
    }
}
