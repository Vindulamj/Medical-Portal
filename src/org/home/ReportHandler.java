package org.home;

import org.DAO.DBAccess;
import org.DAO.DBConnection;
import org.objects.Report;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by vindula on 9/25/16.
 */

public class ReportHandler extends HttpServlet {
    private DBConnection dbconnection =null;
    private String user_id;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String rep_id= request.getParameter("id");
        String uid= request.getParameter("uid");
        request.setAttribute("user_id",uid);
        request.setAttribute("name",name);
        request.setAttribute("report",getReportList(rep_id));
        request.setAttribute("image",getImage(rep_id));
        RequestDispatcher rd= request.getRequestDispatcher("WebUser/report.jsp");
        rd.forward(request, response);

    }

    public void setUser_id(String id){
        this.user_id=id;
    }

    private ArrayList<Report> getReportList(String id){
        ArrayList<Report> reportList = new ArrayList<Report>();
        try {
            dbconnection = DBConnection.getDBConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String str="SELECT * FROM med_report where rep_id='"+id+"';";
        try {
            ResultSet ts= DBAccess.getData(dbconnection.getConnectionToDB(),str,null);
            while(ts.next()){
                Report report =new Report();
                report.setId(ts.getString("rep_id"));
                report.setDoctor(ts.getString("rep_doctor"));
                report.setContent(ts.getString("rep_content"));
                report.setHeading(ts.getString("rep_heading"));
                report.setDate(ts.getString("rep_created_in"));
                report.setStatus(ts.getString("rep_status"));
                reportList.add(report);
            }
        } catch (SQLException ex) {
            return null;
        }
        return reportList;
    }

    private String getImage(String id){
        try {
            dbconnection =DBConnection.getDBConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String str="SELECT scn_image_name FROM med_scanned where scn_image='"+id+"';";
        try {
            ResultSet ts= DBAccess.getData(dbconnection.getConnectionToDB(),str,null);
            ts.first();
            String name=ts.getString("scn_image_name");
            return "../images/"+name;
        } catch (SQLException ex) {
            return null;
        }
    }
}

