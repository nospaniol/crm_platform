<%@include file="administratortablehead.jsp" %>

<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
    <div class="kt-portlet kt-portlet--mobile">
        <div class="kt-portlet__body">
            <div class="row row-no-padding row-col-separator-xl">
                <div class="col-xl-12 col-lg-12 order-lg-1 order-xl-1">
                    <table  class="table table-striped- table-bordered table-hover table-checkable" id="citation_info" >
                        <thead class="thead-dark">
                            <tr>
                                <th style="color:white" >Citation Id</th>   
                                <th style="color:white" >Client</th>   
                                <th style="color:white" >Department</th> 
                                <th style="color:white" >License Plate</th>
                                <th style="color:white" >Citation Amount</th>
                                <th style="color:white" >Citation Type</th>
                                <th style="color:white" >Citation Status</th>
                                <th style="color:white" >Citation Date</th>
                                <th style="color:white" >Violation State</th>
                                <th style="color:white" >Violation Number</th>
                                <th style="color:white" >Payable To</th>
                                <th style="color:white" >Paid Amount</th>
                                <th style="color:white" >Actions</th>
                        </thead>
                        <tbody id="data_info">
                            <c:choose>
                                <c:when test="${allCitations.size() > 0 }">
                                    <c:forEach var="citation" items="${allCitations}">  
                                        <tr>       
                                            <td> ${citation.citationId}</td>
                                            <td> ${citation.clientProfile.companyName}</td>
                                            <td>${citation.department.departmentName}</td>
                                            <td><i class="flaticon2-correct"></i>${citation.vehicle.licensePlate}</td>
                                            <td><b>${citation.citationAmount}</b></td>
                                            <td>${citation.citationType.citationTypeName}</td>
                                            <td>${citation.citationStatus}</td>
                                            <td>${citation.citationDate}</td>
                                            <td>${citation.violationState.stateName}</td>
                                            <td>${citation.violationNumber}</td>
                                            <td>${citation.payableTo}</td>
                                            <td>${citation.paidAmount}</td>
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
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-citation.js?v=1001" />"   type="text/javascript"></script>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/load-department-car.js?v=1001" />"   type="text/javascript"></script>
<script>
    $(document).ready(function () {
        $("input[type='search']").wrap("<form>");
        $("input[type='search']").closest("form").attr("autocomplete", "off");
    });
</script>
</body>

</html>