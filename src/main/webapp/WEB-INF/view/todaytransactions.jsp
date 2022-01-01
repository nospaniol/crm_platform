<%@include file="administratortablehead.jsp" %>

<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
    <div class="kt-portlet kt-portlet--mobile">
        <div class="kt-portlet__head kt-portlet__head--lg">          

            <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">                
                <c:url var="searchUrl" value="/transaction/view_date_transaction" />
                <form:form id="kt_innovative_form" class="kt-form" action="${searchUrl}" method="post" modelAttribute="transaction" enctype="multipart/form-data" >
                    <div class="row row-no-padding row-col-separator-xl">                                       
                        <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">
                            <div class="form-group" id="type-panel">
                                <form:input  path="transactionDate" type="date" placeholder="select  transaction date" class="form-control" id="transactionDate" name="transactionDate" value="${transactionDate}"/>
                                <span class="form-text text-muted">select date please.</span> 
                            </div>
                        </div>
                        <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">
                            <div class="kt-login__actions">
                                <button  id="kt_innovative_search" class="btn btn-brand btn-pill btn-elevate">search<i class="kt-menu__link-icon flaticon2-add kt-margin-r-15"></i></button>
                            </div>    
                        </div>
                    </div>
                </form:form>
            </div>

            <div class="col-lg-8 col-xl-8 order-lg-1 order-xl-2">
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-xl-12 col-lg-12 order-lg-1 order-xl-1">
                        <form:form id="kt_innovative_form" class="kt-form" method="post" modelAttribute="transaction" >
                            <div class="row row-no-padding row-col-separator-xl">                                       
                                <div class="col-xl-3 col-lg-3 order-lg-1 order-xl-1">
                                    <div class="form-group">
                                        <form:select  path="client" type="text" placeholder="select  client" class="form-control" id="client" onchange="loadDepartments()">
                                            <form:option value="" label="--- select client ---"/>
                                            <c:forEach var="clientProfile" items="${clientList}">  
                                                <form:option value="${clientProfile.companyName}" label="${clientProfile.companyName}"/>
                                            </c:forEach>
                                        </form:select>
                                        <span class="form-text text-muted">Please select  a client.</span>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-lg-2 order-lg-1 order-xl-1">
                                    <div class="kt-login__actions">
                                        <button  id="kt_innovative_client" class="btn btn-primary">client<i class="kt-menu__link-icon flaticon-customer"></i></button>
                                    </div>
                                </div>
                                <div class="col-xl-3 col-lg-3 order-lg-1 order-xl-1">
                                    <div class="form-group" id="department-panel">
                                        <form:select  path="department" class="form-control" type="text" placeholder="select  department" id="department" name="department" />
                                        <span class="form-text text-muted">Please select  the department.</span> 
                                    </div>
                                </div>                                                   
                                <div class="col-xl-2 col-lg-2 order-lg-1 order-xl-1">
                                    <div class="kt-login__actions">
                                        <button  id="kt_innovative_department" class="btn btn-dark">department<i class="kt-menu__link-icon flaticon2-group"></i></button>
                                    </div>
                                </div>    
                            </div> 
                        </form:form>
                    </div> 

                </div>
            </div>
        </div> 
        <div class="kt-portlet__body">
            <table class="table table-striped- table-bordered table-hover table-checkable" id="transaction_info" >
                <thead class="thead-dark">                
                    <tr>
                        <th style="color:white;">ID</th>  
                        <th style="color:white;">License Plate</th>                        
                        <th style="color:white;">State</th>
                        <th style="color:white;">Agency</th>
                        <th style="color:white;">Exit / Date Time</th>
                        <th style="color:white;">Posted Date</th>
                        <th style="color:white;">Exit Location</th>
                        <th style="color:white;">Exit Lane</th>
                        <th style="color:white;">Amount</th>
                        <th style="color:white;">Actions</th>
                    </tr>
                </thead>
                <tbody id="data_info">
                    <c:choose>
                        <c:when test="${allTransactions.size() > 0 }">
                            <c:forEach var="transaction" items="${allTransactions}">                            
                                <tr>
                                    <td>${transaction.transactionId}</td>
                                    <td> <i class="flaticon2-correct"></i> ${transaction.vehicle.licensePlate}</td>
                                    <td>${transaction.state}</td>
                                    <td>${transaction.agency}</td>
                                    <td>${transaction.exitDateTime}</td>
                                    <td>${transaction.postedDate}</td>
                                    <td>${transaction.exitLocation}</td>
                                    <td>${transaction.exitLane}</td>
                                    <td><b>${transaction.amount}</b></td>
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
                                <td>No Data</td>
                                <td></td>
                                <td></td>
                            </tr>
                        </c:otherwise>
                    </c:choose>

                </tbody>
            </table>

        </div> 
    </div> 
</div> 
<%@include file="dashtablefooter.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/load-department.js?v=1001" />"   type="text/javascript"></script>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-today-transaction.js?v=1001" />"   type="text/javascript"></script>

</body>

<!-- end::Body -->
</html>