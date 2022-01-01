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
            <c:url var="uploadUrl" value="/transaction/delete/batch" />
            <form:form id="kt_innovative_form" class="kt-form" action="${uploadUrl}" method="post" modelAttribute="transaction" enctype="multipart/form-data" >
                <div class="form-group">
                    <form:select  style="font-size:10px;"  path="client" type="text" placeholder="select category" class="form-control" id="client" onchange="loadDepartments()">
                        <form:option value="" label="--- select client ---"/>
                        <c:forEach var="clientProfile" items="${clientList}">  
                            <form:option value="${clientProfile.companyName}" label="${clientProfile.companyName}"/>
                        </c:forEach>
                    </form:select>
                    <span class="form-text text-muted">Please select a client.</span>
                </div>
                <div class="form-group">
                    <form:select class="form-control" type="text"  path="dateType" placeholder="select month" id="month1" name="month1">
                        <form:option value="" selected="selected" label="${nsmonth}"/>
                        <form:option value="JANUARY" label="JANUARY"/>
                        <form:option value="FEBRUARY" label="FEBRUARY"/>
                        <form:option value="MARCH" label="MARCH"/>
                        <form:option value="APRIL" label="APRIL"/>
                        <form:option value="MAY" label="MAY"/>
                        <form:option value="JUNE" label="JUNE"/>
                        <form:option value="JULY" label="JULY"/>
                        <form:option value="AUGUST" label="AUGUST"/>
                        <form:option value="SEPTEMBER" label="SEPTEMBER"/>
                        <form:option value="OCTOBER" label="OCTOBER"/>
                        <form:option value="NOVEMBER" label="NOVEMBER"/>
                        <form:option value="DECEMBER" label="DECEMBER"/>
                    </form:select>
                    <span class="form-text text-muted">Please select month.</span>
                </div>
                <div class="form-group">
                    <form:select style="width:100px;" path="transactionDate" class="form-control" type="text" placeholder="select department" id="year1" name="year1">
                        <form:option value="" selected="selected" label="${nsyear}"/>
                        <form:option value="2020" label="2020"/>
                        <form:option value="2021" label="2021"/>
                    </form:select>
                    <span class="form-text text-muted">Please select year.</span>
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
