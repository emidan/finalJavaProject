package maint;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class DestroyVBServlet extends HttpServlet {

	Connection connect = null;

  Statement stmt = null;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

  	PrintWriter out = response.getWriter();

  	createConnection();

  	destroyEverything();

  	printHTML(out);

  }

  private void destroyEverything() {

  	try {

  		stmt.executeUpdate("DROP TABLE VB_CUSTOMER_COUPON");
  		stmt.executeUpdate("DROP TABLE VB_VOUCHER");
  		stmt.executeUpdate("DROP TABLE VB_CUSTOMER");
  		stmt.executeUpdate("DROP TABLE VB_BUSINESS");

  		stmt.executeUpdate("DROP SEQUENCE CUST_ID_SEQ");
  		stmt.executeUpdate("DROP SEQUENCE BUS_ID_SEQ");
  		stmt.executeUpdate("DROP SEQUENCE VOUCH_ID_SEQ");

  		stmt.executeUpdate("DROP VIEW BUSINESS_VOUCHERS_VU");
  		stmt.executeUpdate("DROP VIEW CUSTOMER_VOUCHERS_VU");

  		stmt.executeUpdate("COMMIT");
  	}

  	catch(Exception e) {

  		System.out.println("Failed to destroy database information.");
  		System.out.println(e.getMessage());
  	}
  }

  private void printHTML(PrintWriter out) {

  	out.println("<!DOCTYPE html>");
    out.println("<html lang='EN'>");
    out.println("<head>");
    out.println("<title>");
    out.println("VoucherBank Destruction");
    out.println("</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<div>");
    out.println("VoucherBank Application Destroyed!");
    out.println("<br />");
    out.println("Return to main menu?");
    out.println("<br /> <br />");
    out.println("<a href='index.html'>Home</a>");
    out.println("</div>");
    out.println("</body>");
    out.println("</html>");
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