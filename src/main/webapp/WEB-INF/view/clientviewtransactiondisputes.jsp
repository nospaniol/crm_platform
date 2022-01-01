<%@include file="clientheader.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content" style="padding-top:.2vh">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-xl-12 col-lg-12 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <table class="table table-striped- table-bordered table-hover table-checkable" id="dispute_info" >
                        <thead class="thead-dark">                
                            <tr>
                                <th style="color:white;">Dispute ID</th>
                                <th style="color:white;">Transaction ID</th>  
                                <th style="color:white;">License Plate</th>                        
                                <th style="color:white;">Company</th>
                                <th style="color:white;">Department</th>
                                <th style="color:white;">Exit / Date Time</th>
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
                                        <td></td>
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

<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-client-dispute.js?v=1001" />"   type="text/javascript"></script>
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
                                url: 'http://127.0.0.1:800/client_transaction/close/dispute/' + disputeId,
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

                                        $('#data_info').empty();
                                        /*
                                         * Refresh data
                                         */
                                        $.ajax({
                                            type: "POST",
                                            url: 'http://127.0.0.1:800/client_transaction/load/disputes',
                                            success: function (res) {
                                                var title = res["title"];
                                                var message = res["message"];
                                                if (title === "fail") {
                                                    console.log(message);
                                                    return;
                                                }
                                                if (title === "success") {
                                                    var table = $('#dispute_info');
                                                    table.dataTable().fnClearTable();
                                                    table.dataTable().fnDestroy();
                                                    var search_results = res["results"];
                                                    search_results.forEach((item) => {
                                                        var dtm = new Date(item.transaction.exitDateTime);
                                                        $('#data_info').append(
                                                                '<tr>' +
                                                                '<td>' + item.disputeId + '</td>' +
                                                                '<td>' + item.transaction.transactionId + '</td>' +
                                                                '<td>' + item.transaction.vehicle.licensePlate + '</td>' +
                                                                '<td>' + item.clientProfile.companyName + '</td>' +
                                                                '<td>' + item.department.departmentName + '</td>' +
                                                                '<td>' + dtm + '</td>' +
                                                                '<td>' + item.transaction.exitLocation + '</td>' +
                                                                '<td>' + item.transaction.exitLane + '</td>' +
                                                                '<td>' + item.dispute + '</td>' +
                                                                '<td nowrap></td>' +
                                                                '</tr>'
                                                                )
                                                    });
                                                    initTransactionTable();
                                                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);

                                                }
                                            },
                                            error: function (res) {
                                                console.log(res);
                                            }
                                        });
                                        /*
                                         * refreshed
                                         */
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
    var initTransactionTable = function () {
        var table = $('#dispute_info');
        table.DataTable({
            responsive: true,
            "paging": true,
            pagingType: 'full_numbers',
            columnDefs: [
                {
                    targets: -1,
                    title: 'Actions',
                    orderable: false,
                    render: function (data, type, full, meta) {
                        var editor = '<span class="dropdown">'
                                + '<a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown" aria-expanded="true">'
                                + '<i class="la la-ellipsis-h"></i>'
                                + '</a>'
                                + '<div class="dropdown-menu dropdown-menu-right">'
                                + '<button  class="dropdown-item"  type="button" data-toggle="modal" data-target="#myModal' + full[0] + '">'
                                + '<i class="la la-edit"></i>Resolve</button>'
                                // + '<a class="dropdown-item" href="#"><i class="la la-leaf"></i> Message Admin</a>'
                                + '</div>'
                                + '</span>';
                        return editor;

                    },
                },
                {
                    targets: 7,
                    render: function (data, type, full, meta) {
                        var status = {
                            1: {'title': 'Pending', 'class': 'kt-badge--brand'},
                            2: {'title': 'Delivered', 'class': ' kt-badge--danger'},
                            3: {'title': 'Canceled', 'class': ' kt-badge--primary'},
                            4: {'title': 'Success', 'class': ' kt-badge--success'},
                            5: {'title': 'Info', 'class': ' kt-badge--info'},
                            6: {'title': 'Danger', 'class': ' kt-badge--danger'},
                            7: {'title': 'Warning', 'class': ' kt-badge--warning'},
                        };
                        if (typeof status[data] === 'undefined') {
                            return data;
                        }
                        return '<span class="kt-badge ' + status[data].class + ' kt-badge--inline kt-badge--pill">' + status[data].title + '</span>';
                    },
                },
                {
                    targets: 8,
                    render: function (data, type, full, meta) {
                        var status = {
                            1: {'title': 'Online', 'state': 'danger'},
                            2: {'title': 'Retail', 'state': 'primary'},
                            3: {'title': 'Direct', 'state': 'success'},
                        };
                        if (typeof status[data] === 'undefined') {
                            return data;
                        }
                        return '<span class="kt-badge kt-badge--' + status[data].state + ' kt-badge--dot"></span>&nbsp;' +
                                '<span class="kt-font-bold kt-font-' + status[data].state + '">' + status[data].title + '</span>';
                    },
                },
            ],
        });
    };


    $(document).ready(function () {
        $("input[type='search']").wrap("<form>");
        $("input[type='search']").closest("form").attr("autocomplete", "off");
    });
</script>
</body>
</html> 