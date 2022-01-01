<%@include file="administratorhead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet">
            <div class="kt-portlet__body  kt-portlet__body--fit">
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                        <div class="kt-login__body">                              
                            <div class="kt-login__signup">                                        
                                <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                    <div class="kt-portlet__head kt-portlet__head--noborder">
                                        <div class="kt-portlet__head-label">
                                            <h3 class="kt-portlet__head-title">
                                                VEHICLE UPLOAD
                                            </h3>
                                            <%@include file="alert.jsp" %>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                        <div class="kt-login__body">                              
                            <div class="kt-login__signup">                                        
                                <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                    <div class="kt-portlet__head kt-portlet__head--noborder">
                                        <div class="kt-portlet__head-label">
                                            <a href="<c:url value="/resources/templates/car-template.xlsx" />" download="car-batch-upload">
                                                <button  class="btn btn-brand btn-pill btn-elevate">Template<i class="kt-menu__link-icon flaticon2-download"></i></button>
                                            </a>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                        <div class="kt-login__form">
                            <c:url var="uploadUrl" value="/vehicle/upload/file/save" />
                            <form:form id="kt_innovative_form" class="kt-form" action="${uploadUrl}" method="post" modelAttribute="vehicle" enctype="multipart/form-data" >
                                <div class="form-group">
                                    <form:select  path="client" type="text" placeholder="select  category" class="form-control" id="client" onchange="loadDepartments()">
                                        <form:option value="" label="--- select client ---"/>
                                        <c:forEach var="clientProfile" items="${clientList}">  
                                            <form:option value="${clientProfile.companyName}" label="${clientProfile.companyName}"/>
                                        </c:forEach>
                                    </form:select>
                                    <span class="form-text text-muted">Please select  a client.</span>
                                </div>
                                <div class="form-group" id="department-panel">
                                    <form:select  path="department" class="form-control" type="text" placeholder="select  department" id="department"/>
                                    <span class="form-text text-muted">Please select  the department.</span> 
                                </div>
                                <div class="form-group" id="type-panel">
                                    <form:select  path="type" type="text" placeholder="select  type" class="form-control" id="type">
                                        <form:option value="" label="--- select type ---"/>
                                        <c:forEach var="type" items="${types}">  
                                            <form:option value="${type}" label="${type}"/>
                                        </c:forEach>
                                    </form:select>
                                    <span class="form-text text-muted">select type.</span> 
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-2 control-label">Select file :</label>
                                    <div class="col-xs-8">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
                                            </div>
                                            <div class="custom-file">
                                                <form:input path="vehicles" type="file" accept=".xlsx"
                                                            class="custom-file-input" id="inputGroupFile01"
                                                            aria-describedby="inputGroupFileAddon01" /> 
                                                <label  class="custom-file-label" for="inputGroupFile01" required="">Choose file</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="kt-login__actions">
                                    <button  id="kt_innovative_submit" class="btn btn-brand btn-pill btn-elevate">Save<i class="kt-menu__link-icon flaticon2-add kt-margin-r-15"></i></button>
                                </div>                                             

                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>             
</div>  
<%@include file="administratorfoot.jsp" %>
<script type="text/javascript">
    //var form = $('#kt_innovative_form');
    // form.reset();
    $('#department-panel').hide();
    $('#type-panel').hide();
    $('#client').prop('selectedIndex', 0);
    function loadDepartments() {
        $('#department').empty();
        var btn = $('#kt_innovative_submit');
        btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
        var e = document.getElementById("client");
        var result = e.options[e.selectedIndex].text;
        console.log(result);
        var clientname = e.options[e.selectedIndex].value;
        var clientnam = "";
        clientnam = clientname;
        $.ajax({
            type: "POST",
            data: {
                searchItem: clientnam
            },
            url: 'http://127.0.0.1:800/client/load/departments/',
            success: function (res) {
                console.log("loading departments...");
                var response = res;
                var title = response["title"];
                var message = response["message"];
                var result = response["results"];
                if (title === "fail") {
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    return;
                }
                var search_results = response["results"];
                $('#department').empty();
                $('#department').append('<option value="">Select a department first</option>');
                search_results.forEach((item) => {
                    $('#department').append('<option value="' + item.departmentName + '">' + item.departmentName + '</option>');
                });
                $('#department-panel').show();
                $('#type-panel').show();
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);

            },
            error: function (res) {
                var response = JSON.stringify(res);
                console.log(response);
            }
        });
    }
    function submitForm() {
        btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);

    }
</script>
