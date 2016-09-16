package login;

import DAO.DBAccess;
import DAO.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by vindula on 9/16/16.
 */
public class LoginHandler extends HttpServlet {

    private DBConnection dbconnection =null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // reading the user input
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        PrintWriter out = response.getWriter();

        out.println (setDiagnose());
    }

    public String setDiagnose(){
        try {
            dbconnection =DBConnection.getDBConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String str="SELECT * FROM user";
        try {
            ResultSet ts= DBAccess.getData(dbconnection.getConnectionToDB(),str,null);
            ts.first();
            String name=ts.getString("name");
            return name;
        } catch (SQLException ex) {
           return null;
        }

    }



}