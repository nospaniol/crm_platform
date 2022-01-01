<%@include file="clientheader.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content" style="padding-top:.2vh">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
        <div class="row row-no-padding row-col-separator-xl">  
            <div class="col-xl-8 col-lg-8 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <div class="row">
                        <div clsss="col-6" style="padding-left:2vh; padding-top:2vh;">
                            <div class="kt-portlet__head-label">                          
                                <button id="excel-button" style="font-size:8px;" onclick="printExcel()" type="button" class="btn btn-dark">
                                    <i class="la la-file-excel-o"></i>EXPORT EXCEL
                                </button>
                            </div>
                        </div>
                        <div clsss="col-6" style="padding-left:2vh; padding-top:2vh;">
                            <div class="kt-portlet__head-label">
                                <button id="pdf-button" style="font-size:8px;" onclick="printPdf()" type="button" class="btn btn-brand">
                                    <i class="la la-file-pdf-o"></i>PRINT PDF
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-4 col-lg-4 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <div class="kt-portlet__head kt-portlet__head--lg">
                        <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1" style="padding-left:2vh; padding-top:2vh;">
                            <div class="kt-portlet__head-label">
                                <a class="kt-menu__link" href="<c:url value='http://127.0.0.1:800/client_vehicle/file/upload' />">
                                    <button style="font-size:8px;" type="button"  style="color:black;font-size:12px;" class="btn btn-brand"><i class="flaticon-upload-1"></i> Fleet List Upload</button>
                                </a>  
                            </div>
                        </div>
                        <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1" style="padding-left:2vh; padding-top:2vh;">
                            <div class="kt-portlet__head-label">
                                <a class="kt-menu__link" href="<c:url value='http://127.0.0.1:800/client_vehicle/register/new/vehicle/' />">
                                    <button style="font-size:8px;" type="button"  style="color:black;font-size:12px;" class="btn btn-dark"><i class="flaticon-upload-1"></i> Add Vehicle</button>
                                </a>  
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                                    
        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-xl-5 col-lg-5 order-lg-2 order-xl-1">
                <div class="input-group">
                    <div class="input-group-prepend"><span class="input-group-text"><i class="flaticon2-search-1"></i></span></div>
                    <input  type="text" class="form-control kt-quick-search__input" id="search_vehicle" placeholder="Search LP / Tolltag / VIN"  readonly onfocus="this.removeAttribute('readonly');">
                    <div class="input-group-append"><span class="input-group-text"><i class="la la-close kt-quick-search__close"></i></span></div>
                    <button id="kt_search_submit" class="btn btn-brand btn-pill btn-elevate">searching</button>
                    <button id="kt_clear_submit" style="font-size:8px;" type="button"  style="color:black;font-size:12px;" class="btn btn-pill btn-brand" onclick="clearSearch()"><i class="flaticon-close"></i>Clear</button>
                </div>
            </div> 
            <div class="col-xl-5 col-lg-5 order-lg-2 order-xl-1"></div>
        </div>
        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-xl-12 col-lg-12 order-lg-2 order-xl-1">
                <table class="table table-striped- table-bordered table-hover table-checkable" id="vehicle_search_info" >
                    <thead class="thead">
                        <tr> 
                            <%
                                if (ns_company.contains("PROTECH") || ns_company.contains("SHERMCO") || ns_company.contains("CALIBER")) {
                            %> 
                            <th style="color:black;font-size:12px;">Cost Center</th>  
                                <%
                                } else {
                                %>
                            <th style="color:black;font-size:12px;">#ID</th>   
                                <%
                                    }
                                %>
                            <th style="color:black;font-size:12px;">State</th>   
                            <th style="color:black;font-size:12px;">License Plate</th>                         
                            <th style="color:black;font-size:12px;">Toll tag</th>  
                                <%
                                    if (ns_company.contains("PROTECH") || ns_company.contains("SHERMCO")) {
                                %> 
                            <th style="color:black;font-size:12px;">Customer Vehicle Id</th>  
                                <%
                                } else {
                                %>
                            <th style="color:black;font-size:12px;">Unit</th>   
                                <%
                                    }
                                %>
                            <th style="color:black;font-size:12px;">VIN</th> 
                            <th style="color:black;font-size:12px;">Make</th>                
                            <th style="color:black;font-size:12px;">Model</th>
                            <th style="color:black;font-size:12px;">Color</th>
                            <th style="color:black;font-size:12px;">Start Date</th>
                            <th style="color:black;font-size:12px;">Actions</th>
                        </tr>
                    </thead>
                    <tbody id="data_search_info">

                    </tbody>
                </table>
            </div>
        </div>
        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-xl-12 col-lg-12 order-lg-2 order-xl-1">
                <table class="table table-striped- table-bordered table-hover table-checkable" id="vehicle_info" >
                    <thead class="thead-dark">
                        <tr> 

                            <%
                                if (ns_company.contains("PROTECH") || ns_company.contains("SHERMCO") || ns_company.contains("CALIBER")) {
                            %> 
                            <th style="color:white;font-size:12px;">Cost Center</th>  
                                <%
                                } else {
                                %>
                            <th style="color:white;font-size:12px;">#ID</th>   
                                <%
                                    }
                                %>
                            <th style="color:white;font-size:12px;">State</th>   
                            <th style="color:white;font-size:12px;">License Plate</th>                         
                            <th style="color:white;font-size:12px;">Toll tag</th>  
                                <%
                                    if (ns_company.contains("PROTECH") || ns_company.contains("SHERMCO")) {
                                %> 
                            <th style="color:white;font-size:12px;">Customer Vehicle Id</th>  
                                <%
                                } else {
                                %>
                            <th style="color:white;font-size:12px;">Unit</th>   
                                <%
                                    }
                                %>
                            <th style="color:white;font-size:12px;">VIN</th> 
                            <th style="color:white;font-size:12px;">Make</th>                
                            <th style="color:white;font-size:12px;">Model</th>
                            <th style="color:white;font-size:12px;">Color</th>
                            <th style="color:white;font-size:12px;">Start Date</th>
                            <th style="color:white;font-size:12px;">Actions</th>
                        </tr>
                    </thead>
                    <tbody id="data_info">
                        <c:choose>
                            <c:when test="${data.size() > 0 }">
                                <c:forEach var="vehicle" items="${data}">                            
                                    <tr>     
                                        <%
                                            if (ns_company.contains("PROTECH") || ns_company.contains("SHERMCO") || ns_company.contains("CALIBER")) {
                                        %> 
                                        <td style="font-size:12px;">${vehicle.department.departmentName}</td>     
                                        <%
                                        } else {
                                        %>
                                        <td style="font-size:12px;">${vehicle.vehicleId}</td>    
                                        <%
                                            }
                                        %>                                    
                                        <td style="font-size:12px;">${vehicle.state}</td>
                                        <td style="font-size:12px;">${vehicle.licensePlate}</td>
                                        <td style="font-size:12px;">${vehicle.tollTagId}</td>
                                        <td style="font-size:12px;">${vehicle.unit}</td>
                                        <td style="font-size:12px;">${vehicle.vin}</td>
                                        <td style="font-size:12px;">${vehicle.make}</td>
                                        <td style="font-size:12px;">${vehicle.model}</td>
                                        <td style="font-size:12px;">${vehicle.color}</td>
                                        <td style="font-size:12px;">${vehicle.startDate}</td>
                                        <td style="font-size:12px;">
                                            <a title="View Details" href="http://127.0.0.1:800/client_vehicle/view/vehicle/info/${vehicle.vehicleId}" class="btn btn-sm btn-clean btn-icon btn-icon-md"> 
                                                <i class="fas fa-eye"></i>
                                            </a> 
                                            <a href="http://127.0.0.1:800/client_vehicle/edit/vehicle/${vehicle.vehicleId}" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Edit Vehicle"> 
                                                <i class="fas fa-edit"></i>
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

        <c:if test="${data.size() > 0 }">
            <nav aria-label="Pages">
                <ul class="pagination pagination-sm  flex-wrap">
                    <c:forEach begin="0" end="${totalPages}" var="page">                             
                        <li class="page-item"><a class="page-link" href="<c:url value='http://127.0.0.1:800/client_vehicle/view_vehicle?page=${page}&size=${size}' />">${page+1}</a></li>
                        </c:forEach>
                </ul>
            </nav>
        </c:if> 
    </div> 
