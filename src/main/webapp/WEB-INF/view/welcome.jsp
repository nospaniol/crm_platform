<%@include file="header.jsp" %>
<body  style ="font-family:arial" class="kt-quick-panel--right kt-demo-panel--right kt-offcanvas-panel--right kt-header--fixed kt-header-mobile--fixed kt-subheader--enabled kt-subheader--fixed kt-subheader--solid kt-aside--enabled kt-aside--fixed kt-page--loading"> <!-- <div class="se-pre-con"></div> --> <!-- <div class="se-pre-con"></div> -->

    <% 
        session.removeAttribute("ns_mail_address");
        session.removeAttribute("NsfirstName");
        session.invalidate();
    %>	
    <div class="kt-grid kt-grid--ver kt-grid--root">
        <div class="kt-grid kt-grid--hor kt-grid--root  kt-login kt-login--v6 kt-login--signin" id="kt_login">
            <div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--desktop kt-grid--ver-desktop kt-grid--hor-tablet-and-mobile">
                <div class="kt-grid__item  kt-grid__item--order-tablet-and-mobile-2  kt-grid kt-grid--hor kt-login__aside">
                    <%@include file="loginform.jsp" %>
                </div>
                <div class="kt-grid__item kt-grid__item--fluid kt-grid__item--center kt-grid kt-grid--ver kt-login__content" style="background-image: url('https://res.cloudinary.com/nospaniol/image/upload/v1602399875/bg-4_lpzcxn.png');">
                    <div class="kt-login__section">
                        <div class="kt-login__block">
                            <h3 class="kt-login__title">INNOVATIVE TOLL</h3>
                            <div class="kt-login__desc text-white font-weight-bolder">
                                Form a relationship with a great toll management solution provider
                                <br> and set yourself for success in the changing Tolling space 
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="footer.jsp" %>
    <script src="<c:url value="/resources/assets/js/nshome/pages/login/login-general.js?v=1001" />"  type="text/javascript"></script>
</body>
</html>