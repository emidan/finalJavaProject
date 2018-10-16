package maint;

import beans.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.net.URL;
import oracle.jdbc.OracleResultSetMetaData;

public class DataServlet extends HttpServlet {

  Connection connect = null;

  Statement stmt = null;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    PrintWriter out = response.getWriter();

    printHeader(out);
    printFooter(out);

  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    PrintWriter out = response.getWriter();

    if(request.getParameter("submit") != null) {

    	String dataType = request.getParameter("data");

    	printHeader(out);

    	if("customer".equals(dataType)) {

    		printCustomerData(out);

    	}

    	else if("business".equals(dataType)) {

    		printBusinessData(out);
    	}

    	else if("voucher".equals(dataType)) {

    		printVoucherData(out);
    	}

    	else if("customerVoucher".equals(dataType)) {

    		printCustomerVoucherData(out);
    	}


    	printFooter(out);

    }

  }

  private void printHeader(PrintWriter out) {

    out.println("<!DOCTYPE html>");
    out.println("<html lang='EN'>");
    out.println("<head>");
    out.println("<title>");
    out.println("VoucherBank Data Viewer");
    out.println("</title>");
    out.println("<link rel='stylesheet' href='styles/VB_Styles.css'>");
    out.println("</head>");
    out.println("<body>");
    out.println("<div>");

    printSelector(out);

    out.println("</div>");
    out.println("<br /> <br /> <br />");
  }

  private void printFooter(PrintWriter out) {

  	out.println("<br /> <br /> <br />");
  	out.println("<div>");
    out.println("Return to main menu?");
    out.println("<br /> <br />");
    out.println("<a href='index.html'>Home</a>");
    out.println("</div>");
    out.println("</body>");
    out.println("</html>");
  }

  private void printSelector(PrintWriter out) {

    out.println("<b>Data Table Viewer:</b>");
    out.println("<br /> <br />");
    out.println("<form method='post' action='viewData'>");
    out.println("<label> Which Data? </label>");
    out.println("<br />");
    out.println("<select class='optionColor' name='data'>");
    out.println("<option class='optionColor' value='empty'>Choose One...</option>");
    out.println("<option class='optionColor' value='customer'>Customers</option>");
    out.println("<option class='optionColor' value='business'>Businesses</option>");
    out.println("<option class='optionColor' value='voucher'>Vouchers</option>");
    out.println("<option class='optionColor' value='customerVoucher'>Customer-Claimed Vouchers</option>");
    out.println("</select>");
    out.println("<br /> <br /> <br />");
    out.println("<input type='submit' name='submit' value='Submit'>");
    out.println("</form>");

  }

  private void printCustomerData(PrintWriter out) {

  	createConnection();

  	ResultSet results = null;

  	try{

	  	results = stmt.executeQuery("SELECT CUSTOMER_ID, CUSTOMER_NAME, EMAIL FROM VB_CUSTOMER ORDER BY CUSTOMER_ID ASC");

	  	out.println("<table border=2>");
	    out.println("<tr>");
	    out.println("<th> Customer ID </th>");
	    out.println("<th> Customer Name </th>");
	    out.println("<th> Customer Email </th>");
	    out.println("</tr>");

	  	while(results.next()) {

        out.println("<tr>");

        for(int i = 1; i <= results.getMetaData().getColumnCount(); i++) {

          out.println("<td>");

          if(results.getString(i) == null) {
            out.print(" ");
          }
          else {
            out.print((results.getString(i)).trim() + " ");
          }

          out.println("</td>");
        }

        out.println("</tr>");
      }

      out.println("</table>");

    }

    catch(Exception e) {
    	System.out.println("Failed to get data.");
    	System.out.println(e.getMessage());
    	System.out.println(e.getStackTrace());
    }

  }

  private void printBusinessData(PrintWriter out) {

  	createConnection();

  	ResultSet results = null;

  	try{

	  	results = stmt.executeQuery("SELECT BUSINESS_ID, BUSINESS_NAME, ADD_LINE1, ADD_LINE2, CITY, STATE, ZIP_CODE FROM VB_BUSINESS ORDER BY BUSINESS_ID ASC");

      out.println("<table border=2>");
      out.println("<tr>");
      out.println("<th> Business ID </th>");
      out.println("<th> Business Name </th>");
      out.println("<th> Address Line 1 </th>");
      out.println("<th> Address Line 2 </th>");
      out.println("<th> City </th>");
      out.println("<th> State </th>");
      out.println("<th> Postal Code </th>");
      out.println("</tr>");

      while(results.next()) {

        out.println("<tr>");

        for(int i = 1; i <= results.getMetaData().getColumnCount(); i++) {

          out.println("<td>");

          if(results.getString(i) == null) {
            out.print(" ");
          }
          else {
            out.print((results.getString(i)).trim() + " ");
          }

          out.println("</td>");
        }

        out.println("</tr>");
      }

      out.println("</table>");

    }

    catch(Exception e) {
    	System.out.println("Failed to get data.");
    	System.out.println(e.getMessage());
    	System.out.println(e.getStackTrace());
    }

  }

  private void printVoucherData(PrintWriter out) {

  	createConnection();

  	ResultSet results = null;

  	try{

	  	results = stmt.executeQuery("SELECT BUSINESS_NAME, DEAL_DETAILS, DEAL_DETAILS2, EFFECTIVE_DATE, EXPIRATION_DATE FROM BUSINESS_VOUCHERS_VU");

      out.println("<table border=2>");
      out.println("<tr>");
      out.println("<th> Business Name </th>");
      out.println("<th> Deal Details </th>");
      out.println("<th> More Details </th>");
      out.println("<th> Effective Date </th>");
      out.println("<th> Expiration Date </th>");
      out.println("</tr>");

      while(results.next()) {

        out.println("<tr>");

        for(int i = 1; i <= results.getMetaData().getColumnCount(); i++) {

          out.println("<td>");

          if(results.getString(i) == null) {
            out.print(" ");
          }
          else {
            out.print((results.getString(i)).trim() + " ");
          }

          out.println("</td>");
        }

        out.println("</tr>");
      }

      out.println("</table>");

    }

    catch(Exception e) {
    	System.out.println("Failed to get data.");
    	System.out.println(e.getMessage());
    	System.out.println(e.getStackTrace());
    }

  }

  private void printCustomerVoucherData(PrintWriter out) {

  	createConnection();

  	ResultSet results = null;

  	try{

	  	results = stmt.executeQuery("SELECT CUSTOMER_NAME, DEAL_DETAILS, DEAL_DETAILS2, EXPIRATION_DATE FROM CUSTOMER_VOUCHERS_VU");

      out.println("<table border=2>");
      out.println("<tr>");
      out.println("<th> Customer Name </th>");
      out.println("<th> Deal Details </th>");
      out.println("<th> More Details </th>");
      out.println("<th> Expiration Date </th>");
      out.println("</tr>");

      while(results.next()) {

        out.println("<tr>");

        for(int i = 1; i <= results.getMetaData().getColumnCount(); i++) {

          out.println("<td>");

          if(results.getString(i) == null) {
            out.print(" ");
          }
          else {
            out.print((results.getString(i)).trim() + " ");
          }

          out.println("</td>");
        }

        out.println("</tr>");
      }

      out.println("</table>");

    }

    catch(Exception e) {
    	System.out.println("Failed to get data.");
    	System.out.println(e.getMessage());
    	System.out.println(e.getStackTrace());
    }

  }

  private void createConnection() {

    try {

      DriverManager.registerDriver (new oracle.jdbc.OracleDriver());

      connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "student1","pass");

      stmt = connect.createStatement();
    }

    catch(Exception e) {

      return;
    }
  }
}