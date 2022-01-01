<%@include file="administratortablehead.jsp" %>

<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content" style="padding-top:.2vh;">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">

        <div class="row"> 
            <div class="col-xl-12 col-lg-12 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <table class="table table-striped- table-bordered table-hover table-checkable" id="vehicle_info" >
                        <thead class="thead-dark">
                            <tr>  
                                <th style="color:white;">ID</th>   
                                <th style="color:white;">Company</th>                           
                                <th style="color:white;">Department</th>  
                                <th style="color:white;">File Name</th>  
                                <th style="color:white;">View</th>     
                                <th style="color:white;">Updated Time</th>  
                                <th style="color:white;">Action</th>  
                            </tr>
                        </thead>
                        <tbody id="data_info">
                            <c:choose>
                                <c:when test="${data.size() > 0 }">
                                    <c:forEach var="vehicleFile" items="${data}">                            
                                        <tr>
                                            <td>${vehicleFile.vehicleFileId}</td> 
                                            <td>${vehicleFile.clientProfile.companyName}</td>
                                            <td>${vehicleFile.department.departmentName}</td>
                                            <td>${vehicleFile.vehicleName}</td>
                                            <td>
                                                <a target="blank" href="http://127.0.0.1:800/vehicle/download/upload/${vehicleFile.vehicleFileId}">
                                                    <button  class="btn btn-brand btn-pill btn-elevate">Download<i class="kt-menu__link-icon flaticon2-download"></i></button>
                                                </a>                                              
                                            </td>
                                            <td>${vehicleFile.updatedTime}</td>
                                            <td>
                                                <span class="dropdown">
                                                    <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown" aria-expanded="true">
                                                        <i class="la la-ellipsis-h"></i>
                                                    </a>
                                                    <div class="dropdown-menu dropdown-menu-right">
                                                        <button  class="dropdown-item" type="button" data-toggle="modal" data-target="#myModal${vehicleFile.vehicleFileId}">
                                                            <i class="la la-edit"></i> Reverse Upload</button>
                                                    </div>
                                                </span>
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
                                        <td nowrap></td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                    <table class="table table-striped- table-bordered table-hover table-checkable">
                        <thead class="thead-dark"> 
                            <tr>
                                <c:if test="${data.size() > 0 }">
                            <div class="panel-footer">
                              <!-- Showing ${number+1} <b>to</b> ${size+1} of --> <!-- Total  Entries ::  <b>  ${totalElements} </b> -->
                                <ul class="pagination"> 
                                    <li class="kt-menu__item "> 
                                        Page   
                                    </li> 
                                    <c:forEach begin="0" end="${totalPages-1}" var="page">
                                        <li class="kt-menu__item "> 
                                            <a href="<c:url value='http://127.0.0.1:800/vehicle/view_file?page=${page}&size=${size}' />"> <button  type="button" class="btn btn-default"> ${page+1}</button></a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:if>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div> 
</div>
<%@include file="dashtablefooter.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-file-vehicle.js?v=1001" />"   type="text/javascript"></script>
<c:choose>
    <c:when test="${data.size() > 0 }">
        <c:forEach var="vehicle" items="${data}">                                             
            <div id="myModal${vehicle.vehicleFileId}" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">VEHICLE REVERSAL UPLOAD</h4>
                            <p>FILE ID : ${vehicle.vehicleFileId}</p>
                        </div>
                        <div class="modal-body">  
                            <div class="form">
                                <div class="row">
                                    <div class="form-group">
                                        <input  class="form-control" type="hidden" value="${vehicle.vehicleFileId}" name="vehicleId${vehicle.vehicleFileId}" id="vehicleId${vehicle.vehicleFileId}"/>
                                    </div>                                                        
                                </div>                                                    
                                <div class="row">
                                    <div class="kt-login__actions" onclick="reverseUpload${vehicle.vehicleFileId}">
                                        <button   onclick="reverseUpload${vehicle.vehicleFileId}()" class="btn btn-brand btn-pill btn-elevate">Submit<i class="kt-menu__link-icon flaticon2-add"></i></button>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button  type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <script>
                function reverseUpload${vehicle.vehicleFileId}() {
                    var vehicleFileId = $('#vehicleId${vehicle.vehicleFileId}').val();
                    const swalWithBootstrapButtons = Swal.mixin({
                        customClass: {
                            confirmButton: 'btn btn-brand',
                            cancelButton: 'btn btn-dark'
                        },
                        buttonsStyling: false
                    });
                    swalWithBootstrapButtons.fire({
                        title: 'Are you sure?',
                        text: "You are about to reverse a vehicle",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Yes, proceed!',
                        cancelButtonText: 'No, Go back!',
                        reverseButtons: true
                    }).then((result) => {
                        if (result.value) {
                            $.ajax({
                                type: "POST",
                                url: 'http://127.0.0.1:800/vehicle/reverse/upload/' + vehicleFileId,
                                success: function (res) {
                                    var title = res["title"];
                                    var message = res["message"];
                                    if (title === "fail") {
                                        swalWithBootstrapButtons.fire(
                                                'Innovative Toll',
                                                message,
                                                'error'
                                                );
                                        return;
                                    }
                                    if (title === "success") {
                                        swalWithBootstrapButtons.fire(
                                                'Innovative Toll',
                                                message,
                                                'success'
                                                );
                                        /*
                                         * Refresh data
                                         */
                                        setTimeout(function () {
                                            window.location.reload();
                                        }, 1000);
                                        return;
                                    }
                                },
                                error: function (res) {
                                    console.log(res);
                                }
                            });
                        } else if (result.dismiss === Swal.DismissReason.cancel) {
                            swalWithBootstrapButtons.fire(
                                    'Innovative Toll',
                                    'Reverted',
                                    'error'
                                    );
                        }
                    });
                }
            </script>

        </c:forEach>
    </c:when>
</c:choose>
<script>
    $(document).ready(function () {
        $("input[type='search']").wrap("<form>");
        $("input[type='search']").closest("form").attr("autocomplete", "off");
    });
</script>
</body>
</html>