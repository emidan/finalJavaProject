package maint;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class SetupVBServlet extends HttpServlet {

	Connection connect = null;

  Statement stmt = null;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

  	PrintWriter out = response.getWriter();

  	createConnection();

  	setupVBTables();

  	setupVBSequences();

  	setupVBViews();

  	insertSetupData();

  	printHTML(out);

  }

  private void setupVBTables() {

  	try {

  		stmt.executeUpdate("CREATE TABLE VB_CUSTOMER(CUSTOMER_ID NUMBER(4) NOT NULL, CUSTOMER_NAME VARCHAR2(50) NOT NULL, EMAIL VARCHAR2(30) NOT NULL, "
  			+ "CONSTRAINT CUST_ID_PK PRIMARY KEY (CUSTOMER_ID))");

  		stmt.executeUpdate("CREATE TABLE VB_BUSINESS(BUSINESS_ID NUMBER(4) NOT NULL, BUSINESS_NAME VARCHAR2(50) NOT NULL, ADD_LINE1 VARCHAR2(30) NOT NULL, "
  			+ "ADD_LINE2 VARCHAR2(30), CITY VARCHAR2(30) NOT NULL, STATE CHAR(2) NOT NULL, ZIP_CODE CHAR(5) NOT NULL, CONSTRAINT BUS_ID_PK PRIMARY KEY (BUSINESS_ID))");
  	
  		stmt.executeUpdate("CREATE TABLE VB_VOUCHER(VOUCHER_ID NUMBER(4) NOT NULL, EFFECTIVE_DATE DATE NOT NULL, EXPIRATION_DATE DATE NOT NULL, "
  			+ "DEAL_DETAILS VARCHAR2(50) NOT NULL, DEAL_DETAILS2 VARCHAR2(50) NOT NULL, BUSINESS_ID NUMBER(4) NOT NULL, CONSTRAINT VOUCH_ID_PK PRIMARY KEY (VOUCHER_ID), "
  			+ "CONSTRAINT VOUCH_BUS_FK FOREIGN KEY (BUSINESS_ID) REFERENCES VB_BUSINESS (BUSINESS_ID))");

  		stmt.executeUpdate("CREATE TABLE VB_CUSTOMER_COUPON(CUSTOMER_ID NUMBER(4) NOT NULL, VOUCHER_ID 	NUMBER(4) NOT NULL, CONSTRAINT CUST_VOUCH_ID_PK "
  			+ "PRIMARY KEY (CUSTOMER_ID, VOUCHER_ID), CONSTRAINT CUSTOMER_ID_FK FOREIGN KEY (CUSTOMER_ID) REFERENCES VB_CUSTOMER (CUSTOMER_ID), "
  			+ "CONSTRAINT VOUCH_ID_FK FOREIGN KEY (VOUCHER_ID) REFERENCES VB_VOUCHER (VOUCHER_ID))");
  	}

  	catch(Exception e) {

  		System.out.println("Failed to create tables.");
  		System.out.println(e.getMessage());
  	}

  }

  private void setupVBSequences() {

  	try {

  		stmt.executeUpdate("CREATE SEQUENCE CUST_ID_SEQ START WITH 1 INCREMENT BY 1 MAXVALUE 9999");
  		stmt.executeUpdate("CREATE SEQUENCE BUS_ID_SEQ START WITH 1 INCREMENT BY 1 MAXVALUE 9999");
  		stmt.executeUpdate("CREATE SEQUENCE VOUCH_ID_SEQ START WITH 1 INCREMENT BY 1 MAXVALUE 9999");
  	}

  	catch(Exception e) {

  		System.out.println("Failed to create sequences for VoucherBank");
  		System.out.println(e.getMessage());
  	}
  }

  private void setupVBViews() {

  	try {
  		stmt.executeUpdate("CREATE VIEW BUSINESS_VOUCHERS_VU AS SELECT B.BUSINESS_NAME, V.DEAL_DETAILS, V.DEAL_DETAILS2, EFFECTIVE_DATE, EXPIRATION_DATE "
  			+ "FROM VB_BUSINESS B, VB_VOUCHER V WHERE V.BUSINESS_ID = B.BUSINESS_ID ORDER BY B.BUSINESS_NAME, V.DEAL_DETAILS, V.DEAL_DETAILS2, EFFECTIVE_DATE, EXPIRATION_DATE");

  		stmt.executeUpdate("CREATE VIEW CUSTOMER_VOUCHERS_VU AS SELECT C.CUSTOMER_NAME, V.DEAL_DETAILS, V.DEAL_DETAILS2, V.EXPIRATION_DATE "
  			+ "FROM VB_CUSTOMER C, VB_VOUCHER V, VB_CUSTOMER_COUPON CV WHERE CV.CUSTOMER_ID = C.CUSTOMER_ID AND CV.VOUCHER_ID = V.VOUCHER_ID "
  			+ "ORDER BY C.CUSTOMER_NAME, V.DEAL_DETAILS, V.DEAL_DETAILS2, V.EXPIRATION_DATE");
  	}

  	catch(Exception e) {

  		System.out.println("Failed to create views.");
  		System.out.println(e.getMessage());
  	}
  }

  private void insertSetupData() {

  	try {

  		stmt.executeUpdate("INSERT INTO VB_CUSTOMER(CUSTOMER_ID, CUSTOMER_NAME, EMAIL) VALUES(CUST_ID_SEQ.NEXTVAL, 'ASTRO NOMICAL', 'astro@sf.com')");
  		stmt.executeUpdate("INSERT INTO VB_CUSTOMER(CUSTOMER_ID, CUSTOMER_NAME, EMAIL) VALUES(CUST_ID_SEQ.NEXTVAL, 'CODEY BEAR', 'codey@sf.com')");
  		stmt.executeUpdate("INSERT INTO VB_CUSTOMER(CUSTOMER_ID, CUSTOMER_NAME, EMAIL) VALUES(CUST_ID_SEQ.NEXTVAL, 'APPY BOBCAT', 'appy@sf.com')");

  		stmt.executeUpdate("INSERT INTO VB_BUSINESS(BUSINESS_ID, BUSINESS_NAME, ADD_LINE1, CITY, STATE, ZIP_CODE) "
  			+ "VALUES(BUS_ID_SEQ.NEXTVAL, 'HAPPY DAYS PIZZA', '661 LEY ST', 'COMO', 'WA', '6152')");
  		stmt.executeUpdate("INSERT INTO VB_BUSINESS(BUSINESS_ID, BUSINESS_NAME, ADD_LINE1, CITY, STATE, ZIP_CODE) "
  			+ "VALUES(BUS_ID_SEQ.NEXTVAL, 'NORTHLANDS KEBABS', '201 AMELIA ST', 'PERTH', 'WA', '6021')");
  		stmt.executeUpdate("INSERT INTO VB_BUSINESS(BUSINESS_ID, BUSINESS_NAME, ADD_LINE1, CITY, STATE, ZIP_CODE) "
  			+ "VALUES(BUS_ID_SEQ.NEXTVAL, 'SWAGMAN ROADHOUSE', '599 HEPBURN ST', 'MOUNT MAGNET', 'WA', '6638')");

  		stmt.executeUpdate("INSERT INTO VB_VOUCHER(VOUCHER_ID, EFFECTIVE_DATE, EXPIRATION_DATE, DEAL_DETAILS, DEAL_DETAILS2, BUSINESS_ID) "
  			+ "VALUES(VOUCH_ID_SEQ.NEXTVAL, TO_DATE('10-NOV-2018','DD-MON-YYYY'), TO_DATE('10-DEC-2018','DD-MON-YYYY'), '25% OFF', '1 PER CUSTOMER', 1)");
  		stmt.executeUpdate("INSERT INTO VB_VOUCHER(VOUCHER_ID, EFFECTIVE_DATE, EXPIRATION_DATE, DEAL_DETAILS, DEAL_DETAILS2, BUSINESS_ID) "
  			+ "VALUES(VOUCH_ID_SEQ.NEXTVAL, TO_DATE('10-OCT-2018','DD-MON-YYYY'), TO_DATE('31-DEC-2018','DD-MON-YYYY'), '50% OFF', 'HOLIDAYS EXCLUDED', 2)");
  		stmt.executeUpdate("INSERT INTO VB_VOUCHER(VOUCHER_ID, EFFECTIVE_DATE, EXPIRATION_DATE, DEAL_DETAILS, DEAL_DETAILS2, BUSINESS_ID) "
  			+ "VALUES(VOUCH_ID_SEQ.NEXTVAL, TO_DATE('10-NOV-2018','DD-MON-YYYY'), TO_DATE('09-NOV-2019','DD-MON-YYYY'), 'BUY ONE ENTREE GET ONE FREE', "
  			+ "'CHEAPEST ITEM FREE', 3)");

  		stmt.executeUpdate("INSERT INTO VB_CUSTOMER_COUPON(CUSTOMER_ID, VOUCHER_ID) VALUES(1,1)");
			stmt.executeUpdate("INSERT INTO VB_CUSTOMER_COUPON(CUSTOMER_ID, VOUCHER_ID) VALUES(1,2)");
			stmt.executeUpdate("INSERT INTO VB_CUSTOMER_COUPON(CUSTOMER_ID, VOUCHER_ID) VALUES(2,3)");
			stmt.executeUpdate("INSERT INTO VB_CUSTOMER_COUPON(CUSTOMER_ID, VOUCHER_ID) VALUES(3,1)");
			stmt.executeUpdate("INSERT INTO VB_CUSTOMER_COUPON(CUSTOMER_ID, VOUCHER_ID) VALUES(3,3)");

			stmt.executeUpdate("COMMIT");

  	}

  	catch(Exception e) {

  		System.out.println("Failed to insert test data.");
  		System.out.println(e.getMessage());
  	}
  }

  private void printHTML(PrintWriter out) {

  	out.println("<!DOCTYPE html>");
    out.println("<html lang='EN'>");
    out.println("<head>");
    out.println("<title>");
    out.println("VoucherBank Setup");
    out.println("</title>");
    out.println("<link rel='stylesheet' href='styles/VB_Styles.css'>");
    out.println("</head>");
    out.println("<body>");
    out.println("<div class='row'>");
    out.println("<div class='column left'>");
    out.println("<div class='maint'>");
    out.println("VoucherBank Application Complete!");
    out.println("<br />");
    out.println("Return to main menu?");
    out.println("<br /> <br />");
    out.println("<a href='index.html'>Home</a>");
    out.println("</div>");
    out.println("</div>");
    out.println("<div class='column right'>");
    out.println("<img class='setup' src='images/creation.jpg' alt='Success'>");
    out.println("</div>");
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