<%@include file="administratorhead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content" style="padding-top:1vh">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
        <div class="row">
            <div class="col-xl-3 col-lg-3 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <div class="kt-widget14">
                        <div class="kt-widget14__header">
                            <h3 class="kt-widget14__title">
                                Vehicle Count
                            </h3>
                            <span class="kt-widget14__desc">
                                Total vehicles added
                            </span>
                        </div>
                        <div class="kt-widget14__content">
                            <h3><a href="<c:url value="/vehicle/view_vehicle" />">
                                    <p id="innovative_vehicle"> ${vehicleCount} </p>                                           
                                    <i style="font-size:10vh;" class="kt-menu__link-icon flaticon-car"></i>
                                </a> 
                            </h3>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-xl-3 col-lg-3 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <div class="kt-widget14">
                        <div class="kt-widget14__header">
                            <h3 class="kt-widget14__title">
                                Client Count
                            </h3>
                            <span class="kt-widget14__desc">
                                Total clients added
                            </span>
                        </div>
                        <div class="kt-widget14__content">
                            <h3>  <a href="<c:url value="/administrator/view_client" />">
                                    <p id="innovative_client"> ${clientCount}</p>
                                    <i style="font-size:10vh;" class="kt-menu__link-icon flaticon-users"></i>&nbsp;
                                </a> 
                            </h3> 
                        </div>
                    </div>
                </div>

                <!--end:: Widgets/Profit Share-->
            </div>

            <div class="col-xl-3 col-lg-3 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <div class="kt-widget14">
                        <div class="kt-widget14__header">
                            <h3 class="kt-widget14__title">
                                Transaction Count
                            </h3>
                            <span class="kt-widget14__desc">
                                Today total transactions
                            </span>
                        </div>
                        <div class="kt-widget14__content">
                            <h3> 
                                <a href="<c:url value="/transaction/view_today_transaction" />">
                                    <p id="innovative_transaction"> ${transactionCount}</p>
                                    <i style="font-size:10vh;" class="kt-menu__link-icon flaticon-coins"></i>&nbsp;
                                </a> 
                            </h3>
                        </div>
                    </div>
                </div>

                <!--end:: Widgets/Profit Share-->
            </div>

            <div class="col-xl-3 col-lg-3 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <div class="kt-widget14">
                        <div class="kt-widget14__header">
                            <h3 class="kt-widget14__title">
                                Total Toll Spending Amount
                            </h3>
                            <span class="kt-widget14__desc">
                                Current month summary
                            </span>
                        </div>
                        <div class="kt-widget14__content">
                            <h3> 
                                <a href="<c:url value="/tollspending/monthly" />">
                                    <p id="innovative_spending"> ${tollCount}</p>
                                    <i style="font-size:10vh;" class="text-primary kt-menu__link-icon flaticon-piggy-bank"></i>&nbsp;
                                </a> 
                            </h3>
                        </div>
                    </div>
                </div>

                <!--end:: Widgets/Profit Share-->
            </div>

        </div>

    </div>
</div>
<%@include file="administratorfoot.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/dashboard-data.js?v=1001" />"   type="text/javascript"></script>

</body>

</html>