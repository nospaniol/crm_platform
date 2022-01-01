<%@include file="administratortablehead.jsp" %>
<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
    <div class="kt-portlet kt-portlet--mobile">

        <div class="kt-portlet__body">
            <table class="table table-striped- table-bordered table-hover table-checkable" id="dispute_info" >
                <thead class="thead-dark">                
                    <tr>
                        <th style="color:white;">Dispute ID</th>
                        <th style="color:white;">Transaction ID</th>  
                        <th style="color:white;">License Plate</th>                        
                        <th style="color:white;">Company</th>
                        <th style="color:white;">Department</th>
                        <th style="color:white;">Exit / Date Time</th>
                        <th style="color:white;">Posted Date</th>
                        <th style="color:white;">Exit Location</th>
                        <th style="color:white;">Exit Lane</th>
                        <th style="color:white;">Dispute</th>
                        <th style="color:white;">Actions</th>
                    </tr>
                </thead>
                <tbody id="data_info">
                    <c:choose>
                        <c:when test="${allDisputes.size() > 0 }">
                            <c:forEach var="transactionDispute" items="${allDisputes}">                            
                                <tr>
                                    <td>${transactionDispute.disputeId}</td>
                                    <td>${transactionDispute.transaction.transactionId}</td>
                                    <td>${transactionDispute.transaction.vehicle.licensePlate}</td>
                                    <td>${transactionDispute.transaction.clientProfile.companyName}</td>
                                    <td>${transactionDispute.transaction.department.departmentName}</td>
                                    <td>${transactionDispute.transaction.exitDateTime}</td>
                                    <td>${transactionDispute.transaction.postedDate}</td>
                                    <td>${transactionDispute.transaction.exitLocation}</td>
                                    <td>${transactionDispute.transaction.exitLane}</td>
                                    <td>${transactionDispute.dispute}</td>
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
                                <td></td>
                            </tr>
                        </c:otherwise>
                    </c:choose>

                </tbody>
            </table>

        </div> 
    </div> 
</div> 
<%@include file="dashtablefooter.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/load-department.js?v=1001" />"   type="text/javascript"></script>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-dispute.js?v=1001" />"   type="text/javascript"></script>
<c:choose>
    <c:when test="${allDisputes.size() > 0 }">
        <c:forEach var="transactionDispute" items="${allDisputes}">                          

            <div id="myModal${transactionDispute.disputeId}" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">RESOLUTION PANEL</h4>
                            <p>DISPUTE ID : ${transactionDispute.disputeId}</p>
                        </div>
                        <div class="modal-body">  
                            <div class="form">
                                <div class="row">
                                    <div class="form-group">
                                        <input  class="form-control" type="hidden" id="disputeId${transactionDispute.disputeId}" value="${transactionDispute.disputeId}"/>
                                    </div>                                                          
                                </div>                                                    
                                <div class="row">
                                    <div class="col-6 kt-login__actions">
                                        <button   onclick="reverseSolution${transactionDispute.disputeId}()" class="btn btn-brand btn-pill btn-elevate" style="background-color:red">REVERSE TRANSACTION<i class="kt-menu__link-icon flaticon2-refresh"></i></button>
                                    </div>
                                    <div class="col-6 kt-login__actions">
                                        <button   onclick="closeSolution${transactionDispute.disputeId}()" class="btn btn-brand btn-pill btn-elevate">CLOSE DISPUTE<i class="kt-menu__link-icon flaticon2-close-cross"></i></button>
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

                function reverseSolution${transactionDispute.disputeId}() {
                    var disputeId = $('#disputeId${transactionDispute.disputeId}').val();
                    const swalWithBootstrapButtons = Swal.mixin({
                        customClass: {
                            confirmButton: 'btn btn-brand',
                            cancelButton: 'btn btn-dark'
                        },
                        buttonsStyling: false
                    });
                    var btn = $(this);
                    btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
                    swalWithBootstrapButtons.fire({
                        title: 'Are you sure?',
                        text: "You won't be able to revert this!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Yes, Reverse transaction',
                        cancelButtonText: 'No, Go back!',
                        reverseButtons: true
                    }).then((result) => {
                        if (result.value) {
                            $.ajax({
                                type: "POST",
                                url: 'http://127.0.0.1:800/transaction/reverse/dispute/' + disputeId,
                                success: function (res) {
                                    var title = res["title"];
                                    var message = res["message"];
                                    if (title === "fail") {
                                        swalWithBootstrapButtons.fire(
                                                'Innovative Toll',
                                                message,
                                                'error'
                                                );
                                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                        return;
                                    }
                                    if (title === "success") {
                                        swalWithBootstrapButtons.fire(
                                                'Innovative Toll',
                                                message,
                                                'success'
                                                );
                                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
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

                function closeSolution${transactionDispute.disputeId}() {
                    var disputeId = $('#disputeId${transactionDispute.disputeId}').val();
                    const swalWithBootstrapButtons = Swal.mixin({
                        customClass: {
                            confirmButton: 'btn btn-brand',
                            cancelButton: 'btn btn-dark'
                        },
                        buttonsStyling: false
                    });
                    var btn = $(this);
                    btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
                    swalWithBootstrapButtons.fire({
                        title: 'Are you sure?',
                        text: "You are about to close this dispute!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Yes, Close dispute!',
                        cancelButtonText: 'No, Go back!',
                        reverseButtons: true
                    }).then((result) => {
                        if (result.value) {
                            $.ajax({
                                type: "POST",
                                url: 'http://127.0.0.1:800/transaction/close/dispute/' + disputeId,
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
                                        setTimeout(function () {
                                            window.location.reload();
                                        },
                                                1000);
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