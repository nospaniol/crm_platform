<%@include file="administratortablehead.jsp" %>

<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet">
            <div class="row row-no-padding row-col-separator-xl">
                <div class="col-md-10 col-lg-10 order-lg-1 order-xl-1">                        
                    <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                        <div class="kt-portlet__head kt-portlet__head--noborder">
                            <div class="kt-portlet__head-label">
                                <h3 class="kt-portlet__head-title">
                                    ${profile.companyName} Departments
                                </h3>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="col-lg-2 col-xl-2 order-lg-1 order-xl-1 kt-margin-top-20">
                    <a href="<c:url value='http://127.0.0.1:800/client/view/${profile.clientProfileId}' />">
                        <button  type="button" class="btn btn-brand btn-pill btn-elevate">Client Profile<i class="kt-menu__link-icon flaticon-map kt-margin-r-15"></i></button>&nbsp;
                    </a> 
                </div>
            </div>
            <div class="row row-no-padding row-col-separator-xl">
                <div class="col-md-12 col-lg-12 order-lg-1 order-xl-1">  
                    <div class="kt-login__form">
                        <c:if test="${!empty depart}">
                            <c:url var="departmentUrl" value="/sub_department/register/saveProfile" />
                        </c:if>
                        <c:if test="${empty depart}">
                            <c:url var="departmentUrl" value="/department/register/saveProfile" />
                        </c:if>
                        <form:form class="kt-form" action="${departmentUrl}" method="post" modelAttribute="departmentForm" enctype="multipart/form-data" >
                            <div class="form-group">
                                <form:input   path="departmentId" class="form-control" type="hidden" name="departmentId" id="departmentId"/>
                                <form:input   path="clientProfileId" class="form-control" type="hidden" value="${profile.clientProfileId}" id="clientProfileId"/>
                            </div>   
                            <div class="row">
                                <div class="col-xl-4 col-lg-4 order-lg-1 order-xl-1">
                                    <div class="form-group">
                                        <form:input   path="departmentName" class="form-control" type="text" placeholder="Department Name" name="departmentName" id="departmentName" required=""/>
                                    </div>                                                        
                                </div> 
                                <div class="col-xl-4 col-lg-4 order-lg-1 order-xl-1">
                                    <div class="form-group">                                                            
                                        <div class="col-xs-8">
                                            <div class="input-group">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
                                                </div>
                                                <div class="custom-file">
                                                    <form:input  path="departmentLogo" type="file" name="departmentLogo" accept=".png,.jpg,.jpeg"
                                                                 class="custom-file-input" id="inputGroupFile01"
                                                                 aria-describedby="inputGroupFileAddon01" /> 
                                                    <label class="custom-file-label" for="inputGroupFile01" required="">Choose file</label>
                                                </div>
                                            </div>
                                        </div>
                                        <label class="col-xs-2 control-label">Logo is required for a new account *, On edit, select only if you want to modidy : </label>
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
                </div>
            </div>    
            <div class="row row-no-padding row-col-separator-xl">
                <div class="col-md-12 col-lg-12 order-lg-1 order-xl-1"> 
                    <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                        <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                            <div class="kt-portlet__head kt-portlet__head--noborder">
                                <div class="kt-portlet__head-label">                                                               
                                    <%@include file="alert.jsp" %>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${!empty allDepartments}">                                                         
                <div class="col-lg-12 col-xl-12 order-lg-2 order-xl-2">
                    <div class="kt-portlet kt-portlet--height-fluid"> 
                        <div class="kt-portlet__body"> 
                            <table  class="table table-striped- table-bordered table-hover table-checkable" id="department_info" >
                                <thead class="thead-dark">
                                    <tr>
                                        <th style="color:white" >Department ID</th> 
                                        <th style="color:white" >Department Name</th>                        
                                        <th style="color:white" >Department Logo</th>
                                        <th style="color:white" >Action</th>
                                </thead>
                                <tbody>
                                    <c:forEach items="${allDepartments}" var="department">
                                        <tr>
                                            <td> ${department.departmentId} </td> 
                                            <td> <i class="flaticon2-correct"></i> ${department.departmentName}  </td> 
                                            <td>
                                                <img alt="Department Logo" src="data:image/jpg;base64,${department.logo}" style="width:15vh;height:10vh;"/>
                                            </td> 
                                            <td>
                                                <button  class="dropdown-item" type="button" data-toggle="modal" data-target="#myModal${department.departmentId}">
                                                    <i class="flaticon-delete"></i></button>
                                                <a href="http://127.0.0.1:800/department/new/user/ ${department.departmentId} " class="btn btn-sm btn-clean btn-icon btn-icon-md" style="font-size:50px;" title="Add User"> 
                                                    <i class="la la-user"></i> 
                                                </a> 
                                                <a href="http://127.0.0.1:800/department/edit/ ${department.departmentId} " class="btn btn-sm btn-clean btn-icon btn-icon-md" title="View Profile"> 
                                                    <i class="la la-edit"></i> 
                                                </a>
                                                <c:if test="${empty depart}">
                                                    <a href="<c:url value='http://127.0.0.1:800/sub_department/view/department/${department.departmentId}' />">
                                                        <button  type="button" class="btn btn-brand btn-pill btn-elevate">Sub Departments<i class="kt-menu__link-icon flaticon-map kt-margin-r-15"></i></button>&nbsp;
                                                    </a> 

                                                </c:if>
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
<%@include file="dashtablefooter.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-department.js?v=1001" />"   type="text/javascript"></script>
<c:choose>
    <c:when test="${allDepartments.size() > 0 }">
        <c:forEach var="department" items="${allDepartments}">                                             
            <div id="myModal${department.departmentId}" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                        </div>
                        <div class="modal-body">  
                            <div class="form">
                                <div class="row">
                                    <div class="col-6 form-group">
                                        <input  class="form-control" type="hidden" value="${department.departmentId}" name="departmentId${department.departmentId}" id="departmentId${department.departmentId}"/>
                                    </div>
                                    <div class="col-6 kt-login__actions" onclick="deleteDepartment${department.departmentId}">
                                        <button   onclick="deleteDepartment${department.departmentId}()" class="btn btn-dark btn-pill btn-elevate">Delete<i class="kt-menu__link-icon flaticon2-add"></i></button>
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
                function deleteDepartment${department.departmentId}() {
                    var departmentId = $('#departmentId${department.departmentId}').val();
                    const swalWithBootstrapButtons = Swal.mixin({
                        customClass: {
                            confirmButton: 'btn btn-brand',
                            cancelButton: 'btn btn-dark'
                        },
                        buttonsStyling: false
                    });
                    swalWithBootstrapButtons.fire({
                        title: 'Are you sure?',
                        text: "You are about to delete a department!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonText: 'Yes, proceed!',
                        cancelButtonText: 'No, Go back!',
                        reverseButtons: true
                    }).then((result) => {
                        if (result.value) {
                            $.ajax({
                                type: "POST",
                                url: 'http://127.0.0.1:800/department/delete/' + departmentId,
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