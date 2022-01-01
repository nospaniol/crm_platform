<%@include file="administratortablehead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet">
            <div class="row row-no-padding row-col-separator-xl">
                <div class="col-lg-10 col-xl-10 order-lg-1 order-xl-1">
                    <div class="kt-portlet__head kt-portlet__head--noborder">
                        <div class="kt-portlet__head-label">
                            <h3 class="kt-portlet__head-title">
                                ${department.departmentName} Users 
                            </h3>
                            <%@include file="alert.jsp" %>
                        </div>
                    </div>
                </div>
                <div class="col-lg-2 col-xl-2 order-lg-1 order-xl-1 kt-margin-top-20">
                    <a href="<c:url value='http://127.0.0.1:800/client/view/department/${department.clientProfile.clientProfileId}' />">
                        <button  type="button" class="btn btn-brand btn-pill btn-elevate">Departments<i class="kt-menu__link-icon flaticon-map kt-margin-r-15"></i></button>&nbsp;
                    </a> 
                </div>
                <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                    <form:form class="kt-form" id="client-form" modelAttribute="user" method="POST" >
                        <div class="form-group">
                            <form:input   path="userId" class="form-control" type="hidden" id="userId"/>
                            <form:input   path="departmentId" class="form-control" type="hidden" value="${departmentId}" id="departmentId"/>
                        </div>
                        <div class="form-group">
                            <form:input  path="firstName" class="form-control" type="text" placeholder="First Name"/>
                        </div>
                        <div class="form-group">
                            <form:input  path="lastName" class="form-control" type="text" placeholder="Last Name"/>
                        </div>
                        <div class="form-group">
                            <form:input  path="phoneNumber" class="form-control" type="text" placeholder="Phone Number"/>
                        </div>
                        <div class="form-group">
                            <form:input  path="designation" class="form-control" type="text" placeholder="Designation" id="designation"/>
                        </div>
                        <div class="form-group">
                            <form:input  path="emailAddress" class="form-control" type="text" placeholder="Email" id="email"/>
                        </div>                                                        
                        <div class="form-group">
                            <form:input  path="password" class="form-control" type="password" placeholder="Password" id="pass" />
                        </div>
                        <div class="form-group">
                            <form:input  path="rpassword" class="form-control form-control-last" type="password" placeholder="Confirm Password" id="rpassword" name="rpassword"/>
                        </div>
                        <div class="kt-login__actions">
                            <button  id="kt_register_user" class="kt_register_user btn btn-brand btn-pill btn-elevate">Save<i class="kt-menu__link-icon flaticon2-add kt-margin-r-15"></i></button>
                        </div>
                    </form:form>
                </div>
                <div class="col-lg-8 col-xl-8 order-lg-1 order-xl-1">
                    <table class="table table-striped- table-bordered table-hover table-checkable" id="user_info" >
                        <thead class="thead-dark">                
                            <tr>
                                <th style="color:white;">ID</th>  
                                <th style="color:white;">First Name</th>                        
                                <th style="color:white;">Last Name</th>
                                <th style="color:white;">Email Address</th>
                                <th style="color:white;">Designation</th>
                                <th style="color:white;">Phone Number</th>
                                <th style="color:white;">Remove</th>
                                <th style="color:white;">Edit</th>
                            </tr>
                        </thead>
                        <tbody id="data_info">
                            <c:choose>
                                <c:when test="${data.size() > 0 }">
                                    <c:forEach var="user" items="${data}">                            
                                        <tr>
                                            <td>${user.userId}</td>
                                            <td>${user.firstName}</td>
                                            <td>${user.lastName}</td>
                                            <td>${user.emailToken.emailAddress}</td>
                                            <td>${user.designation}</td>
                                            <td>${user.phoneToken.phoneNumber}</td>
                                            <td>
                                                <form class="kt-form" id="kt_form" method="post">  
                                                    <input  type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" /> 
                                                    <div class="kt-form__section kt-form__section--first">  
                                                        <div class="kt-wizard-v3__form">  
                                                            <input  type="hidden"  class="cancelItem" name="cancelItem" value="${user.userId}"/> 
                                                            <div class="kt-login__actions">  
                                                                <button  class="kt_innovative_cancel btn btn-sm btn-clean btn-icon btn-icon-md" title="Delete Department"> 
                                                                    <i class="flaticon-delete"></i> 
                                                                </button> 
                                                            </div>  
                                                        </div>  
                                                    </div>  
                                                </form> 
                                            </td>
                                            <td nowrap></td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td>No Data</td>
                                        <td>No Data</td>
                                        <td>No Data</td>
                                        <td>No Data</td>
                                        <td>No Data</td>
                                        <td>No Data</td>
                                        <td>No Data</td>
                                        <td nowrap></td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>

                        </tbody>
                    </table>
                </div>        
            </div>
        </div>
    </div>
</div>  

<%@include file="dashtablefooter.jsp" %>
<script src="<c:url value="/resources/assets/js/nshome/pages/login/department-user-general.js?v=1001" />"  type="text/javascript"></script>
<script src="<c:url value="/resources/assets/js/nshome/pages/wizard/delete-client.js?v=1001" />"  type="text/javascript"></script>

</body>
</html>