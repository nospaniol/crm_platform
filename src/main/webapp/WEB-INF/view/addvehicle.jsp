<%@include file="administratorhead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet">
            <div class="kt-portlet__body  kt-portlet__body--fit">
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                        <div class="kt-login__body">                              
                            <div class="kt-login__signup">                                        
                                <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                    <div class="kt-portlet__head kt-portlet__head--noborder">
                                        <div class="kt-portlet__head-label">
                                            <h3 class="kt-portlet__head-title">
                                                VEHICLE UPLOAD
                                            </h3>
                                            <%@include file="alert.jsp" %>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                        <div class="kt-login__body">                              
                            <div class="kt-login__signup">                                        
                                <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                    <div class="kt-portlet__head kt-portlet__head--noborder">
                                        <div class="kt-portlet__head-label">
                                            <a href="<c:url value="/resources/templates/car-template.xlsx" />" download="car-batch-upload">
                                                <button  class="btn btn-brand btn-pill btn-elevate">Template<i class="kt-menu__link-icon flaticon2-download"></i></button>
                                            </a>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                        <div class="kt-login__form">
                            <c:url var="vehicleUrl" value="/vehicle/register/saveProfile" />
                            <form:form class="kt-form" action="${vehicleUrl}" method="post" modelAttribute="vehicle">
                                <div class="row">
                                    <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1"> 
                                        <div class="form-group">
                                            <form:input   path="vehicleId" class="form-control" type="hidden" name="vehicleId" id="vehicleId"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input   path="licensePlate" class="form-control" type="text" placeholder="License Plate" name="licensePlate" id="licensePlate"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input  path="model" class="form-control" type="text" placeholder="Model" id="model"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input  path="color" class="form-control" type="text" placeholder="Color" id="color"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input  path="make" class="form-control" type="text" placeholder="Make" id="make"/>
                                        </div>
                                        <div class="form-group">
                                            <form:select  path="axle" type="text" placeholder="select  axle" class="form-control" id="client" onchange="loadDepartments()">
                                                <form:option value="" label="--- select axle ---"/>
                                                <c:forEach var="axle" items="${axleList}">  
                                                    <form:option value="${axle.axleName}" label="${axle.axleName}"/>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                        <div class="form-group">
                                            <form:select  path="tagType" type="text" placeholder="select  tag type" class="form-control" id="client" onchange="loadDepartments()">
                                                <form:option value="" label="--- select tag type ---"/>
                                                <c:forEach var="tagType" items="${tagTypeList}">  
                                                    <form:option value="${tagType.tagTypeName}" label="${tagType.tagTypeName}"/>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                        <div class="form-group">
                                            <form:input  path="startDate" class="form-control" type="date" placeholder="Start Date" id="startDate"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input  path="endDate" class="form-control" type="date" placeholder="End Date" id="endDate"/>
                                        </div>
                                        <div class="form-group">
                                            <form:select  path="vehicleStatus" class="form-control" type="text" placeholder="select  status" id="vehicleStatus">
                                                <form:option class="form-control select-item" value="" label="--- Select ---"/>
                                                <form:option class="form-control" value="start" label="start"/>
                                                <form:option class="select-item" value="active" label="active"/>
                                                <form:option class="select-item" value="maintenance" label="maintenance"/>
                                                <form:option class="form-control" value="end" label="end"/>
                                                <form:option class="select-item" value="inactive" label="inactive"/>
                                            </form:select>
                                            <span class="form-text text-muted">Please select  the vehicle status.</span>
                                        </div>
                                        <div class="form-group">
                                            <form:textarea path="vehicleComment" class="form-control" type="text" placeholder="Comment" id="vehicleComment"/>
                                        </div>
                                    </div>
                                    <!-- contact person -->
                                    <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">                                                      
                                        <div class="form-group">
                                            <form:input  path="store" class="form-control" type="text" placeholder="Store" name="store" id="store"/>
                                        </div>
                                        <div class="form-group">
                                            <form:select path="storeLocation" type="text" placeholder="select  category" class="form-control" id="client" onchange="loadDepartments()">
                                                <form:option value="" label="--- select state ---"/>
                                                <c:forEach var="state" items="${stateList}">  
                                                    <form:option value="${state.stateName}" label="${state.stateName}"/>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                        <div class="form-group">
                                            <form:input  path="year" class="form-control" type="year" placeholder="Year" name="year" id="year"/>
                                        </div>                                                        
                                        <div class="form-group">
                                            <form:input  path="unit" class="form-control" type="text" placeholder="Unit" name="unit" id="unit"/>
                                        </div>
                                        <div class="form-group">
                                            <form:select  path="client" type="text" placeholder="select  category" class="form-control" id="client" onchange="loadDepartments()">
                                                <form:option value="" label="--- select client ---"/>
                                                <c:forEach var="clientProfile" items="${clientList}">  
                                                    <form:option value="${clientProfile.companyName}" label="${clientProfile.companyName}"/>
                                                </c:forEach>
                                            </form:select>
                                            <span class="form-text text-muted">Please select  a client.</span>
                                        </div>
                                        <div class="form-group" id="department-panel">
                                            <form:select  path="department" class="form-control" type="text" placeholder="select  department" id="department" name="department"/>
                                            <span class="form-text text-muted">Please select  the department.</span> 
                                        </div>
                                        <div class="form-group">
                                            <form:select  path="type" type="text" placeholder="select " class="form-control" id="client" onchange="loadDepartments()">
                                                <form:option value="" label="--- select type ---"/>
                                                <form:option value="DEMO" label="DEMO"/>
                                                <form:option value="RENTAL" label="RENTAL"/>
                                            </form:select>
                                            <span class="form-text text-muted">Please select a type.</span>
                                        </div>

                                        <div class="form-group">
                                            <form:input  path="tollTagId" class="form-control" type="text" placeholder="Toll Tag Id" name="tollTagId" id="tollTagId"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input  path="vin" class="form-control" type="text" placeholder="Vin" name="vin" id="vin"/>
                                        </div>
                                        <div class="kt-login__actions">
                                            <button  id="kt_login_signin_submit" class="btn btn-brand btn-pill btn-elevate">Save<i class="kt-menu__link-icon flaticon2-add kt-margin-r-15"></i></button>
                                        </div>
                                    </div> 
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
<%@include file="administratorfoot.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/load-department.js?v=1001" />"   type="text/javascript"></script>

