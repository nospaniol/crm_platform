<%@include file="clientheader.jsp" %>
<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
    <div class="kt-portlet kt-portlet--mobile">
        <div class="kt-portlet__head kt-portlet__head--lg">
            <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                <div class="kt-portlet__head-label">
                    <h3 class="kt-portlet__head-title">
                        ALL VEHICLES
                    </h3>
                    <a href="<c:url value='http://127.0.0.1:800/vehicle/all/added/vehicles/' />">
                        <button  type="button" class="btn btn-primary"><i class="la la-refresh"></i></button>
                    </a>  
                </div>
            </div>
        </div>

        <div class="kt-portlet__body">

            <%
                int i = 0;

            %>
            <table class="table table-striped- table-bordered table-hover table-checkable" id="vehicle_info" >
                <thead class="thead-dark">                
                    <tr>
                        <th style="color:white;">ID</th>  
                        <th style="color:white;">License Plate</th>                        
                        <th style="color:white;">Model</th>
                        <th style="color:white;">Color</th>
                        <th style="color:white;">Make</th>
                        <th style="color:white;">State</th>
                        <th style="color:white;">Year</th>
                        <th style="color:white;">Vin</th>
                        <th style="color:white;">Type</th>
                        <th style="color:white;">Start Date</th>
                        <th style="color:white;">Actions</th>
                    </tr>
                </thead>
                <tbody id="data_info">
                    <c:choose>
                        <c:when test="${data.size() > 0 }">
                            <c:forEach var="vehicle" items="${data}">
                                <%                                    i = i + 1;
                                %>
                                <tr>
                                    <td>${vehicle.vehicleId}</td>
                                    <td> <i class="flaticon2-correct"></i> ${vehicle.licensePlate}</td>
                                    <td>${vehicle.model}</td>
                                    <td>${vehicle.color}</td>
                                    <td>${vehicle.make}</td>
                                    <td>${vehicle.state}</td>
                                    <td>${vehicle.year}</td>
                                    <td>${vehicle.vin}</td>
                                    <td>${vehicle.type}</td>
                                    <td>${vehicle.startDate}</td>
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
                                <td>No Data</td>
                                <td>No Data</td>
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

<%@include file="dashtablefooter.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-client-vehicle.js?v=1001" />"   type="text/javascript"></script>

