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
                                                            State
                                                        </h3>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="kt-login__form">
                                            <c:url var="stateUrl" value="/state/register/save" />
                                            <form:form class="kt-form" action="${stateUrl}" method="post" modelAttribute="state" >
                                                <div class="form-group">
                                                    <form:input   path="stateId" class="form-control" type="hidden" name="stateId" id="stateId"/>
                                                </div>   
                                                <div class="row">
                                                    <div class="col-xl-4 col-lg-4 order-lg-1 order-xl-1">
                                                        <div class="form-group">
                                                            <form:input   path="stateName" class="form-control" type="text" placeholder="State" name="stateName" id="stateName" required=""/>
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
                                                <c:if test="${!empty allStates}">                                                         
                                                    <div class="col-lg-12 col-xl-12 order-lg-2 order-xl-2">
                                                        <div class="kt-portlet kt-portlet--height-fluid"> 
                                                            <div class="kt-portlet__body"> 
                                                                <table  class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1" >
                                                                    <thead class="thead-dark">
                                                                        <tr>
                                                                            <th style="color:white" >State</th>
                                                                            <th style="color:white" >Action</th>
                                                                    </thead>
                                                                    <tbody>
                                                                        <c:forEach items="${allStates}" var="state">
                                                                            <tr>
                                                                                <td scope="row"> <i class="flaticon2-correct"></i> ${state.stateName} </td>                            
                                                                                <td>
                                                                                    <div class="row">
                                                                                        <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                                                                                            <a href="<c:url value='http://127.0.0.1:800/state/edit/${state.stateId}' />">
                                                                                                <button  type="button" class="btn btn-brand btn-pill btn-elevate"><i class="kt-menu__link-icon flaticon-edit-1"></i></button>&nbsp;
                                                                                            </a> 
                                                                                        </div>
                                                                                        <div class="col-lg-2 col-xl-2 order-lg-1 order-xl-1">
                                                                                            <button  class="btn btn-brand btn-pill btn-elevate" type="button" data-toggle="modal" data-target="#myModal${state.stateId}">   <i class="flaticon-delete"></i></button>
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
    <c:when test="${allStates.size() > 0 }">
        <c:forEach var="state" items="${allStates}">                                             
            <div id="myModal${state.stateId}" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                        </div>
                        <div class="modal-body">  
                            <div class="form">
                                <div class="row">
                                    <div class="col-6 form-group">
                                        <input  class="form-control" type="hidden" value="${state.stateId}" name="stateId${state.stateId}" id="stateId${state.stateId}"/>
                                    </div>
                                    <div class="col-6 kt-login__actions" onclick="deleteState${state.stateId}">
                                        <button   onclick="deleteState${state.stateId}()" class="btn btn-dark btn-pill btn-elevate">Delete<i class="kt-menu__link-icon flaticon2-add"></i></button>
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
                function deleteState${state.stateId}() {
                    var stateId = $('#stateId${state.stateId}').val();
                    const swalWithBootstrapButtons = Swal.mixin({
                        customClass: {
                            confirmButton: 'btn btn-brand',
                            cancelButton: 'btn btn-dark'
                        },
                        buttonsStyling: false
                    });
                    swalWithBootstrapButtons.fire({
                        title: 'Are you sure?',
                        text: "You are about to delete a state!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Yes, proceed!',
                        cancelButtonText: 'No, Go back!',
                        reverseButtons: true
                    }).then((result) => {
                        if (result.value) {
                            $.ajax({
                                type: "POST",
                                url: 'http://127.0.0.1:800/state/delete/' + stateId,
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