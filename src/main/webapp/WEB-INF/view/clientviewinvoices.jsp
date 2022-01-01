<%@include file="clientheader.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
        <div class="row">           
            <div class="col-xl-4 col-lg-12 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <table  class="table table-striped- table-bordered table-hover table-checkable" id="invoice_info" >
                        <thead class="thead-dark">
                            <tr>
                                <th style="color:white" >Invoice Id</th>   
                                <th style="color:white" >Client</th>   
                                <th style="color:white" >Department</th> 
                                <th style="color:white" >Fee Type</th>
                                <th style="color:white" >Invoice Date</th>
                                <th style="color:white" >Invoice Amount</th>
                                <th style="color:white" >Invoice Status</th>
                                <th style="color:white" >Paid Date</th>
                                <th style="color:white" >Fee Amount</th>
                                <th style="color:white" >Toll Amount</th>
                                <th style="color:white" >Paid Amount</th>
                                <th style="color:white" >Actions</th>
                        </thead>
                        <tbody id="data_info">
                            <c:choose>
                                <c:when test="${allInvoices.size() > 0 }">
                                    <c:forEach var="invoice" items="${allInvoices}">  
                                        <tr>       
                                            <td style="font-size:12px;"> ${invoice.invoiceId}</td>
                                            <td style="font-size:12px;"> ${invoice.clientProfile.companyName}</td>
                                            <td style="font-size:12px;">${invoice.department.departmentName}</td>
                                            <td style="font-size:12px;">${invoice.feeType.feeTypeName}</td>
                                            <td style="font-size:12px;">${invoice.invoiceDate}</td>
                                            <td style="font-size:12px;">${invoice.invoiceAmount}</td>
                                            <td style="font-size:12px;">${invoice.invoiceStat}</td>
                                            <td style="font-size:12px;">${invoice.paidDate}</td>
                                            <td style="font-size:12px;">${invoice.feeAmount}</td>
                                            <td style="font-size:12px;">${invoice.tollAmount}</td>
                                            <td style="font-size:12px;">${invoice.totalPaid}</td>
                                            <td nowrap></td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td style="font-size:12px;">No Data</td>
                                        <td style="font-size:12px;">No Data</td>
                                        <td style="font-size:12px;">No Data</td>
                                        <td style="font-size:12px;">No Data</td>
                                        <td style="font-size:12px;">No Data</td>
                                        <td style="font-size:12px;">No Data</td>
                                        <td style="font-size:12px;">No Data</td>
                                        <td style="font-size:12px;">No Data</td>
                                        <td style="font-size:12px;">No Data</td>
                                        <td style="font-size:12px;">No Data</td>
                                        <td style="font-size:12px;">No Data</td>
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
<%@include file="clientdashtablefooter.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-client-invoice.js?v=1001" />"   type="text/javascript"></script>
<script>
    $(document).ready(function () {
        $("input[type='search']").wrap("<form>");
        $("input[type='search']").closest("form").attr("autocomplete", "off");
    });
</script>
</body>
</html>