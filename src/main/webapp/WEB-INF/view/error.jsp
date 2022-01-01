<%@include file="header.jsp" %>
<body class="kt-quick-panel--right kt-demo-panel--right kt-offcanvas-panel--right kt-header--fixed kt-header-mobile--fixed kt-subheader--enabled kt-subheader--fixed kt-subheader--solid kt-aside--enabled kt-aside--fixed kt-page--loading"> <!-- <div class="se-pre-con"></div> -->
    <style>
        .kt-error-v1 {
            background-position: center;
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: cover; }
        .kt-error-v1 .kt-error-v1__container .kt-error-v1__number {
            font-size: 150px;
            margin-left: 80px;
            margin-top: 9rem;
            font-weight: 700;
            color: #595d6e; }
        .kt-error-v1 .kt-error-v1__container .kt-error-v1__desc {
            font-size: 1.5rem;
            margin-left: 80px;
            color: #74788d; }

        @media (max-width: 768px) {
            .kt-error-v1 .kt-error-v1__container .kt-error-v1__number {
                margin: 120px 0 0 3rem;
                font-size: 8rem; }
            .kt-error-v1 .kt-error-v1__container .kt-error-v1__desc {
                margin-left: 3rem;
                padding-right: 0.5rem; } }

    </style>
    <div class="kt-grid kt-grid--ver kt-grid--root">
        <div class="kt-grid__item kt-grid__item--fluid kt-grid  kt-error-v1" style="background-image: url(<c:url value="/resources/assets/media/error/bg1.jpg" />);">
            <div class="kt-error-v1__container">
                <h1 class="kt-error-v1__number">404</h1>
                <p class="kt-error-v1__desc">
                    OOPS! Something went wrong here<br><br>
                    ${message}
                </p>
            </div>
        </div>
    </div>
    <%@include file="footer.jsp" %>
</body>