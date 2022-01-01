<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%

    if (request.getAttribute("loggedMail") != null) {
        session.setAttribute("ns_user", (String) request.getAttribute("logged_user"));
        session.setAttribute("ns_user_id", (Long) request.getAttribute("user_id"));
        session.setAttribute("ns_phone_number", (String) request.getAttribute("loggedNumber"));
        session.setAttribute("ns_mail_address", (String) request.getAttribute("loggedMail"));
        session.setAttribute("NsfirstName", (String) request.getAttribute("firstName"));
        session.setAttribute("NslastName", (String) request.getAttribute("lastName"));
        session.setAttribute("NsmiddleName", (String) request.getAttribute("middleName"));
        session.setAttribute("NsId", (String) request.getAttribute("client_id"));
        session.setMaxInactiveInterval(1440000);
    }
    String ns_id = (String) session.getAttribute("NsId");
    String ns_mail = (String) session.getAttribute("ns_mail_address");
    String ns_first_name = (String) session.getAttribute("NsfirstName");
    String ns_last_name = (String) session.getAttribute("NslastName");
    String ns_user = (String) session.getAttribute("ns_user");

    response.sendRedirect("http://127.0.0.1:800/administrator/dashboard");
%>