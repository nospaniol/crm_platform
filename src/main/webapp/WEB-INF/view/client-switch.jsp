<%@page import="java.util.List"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.LocalDateTime"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:if test="${!empty client_id}">  
    <%      
        session.setAttribute("NsId", (String) request.getAttribute("client_id"));
    %>
</c:if>
<%
    response.sendRedirect("http://127.0.0.1:800/client_dash/dashboard");
%>