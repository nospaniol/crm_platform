<%@include file="clientheader.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content" style="padding-top:.2vh">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
        <div class="row row-no-padding row-col-separator-xl"> 
            <div class="col-xl-3 col-lg-3 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <div class="row">
                        <div clsss="col-6" style="padding-left:2vh; padding-top:2vh;">                            
                            <button id="excel-button" style="font-size:8px;" onclick="printExcel()" type="button" class="btn btn-dark">
                                <i class="la la-file-excel-o"></i>EXPORT EXCEL
                            </button>
                        </div> 
                        <div clsss="col-6" style="padding-left:2vh; padding-top:2vh;">
                            <button id="pdf-button" style="font-size:8px;" onclick="printPdf()" type="button" class="btn btn-brand">
                                <i class="la la-file-pdf-o"></i>PRINT PDF
                            </button>
                        </div> 
                    </div>
                </div>
            </div>
            <div class="col-xl-7 col-lg-7 order-lg-2 order-xl-1 align-content-xl-center">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <div class="kt-form row" method="post">
                        <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                            <div class="kt-portlet__head-label">
                                <div class="form-group" style="padding-left:2vh; padding-top:2vh;">
                                    <input style="font-size:12px;"  class="form-control" type="date" placeholder="start date" id="startDate" name="startDate"></select>
                                    <span class="form-text text-muted">Please select  the start date.</span> 
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                            <div class="kt-portlet__head-label">
                                <div class="form-group" style="padding-left:2vh; padding-top:2vh;">
                                    <input style="font-size:12px;" class="form-control" type="date" placeholder="end date" id="endDate" name="endDate"></select>
                                    <span class="form-text text-muted">Please select  end date.</span> 
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                            <div class="kt-portlet__head-label">
                                <div class="form-group" style="padding-top:2vh;">
                                    <button style="font-size:12px;" id="kt_search_inf" onclick="setSearchDates()" class=" btn btn-brand btn-pill btn-elevate">Search<i class="kt-menu__link-icon flaticon2-search kt-margin-r-15"></i></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-xl-2 col-lg-2 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <div class="row">
                        <div clsss="col-6" style="padding-left:2vh; padding-top:2vh;">  
                            <a target="_blank" href="<c:url value='http://127.0.0.1:800/client_transaction/disputes' />">
                                <button style="font-size:8px;"  type="button" class="btn btn-dark" style="hover:skylightblue;"><i class="flaticon-warning-sign"></i>View Disputes</button>
                            </a> 
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-xl-12 col-lg-12 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <table class="table table-striped- table-bordered table-hover table-checkable" id="transaction_info" >
                        <thead class="thead-dark">               
                            <tr>
                                <th style="color:white;font-size:12px;">ID</th>  
                                <th style="color:white;font-size:12px;">License Plate</th>                        
                                <th style="color:white;font-size:12px;">State</th>
                                <th style="color:white;font-size:12px;">Agency</th>
                                <th style="color:white;font-size:12px;">Exit / Date Time</th>
                                    <%                                        if (ns_company.contains("AMAZON")) {
                                    %> 
                                <th style="color:white;font-size:12px;">Class</th>  
                                    <%
                                    } else {
                                    %>
                                <th style="color:white;font-size:12px;">Exit Location</th>   
                                    <%
                                        }
                                    %>
                                <th style="color:white;font-size:12px;">Exit Lane</th>
                                <th style="color:white;font-size:12px;">Amount</th>
                                <th style="color:white;font-size:12px;">Actions</th>
                            </tr>
                        </thead>
                        <tbody id="data_info">
                            <c:choose>
                                <c:when test="${allTransactions.size() > 0 }">
                                    <c:forEach var="transaction" items="${allTransactions}">                                         
                                        <tr id="transaction${transaction.transactionId}">     
                                            <c:if test="${transaction.transactionDispute == true}">
                                                <td style="font-size:12px; color:lightskyblue;" title="DISPUTED TRANSACTION">${transaction.transactionId}</td>
                                                <td style="font-size:12px; color:lightskyblue;" title="DISPUTED TRANSACTION" >${transaction.vehicle.licensePlate}</td>
                                                <td style="font-size:12px; color:lightskyblue" title="DISPUTED TRANSACTION" >${transaction.state}</td>
                                                <td style="font-size:12px; color:lightskyblue" title="DISPUTED TRANSACTION" >${transaction.agency}</td>
                                                <td style="font-size:12px; color:lightskyblue" title="DISPUTED TRANSACTION" >${transaction.exitDateTime}</td>

                                                <%
                                                    if (ns_company.contains("AMAZON")) {
                                                %> 
                                                <td style="font-size:12px; color:lightskyblue" title="DISPUTED TRANSACTION" >${transaction.transactionClass}</td>
                                                <%
                                                } else {
                                                %>
                                                <td style="font-size:12px; color:lightskyblue" title="DISPUTED TRANSACTION" >${transaction.exitLocation}</td>
                                                <%
                                                    }
                                                %>
                                                <td style="font-size:12px; color:lightskyblue" title="DISPUTED TRANSACTION" >${transaction.exitLane}</td>
                                                <td style="font-size:12px; color:lightskyblue" title="DISPUTED TRANSACTION" ><b>${transaction.amount}</b></td>
                                                <td style="font-size:12px; color:lightskyblue" nowrap></td>                                            
                                            </c:if>
                                            <c:if test="${transaction.transactionDispute == false}">                                              
                                                <td style="font-size:12px;">${transaction.transactionId}</td>
                                                <td style="font-size:12px;">${transaction.vehicle.licensePlate}</td>
                                                <td style="font-size:12px;">${transaction.state}</td>
                                                <td style="font-size:12px;">${transaction.agency}</td>
                                                <td style="font-size:12px;">${transaction.exitDateTime}</td>             
                                                <%
                                                    if (ns_company.contains("AMAZON")) {
                                                %> 
                                                <td style="font-size:12px;" >${transaction.transactionClass}</td>
                                                <%
                                                } else {
                                                %>
                                                <td style="font-size:12px;" >${transaction.exitLocation}</td>
                                                <%
                                                    }
                                                %>
                                                <td style="font-size:12px;">${transaction.exitLane}</td>
                                                <td style="font-size:12px;"><b>${transaction.amount}</b></td>
                                                <td nowrap>
                                                    <span class="dropdown">
                                                        <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown" aria-expanded="true">
                                                            <i class="fas fa-ellipsis-h"></i>
                                                        </a>
                                                        <div class="dropdown-menu dropdown-menu-right">
                                                            <button  class="dropdown-item" type="button" data-toggle="modal" data-target="#myModal${transaction.transactionId}">
                                                                <i class="la la-edit"></i> Dispute Transaction</button>
                                                        </div>
                                                    </span>
                                                </td>
                                            </c:if>  
                                        </tr>
                                    <div id="myModal${transaction.transactionId}" class="modal fade" role="dialog">
                                        <div class="modal-dialog">
                                            <!-- Modal content-->
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h4 class="modal-title">Reason for dispute</h4>
                                                    <p>TRANSACTION ID : ${transaction.transactionId}</p>
                                                </div>
                                                <div class="modal-body">  
                                                    <div class="form">
                                                        <div class="row">
                                                            <div class="form-group">
                                                                <input  class="form-control" type="hidden" value="${transaction.transactionId}" name="transactionId${transaction.transactionId}" id="transactionId${transaction.transactionId}"/>
                                                            </div>  
                                                            <div class="form-group">
                                                                <textarea class="form-control" type="text" placeholder="Type your dispute here..." name="dispute${transaction.transactionId}" id="dispute${transaction.transactionId}" required=""></textarea>
                                                            </div>                                                        
                                                        </div>                                                    
                                                        <div class="row">
                                                            <div class="kt-login__actions" onclick="submitDispute${transaction.transactionId}">
                                                                <button   onclick="submitDispute${transaction.transactionId}()" class="btn btn-brand btn-pill btn-elevate">Submit<i class="kt-menu__link-icon flaticon2-add"></i></button>
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
                                        function submitDispute${transaction.transactionId}() {
                                            var transactionId = $('#transactionId${transaction.transactionId}').val();
                                            var dispute = $('#dispute${transaction.transactionId}').val();
                                            const swalWithBootstrapButtons = Swal.mixin({
                                                customClass: {
                                                    confirmButton: 'btn btn-brand',
                                                    cancelButton: 'btn btn-dark'
                                                },
                                                buttonsStyling: false
                                            });
                                            if (dispute === null || dispute === '') {
                                                swalWithBootstrapButtons.fire(
                                                        'Innovative Toll',
                                                        'Fill required fields!',
                                                        'error'
                                                        );
                                                return;
                                            }
                                            swalWithBootstrapButtons.fire({
                                                title: 'Are you sure?',
                                                text: "You are about submit a dispute!",
                                                icon: 'warning',
                                                showCancelButton: true,
                                                confirmButtonText: 'Yes, submit dispute!',
                                                cancelButtonText: 'No, Go back!',
                                                reverseButtons: true
                                            }).then((result) => {
                                                if (result.value) {
                                                    $.ajax({
                                                        type: "POST",
                                                        url: 'http://127.0.0.1:800/client_transaction/post/dispute/' + transactionId + '/' + dispute,
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
                            <c:otherwise>
                                <tr>
                                    <td style="font-size:12px;">No Data</td>
                                    <td style="font-size:12px;">No Data</td>
                                    <td style="font-size:12px;">No Data</td>
                                    <td style="font-size:12px;">No Data</td>
                                    <td style="font-size:12px;">No Data</td>
                                    <td style="font-size:12px;">No Data</td>
                                    <td style="font-size:12px;">No Data</td>
                                    <td style="font-size:12px;">No Data</td>
                                    <td style="font-size:12px;"></td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>

                    <table class="table table-striped- table-bordered table-hover table-checkable" id="total_info" >
                        <thead>
                            <tr>                               
                                <th style="color:white;font-size:12px;"></th> 
                                <th style="color:white;font-size:12px;"></th> 
                                <th style="color:white;font-size:12px;"></th>                        
                                <th style="color:white;font-size:12px;"></th>
                                <th style="color:white;font-size:12px;"></th>
                                <th style="color:white;font-size:12px;"></th>
                                <th style="color:white;font-size:12px;"></th>
                                <th style="color:white;font-size:12px;"></th>
                                <th style="color:white;font-size:12px;"></th>
                                <th>TOTAL AMOUNT</th>
                                <th style="color:white;font-size:12px;"></th>
                            </tr>
                        </thead>
                        <tbody id="total_body">
                            <tr>
                                <td style="font-size:12px;"><b>TOTAL AMOUNT</b></td>
                                <td style="font-size:12px;"></td>
                                <td style="font-size:12px;"></td>
                                <td style="font-size:12px;"></td>
                                <td style="font-size:12px;"></td>
                                <td style="font-size:12px;"></td>
                                <td style="font-size:12px;"></td>
                                <td style="font-size:12px;"></td>
                                <td style="font-size:12px;"></td>
                                <td style="font-size:12px;"><b>${totalAmount}</b></td>
                                <td nowrap></td>
                        </tbody>
                        </tr>
                    </table>
                    <c:if test="${allTransactions.size() > 0 }">
                        <nav aria-label="Pages">
                            <ul class="pagination pagination-sm  flex-wrap">
                                <c:forEach begin="0" end="${totalPages}" var="page">                             
                                    <li class="page-item"><a class="page-link" href="<c:url value='http://127.0.0.1:800/client_transaction/view_transaction?page=${page}&size=${size}' />">${page+1}</a></li>
                                    </c:forEach>
                            </ul>
                        </nav>
                    </c:if>

                </div> 
            </div> 
        </div> 
    </div>
</div>                        

<%@include file="clientdashtablefooter.jsp" %>             X

<script src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-client-transaction.js?v=1001" />"   type="text/javascript"></script>

<script>
                                        $(document).ready(function () {
                                            $("input[type='search']").wrap("<form>");
                                            $("input[type='search']").closest("form").attr("autocomplete", "off");
                                        });
                                        function setSearchDates() {
                                            $("#kt_search_inf").addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
                                            var endDate = $("#endDate").val();
                                            var startDate = $("#startDate").val();
                                            $.ajax({
                                                type: "POST",
                                                data: {
                                                    searchItem: startDate,
                                                    additionalField: endDate
                                                },
                                                url: 'http://127.0.0.1:800/client_transaction/change_mode/dates/',
                                                success: function (res) {
                                                    var response = res;
                                                    var title = response["title"];
                                                    if (title === "fail") {
                                                        $("#kt_search_inf").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                                        return;
                                                    }
                                                    window.open("http://127.0.0.1:800/client_transaction/search_transaction", "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500");
                                                    $("#kt_search_inf").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                                },
                                                error: function (res) {
                                                    $("#kt_search_inf").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                                    var response = JSON.stringify(res);
                                                    console.log(response);
                                                }
                                            });
                                        }

                                        function printPdf() {
                                            const swalWithBootstrapButtons = Swal.mixin({
                                                customClass: {
                                                    confirmButton: 'btn btn-brand',
                                                    cancelButton: 'btn btn-dark'
                                                },
                                                buttonsStyling: false
                                            });
                                            $("#pdf-button").addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
                                            $.ajax({
                                                type: "POST",
                                                url: 'http://127.0.0.1:800/client_transaction/count/printing/',
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

                                                    $("#pdf-button").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                                    window.open("http://127.0.0.1:800/client_transaction/transactionPdf", "_blank", "resizable=yes,top=500,left=500");

                                                },
                                                error: function (res) {
                                                    $("#pdf-button").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                                    var response = JSON.stringify(res);
                                                }
                                            });
                                        }



                                        function printExcel() {
                                            const swalWithBootstrapButtons = Swal.mixin({
                                                customClass: {
                                                    confirmButton: 'btn btn-brand',
                                                    cancelButton: 'btn btn-dark'
                                                },
                                                buttonsStyling: false
                                            });
                                            // console.log("*****GENERATE EXCEL*******");
                                            $("#excel-button").addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
                                            $.ajax({
                                                type: "POST",
                                                url: 'http://127.0.0.1:800/client_transaction/count/printing/',
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
                                                    window.open("http://127.0.0.1:800/client_transaction/excel/transactions.xlsx", "_top", "resizable=yes,top=500,left=500");
                                                    $("#excel-button").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                                },
                                                error: function (res) {
                                                    $("#excel-button").removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                                    var response = JSON.stringify(res);
                                                }
                                            });
                                        }


</script>

</body>
</html>