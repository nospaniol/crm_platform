<%@include file="clientheader.jsp" %>

<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
    <div class="kt-portlet kt-portlet--mobile">
        <div class="kt-portlet__head kt-portlet__head--lg">
            <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                <div class="kt-portlet__head-label">
                    <h3 class="kt-portlet__head-title">
                        ${topic}
                    </h3>
                    <a href="<c:url value='http://127.0.0.1:800/client_citation/all/added/citations/' />">
                        <button  type="button" class="btn btn-primary"><img src="<c:url value="/resources/assets/media/icons/svg/Navigation/Waiting.svg" />">Refresh </button>
                    </a>  
                </div>
            </div>  
        </div>
        <div class="kt-portlet__body">
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
</div>             
<%@include file="clientdashtablefooter.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-client-citation.js?v=1001" />"   type="text/javascript"></script>

</body>
</html>