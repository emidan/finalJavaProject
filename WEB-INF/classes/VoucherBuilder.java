package tags;

import beans.*;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.sql.*;
import java.net.URL;
import oracle.jdbc.OracleResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class VoucherBuilder extends BodyTagSupport {

  public static Connection connect = null;
  public static Statement stmt = null;
  public static ResultSet results = null;
  public static List<String> businessList = new ArrayList<String>();
  VB_Voucher voucher = null;

  public void setNewVoucher(VB_Voucher voucher) {

    this.voucher = voucher;
  }

  public int doEndTag() throws JspException {

    JspWriter out = null;

    try {

	    createConnection();

	    out = pageContext.getOut();

	    if(voucher.getDetails1() != null) {

	    	ResultSet voucherID = null;

	    	voucherID = stmt.executeQuery("SELECT VOUCH_ID_SEQ.NEXTVAL FROM VB_VOUCHER");
	    	voucherID.next();
	    	voucher.setVoucherID(Integer.parseInt(voucherID.getString(1)));
	    	voucher.setBusinessID(determineBusinessID(voucher.getBusinessName()));

	    	stmt.executeQuery("INSERT INTO VB_VOUCHER(VOUCHER_ID, EFFECTIVE_DATE, EXPIRATION_DATE, DEAL_DETAILS, DEAL_DETAILS2, BUSINESS_ID) "
	                          + "VALUES(" + voucher.getVoucherID() + ", TO_CHAR(TO_DATE('" + voucher.getEffectiveDate() + "','YYYY-MM-DD'),'DD-MON-YYYY'), "
	                          + "TO_CHAR(TO_DATE('" + voucher.getExpirationDate() + "','YYYY-MM-DD'),'DD-MON-YYYY'), '" + voucher.getDetails1() + "', '"
	                          + voucher.getDetails2() + "', " + voucher.getBusinessID() + ")");

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

	private static List<String> getBusinessList() throws IOException, SQLException {

		createConnection();

		ResultSet businessNames = null;

		businessList.clear();

		businessNames = stmt.executeQuery("SELECT BUSINESS_NAME FROM VB_BUSINESS");

		while(businessNames.next()) {

			businessList.add(businessNames.getString("BUSINESS_NAME"));
		}

		return businessList;

	}

	private static int determineBusinessID(String businessName) throws IOException {

		int businessID = 0;

		createConnection();

		try {

			results = stmt.executeQuery("SELECT BUSINESS_ID FROM VB_BUSINESS WHERE BUSINESS_NAME = '" + businessName + "'");
			results.next();
			businessID = Integer.parseInt(results.getString(1));

		}

		catch(Exception e) {
		
			System.out.println(e.getMessage());
		}

		return businessID;
	}

	private static void printNewForm(JspWriter out) throws IOException, SQLException {

		List<String> businessList = new ArrayList<String>();

  	businessList = getBusinessList();

    out.println("<b>New Voucher Entry Form</b>");
    out.println("<br /> <br />");
    out.println("<form method='post' action='NewVoucherForm.jsp'>");
    out.println("<label> Business: </label>");
    out.println("<select class='optionColor' name='business'>");

    for(int i = 0; i < businessList.size(); i++) {

    	String name = (String)businessList.get(i);
    	out.println("<option class='optionColor' value='" + name + "'>" + name + "</option>");
    }

    out.println("</select>");
    out.println("<br /> <br />");
    out.println("<label> Details: </label>");
    out.println("<input type='text' name='dtls1' size='60' maxlength='50'>");
    out.println("<br /> <br />");
    out.println("<label> Additional Details: </label>");
    out.println("<input type='text' name='dtls2' size='60' maxlength='50'>");
    out.println("<br /> <br />");
    out.println("<label> Effective Date: </label>");
    out.println("<input type='date' name='effDt'>");
    out.println("<br /> <br />");
    out.println("<label> Expiration Date: </label>");
    out.println("<input type='date' name='exprDt' size='30' maxlength='30'>");
    out.println("<br /> <br /> <br />");
    out.println("<input type='submit' name='submit' value='Submit'>");
    out.println("</form>");
  }

  private static void printTableResults(JspWriter out) throws IOException {

  	ResultSet results = null;

    createConnection();

    try {

    	results = stmt.executeQuery("SELECT VOUCHER_ID, B.BUSINESS_NAME, DEAL_DETAILS, DEAL_DETAILS2, EFFECTIVE_DATE, EXPIRATION_DATE FROM VB_VOUCHER "
    		+ "JOIN VB_BUSINESS B ON VB_VOUCHER.BUSINESS_ID = B.BUSINESS_ID ORDER BY VOUCHER_ID ASC");

      out.println("<table border=2>");
      out.println("<tr>");
      out.println("<th> Voucher ID </th>");
      out.println("<th> Business Name </th>");
      out.println("<th> Deal Details </th>");
      out.println("<th> Additional Details </th>");
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