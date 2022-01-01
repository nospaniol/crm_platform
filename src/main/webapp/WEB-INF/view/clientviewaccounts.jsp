<%@include file="clientheader.jsp" %>
<%@ page import="com.crm.model.Account" %>
<%@ page import="com.crm.model.DepartmentAccount" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-md-12 col-lg-12 col-xl-12">
                <div class="kt-login__wrapper">
                    <div class="kt-login__body">                              
                        <div class="kt-login__signup">
                            <div class="kt-login__logo">
                                <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                                    <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                        <div class="kt-portlet__head kt-portlet__head--noborder">
                                            <div class="kt-portlet__head-label">
                                                <h3 class="kt-portlet__head-title" id="data_title">
                                                    ${topic}
                                                </h3>
                                                <%@include file="alert.jsp" %>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <form>
                                    <input  id="clientName" name="clientName" type="hidden" value="${clientName}">
                                    <input  id="departmentName" name="departmentName" type="hidden" value="${departmentName}">
                                </form>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>

        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-2">
                <div class="kt-portlet kt-portlet--height-fluid"> 
                    <div class="kt-portlet__body"> 
                        <div class="row">
                            <div class="col-xl-12">
                                <%                                    if (ns_user.equals("CLIENT")) {
                                %>
                                <!--begin:: Widgets/Applications/User/Profile3-->
                                <div class="kt-portlet kt-portlet--height-fluid">
                                    <div class="kt-portlet__body">
                                        <div class="kt-widget kt-widget--user-profile-3">
                                            <div class="kt-widget__top">
                                                <div class="kt-widget__media kt-hidden-">
                                                    <img src="data:image/jpg;base64,${companyLogo}"  style="width:20vh;height:12vh;" alt="LOGO">
                                                </div>
                                                <div class="kt-widget__pic kt-widget__pic--danger kt-font-danger kt-font-boldest kt-font-light kt-hidden">
                                                    <%                                                        Account acc = (Account) request.getAttribute("account");
                                                        out.print(acc.getClientProfile().getCompanyName().charAt(0));
                                                    %>
                                                </div>
                                                <div class="kt-widget__content">
                                                    <div class="kt-widget__head">
                                                        <a href="#" class="kt-widget__username">
                                                            ${account.clientProfile.companyName}
                                                            <i class="flaticon2-correct"></i>
                                                        </a>
                                                        <div class="kt-widget__action">
                                                            <a href="<c:url value='http://127.0.0.1:800/client_account/view/department/' />">
                                                                <button  type="button" class="btn btn-brand btn-sm btn-upper"><i class="fa fa-search"></i> Departments</button>
                                                            </a> 
                                                        </div>
                                                    </div>
                                                    <div class="kt-widget__subhead">
                                                        <a href="#"><i class="flaticon2-new-email"></i>${account.clientProfile.companyEmailAddress}</a>
                                                        <a href="#"><i class="flaticon2-calendar-3"></i>${account.clientProfile.companyPhoneNumber}</a>
                                                        <a href="#"><i class="flaticon2-placeholder"></i>${account.clientProfile.postalAddress}</a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="kt-widget__bottom">
                                                <div class="kt-widget__item">
                                                    <div class="kt-widget__icon">
                                                        <i class="flaticon-piggy-bank"></i>
                                                    </div>
                                                    <div class="kt-widget__details">
                                                        <span class="kt-widget__title">Amount</span>
                                                        <span class="kt-widget__value"><span>$</span>${account.amount}</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!--end:: Widgets/Applications/User/Profile3-->

                                <%
                                    }

                                    if (ns_user.equals("DEPARTMENT")) {
                                %>

                                <!--begin:: Widgets/Applications/User/Profile3-->
                                <div class="kt-portlet kt-portlet--height-fluid">
                                    <div class="kt-portlet__body">
                                        <div class="kt-widget kt-widget--user-profile-3">
                                            <div class="kt-widget__top">
                                                <div class="kt-widget__media kt-hidden-">
                                                    <img src="data:image/jpg;base64,${companyLogo}"  style="width:20vh;height:12vh;" alt="LOGO">
                                                </div>
                                                <div class="kt-widget__pic kt-widget__pic--danger kt-font-danger kt-font-boldest kt-font-light kt-hidden">
                                                    <%
                                                        DepartmentAccount acc = (DepartmentAccount) request.getAttribute("account");
                                                        out.print(acc.getDepartment().getDepartmentName().charAt(0));
                                                    %>
                                                </div>
                                                <div class="kt-widget__content">
                                                    <div class="kt-widget__head">
                                                        <a href="#" class="kt-widget__username">
                                                            ${account.getDepartment().getDepartmentName()}
                                                            <i class="flaticon2-correct"></i>
                                                        </a>
                                                    </div>
                                                    <div class="kt-widget__subhead">
                                                        <a href="#"><i class="flaticon2-new-email"></i>${account.clientProfile.companyEmailAddress}</a>
                                                        <a href="#"><i class="flaticon2-calendar-3"></i>${account.clientProfile.companyPhoneNumber}</a>
                                                        <a href="#"><i class="flaticon2-placeholder"></i>${account.clientProfile.postalAddress}</a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="kt-widget__bottom">
                                                <div class="kt-widget__item">
                                                    <div class="kt-widget__icon">
                                                        <i class="flaticon-piggy-bank"></i>
                                                    </div>
                                                    <div class="kt-widget__details">
                                                        <span class="kt-widget__title">Amount</span>
                                                        <span class="kt-widget__value"><span>$</span>${account.amount}</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!--end:: Widgets/Applications/User/Profile3-->
                                <%
                                    }
                                %>
                            </div>
                        </div>

                    </div> 
                </div> 
            </div> 
        </div> 
    </div>
</div>            
<%@include file="clientfoot.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/manage-account.js?v=1001" />"   type="text/javascript"></script>

