
<%@page import="com.crm.model.Department"%>
<%@page import="java.util.List"%>
<%@include file="clientdashdatatablehead.jsp" %>  

<%            String ns_company = (String) session.getAttribute("ns_company");
%> 
<body style ="font-family:arial" class="kt-quick-panel--right kt-demo-panel--right kt-offcanvas-panel--right kt-header--fixed kt-header-mobile--fixed kt-subheader--enabled kt-subheader--fixed kt-subheader--solid kt-aside--enabled kt-aside--fixed kt-page--loading">
    <%@include file="clientmobileheader.jsp" %>
    <div class="kt-grid kt-grid--hor kt-grid--root">
        <div class="kt-grid__item kt-grid__item--fluid">
            <div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor kt-wrapper" id="kt_wrapper">

                <div id="kt_header" class="kt-header kt-grid kt-grid--ver  kt-header--fixed ">
                    <!-- begin:: Aside -->
                    <div class="kt-header__brand kt-grid__item  " id="kt_header_brand" style="background-color:white;">
                        <div class="kt-header__brand-logo">
                            <a href="http://127.0.0.1:800/client_dash/dashboard">
                                <%                                    String institution = (String) request.getSession().getAttribute("institution");
                                    String parent_institution = (String) request.getSession().getAttribute("parent_institution");
                                    String department_mode = (String) request.getSession().getAttribute("department_mode");
                                    List<Department> departments = (List) request.getSession().getAttribute("departments");
                                    String companyLogo = (String) session.getAttribute("companyLogo");

                                    if (companyLogo != null || !companyLogo.isEmpty()) {
                                %>
                                <img id="client-logo" src="data:image/jpg;base64,<%out.print(companyLogo);%>" alt="<%out.print(institution);%>" style="width:72px;height:72px;" />
                                <% } else {
                                %>
                                <img id="client-logo" src="<c:url value="/resources/assets/media/company-logos/logo-2.png?v=1001" />" alt="<%out.print(institution);%>" style="width:72px;height:72px;" />
                                <%
                                    }
                                %>   
                            </a>
                        </div>
                    </div>
                    <h3 class="kt-header__title kt-grid__item">
                        <%out.print(institution);%>
                    </h3>                

                    <button  class="kt-header-menu-wrapper-close" id="kt_header_menu_mobile_close_btn"><i class="la la-close"></i></button>
                    <div class="kt-header-menu-wrapper kt-grid__item kt-grid__item--fluid" id="kt_header_menu_wrapper">
                        <div id="kt_header_menu" class="kt-header-menu kt-header-menu-mobile  kt-header-menu--layout-default ">
                            <ul class="kt-menu__nav ">  
                                <li class="kt-menu__item kt-menu__item--active" aria-haspopup="true" data-ktmenu-submenu-toggle="hover">
                                    <%
                                        if (departments == null || departments.size() == 0) {
                                        } else {
                                    %>
                                    <form class="kt-form" method="post" id="department-panel">
                                        <div class="form-group" style="padding-top:4vh;" >
                                            <select class="form-control" type="text" placeholder="select department" id="department" name="department">
                                                <option value="" selected="selected"> - - </option>
                                                <option value="ALL">ALL</option>
                                                <%
                                                    for (Department department : departments) {
                                                %>
                                                <option value="<%out.print(department.getDepartmentName());%>"><%out.print(department.getDepartmentName());%></option>
                                                <%                                        }
                                                %>

                                            </select>
                                        </div>
                                    </form> 
                                    <%
                                        }
                                    %>
                                </li>
                                <%
                                    if (institution.contains("PROTECH AS")) {
                                %>
                                <li class="kt-menu__item" aria-haspopup="true">
                                    <a style="font-size:12px"  href="<c:url value="/client_dash/dashboard/caliber" />" class="kt-menu__link btn btn-pill btn-outline-dark"><i style="font-size:12px" class="kt-menu__link-icon flaticon-squares"></i><span class="kt-menu__link-text">Caliber Auto Glass</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                                </li>
                                <%  }
                                %>
                                <%
                                    if (institution.contains("CALIBER AUTO GLASS")) {
                                %>
                                <li class="kt-menu__item" aria-haspopup="true">
                                    <a style="font-size:12px"  href="<c:url value="/client_dash/dashboard/protech" />" class="kt-menu__link btn btn-pill btn-outline-dark"><i style="font-size:12px" class="kt-menu__link-icon flaticon-squares"></i><span class="kt-menu__link-text">Protech AS</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                                </li>
                                <%  }
                                %>

                                <li class="kt-menu__item  <c:if test="${menutype==null||menutype == 'header'||menutype==''}">kt-menu__item--active</c:if> " aria-haspopup="true">
                                    <a style="font-size:12px"  href="<c:url value="/client_dash/dashboard" />" class="kt-menu__link"><i style="font-size:12px" class="kt-menu__link-icon flaticon-home-2"></i><span class="kt-menu__link-text">Home</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                                </li>
                                <li class="kt-menu__item <c:if test="${menutype == 'vehicle'}">kt-menu__item--active</c:if>" aria-haspopup="true" data-ktmenu-submenu-toggle="hover">
                                    <a style="font-size:12px"  href="<c:url value="/client_vehicle/view_vehicle" />" class="kt-menu__link"><i style="font-size:12px" class="kt-menu__link-icon flaticon-car"></i><span class="kt-menu__link-text">Vehicle</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                                </li> 
                                <li class="kt-menu__item <c:if test="${menutype == 'transaction'}">kt-menu__item--active</c:if>" aria-haspopup="true" data-ktmenu-submenu-toggle="hover">
                                    <a style="font-size:12px"  href="<c:url value="/client_transaction/view_transaction" />" class="kt-menu__link"><i style="font-size:12px" class="kt-menu__link-icon flaticon-tabs"></i><span class="kt-menu__link-text">Transactions</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                                </li>                       
                                <c:choose>
                                    <c:when test="${institution=='AMAZON'||parent_institution=='AMAZON'||institution=='PROTECH AS'||parent_institution=='PROTECH AS'}">
                                        <li class="kt-menu__item <c:if test="${menutype == 'transponder_order'}">kt-menu__item--active</c:if>" aria-haspopup="true" data-ktmenu-submenu-toggle="hover">
                                            <a style="font-size:12px"  href="<c:url value="/client_transponder/make_order" />" class="kt-menu__link"><i style="font-size:12px" class="kt-menu__link-icon flaticon-tabs"></i><span class="kt-menu__link-text">Transponder Fulfillment</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                                        </li> 
                                    </c:when>
                                </c:choose> 
                                <li class="kt-menu__item   <c:if test="${menutype == 'citation'}">kt-menu__item--active</c:if>" aria-haspopup="true" data-ktmenu-submenu-toggle="hover">
                                    <a style="font-size:12px"  href="<c:url value="/client_citation/view_citations" />" class="kt-menu__link"><i style="font-size:12px" class="kt-menu__link-icon flaticon-warning-sign"></i><span class="kt-menu__link-text">Citations</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                                </li>   
                                <li class="kt-menu__item   <c:if test="${menutype == 'invoice'}">kt-menu__item--active</c:if>" aria-haspopup="true" data-ktmenu-submenu-toggle="hover">
                                    <a style="font-size:12px"  href="<c:url value="/client_invoice/monthly_invoice" />" class="kt-menu__link"><i style="font-size:12px" class="kt-menu__link-icon flaticon-warning-sign"></i><span class="kt-menu__link-text">Invoices</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="kt-header__topbar">
                        <%@include file="clientuserbar.jsp" %>    
                    </div>
                </div>
            </div>

