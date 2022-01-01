<%@include file="administratorhead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet">
            <div class="kt-portlet__body  kt-portlet__body--fit">
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                        <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                            <div class="kt-portlet__head kt-portlet__head--noborder">
                                <div class="kt-portlet__head-label">
                                    <h3 class="kt-portlet__head-title">
                                        View Departments
                                    </h3>
                                    <%@include file="alert.jsp" %>
                                </div>
                            </div>

                        </div>
                    </div>  
                </div>
                <div class="row row-no-padding row-col-separator-xl">                                                 
                    <div class="col-lg-12 col-xl-12 order-lg-2 order-xl-2">
                        <div class="kt-portlet kt-portlet--height-fluid"> 
                            <div class="kt-portlet__body"> 
                                <table  class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1" >
                                    <thead class="thead-dark">
                                        <tr>
                                            <th style="color:white" >Department Name</th> 
                                            <th style="color:white" >View Spendings</th>
                                    </thead>
                                    <tbody id="data_info">
                                        <c:choose>
                                            <c:when test="${data.size() > 0 }">
                                                <c:forEach var="department" items="${data}">
                                                    <tr>
                                                        <td scope="row"> <i class="flaticon2-correct"></i> ${department.departmentName} </td>                            
                                                        <td>
                                                            <a href="<c:url value='http://127.0.0.1:800/tollspending/annual/department/expenditure/${department.departmentId}' />">
                                                                <button  type="button" class="btn btn-brand btn-pill btn-elevate">View Spending</button>&nbsp;
                                                            </a> 
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <tr>
                                                    <td colspan="16">No available departments</td>
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
        </div>
    </div>
</div>   
<%@include file="administratorfoot.jsp" %>
