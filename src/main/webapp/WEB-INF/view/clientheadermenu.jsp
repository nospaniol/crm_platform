<%@page import="com.crm.model.Department"%>
<%@page import="java.util.List"%>
<button  class="kt-header-menu-wrapper-close" id="kt_header_menu_mobile_close_btn"><i class="la la-close"></i></button>
<div class="kt-header-menu-wrapper kt-grid__item kt-grid__item--fluid" id="kt_header_menu_wrapper">
    <div id="kt_header_menu" class="kt-header-menu kt-header-menu-mobile  kt-header-menu--layout-default ">
        <ul class="kt-menu__nav ">  
            <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover">
                <%
                    String department_mode = (String) request.getSession().getAttribute("department_mode");
                   
                        List<Department> departments = (List) request.getSession().getAttribute("departments");
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
                <%                                        }
                %>
            </li>
            <li class="kt-menu__item  kt-menu__item--active " aria-haspopup="true">
            <c:choose>
                <c:when test="${institution=='PROTECH AS'}">
                    <a style="font-size:12px"  href="<c:url value="/client_dash/dashboard/caliber" />" class="kt-menu__link btn btn-brand"><i style="font-size:12px" class="kt-menu__link-icon flaticon-squares"></i><span class="kt-menu__link-text">Caliber Auto Glass</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                </c:when>
                <c:when test="${institution=='CALIBER AUTO GLASS'}">
                    <a style="font-size:12px"  href="<c:url value="/client_dash/dashboard/protech" />" class="kt-menu__link btn btn-brand"><i style="font-size:12px" class="kt-menu__link-icon flaticon-squares"></i><span class="kt-menu__link-text">Protech AS</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                </c:when>
            </c:choose> 
            </li>
            <li class="kt-menu__item  kt-menu__item--active " aria-haspopup="true">
                <a style="font-size:12px"  href="<c:url value="/client_dash/dashboard" />" class="kt-menu__link"><i style="font-size:12px" class="kt-menu__link-icon flaticon-home-2"></i><span class="kt-menu__link-text">Home</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
            </li>
            <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover">
                <a style="font-size:12px"  href="<c:url value="/client_vehicle/view_vehicle" />" class="kt-menu__link"><i style="font-size:12px" class="kt-menu__link-icon flaticon-car"></i><span class="kt-menu__link-text">Vehicle</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
            </li> 
            <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover">
                <a style="font-size:12px"  href="<c:url value="/client_transaction/view_transaction" />" class="kt-menu__link"><i style="font-size:12px" class="kt-menu__link-icon flaticon-tabs"></i><span class="kt-menu__link-text">Transactions</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
            </li>                       
            <c:choose>
                <c:when test="${institution=='AMAZON'||parent_institution=='AMAZON'}">
                    <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover">
                        <a style="font-size:12px"  href="<c:url value="/client_transponder/make_order" />" class="kt-menu__link"><i style="font-size:12px" class="kt-menu__link-icon flaticon-tabs"></i><span class="kt-menu__link-text">Transponder Fulfillment</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                    </li> 
                </c:when>
            </c:choose> 
            <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover">
                <a style="font-size:12px"  href="<c:url value="/client_citation/view_citations" />" class="kt-menu__link"><i style="font-size:12px" class="kt-menu__link-icon flaticon-warning-sign"></i><span class="kt-menu__link-text">Citations</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
            </li>   
            <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover">
                <a style="font-size:12px"  href="<c:url value="/client_invoice/monthly_invoice" />" class="kt-menu__link"><i style="font-size:12px" class="kt-menu__link-icon flaticon-warning-sign"></i><span class="kt-menu__link-text">Invoices</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
            </li>
        </ul>
    </div>
</div>
