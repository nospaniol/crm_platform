
<%
    String ns_id = (String) session.getAttribute("NsId");
    String ns_mail = (String) session.getAttribute("ns_mail_address");
    String ns_first_name = (String) session.getAttribute("NsfirstName");
    String ns_last_name = (String) session.getAttribute("NslastName");
    String ns_user = (String) session.getAttribute("ns_user");

%>


<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="${institution}">
<meta name="author" content="${institution}">
<title>${institution}</title>
<meta name="description" content="${institution}">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link href="<c:url value="/resources/assets/css/nsdash/style.bundle.css?v=1001" />"  rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/assets/vendors/general/select2/dist/css/select2.css?v=1001" />"  rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/assets/vendors/general/ion-rangeslider/css/ion.rangeSlider.css?v=1001" />"  rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/assets/vendors/general/nouislider/distribute/nouislider.css?v=1001" />"  rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/assets/vendors/general/bootstrap-markdown/css/bootstrap-markdown.min.css?v=1001" />"  rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/assets/vendors/general/animate.css/animate.css?v=1001" />"  rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/assets/vendors/general/toastr/build/toastr.css?v=1001" />"  rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/assets/vendors/general/morris.js/morris.css?v=1001" />"  rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/assets/vendors/general/sweetalert2/dist/sweetalert2.css?v=1001" />"  rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/SocialIcons/1.0.1/soc.min.css?v=1001" integrity="sha512-PTz/4lAo890ortUEd041dNdebPVxpjxZiTTgW8DXUTIiPZQGXFua9U7izCygP7NqHUDmaDF4F1CswmblvYq4Vw==" crossorigin="anonymous" />
<!--link href="<c:url value="/resources/assets/vendors/general/socicon/css/socicon.css?v=1001" />"  rel="stylesheet" type="text/css" /-->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css?v=1001" integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w==" crossorigin="anonymous" />
<link href="<c:url value="/resources/assets/vendors/custom/vendors/line-awesome/css/line-awesome.css?v=1001" />"  rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/assets/vendors/custom/vendors/flaticon/flaticon.css?v=1001" />"  rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/assets/vendors/custom/vendors/flaticon2/flaticon.css?v=1001" />"  rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/assets/vendors/custom/datatables/datatables.bundle.css?v=1001" />"  rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="<c:url value="/resources/images/favicon/favicon.ico?v=1001" />"  />

