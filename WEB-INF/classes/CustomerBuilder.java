package tags;

import beans.*;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.sql.*;
import java.net.URL;
import oracle.jdbc.OracleResultSetMetaData;

public class CustomerBuilder extends BodyTagSupport {

  public static Connection connect = null;
  public static Statement stmt = null;
  public static ResultSet results = null;
  VB_Customer customer = null;

  public void setNewCustomer(VB_Customer customer) {

    this.customer = customer;
  }

  public int doEndTag() throws JspException {

    JspWriter out = null;

    try {

	    createConnection();

	    out = pageContext.getOut();

	    if(customer.getCustomerName() != null) {

	    	ResultSet customerID = null;

	    	customerID = stmt.executeQuery("SELECT CUST_ID_SEQ.NEXTVAL FROM VB_CUSTOMER");
	    	customerID.next();
	    	customer.setCustomerID(Integer.parseInt(customerID.getString(1)));

	    	stmt.executeQuery("INSERT INTO VB_CUSTOMER(CUSTOMER_ID, CUSTOMER_NAME, EMAIL) "
	                          + "VALUES(" + customer.getCustomerID() + ", '" + customer.getCustomerName().toUpperCase() + "', '" 
	                          + customer.getCustomerEmail() + "')");

	      stmt.executeUpdate("COMMIT");

	      stmt.close();
	      connect.close();

	    }

	  }

	  catch(Exception e) {

	  	e.printStackTrace();
	  }

	  try {

	  	printNewForm(out);
	  	printTableResults(out);
	  }

	  catch(Exception e) {

	  	e.printStackTrace();
	  }

	  return super.doEndTag();

  }

  private static void printNewForm(JspWriter out) throws IOException {

    out.println("<b>New Customer Entry Form</b>");
    out.println("<br /> <br />");
    out.println("<form method='post' action='NewCustomerForm.jsp'>");
    out.println("<label> Customer Name: </label>");
    out.println("<input type='text' name='name' size='60' maxlength='50'>");
    out.println("<br /> <br />");
    out.println("<label> Customer Email: </label>");
    out.println("<input type='email' name='email' size='30' maxlength='30'>");
    out.println("<br /> <br /> <br />");
    out.println("<input type='submit' name='submit' value='Submit'>");
    out.println("</form>");
  }

  private static void printTableResults(JspWriter out) throws IOException {

  	ResultSet results = null;

    createConnection();

    try {

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
    	out.println("Failed to get data.");
      out.println(e.getMessage());
    }
  }


  private static void createConnection() throws IOException {

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