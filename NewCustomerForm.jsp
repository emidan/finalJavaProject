<%@ page import="beans.VB_Customer"%>

<jsp:useBean id='newCustomer' scope="session" class='beans.VB_Customer' />

<html>
  <head>
    <title>
      VoucherBank New Customer Entry
    </title>
  </head>
  <body>
    <div>

      <% 
      if(request.getMethod().equals("POST")) {
      %>

      <%@ taglib uri="/CustomerTag" prefix="cus" %>
      <% VB_Customer newCus = new VB_Customer();

      newCus.setCustomerName(request.getParameter("name"));
      newCus.setCustomerEmail(request.getParameter("email"));

      %>

      <cus:CustomerTag newCustomer="<%= newCus %>" />

      <%
      }
      %>

      <% 
      if(request.getMethod().equals("GET")) {
      %>

      <%@ taglib uri="/CustomerTag" prefix="cus" %>
      <% VB_Customer blankCus = new VB_Customer(); %>

      <cus:CustomerTag newCustomer="<%= blankCus %>" />

      <%
      }
      %>
      
    </div>
    
    <br />
    <br />

    <div>
      <a href="index.html">Return Home</a>
    </div>
  </body>
</html>
