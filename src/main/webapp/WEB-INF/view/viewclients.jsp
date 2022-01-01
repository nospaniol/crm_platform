<%@include file="administratortablehead.jsp" %>

<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content" style="padding-top:.5vh">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
        <div class="row">                                            
            <div class="col-xl-12 col-lg-12 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <table class="table table-striped- table-bordered table-hover table-checkable" id="client_info" >
                        <thead class="thead-dark">
                            <tr>
                                <th style="color:white" >Client ID</th>  
                                <th style="color:white" >Company Name</th>                        
                                <th style="color:white" >Company Email Address</th>
                                <th style="color:white" >Company Phone Number</th>
                                <th style="color:white" >Action</th>
                            </tr>
                        </thead>
                        <tbody id="data_info">
                            <c:choose>
                                <c:when test="${data.size() > 0 }">
                                    <c:forEach var="clientProfile" items="${data}">
                                        <tr>
                                            <td>${clientProfile.clientProfileId}</td>
                                            <td> <i class="flaticon2-correct"></i> ${clientProfile.companyName} </td>                            
                                            <td>${clientProfile.companyEmailAddress}</td>
                                            <td>${clientProfile.companyPhoneNumber}</td>
                                            <td nowrap></td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td>No Data</td>
                                        <td>No Data</td>
                                        <td>No Data</td>
                                        <td>No Data</td>
                                        <td nowrap></td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>

                        </tbody>
                    </table>
                </div> 
            </div> 
        </div> 
    </div>       
</div>

<%@include file="dashtablefooter.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/delete-user.js?v=1001" />"   type="text/javascript"></script>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-clients.js?v=1001" />"   type="text/javascript"></script>
<script>
    $(document).ready(function () {
        $("input[type='search']").wrap("<form>");
        $("input[type='search']").closest("form").attr("autocomplete", "off");
    });
</script>
</body>
</html>