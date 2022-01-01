<%@page import="java.util.List"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.LocalDateTime"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
    response.addHeader("Cache-Control", "max-age=60, must-revalidate, no-transform");
    if (request.getAttribute("loggedMail") != null) {
        session.setAttribute("ns_user", (String) request.getAttribute("logged_user"));
        session.setAttribute("ns_company", (String) request.getAttribute("logged_company_name"));
        session.setAttribute("ns_user_id", (Long) request.getAttribute("user_id"));
        session.setAttribute("ns_phone_number", (String) request.getAttribute("loggedNumber"));
        session.setAttribute("ns_mail_address", (String) request.getAttribute("loggedMail"));
        session.setAttribute("NsfirstName", (String) request.getAttribute("firstName"));
        session.setAttribute("NslastName", (String) request.getAttribute("lastName"));
        session.setAttribute("NsmiddleName", (String) request.getAttribute("middleName"));
        session.setAttribute("NsId", (String) request.getAttribute("client_id"));
        session.setAttribute("department_id",(String) request.getAttribute("department_id"));
        session.setAttribute("departments", (List) request.getAttribute("departments"));
        LocalDate today = LocalDate.now();
        session.setAttribute("year_mode", String.valueOf(today.getYear()));
        session.setAttribute("month_mode", String.valueOf(today.getMonth()));
        session.setAttribute("companyLogo",(String)request.getAttribute("companyLogo"));
        session.setAttribute("institution",(String)request.getAttribute("institution"));
        session.setAttribute("parent_institution",(String)request.getAttribute("parent_institution"));
        session.setMaxInactiveInterval(1440000);
    }
    String ns_id = (String) session.getAttribute("NsId");
    String ns_mail = (String) session.getAttribute("ns_mail_address");
    String ns_first_name = (String) session.getAttribute("NsfirstName");
    String ns_last_name = (String) session.getAttribute("NslastName");
    String ns_user = (String) session.getAttribute("ns_user");
    response.sendRedirect("http://127.0.0.1:800/client_dash/dashboard");
%>