<%@include file="clientheader.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">

        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                    <div class="kt-portlet__head kt-portlet__head--noborder">
                        <div class="kt-portlet__head-label">
                            <h3 class="kt-portlet__head-title">
                                Make an order
                            </h3>
                            <%@include file="alert.jsp" %>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-xl-4 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                    <div class="kt-portlet__head kt-portlet__head--noborder row">
                        <div class="kt-portlet__head-label col-6">
                            <a class="kt-menu__link" href="<c:url value='http://127.0.0.1:800/client_transponder/view_order' />">
                                <button style="font-size:12px;" type="button"  style="color:black;font-size:12px;" class="btn btn-brand btn-pill btn-elevate">
                                    <i class="flaticon-search-magnifier-interface-symbol"></i> View Orders</button>
                            </a>  
                        </div>
                        <div class="kt-portlet__head-label col-6">
                            <a class="kt-menu__link" href="<c:url value='http://127.0.0.1:800/client_transponder/view_saved_orders' />">
                                <button style="font-size:12px;" type="button"  style="color:black;font-size:12px;" class="btn btn-dark btn-pill btn-elevate">
                                    <i class="flaticon-search-magnifier-interface-symbol"></i> Saved Orders</button>
                            </a>  
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                <div class="kt-login__form">
                    <c:url var="orderUrl" value="/client_transponder/upload/file/save" />
                    <form:form  id="kt_innovative_form" class="kt-form" action="${orderUrl}" method="post" modelAttribute="transponders" enctype="multipart/form-data" >
                        <div class="row">
                            <div class="col-xl-5 col-lg-5 order-lg-1 order-xl-1"> 
                                <div class="form-group">
                                    <form:input path="orderId" class="form-control" type="hidden"  id="orderId" required=''/>
                                </div>
                                <div class="form-group">
                                    <form:input  path="transponderQuantity" class="form-control" type="text" placeholder="Transponder Quantity" id="transponderQuantity"/>
                                    <span class="form-text text-muted">Enter transponder quantity*.</span> 
                                </div>
                                <div class="form-group">
                                    <form:input  path="extraVelcro" class="form-control" type="text" placeholder="Extra Velcro"  id="extraVelcro"/>
                                    <span class="form-text text-muted">Enter extra velcro.</span> 
                                </div>

                                <c:choose>
                                    <c:when test="${institution=='AMAZON'||parent_institution=='AMAZON'}">
                                        <div class="form-group">
                                            <form:select class="form-control" path="assetName" id="assetName">
                                                <form:option class="select-item" value="AFP" selected="selected" label="AFP OWNED"/>
                                                <form:option class="select-item" value="AFP" label="RENTAL OWNED"/>
                                            </form:select>
                                            <span class="form-text text-muted">Please Specify Asset (Rental OR AFP Owned)*.</span> 
                                        </div>
                                        <div class="form-group">
                                            <label style="font-size:10px;" class="col-xs-2 control-label">Upload Asset Details :</label>
                                            <div class="col-xs-8">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <span style="font-size:10px;" class="input-group-text" id="inputGroupFileAddon02">Upload</span>
                                                    </div>
                                                    <div class="custom-file">
                                                        <form:input style="font-size:10px;" path="assetFile" type="file" accept=".xlsx"
                                                                    class="custom-file-input" id="inputGroupFile02"
                                                                    aria-describedby="inputGroupFileAddon02" /> 
                                                        <label  style="font-size:10px;" class="custom-file-label" for="inputGroupFile02" required="">Choose file</label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>   
                                        <div class="form-group">
                                            <form:input  path="domicileTerminal" class="form-control" type="text" placeholder="Domicile Terminal"  id="domicileTerminal"/>
                                            <span class="form-text text-muted">Please Select Domicile Terminal.</span> 
                                        </div>
                                    </c:when>
                                </c:choose>

                                <c:choose>
                                    <c:when test="${institution=='PROTECH AS'||parent_institution=='PROTECH AS'}">
                                        <c:choose>
                                            <c:when test="${departments.size() > 0 }">
                                                <div class="form-group" id="department-panel">
                                                    <form:select class="form-control" path="department" id="departmentList">
                                                        <form:option class="select-item" value="" selected="selected" label="Select department"/>
                                                        <c:forEach var="department" items="${departments}"> 
                                                            <form:option class="select-item" value="${department.departmentName}" label="${department.departmentName}"/>
                                                        </c:forEach>
                                                    </form:select>
                                                    <span class="form-text text-muted">Please Specify Cost Center.</span> 
                                                </div>    
                                            </c:when>
                                        </c:choose>

                                        <div class="form-group">
                                            <form:input path="licensePlate" class="form-control" type="text" placeholder="License Plate" id="licensePlate"/>
                                            <span class="form-text text-muted">Enter license plate.</span> 
                                        </div>
                                        <div class="form-group">
                                            <form:input path="customerVehicleId" class="form-control" type="text" placeholder="Customer Vehicle Id" id="customerVehicleId"/>
                                            <span class="form-text text-muted">Enter customer vehicle ID</span> 
                                        </div>     
                                        <div class="form-group">
                                            <form:select path="state" class="form-control" type="text" placeholder="select status" id="state">
                                                <c:choose>
                                                    <c:when test="${states.size() > 0 }">
                                                        <c:forEach var="state" items="${states}"> 
                                                            <form:option class="select-item" value="${state}" label="${state}"/>
                                                        </c:forEach>
                                                    </c:when>
                                                </c:choose>
                                            </form:select>
                                        </div>
                                    </c:when>
                                </c:choose>
                            </div>

                            <div class="col-xl-1 col-lg-1 order-lg-1 order-xl-1"> 
                            </div>

                            <div class="col-xl-5 col-lg-5 order-lg-1 order-xl-1"> 
                                <div class="form-group">
                                    <form:input path="shippingAddress" class="form-control" type="text" placeholder="Shipping Address" id="shippingAddress"/>
                                    <span class="form-text text-muted">Please enter shipping address*</span> 
                                </div> 
                                <div class="form-group">
                                    <label style="font-size:10px;" class="col-xs-2 control-label">Upload shipping list:</label>
                                    <div class="col-xs-8">
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span style="font-size:10px;" class="input-group-text" id="inputGroupFileAddon01">Upload</span>
                                            </div>
                                            <div class="custom-file">
                                                <form:input style="font-size:10px;" path="shippingFile" type="file" accept=".xlsx"
                                                            class="custom-file-input" id="inputGroupFile01"
                                                            aria-describedby="inputGroupFileAddon01" /> 
                                                <label  style="font-size:10px;" class="custom-file-label" for="inputGroupFile01" required="">Choose file</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <form:textarea path="instructions" class="form-control" type="text" placeholder="Instructions..." id="instructions"/>
                                    <span class="form-text text-muted">Please provide additional instructions specific to this order</span> 
                                </div>                          
                                <div class="row">
                                    <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1"> 
                                        <div class="kt-login__actions">
                                            <button  id="kt_login_signin_submit" class="btn btn-dark btn-pill btn-elevate">Submit Order<i class="kt-menu__link-icon flaticon2-add kt-margin-r-15"></i></button>
                                        </div>
                                    </div>
                                </div> 
                            </div> 
                        </div>

                    </form:form>

                    <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1"> 
                        <div class="kt-login__actions">
                            <button  id="kt_save" onclick="saveOrder()" class="btn btn-brand btn-pill btn-elevate">Save<i class="kt-menu__link-icon flaticon2-add kt-margin-r-15"></i></button>
                        </div>
                    </div>  
                </div>
            </div>
        </div>
    </div>
