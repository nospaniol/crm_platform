<div class="kt-login__wrapper">
    <div class="kt-login__container">
        <div class="kt-login__body">
            <div class="kt-login__logo">
                <a href="#">
                    <img src="<c:url value="/resources/assets/media/company-logos/logo-2.png?v=1001" />" style="width:25vh;height:20vh;">
                </a>
            </div>
            <div class="kt-login__signin">
                <div class="kt-login__head">
                    <h3 class="kt-login__title"></h3>
                    <%@include file="alert.jsp" %>
                </div>
                <div class="kt-login__form">
                    <c:url var="loginUrl" value="/user/validate" />
                    <form class="kt-form" action="${loginUrl}" method="post">
                        <div class="form-group">
                            <input  class="form-control" type="text" placeholder="Email" id="emailAddress" name="emailAddress">
                        </div>
                        <div class="form-group">
                            <input  class="form-control form-control-last" type="password" placeholder="Password" id="password" name="password">
                        </div>
                        <div class="kt-login__extra">
                            <label class="kt-checkbox">
                                <input  type="checkbox" name="remember"> Remember me
                                <span></span>
                            </label>
                            <a href="javascript:;" id="kt_login_forgot">Forget Password ?</a>
                        </div>
                        <div class="kt-login__actions">
                            <button  id="kt_login_signin_submit" class="btn btn-brand btn-pill btn-elevate">Sign In</button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="kt-login__signup">
                <c:url var="userRegisterUrl" value="/user/register" />
                <div class="kt-login__head">
                    <div class="kt-login__desc">Enter your details to create your account:</div>
                </div>
                <div class="kt-login__form">
                    <form class="kt-form" action="${userRegisterUrl}" method="post">
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
                            <input  class="form-control" type="text" placeholder="Company Name" name="companyName" id="companyName">
                        </div>
                        <div class="form-group">
                            <input  class="form-control" type="text" placeholder="Company Title" name="companyTitle" id="companyTitle">
                        </div>                        
                        <div class="kt-login__actions">
                            <button  id="kt_login_signup_submit" class="btn btn-brand btn-pill btn-elevate">Sign Up</button>
                            <button  id="kt_login_signup_cancel" class="btn btn-outline-brand btn-pill">Cancel</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="kt-login__account">
        <span class="kt-login__account-msg">
            Don't have an account yet ?
        </span>&nbsp;&nbsp;
        <a href="javascript:;" id="kt_login_signup" class="kt-login__account-link">Sign Up!</a>
    </div>
</div>