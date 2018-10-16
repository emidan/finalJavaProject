<%@ page import="beans.VB_Voucher"%>
<jsp:useBean id='newVoucher' scope="session" class='beans.VB_Voucher' />

<html>
  <head>
    <title>
      VoucherBank New Voucher Entry
    </title>
    <link rel='stylesheet' href='styles/VB_Styles.css'>
  </head>
  <body>
    <div>

      <% 
      if(request.getMethod().equals("POST")) {
      %>

      <%@ taglib uri="/VoucherTag" prefix="vouch" %>
      <% VB_Voucher newVouch = new VB_Voucher();

      newVouch.setEffectiveDate(request.getParameter("effDt"));
      newVouch.setExpirationDate(request.getParameter("exprDt"));
      newVouch.setDetails1(request.getParameter("dtls1"));
      newVouch.setDetails2(request.getParameter("dtls2"));
      newVouch.setBusinessName(request.getParameter("business"));

      %>

      <vouch:VoucherTag newVoucher="<%= newVouch %>" />

      <%
      }
      %>

      <% 
      if(request.getMethod().equals("GET")) {
      %>

      <%@ taglib uri="/VoucherTag" prefix="vouch" %>
      <% VB_Voucher blankVouch = new VB_Voucher(); %>

      <vouch:VoucherTag newVoucher="<%= blankVouch %>" />

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
