<%@include file="clientheader.jsp" %>
<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
    <div class="row row-col-separator-xl">
        <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1 kt-portlet">
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
    </div>
    <div class="row row-col-separator-xl">
        <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
            <div class="kt-login__form">
                <c:url var="uploadUrl" value="/client_vehicle/upload/file/save" />
                <form:form id="kt_innovative_form" class="kt-form" action="${uploadUrl}" method="post" modelAttribute="vehicle" enctype="multipart/form-data" >
                    <c:choose>
                        <c:when test="${departments> 0 }">
                            <%
                           if (ns_user.equals("CLIENT")) {
                            %>

                            <div class="form-group" id="department-panel">
                                <form:select   path="department" items="${departmentMap}" class="form-control" id="department-1"/>
                                <span class="form-text text-muted">Please select  the department.</span> 
                            </div>

                            <%
                           }
                            %>
                        </c:when>
                    </c:choose>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">Select file :</label>
                        <div class="col-xs-8">
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
                                </div>
                                <div class="custom-file">
                                    <form:input  path="vehicles" type="file" accept=".xlsx"
                                                 class="custom-file-input" id="inputGroupFile01"
                                                 aria-describedby="inputGroupFileAddon01" /> 
                                    <label class="custom-file-label" for="inputGroupFile01" required="">Choose file</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="kt-login__actions">
                        <button  id="kt_innovative_submit" class="btn btn-brand btn-pill btn-elevate">Save<i class="kt-menu__link-icon flaticon2-add kt-margin-r-15"></i></button>
                    </div>                                             

                </form:form>
            </div>
        </div>
    </div>
</div>
<%@include file="clientfoot.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/load-client-department.js?v=1001" />"   type="text/javascript"></script>

