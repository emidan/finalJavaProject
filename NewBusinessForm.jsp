<%@ page import="beans.VB_Business"%>

<jsp:useBean id='newBusiness' scope="session" class='beans.VB_Business' />

<html>
  <head>
    <title>
      VoucherBank New Business Entry
    </title>
  </head>
  <body>
  	<div>

  		<% 
      if(request.getMethod().equals("POST")) {
      %>

      <%@ taglib uri="/BusinessTag" prefix="wk8" %>
      <% VB_Business newBus = new VB_Business();

      newBus.setBusinessName(request.getParameter("name"));
      newBus.setAddressLine1(request.getParameter("addr1"));
      newBus.setAddressLine2(request.getParameter("addr2"));
      newBus.setCity(request.getParameter("city"));
      newBus.setState(request.getParameter("state"));
      newBus.setZip(request.getParameter("zip"));

      %>

      <wk8:BusinessTag newBusiness="<%= newBus %>" />

      <%
      }
      %>

      <% 
      if(request.getMethod().equals("GET")) {
      %>

      <%@ taglib uri="/BusinessTag" prefix="wk8" %>
      <% VB_Business blankBus = new VB_Business(); %>

      <wk8:BusinessTag newBusiness="<%= blankBus %>" />

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
