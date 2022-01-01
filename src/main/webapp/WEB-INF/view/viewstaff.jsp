<%@include file="administratorhead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet">
            <div class="kt-portlet__body  kt-portlet__body--fit">
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-md-12 col-lg-12 col-xl-3">
                        <div class="kt-login__wrapper">
                            <div class="kt-login__container">
                                <div class="kt-login__body">                              
                                    <div class="kt-login__signup">
                                        <div class="kt-login__logo">
                                            <div class="row row-no-padding row-col-separator-xl">
                                                <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                                                    <p>

                                                    </p>
                                                </div>
                                                <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                                                    <a href="#">
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="kt-login__form">
                                            <div class="row">
                                                <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                                                    <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                                        <div class="kt-portlet__head kt-portlet__head--noborder">
                                                            <div class="kt-portlet__head-label">
                                                                <h3 class="kt-portlet__head-title">
                                                                    Users
                                                                </h3>
                                                                <%@include file="alert.jsp" %>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>
                                                <c:if test="${!empty allUsers}">                                                         
                                                    <div class="col-lg-12 col-xl-12 order-lg-2 order-xl-2">
                                                        <div class="kt-portlet kt-portlet--height-fluid"> 
                                                            <div class="kt-portlet__body"> 
                                                                <table  class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1" >
                                                                    <thead class="thead-dark">
                                                                        <tr>
                                                                            <th style="color:white" >Names</th>                        
                                                                            <th style="color:white" >Email Address</th>
                                                                            <th style="color:white" >Phone Number</th>
                                                                            <th style="color:white" >Action</th>
                                                                    </thead>
                                                                    <tbody>
                                                                        <c:forEach items="${allUsers}" var="user">
                                                                            <tr>
                                                                                <td scope="row"> <i class="flaticon2-correct"></i> ${user.firstName} ${user.lastName} </td>                            
                                                                                <td>${user.emailToken.emailAddress}</td>
                                                                                <td>${user.phoneToken.phoneNumber}</td>
                                                                                <td>
                                                                                    <div class="row">
                                                                                        <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                                                                                            <a href="<c:url value='http://127.0.0.1:800/staff/edit/${user.userId}' />">
                                                                                                <button  type="button" class="btn btn-brand btn-pill btn-elevate"><i class="kt-menu__link-icon flaticon-edit-1"></i></button>&nbsp;
                                                                                            </a> 
                                                                                        </div>
                                                                                        <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                                                                                            <form class="kt-form" id="kt_form" method="post">
                                                                                                <input  type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />  
                                                                                                <div class="kt-form__section kt-form__section--first">
                                                                                                    <div class="kt-wizard-v3__form"> 
                                                                                                        <input  type="hidden"  class="cancelItem" name="cancelItem" value="${user.userId}"/>
                                                                                                        <div class="kt-login__actions">
                                                                                                            <button  id="kt_innovative_cancel" class="btn btn-danger"><i class="kt-menu__link-icon flaticon-delete"></i></button>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </div>
                                                                                            </form> 
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </tbody>
                                                                </table>
                                                            </div> 
                                                        </div> 
                                                    </div> 
                                                </c:if>
                                            </div>       
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>             
                </div>
            </div>
        </div>
    </div>    
</div>    
<%@include file="administratorfoot.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/delete-user.js?v=1001" />"   type="text/javascript"></script>
<script>
    $(document).ready(function () {
        $("input[type='search']").wrap("<form>");
        $("input[type='search']").closest("form").attr("autocomplete", "off");
    });
</script>