<%@include file="administratorhead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet">
            <div class="kt-portlet__body  kt-portlet__body--fit">
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                        <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                            <div class="kt-portlet__head kt-portlet__head--noborder">
                                <div class="kt-portlet__head-label">
                                    <h3 class="kt-portlet__head-title">
                                        Store Location
                                    </h3>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                        <c:url var="storeLocationUrl" value="/store/register/save" />
                        <form:form class="kt-form" action="${storeLocationUrl}" method="post" modelAttribute="storeLocation" enctype="multipart/form-data" >
                            <div class="form-group">
                                <form:input   path="storeLocationId" class="form-control" type="hidden" name="storeLocationId" id="storeLocationId"/>
                            </div>   
                            <div class="row">
                                <div class="col-xl-4 col-lg-4 order-lg-1 order-xl-1">
                                    <div class="form-group">
                                        <form:input   path="storeLocationName" class="form-control" type="text" placeholder="Store location" name="storeLocationName" id="storeLocationName" required=""/>
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
                    <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">                                                              
                        <%@include file="alert.jsp" %>
                        <c:if test="${!empty allStoreLocations}">      
                            <table  class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1" >
                                <thead class="thead-dark">
                                    <tr>
                                        <th style="color:white" >Store Location</th>
                                        <th style="color:white" >Action</th>
                                </thead>
                                <tbody>
                                    <c:forEach items="${allStoreLocations}" var="storeLocation">
                                        <tr>
                                            <td scope="row"> <i class="flaticon2-correct"></i> ${storeLocation.storeLocationName} </td>                            
                                            <td>
                                                <div class="row">
                                                    <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                                                        <a href="<c:url value='http://127.0.0.1:800/store/edit/${storeLocation.storeLocationId}' />">
                                                            <button  type="button" class="btn btn-brand btn-pill btn-elevate"><i class="kt-menu__link-icon flaticon-edit-1"></i></button>&nbsp;
                                                        </a> 
                                                    </div>
                                                    <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                                                        <form class="kt-form" id="kt_form" method="post">
                                                            <input  type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />  
                                                            <div class="kt-form__section kt-form__section--first">
                                                                <div class="kt-wizard-v3__form"> 
                                                                    <input  type="hidden" class="cancelItem" name="cancelItem" value="${storeLocation.storeLocationId}"/>
                                                                    <div class="kt-login__actions">
                                                                        <button  id="kt_innovative_cancel" class="kt_innovative_cancel btn btn-danger"><i class="kt-menu__link-icon flaticon-delete"></i></button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </form> 
                                                    </div>
                                                </div>    
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                    </div> 
                </div> 
            </div> 
        </div>       
    </div>
</div>  
<%@include file="administratorfoot.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/delete-storelocation.js?v=1001" />"   type="text/javascript"></script>
