<%@include file="administratorhead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet">
            <div class="kt-portlet__body  kt-portlet__body--fit">

                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                        <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                            <div class="kt-portlet__head kt-portlet__head--noborder">
                                <div class="kt-portlet__head-label">
                                    <h3 class="kt-portlet__head-title">
                                        Vehicle Info
                                    </h3>
                                    <%@include file="alert.jsp" %>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="col-lg-8 col-xl-8 order-lg-1 order-xl-1">
                        <div class="row row-no-padding row-col-separator-xl">
                            <div class="col-4">
                                <a href="<c:url value='http://127.0.0.1:800/vehicle/edit/vehicle/${vehicle.vehicleId}' />">
                                    <button  type="button" class="btn btn-brand btn-pill btn-elevate">Update Info<i class="kt-menu__link-icon flaticon-edit kt-margin-r-15"></i></button>&nbsp;
                                </a> 
                            </div>
                            <div class="col-4">
                                <form class="kt-form" id="kt_form" method="post">
                                    <input  type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />  
                                    <div class="kt-form__section kt-form__section--first">
                                        <div class="kt-wizard-v3__form"> 
                                            <input  type="hidden"  class="cancelItem" name="cancelItem" value="${vehicle.vehicleId}"/>
                                            <div class="kt-login__actions">
                                                <button  id="kt_innovative_cancel" class="kt_innovative_cancel btn btn-dark">Delete Vehicle<i class="kt-menu__link-icon flaticon-delete"></i></button>
                                            </div>
                                        </div>
                                    </div>
                                </form> 
                            </div>
                        </div> 
                    </div>
                </div>
                <div class="row row-no-padding row-col-separator-xl">                
                    <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                        <form action="" method="">
                            <div class="kt-form kt-form--label-right">
                                <div class="kt-form__body">
                                    <div class="kt-section kt-section--first">
                                        <div class="kt-section__body">                                                                            
                                            <div>
                                                <label class="col-form-label">License Plate</label>
                                                <div class="col-lg-9 col-xl-6">
                                                    <h3 class="kt-portlet__head-title">
                                                        ${vehicle.licensePlate}
                                                    </h3>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <!-- company info -->
                                                <div class="col-xl-4 col-lg-4 order-lg-1 order-xl-1">
                                                    <div>
                                                        <label class="col-form-label">Company Name</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="kt-portlet__head-label">
                                                                <h6 class="kt-portlet__head-title text-primary">
                                                                    ${vehicle.clientProfile.companyName}
                                                                </h6>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <label class="col-form-label">Model</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h6 class="kt-portlet__head-title text-primary">
                                                                ${vehicle.model}
                                                            </h6>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <label class="col-form-label">Color</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="input-group">

                                                                <h6 class="kt-portlet__head-title text-primary"> ${vehicle.color}</h6> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <label class="col-form-label">Make</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="input-group">

                                                                <h6 class="kt-portlet__head-title text-primary"> ${vehicle.make}</h6> 
                                                            </div>
                                                        </div>
                                                    </div> 
                                                    <div>
                                                        <label class="col-form-label">Axle</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="input-group">
                                                                <h6 class="kt-portlet__head-title text-primary"> ${vehicle.axle.axleName}</h6> 
                                                            </div>
                                                        </div>
                                                    </div> 
                                                    <div>
                                                        <label class="col-form-label">Tag Type</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="input-group">
                                                                <h6 class="kt-portlet__head-title text-primary"> ${vehicle.tagType.tagTypeName}</h6> 
                                                            </div>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-xl-4 col-lg-4 order-lg-1 order-xl-2">
                                                    <div>
                                                        <label class="col-form-label">Start Date</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h6 class="kt-portlet__head-title text-primary">
                                                                ${vehicle.startDate}
                                                            </h6>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <label class="col-form-label">End Date</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h6 class="kt-portlet__head-title text-primary">
                                                                ${vehicle.endDate}
                                                            </h6>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <label class="col-form-label">Store</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="input-group">

                                                                <h6 class="kt-portlet__head-title text-primary"> ${vehicle.store}</h6> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <label class="col-form-label">Store Location</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="input-group">

                                                                <h6 class="kt-portlet__head-title text-primary"> ${vehicle.storeLocation.storeLocationName}</h6> 
                                                            </div>
                                                        </div>
                                                    </div> 
                                                    <div>
                                                        <label class="col-form-label">Year</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h6 class="kt-portlet__head-title text-primary">
                                                                ${vehicle.year}
                                                            </h6>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <label class="col-form-label">Unit</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h6 class="kt-portlet__head-title text-primary">
                                                                ${vehicle.unit}
                                                            </h6>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-xl-4 col-lg-4 order-lg-1 order-xl-2">

                                                    <div>
                                                        <label class="col-form-label">Client</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h6 class="kt-portlet__head-title text-primary">
                                                                ${vehicle.clientProfile.companyName}
                                                            </h6>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <label class="col-form-label">Department</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h6 class="kt-portlet__head-title text-primary">
                                                                ${vehicle.department.departmentName}
                                                            </h6>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <label class="col-form-label">Toll Tag Id</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h6 class="kt-portlet__head-title text-primary">
                                                                ${vehicle.tollTagId}
                                                            </h6>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <label class="col-form-label">Vin</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h6 class="kt-portlet__head-title text-primary">
                                                                ${vehicle.vin}
                                                            </h6>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <label class="col-form-label">Vehicle Status</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h6 class="kt-portlet__head-title text-primary">
                                                                ${vehicle.vehicleStatus}
                                                            </h6>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <label class="col-form-label">Vehicle Comment</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h6 class="kt-portlet__head-title text-primary">
                                                                ${vehicle.vehicleComment}
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
<%@include file="administratorfoot.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/delete-vehicle.js?v=1001" />"   type="text/javascript"></script>
