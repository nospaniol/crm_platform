<%@include file="administratorhead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet">
            <div class="kt-portlet__body  kt-portlet__body--fit">
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-md-12 col-lg-12 col-xl-3">
                        <div class="kt-login__wrapper">
                            <div class="kt-login__container">
                                <div class="kt-login__body">                              
                                    <div class="kt-login__signup">
                                        <div class="kt-login__logo">
                                            <div class="row row-no-padding row-col-separator-xl">
                                                <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                                                    <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                                        <div class="kt-portlet__head kt-portlet__head--noborder">
                                                            <div class="kt-portlet__head-label">
                                                                <h3 class="kt-portlet__head-title">
                                                                    Citation
                                                                </h3>
                                                                <%@include file="alert.jsp" %>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>
                                                <div class="col-lg-8 col-xl-8 order-lg-1 order-xl-1">
                                                    <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                                        <div class="kt-portlet__head kt-portlet__head--noborder">
                                                            <div class="kt-portlet__head-label">
                                                                <div class="row">
                                                                    <!--   <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                                                                            <a href="<c:url value='http://127.0.0.1:800/citation/info/edit/${citation.citationId}' />">
                                                                                <button  type="button" class="btn btn-brand btn-pill btn-elevate">Update Info<i class="kt-menu__link-icon flaticon-edit kt-margin-r-15"></i></button>&nbsp;
                                                                            </a> 
                                                                        </div>
                                                                    -->
                                                                    <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                                                                        <form class="kt-form" id="kt_form" method="post">
                                                                            <input  type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />  
                                                                            <div class="kt-form__section kt-form__section--first">
                                                                                <div class="kt-wizard-v3__form"> 
                                                                                    <input  type="hidden"  class="cancelItem" name="cancelItem" value="${citation.citationId}"/>
                                                                                    <div class="kt-login__actions">
                                                                                        <button  id="kt_innovative_cancel" class="btn btn-dark">Delete Citation<i class="kt-menu__link-icon flaticon-delete"></i></button>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </form> 
                                                                    </div>
                                                                </div> 
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="kt-portlet__body">
                                            <form action="" method="">
                                                <div class="kt-form kt-form--label-right">
                                                    <div class="kt-form__body">
                                                        <div class="kt-section kt-section--first">
                                                            <div class="kt-section__body">                                                                            
                                                                <div class="form-group row">
                                                                    <label class="col-xl-3 col-lg-3 col-form-label">Company Logo</label>
                                                                    <div class="col-lg-9 col-xl-6">
                                                                        <img alt="Company Logo" src="data:image/jpg;base64,${companyLogo}"  style="width:20vh;height:12vh;"/>                                                                                  
                                                                    </div>
                                                                </div>

                                                                <div class="row">
                                                                    <!-- company info -->
                                                                    <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">
                                                                        <div class="form-group row">
                                                                            <label class="col-xl-3 col-lg-3 col-form-label">Company Name</label>
                                                                            <div class="col-lg-9 col-xl-6">
                                                                                <div class="kt-portlet__head-label">
                                                                                    <h6 class="kt-portlet__head-title">
                                                                                        ${citation.clientProfile.companyName}
                                                                                    </h6>

                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-xl-3 col-lg-3 col-form-label">Department</label>
                                                                            <div class="col-lg-9 col-xl-6">
                                                                                <h6 class="kt-portlet__head-title">
                                                                                    ${citation.department.departmentName}
                                                                                </h6>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-xl-3 col-lg-3 col-form-label">License Plate</label>
                                                                            <div class="col-lg-9 col-xl-6">
                                                                                <div class="input-group">
                                                                                    <h6 class="kt-portlet__head-title"> ${citation.vehicle.licensePlate}</h6> 
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-xl-3 col-lg-3 col-form-label">License Plate State</label>
                                                                            <div class="col-lg-9 col-xl-6">
                                                                                <div class="input-group">
                                                                                    <h6 class="kt-portlet__head-title"> ${citation.licensePlateState.stateName}</h6> 
                                                                                </div>
                                                                            </div>
                                                                        </div> 
                                                                        <div class="form-group row">
                                                                            <label class="col-xl-3 col-lg-3 col-form-label">Violation State</label>
                                                                            <div class="col-lg-9 col-xl-6">
                                                                                <div class="input-group">
                                                                                    <h6 class="kt-portlet__head-title"> ${citation.violationState.stateName}</h6> 
                                                                                </div>
                                                                            </div>
                                                                        </div> 
                                                                        <div class="form-group row">
                                                                            <label class="col-xl-3 col-lg-3 col-form-label">Violation Number</label>
                                                                            <div class="col-lg-9 col-xl-6">
                                                                                <div class="input-group">
                                                                                    <h6 class="kt-portlet__head-title"> ${citation.violationNumber}</h6> 
                                                                                </div>
                                                                            </div>
                                                                        </div> 
                                                                    </div>
                                                                    <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">
                                                                        <div class="form-group row">
                                                                            <label class="col-xl-3 col-lg-3 col-form-label">Citation Type</label>
                                                                            <div class="col-lg-9 col-xl-6">
                                                                                <h6 class="kt-portlet__head-title">
                                                                                    ${citation.citationType.citationTypeName}
                                                                                </h6>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-xl-3 col-lg-3 col-form-label">Citation Status</label>
                                                                            <div class="col-lg-9 col-xl-6">
                                                                                <h6 class="kt-portlet__head-title">
                                                                                    ${citation.citationStatus}
                                                                                </h6>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-xl-3 col-lg-3 col-form-label">Citation Date</label>
                                                                            <div class="col-lg-9 col-xl-6">
                                                                                <div class="input-group">
                                                                                    <h6 class="kt-portlet__head-title"> ${citation.citationDate}</h6> 
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-xl-3 col-lg-3 col-form-label">Citation Amount</label>
                                                                            <div class="col-lg-9 col-xl-6">
                                                                                <div class="input-group">
                                                                                    <h6 class="kt-portlet__head-title"> ${citation.citationAmount}</h6> 
                                                                                </div>
                                                                            </div>
                                                                        </div> 
                                                                        <div class="form-group row">
                                                                            <label class="col-xl-3 col-lg-3 col-form-label">Fee Amount</label>
                                                                            <div class="col-lg-9 col-xl-6">
                                                                                <h6 class="kt-portlet__head-title">
                                                                                    ${citation.feeAmount}
                                                                                </h6>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-xl-3 col-lg-3 col-form-label">Paid Amount</label>
                                                                            <div class="col-lg-9 col-xl-6">
                                                                                <h6 class="kt-portlet__head-title">
                                                                                    ${citation.paidAmount}
                                                                                </h6>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <label class="col-xl-3 col-lg-3 col-form-label">Payable To</label>
                                                                            <div class="col-lg-9 col-xl-6">
                                                                                <h6 class="kt-portlet__head-title">
                                                                                    ${citation.payableTo}
                                                                                </h6>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>                                                                        
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
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
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/delete-citation.js?v=1001" />"   type="text/javascript"></script>
