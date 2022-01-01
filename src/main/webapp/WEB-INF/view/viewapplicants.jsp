<%@include file="administratortablehead.jsp" %>

<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content" style="padding-top:.5vh">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
        <div class="row">                                               
            <div class="col-xl-12 col-lg-12 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <table class="table table-striped- table-bordered table-hover table-checkable" id="client_info" >
                        <thead class="thead-dark">
                            <tr>
                                <th style="color:white" >Applicant ID</th>  
                                <th style="color:white" >Company Name</th>   
                                <th style="color:white" >Company Title</th>                      
                                <th style="color:white" >First Name</th>
                                <th style="color:white" >Last Name</th>                      
                                <th style="color:white" >Email Address</th>
                                <th style="color:white" >Phone Number</th>
                                <th style="color:white" >Action</th>
                        </thead>
                        <tbody id="data_info">
                            <c:choose>
                                <c:when test="${data.size() > 0 }">
                                    <c:forEach var="application" items="${data}">
                                        <tr>
                                            <td>${application.applicationId}</td>
                                            <td>${application.companyName} </td>  
                                            <td>${application.companyTitle} </td>  
                                            <td>${application.firstName} </td>  
                                            <td>${application.lastName} </td>                            
                                            <td>${application.emailAddress}</td>
                                            <td>${application.phoneNumber}</td>
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
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-applicants.js?v=1001" />"   type="text/javascript"></script>
<c:choose>
    <c:when test="${data.size() > 0 }">
        <c:forEach var="application" items="${data}">                                             
            <div id="myModal${application.applicationId}" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">APPLICATION ID : ${application.applicationId}</h4>
                        </div>
                        <div class="modal-body">  
                            <div class="form">
                                <div class="row">
                                    <div class="form-group">
                                        <input  class="form-control" type="hidden" value="${application.applicationId}" name="applicationId${application.applicationId}" id="applicationId${application.applicationId}"/>
                                    </div>                                                       
                                </div>                                                    
                                <div class="row">
                                    <div class="col-6 kt-login__actions">
                                        <button   onclick="confirmApplication${application.applicationId}()" class="btn btn-brand btn-pill btn-elevate">Approve<i class="kt-menu__link-icon flaticon2-add"></i></button>
                                    </div>
                                    <div class="col-6 kt-login__actions">
                                        <button   onclick="deleteApplication${application.applicationId}()" class="btn btn-dark btn-pill btn-elevate">Delete<i class="kt-menu__link-icon flaticon2-trash"></i></button>
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
                function confirmApplication${application.applicationId}() {
                    var applicationId = $('#applicationId${application.applicationId}').val();
                    const swalWithBootstrapButtons = Swal.mixin({
                        customClass: {
                            confirmButton: 'btn btn-brand',
                            cancelButton: 'btn btn-dark'
                        },
                        buttonsStyling: false
                    });
                    swalWithBootstrapButtons.fire({
                        title: 'Are you sure?',
                        text: "You are about to approve an application!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Yes, proceed!',
                        cancelButtonText: 'No, Go back!',
                        reverseButtons: true
                    }).then((result) => {
                        if (result.value) {
                            $.ajax({
                                type: "POST",
                                url: 'http://127.0.0.1:800/user/approve/application/' + applicationId,
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

                function deleteApplication${application.applicationId}() {
                    var applicationId = $('#applicationId${application.applicationId}').val();
                    const swalWithBootstrapButtons = Swal.mixin({
                        customClass: {
                            confirmButton: 'btn btn-brand',
                            cancelButton: 'btn btn-dark'
                        },
                        buttonsStyling: false
                    });
                    swalWithBootstrapButtons.fire({
                        title: 'Are you sure?',
                        text: "You are about delete an application!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Yes, proceed!',
                        cancelButtonText: 'No, Go back!',
                        reverseButtons: true
                    }).then((result) => {
                        if (result.value) {
                            $.ajax({
                                type: "POST",
                                url: 'http://127.0.0.1:800/user/delete/application/' + applicationId,
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