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
                    <div class="kt-login__form">
                        <c:url var="profileUrl" value="/client/profile/saveProfile" />
                        <form:form class="kt-form" id="client-form" action="${profileUrl}" modelAttribute="profile" enctype="multipart/form-data" method="POST" >
                            <div class="row">
                                <!-- company info -->
                                <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">
                                    <div class="kt-portlet__head kt-portlet__head--noborder">
                                        <div class="kt-portlet__head-label">
                                            <h3 class="kt-portlet__head-title">
                                                Company Info
                                            </h3>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <form:input   path="clientProfileId" class="form-control" type="hidden"  id="clientProfileId"/>
                                    </div>
                                    <div class="form-group">
                                        <form:input   path="companyName" class="form-control" type="text" placeholder="Company Name" id="companyName"/>
                                    </div>
                                    <div class="form-group">
                                        <form:input  path="postalAddress" class="form-control" type="text" placeholder="Postal Address" id="postalAddress"/>
                                    </div>
                                    <div class="form-group">
                                        <form:input  path="companyPhoneNumber" class="form-control" type="text" placeholder="Phone Number" id="companyPhoneNumber"/>
                                    </div>

                                    <div class="form-group">
                                        <form:input  path="companyEmailAddress" class="form-control" type="text" placeholder="Email" name="companyEmailAddress" id="companyEmailAddress"/>
                                    </div>
                                </div>
                                <!-- company info -->
                                <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">

                                    <div class="form-group">
                                        <form:select  path="category" class="form-control" type="text" placeholder="select  category" id="category" name="category">
                                            <form:option class="form-control select-item" value="" label="--- Select ---"/>
                                            <form:option class="form-control" value="Transactional" label="Transactional"/>
                                            <form:option class="select-item" value="Licence Plate" label="License Plate"/>
                                            <form:option class="select-item" value="No Fee" label="No Fee"/>
                                            <form:option class="select-item" value="Service Fee" label="Service Fee"/>
                                        </form:select>
                                        <span class="form-text text-muted">Please select  a category.</span>
                                    </div>
                                    <div class="form-group">
                                        <form:select  path="accountType" class="form-control" type="text" placeholder="select  account type" id="accountType" name="accountType">
                                            <form:option value="" label="select  account type"/>
                                            <form:option value="Demo Account" label="Demo Account"/>
                                            <form:option value="Production Account" label="Production Account"/>
                                        </form:select>
                                        <span class="form-text text-muted">Please select  an account type.</span> 
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-2 control-label">Select company logo :
                                        </label>
                                        <div class="col-xs-8">
                                            <div class="input-group">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
                                                </div>
                                                <div class="custom-file">
                                                    <form:input  path="companyLogo" type="file" name="companyLogo" accept=".png,.jpg,.jpeg"
                                                                 class="custom-file-input" id="inputGroupFile01"
                                                                 aria-describedby="inputGroupFileAddon01" /> 
                                                    <label class="custom-file-label" for="inputGroupFile01" required="">Choose file</label>
                                                </div>
                                            </div>
                                        </div>
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
<%@include file="administratorfoot.jsp" %>
<script src="<c:url value="/resources/assets/js/nshome/pages/login/client-enroll-general.js?v=1001" />"  type="text/javascript"></script>
</body>
</html>