</div>   
<%@include file="clientdashtablefooter.jsp" %>

<script>
    $(document).ready(function () {
        var opt = $("#departmentList option").sort(function (a, b) {
            return a.value.toUpperCase().localeCompare(b.value.toUpperCase())
        });
        $("#departmentList").append(opt);
    });

    function saveOrder() {
        const swalWithBootstrapButtons = Swal.mixin({
            customClass: {
                confirmButton: 'btn btn-brand',
                cancelButton: 'btn btn-dark'
            },
            buttonsStyling: false
        });
        var btn = $("#kt_save")
        btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
        var transponderQuantit = $("#transponderQuantity").val();
        var extraVelcr = $("#extraVelcro").val();
        var assetNam = $("#assetName").val();
        var domicileTermina = $("#domicileTerminal").val();
        var departmen = $("#departmentList option:selected").val();
        var stat = $("#state option:selected").val();
        var licensePlat = $("#licensePlate").val();
        var shippingAddres = $("#shippingAddress").val();
        var customerVehicleI= $("#customerVehicleId").val();
        var instruction = $("#instructions").val();

        if (transponderQuantit === null || transponderQuantit === "") {
            transponderQuantit = "0";
        }
        if (extraVelcr === null || extraVelcr === "") {
            extraVelcr = "0";
        }
        if (domicileTermina === null || domicileTermina === "") {
            domicileTermina = "NA";
        }
        if (assetNam === null || assetNam === "") {
            assetNam = "N/A";
        }
        if (departmen === null || departmen === "") {
            departmen = "NA";
        }
        if (stat === null || stat === "") {
            stat = "NA";
        }
        if (licensePlat === null || licensePlat === "") {
            licensePlat = "NA";
        }
        if (shippingAddres === null || shippingAddres === "") {
            shippingAddres = "NA";
        }
        if (customerVehicleI === null || customerVehicleI === "") {
            customerVehicleI = "NA";
        }
        if (instruction === null || instruction === "") {
            instruction = "NA";
        }

        console.log("TRANSPONDER :: "+transponderQuantit + extraVelcr + assetNam + departmen + domicileTermina + licensePlat + customerVehicleI + stat + instruction + shippingAddres);

        $.ajax({
            type: "POST",
            data: {
                transponderQuantity: transponderQuantit,
                extraVelcro: extraVelcr,
                assetName: assetNam,
                departmen: departmen,
                domicileTerminal: domicileTermina,
                licensePlate: licensePlat,
                customerVehicleId: customerVehicleI,
                state: stat,
                instructions: instruction,
                shippingAddress: shippingAddres
            },
            url: 'http://127.0.0.1:800/client_transponder/save/order/',
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
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    return;
                }
                swalWithBootstrapButtons.fire(
                        'Innovative Toll Solution',
                        message,
                        'success'
                        );
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
            },
            error: function (res) {
                var response = JSON.stringify(res);
                console.log(response);
            }
        });



        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
    }

</script>

