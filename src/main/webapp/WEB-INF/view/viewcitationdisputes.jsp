<%@include file="administratortablehead.jsp" %>
<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
    <div class="kt-portlet kt-portlet--mobile">

        <div class="kt-portlet__body">
            <table class="table table-striped- table-bordered table-hover table-checkable" id="dispute_info" >
                <thead class="thead-dark">                
                    <tr>
                        <th style="color:white;">Dispute ID</th>
                        <th style="color:white;">Citation ID</th>
                        <th style="color:white;">Citation Date</th>  
                        <th style="color:white;">Violation Number</th>                        
                        <th style="color:white;">Company</th>
                        <th style="color:white;">Department</th>
                        <th style="color:white;">Citation Amount</th>
                        <th style="color:white;">Fee Amount</th>
                        <th style="color:white;">Paid Amount</th>
                        <th style="color:white;">Payable To</th>
                        <th style="color:white;">Dispute</th>
                        <th style="color:white;">Actions</th>
                    </tr>
                </thead>
                <tbody id="data_info">
                    <c:choose>
                        <c:when test="${allDisputes.size() > 0 }">
                            <c:forEach var="citationDispute" items="${allDisputes}">                            
                                <tr>
                                    <td>${citationDispute.disputeId}</td>
                                    <td>${citationDispute.citation.citationId}</td>
                                    <td>${citationDispute.citation.citationDate}</td>
                                    <td>${citationDispute.citation.violationNumber}</td>
                                    <td>${citationDispute.citation.clientProfile.companyName}</td>
                                    <td>${citationDispute.citation.department.departmentName}</td>
                                    <td>${citationDispute.citation.citationAmount}</td>
                                    <td>${citationDispute.citation.feeAmount}</td>
                                    <td>${citationDispute.citation.paidAmount}</td>
                                    <td>${citationDispute.citation.payableTo}</td>
                                    <td>${citationDispute.dispute}</td>
                                    <td nowrap></td>
                                </tr>
                            <div id="myModal${citationDispute.disputeId}" class="modal fade" role="dialog">
                                <div class="modal-dialog">
                                    <!-- Modal content-->
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h4 class="modal-title">RESOLUTION PANEL</h4>
                                            <p>DISPUTE ID : ${citationDispute.disputeId}</p>
                                        </div>
                                        <div class="modal-body">  
                                            <div class="form">
                                                <div class="row">
                                                    <div class="form-group">
                                                        <input  class="form-control" type="hidden" id="disputeId${citationDispute.disputeId}" value="${citationDispute.disputeId}"/>
                                                    </div>                                                          
                                                </div>                                                    
                                                <div class="row">
                                                    <div class="col-6 kt-login__actions">
                                                        <button   onclick="reverseSolution${citationDispute.disputeId}()" class="btn btn-brand btn-pill btn-elevate" style="background-color:red">REMOVE DISPUTE<i class="kt-menu__link-icon flaticon2-refresh"></i></button>
                                                    </div>
                                                    <div class="col-6 kt-login__actions">
                                                        <button   onclick="closeSolution${citationDispute.disputeId}()" class="btn btn-brand btn-pill btn-elevate">CLOSE DISPUTE<i class="kt-menu__link-icon flaticon2-close-cross"></i></button>
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

                                function reverseSolution${citationDispute.disputeId}() {
                                    var disputeId = $('#disputeId${citationDispute.disputeId}').val();
                                    const swalWithBootstrapButtons = Swal.mixin({
                                        customClass: {
                                            confirmButton: 'btn btn-brand',
                                            cancelButton: 'btn btn-dark'
                                        },
                                        buttonsStyling: false
                                    });
                                    var btn = $(this);
                                    swalWithBootstrapButtons.fire({
                                        title: 'Are you sure?',
                                        text: "You won't be able to revert this!",
                                        icon: 'warning',
                                        showCancelButton: true,
                                        confirmButtonText: 'Yes, Reverse citation',
                                        cancelButtonText: 'No, Go back!',
                                        reverseButtons: true
                                    }).then((result) => {
                                        if (result.value) {
                                            $.ajax({
                                                type: "POST",
                                                url: 'http://127.0.0.1:800/citation/reverse/dispute/' + disputeId,
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

                                function closeSolution${citationDispute.disputeId}() {
                                    var disputeId = $('#disputeId${citationDispute.disputeId}').val();
                                    const swalWithBootstrapButtons = Swal.mixin({
                                        customClass: {
                                            confirmButton: 'btn btn-brand',
                                            cancelButton: 'btn btn-dark'
                                        },
                                        buttonsStyling: false
                                    });
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
                                                url: 'http://127.0.0.1:800/citation/close/dispute/' + disputeId,
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

<script>
    $(document).ready(function () {
        $("input[type='search']").wrap("<form>");
        $("input[type='search']").closest("form").attr("autocomplete", "off");
    });
</script>
</body>
</html> 