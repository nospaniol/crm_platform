<%@include file="administratorhead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet kt-portlet__body  kt-portlet__body--fit">
            <div class="row row-no-padding row-col-separator-xl">
                <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                    <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                        <div class="kt-portlet__head kt-portlet__head--noborder">
                            <div class="kt-portlet__head-label">
                                <h3 class="kt-portlet__head-title">
                                    CLIENT ACCOUNT
                                </h3>
                                <%@include file="alert.jsp" %>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

            <div class="row row-no-padding row-col-separator-xl">
                <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                    <c:url var="invoiceUrl" value="/invoice/save/saveInfo" />
                    <form:form class="kt-form" action="${invoiceUrl}"  enctype="multipart/form-data" method="POST" modelAttribute="invoice" >
                        <div class="row">
                            <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">
                                <div class="form-group">
                                    <form:input   path="invoiceId" class="form-control" type="hidden" name="invoiceId" id="invoiceId"/>
                                </div>
                                <div class="form-group">
                                    <form:select  style="font-size:10px;"  path="client" type="text" placeholder="select  category" class="form-control" id="client" onchange="loadDepartments()">
                                        <form:option value="" label="--- select client ---"/>
                                        <c:forEach var="clientProfile" items="${clientList}">  
                                            <form:option value="${clientProfile.companyName}" label="${clientProfile.companyName}"/>
                                        </c:forEach>
                                    </form:select>
                                    <span class="form-text text-muted">Please select  a client.</span>
                                </div>
                                <div class="form-group" id="department-panel">
                                    <form:select  path="department" class="form-control" type="text" placeholder="Department" id="department"/>
                                    <span class="form-text text-muted">Please select  a department.</span> 
                                </div>
                                <div class="form-group">
                                    <form:input path="invoiceDate" class="form-control" type="date" placeholder="Invoice Date"  id="invoiceDate"/>
                                    <span class="form-text text-muted">Please select  invoice date.</span> 
                                </div>
                                <div class="form-group">
                                    <form:input path="invoiceAmount" class="form-control" type="text" placeholder="Invoice Amount" id="invoiceAmount"/>
                                    <span class="form-text text-muted">Please enter invoice amount.</span> 
                                </div>
                                <div class="form-group">
                                    <form:select  path="feeType" class="form-control" type="text" placeholder="Fee Type" id="feeType">
                                        <form:option value="" label="--- select fee type ---"/>
                                        <c:forEach var="feeTypes" items="${feeList}">  
                                            <form:option value="${feeTypes.feeTypeName}" label="${feeTypes.feeTypeName}"/>
                                        </c:forEach>
                                    </form:select>
                                    <span class="form-text text-muted">Please select  the fee type.</span> 
                                </div>
                                <div class="form-group">
                                    <form:select  path="invoiceStat" class="form-control" type="text" placeholder="select  invoice status" id="invoiceStat" name="invoiceStat">
                                        <form:option class="form-control select-item" value="" label="--- Select ---"/>
                                        <form:option class="form-control select-item" value="OPEN" label="OPEN"/>
                                        <form:option class="form-control select-item" value="CLOSED" label="CLOSED"/>
                                    </form:select>
                                    <span class="form-text text-muted">Please select a invoice status.</span>
                                </div>

                            </div>
                            <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">

                                <div class="form-group">
                                    <form:input  path="paidDate" class="form-control" type="date" placeholder="Paid Date" id="paidDate"/>
                                    <span class="form-text text-muted">Please enter paid date.</span> 
                                </div>
                                <div class="form-group">
                                    <form:input  path="tollAmount" class="form-control" type="text" placeholder="Toll Amount"  id="tollAmount"/>
                                    <span class="form-text text-muted">Please enter toll amount.</span> 
                                </div>
                                <div class="form-group">
                                    <form:input  path="feeAmount" class="form-control" type="text" placeholder="Fee Amount" id="feeAmount"/>
                                    <span class="form-text text-muted">Please enter fee amount.</span> 
                                </div>
                                <div class="form-group">
                                    <form:input  path="totalPaid" class="form-control" type="text" placeholder="Total Paid " id="totalPaid"/>
                                <span class="form-text text-muted">Please enter total paid amount.</span> 
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-2 control-label">Select pdf file :
                                    </label>
                                    <div class="col-xs-8">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
                                            </div>
                                            <div class="custom-file">
                                                <form:input  path="pdfFile" type="file" name="pdfFile" accept=".pdf"
                                                             class="custom-file-input" id="inputGroupFile01"
                                                             aria-describedby="inputGroupFileAddon01" /> 
                                                <label class="custom-file-label" for="inputGroupFile01" required="">Choose file</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-xs-2 control-label">Select excel file :
                                    </label>
                                    <div class="col-xs-8">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
                                            </div>
                                            <div class="custom-file">
                                                <form:input  path="excelFile" type="file" name="excelFile" accept=".xlsx"
                                                             class="custom-file-input" id="inputGroupFile01"
                                                             aria-describedby="inputGroupFileAddon01" /> 
                                                <label class="custom-file-label" for="inputGroupFile01" required="">Choose file</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class=" row kt-login__actions">
                                    <button   class="btn btn-primary">submit<i class="kt-menu__link-icon flaticon2-add-1"></i></button>
                                </div>
                            </div>
                        </div>

                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="administratorfoot.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/load-department.js?v=1001" />"   type="text/javascript"></script>
