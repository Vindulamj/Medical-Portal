package org.login;

import org.DAO.DBAccess;
import org.DAO.DBConnection;
import org.OCR.Tesseract;

import javax.servlet.RequestDispatcher;
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

        String id=checkLogin(username,password);
        //Tesseract tsrct=new Tesseract();
        //tsrct.check()
        request.setAttribute("user_id",id);

        RequestDispatcher rd= request.getRequestDispatcher("WebUser/home.jsp");
        rd.forward(request, response);

    }

    public String checkLogin(String username, String passowrd){
        try {
            dbconnection =DBConnection.getDBConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String str="SELECT * FROM user where username='"+username+"' and password='"+passowrd+"';";
        try {
            ResultSet ts= DBAccess.getData(dbconnection.getConnectionToDB(),str,null);
            ts.first();
            String id=ts.getString("id_user");
            if(id!=null){
                return id;
            }
            else{
                return "0";
            }
        } catch (SQLException ex) {
           return "0";
        }

    }



}