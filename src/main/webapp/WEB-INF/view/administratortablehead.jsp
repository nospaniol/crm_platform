<%@include file="dashheader.jsp" %>
<body class="kt-quick-panel--right kt-demo-panel--right kt-offcanvas-panel--right kt-header--fixed kt-header-mobile--fixed kt-subheader--enabled kt-subheader--solid kt-aside--enabled kt-aside--fixed kt-aside--minimize kt-page--loading">
    <!-- begin:: Page -->
    <!-- <div class="se-pre-con"></div> -->
    <!-- begin:: Header Mobile -->
    <%@include file="adminmobileheader.jsp" %>
    <!-- end:: Header Mobile -->

    <div class="kt-grid kt-grid--hor kt-grid--root">
        <div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--ver kt-page">
            <!-- begin:: Aside -->

            <%@include file="adminsidemenu.jsp" %>
            <!-- end:: Aside -->
            <div class="kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor kt-wrapper" id="kt_wrapper">

                <!-- begin:: Header -->
                <div id="kt_header" class="kt-header kt-grid kt-grid--ver  kt-header--fixed ">

                    <!-- begin:: Aside -->
                    <div class="kt-header__brand kt-grid__item  " id="kt_header_brand" style="background-color:white;">
                        <div class="kt-header__brand-logo">
                            <a href="http://127.0.0.1:800/administrator/dashboard">
                                <img id="client-logo" alt="INNOVATIVE TOLL"  src="<c:url value="/resources/images/favicon/android-icon-72x72.png?v=1001" />" />
                            </a>
                        </div>
                    </div>

                    <!-- end:: Aside -->

                    <!-- begin:: Title -->
                    <h3 class="kt-header__title kt-grid__item">
                        ${institution}
                    </h3>

                    <!-- end:: Title -->

                    <!-- begin: Header Menu -->
                    <%@include file="adminheadermenu.jsp" %>
                    <!-- end: Header Menu -->

                    <!-- begin:: Header Topbar -->
                    <div class="kt-header__topbar">
                        <!--begin: User bar -->
                        <%@include file="adminuserbar.jsp" %>                        <!--end: User bar -->

                        <!--end: Quick panel toggler -->
                    </div>

                    <!-- end:: Header Topbar -->
                </div>

                <!-- end:: Header -->
