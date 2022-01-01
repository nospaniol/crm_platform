<%@include file="clientheader.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">

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

        </div>
        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                <div class="kt-login__form">
                    <c:url var="vehicleUrl" value="/client_vehicle/register/saveProfile" />
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
                                    <form:input  path="model" class="form-control" type="text" placeholder="Model" name="model" id="model"/>
                                </div>
                                <div class="form-group">
                                    <form:input  path="color" class="form-control" type="text" placeholder="Color" name="color" id="color"/>
                                </div>
                                <div class="form-group">
                                    <form:input  path="make" class="form-control" type="text" placeholder="Make" name="make" id="make"/>
                                </div> 

                                <c:choose>
                                    <c:when test="${axles.size() > 0 }">
                                        <div class="form-group">
                                            <form:select path="axle" class="form-control" type="text" placeholder="select axle" id="vehicleStatus">
                                                <form:option class="select-item" value="" label="Select axle"/>
                                                <c:forEach var="axle" items="${axles}"> 
                                                    <form:option class="select-item" value="${axle.axleName}" label="${axle.axleName}"/>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                    </c:when>
                                </c:choose>
                                <div class="form-group">
                                    <form:input  path="startDate" class="form-control" type="date" placeholder="Start Date" name="startDate" id="startDate"/>
                                </div>
                                <div class="form-group">
                                    <form:input  path="endDate" class="form-control" type="date" placeholder="End Date" name="endDate" id="endDate"/>
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
                                    <form:select path="state" class="form-control" type="text" placeholder="select status" id="vehicleStatus">
                                        <c:choose>
                                            <c:when test="${states.size() > 0 }">
                                                <c:forEach var="state" items="${states}"> 
                                                    <form:option class="select-item" value="${state}" label="${state}"/>
                                                </c:forEach>
                                            </c:when>
                                        </c:choose>
                                    </form:select>
                                </div>
                                <div class="form-group">
                                    <form:input  path="year" class="form-control" type="year" placeholder="Year" name="year" id="year"/>
                                </div>                                                        
                                <div class="form-group">
                                    <form:input  path="unit" class="form-control" type="text" placeholder="Unit" name="unit" id="unit"/>
                                </div>
                                <c:choose>
                                    <c:when test="${institution=='PROTECH AS'||parent_institution=='PROTECH AS'}">
                                        <c:choose>
                                            <c:when test="${departments.size() > 0 }">
                                                <div class="form-group" id="department-panel">
                                                    <form:select class="form-control" path="department" id="departmentList">
                                                        <form:option class="select-item" value="" selected="selected" label="Select department"/>
                                                        <c:forEach var="department" items="${departments}"> 
                                                            <form:option class="select-item" value="${department.departmentName}" label="${department.departmentName}"/>
                                                        </c:forEach>
                                                    </form:select>
                                                    <span class="form-text text-muted">Please select the department.</span> 
                                                </div>    
                                            </c:when>
                                        </c:choose>

                                        <c:choose>
                                            <c:when test="${transponders.size() > 0 }">
                                                <div class="form-group">
                                                    <form:select path="tollTagId" items="${transponders}" class="form-control" id="tollTag" />
                                                    <span class="form-text text-muted">Please select toll tag .</span> 
                                                </div>                                 
                                            </c:when>
                                            <c:otherwise>                                   
                                                <div class="form-group" readonly>
                                                    <form:input  path="tollTagId" class="form-control" type="text" placeholder="Toll Tag Id" name="tollTagId" id="tollTagId" readonly="true"/>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                     <c:otherwise>
                                     <div class="form-group" readonly>
                                                    <form:input  path="tollTagId" class="form-control" type="text" placeholder="Toll Tag Id" name="tollTagId" id="tollTagI"/>
                                                </div>
                                </c:otherwise>
                                </c:choose>
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
<%@include file="clientdashtablefooter.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/delete-vehicle.js?v=1001" />"   type="text/javascript"></script>

<script>
    $(document).ready(function () {
        var opt = $("#departmentList option").sort(function (a, b) {
            return a.value.toUpperCase().localeCompare(b.value.toUpperCase())
        });
        $("#departmentList").append(opt);
        $('#tollTagId').fadeTo("slow", 0.5);
    });
</script>

