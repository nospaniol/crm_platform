<%@include file="administratorhead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet">
            <div class="kt-portlet__body  kt-portlet__body--fit">
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-md-12 col-lg-12 col-xl-3">
                        <div class="kt-login__wrapper">
                            <div class="kt-login__container">
                                <div class="kt-login__body">                              
                                    <div class="kt-login__signup">
                                        <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                                            <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                                <div class="kt-portlet__head kt-portlet__head--noborder">
                                                    <div class="kt-portlet__head-label">
                                                        <h3 class="kt-portlet__head-title">
                                                            Agency
                                                        </h3>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="kt-login__form">
                                            <c:url var="agencyUrl" value="/agency/register/save" />
                                            <form:form class="kt-form" action="${agencyUrl}" method="post" modelAttribute="agency" >
                                                <div class="form-group">
                                                    <form:input   path="agencyId" class="form-control" type="hidden" name="agencyId" id="agencyId"/>
                                                </div>   
                                                <div class="row">
                                                    <div class="col-xl-4 col-lg-4 order-lg-1 order-xl-1">
                                                        <div class="form-group">
                                                            <form:input   path="agencyName" class="form-control" type="text" placeholder="Agency" name="agencyName" id="agencyName" required=""/>
                                                        </div>                                                        
                                                    </div>                                                    
                                                    <div class="col-xl-4 col-lg-4 order-lg-1 order-xl-1">
                                                        <div class="kt-login__actions">
                                                            <button  id="kt_login_signin_submit" class="btn btn-brand btn-pill btn-elevate">Save<i class="kt-menu__link-icon flaticon2-add kt-margin-r-15"></i></button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form:form>
                                        </div>

                                        <div class="kt-login__form">
                                            <div class="row">
                                                <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                                                    <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                                        <div class="kt-portlet__head kt-portlet__head--noborder">
                                                            <div class="kt-portlet__head-label">                                                               
                                                                <%@include file="alert.jsp" %>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <c:if test="${!empty allAgencys}">                                                         
                                                    <div class="col-lg-12 col-xl-12 order-lg-2 order-xl-2">
                                                        <div class="kt-portlet kt-portlet--height-fluid"> 
                                                            <div class="kt-portlet__body"> 
                                                                <table  class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1" >
                                                                    <thead class="thead-dark">
                                                                        <tr>
                                                                            <th style="color:white" >Store Location</th>
                                                                            <th style="color:white" >Action</th>
                                                                    </thead>
                                                                    <tbody>
                                                                        <c:forEach items="${allAgencys}" var="agency">
                                                                            <tr>
                                                                                <td scope="row"> <i class="flaticon2-correct"></i> ${agency.agencyName} </td>                            
                                                                                <td>
                                                                                    <div class="row">
                                                                                        <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                                                                                            <a href="<c:url value='http://127.0.0.1:800/agency/edit/${agency.agencyId}' />">
                                                                                                <button  type="button" class="btn btn-brand btn-pill btn-elevate"><i class="kt-menu__link-icon flaticon-edit-1"></i></button>&nbsp;
                                                                                            </a> 
                                                                                        </div>
                                                                                        <div class="col-lg-2 col-xl-2 order-lg-1 order-xl-1">
                                                                                            <button  class="btn btn-brand btn-pill btn-elevate" type="button" data-toggle="modal" data-target="#myModal${agency.agencyId}">   <i class="flaticon-delete"></i></button>
                                                                                        </div>
                                                                                    </div>    
                                                                                </td>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </tbody>
                                                                </table>
                                                            </div> 
                                                        </div> 
                                                    </div> 
                                                </c:if>
                                            </div>       
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>             
                </div>
            </div>
        </div>
    </div>    
</div>    
<%@include file="administratorfoot.jsp" %>
<c:choose>
    <c:when test="${allAgencys.size() > 0 }">
        <c:forEach var="agency" items="${allAgencys}">                                             
            <div id="myModal${agency.agencyId}" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                        </div>
                        <div class="modal-body">  
                            <div class="form">
                                <div class="row">
                                    <div class="col-6 form-group">
                                        <input  class="form-control" type="hidden" value="${agency.agencyId}" name="agencyId${agency.agencyId}" id="agencyId${agency.agencyId}"/>
                                    </div>
                                    <div class="col-6 kt-login__actions" onclick="deleteAgency${agency.agencyId}">
                                        <button   onclick="deleteAgency${agency.agencyId}()" class="btn btn-dark btn-pill btn-elevate">Delete<i class="kt-menu__link-icon flaticon2-add"></i></button>
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
                function deleteAgency${agency.agencyId}() {
                    var agencyId = $('#agencyId${agency.agencyId}').val();
                    const swalWithBootstrapButtons = Swal.mixin({
                        customClass: {
                            confirmButton: 'btn btn-brand',
                            cancelButton: 'btn btn-dark'
                        },
                        buttonsStyling: false
                    });
                    swalWithBootstrapButtons.fire({
                        title: 'Are you sure?',
                        text: "You are about to delete a agency!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Yes, proceed!',
                        cancelButtonText: 'No, Go back!',
                        reverseButtons: true
                    }).then((result) => {
                        if (result.value) {
                            $.ajax({
                                type: "POST",
                                url: 'http://127.0.0.1:800/agency/delete/' + agencyId,
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