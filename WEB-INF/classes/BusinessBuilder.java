package tags;

import beans.*;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.sql.*;
import java.net.URL;
import oracle.jdbc.OracleResultSetMetaData;

public class BusinessBuilder extends BodyTagSupport {

  public static Connection connect = null;
  public static Statement stmt = null;
  public static ResultSet results = null;
  VB_Business business = null;

  public void setNewBusiness(VB_Business business) {

    this.business = business;
  }

  public int doEndTag() throws JspException {

    JspWriter out = null;

    try {

	    createConnection();

	    out = pageContext.getOut();

	    if(business.getBusinessName() != null) {

	    	ResultSet businessID = null;

	    	businessID = stmt.executeQuery("SELECT BUS_ID_SEQ.NEXTVAL FROM VB_BUSINESS");
	    	businessID.next();
	    	business.setBusinessID(Integer.parseInt(businessID.getString(1)));

	    	stmt.executeQuery("INSERT INTO VB_BUSINESS(BUSINESS_ID, BUSINESS_NAME, ADD_LINE1, ADD_LINE2, CITY, STATE, ZIP_CODE) "
	                          + "VALUES(" + business.getBusinessID() + ", '" + business.getBusinessName().toUpperCase() + "', '" 
	                          + business.getAddressLine1().toUpperCase() + "', '" + business.getAddressLine2().toUpperCase() + "', '"
	                          + business.getCity().toUpperCase() + "', '" + business.getState().toUpperCase() + "', '"
	                          + business.getZip().toUpperCase() + "')");

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

    out.println("<b>New Business Entry Form</b>");
    out.println("<br /> <br />");
    out.println("<form method='post' action='NewBusinessForm.jsp'>");
    out.println("<label> Business Name: </label>");
    out.println("<input type='text' name='name' size='60' maxlength='50' style='background-color: #3CBC8D;'>");
    out.println("<br /> <br />");
    out.println("<label> Address Line 1: </label>");
    out.println("<input type='text' name='addr1' size='30' maxlength='30' style='background-color: #3CBC8D;'>");
    out.println("<br /> <br />");
    out.println("<label> Address Line 2: </label>");
    out.println("<input type='text' name='addr2' size='30' maxlength='30' style='background-color: #3CBC8D;'>");
    out.println("<br /> <br />");
    out.println("<label> City: </label>");
    out.println("<input type='text' name='city' size='30' maxlength='30' style='background-color: #3CBC8D;'>");
    out.println("<br /> <br />");
    out.println("<label> State: </label>");
    out.println("<input type='text' name='state' size='5' maxlength='2' style='background-color: #3CBC8D;'>");
    out.println("<br /> <br />");
    out.println("<label> Postal Code: </label>");
    out.println("<input type='text' name='zip' size='10' maxlength='5' style='background-color: #3CBC8D;'>");
    out.println("<br /> <br /> <br />");
    out.println("<input type='submit' name='submit' value='Submit'>");
    out.println("</form>");
  }

  private static void printTableResults(JspWriter out) throws IOException {

  	ResultSet results = null;

    createConnection();

    try {

    	results = stmt.executeQuery("SELECT BUSINESS_ID, BUSINESS_NAME, ADD_LINE1, ADD_LINE2, CITY, STATE, ZIP_CODE FROM VB_BUSINESS");

      out.println("<table border=2>");
      out.println("<tr bgcolor='#189641'>");
      out.println("<th> Business ID </th>");
      out.println("<th> Business Name </th>");
      out.println("<th> Address Line 1 </th>");
      out.println("<th> Address Line 2 </th>");
      out.println("<th> City </th>");
      out.println("<th> State </th>");
      out.println("<th> Postal Code </th>");
      out.println("</tr>");

      while(results.next()) {

        out.println("<tr bgcolor='#42bacc'>");

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