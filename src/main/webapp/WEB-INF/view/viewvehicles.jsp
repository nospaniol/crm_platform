<%@include file="administratortablehead.jsp" %>

<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content" style="padding-top:8vh;">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
        <div class="row">          
            <div class="col-xl-5 col-lg-5 order-lg-1 order-xl-1">
                <div class="kt-portlet">
                    <div class="row row-no-padding row-col-separator-xl">
                        <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                            <a class="kt-menu__link"  target="_blank" href="<c:url value='http://127.0.0.1:800/vehicle/file/upload' />">
                                <button  style="font-size:10px;"  type="button" class="btn btn-brand"><i class="flaticon-upload-1"></i> Fleet List Upload</button>
                            </a> 
                        </div>
                        <div class="col-lg-3 col-xl-3 order-lg-1 order-xl-1">                            
                            <button style="font-size:8px;" onclick="printExcel()" type="button" style="font-size:12px;" class="btn btn-dark">
                                <i class="la la-file-excel-o"></i>EXPORT EXCEL
                            </button>
                        </div>
                        <div class="col-lg-3 col-xl-3 order-lg-1 order-xl-1">
                            <button style="font-size:8px;" onclick="printPdf()" type="button" style="font-size:12px;" class="btn btn-brand">
                                <i class="la la-file-pdf-o"></i>PRINT PDF
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-7 col-xl-7 order-lg-1 order-xl-1">
                <div class="kt-portlet">
                    <form:form id="kt_innovative_form" class="kt-form" method="post" modelAttribute="vehicle" >
                        <div class="row row-no-padding row-col-separator-xl">
                            <div class="col-xl-4 col-lg-4 order-lg-1 order-xl-1">
                                <div class="form-group">
                                    <form:select  style="font-size:10px;"  path="client" type="text" placeholder="select  category" class="form-control" id="client" onchange="loadDepartments()">
                                        <form:option value="" label="--- select client ---"/>
                                        <c:forEach var="clientProfile" items="${clientList}">  
                                            <form:option value="${clientProfile.companyName}" label="${clientProfile.companyName}"/>
                                        </c:forEach>
                                    </form:select>
                                    <span class="form-text text-muted">Please select  a client.</span>
                                </div>
                            </div>
                            <div class="col-xl-2 col-lg-2 order-lg-1 order-xl-1">
                                <div class="kt-login__actions">
                                    <button  id="kt_innovative_client" class="btn btn-brand">client<i class="kt-menu__link-icon flaticon-customer"></i></button>
                                </div>
                            </div>
                            <div class="col-xl-4 col-lg-4 order-lg-1 order-xl-1">
                                <div class="form-group" id="department-panel">
                                    <form:select  style="font-size:10px;"  path="department" class="form-control" type="text" placeholder="select  department" id="department"/>
                                    <span class="form-text text-muted">Please select  the department.</span> 
                                </div>
                            </div>                                                   
                            <div class="col-xl-2 col-lg-2 order-lg-1 order-xl-1">
                                <div class="kt-login__actions">
                                    <button  id="kt_innovative_department" class="btn btn-dark">department<i class="kt-menu__link-icon flaticon2-group"></i></button>
                                </div>
                            </div>                
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
        <div class="row"> 
            <div class="col-xl-12 col-lg-12 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <table class="table table-striped- table-bordered table-hover table-checkable" id="vehicle_info" >
                        <thead class="thead-dark">
                            <tr>  
                                <th style="color:white;font-size:10px;">ID</th>   
                                <th style="color:white;font-size:10px;">#</th>   
                                <th style="color:white;font-size:10px;">License Plate</th>                           
                                <th style="color:white;font-size:10px;">Toll tag</th>  
                                <th style="color:white;font-size:10px;">Unit</th>  
                                <th style="color:white;font-size:10px;">VIN</th> 
                                <th style="color:white;font-size:10px;">Make</th>                
                                <th style="color:white;font-size:10px;">Model</th>
                                <th style="color:white;font-size:10px;">Color</th>
                                <th style="color:white;font-size:10px;">Start Date</th>
                                <th style="color:white;font-size:10px;">End Date</th>
                                <th style="color:white;font-size:10px;">Actions</th>
                            </tr>
                        </thead>
                        <tbody id="data_info">
                            <c:choose>
                                <c:when test="${data.size() > 0 }">
                                    <c:forEach var="vehicle" items="${data}">                            
                                        <tr>
                                            <td style="font-size:10px;">${vehicle.vehicleId}</td>                                            
                                            <c:if test="${vehicle.vehicleStatus=='start'}">
                                                <td style="font-size:10px;"><i style="color:skyblue;" title="start" class="flaticon2-add-circular-button"></i></td>                                                
                                                </c:if>
                                                <c:if test="${vehicle.vehicleStatus=='active'}">
                                                <td style="font-size:10px;"><i style="color:blue;" title="active" class="flaticon2-correct"></i></td>                                                
                                                </c:if>
                                                <c:if test="${vehicle.vehicleStatus=='maintenance'}">
                                                <td style="font-size:10px;"><i style="color:black;" title="maintenance" class="flaticon2-correct"></i></td>                                                
                                                </c:if>
                                                <c:if test="${vehicle.vehicleStatus=='inactive'}">
                                                <td style="font-size:10px;"><i style="color:grey;" title="inactive" class="flaticon2-correct"></i></td>                                                
                                                </c:if>
                                                <c:if test="${vehicle.vehicleStatus=='end'}">
                                                <td style="font-size:10px;"><i style="color:red;" title="end" class="flaticon2-correct"></i></td>                                                
                                                </c:if>
                                            <td style="font-size:10px;">${vehicle.licensePlate}</td>
                                            <td style="font-size:10px;">${vehicle.tollTagId}</td>
                                            <td style="font-size:10px;">${vehicle.unit}</td>
                                            <td style="font-size:10px;">${vehicle.vin}</td>
                                            <td style="font-size:10px;">${vehicle.make}</td>
                                            <td style="font-size:10px;">${vehicle.model}</td>
                                            <td style="font-size:10px;">${vehicle.color}</td>
                                            <td style="font-size:10px;">${vehicle.startDate}</td>
                                            <td style="font-size:10px;">${vehicle.endDate}</td>
                                            <td style="font-size:10px;">
                                                <a title="View Details" href="http://127.0.0.1:800/vehicle/view/vehicle/info/${vehicle.vehicleId}" class="btn btn-sm btn-clean btn-icon btn-icon-md"> 
                                                    <i class="la la-book"></i> 
                                                </a> 
                                                <a href="http://127.0.0.1:800/vehicle/edit/vehicle/${vehicle.vehicleId}" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Edit Vehicle"> 
                                                    <i class="la la-edit"></i> 
                                                </a>                                                
                                            </td>
                                        </tr>
                                    </c:forEach>                                   
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td style="font-size:10px;">No Data</td>
                                        <td style="font-size:10px;">No Data</td>
                                        <td style="font-size:10px;">No Data</td>
                                        <td style="font-size:10px;">No Data</td>
                                        <td style="font-size:10px;">No Data</td>
                                        <td style="font-size:10px;">No Data</td>
                                        <td style="font-size:10px;">No Data</td>
                                        <td style="font-size:10px;">No Data</td>
                                        <td style="font-size:10px;">No Data</td>
                                        <td style="font-size:10px;">No Data</td>
                                        <td style="font-size:10px;">No Data</td>
                                        <td nowrap></td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

                    <c:if test="${data.size() > 0 }">
                        <nav aria-label="Pages">
                            <ul class="pagination pagination-sm  flex-wrap">
                                <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                                    <c:forEach begin="0" end="${totalPages-1}" var="page">                             
                                    <li class="page-item"><a class="page-link" href="<c:url value='http://127.0.0.1:800/vehicle/view_vehicle?page=${page}&size=${size}' />">${page+1}</a></li>
                                    </c:forEach>
                                <li class="page-item"><a class="page-link" href="#">Next</a></li>
                            </ul>
                        </nav>
                    </c:if>
                </div>
            </div>
        </div>
    </div> 
