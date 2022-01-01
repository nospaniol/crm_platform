<%@include file="administratorhead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet">
            <div class="kt-portlet__body  kt-portlet__body--fit">
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-md-12 col-lg-6 col-xl-3">
                        <div class="kt-login__wrapper">
                            <div class="kt-login__container">
                                <div class="kt-login__body">                              
                                    <div class="kt-login__signup">
                                        <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                                            <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                                <div class="kt-portlet__head kt-portlet__head--noborder">
                                                    <div class="kt-portlet__head-label">
                                                        <h3 class="kt-portlet__head-title">
                                                            CREATE ACCOUNT
                                                        </h3>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="kt-login__form">
                                            <form class="kt-form" action="" method="post">
                                                <div class="form-group">
                                                    <input  class="form-control" type="text" placeholder="First Name" name="firstName" id="fisrtName">
                                                </div>
                                                <div class="form-group">
                                                    <input  class="form-control" type="text" placeholder="Last Name" name="lastName" id="lastName">
                                                </div>
                                                <div class="form-group">
                                                    <input  class="form-control" type="text" placeholder="Phone Number" name="phoneNumber" id="phoneNumber">
                                                </div>
                                                <div class="form-group">
                                                    <input  class="form-control" type="text" placeholder="Email" name="emailAddress" id="email">
                                                </div>
                                                <div class="form-group">
                                                    <input  class="form-control" type="password" placeholder="Password" id="pass" name="password">
                                                </div>
                                                <div class="form-group">
                                                    <input  class="form-control form-control-last" type="password" placeholder="Confirm Password" id="rpassword" name="rpassword">
                                                </div>
                                                <div class="kt-login__actions">
                                                    <button  id="kt_login_signup_submit" class="btn btn-brand btn-pill btn-elevate">Save<i class="kt-menu__link-icon flaticon2-add kt-margin-r-15"></i></button>
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
<script src="<c:url value="/resources/assets/js/nshome/pages/login/staff-general.js?v=1001" />"  type="text/javascript"></script>
