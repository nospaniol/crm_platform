<%@include file="administratorhead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-md-12 col-lg-12 col-xl-12">
                <div class="kt-login__wrapper">
                    <div class="kt-login__body">                              
                        <div class="kt-login__signup">
                            <div class="kt-login__logo">
                                <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                                    <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                        <div class="kt-portlet__head kt-portlet__head--noborder">
                                            <div class="kt-portlet__head-label">
                                                <h3 class="kt-portlet__head-title" id="data_title">
                                                    ${topic}
                                                </h3>
                                                <%@include file="alert.jsp" %>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <form>
                                    <input  id="clientName" name="clientName" type="hidden" value="${clientName}">
                                    <input  id="departmentName" name="departmentName" type="hidden" value="${departmentName}">
                                </form>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>

        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-2">
                <div class="kt-portlet kt-portlet--height-fluid"> 
                    <div class="kt-portlet__body"> 
                        <table  class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1" >
                            <thead class="thead-dark">
                                <tr>
                                    <th style="color:white" >Department</th>    
                                    <th style="color:white" >Balance</th>
                                    <th style="color:white" >Action</th>
                            </thead>
                            <tbody id="data_info">
                                <c:choose>
                                    <c:when test="${data.size() > 0 }">
                                        <c:forEach var="account" items="${data}">
                                            <tr>               
                                                <td>${account.department.departmentName}</td>
                                                <td>${account.amount}</td>
                                                <td>
                                                    <button  class="btn btn-brand" type="button" data-toggle="modal" data-target="#myModal${account.accountId}"><i class="la la-edit"></i>Update Amount</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="16">No accounts available</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>

                                <c:if test="${data.size() > 0 }">
                                <div class="panel-footer">
                                   <!-- Showing ${number+1} <b>to</b> ${size+1} of --> <!-- Total  Entries ::  <b>  ${totalElements} </b> -->
                                    <ul class="pagination"> Page  ::     
                                        <c:forEach begin="0" end="${totalPages-1}" var="page">
                                            <li class="page-item"> 
                                                <a href="<c:url value='http://127.0.0.1:800/account/view/department/${clientId}?page=${page}&size=${size}' />" class="page-link">pg. ${page+1}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                            </tbody>
                        </table>
                    </div> 
                </div> 
            </div> 
        </div> 
    </div>
</div>             

<%@include file="administratorfoot.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/manage-department-account.js?v=1001" />"   type="text/javascript"></script>
<c:choose>
    <c:when test="${data.size() > 0 }">
        <c:forEach var="account" items="${data}">                          

            <div id="myModal${account.accountId}" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">ACCOUNT MANAGEMENT</h4>
                            <p>Department : ${account.department.departmentName}</p>
                        </div>
                        <div class="modal-body">  
                            <div class="form">
                                <div class="row">
                                    <div class="form-group">
                                        <input  class="form-control" type="hidden" id="accountId${account.accountId}" value="${account.accountId}"/>
                                    </div>                                                          
                                </div>     
                                <div class="row">
                                    <div class="form-group">
                                        <input  class="form-control" type="number" id="amount${account.accountId}"/>
                                    </div>                                                          
                                </div>
                                <div class="row">
                                    <div class="col-6 kt-login__actions">
                                        <button   onclick="depositAmount${account.accountId}()" class="btn btn-brand btn-pill btn-elevate">DEPOSIT<i class="kt-menu__link-icon flaticon2-add-square"></i></button>
                                    </div>
                                    <div class="col-6 kt-login__actions">
                                        <button   onclick="subtractAmount${account.accountId}()" class="btn btn-dark btn-pill btn-elevate">SUBTRACT<i class="kt-menu__link-icon flaticon2-pen"></i></button>
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

                function depositAmount${account.accountId}() {
                    var accountId = $('#accountId${account.accountId}').val();
                    var amount = $('#amount${account.accountId}').val();
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
                        text: "You are about to deposit : " + amount,
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Yes, proceed',
                        cancelButtonText: 'No, Go back!',
                        reverseButtons: true
                    }).then((result) => {
                        if (result.value) {
                            $.ajax({
                                type: "POST",
                                url: 'http://127.0.0.1:800/account/department/deposit/' + amount + '/' + accountId,
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

                function subtractAmount${account.accountId}() {
                    var accountId = $('#accountId${account.accountId}').val();
                    var amount = $('#amount${account.accountId}').val();
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
                        text: "You are about to subtract : " + amount,
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Yes, proceed',
                        cancelButtonText: 'No, Go back!',
                        reverseButtons: true
                    }).then((result) => {
                        if (result.value) {
                            $.ajax({
                                type: "POST",
                                url: 'http://127.0.0.1:800/account/department/credit/' + amount + '/' + accountId,
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

