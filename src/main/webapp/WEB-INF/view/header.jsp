<%   
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires",600); //Causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility

%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="${institution}">
        <meta name="author" content="${institution}">
        <title>${institution}</title>
        <meta name="description" content="${institution}">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="<c:url value="/resources/assets/css/nshome/pages/login/login-6.css?v=1001" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/assets/css/nsdash/style.bundle.css?v=1001" />"  rel="stylesheet" type="text/css" />
        <link rel="shortcut icon" href="<c:url value="/resources/images/favicon/favicon.ico?v=1001" />"/>  
    </head>



