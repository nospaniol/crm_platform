<%@include file="clientheader.jsp" %>
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
                        <table  class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1" >
                            <thead class="thead-dark">
                                <tr>
                                    <th style="color:white" >Department</th>    
                                    <th style="color:white" >Balance</th>
                            </thead>
                            <tbody id="data_info">
                                <c:choose>
                                    <c:when test="${data.size() > 0 }">
                                        <c:forEach var="account" items="${data}">
                                            <tr>               
                                                <td scope="row"> <i class="flaticon2-correct"></i> ${account.department.departmentName}</td>
                                                <td>${account.amount}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="16">No accounts available</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>

                                <c:if test="${data.size() > 0 }">
                                <div class="panel-footer">
                                   <!-- Showing ${number+1} <b>to</b> ${size+1} of --> <!-- Total  Entries ::  <b>  ${totalElements} </b> -->
                                    <ul class="pagination"> Page  ::     
                                        <c:forEach begin="0" end="${totalPages-1}" var="page">
                                            <li class="page-item"> 
                                                <a href="<c:url value='http://127.0.0.1:800/account/view/department/${clientId}?page=${page}&size=${size}' />" class="page-link">pg. ${page+1}</a>
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

<%@include file="administratorfoot.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/manage-department-account.js?v=1001" />"   type="text/javascript"></script>
