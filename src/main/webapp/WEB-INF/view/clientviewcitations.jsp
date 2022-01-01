<%@include file="clientheader.jsp" %>
<div class="kt-content" id="kt_content" style="padding-top:.2vh">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">  
        <c:choose>
            <c:when test="${allCitations.size() > 0 }">
                <div class="row row-col-separator-xl">        
                    <div class="col-xl-7 col-lg-7 order-lg-2 order-xl-1">
                        <div class="kt-portlet kt-portlet--height-fluid">
                            <form class="kt-form row" method="post">
                                <div class="col-4">
                                    <div class="kt-portlet__head-label">
                                        <div class="form-group" style="padding-left:2vh; padding-top:2vh;">
                                            <input style="font-size:12px;" class="form-control" type="date" placeholder="start date" id="startDate" name="startDate"></select>
                                            <span class="form-text text-muted">Please select  the start date.</span> 
                                        </div>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="kt-portlet__head-label">
                                        <div class="form-group" style="padding-left:2vh; padding-top:2vh;">
                                            <input style="font-size:12px;" class="form-control" type="date" placeholder="end date" id="endDate" name="endDate"></select>
                                            <span class="form-text text-muted">Please select  end date.</span> 
                                        </div>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="kt-portlet__head-label">
                                        <div class="form-group" style="padding-top:2vh;">
                                            <button style="font-size:8px;" id="kt_search_info" class=" btn btn-brand btn-pill btn-elevate">Search<i class="kt-menu__link-icon flaticon2-search kt-margin-r-15"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="col-xl-5 col-lg-5 order-lg-2 order-xl-1">
                        <div class="kt-portlet kt-portlet--height-fluid">
                            <div class="row">
                                <div clsss="col-6" style="padding-left:2vh; padding-top:2vh;">  
                                    <a target="_blank" href="<c:url value='http://127.0.0.1:800/client_citation/disputes' />">
                                        <button style="font-size:12px;"  type="button" class="btn btn-dark"><i class="flaticon-warning-sign"></i>View Disputes</button>
                                    </a> 
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">  
                    <div class="col-xl-12 col-lg-12 order-lg-2 order-xl-1">
                        <div class="kt-portlet kt-portlet--height-fluid">
                            <table  class="table table-striped- table-bordered table-hover table-checkable" id="citation_info" >
                                <thead class="thead-dark">
                                    <tr>
                                        <th style="color:white" >Citation Id</th>   
                                        <th style="color:white" >Client</th>   
                                        <th style="color:white" >Department</th> 
                                        <th style="color:white" >License Plate</th>
                                        <th style="color:white" >Citation Amount</th>
                                        <th style="color:white" >Citation Type</th>
                                        <th style="color:white" >Citation Status</th>
                                        <th style="color:white" >Citation Date</th>
                                        <th style="color:white" >Violation State</th>
                                        <th style="color:white" >Violation Number</th>
                                        <th style="color:white" >Payable To</th>
                                        <th style="color:white" >Paid Amount</th>
                                        <th style="color:white" >Actions</th>
                                    </tr>
                                </thead>
                                <tbody id="data_info">
                                    <c:choose>
                                        <c:when test="${allCitations.size() > 0 }">
                                            <c:forEach var="citation" items="${allCitations}">  
                                                <tr>  
                                                    <c:if test="${citation.citationDispute == true}">
                                                        <td style="color:lightskyblue;" title="DISPUTED CITATION">${citation.citationId}</td>
                                                        <td style="color:lightskyblue;" title="DISPUTED CITATION"> ${citation.clientProfile.companyName}</td>
                                                        <td style="color:lightskyblue;" title="DISPUTED CITATION">${citation.department.departmentName}</td>
                                                        <td style="color:lightskyblue;" title="DISPUTED CITATION">${citation.vehicle.licensePlate}</td>
                                                        <td style="color:lightskyblue;" title="DISPUTED CITATION">${citation.citationAmount}</td>
                                                        <td style="color:lightskyblue;" title="DISPUTED CITATION">${citation.citationType.citationTypeName}</td>
                                                        <td style="color:lightskyblue;" title="DISPUTED CITATION">${citation.citationStatus}</td>
                                                        <td style="color:lightskyblue;" title="DISPUTED CITATION">${citation.citationDate}</td>
                                                        <td style="color:lightskyblue;" title="DISPUTED CITATION">${citation.violationState.stateName}</td>
                                                        <td style="color:lightskyblue;" title="DISPUTED CITATION">${citation.violationNumber}</td>
                                                        <td style="color:lightskyblue;" title="DISPUTED CITATION">${citation.payableTo}</td>
                                                        <td style="color:lightskyblue;" title="DISPUTED CITATION">${citation.paidAmount}</td>
                                                    </c:if>
                                                    <c:if test="${citation.citationDispute == false}"> 
                                                        <td style="font-size:12px;"> ${citation.citationId}</td>
                                                        <td style="font-size:12px;"> ${citation.clientProfile.companyName}</td>
                                                        <td style="font-size:12px;">${citation.department.departmentName}</td>
                                                        <td style="font-size:12px;">${citation.vehicle.licensePlate}</td>
                                                        <td style="font-size:12px;">${citation.citationAmount}</td>
                                                        <td style="font-size:12px;">${citation.citationType.citationTypeName}</td>
                                                        <td style="font-size:12px;">${citation.citationStatus}</td>
                                                        <td style="font-size:12px;">${citation.citationDate}</td>
                                                        <td style="font-size:12px;">${citation.violationState.stateName}</td>
                                                        <td style="font-size:12px;">${citation.violationNumber}</td>
                                                        <td style="font-size:12px;">${citation.payableTo}</td>
                                                        <td style="font-size:12px;">${citation.paidAmount}</td>
                                                    </c:if>
                                                    <td nowrap></td>

                                                </tr>

                                            <div id="myModal${citation.citationId}" class="modal fade" role="dialog">
                                                <div class="modal-dialog">
                                                    <!-- Modal content-->
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h4 class="modal-title">Reason for dispute</h4>
                                                            <p>CITATION ID : ${citation.citationId}</p>
                                                        </div>
                                                        <div class="modal-body">  
                                                            <div class="form">
                                                                <div class="row">
                                                                    <div class="form-group">
                                                                        <input  class="form-control" type="hidden" value="${citation.citationId}" name="citationId${citation.citationId}" id="citationId${citation.citationId}"/>
                                                                    </div>  
                                                                    <div class="form-group">
                                                                        <textarea class="form-control" type="text" placeholder="Dispute" name="dispute${citation.citationId}" id="dispute${citation.citationId}" required=""></textarea>
                                                                    </div>                                                        
                                                                </div>                                                    
                                                                <div class="row">
                                                                    <div class="kt-login__actions" onclick="submitDispute${citation.citationId}">
                                                                        <button   onclick="submitDispute${citation.citationId}()" class="btn btn-brand btn-pill btn-elevate">Submit<i class="kt-menu__link-icon flaticon2-add"></i></button>
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
                                                function submitDispute${citation.citationId}() {
                                                    var citationId = $('#citationId${citation.citationId}').val();
                                                    var dispute = $('#dispute${citation.citationId}').val();

                                                    const swalWithBootstrapButtons = Swal.mixin({
                                                        customClass: {
                                                            confirmButton: 'btn btn-brand',
                                                            cancelButton: 'btn btn-dark'
                                                        },
                                                        buttonsStyling: false
                                                    });
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
                                                                url: 'http://127.0.0.1:800/client_citation/post/dispute/' + citationId + '/' + dispute,
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
                                            <td style="font-size:12px;">No Data</td>
                                            <td style="font-size:12px;">No Data</td>
                                            <td style="font-size:12px;">No Data</td>
                                            <td style="font-size:12px;">No Data</td>
                                            <td style="font-size:12px;">No Data</td>
                                            <td style="font-size:12px;">No Data</td>
                                            <td style="font-size:12px;">No Data</td>
                                            <td style="font-size:12px;">No Data</td>
                                            <td style="font-size:12px;">No Data</td>
                                            <td style="font-size:12px;">No Data</td>
                                            <td style="font-size:12px;">No Data</td>
                                            <td style="font-size:12px;">No Data</td>
                                            <td nowrap></td> 
                                        </tr>                            
                                    </c:otherwise>
                                </c:choose>
                                </tbody>
                            </table>
                        </div> 
                    </div> 
                </div> 
            </c:when>
            <c:otherwise>
                <div class="row kt-portlet" style="padding:0vh;">
                    <div class="col-12">
                        <!-- Month summary -->
                        <div class="kt-portlet kt-portlet--height-fluid">
                            <div class="kt-portlet__head" style="background-color:black;">
                                <div class="kt-portlet__head-label">
                                    <h3 class="kt-portlet__head-title text-uppercase  text-white">
                                        NO CITATIONS HAVE BEEN UPLOADED YET
                                    </h3>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </c:otherwise>
        </c:choose>
    </div> 
</div>   

<%@include file="clientdashtablefooter.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-client-citation.js?v=1001" />"   type="text/javascript"></script>
<script>
    $(document).ready(function () {
        $("input[type='search']").wrap("<form>");
        $("input[type='search']").closest("form").attr("autocomplete", "off");
    });
</script>
<script>
                                                $('.dummy-info').hide();
</script>
</body>