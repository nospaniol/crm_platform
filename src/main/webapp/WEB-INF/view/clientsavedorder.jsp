<%@include file="clientheader.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content" style="padding-top:.2vh">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">

        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-lg-6 col-xl-6 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                    <div class="kt-portlet__head kt-portlet__head--noborder">
                        <div class="kt-portlet__head-label">
                            <a class="kt-menu__link" href="<c:url value='http://127.0.0.1:800/client_transponder/make_order' />">
                                <button style="font-size:12px;" type="button"  style="color:black;font-size:12px;" class="btn btn-brand btn-pill btn-elevate">
                                    <i class="flaticon-add-circular-button"></i>New Order</button>
                            </a> 
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-xl-12 col-lg-12 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">

                    <table class="table table-striped- table-bordered table-hover table-checkable" id="order_info" >
                        <thead class="thead-dark">
                            <tr>
                                <th style="color:white;font-size:12px;">Order Id</th>  
                                <th style="color:white;font-size:12px;">Status</th>  
                                <th style="color:white;font-size:12px;">Transponder Quantity</th>  
                                <th style="color:white;font-size:12px;">Details</th>   
                                    <c:choose>
                                        <c:when test="${institution=='AMAZON'||parent_institution=='AMAZON'}">                        
                                        <th style="color:white;font-size:12px;">Asset</th>  
                                        <th style="color:white;font-size:12px;">Domicile Terminal</th> 
                                        </c:when>
                                    </c:choose>  
                                    <c:choose>
                                        <c:when test="${institution=='PROTECH AS'||parent_institution=='PROTECH AS'}">  
                                        <th style="color:white;font-size:12px;">License Plate</th> 
                                        <th style="color:white;font-size:12px;">State</th>                
                                        <th style="color:white;font-size:12px;">Department</th>
                                        </c:when>
                                    </c:choose>  
                                <th style="color:white;font-size:12px;">Shipping Address</th>
                                <th style="color:white;font-size:12px;">Actions</th>
                            </tr>
                        </thead>
                        <tbody id="data_info">
                            <c:choose>
                                <c:when test="${data.size() > 0 }">
                                    <c:forEach var="transponder" items="${data}">                            
                                        <tr>   
                                            <td style="font-size:12px;">${transponder.orderId}</td>                                      
                                            <td style="font-size:12px;">${transponder.orderStatus}</td>                          
                                            <td style="font-size:12px;">${transponder.transponderQuantity}</td>
                                            <td style="font-size:12px;">${transponder.instructions}</td>
                                            <c:choose>
                                                <c:when test="${institution=='AMAZON'||parent_institution=='AMAZON'}">  
                                                    <td style="font-size:12px;">${transponder.assetName}</td>
                                                    <td style="font-size:12px;">${transponder.domicileTerminal}</td>
                                                </c:when>
                                            </c:choose>     
                                            <c:choose>
                                                <c:when test="${institution=='PROTECH AS'||parent_institution=='PROTECH AS'}">  
                                                    <td style="font-size:12px;">${transponder.licensePlate}</td>
                                                    <td style="font-size:12px;">${transponder.state}</td>
                                                    <td style="font-size:12px;">${transponder.department.departmentName}</td>
                                                </c:when>
                                            </c:choose>  
                                            <td style="font-size:12px;">${transponder.shippingAddress}</td>
                                            <td style="font-size:12px;">
                                                <a title="View Details" href="http://127.0.0.1:800/client_transponder/view/saved/info/${transponder.orderId}" class="btn btn-sm btn-clean btn-icon btn-icon-md"> 
                                                    <i class="fas fa-eye"></i>
                                                </a> 
                                            </td>
                                        </tr>
                                    </c:forEach>                                   
                                </c:when>
                                <c:otherwise>
                                    <tr>   
                                        <td>No Data</td>
                                        <td>No Data</td>
                                        <td>No Data</td> 
                                        <td>No Data</td>
                                        <c:choose>
                                            <c:when test="${institution=='AMAZON'||parent_institution=='AMAZON'}">  
                                                <td>No Data</td> 
                                                <td>No Data</td>
                                            </c:when>
                                        </c:choose>     
                                        <c:choose>
                                            <c:when test="${institution=='PROTECH AS'||parent_institution=='PROTECH AS'}">  
                                                <td>No Data</td>
                                                <td>No Data</td>
                                                <td>No Data</td>
                                            </c:when>
                                        </c:choose>  
                                        <td>No Data</td>
                                        <td>No Data</td>
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
<%@include file="clientdashtablefooter.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-client-order.js?v=1001" />"   type="text/javascript"></script>

<script>
    $(document).ready(function () {
        $("input[type='search']").wrap("<form>");
        $("input[type='search']").closest("form").attr("autocomplete", "off");
    });
</script>
</body>
</html>