</div>
<%@include file="dashtablefooter.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-vehicle.js?v=1001" />"   type="text/javascript"></script>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/load-department.js?v=1001" />"   type="text/javascript"></script>

<script>
                                $(document).ready(function () {
                                    $("input[type='search']").wrap("<form>");
                                    $("input[type='search']").closest("form").attr("autocomplete", "off");
                                });

                                function printPdf() {
                                    var doc = new jsPDF();
                                    doc.addPage('a4', 'landscape');
                                    var tbl = $('#vehicle_info').clone();
                                    tbl.find('tr th:nth-child(1), tr td:nth-child(1)').remove();
                                    tbl.find('tr th:nth-child(11), tr td:nth-child(11)').remove();
                                    var res = doc.autoTableHtmlToJson(tbl.get(0));

                                    var imgData = document.getElementById("client-logo");
                                    doc.addImage(imgData, 'PNG', 120, 20, 45, 35);
                                    doc.autoTable(res.columns, res.data, {
                                        startY: 60,
                                        styles: {
                                            overflow: 'linebreak',
                                            fontSize: 8,
                                            columnWidth: 'wrap'
                                        },
                                        columnStyles: {
                                            1: {columnWidth: 'auto'}
                                        },
                                        createdCell: function (cell, data) {
                                            var a = this;
                                        }
                                    });
                                    doc = addWaterMark(doc);
                                    doc.deletePage(1);
                                    doc.save('innovativetoll.pdf');
                                }

                                function printExcel() {
                                    console.log("*****GENERATE EXCEL*******");
                                    var tbl = $('#vehicle_info').clone();
                                    tbl.find('tr th:nth-child(1), tr td:nth-child(1)').remove();
                                    tbl.find('tr th:nth-child(11), tr td:nth-child(11)').remove();
                                    $(tbl).table2excel({
                                        exclude: ".noExl",
                                        name: "Innovative",
                                        filename: "innovative-spreadsheet", //do not include extension
                                        fileext: ".xls"
                                    });
                                }
</script>
</body>
</html>