<%@include file="administratortablehead.jsp" %>

<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content" style="padding-top:.2vh;">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">

        <div class="row"> 
            <div class="col-xl-12 col-lg-12 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <table class="table table-striped- table-bordered table-hover table-checkable" id="transaction_info" >
                        <thead class="thead-dark">
                            <tr>  
                                <th style="color:white;">ID</th>   
                                <th style="color:white;">Company</th>     
                                <th style="color:white;">File Name</th>  
                                <th style="color:white;">View</th>     
                                <th style="color:white;">Updated Time</th>  
                                <th style="color:white;">Action</th>  
                            </tr>
                        </thead>
                        <tbody id="data_info">
                            <c:choose>
                                <c:when test="${data.size() > 0 }">
                                    <c:forEach var="transactionFile" items="${data}">                            
                                        <c:if test="${transactionFile.transactionName!=null||transactionFile.transactionName!=''}">
                                            <tr>  <td>${transactionFile.transactionFileId}</td> 
                                                <td title="${transactionFile.department.departmentName}">${transactionFile.clientProfile.companyName}</td>
                                                <td>${transactionFile.transactionName}</td>
                                                <td>
                                                    <a target="blank" href="http://127.0.0.1:800/transaction/download/upload/${transactionFile.transactionFileId}">
                                                        <button  class="btn btn-brand btn-pill btn-elevate">Download<i class="kt-menu__link-icon flaticon2-download"></i></button>
                                                    </a>                                              
                                                </td>
                                                <td>${transactionFile.updatedTime}</td>
                                                <td>
                                                    <span class="dropdown">
                                                        <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown" aria-expanded="true">
                                                            <i class="la la-ellipsis-h"></i>
                                                        </a>
                                                        <div class="dropdown-menu dropdown-menu-right">
                                                            <button  class="dropdown-item" type="button" data-toggle="modal" data-target="#myModal${transactionFile.transactionFileId}">
                                                                <i class="la la-edit"></i> Reverse Upload</button>
                                                        </div>
                                                    </span>
                                                </td> 
                                            </tr>                                             
                                        </c:if>
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
                                            <a href="<c:url value='http://127.0.0.1:800/transaction/view_file?page=${page}&size=${size}' />"> <button  type="button" class="btn btn-default"> ${page+1}</button></a>
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
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-file-transaction.js?v=1001" />"   type="text/javascript"></script>
<c:choose>
    <c:when test="${data.size() > 0 }">
        <c:forEach var="transaction" items="${data}">                                             
            <div id="myModal${transaction.transactionFileId}" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">VEHICLE REVERSAL UPLOAD</h4>
                            <p>FILE ID : ${transaction.transactionFileId}</p>
                        </div>
                        <div class="modal-body">  
                            <div class="form">
                                <div class="row">
                                    <div class="form-group">
                                        <input  class="form-control" type="hidden" value="${transaction.transactionFileId}" name="transactionId${transaction.transactionFileId}" id="transactionId${transaction.transactionFileId}"/>
                                    </div>                                                        
                                </div>                                                    
                                <div class="row">
                                    <div class="kt-login__actions" onclick="reverseUpload${transaction.transactionFileId}">
                                        <button   onclick="reverseUpload${transaction.transactionFileId}()" class="btn btn-brand btn-pill btn-elevate">Submit<i class="kt-menu__link-icon flaticon2-add"></i></button>
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
                function reverseUpload${transaction.transactionFileId}() {
                    var transactionFileId = $('#transactionId${transaction.transactionFileId}').val();
                    const swalWithBootstrapButtons = Swal.mixin({
                        customClass: {
                            confirmButton: 'btn btn-brand',
                            cancelButton: 'btn btn-dark'
                        },
                        buttonsStyling: false
                    });
                    swalWithBootstrapButtons.fire({
                        title: 'Are you sure?',
                        text: "You are about to reverse a transaction",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Yes, proceed!',
                        cancelButtonText: 'No, Go back!',
                        reverseButtons: true
                    }).then((result) => {
                        if (result.value) {
                            $.ajax({
                                type: "POST",
                                url: 'http://127.0.0.1:800/transaction/reverse/upload/' + transactionFileId,
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