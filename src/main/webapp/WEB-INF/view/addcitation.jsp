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
                                        CITATION
                                    </h3>
                                    <%@include file="alert.jsp" %>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                        <div class="kt-login__form">
                            <c:url var="citationUrl" value="/citation/save/saveInfo" />
                            <form:form class="kt-form" action="${citationUrl}" method="post" modelAttribute="citation" >
                                <div class="row">
                                    <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">
                                        <div class="form-group">
                                            <form:input   path="citationId" class="form-control" type="hidden" name="citationId" id="citationId"/>
                                        </div>
                                        <div class="form-group">
                                            <form:select   path="client" items="${clientMap}" class="form-control" type="text" placeholder="Client" name="client" id="client"  onchange="loadDepartments()"/>
                                            <span class="form-text text-muted">Please select  a client.</span> 
                                        </div>
                                        <div class="form-group" id="department-panel">
                                            <form:select  path="department" class="form-control" type="text" placeholder="Department" name="department" id="department"  onchange="loadDepartmentPlates()"/>
                                            <span class="form-text text-muted">Please select  a department.</span> 
                                        </div>
                                        <div class="form-group" id='kt_innovative_plate'>
                                            <form:select  path="licensePlate" class="form-control" type="text" placeholder="License Plate" name="licensePlate" id="licensePlate"/>
                                            <span class="form-text text-muted">Please select  a license plate.</span> 
                                        </div>
                                        <div class="form-group">
                                            <form:select  path="licensePlateState" items="${stateMap}" class="form-control" type="text" placeholder="License Plate State" name="licensePlateState" id="licensePlateState"/>
                                            <span class="form-text text-muted">Please select  the license plate's state.</span> 
                                        </div>
                                        <div class="form-group">
                                            <form:select  path="violationState" items="${stateMap}" class="form-control" type="text" placeholder="Violation State" name="violationState" id="violationState"/>
                                            <span class="form-text text-muted">Please select  the violation state.</span> 
                                        </div>
                                        <div class="form-group">
                                            <form:input  path="violationNumber" class="form-control" type="text" placeholder="Violation Number" name="violationNumber" id="violationNumber"/>
                                        </div>     

                                    </div>
                                    <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">
                                        <div class="form-group">
                                            <form:select  path="citationType" items="${typeMap}" class="form-control" type="text" placeholder="Citation Type" name="citationType" id="citationType"/>
                                            <span class="form-text text-muted">Please select  the citation type.</span> 
                                        </div>
                                        <div class="form-group">
                                            <form:select  path="citationStatus" class="form-control" type="text" placeholder="select  citation status" id="citationStatus" name="citationStatus">
                                                <form:option class="form-control select-item" value="" label="--- Select ---"/>
                                                <form:option class="form-control select-item" value="OPEN" label="OPEN"/>
                                                <form:option class="form-control select-item" value="CLOSED" label="CLOSED"/>
                                            </form:select>
                                            <span class="form-text text-muted">Please select  a citation status.</span>
                                        </div>
                                        <div class="form-group">
                                            <form:input  path="citationDate" class="form-control" type="date" placeholder="Citation Date" name="citationDate" id="citationDate"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input  path="citationAmount" class="form-control" type="number" placeholder="Citation Amount" name="citationAmount" id="citationAmount"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input  path="feeAmount" class="form-control" type="number" placeholder="Fee Amount" name="feeAmount" id="feeAmount"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input  path="paidAmount" class="form-control" type="number" placeholder="Paid Amount" name="paidAmount" id="paidAmount"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input  path="payableTo" class="form-control" type="text" placeholder="Payable To" name="payableTo" id="payableTo"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input  path="paidDate" class="form-control" type="date" placeholder="Paid Date" name="paidDate" id="paidDate"/>
                                        </div>
                                    </div> 
                                </div>
                                <div class=" row kt-login__actions">
                                    <button  class="btn btn-primary">submit<i class="kt-menu__link-icon flaticon2-add-1"></i></button>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>             
</div>
</div>
</div>
</div>    
</div>    
<%@include file="administratorfoot.jsp" %>
<script src="<c:url value="/resources/assets/js/nshome/pages/login/citation-general.js?v=1001" />"  type="text/javascript"></script>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/load-department-cars.js?v=1001" />"   type="text/javascript"></script>
