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
                                        Client Profile
                                    </h3>
                                    <%@include file="alert.jsp" %>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-2 col-xl-2 order-lg-1 order-xl-1 kt-margin-top-20">
                        <a href="<c:url value='http://127.0.0.1:800/client/new/user/${clientProfile.clientProfileId}' />">
                            <button  type="button" class="btn btn-brand btn-pill">Users<i class="kt-menu__link-icon flaticon-user-add kt-margin-r-15"></i></button>&nbsp;
                        </a> 
                    </div>
                    <div class="col-lg-3 col-xl-3 order-lg-1 order-xl-1 kt-margin-top-20">
                        <a href="<c:url value='http://127.0.0.1:800/client/profile/edit/${clientProfile.clientProfileId}' />">
                            <button  type="button" class="btn btn-dark btn-pill">Update Info<i class="kt-menu__link-icon flaticon-edit kt-margin-r-15"></i></button>&nbsp;
                        </a> 
                    </div>
                    <div class="col-lg-2 col-xl-2 order-lg-1 order-xl-1 kt-margin-top-20">
                        <a href="<c:url value='http://127.0.0.1:800/client/view/department/${clientProfile.clientProfileId}' />">
                            <button  type="button" class="btn btn-brand btn-pill">Departments<i class="kt-menu__link-icon flaticon-map kt-margin-r-15"></i></button>&nbsp;
                        </a> 
                    </div>
                    <div class="col-lg-2 col-xl-2 order-lg-1 order-xl-1 kt-margin-top-20">
                       <!-- <a href="<c:url value="/client_dash/view/group/" />">
                        <button  type="button" onclick="notifyProgress()" class="btn btn-brand btn-pill btn-info">View Groupings<i class="kt-menu__link-icon flaticon-map kt-margin-r-15"></i></button>
                         </a> -->
                    </div>
                    <div class="col-lg-3 col-xl-3 order-lg-1 order-xl-1 kt-margin-top-20">
                        <form class="kt-form" id="kt_form" method="post">
                            <input  type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />  
                            <div class="kt-form__section kt-form__section--first">
                                <div class="kt-wizard-v3__form"> 
                                    <input  type="hidden"  class="cancelItem" name="cancelItem" value="${clientProfile.clientProfileId}"/>
                                    <div class="kt-login__actions">
                                        <button  id="kt_innovative_cancel" class="btn btn-dark btn-pill">Delete Account<i class="kt-menu__link-icon flaticon-delete"></i></button>
                                    </div>
                                </div>
                            </div>
                        </form> 
                    </div>
                </div> 


                <div class="row">
                    <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                        <div class="kt-portlet__body">
                            <div class="kt-form kt-form--label-right">
                                <div class="kt-form__body">
                                    <div class="kt-section kt-section--first">
                                        <div class="kt-section__body"> 
                                            <form action="" method="">                                                                           
                                                <div class="form-group row">
                                                    <label class="col-xl-3 col-lg-3 col-form-label">Company Logo</label>
                                                    <div class="col-lg-9 col-xl-6">
                                                        <img alt="Company Logo" src="data:image/jpg;base64,${companyLogo}"  style="width:20vh;height:12vh;"/>                                                                                  
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Company Name</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <div class="kt-portlet__head-label">
                                                                    <h6 class="kt-portlet__head-title">
                                                                        ${clientProfile.companyName}
                                                                    </h6>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Postal Address</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <h6 class="kt-portlet__head-title">
                                                                    ${clientProfile.postalAddress}
                                                                </h6>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Company Phone</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <div class="input-group">
                                                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                                                    <h6 class="kt-portlet__head-title"> ${clientProfile.companyPhoneNumber}</h6> 
                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>                                                              
                                                    <!--
                                                    
                                                    
                                                    -->
                                                    <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">

                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Email Address</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <div class="input-group">
                                                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-at"></i></span></div>
                                                                    <h6 class="kt-portlet__head-title"> ${clientProfile.companyEmailAddress}</h6> 
                                                                </div>
                                                            </div>
                                                        </div> 
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Category</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <div class="input-group">
                                                                    <h6 class="kt-portlet__head-title"> ${clientProfile.category}</h6> 
                                                                </div>
                                                            </div>
                                                        </div> 
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Account Type</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <div class="input-group">
                                                                    <h6 class="kt-portlet__head-title"> ${clientProfile.accountType}</h6> 
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
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/delete-client-account.js?v=1001" />"   type="text/javascript"></script>