</div>   

<%@include file="clientdashtablefooter.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-client-vehicle.js?v=1001" />"   type="text/javascript"></script>


<script>

                        $(document).ready(function () {
                            $("input[type='search']").wrap("<form>");
                            $("input[type='search']").closest("form").attr("autocomplete", "off");
                        });


                        const swalWithBootstrapButtons = Swal.mixin({
                            customClass: {
                                confirmButton: 'btn btn-brand',
                                cancelButton: 'btn btn-dark'
                            },
                            buttonsStyling: false
                        });
                        $("#vehicle_search_info").hide();
                        $("#kt_search_submit").hide();
                        $("#kt_clear_submit").hide();
                        function searchVehicle() {
                            $("#vehicle_search_info").hide();
                            var searchItem = $("#search_vehicle").val();
                            if (searchItem !== "") {
                                $("#data_search_info").empty();
                                $("#kt_search_submit").show();
                                var searchItem = $("#search_vehicle").val();
                                $("#kt_search_submit").addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
                                $.ajax({
                                    type: "POST",
                                    data: {
                                        searchItem: searchItem
                                    },
                                    url: 'http://127.0.0.1:800/client_vehicle/search/vehicle/',
                                    success: function (res) {
                                        var response = res;
                                        var title = response["title"];
                                        var message = response["message"];
                                        if (title === "fail") {
                                            swalWithBootstrapButtons.fire(
                                                    'Innovative Toll Solution',
                                                    message,
                                                    'error'
                                                    );
                                            $("#kt_search_submit").hide();
                                            $("#kt_search_submit").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                            return;
                                        }

                                        var search_results = response["results"];
                                        search_results.forEach((item) => {
                                            var stat = "";
                                            if (item.vehicleStatus === 'start') {
                                                stat = '<td><i style="color:skyblue;" title="start" class="flaticon2-add-circular-button"></i></td>';
                                            }
                                            if (item.vehicleStatus === 'active') {
                                                stat = '<td><i style="color:blue;" title="active" class="flaticon2-correct"></i></td> ';
                                            }
                                            if (item.vehicleStatus === 'maintenance') {
                                                stat = '<td><i style="color:black;" title="maintenance" class="flaticon2-correct"></i></td>';
                                            }
                                            if (item.vehicleStatus === 'inactive') {
                                                stat = '<td><i style="color:grey;" title="inactive" class="flaticon2-correct"></i></td>';
                                            }
                                            if (item.vehicleStatus === 'end') {
                                                stat = '<td><i style="color:red;" title="end" class="flaticon2-correct"></i></td>';
                                            }
                                            var dtm = new Date(item.startDate);
                                            $('#data_search_info').append(
                                                    '<tr>' +
    <%
        if (ns_company.contains("PROTECH") || ns_company.contains("SHERMCO") || ns_company.contains("CALIBER")) {
    %>
                                            '<td>' + item.department.departmentName + '</td>' +
    <%
    } else {
    %>
                                            '<td>' + item.vehicleId + '</td>' +
    <%
        }
    %>
                                            '<td>' + item.state + '</td>' +
                                                    '<td>' + item.licensePlate + '</td>' +
                                                    '<td>' + item.tollTagId + '</td>' +
                                                    '<td>' + item.unit + '</td>' +
                                                    '<td>' + item.vin + '</td>' +
                                                    '<td>' + item.make + '</td>' +
                                                    '<td>' + item.model + '</td>' +
                                                    '<td>' + item.color + '</td>' +
                                                    '<td>' + dtm + '</td>' +
                                                    '<td style="font-size:12px;">' +
                                                    '<a title="View Details" href="http://127.0.0.1:800/client_vehicle/view/vehicle/info/' + item.vehicleId + '" class="btn btn-sm btn-clean btn-icon btn-icon-md">' +
                                                    '<i class="fas fa-eye"></i>' +
                                                    '</a> ' +
                                                    '<a href="http://127.0.0.1:800/client_vehicle/edit/vehicle/' + item.vehicleId + '" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Edit Vehicle"> ' +
                                                    '<i class="fas fa-edit"></i>' +
                                                    '</a> ' +
                                                    '</td>' +
                                                    '</tr>'
                                                    );
                                        });
                                        $("#vehicle_search_info").show();
                                        $("#kt_clear_submit").show();
                                        $("#kt_search_submit").hide();
                                        $("#kt_search_submit").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                    },
                                    error: function (res) {
                                        $("#kt_search_submit").hide();
                                        $("#kt_search_submit").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                        var response = JSON.stringify(res);
                                        //console.log(response);
                                    }
                                });
                            } else {
                                $("#data_search_info").empty();
                                $("#vehicle_search_info").hide();
                            }
                        }
                        const search_vehicle = document.getElementById("search_vehicle");


                        search_vehicle.addEventListener(
                                "keyup",
                                debounce(searchVehicle, 3000)
                                );


                        function clearSearch() {
                            $("#vehicle_search_info").hide();
                            $("#kt_search_submit").hide();
                            $("#kt_clear_submit").hide();
                        }

                        function printPdf() {
                            $("#pdf-button").addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
                           /* $.ajax({
                                type: "POST",
                                url: 'http://127.0.0.1:800/client_vehicle/count/printing/',
                                success: function (res) {
                                    var response = res;
                                    var title = response["title"];
                                    var message = response["message"];
                                    if (title === "fail") {
                                        swalWithBootstrapButtons.fire(
                                                'Innovative Toll Solution',
                                                message,
                                                'error'
                                                );
                                        $("#pdf-button").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                        return;
                                    }
*/
                                    $("#pdf-button").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                    window.open("http://127.0.0.1:800/client_vehicle/vehiclePdf", "_parent", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500");
/*
                                },
                                error: function (res) {
                                    $("#pdf-button").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                    var response = JSON.stringify(res);
                                }
                            });*/
                        }



                        function printExcel() {
                            // console.log("*****GENERATE EXCEL*******");
                            $("#excel-button").addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
                            /*$.ajax({
                                type: "POST",
                                url: 'http://127.0.0.1:800/client_vehicle/count/printing/',
                                success: function (res) {
                                    var response = res;
                                    var title = response["title"];
                                    var message = response["message"];
                                    if (title === "fail") {
                                        swalWithBootstrapButtons.fire(
                                                'Innovative Toll Solution',
                                                message,
                                                'error'
                                                );
                                        $("#excel-button").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                        return;
                                    }
                                    */
                                    window.open("http://127.0.0.1:800/client_vehicle/excel/vehicles.xlsx", "_top", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500");
                                    $("#excel-button").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                /*},
                                error: function (res) {
                                    $("#excel-button").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                    var response = JSON.stringify(res);
                                }
                            });
*/
                        }
</script>
</body>
</html>