package org.login;

import org.DAO.DBAccess;
import org.DAO.DBConnection;
import org.OCR.Tesseract;
import org.home.ReportHandler;
import org.objects.Report;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        //tsrct.check

        if(!id.equals("0")){
            if(getType(id).equals("user")){
                request.setAttribute("user_id",id);
                request.setAttribute("reports",getReportList(id));
                request.setAttribute("name",getName(id));
                RequestDispatcher rd= request.getRequestDispatcher("WebUser/home.jsp");
                rd.forward(request, response);
            }
            else if(getType(id).equals("scanner")){
                request.setAttribute("user_id",id);
                request.setAttribute("name",getName(id));
                RequestDispatcher rd= request.getRequestDispatcher("LabUser/home.jsp");
                rd.forward(request, response);
            }
            else if(getType(id).equals("doctor")){
                request.setAttribute("user_id",id);
                request.setAttribute("reports",getDoctorReportList(id));
                request.setAttribute("name",getName(id));
                RequestDispatcher rd= request.getRequestDispatcher("Doctor/home.jsp");
                rd.forward(request, response);
            }

        }
        else{
            out.println("<script type=\"text/javascript\">");
            out.println("alert('User or password incorrect');");
            out.println("location='index.jsp';");
            out.println("</script>");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // reading the user input
        String id = request.getParameter("uid");

        if(getType(id).equals("user")){
            request.setAttribute("user_id",id);
            request.setAttribute("reports",getReportList(id));
            request.setAttribute("name",getName(id));
            RequestDispatcher rd= request.getRequestDispatcher("WebUser/home.jsp");
            rd.forward(request, response);
        }
        else if(getType(id).equals("scanner")){
            request.setAttribute("user_id",id);
            request.setAttribute("name",getName(id));
            RequestDispatcher rd= request.getRequestDispatcher("LabUser/home.jsp");
            rd.forward(request, response);
        }
        else if(getType(id).equals("doctor")){
            request.setAttribute("user_id",id);
            request.setAttribute("reports",getDoctorReportList(id));
            request.setAttribute("name",getName(id));
            RequestDispatcher rd= request.getRequestDispatcher("Doctor/home.jsp");
            rd.forward(request, response);
        }
    }

    public String checkLogin(String username, String passowrd){
        try {
            dbconnection =DBConnection.getDBConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String str="SELECT * FROM users where usr_email='"+username+"' and usr_encrypted_password='"+passowrd+"';";
        try {
            ResultSet ts= DBAccess.getData(dbconnection.getConnectionToDB(),str,null);
            ts.first();
            String id=ts.getString("usr_id");
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

    private ArrayList<Report> getReportList(String id){
        ArrayList<Report> reportList =new ArrayList<>();
        try {
            dbconnection =DBConnection.getDBConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String str="SELECT * FROM med_report where rep_user='"+id+"';";
        try {
            ResultSet ts= DBAccess.getData(dbconnection.getConnectionToDB(),str,null);
            while(ts.next()){
                Report report =new Report();
                report.setId(ts.getString("rep_id"));
                report.setDoctor(ts.getString("rep_doctor"));
                report.setContent(ts.getString("usr_content"));
                report.setHeading(ts.getString("usr_heading"));
                report.setHeading(ts.getString("usr_created_in"));
                report.setHeading(ts.getString("usr_status"));
                reportList.add(report);
            }
        } catch (SQLException ex) {
            return null;
        }
        return reportList;
    }

    private ArrayList<Report> getDoctorReportList(String id){
        ArrayList<Report> reportList =new ArrayList<>();
        try {
            dbconnection =DBConnection.getDBConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String str="SELECT * FROM med_report where rep_doctor='"+id+"';";
        try {
            ResultSet ts= DBAccess.getData(dbconnection.getConnectionToDB(),str,null);
            while(ts.next()){
                Report report =new Report();
                report.setId(ts.getString("rep_id"));
                report.setDoctor(ts.getString("rep_user"));
                report.setContent(ts.getString("usr_content"));
                report.setHeading(ts.getString("usr_heading"));
                report.setHeading(ts.getString("usr_created_in"));
                report.setHeading(ts.getString("usr_status"));
                reportList.add(report);
            }
        } catch (SQLException ex) {
            return null;
        }
        return reportList;
    }


    public String getName(String id){
        try {
            dbconnection =DBConnection.getDBConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String str="SELECT usr_name FROM users where usr_id='"+id+"';";
        try {
            ResultSet ts= DBAccess.getData(dbconnection.getConnectionToDB(),str,null);
            ts.first();
            String name=ts.getString("usr_name");
            return name;
        } catch (SQLException ex) {
            return null;
        }

    }

    public String getType(String id){
        try {
            dbconnection =DBConnection.getDBConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String str="SELECT usr_type FROM users where usr_id='"+id+"';";
        try {
            ResultSet ts= DBAccess.getData(dbconnection.getConnectionToDB(),str,null);
            ts.first();
            String name=ts.getString("usr_type");
            return name;
        } catch (SQLException ex) {
            return null;
        }

    }
}