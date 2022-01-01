<%@include file="clientheader.jsp" %>
<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
    <div class="kt-portlet kt-portlet--mobile">
        <div class="kt-portlet__head kt-portlet__head--lg">
            <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                <div class="kt-portlet__head-label">
                    <h3 class="kt-portlet__head-title">
                        ${topic}
                    </h3>
                    <a href="<c:url value='http://127.0.0.1:800/client_transaction/all/added/transactions/' />">
                        <button  type="button" class="btn btn-primary"><i class="la la-refresh"></i></button>
                    </a>  
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
                                <td nowrap></td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div> 
    </div> 
</div> 
<%@include file="dashtablefooter.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-client-transaction.js?v=1001" />"   type="text/javascript"></script>
<script  src="<c:url value="/resources/assets/js/nshome/pages/load-client-department.js?v=1001" />"   type="text/javascript"></script>
