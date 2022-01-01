<%@include file="clientheader.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet">
            <div class="kt-portlet__body  kt-portlet__body--fit">

                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                        <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                            <div class="kt-portlet__head kt-portlet__head--noborder">
                                <div class="kt-portlet__head-label">
                                    <h3 class="kt-portlet__head-title">
                                        FULFILLMENT INFO
                                    </h3>
                                    <%@include file="alert.jsp" %>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <c:if test="${transponder.orderStatus=='processing'}"> 
                    <div class="row row-no-padding row-col-separator-xl">
                        <div class="col-lg-12 col-xl-12 order-lg-2 order-xl-1">
                            <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                <div class="kt-portlet__head kt-portlet__head--noborder">
                                    <div class="kt-portlet__head-label">
                                        <img src="<c:url value="/resources/images/order/stage2.png?v=1001" />" style="width:100%;height:12vh;">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${transponder.orderStatus=='shipping'}"> 
                    <div class="row row-no-padding row-col-separator-xl">
                        <div class="col-lg-12 col-xl-12 order-lg-2 order-xl-1">
                            <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                <div class="kt-portlet__head kt-portlet__head--noborder">
                                    <div class="kt-portlet__head-label">
                                        <img src="<c:url value="/resources/images/order/stage3.png?v=1001" />" style="width:100%;height:12vh;">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${transponder.orderStatus=='shipped'}"> 
                    <div class="row row-no-padding row-col-separator-xl">
                        <div class="col-lg-12 col-xl-12 order-lg-2 order-xl-1">
                            <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                <div class="kt-portlet__head kt-portlet__head--noborder">
                                    <div class="kt-portlet__head-label">
                                        <img src="<c:url value="/resources/images/order/stage4.png?v=1001" />" style="width:100%;height:12vh;">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${transponder.orderStatus=='delivered'}"> 
                    <div class="row row-no-padding row-col-separator-xl">
                        <div class="col-lg-12 col-xl-12 order-lg-2 order-xl-1">
                            <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                <div class="kt-portlet__head kt-portlet__head--noborder">
                                    <div class="kt-portlet__head-label">
                                        <img src="<c:url value="/resources/images/order/stage5.png?v=1001" />" style="width:100%;height:12vh;">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${transponder.orderStatus=='cancelled'}"> 
                    <div class="row row-no-padding row-col-separator-xl">
                        <div class="col-lg-12 col-xl-12 order-lg-2 order-xl-1">
                            <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                <div class="kt-portlet__head kt-portlet__head--noborder">
                                    <div class="kt-portlet__head-label">
                                        <img src="<c:url value="/resources/images/order/cancelled.png?v=1001" />" style="width:100%;height:12vh;">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-lg-3 col-xl-3 order-lg-2 order-xl-1">
                        <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                            <div class="kt-portlet__head kt-portlet__head--noborder">
                                <div class="kt-portlet__head-label">
                                    <a class="kt-menu__link" href="<c:url value='http://127.0.0.1:800/client_transponder/view_order' />">
                                        <button style="font-size:12px;" type="button"  style="color:black;font-size:12px;" class="btn btn-brand btn-pill btn-elevate">
                                            <i class="flaticon-add-circular-button"></i>View Orders</button>
                                    </a> 
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-xl-3 order-lg-2 order-xl-1">
                        <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                            <div class="kt-portlet__head kt-portlet__head--noborder">
                                <div class="kt-portlet__head-label">
                                    <a class="kt-menu__link" href="<c:url value='http://127.0.0.1:800/client_transponder/make_order' />">
                                        <button style="font-size:12px;" type="button"  style="color:black;font-size:12px;" class="btn btn-dark btn-pill btn-elevate">
                                            <i class="flaticon-add-circular-button"></i>New Order</button>
                                    </a> 
                                </div>
                            </div>
                        </div>
                    </div>
                     <c:if test="${transponder.orderStatus=='processing'}">   
                    <div class="col-lg-3 col-xl-3 order-lg-2 order-xl-1">
                        <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                            <div class="kt-portlet__head kt-portlet__head--noborder">
                                <div class="kt-portlet__head-label">
                                    <a class="kt-menu__link" href="<c:url value='http://127.0.0.1:800/client_transponder/edit/${transponder.orderId}' />">
                                        <button style="font-size:12px;" type="button"  style="color:black;font-size:12px;" class="btn btn-brand btn-pill btn-elevate">
                                            <i class="flaticon-add-circular-button"></i>Edit Order</button>
                                    </a> 
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-xl-3 order-lg-2 order-xl-1">
                        <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                            <div class="kt-portlet__head kt-portlet__head--noborder">
                                <div class="kt-portlet__head-label">
                                    <a class="kt-menu__link" href="<c:url value='http://127.0.0.1:800/client_transponder/cancel/${transponder.orderId}' />">
                                        <button style="font-size:12px;" type="button"  style="color:black;font-size:12px;" class="btn btn-dark btn-pill btn-elevate">
                                            <i class="flaticon-add-circular-button"></i>Cancel Order</button>
                                    </a> 
                                </div>
                            </div>
                        </div>
                    </div>
                     </c:if>
                </div>
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                        <form action="" method="">
                            <div class="kt-form kt-form--label-right">
                                <div class="kt-form__body">
                                    <div class="kt-section kt-section--first">
                                        <div class="kt-section__body">                                                                            


                                            <div class="row">
                                                <!-- company info -->
                                                <div class="col-xl-5 col-lg-5 order-lg-1 order-xl-1 border-dark">
                                                    <div>
                                                        <label class="form-control">Transponder Quantity</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h3 class="kt-portlet__head-title">
                                                                ${transponder.transponderQuantity}
                                                            </h3>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <label class="form-control">Extra Velcro</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="kt-portlet__head-label">
                                                                <h3 class="kt-portlet__head-title">
                                                                    ${transponder.extraVelcro}
                                                                </h3>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <c:choose>
                                                        <c:when test="${institution=='AMAZON'||parent_institution=='AMAZON'}">      
                                                            <div>
                                                                <label class="form-control">Asset Name</label>
                                                                <div class="col-lg-9 col-xl-6">
                                                                    <h3 class="kt-portlet__head-title">
                                                                        ${transponder.assetName}
                                                                    </h3>
                                                                </div>
                                                            </div>
                                                            <div>
                                                                <label class="form-control">Domicile Terminal</label>
                                                                <div class="col-lg-9 col-xl-6">
                                                                    <div class="input-group">
                                                                        <h3 class="kt-portlet__head-title"> ${transponder.domicileTerminal}</h3> 
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </c:when>
                                                    </c:choose>  


                                                    <c:choose>
                                                        <c:when test="${institution=='PROTECH AS'||parent_institution=='PROTECH AS'}"> 
                                                            <div>
                                                                <label class="form-control">License Plate</label>
                                                                <div class="col-lg-9 col-xl-6">
                                                                    <div class="input-group">

                                                                        <h3 class="kt-portlet__head-title"> ${transponder.licensePlate}</h3> 
                                                                    </div>
                                                                </div>
                                                            </div> 
                                                            <div>
                                                                <label class="form-control">Customer Vehicle Id</label>
                                                                <div class="col-lg-9 col-xl-6">
                                                                    <div class="input-group">
                                                                        <h3 class="kt-portlet__head-title"> ${transponder.customerVehicleId}</h3> 
                                                                    </div>
                                                                </div>
                                                            </div> 
                                                            <div>
                                                                <label class="form-control">State</label>
                                                                <div class="col-lg-9 col-xl-6">
                                                                    <div class="input-group">
                                                                        <h3 class="kt-portlet__head-title"> ${transponder.state}</h3> 
                                                                    </div>
                                                                </div>
                                                            </div> 
                                                        </c:when>
                                                    </c:choose>  
                                                </div>
                                                <div class="col-xl-5 col-lg-5 order-lg-1 order-xl-2">
                                                    <c:if test="${!empty transponder.instructions}">  
                                                        <div>
                                                            <label class="form-control">Instructions</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <h3 class="kt-portlet__head-title">
                                                                    ${transponder.instructions}
                                                                </h3>
                                                            </div>
                                                        </div>
                                                    </c:if>
                                                    <div>
                                                        <label class="form-control">Shipping Address</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h3 class="kt-portlet__head-title">
                                                                ${transponder.shippingAddress}
                                                            </h3>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <label class="form-control">Status</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="input-group">

                                                                <h3 class="kt-portlet__head-title"> ${transponder.orderStatus}</h3> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
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
<%@include file="clientdashtablefooter.jsp" %>
