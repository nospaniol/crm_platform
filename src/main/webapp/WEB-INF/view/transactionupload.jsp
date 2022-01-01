<%@include file="administratortablehead.jsp" %>
<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
    <div class="row row-col-separator-xl">
        <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1 kt-portlet">
            <h3 class="title"> 
                TRANSACTION UPLOAD
            </h3>
            <%@include file="alert.jsp" %>
        </div>
        <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1  kt-portlet">
            <div class="row">
                <div class="col-6">
                    <a href="<c:url value="/resources/templates/transaction-template.xlsx" />" download="transactions-general-template">
                        <button  style="font-size:10px;"  class="btn btn-brand btn-pill btn-elevate">General Template<i class="kt-menu__link-icon flaticon2-download"></i></button>
                    </a>
                </div>
                <div class="col-6">
                    <a href="<c:url value="/resources/templates/transaction-custom-template.xlsx" />" download="transactions-custom-template">
                        <button  style="font-size:10px;"  class="btn btn-brand btn-pill btn-elevate">Custom Template<i class="kt-menu__link-icon flaticon2-download"></i></button>
                    </a>
                </div>
            </div>
        </div>
    </div>


    <div class="row row-col-separator-xl">
        <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1 kt-portlet">
            <c:url var="uploadUrl" value="/transaction/upload/file/save" />
            <form:form id="kt_innovative_form" class="kt-form" action="${uploadUrl}" method="post" modelAttribute="transaction" enctype="multipart/form-data" >
                <div class="form-group">
                    <form:select  style="font-size:10px;"  path="client" type="text" placeholder="select  category" class="form-control" id="client" onchange="loadDepartments()">
                        <form:option value="" label="--- select client ---"/>
                        <c:forEach var="clientProfile" items="${clientList}">  
                            <form:option value="${clientProfile.companyName}" label="${clientProfile.companyName}"/>
                        </c:forEach>
                    </form:select>
                    <span class="form-text text-muted">Please select a client.</span>
                </div>
                <div class="form-group" id="department-panel">
                    <form:select  style="font-size:10px;"   path="department" class="form-control" type="text" placeholder="select  department" id="department" />
                    <span class="form-text text-muted">Please select the department.</span> 
                </div> 
                <div class="form-group">
                    <form:select  style="font-size:10px;"  path="type" type="text" placeholder="select  dump type" class="form-control" id="type" >
                        <form:option value="GENERAL" label="GENERAL"/>
                        <form:option value="CUSTOM" label="CUSTOM"/>
                    </form:select>
                    <span class="form-text text-muted">Please select a dump excel type.</span>
                </div>
                <div class="form-group">
                    <form:select style="font-size:10px;"  path="dateType" type="text" placeholder="select date type" class="form-control" >
                        <form:option value="ddMMyyyy" label="dd/MM/yyyy"/>
                        <form:option value="mmDDyyyy" label="mm/DD/yyyy"/>
                    </form:select>
                    <span class="form-text text-muted">Please select a date type.</span>
                </div>
                <div class="form-group">
                    <label style="font-size:10px;" class="col-xs-2 control-label">Select file :</label>
                    <div class="col-xs-8">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span style="font-size:10px;" class="input-group-text" id="inputGroupFileAddon01">Upload</span>
                            </div>
                            <div class="custom-file">
                                <form:input style="font-size:10px;"  path="transactions" type="file" accept=".xlsx"
                                            class="custom-file-input" id="inputGroupFile01"
                                            aria-describedby="inputGroupFileAddon01" /> 
                                <label style="font-size:10px;" class="custom-file-label" for="inputGroupFile01" required="">Choose file</label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="kt-login__actions">
                    <button  id="kt_innovative_submit" class="btn btn-brand btn-pill btn-elevate">Save<i class="kt-menu__link-icon flaticon2-add kt-margin-r-15"></i></button>
                </div>                                            
            </form:form>
        </div>
        <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1 kt-portlet">
            <c:if test="${!empty missingplates}">                
                <table  class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1" >
                    <thead class="thead-dark">                        <tr>
                            <th style="color:white" >Plates</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${missingplates}" var="plate">
                            <tr>
                                <td scope="row"> <i class="flaticon2-correct"></i> ${plate} </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table> 
            </c:if>
        </div> 
    </div> 
</div>

<%@include file="administratorfoot.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/transaction-load-department.js?v=1001" />"   type="text/javascript"></script>
