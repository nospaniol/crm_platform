<%@include file="administratortablehead.jsp" %>
<div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
    <div class="row row-col-separator-xl">
        <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1 kt-portlet">
            <h3 class="title"> 
                DELETE BY CLIENT 
            </h3>
            <%@include file="alert.jsp" %>
        </div>
    </div>


    <div class="row row-col-separator-xl">
        <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1 kt-portlet">
            <c:url var="uploadUrl" value="/vehicle/delete/batch" />
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
                <div class="kt-login__actions">
                    <button  id="kt_innovative_submit" class="btn btn-brand btn-pill btn-elevate">DELETE<i class="kt-menu__link-icon flaticon2-add kt-margin-r-15"></i></button>
                </div>                                            
            </form:form>
        </div>            
    </div> 
</div>

<%@include file="administratorfoot.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/transaction-load-department.js?v=1001" />"   type="text/javascript"></script>
