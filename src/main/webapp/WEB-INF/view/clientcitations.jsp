<%@include file="clientheader.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet  kt-portlet">
        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                <h3 class="kt-portlet__head-title" id="data_title">
                    ${topic}  
                    <a href="<c:url value='http://127.0.0.1:800/citation/all/added/citations/' />">
                        <button  type="button" class="btn btn-primary"><i class="fa fa-search"></i> View All</button>
                    </a>

                </h3>
            </div>
            <div class="col-lg-8 col-xl-8 order-lg-1 order-xl-2">
                <form>
                    <input  id="clientName" name="clientName" type="hidden" value="${clientName}">
                    <input  id="departmentName" name="departmentName" type="hidden" value="${departmentName}">
                </form>
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-xl-12 col-lg-12 order-lg-1 order-xl-1">
                        <form:form id="kt_innovative_form" class="kt-form" method="post" modelAttribute="citation" >
                            <div class="row row-no-padding row-col-separator-xl">                                       
                                <div class="col-xl-3 col-lg-3 order-lg-1 order-xl-1">
                                    <div class="form-group">
                                        <form:select  path="client" items="${clientMap}" type="text" placeholder="select  category" class="form-control" id="client" name="client" onchange="loadDepartments()"/>
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
        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-2">
                <div class="kt-portlet kt-portlet--height-fluid"> 
                    <div class="kt-portlet__body"> 
                        <table  class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1" >
                            <thead class="thead-dark">
                                <tr>
                                    <th style="color:white" >Client</th>   
                                    <th style="color:white" >Department</th> 
                                    <th style="color:white" >License Plate</th>
                                    <th style="color:white" >Citation Amount</th>
                                    <th style="color:white" >Citation Type</th>
                                    <th style="color:white" >Citation Status</th>
                                    <th style="color:white" >Citation Date</th>
                                    <th style="color:white" >Actions</th>
                                </tr>
                            </thead>
                            <tbody id="data_info">
                                <c:choose>
                                    <c:when test="${data.size() > 0 }">
                                        <c:forEach var="citation" items="${data}">
                                            <tr>               
                                                <td scope="row"> ${citation.clientProfile.companyName}</td>
                                                <td>${citation.department.departmentName}</td>
                                                <td> <i class="flaticon2-correct"></i>${citation.vehicle.licensePlate}</td>
                                                <td><b>${citation.citationAmount}</b></td>
                                                <td>${citation.citationType.citationTypeName}</td>
                                                <td>${citation.citationStatus}</td>
                                                <td>${citation.citationDate}</td>
                                                <td>
                                                    <a href="<c:url value='http://127.0.0.1:800/citation/view/${citation.citationId}' />">
                                                        <button  type="button" class="btn btn-info btn-pill btn-elevate">View More</button>&nbsp;
                                                    </a> 
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="16">No transactions available</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>

                                <c:if test="${data.size() > 0 }">
                                <div class="panel-footer">
                                              <p><!-- Showing ${number+1} <b>to</b> ${size+1} of --></p><p> <!-- Total  Entries ::  <b>  ${totalElements} </b> --></p>
                                    <ul class="pagination"> Page  ::     
                                        <c:forEach begin="0" end="${totalPages-1}" var="page">
                                            <li class="page-item"> 
                                                <a href="<c:url value='http://127.0.0.1:800/transaction/all/added/transactions?page=${page}&size=${size}' />" class="page-link">pg. ${page+1}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                            </tbody>
                        </table>
                    </div> 
                </div> 
            </div> 
        </div> 

    </div>
</div>             

<%@include file="clientfoot.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-citation.js?v=1001" />"   type="text/javascript"></script>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/load-department.js?v=1001" />"   type="text/javascript"></script>
