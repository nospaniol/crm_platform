<%@include file="administratortablehead.jsp" %>

<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
    <div class="kt-portlet kt-portlet--mobile">

        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-xl-12 col-lg-12 order-lg-1 order-xl-1">
                <div class="kt-portlet__head kt-portlet__head--lg">
                    <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                        <div class="kt-portlet__head-label">
                            <h3 class="kt-portlet__head-title">
                                ${topic}
                            </h3>
                        </div>
                    </div>  
                </div>
            </div>
            <div class="col-xl-12 col-lg-12 order-lg-1 order-xl-1">
                <form id="kt_innovative_form" class="kt-form" method="post" >
                    <div class="row row-no-padding row-col-separator-xl">    
                        <div class="form-group">
                            <input  type="hidden" placeholder="select  category" class="form-control" value="${clientId}" id="client" name="client"/>
                        </div>
                        <div class="col-xl-4 col-lg-4 order-lg-1 order-xl-1">
                            <div class="form-group">
                                <select type="text" placeholder="select  month" class="form-control kt_selectpicker" id="year" name="year">
                                    <option value="" class="text-primary form-control">SELECT YEAR</option>
                                    <option value="2020">2020</option>
                                    <option value="2021">2021</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-xl-4 col-lg-4 order-lg-1 order-xl-1">
                            <div class="form-group">
                                <select type="text" placeholder="select  month" class="form-control kt_selectpicker" id="month" name="month">
                                    <option value="">SELECT MONTH</option>
                                    <option value="JANUARY">JANUARY</option>
                                    <option value="FEBRUARY">FEBRUARY</option>
                                    <option value="MARCH">MARCH</option>
                                    <option value="APRIL">APRIL</option>
                                    <option value="MAY">MAY</option>
                                    <option value="JUNE">JUNE</option>
                                    <option value="JULY">JULY</option>
                                    <option value="SEPTEMBER">SEPTEMBER</option>
                                    <option value="OCTOBER">OCTOBER</option>
                                    <option value="NOVEMEBER">NOVEMEBER</option>
                                    <option value="DECEMBER">DECEMBER</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-xl-2 col-lg-3 order-lg-1 order-xl-1">
                            <div class="kt-login__actions">
                                <button  id="kt_innovative_search" class="btn btn-brand">View<i class="kt-menu__link-icon flaticon2-search-1"></i></button>
                            </div>
                        </div>    
                    </div> 
                </form>
            </div> 
            <div class="col-xl-12 col-lg-12 order-lg-1 order-xl-1">
                <div class="kt-portlet__body">
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
                                            <td>${invoice.invoiceId}</td>
                                            <td>${invoice.clientProfile.companyName}</td>
                                            <td>${invoice.department.departmentName}</td>
                                            <td>${invoice.feeType.feeTypeName}</td>
                                            <td>${invoice.invoiceDate}</td>
                                            <td>${invoice.invoiceAmount}</td>
                                            <td>${invoice.invoiceStat}</td>
                                            <td>${invoice.paidDate}</td>
                                            <td>${invoice.feeAmount}</td>
                                            <td>${invoice.tollAmount}</td>
                                            <td>${invoice.totalPaid}</td>
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
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-invoice.js?v=1001" />"   type="text/javascript"></script>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/load-department.js?v=1001" />"   type="text/javascript"></script>
</body>
</html>