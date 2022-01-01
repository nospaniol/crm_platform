<%@include file="administratortablehead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content" style="padding-top:.2vh;">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
        <div class="row">                                                   
            <div class="col-lg-12 col-xl-12 order-lg-2 order-xl-2">
                <div class="kt-portlet kt-portlet--height-fluid"> 
                    <div class="kt-portlet__body"> 
                        <table  class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1" >
                            <thead class="thead-dark">
                                <tr>
                                    <th style="color:white" >Company Name</th> 
                                    <th style="color:white" >View Spendings</th>
                                    <th style="color:white" >Departmental View</th>
                            </thead>
                            <tbody id="data_info">
                                <c:choose>
                                    <c:when test="${data.size() > 0 }">
                                        <c:forEach var="clientProfile" items="${data}">
                                            <tr>
                                                <td scope="row"> <i class="flaticon2-correct"></i> ${clientProfile.companyName} </td>                            
                                                <td>
                                                    <a href="<c:url value='http://127.0.0.1:800/tollspending/expenditure/${clientProfile.clientProfileId}' />">
                                                        <button  type="button" class="btn btn-brand btn-pill btn-elevate">View Spending</button>&nbsp;
                                                    </a> 
                                                </td>
                                                <td>
                                                    <a href="<c:url value='http://127.0.0.1:800/tollspending/departments/${clientProfile.clientProfileId}' />">
                                                        <button  type="button" class="btn"><i class="fa fa-search"></i> Departments</button>
                                                    </a> 
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="16">No clients available</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>

                                <c:if test="${data.size() > 0 }">
                                <div class="panel-footer">
                                  <!-- Showing ${number+1} <b>to</b> ${size+1} of --> <!-- Total  Entries ::  <b>  ${totalElements} </b> -->
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
<%@include file="administratorfoot.jsp" %>
